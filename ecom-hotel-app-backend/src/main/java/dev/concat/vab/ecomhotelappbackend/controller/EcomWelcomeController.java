package dev.concat.vab.ecomhotelappbackend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path= "/api/welcome")
public class EcomWelcomeController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Welcome to my API Ecom Hotel");
    }
}
