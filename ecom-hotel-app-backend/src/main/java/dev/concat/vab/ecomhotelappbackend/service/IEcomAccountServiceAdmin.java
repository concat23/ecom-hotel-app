package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomAccountAdmin;

import java.util.List;

public interface IEcomAccountServiceAdmin {
    EcomAccountAdmin createAccountAdmin(EcomAccountAdmin accountAdmin);
    EcomAccountAdmin findByUsername(String username);
    List<EcomAccountAdmin> getAccountsAdmin();
}
