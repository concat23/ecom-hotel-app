package dev.concat.vab.ecomhotelappbackend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AuthRequest", description = "Authentication Request")
public class AuthRequest {

    @ApiModelProperty(value = "User email", example = "bang@gmail.com", required = true)
    private String email;

    @ApiModelProperty(value = "User password", example = "abc@#123", required = true)
    private String password;
}