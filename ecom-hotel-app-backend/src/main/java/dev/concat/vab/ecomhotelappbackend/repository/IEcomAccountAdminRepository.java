package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomAccountAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IEcomAccountAdminRepository extends JpaRepository<EcomAccountAdmin,Long> {
    EcomAccountAdmin findByUsername(String username);
    EcomAccountAdmin findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    EcomAccountAdmin findByEmail(String email);
}
