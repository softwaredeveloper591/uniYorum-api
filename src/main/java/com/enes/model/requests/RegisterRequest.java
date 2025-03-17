package com.enes.model.requests;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter

public class RegisterRequest {
	
	@NotBlank
	@Size(min = 3, max = 50)
	private String username;
	
	@NotBlank
	@Size(min = 3, max = 50)
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 6, max = 50)
	private String password;
	
	@NotBlank
	private String university;
	
	@NotBlank
	private String faculty;
	
	@NotBlank
	private String department;
	
	private MultipartFile file;
	
	
	
	

}
