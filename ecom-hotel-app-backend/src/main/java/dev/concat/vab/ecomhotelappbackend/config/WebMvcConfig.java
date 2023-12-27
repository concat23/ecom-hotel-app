package dev.concat.vab.ecomhotelappbackend.config;

import dev.concat.vab.ecomhotelappbackend.resolver.TokenIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenIdArgumentResolver tokenIdArgumentResolver;

    public WebMvcConfig(TokenIdArgumentResolver tokenIdArgumentResolver) {
        this.tokenIdArgumentResolver = tokenIdArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenIdArgumentResolver);
    }
}