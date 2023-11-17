//package dev.concat.vab.ecomhotelappbackend.service.implementation;
//
//import dev.concat.vab.ecomhotelappbackend.model.EcomAccountAdmin;
//import dev.concat.vab.ecomhotelappbackend.model.EcomRoleAdmin;
//import dev.concat.vab.ecomhotelappbackend.repository.IEcomAccountAdminRepository;
//import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoleAdminRepository;
//import dev.concat.vab.ecomhotelappbackend.service.IEcomAccountServiceAdmin;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class EcomAccountServiceAdminImpl implements IEcomAccountServiceAdmin {
//
//
//    private final IEcomAccountAdminRepository iEcomAccountAdminRepository;
//    private final IEcomRoleAdminRepository iEcomRoleAdminRepository;
//    private final PasswordEncoder passwordEncoder;
//
//
//    @Override
//    public EcomAccountAdmin createAccountAdmin(EcomAccountAdmin accountAdmin) {
//        accountAdmin.setPassword(passwordEncoder.encode(accountAdmin.getPassword()));
//        EcomRoleAdmin role = this.iEcomRoleAdminRepository.findByName("ROLE_USER");
//        Set<EcomRoleAdmin> roles = new HashSet<>();
//        roles.add(role);
//        accountAdmin.setRoles(roles);
//
//        return this.iEcomAccountAdminRepository.save(accountAdmin);
//    }
//
//
//    @Override
//    public EcomAccountAdmin findByUsername(String username) {
//        return this.iEcomAccountAdminRepository.findByUsername(username);
//    }
//
//
//    @Override
//    public List<EcomAccountAdmin> getAccountsAdmin() {
//        return this.iEcomAccountAdminRepository.findAll();
//    }
//}
