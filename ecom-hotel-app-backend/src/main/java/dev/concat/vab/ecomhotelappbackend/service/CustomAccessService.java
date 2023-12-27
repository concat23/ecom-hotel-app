package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomAccessService {
    private final IEcomUserRepository iEcomUserRepository;

    public boolean hasRole(Authentication authentication, String email) {
        // Lấy thông tin người dùng từ authentication
        Optional<EcomUser> userOptional = iEcomUserRepository.findByEmail(authentication.getName());

        // Kiểm tra quyền của người dùng dựa trên cơ sở dữ liệu
        return userOptional.isPresent() &&
                Arrays.stream(Role.values())
                        .anyMatch(role -> role.name().equals(email)); // Assuming role is an Enum with name() method
    }



}
