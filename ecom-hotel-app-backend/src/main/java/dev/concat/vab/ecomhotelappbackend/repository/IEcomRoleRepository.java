package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEcomRoleRepository extends JpaRepository<EcomRole, Long> {
    @Query("SELECT r FROM EcomRole r WHERE r.name = :name")
    EcomRole findByName(String name);
}
