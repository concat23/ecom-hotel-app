package dev.concat.vab.ecomhotelappbackend.dto;

import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EcomUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String showPassword;
    private boolean isActive;
    private boolean isNotLocked;
    private Role role;


}
