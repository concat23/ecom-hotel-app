package dev.concat.vab.ecomhotelappbackend.repository;


import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEcomUserRepository extends JpaRepository<EcomUser,Long> {
    EcomUser findByUsername(String username);

    EcomUser findByEmail(String email);
}
