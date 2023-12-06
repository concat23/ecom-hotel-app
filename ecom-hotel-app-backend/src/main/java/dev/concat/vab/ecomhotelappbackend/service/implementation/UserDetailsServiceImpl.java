//package dev.concat.vab.ecomhotelappbackend.service.implementation;
//
//import dev.concat.vab.ecomhotelappbackend.exception.ResourceNotFoundExceptionResponse;
//import dev.concat.vab.ecomhotelappbackend.model.EcomAccountAdmin;
//import dev.concat.vab.ecomhotelappbackend.service.IEcomAccountServiceAdmin;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//    private final IEcomAccountServiceAdmin iEcomAccountServiceAdmin;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        EcomAccountAdmin account = iEcomAccountServiceAdmin.findByUsername(username);
//        if(account == null){
//            throw new ResourceNotFoundExceptionResponse("User " + username + "not found");
//        }
//        if (account.getRoles() == null || account.getRoles().isEmpty()) {
//            throw new RuntimeException("User has no roles");
//        }
//        Collection<GrantedAuthority> authorities = account.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//        return new User(account.getUsername(),account.getPassword(),account.isEnabled(),!account.isExpired(), !account.isCredentialsexpired()
//                ,!account.isLocked(),authorities);
//    }
//}
