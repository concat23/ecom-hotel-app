package dev.concat.vab.ecomhotelappbackend.init;

import dev.concat.vab.ecomhotelappbackend.model.EcomRole;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoleRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ApplicationStartRunner implements CommandLineRunner {
    private final IEcomUserService iEcomUserService;

    @Override
    public void run(String... args) throws Exception {
//        this.iEcomUserService.saveRole(new EcomRole(1L,"ROLE_USER","RU999","ALL"));
//        this.iEcomUserService.saveRole(new EcomRole(2L,"ROLE_MANAGER","RM999","ALL"));
//        this.iEcomUserService.saveRole(new EcomRole(3L,"ROLE_ADMIN","RA999","ALL"));
//        this.iEcomUserService.saveRole(new EcomRole(4L,"ROLE_SUPER_ADMIN","RSA999","ALL"));
//
//        this.iEcomUserService.saveUser(new EcomUser(1L,"Bang Vo Anh","bang","abc@#123",null,null,new ArrayList<>()));
//        this.iEcomUserService.saveUser(new EcomUser(2L,"Ha Tran Khanh","ha","abc@#123",null,null,new ArrayList<>()));
//        this.iEcomUserService.saveUser(new EcomUser(3L,"May Tran Khanh","may","abc@#123",null,null,new ArrayList<>()));
//        this.iEcomUserService.saveUser(new EcomUser(4L,"Linh Tran Khanh","linh","abc@#123",null,null,new ArrayList<>()));
//
//        this.iEcomUserService.addRoleToUser("bang","ROLE_SUPER_ADMIN");
//        this.iEcomUserService.addRoleToUser("ha","ROLE_ADMIN");
//        this.iEcomUserService.addRoleToUser("may","ROLE_MANAGER");
//        this.iEcomUserService.addRoleToUser("linh","ROLE_USER");

    }
}
