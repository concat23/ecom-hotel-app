package dev.concat.vab.ecomhotelappbackend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.service.IEcomUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api")
@Slf4j
public class EcomUserController {

    private final IEcomUserService iEcomUserService;

    @GetMapping(path = "/ecom-users")
    public ResponseEntity<List<EcomUser>> getUsers(){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/ecom-users").toString());
        return ResponseEntity.ok().body(this.iEcomUserService.getAllUsers());
    }

    @PostMapping(path = "/ecom-user/create")
    public ResponseEntity<EcomUser> saveEcomUser(@RequestBody EcomUser ecomUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/ecom-user/create").toString());
        return ResponseEntity.created(uri).body(this.iEcomUserService.saveUser(ecomUser));
    }

    @PostMapping(path = "/ecom-role/create")
    public ResponseEntity<EcomRole> saveEcomRole(@RequestBody EcomRole ecomRole){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/ecom-role/create").toString());
        return ResponseEntity.created(uri).body(this.iEcomUserService.saveRole(ecomRole));
    }

    @PostMapping(path="/ecom-role/add-role-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody EcomRoleToUserForm form){
        this.iEcomUserService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();

                EcomUser ecomUser = this.iEcomUserService.getEcomUser(username);
                String access_token = JWT.create()
                        .withSubject(ecomUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", ecomUser.getEcomRoles().stream()
                                .map(EcomRole::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> tokens =  new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);

                if(ecomUser == null){
                    ecomUser = new EcomUser();
                }
                ecomUser.setAccessToken(access_token);
                ecomUser.setRefreshToken(refresh_token);
                this.iEcomUserService.saveUser(ecomUser);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);


            }catch(Exception exc){
                log.error("Error logging in: {}",exc.getMessage());
                response.setHeader("error",exc.getMessage());
                response.setStatus(FORBIDDEN.value());
//                  response.sendError(FORBIDDEN.value());
                Map<String,String> errors =  new HashMap<>();
                errors.put("error_message",exc.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),errors);
            }
        }else{
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class EcomRoleToUserForm{
    private String username;
    private String roleName;
}
