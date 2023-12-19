package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.service.IEcomUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api")
public class EcomUserController {

    private final IEcomUserService iEcomUserService;

    @GetMapping(path = "/ecom-users")
    public ResponseEntity<List<EcomUser>> getUsers(){
        return ResponseEntity.ok().body(this.iEcomUserService.getAllUsers());
    }

    @PostMapping(path = "/ecom-user/create")
    public ResponseEntity<EcomUser> saveEcomUser(@RequestBody EcomUser ecomUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/ecom-user/create").toString());
        return ResponseEntity.created(uri).body(this.iEcomUserService.saveUser(ecomUser));
    }

    @PostMapping(path = "/ecom-role/create")
    public ResponseEntity<EcomRole> saveEcomRole(@RequestBody EcomRole ecomRole){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/ecom-role/create").toString());
        return ResponseEntity.created(uri).body(this.iEcomUserService.saveRole(ecomRole));
    }

    @PostMapping(path="/ecom-role/add-role-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody EcomRoleToUserForm form){
        this.iEcomUserService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}

@Data
class EcomRoleToUserForm{
    private String username;
    private String roleName;
}
