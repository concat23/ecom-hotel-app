package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.exception.*;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.model.EcomUserPrincipal;
import dev.concat.vab.ecomhotelappbackend.provider.JWTTokenProvider;
import dev.concat.vab.ecomhotelappbackend.response.HttpResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static dev.concat.vab.ecomhotelappbackend.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping(path = "/api/auth")
public class EcomAuthController extends ExceptionHandling {

    private final AuthenticationManager authenticationManager;
    private final IEcomUserService iEcomUserService;
    private final UserDetailsService userDetailsService;
    private final JWTTokenProvider jWTTokenProvider;

    public EcomAuthController(AuthenticationManager authenticationManager, IEcomUserService iEcomUserService, JWTTokenProvider jWTTokenProvider,UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.iEcomUserService = iEcomUserService;
        this.jWTTokenProvider = jWTTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    //String firstName, String lastName, String username, String email, String role, boolean isNonlocked, boolean isActive, MultipartFile profileImage
    @PostMapping("/login")
    public ResponseEntity<EcomUser> login(@RequestBody EcomUser user) {
        authenticate(user.getUsername(), user.getPassword());
        EcomUser loginUser = iEcomUserService.findUserByUsername(user.getUsername());
        String tokenValue = iEcomUserService.saveToken(loginUser);
        log.info("Token: {}",tokenValue);
        this.iEcomUserService.updateAccessToken(loginUser.getUsername(),loginUser.getAccessToken());
        EcomUserPrincipal principal = new EcomUserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(principal);
        return ResponseEntity.ok().headers(jwtHeader).body(loginUser);
    }

    @PostMapping("/register")
    public ResponseEntity<EcomUser> register(@RequestBody EcomUser user) throws UsernameExistException, EmailExistException, UserNotFoundException {
        EcomUser newUser = iEcomUserService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/find/" + newUser.getUsername()).toUriString());
        return ResponseEntity.created(uri).body(newUser);
    }

    @PostMapping("/add")
    public ResponseEntity<EcomUser> addNewUser(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("role") String role,
                                           @RequestParam("isActive") String isActive,
                                           @RequestParam("isNonLocked") String isNonLocked,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UsernameExistException, EmailExistException, UserNotFoundException, IOException {
        EcomUser newUser = iEcomUserService.addNewUser(firstName, lastName, username, email, role, Boolean.parseBoolean(isNonLocked),
                Boolean.parseBoolean(isActive), profileImage);
        return ResponseEntity.ok().body(newUser);
    }

    @PostMapping("/update")
    public ResponseEntity<EcomUser> update(@RequestParam("currentUsername") String currentUsername,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam(value = "role", required = false) String role,
                                       @RequestParam("isActive") String isActive,
                                       @RequestParam("isNonLocked") String isNonLocked) throws UsernameExistException, EmailExistException, UserNotFoundException, IOException {
        EcomUser newUser = iEcomUserService.updateUser(currentUsername, firstName, lastName, username, email, role, Boolean.parseBoolean(isNonLocked),
                Boolean.parseBoolean(isActive));
        return ResponseEntity.ok().body(newUser);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<EcomUser> getUser(@PathVariable("username")String username) {
        EcomUser user = iEcomUserService.findUserByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/list")
    public ResponseEntity<List<EcomUser>> getAllUsers() {
        List<EcomUser> users = iEcomUserService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws EmailNotFoundException {
        iEcomUserService.resetPassword(email);
        return response(OK, "Password changed successfully");
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
        iEcomUserService.deleteUser(username);
        return response(OK, "User deleted successfully");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshTokens(@RequestHeader(name = "refreshToken") String refreshToken) {
        String userId = jWTTokenProvider.getSubject(refreshToken);

        if (jWTTokenProvider.isRefreshTokenValid(userId, refreshToken)) {
            EcomUserPrincipal userPrincipal = (EcomUserPrincipal) userDetailsService.loadUserByUsername(userId);
            String newAccessToken = jWTTokenProvider.generateJwtToken(userPrincipal,userPrincipal.getUser().getLastLoginDate());
            return ResponseEntity.ok(newAccessToken);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }
    }


    private HttpHeaders getJwtHeader(EcomUserPrincipal user){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWT_TOKEN_HEADER, jWTTokenProvider.generateJwtToken(user,user.getUser().getLastLoginDate()));
        return httpHeaders;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()), httpStatus);
    }

}
