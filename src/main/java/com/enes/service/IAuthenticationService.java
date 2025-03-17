package com.enes.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.enes.model.requests.RegisterRequest;

public interface IAuthenticationService {

	public ResponseEntity<String> register(MultipartFile file, RegisterRequest data);
}
