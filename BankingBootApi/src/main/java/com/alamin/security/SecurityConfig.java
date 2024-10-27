package com.alamin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private AccountDetailsService accountDetailsService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
		    .csrf(csrf -> csrf.disable())
		    .authorizeHttpRequests(authz -> authz
		        .requestMatchers("/api/accounts/**").permitAll()
				.requestMatchers("/api/accounts/showAccount/**").hasRole("USER")
				.requestMatchers("/api/accounts/showAllAccounts").hasRole("ADMIN")
						 
		        .anyRequest().authenticated()
		    )
				/*
				 * .formLogin(form -> form .loginProcessingUrl("/api/accounts/login")
				 * .permitAll() ) .logout(logout -> logout .logoutUrl("/api/accounts/logout")
				 * .permitAll() )
				 */
		    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder = 
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder
		    .userDetailsService(accountDetailsService)
		    .passwordEncoder(passwordEncoder());
		
		return authenticationManagerBuilder.build();
	}
}
