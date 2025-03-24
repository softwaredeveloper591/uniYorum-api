package com.enes.model.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
		
	@Email
	@NotBlank
	String username,
	
	@NotBlank
    String password) {
	
	
}
