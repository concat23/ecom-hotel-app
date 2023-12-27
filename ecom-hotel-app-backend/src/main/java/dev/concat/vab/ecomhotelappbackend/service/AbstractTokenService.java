package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomToken;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomTokenRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


public abstract class AbstractTokenService {

    protected final IEcomTokenRepository iEcomTokenRepository;
    public AbstractTokenService(IEcomTokenRepository iEcomTokenRepository) {
        this.iEcomTokenRepository = iEcomTokenRepository;
    }
    public abstract EcomToken saveToken(EcomToken ecomToken, EcomUser ecomUser);

    public EcomToken getTokenById(Long tokenId) {
        Optional<EcomToken> optionalEcomToken = this.iEcomTokenRepository.findById(tokenId);
        return optionalEcomToken.orElse(null);
    }

    public void deleteToken(Long tokenId) {
        try {
            this.iEcomTokenRepository.deleteById(tokenId);
        } catch (Exception e) {
            // Handle or log the exception
            throw new TokenNotFoundException("Token not found with ID: " + tokenId);
        }
    }

    public static class TokenNotFoundException extends RuntimeException {
        public TokenNotFoundException(String message) {
            super(message);
        }
    }

    public abstract boolean isTokenValid(String token, EcomUser user);

    public abstract List<EcomToken> findAllValidEcomTokenByUser(Long userId);

    public abstract void saveAllTokens(List<EcomToken> tokens);
}
