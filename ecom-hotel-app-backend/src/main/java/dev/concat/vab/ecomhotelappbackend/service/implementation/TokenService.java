package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.model.EcomToken;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomTokenRepository;
import dev.concat.vab.ecomhotelappbackend.service.AbstractTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TokenService extends AbstractTokenService {

    public TokenService(IEcomTokenRepository iEcomTokenRepository) {
        super(iEcomTokenRepository);
    }

    @Override
    public EcomToken getTokenById(Long tokenId) {
        return super.getTokenById(tokenId);
    }

    @Override
    public void deleteToken(Long tokenId) {
        super.deleteToken(tokenId);
    }

    @Override
    public EcomToken saveToken(EcomToken ecomToken, EcomUser ecomUser) {
        // Provide implementation for saving token
        ecomToken.setEcomUser(ecomUser);
        return this.iEcomTokenRepository.save(ecomToken);
    }
    @Override
    public boolean isTokenValid(String token, EcomUser user) {
        // Checking if the token is valid for the specified user
        if (iEcomTokenRepository.isTokenValid(token, user)) {
            log.info("Token is valid for user: {}", user.getEmail());
            return true;
        } else {
            log.warn("Token is not valid for user: {}", user.getEmail());
            return false;
        }
    }

    @Override
    public List<EcomToken> findAllValidEcomTokenByUser(Long userId) {
        // Delegate to the repository method
        return iEcomTokenRepository.findAllValidEcomTokenByUser(userId);
    }

    @Override
    public void saveAllTokens(List<EcomToken> tokens) {
        iEcomTokenRepository.saveAll(tokens);
    }
}
