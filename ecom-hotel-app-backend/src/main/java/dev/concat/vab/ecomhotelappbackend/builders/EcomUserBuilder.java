package dev.concat.vab.ecomhotelappbackend.builders;

import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EcomUserBuilder {
    private EcomUser ecomUser;

    public EcomUserBuilder() {
        this.ecomUser = new EcomUser();
    }

    public EcomUserBuilder withId(Long id) {
        ecomUser.setId(id);
        return this;
    }

    public EcomUserBuilder withFirstName(String firstName) {
        ecomUser.setFirstName(firstName);
        return this;
    }

    public EcomUserBuilder withLastName(String lastName) {
        ecomUser.setLastName(lastName);
        return this;
    }

    public EcomUserBuilder withEmail(String email) {
        ecomUser.setEmail(email);
        return this;
    }

    public EcomUserBuilder withPassword(String password) {
        ecomUser.setPassword(password);
        return this;
    }
    public EcomUserBuilder withShowPassword(String showPassword) {
        ecomUser.setShowPassword(showPassword);
        return this;
    }

    public EcomUserBuilder withRole(Role role) {
        ecomUser.setRole(role);
        return this;
    }

    public EcomUserBuilder withAuthorities(String[] authorities) {
        if (ecomUser.getRole() != null) {
            ecomUser.setAuthorities(ecomUser.getRole().getAuthorities());
        } else {
            ecomUser.setAuthorities(authorities);
        }
        return this;
    }


    // Add more with methods for other fields as needed

    public EcomUser build() {
        return ecomUser;
    }
}