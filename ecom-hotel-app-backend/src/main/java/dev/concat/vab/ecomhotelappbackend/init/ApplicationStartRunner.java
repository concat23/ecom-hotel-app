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

    }
}
