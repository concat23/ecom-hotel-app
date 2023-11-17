//package dev.concat.vab.ecomhotelappbackend.controller;
//
//import dev.concat.vab.ecomhotelappbackend.model.EcomAccountAdmin;
//import dev.concat.vab.ecomhotelappbackend.service.IEcomAccountServiceAdmin;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path = "/api/accounts")
//@CrossOrigin(origins = "http://localhost:5173")
//public class EcomAccountAdminController {
//    private final IEcomAccountServiceAdmin iEcomAccountServiceAdmin;
//
//
//    @PostMapping(path = "/user/add-new-account")
//    public ResponseEntity<EcomAccountAdmin> addAccountAdmin(@RequestBody EcomAccountAdmin account){
//        EcomAccountAdmin newAccount = this.iEcomAccountServiceAdmin.createAccountAdmin(account);
//        return ResponseEntity.created(getLocation(newAccount.getId().intValue()))
//                .body(this.iEcomAccountServiceAdmin.createAccountAdmin(account));
//    }
//
//    @GetMapping(path = "/all-user-account")
//    public ResponseEntity<List<EcomAccountAdmin>> getAccounts(){
//        return ResponseEntity.ok(this.iEcomAccountServiceAdmin.getAccountsAdmin());
//    }
//
//    protected static URI getLocation(Integer id){
//        return ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(id).toUri();
//    }
//}
