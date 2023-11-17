package dev.concat.vab.ecomhotelappbackend.dto;

import lombok.Data;

@Data
public class LoginAccountAdminDTO {
    private String usernameOrEmail;
    private String password;
}
