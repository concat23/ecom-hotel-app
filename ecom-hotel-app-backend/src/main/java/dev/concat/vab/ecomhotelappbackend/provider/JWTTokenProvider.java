package dev.concat.vab.ecomhotelappbackend.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.model.EcomUserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static dev.concat.vab.ecomhotelappbackend.constant.SecurityConstant.*;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.*;
import static org.hibernate.tool.schema.ast.GeneratedSqlScriptParserTokenTypes.DELIMITER;


@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String getSecret() {
        return secret;
    }

    //    public String generateJwtToken(EcomUserPrincipal userPrincipal) {
//        String[] claims = getClaimsFromUser(userPrincipal);
//        return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(GET_ARRAYS_ADMINISTRATION)
//                .withIssuedAt(new Date()).withSubject(userPrincipal.getUser().getId().toString()).withArrayClaim(AUTHORITIES, claims)
//                .withExpiresAt(new Date(currentTimeMillis() + EXPIRATION_TIME))
//                .sign(HMAC512(secret.getBytes()));
//    }

//    public String generateJwtToken(EcomUserPrincipal userPrincipal) {
//        String[] claims = getClaimsFromUser(userPrincipal);
//        String accessToken = JWT.create()
//                .withIssuer(GET_ARRAYS_LLC)
//                .withAudience(GET_ARRAYS_ADMINISTRATION)
//                .withIssuedAt(new Date())
//                .withSubject(userPrincipal.getUser().getId().toString())
//                .withArrayClaim(AUTHORITIES, claims)
//                .withExpiresAt(new Date(currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
//                .sign(HMAC512(secret.getBytes()));
//
//        String refreshToken = generateRefreshToken(userPrincipal);
//
//
//        return accessToken + DELIMITER + refreshToken;
//    }
//
//    public String generateRefreshToken(EcomUserPrincipal userPrincipal) {
//        return JWT.create().withIssuer(GET_ARRAYS_LLC)
//                .withAudience(GET_ARRAYS_ADMINISTRATION)
//                .withSubject(userPrincipal.getUser().getId().toString())
//                .withExpiresAt(new Date(currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
//                .sign(HMAC512(secret.getBytes()));
//    }

    public String generateJwtToken(UserDetails userDetails, Date expirationDate) {
        String[] claims = getClaimsFromUser((EcomUserPrincipal) userDetails);
        String accessToken = JWT.create()
                .withIssuer(GET_ARRAYS_LLC)
                .withAudience(GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(userDetails.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(expirationDate)
                .sign(HMAC512(secret.getBytes()));

        String refreshToken = generateRefreshToken(userDetails);

        return accessToken + DELIMITER + refreshToken;
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return JWT.create().withIssuer(GET_ARRAYS_LLC)
                .withAudience(GET_ARRAYS_ADMINISTRATION)
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));
    }


    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new
                UsernamePasswordAuthenticationToken(username, null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public boolean isTokenValid(String userId, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(userId) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private Boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public boolean isRefreshTokenValid(String userId, String refreshToken) {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm)
                    .withIssuer(GET_ARRAYS_LLC)
                    .build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }

        // Validate the refresh token
        return StringUtils.isNotEmpty(userId) && !isTokenExpired(verifier, refreshToken);
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    public String[] getClaimsFromUser(EcomUserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }

    public String generateJwtToken(EcomUser loginUser) {
        String[] claims = getClaimsFromUser(new EcomUserPrincipal(loginUser));
        String accessToken = JWT.create()
                .withIssuer(GET_ARRAYS_LLC)
                .withAudience(GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(loginUser.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));

        String refreshToken = generateRefreshToken(new EcomUserPrincipal(loginUser));

        // Limit the length of the combined token string
        String combinedToken = accessToken + DELIMITER + refreshToken;
        int maxLength = 255; // Set your desired maximum length
        if (combinedToken.length() > maxLength) {
            combinedToken = combinedToken.substring(0, maxLength);
        }

        return combinedToken;
    }


}
