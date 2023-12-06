package dev.concat.vab.ecomhotelappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "dev.concat.vab.ecomhotelappbackend")
public class EcomHotelAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources( EcomHotelAppApplication.class);
	}

	public static void main(String[]args){
		SpringApplication.run(EcomHotelAppApplication.class, args);
	}

}
