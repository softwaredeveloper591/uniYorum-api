package com.enes.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.enes.model.entities.Admin;
import com.enes.model.entities.Student;
import com.enes.repository.AdminRepository;
import com.enes.repository.StudentRepository;

@Configuration
public class AppConfig {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			 Optional<Student> optionalStudent = 	studentRepository.findByEmail(email);
			 if(optionalStudent.isPresent()) {
				 return optionalStudent.get();
			 }
			 Optional<Admin> optionalAdmin = 	adminRepository.findByEmail(email);
			 if(optionalAdmin.isPresent()) {
				 return optionalAdmin.get();
			 }
			 throw new UsernameNotFoundException("User not found: " + email);
			}
		};
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
