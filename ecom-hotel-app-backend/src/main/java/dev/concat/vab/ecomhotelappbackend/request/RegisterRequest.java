package dev.concat.vab.ecomhotelappbackend.request;

import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "RegisterRequest", description = "Registration Request")
public class RegisterRequest {

    @ApiModelProperty(value = "User first name", example = "Bang", required = true)
    private String firstName;

    @ApiModelProperty(value = "User last name", example = "Vo Anh", required = true)
    private String lastName;

    @ApiModelProperty(value = "User email", example = "bang@gmail.com", required = true)
    private String email;

    @ApiModelProperty(value = "User password", example = "abc@#123", required = true)
    private String password;

    @ApiModelProperty(value = "User role", example = "ADMIN", required = true)
    private Role role;
    private RegisterRequest() {
        // private constructor to prevent direct instantiation
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public static class Builder {
        private final RegisterRequest request;

        private Builder() {
            this.request = new RegisterRequest();
        }

        public Builder firstName(String firstName) {
            request.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            request.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            request.email = email;
            return this;
        }

        public Builder password(String password) {
            request.password = password;
            return this;
        }

        public Builder role(Role role) {
            request.role = role;
            return this;
        }

        public RegisterRequest build() {
            // Perform any additional validation here if needed
            return request;
        }
    }
}
