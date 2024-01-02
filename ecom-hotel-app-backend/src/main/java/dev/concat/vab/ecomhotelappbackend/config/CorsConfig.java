package dev.concat.vab.ecomhotelappbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Add your specific endpoint
                        .allowedOrigins("http://localhost:5173","http://localhost:8066/swagger-ui/index.html")  // Add your frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}

