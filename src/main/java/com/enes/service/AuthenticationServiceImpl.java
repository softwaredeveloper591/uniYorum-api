package com.enes.service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enes.exception.BaseException;
import com.enes.exception.ErrorMessage;
import com.enes.model.entities.Student;
import com.enes.model.entities.StudentFile;
import com.enes.model.requests.RegisterRequest;
import com.enes.repository.StudentFileRepository;
import com.enes.exception.MessageType;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService  {
	
	@Autowired
	private StudentFileRepository studentFileRepository;

	@Override
	public ResponseEntity<String> register(MultipartFile file, RegisterRequest data) {
		if (file.isEmpty()) {
			throw new BaseException(new ErrorMessage("File must not be empty!", MessageType.NOT_ACCEPTABLE_REQUEST));
        }
		String fileName = file.getOriginalFilename();
		StudentFile file2= new StudentFile();
		file2.setFileName(fileName);
		try {
			file2.setFileData(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new BaseException(new ErrorMessage("File is not properly loaded", MessageType.NOT_ACCEPTABLE_REQUEST));
		}
		file2.setMimeType(file.getContentType());
		Student newStudent= Student.builder()
				.username
				.departmentId(null)
				.uniId(null)
				.profilePicture(fileName)
				.build();
		studentFileRepository.save(file2);
		return null;
		
	}
	
	

}
