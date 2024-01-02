package dev.concat.vab.ecomhotelappbackend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "/api/welcome")
@PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
public class EcomWelcomeController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ','MANAGER_READ')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Welcome to my API Ecom Hotel");
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_CREATE','USER_CREATE','MANAGER_CREATE')")
    public ResponseEntity<String> postWelcome() {
        // Your logic for POST welcome
        return ResponseEntity.ok("Welcome! (POST)");
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_UPDATE','USER_UPDATE','MANAGER_UPDATE')")
    public ResponseEntity<String> putWelcome() {
        // Your logic for PUT welcome
        return ResponseEntity.ok("Welcome! (PUT)");
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<String> deleteWelcome() {
        // Your logic for DELETE welcome
        return ResponseEntity.ok("Welcome! (DELETE)");
    }

}
