package dev.concat.vab.ecomhotelappbackend.converter;

import dev.concat.vab.ecomhotelappbackend.dto.EcomUserDTO;
import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import org.springframework.stereotype.Component;

@Component
public class EcomUserConverter {
    public EcomUser convertToEntity(EcomUserDTO ecomUserDTO) {
        EcomUser ecomUser = new EcomUser();
        ecomUser.setFirstName(ecomUserDTO.getFirstName());
        ecomUser.setLastName(ecomUserDTO.getLastName());
        ecomUser.setEmail(ecomUserDTO.getEmail());
        ecomUser.setShowPassword(ecomUserDTO.getShowPassword());
        ecomUser.setActive(ecomUserDTO.isActive());
        ecomUser.setNotLocked(ecomUserDTO.isNotLocked());

        // Convert role string to Role enum
        if (ecomUserDTO.getRole() != null) {
            ecomUser.setRole(ecomUserDTO.getRole());
        }

        // You can map other fields here

        return ecomUser;
    }

    public EcomUser updateFromDTO(EcomUser existingUser, EcomUserDTO ecomUserDTO) {
        existingUser.setFirstName(ecomUserDTO.getFirstName());
        existingUser.setLastName(ecomUserDTO.getLastName());
        existingUser.setEmail(ecomUserDTO.getEmail());
        existingUser.setShowPassword(ecomUserDTO.getShowPassword());
        existingUser.setActive(ecomUserDTO.isActive());
        existingUser.setNotLocked(ecomUserDTO.isNotLocked());

        // Update role if provided in the DTO
        if (ecomUserDTO.getRole() != null) {
            existingUser.setRole(ecomUserDTO.getRole());
        }

        // Update other fields as needed

        return existingUser;
    }
}
