package dev.concat.vab.ecomhotelappbackend;

import dev.concat.vab.ecomhotelappbackend.enumeration.Role;
import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.request.RegisterRequest;
import dev.concat.vab.ecomhotelappbackend.response.AuthResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomAuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static dev.concat.vab.ecomhotelappbackend.enumeration.Role.ADMIN;
import static dev.concat.vab.ecomhotelappbackend.enumeration.Role.MANAGER;

@SpringBootApplication(scanBasePackages = "dev.concat.vab.ecomhotelappbackend")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EcomHotelAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(EcomHotelAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(EcomHotelAppApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			IEcomAuthenticationService service
//	) {
//		return args -> {
//			RegisterRequest adminRequest = RegisterRequest.builder()
//					.firstName("Bang")
//					.lastName("Vo Anh")
//					.email("bang@mail.com")
//					.password("password")
//					.role(Role.ADMIN)
//					.build();
//			AuthResponse adminResponse = service.register(adminRequest);
//			System.out.println("Admin token: " + adminResponse.getAccessToken());
//
//			RegisterRequest managerRequest = RegisterRequest.builder()
//					.firstName("Manager")
//					.lastName("Manager")
//					.email("manager@mail.com")
//					.password("password")
//					.role(Role.MANAGER)
//					.build();
//			AuthResponse managerResponse = service.register(managerRequest);
//			System.out.println("Manager token: " + managerResponse.getAccessToken());
//		};
//	}
}
