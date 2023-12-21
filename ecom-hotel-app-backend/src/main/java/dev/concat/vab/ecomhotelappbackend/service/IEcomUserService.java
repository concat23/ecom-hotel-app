package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.exception.EmailExistException;
import dev.concat.vab.ecomhotelappbackend.exception.EmailNotFoundException;
import dev.concat.vab.ecomhotelappbackend.exception.UserNotFoundException;
import dev.concat.vab.ecomhotelappbackend.exception.UsernameExistException;
import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEcomUserService {
    EcomUser register(String firstName, String lastName, String username, String email) throws EmailExistException, UsernameExistException, UserNotFoundException;

    EcomUser addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonlocked, boolean isActive, MultipartFile profileImage) throws EmailExistException, UsernameExistException, UserNotFoundException, IOException;

    EcomUser updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonlocked, boolean isActive) throws EmailExistException, UsernameExistException, UserNotFoundException, IOException;

    List<EcomUser> getUsers();

    EcomUser findUserByUsername(String username);

    EcomUser findUserByEmail(String email);

    void deleteUser(String username) throws IOException;

    void resetPassword(String email) throws EmailNotFoundException;

    String saveToken(EcomUser loginUser);

    void updateAccessToken(String username, String accessToken);

}
