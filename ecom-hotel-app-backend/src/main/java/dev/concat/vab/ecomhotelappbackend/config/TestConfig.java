package dev.concat.vab.ecomhotelappbackend.config;

import dev.concat.vab.ecomhotelappbackend.resolver.TokenIdArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public TokenIdArgumentResolver tokenIdArgumentResolver() {
        return new TokenIdArgumentResolver();
    }
}
