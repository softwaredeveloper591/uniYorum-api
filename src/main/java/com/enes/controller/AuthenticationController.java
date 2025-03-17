package com.enes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enes.model.requests.RegisterRequest;
import com.enes.service.AuthenticationServiceImpl;

import jakarta.validation.Valid;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;
		
	@PostMapping("/register")
	public void register(@Valid @RequestPart("data") RegisterRequest request, @RequestPart("file") MultipartFile file) {
		authenticationServiceImpl.register(file, request);
		System.out.println(request.getEmail()+request.getPassword()+request.getUsername());
	}
}
