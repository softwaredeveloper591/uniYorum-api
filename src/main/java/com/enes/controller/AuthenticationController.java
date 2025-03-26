package com.enes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enes.model.requests.AuthRequest;
import com.enes.model.requests.RegisterRequest;
import com.enes.model.responses.StudentResponseDto;
import com.enes.service.AuthenticationServiceImpl;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;
	
	@Autowired
	BCryptPasswordEncoder byBCryptPasswordEncoder;
		
	@PostMapping("/register")
	public ResponseEntity<StudentResponseDto> register(@Valid @RequestPart("data") RegisterRequest request, @RequestPart("file") MultipartFile file) {
		StudentResponseDto savedStudent = authenticationServiceImpl.register(file, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequest loginRequest) {
		String token = authenticationServiceImpl.login(loginRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
	}
	
	@GetMapping("/admin")
	public String getMethodName() {
		return "here";
	}
}
