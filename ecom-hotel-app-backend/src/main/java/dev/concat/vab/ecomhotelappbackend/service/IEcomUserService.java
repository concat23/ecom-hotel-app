package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;

import java.util.List;

public interface IEcomUserService {
    EcomUser saveUser(EcomUser ecomUser);
    EcomRole saveRole(EcomRole ecomRole);
    void addRoleToUser(String username, String roleName);

    EcomUser getEcomUser(String username);
    List<EcomUser> getAllUsers();

}
