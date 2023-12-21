package dev.concat.vab.ecomhotelappbackend.security;

import dev.concat.vab.ecomhotelappbackend.filter.*;
import dev.concat.vab.ecomhotelappbackend.provider.JWTTokenProvider;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomUserRepository;
import dev.concat.vab.ecomhotelappbackend.service.implementation.EcomUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static dev.concat.vab.ecomhotelappbackend.constant.SecurityConstant.PUBLIC_URLS;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jWTAccessDeniedHandler;
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final IEcomUserRepository iEcomUserRepository;
	private final JWTTokenProvider jwtTokenProvider;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().cors().and()
//		.sessionManagement().sessionCreationPolicy(STATELESS)
//		.and()
//		.authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.exceptionHandling().accessDeniedHandler(jWTAccessDeniedHandler)
//		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
//		.and()
//		.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
//
//	}
@Override
protected void configure(HttpSecurity http) throws Exception {
	CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
			iEcomUserRepository,
			authenticationManagerBean());
	customAuthenticationFilter.setFilterProcessesUrl("/api/login");
	http.csrf().disable();
	http.cors().disable();
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.authorizeRequests().antMatchers(PUBLIC_URLS).permitAll();
	http.authorizeRequests().antMatchers(POST,"/api/auth/login", "/api/auth/register").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(GET,"/api/auth/list/**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(GET,"/api/auth/ecom-user/**").hasAnyAuthority("ROLE_ADMIN");
	http.authorizeRequests().antMatchers(POST,"/api/auth/ecom-user/add").hasAnyAuthority("ROLE_ADMIN");
//      http.authorizeRequests().anyRequest().permitAll();
	http.authorizeRequests().anyRequest().authenticated();
	http.exceptionHandling().accessDeniedHandler(jWTAccessDeniedHandler)
							.authenticationEntryPoint(jwtAuthenticationEntryPoint);
	http.addFilter(customAuthenticationFilter);
	http.addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico");

		// swagger
		web.ignoring().antMatchers(
				"/v2/api-docs",  "/configuration/ui",
				"/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**","/swagger/**");
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


}

