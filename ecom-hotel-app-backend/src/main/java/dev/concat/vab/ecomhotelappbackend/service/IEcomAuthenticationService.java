package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.dto.EcomUserDTO;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.request.AuthRequest;
import dev.concat.vab.ecomhotelappbackend.request.RegisterRequest;
import dev.concat.vab.ecomhotelappbackend.response.AuthResponse;

import java.util.List;
import java.util.Optional;

public interface IEcomAuthenticationService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse authenticate(AuthRequest authRequest);

    List<EcomUser> listEcomUserByTokenId(Long tokenId);
    List<EcomUser> listEcomUser();
    EcomUser updateUser(Long userId, EcomUserDTO ecomUserDTO);


    Optional<EcomUser> findByEmail(String email);
    String extractUsername(String refreshToken);
    boolean isTokenValid(String token, EcomUser user);
    String generateToken(EcomUser user);
}
