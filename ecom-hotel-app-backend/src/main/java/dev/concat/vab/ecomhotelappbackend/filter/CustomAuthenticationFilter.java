package dev.concat.vab.ecomhotelappbackend.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {



    private final  IEcomUserRepository iEcomUserRepository;

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(IEcomUserRepository iEcomUserRepository, AuthenticationManager authenticationManager){
        this.iEcomUserRepository = iEcomUserRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is: {}", username);
        log.info("Password is: {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles",user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30*60*1000))
                .withIssuer(request.getRequestURI().toString())
                .sign(algorithm);

//        response.setHeader("access_token",access_token);
//        response.setHeader("refresh_token",refresh_token);
        Map<String,String> tokens =  new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);

        EcomUser ecomUser = iEcomUserRepository.findByUsername(user.getUsername());
        if(ecomUser == null){
            ecomUser = new EcomUser();
        }
        ecomUser.setAccessToken(access_token);
        ecomUser.setRefreshToken(refresh_token);
        iEcomUserRepository.save(ecomUser);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);

    }
}
