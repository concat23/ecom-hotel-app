//package dev.concat.vab.ecomhotelappbackend.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class EcomAccountAdminAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        if (authentication.getCredentials() == null || userDetails.getPassword() == null){
//            throw new BadCredentialsException("Credentials may not be null");
//        }
//
//        if (this.passwordEncoder.matches((String)authentication.getCredentials(),userDetails.getPassword())){
//            throw new BadCredentialsException("Invalid credentials");
//        }
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        return this.userDetailsService.loadUserByUsername(username);
//    }
//}
