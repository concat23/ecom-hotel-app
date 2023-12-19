package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoleRepository;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomUserRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EcomUserServiceImpl implements IEcomUserService, UserDetailsService {

    private final IEcomUserRepository iEcomUserRepository;
    private final IEcomRoleRepository iEcomRoleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public EcomUser saveUser(EcomUser ecomUser) {
        log.info("Saving new user to the database, with username: {}", ecomUser.getUsername());
        ecomUser.setPassword(passwordEncoder.encode(ecomUser.getPassword()));
        return this.iEcomUserRepository.save(ecomUser);
    }

    @Override
    public EcomRole saveRole(EcomRole ecomRole) {
        log.info("Saving new role to the database, with name: {}", ecomRole.getName());
        return this.iEcomRoleRepository.save(ecomRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        EcomUser ecomUser = this.iEcomUserRepository.findByUsername(username);

        if (ecomUser != null) {
            EcomRole ecomRole = this.iEcomRoleRepository.findByName(roleName);

            if (ecomRole != null && !ecomUser.getEcomRoles().contains(ecomRole)) {
                ecomUser.getEcomRoles().add(ecomRole);
                this.iEcomUserRepository.save(ecomUser);
                log.info("Role '{}' added to user '{}'", roleName, username);
            } else {
                log.warn("Role '{}' not added to user '{}' because it already exists or user/role not found", roleName, username);
            }
        } else {
            log.warn("User '{}' not found", username);
        }
    }


    @Override
    public EcomUser getEcomUser(String username) {
        log.info("Fetching user {}", username);
        return this.iEcomUserRepository.findByUsername(username);
    }


    @Override
    public List<EcomUser> getAllUsers() {
        log.info("Fetching all users");
        List<EcomUser> users = this.iEcomUserRepository.findAll();
        log.info("Retrieved {} users from the repository", users.size());

        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EcomUser ecomUser = this.iEcomUserRepository.findByUsername(username);
        if(null == ecomUser){
            log.error("Ecom user not found in the database");
            throw new UsernameNotFoundException("Ecom user not found in the database");
        }else{
            log.info("Ecom user found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        ecomUser.getEcomRoles().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(ecomUser.getUsername(),ecomUser.getPassword(),authorities);
    }
}
