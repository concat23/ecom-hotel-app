package dev.concat.vab.ecomhotelappbackend.service.implementation;

import com.auth0.jwt.JWT;
import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import dev.concat.vab.ecomhotelappbackend.exception.*;
import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.model.EcomUserPrincipal;
import dev.concat.vab.ecomhotelappbackend.provider.JWTTokenProvider;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoleRepository;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomUserRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomUserService;
import dev.concat.vab.ecomhotelappbackend.service.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.tool.schema.ast.GeneratedSqlScriptParserTokenTypes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static dev.concat.vab.ecomhotelappbackend.constant.EcomUserImplConstant.*;
import static dev.concat.vab.ecomhotelappbackend.constant.SecurityConstant.*;
import static dev.concat.vab.ecomhotelappbackend.enumeration.Role.ROLE_ADMIN;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.hibernate.boot.model.source.spi.AttributePath.DELIMITER;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EcomUserServiceImpl implements IEcomUserService, UserDetailsService {

    private final IEcomUserRepository iEcomUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final JWTTokenProvider jwtTokenProvider;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EcomUser ecomUser = this.iEcomUserRepository.findByUsername(username);
        if (ecomUser == null) {
            log.error(NO_USER_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_BY_USERNAME + username);
        } else {
            validateLoginAttempt(ecomUser);
            ecomUser.setLogInDateDisplay(ecomUser.getLastLoginDate());
            ecomUser.setLastLoginDate(new Date());
            this.iEcomUserRepository.save(ecomUser);
            EcomUserPrincipal ecomUserPrincipal = new EcomUserPrincipal(ecomUser);
            log.info(USER_FROM_USER_DETAILS_SERVICE + username);
            return ecomUserPrincipal;
        }

    }

    @Override
    public EcomUser register(String firstName, String lastName, String username, String email)
            throws EmailExistException, UsernameExistException, UserNotFoundException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        EcomUser user = new EcomUser();
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setAuthorities(ROLE_ADMIN.getAuthorities());
        user.setRole(ROLE_ADMIN.name());
        this.iEcomUserRepository.save(user);
        log.info(password);
        return user;
    }

    @Override
    public EcomUser addNewUser(String firstName, String lastName, String username, String email, String role,
                           boolean isNonlocked, boolean isActive, MultipartFile profileImage)
            throws EmailExistException, UsernameExistException, UserNotFoundException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        EcomUser user = new EcomUser();
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setActive(isActive);
        user.setNotLocked(isNonlocked);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        this.iEcomUserRepository.save(user);
        log.info(password);
        return user;
    }

    @Override
    public EcomUser updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
                           String newEmail, String role, boolean isNonlocked, boolean isActive)
            throws EmailExistException, UsernameExistException, UserNotFoundException {
        EcomUser currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
        if(currentUser != null){
            currentUser.setFirstName(newFirstName);
            currentUser.setLastName(newLastName);
            currentUser.setUsername(newUsername);
            currentUser.setEmail(newEmail);
            currentUser.setActive(isActive);
            currentUser.setNotLocked(isNonlocked);
            currentUser.setRole(getRoleEnumName(role).name());
            currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
            this.iEcomUserRepository.save(currentUser);
        }

        return currentUser;
    }

    @Override
    public List<EcomUser> getUsers() {
        return this.iEcomUserRepository.findAll();
    }

    @Override
    public EcomUser findUserByUsername(String username) {
        return this.iEcomUserRepository.findByUsername(username);
    }

    @Override
    public EcomUser findUserByEmail(String email) {
        return this.iEcomUserRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(String username) throws IOException {
        EcomUser user = this.iEcomUserRepository.findByUsername(username);
        this.iEcomUserRepository.deleteById(user.getId());
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException {
        EcomUser user =this.iEcomUserRepository.findByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(NO_USER_BY_EMAIL + email);
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        log.info(password);
        this.iEcomUserRepository.save(user);
    }

    @Override
    public String saveToken(EcomUser loginUser) {
        // Kiểm tra sự tồn tại của người dùng
        if (!iEcomUserRepository.existsByUsername(loginUser.getUsername())) {
                throw new ResourceNotFoundException("User not exist !");
        }

        String[] claims = jwtTokenProvider.getClaimsFromUser(new EcomUserPrincipal(loginUser));

        String accessToken = JWT.create()
                .withIssuer(GET_ARRAYS_LLC)
                .withAudience(GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(loginUser.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(jwtTokenProvider.getSecret().getBytes()));


        String refreshToken = jwtTokenProvider.generateRefreshToken(new EcomUserPrincipal(loginUser));
        String combinedToken = accessToken + GeneratedSqlScriptParserTokenTypes.DELIMITER + refreshToken;

        // Giới hạn độ dài của combinedToken
        int maxLength = 255;
        if (combinedToken.length() > maxLength) {
            combinedToken = combinedToken.substring(0, maxLength);
        }
        // Lưu trữ token vào cơ sở dữ liệu
        this.updateAccessToken(loginUser.getUsername(), accessToken);

        return combinedToken;
    }

    @Override
    public void updateAccessToken(String username, String accessToken) {
        if (accessToken != null && username != null) {
            if (iEcomUserRepository.existsByUsername(username)) {
                EcomUser user = iEcomUserRepository.findByUsername(username);

                // Giới hạn độ dài của access_token
                int maxAccessTokenLength = 255; // Đặt chiều dài tối đa mong muốn
                if (accessToken.length() > maxAccessTokenLength) {
                    accessToken = accessToken.substring(0, maxAccessTokenLength);
                }

                user.setAccessToken(accessToken);
                // Cập nhật các trường khác nếu cần
                iEcomUserRepository.save(user);
            } else {
                throw new ResourceNotFoundException("User not found with username: " + username);
            }
        } else {
            throw new IllegalArgumentException("accessToken and username must not be null");
        }
    }




    private void validateLoginAttempt(EcomUser user) {
        if (user.isNotLocked()) {
            if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private EcomUser validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UserNotFoundException, UsernameExistException, EmailExistException{
        EcomUser userByNewUsername = findUserByUsername(newUsername);
        EcomUser userByNewEmail = findUserByEmail(newEmail);
        if (StringUtils.isNotBlank(currentUsername)) {
            EcomUser currentUser = findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(USER_NOT_FOUND + currentUsername);
            }
            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_EXISTS);
            }
            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_EXISTS);
            }
            return currentUser;
        } else {
            if (userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_EXISTS);
            }
            if (userByNewEmail != null) {
                throw new EmailExistException(EMAIL_EXISTS);
            }
            return null;
        }
    }
}
