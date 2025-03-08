package com.enes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


public class SecurityConfig {
	
	
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().permitAll() // Allows access to all endpoints without authentication
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing (not recommended for production)
                .formLogin(login -> login.disable()); // Disables login redirection

        return http.build();
    }
}
