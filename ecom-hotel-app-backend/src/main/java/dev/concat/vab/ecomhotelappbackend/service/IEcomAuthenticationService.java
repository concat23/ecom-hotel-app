package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.dto.EcomUserDTO;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.request.AuthRequest;
import dev.concat.vab.ecomhotelappbackend.request.RegisterRequest;
import dev.concat.vab.ecomhotelappbackend.response.AuthResponse;

import java.util.List;

public interface IEcomAuthenticationService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse authenticate(AuthRequest authRequest);

    List<EcomUser> listEcomUserByTokenId(Long tokenId);
    EcomUser updateUser(Long userId, EcomUserDTO ecomUserDTO);
}
