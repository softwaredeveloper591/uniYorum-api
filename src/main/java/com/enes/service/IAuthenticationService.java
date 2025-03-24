package com.enes.service;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.enes.model.requests.RegisterRequest;
import com.enes.model.responses.StudentResponseDto;

public interface IAuthenticationService {

	public StudentResponseDto register(MultipartFile file, RegisterRequest data);
}
