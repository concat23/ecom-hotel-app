package dev.concat.vab.ecomhotelappbackend.request;

import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import lombok.Data;

@Data
public class UpdateRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String showPassword;
    private boolean isActive;
    private boolean isNotLocked;
    private Role role;
    // Add more fields as needed

    public static UpdateRegisterRequestBuilder builder() {
        return new UpdateRegisterRequestBuilder();
    }

    // Inner builder class
    public static class UpdateRegisterRequestBuilder {
        // Copy the fields from the original class
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String showPassword;
        private boolean isActive;
        private boolean isNotLocked;
        private Role role;

        // Setters for each field
        public UpdateRegisterRequestBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UpdateRegisterRequestBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UpdateRegisterRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UpdateRegisterRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UpdateRegisterRequestBuilder showPassword(String showPassword) {
            this.showPassword = showPassword;
            return this;
        }

        public UpdateRegisterRequestBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public UpdateRegisterRequestBuilder isNotLocked(boolean isNotLocked) {
            this.isNotLocked = isNotLocked;
            return this;
        }

        public UpdateRegisterRequestBuilder role(Role role) {
            this.role = role;
            return this;
        }

        // Build method
        public UpdateRegisterRequest build() {
            return new UpdateRegisterRequest(this);
        }
    }

    // Private constructor
    private UpdateRegisterRequest(UpdateRegisterRequestBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.showPassword = builder.showPassword;
        this.isActive = builder.isActive;
        this.isNotLocked = builder.isNotLocked;
        this.role = builder.role;
        // Set other fields as needed
    }
}
