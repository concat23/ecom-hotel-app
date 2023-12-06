//package dev.concat.vab.ecomhotelappbackend.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//@Configuration
//public class WebApplicationSecurityConfiguration {
//
//    private final EcomAccountAdminAuthenticationProvider ecomAccountAuthenticationProvider;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(ecomAccountAuthenticationProvider);
//        http.csrf()
//                .disable();
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/api/accounts/**")
//                .permitAll();
//        http.authorizeRequests()
//                .anyRequest()
//                .hasAnyRole("ROLE_USER","ROLE_ADMIN","ROLE_ALL_PERMISSION")
//                .and()
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        return http.build();
//    }
//
//}
