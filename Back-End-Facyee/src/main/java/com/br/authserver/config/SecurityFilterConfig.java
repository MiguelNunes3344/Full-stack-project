package com.br.authserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;





@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterConfig {
	
	
	private final UserAuthProvider userAuthProvider;
	
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		.addFilterBefore(new JwtAuthFilter(userAuthProvider),BasicAuthenticationFilter.class)
		.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/obterTokenCSRF").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/register").permitAll()
				.requestMatchers("/verifyToken").permitAll()
				.requestMatchers("/processimage").permitAll()
				.anyRequest().authenticated())
		.formLogin(form -> form.loginPage("http://localhost:4200/").permitAll());
		return http.build();
	}
	
	
	
	
	
}
