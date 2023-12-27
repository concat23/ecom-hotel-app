package dev.concat.vab.ecomhotelappbackend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUsername(String jwt);

    String generateJwtToken(UserDetails userDetails);
    boolean isJwtTokenInvalid(String token, UserDetails userDetails);
}
