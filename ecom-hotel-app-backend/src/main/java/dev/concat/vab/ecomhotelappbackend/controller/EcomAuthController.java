package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.annotations.TokenId;
import dev.concat.vab.ecomhotelappbackend.dto.EcomUserDTO;
import dev.concat.vab.ecomhotelappbackend.dto.LoginDTO;
import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import dev.concat.vab.ecomhotelappbackend.exception.*;
import dev.concat.vab.ecomhotelappbackend.model.EcomToken;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.model.EcomUserPrincipal;
import dev.concat.vab.ecomhotelappbackend.request.AuthRequest;
import dev.concat.vab.ecomhotelappbackend.request.RegisterRequest;
import dev.concat.vab.ecomhotelappbackend.response.AuthResponse;
import dev.concat.vab.ecomhotelappbackend.response.HttpResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomAuthenticationService;
import dev.concat.vab.ecomhotelappbackend.service.implementation.TokenService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static dev.concat.vab.ecomhotelappbackend.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path= "/api/auth")
@Api(value = "Ecom Authentication API", tags = "Authentication")
public class EcomAuthController extends ExceptionHandling {

    private final IEcomAuthenticationService iEcomAuthenticationService;
    private final TokenService tokenService;
    @PostMapping(path = "/register")
    @ApiOperation(value = "Register a new user", notes = "Registers a new user with the provided details.")
// @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER') and hasAnyAuthority('ADMIN_CREATE','USER_CREATE','MANAGER_CREATE')")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        logCurrentUserRoles();

        AuthResponse response = iEcomAuthenticationService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    private void logCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities() != null) {
            authentication.getAuthorities().forEach(authority -> {
                if (authority instanceof GrantedAuthority) {
                    String role = ((GrantedAuthority) authority).getAuthority();
                    log.info("Current user has role: {}", role);
                }
            });
        }
    }



    @PostMapping(path = "/authenticate")
    @ApiOperation(value = "Authenticate a user", notes = "Authenticates a user with the provided credentials.")
//    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ','MANAGER_READ')")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Log the roles of the current user
        if (authentication != null && authentication.getAuthorities() != null) {
            authentication.getAuthorities().forEach(authority ->
                    log.info("Current user has role: {}", authority.getAuthority())
            );
        }
        return ResponseEntity.ok(this.iEcomAuthenticationService.authenticate(authRequest));
    }



    @PutMapping("/{userId}")
//    @ApiOperation(value = "Update user information", notes = "Updates the information of a user with the specified ID.")
    public ResponseEntity<EcomUser> updateUser(@PathVariable(value = "userId") Long userId, @RequestBody EcomUserDTO ecomUserDTO) {
        ecomUserDTO.setRole(ecomUserDTO.getRole());
        EcomUser updatedUser = iEcomAuthenticationService.updateUser(userId, ecomUserDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/list")
    public ResponseEntity<List<EcomUser>> listEcomUserByTokenId(@TokenId Long tokenId) {
        List<EcomUser> ecomUsers = iEcomAuthenticationService.listEcomUserByTokenId(tokenId);
        return ResponseEntity.ok(ecomUsers);
    }

    @GetMapping("/all-user")
    @RolesAllowed("ADMIN")
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER') and hasAnyAuthority('ADMIN_READ','USER_READ','MANAGER_READ')")
    public ResponseEntity<List<EcomUser>> listEcomUser() {
        List<EcomUser> ecomUsers = iEcomAuthenticationService.listEcomUser();
        return ResponseEntity.ok(ecomUsers);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException, UserNotFoundException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        refreshToken = authHeader.substring(7);
        userEmail = iEcomAuthenticationService.extractUsername(refreshToken);

        if (userEmail != null) {
            EcomUser user = iEcomAuthenticationService.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            if (iEcomAuthenticationService.isTokenValid(refreshToken, user)) {
                String accessToken = iEcomAuthenticationService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                AuthResponse authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                return ResponseEntity.ok(authResponse);
            }
        }

        return ResponseEntity.badRequest().build();
    }

        private void saveUserToken(EcomUser ecomUser, String jwtToken) {
            EcomToken ecomToken = EcomToken.builder()
                    .ecomUser(ecomUser)
                    .accessToken(jwtToken)
                    .expired(false)
                    .revoked(false)
                    .build();

            tokenService.saveToken(ecomToken,ecomUser);
        }

    private void revokeAllUserTokens(EcomUser ecomUser) {
        List<EcomToken> validUserTokens = tokenService.findAllValidEcomTokenByUser(ecomUser.getId());
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAllTokens(validUserTokens);
    }

    private void logRoles(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities() != null) {
            authentication.getAuthorities().forEach(authority ->
                    log.info("Current user has role: {}", authority.getAuthority())
            );
        }
    }

}
