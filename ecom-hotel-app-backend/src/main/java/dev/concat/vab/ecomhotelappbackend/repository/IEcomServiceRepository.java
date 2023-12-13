package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEcomServiceRepository extends JpaRepository<EcomService, Long> {
}
