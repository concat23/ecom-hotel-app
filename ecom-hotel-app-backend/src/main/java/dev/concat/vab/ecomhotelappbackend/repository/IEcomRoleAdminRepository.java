package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoleAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IEcomRoleAdminRepository extends JpaRepository<EcomRoleAdmin, Long> {
    EcomRoleAdmin findByName(String name);
}
