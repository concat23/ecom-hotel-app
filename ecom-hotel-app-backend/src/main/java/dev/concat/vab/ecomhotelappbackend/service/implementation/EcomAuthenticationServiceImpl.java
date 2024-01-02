package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.builders.EcomUserBuilder;
import dev.concat.vab.ecomhotelappbackend.converter.EcomUserConverter;
import dev.concat.vab.ecomhotelappbackend.dto.EcomUserDTO;
import dev.concat.vab.ecomhotelappbackend.enumeration.TokenType;
import dev.concat.vab.ecomhotelappbackend.model.EcomToken;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomUserRepository;
import dev.concat.vab.ecomhotelappbackend.request.AuthRequest;
import dev.concat.vab.ecomhotelappbackend.request.RegisterRequest;
import dev.concat.vab.ecomhotelappbackend.response.AuthResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomAuthenticationService;
import dev.concat.vab.ecomhotelappbackend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EcomAuthenticationServiceImpl implements IEcomAuthenticationService {

    // Logger for logging messages
    private static final Logger logger = LoggerFactory.getLogger(EcomAuthenticationServiceImpl.class);

    // Autowired dependencies
    private final IEcomUserRepository iEcomUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EcomUserConverter ecomUserConverter;
    private final TokenService tokenService;

    // Register a new user and generate an authentication token
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        // Log registration attempt with user's email
        logger.info("Registering a new user: {}", registerRequest.getEmail());

        if (iEcomUserRepository.existsByEmail(registerRequest.getEmail())) {
            // Log a warning since a user with the provided email already exists
            logger.warn("Registration failed. User with email {} already exists.", registerRequest.getEmail());

            // Return an error response indicating that registration failed
            return AuthResponse.builder()
                    .success(false)
                    .message("User with this email already exists. Registration failed.")
                    .build();
        }
        List<String> authorities = registerRequest.getRole().getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        EcomUser ecomUser = new EcomUserBuilder()
                .withFirstName(registerRequest.getFirstName())
                .withLastName(registerRequest.getLastName())
                .withEmail(registerRequest.getEmail())
                .withPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .withShowPassword(registerRequest.getPassword())
                .withAuthorities(authorities.toArray(new String[0]))
                .withRole(registerRequest.getRole())
                .build();

        // Save the new user to the repository
        this.iEcomUserRepository.save(ecomUser);

        // Generate a JWT token for the new user
        String jwtToken = this.jwtService.generateToken(ecomUser);

        // Create a token entity and save it
        EcomToken ecomToken = new EcomToken();
        ecomToken.setAccessToken(jwtToken);
        tokenService.saveToken(ecomToken, ecomUser);

        // Log successful registration with user's email
        logger.info("User registered successfully: {}", ecomUser.getEmail());

        // Return the authentication response with the generated token
        return AuthResponse.builder()
                .success(true)
                .tokenType(TokenType.BEARER.name())
                .accessToken(jwtToken)
                .message("Register successfully.")
                .build();
    }

    // Authenticate a user and generate a new authentication token
    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        // Log authentication attempt with user's email
        logger.info("Authenticating user: {}", authRequest.getEmail());

        // Perform authentication using Spring Security
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        // Retrieve the authenticated user from the repository
        EcomUser ecomUser = this.iEcomUserRepository.findByEmail(authRequest.getEmail()).orElseThrow();

        // Generate a new JWT token for the authenticated user
        String newJwtToken = this.jwtService.generateToken(ecomUser);

        // Create a new EcomToken and save it
        EcomToken ecomToken = new EcomToken();
        ecomToken.setAccessToken(newJwtToken);
        tokenService.saveToken(ecomToken, ecomUser);
        if(ecomUser.getId().equals(ecomToken.getEcomUser().getId())){
            tokenService.deleteToken(ecomToken.getId());
        }
        // Log successful authentication with user's email
        logger.info("User authenticated successfully: {}", ecomUser.getEmail());

        // Return the authentication response with the new token
        return AuthResponse.builder()
                .success(true)
                .tokenType(TokenType.BEARER.name())
                .accessToken(newJwtToken)
                .message("Login successfully.")
                .build();
    }

    // Update user information
    @Override
    public EcomUser updateUser(Long userId, EcomUserDTO ecomUserDTO) {
        // Retrieve the existing user from the repository by ID
        Optional<EcomUser> optionalExistingUser = iEcomUserRepository.findUserById(userId);

        // Check if the user with the specified ID exists
        if (optionalExistingUser.isPresent()) {
            // Get the existing user
            EcomUser existingUser = optionalExistingUser.get();

            // Update fields only if they are present in the DTO
            if (ecomUserDTO.getFirstName() != null) {
                existingUser.setFirstName(ecomUserDTO.getFirstName());
            }

            if (ecomUserDTO.getLastName() != null) {
                existingUser.setLastName(ecomUserDTO.getLastName());
            }

            if (ecomUserDTO.getEmail() != null) {
                existingUser.setEmail(ecomUserDTO.getEmail());
            }

            if (ecomUserDTO.getShowPassword() != null) {
                existingUser.setShowPassword(ecomUserDTO.getShowPassword());
            }

            if (ecomUserDTO.getPassword() != null) {
                existingUser.setPassword(ecomUserDTO.getPassword());
            }

            if (ecomUserDTO.getRole() != null) {
                existingUser.setRole(ecomUserDTO.getRole());
            }

            // Save the updated user to the repository
            EcomUser updatedUser = iEcomUserRepository.save(existingUser);

            // Log successful user update with user's email
            logger.info("User updated successfully: {}", updatedUser.getEmail());

            // Return the updated user
            return updatedUser;
        }

        // Log a warning if the user with the specified ID is not found
        logger.warn("User not found with ID: {}", userId);

        // Return null as the user was not found
        return null;
    }

    @Override
    public Optional<EcomUser> findByEmail(String email) {
        return this.iEcomUserRepository.findByEmail(email);
    }

    @Override
    public List<EcomUser> listEcomUserByTokenId(Long tokenId) {

        List<EcomUser> ecomUsers = iEcomUserRepository.findUsersByTokenId(tokenId);

        return ecomUsers;
    }

    @Override
    public List<EcomUser> listEcomUser() {
        List<EcomUser> ecomUsers = iEcomUserRepository.findAll();
        return ecomUsers;
    }


    @Override
    public String extractUsername(String refreshToken) {
        // Extracting the username from the refresh token
        String username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            logger.info("Extracted username from refresh token: {}", username);
            return username;
        } else {
            logger.warn("Failed to extract username from refresh token");
            return null;
        }
    }

    @Override
    public boolean isTokenValid(String token, EcomUser user) {
        // Checking if the token is valid for the specified user
        if (tokenService.isTokenValid(token, user)) {
            logger.info("Token is valid for user: {}", user.getEmail());
            return true;
        } else {
            logger.warn("Token is not valid for user: {}", user.getEmail());
            return false;
        }
    }

    @Override
    public String generateToken(EcomUser user) {
        // Generating a new token for the specified user
        String newToken = jwtService.generateToken(user);

        if (newToken != null) {
            logger.info("Generated new token for user: {}", user.getEmail());
            return newToken;
        } else {
            logger.warn("Failed to generate new token for user: {}", user.getEmail());
            return null;
        }
    }
}
