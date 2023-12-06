package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoleAdmin;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoleAdminRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomRoleServiceAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EcomRoleServiceAdminImpl implements IEcomRoleServiceAdmin {

    private final IEcomRoleAdminRepository iEcomRoleAdminRepository;
//
//    @Override
//    public EcomRoleEntity createEcomRole(EcomRoleEntity role) {
//        EcomRoleEntity createdRole = this.iEcomRoleEntityRepository.save(role);
//        return createdRole;
//    }

//    @Override
//    public Optional<EcomRoleEntity> findByName(String name) {
//        return iEcomRoleEntityRepository.findByName(name);
//    }


    @Override
    public EcomRoleAdmin createEcomRoleAdmin(EcomRoleAdmin role) {
        EcomRoleAdmin createdRole = this.iEcomRoleAdminRepository.save(role);
       return createdRole;
    }

    @Override
    public Optional<EcomRoleAdmin> findByName(String name) {
        return Optional.ofNullable(iEcomRoleAdminRepository.findByName(name));
    }
}
