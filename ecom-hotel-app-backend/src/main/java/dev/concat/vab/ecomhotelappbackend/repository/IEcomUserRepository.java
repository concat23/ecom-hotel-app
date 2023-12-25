package dev.concat.vab.ecomhotelappbackend.repository;


import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEcomUserRepository extends JpaRepository<EcomUser,Long> {
    @Query("SELECT u FROM EcomUser u WHERE u.username = :username")
    EcomUser findByUsername(@Param("username") String username);

    @Query("SELECT u FROM EcomUser u WHERE u.email = :email")
    EcomUser findByEmail(@Param("email") String email);

    boolean existsByUsername(String username);
}
