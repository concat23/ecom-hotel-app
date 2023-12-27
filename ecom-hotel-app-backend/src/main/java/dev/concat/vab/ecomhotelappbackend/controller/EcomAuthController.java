package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.annotations.TokenId;
import dev.concat.vab.ecomhotelappbackend.dto.EcomUserDTO;
import dev.concat.vab.ecomhotelappbackend.dto.LoginDTO;
import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import dev.concat.vab.ecomhotelappbackend.exception.*;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.model.EcomUserPrincipal;
import dev.concat.vab.ecomhotelappbackend.request.AuthRequest;
import dev.concat.vab.ecomhotelappbackend.request.RegisterRequest;
import dev.concat.vab.ecomhotelappbackend.response.AuthResponse;
import dev.concat.vab.ecomhotelappbackend.response.HttpResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomAuthenticationService;
import io.swagger.annotations.*;
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
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path= "/api/auth")
@Api(value = "Ecom Authentication API", tags = "Authentication")
public class EcomAuthController extends ExceptionHandling {

    private final IEcomAuthenticationService iEcomAuthenticationService;
    @PostMapping(path = "/register")
    @ApiOperation(value = "Register a new user", notes = "Registers a new user with the provided details.")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(this.iEcomAuthenticationService.register(registerRequest));
    }


    @PostMapping(path = "/authenticate")
    @ApiOperation(value = "Authenticate a user", notes = "Authenticates a user with the provided credentials.")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(this.iEcomAuthenticationService.authenticate(authRequest));
    }



    @PutMapping("/{userId}")
    @ApiOperation(value = "Update user information", notes = "Updates the information of a user with the specified ID.")
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


}
