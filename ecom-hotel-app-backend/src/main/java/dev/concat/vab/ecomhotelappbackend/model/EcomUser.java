package dev.concat.vab.ecomhotelappbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Data
@Getter
@Setter
@Entity
@Table(name = "ecom_users")
public class EcomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String showPassword;
    private boolean isActive;
    private boolean isNotLocked;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String[] authorities;

    @OneToOne(mappedBy = "ecomUser", cascade = CascadeType.ALL)
    @JsonBackReference
    private EcomToken ecomToken;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();;
    @LastModifiedDate
    private LocalDateTime createdUpdate = LocalDateTime.now();
    @LastModifiedDate
    private LocalDateTime deleted;

    public EcomUser() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
