package dev.concat.vab.ecomhotelappbackend.dto;

import lombok.Data;

@Data
public class RegisterAccountAdminDTO {
    private String name;
    private String username;
    private String email;
    private String password;
}
