package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomToken;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEcomTokenRepository extends JpaRepository<EcomToken,Long> {
    EcomToken getTokenById(Long tokenId);
    void deleteToken(Long tokenId);

    @Query("""
    SELECT t FROM EcomToken t
    INNER JOIN t.ecomUser u
    WHERE u.id = :id AND (t.expired= false OR t.revoked = false)
""")
    List<EcomToken> findAllValidEcomTokenByUser(@Param("id") Long id);

    @Query("SELECT et FROM EcomToken et JOIN et.ecomUser eu WHERE et.accessToken = :token")
    Optional<EcomToken> findByEcomToken(@Param("token") String token);

    default boolean isTokenValid(String token, EcomUser user) {
        List<EcomToken> validTokens = findAllValidEcomTokenByUser(user.getId());
        return validTokens.stream().anyMatch(t -> t.getAccessToken().equals(token));
    }

}
