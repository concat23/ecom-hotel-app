package dev.concat.vab.ecomhotelappbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name="ecom_users")
public class EcomUser {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    @Getter
    private String firstName;
    private String lastName;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private Date lastLoginDate;
    private Date logInDateDisplay;
    private String role;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;
    private String accessToken;
    private String refreshToken;

    public EcomUser() {}

    public EcomUser(Long id, String firstName, String lastName, String username, String password, String email,
                Date lastLoginDate, Date logInDateDisplay, Date joinDate, String role, String[] authorities,
                boolean isActive, boolean isNotLocked, String accessToken,String refreshToken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastLoginDate = lastLoginDate;
        this.logInDateDisplay = logInDateDisplay;
        this.role = role;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.accessToken =  accessToken;
        this.refreshToken = refreshToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLogInDateDisplay() {
        return logInDateDisplay;
    }

    public void setLogInDateDisplay(Date logInDateDisplay) {
        this.logInDateDisplay = logInDateDisplay;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String ...authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean isNotLocked) {
        this.isNotLocked = isNotLocked;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
