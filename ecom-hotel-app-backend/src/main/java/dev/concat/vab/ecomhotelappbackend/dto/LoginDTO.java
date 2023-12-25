package dev.concat.vab.ecomhotelappbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDTO {
    @ApiModelProperty(position = 1)
    private String usernameOrEmail;
    @ApiModelProperty(position = 2)
    private String password;
}
