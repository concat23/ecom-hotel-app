package dev.concat.vab.ecomhotelappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@JsonPropertyOrder({"usernameOrEmail", "password"})
public class LoginDTO {

    @ApiParam(name = "usernameOrEmail")
    @JsonProperty("usernameOrEmail")
    private String usernameOrEmail;

    @ApiParam(name = "password")
    @JsonProperty("password")
    private String password;
}
