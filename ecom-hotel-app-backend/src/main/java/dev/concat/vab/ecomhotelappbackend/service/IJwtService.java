package dev.concat.vab.ecomhotelappbackend.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJwtService {
    String extractUsername(String jwt);

    String generateJwtToken(
                            UserDetails userDetails);
    String generateRefreshToken(
            UserDetails userDetails
    );
    boolean isJwtTokenInvalid(String token, UserDetails userDetails);
}
