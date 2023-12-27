package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.model.EcomToken;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomTokenRepository;
import dev.concat.vab.ecomhotelappbackend.service.AbstractTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
}
