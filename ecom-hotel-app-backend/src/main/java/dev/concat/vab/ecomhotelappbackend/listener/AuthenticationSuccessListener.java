package dev.concat.vab.ecomhotelappbackend.listener;

import dev.concat.vab.ecomhotelappbackend.model.EcomUser;
import dev.concat.vab.ecomhotelappbackend.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {
	private final LoginAttemptService loginAttemptService;


	public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
		this.loginAttemptService = loginAttemptService;
	}

	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if(principal instanceof EcomUser) {
			EcomUser ecomUser = (EcomUser) event.getAuthentication().getPrincipal();
			loginAttemptService.evictUserFromLoginAttemptCache(ecomUser.getUsername());
		}
	}


}
