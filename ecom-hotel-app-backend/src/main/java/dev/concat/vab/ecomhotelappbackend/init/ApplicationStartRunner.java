//package dev.concat.vab.ecomhotelappbackend.init;
//
//import dev.concat.vab.ecomhotelappbackend.model.EcomRoleAdmin;
//import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoleAdminRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//@RequiredArgsConstructor
//public class ApplicationStartRunner implements CommandLineRunner
//{
//    private final IEcomRoleAdminRepository iEcomRoleAdminRepository;
//    @Override
//    public void run(String... args) throws Exception {
//        EcomRoleAdmin roleUser = new EcomRoleAdmin(1L,"123","ROLE_USER");
//        EcomRoleAdmin roleAdmin = new EcomRoleAdmin(2L,"456","ROLE_ADMIN");
//        EcomRoleAdmin roleFullPermission = new EcomRoleAdmin(3L,"789","ROLE_ALL_PERMISSION");
//        iEcomRoleAdminRepository.saveAll(Arrays.asList(roleUser,roleAdmin,roleFullPermission));
//    }
//}
