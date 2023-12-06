package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoleAdmin;

import java.util.Optional;

public interface IEcomRoleServiceAdmin {
    EcomRoleAdmin createEcomRoleAdmin(EcomRoleAdmin role);
    Optional<EcomRoleAdmin> findByName(String name);
}
