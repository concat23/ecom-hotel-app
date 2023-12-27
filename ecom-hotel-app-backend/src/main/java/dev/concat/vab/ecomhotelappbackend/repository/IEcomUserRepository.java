package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IEcomUserRepository extends JpaRepository<EcomUser,Long> {
    Optional<EcomUser> findByEmail(Object email);
    Optional<EcomUser> findUserById(Long id);

    boolean existsByEmail(String email);

    @Query("SELECT ecomUser FROM EcomUser ecomUser JOIN ecomUser.tokens token WHERE token.id = :tokenId")
    List<EcomUser> findUsersByTokenId(@Param("tokenId") Long tokenId);


}
