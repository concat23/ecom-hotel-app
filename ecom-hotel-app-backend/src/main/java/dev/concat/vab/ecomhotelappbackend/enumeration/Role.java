package dev.concat.vab.ecomhotelappbackend.enumeration;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static dev.concat.vab.ecomhotelappbackend.constant.AuthorityConstant.*;

public enum Role {
    USER(USER_AUTHORITIES),
    HR(HR_AUTHORITIES),
    MANAGER(MANAGER_AUTHORITIES),
    ADMIN(ADMIN_AUTHORITIES),
    SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authorities;

    public static Role fromName(String name) {
        for (Role role : values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + name);
    }

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public String getRoleWithPrefix() {
        return "ROLE_" + this.name();
    }

    // Additional method to get the role name without the prefix
    public String getRoleName() {
        return this.name();
    }
}
