package com.enes.service;


import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enes.exception.BaseException;
import com.enes.exception.ErrorMessage;
import com.enes.exception.MessageType;
import com.enes.jwt.JwtService;
import com.enes.model.entities.Department;
import com.enes.model.entities.Student;
import com.enes.model.entities.StudentFile;
import com.enes.model.entities.Token;
import com.enes.model.entities.University;
import com.enes.model.entities.User;
import com.enes.model.enums.Role;
import com.enes.model.requests.AuthRequest;
import com.enes.model.requests.RegisterRequest;
import com.enes.model.responses.StudentResponseDto;
import com.enes.repository.DepartmentRepository;
import com.enes.repository.StudentFileRepository;
import com.enes.repository.StudentRepository;
import com.enes.repository.TokenRepository;
import com.enes.repository.UniversityRepository;
import com.enes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService  {
	
	private final StudentFileRepository studentFileRepository;
	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationProvider authenticationProvider;
	private final TokenRepository tokenRepository;
	private final DepartmentRepository departmentRepository;
	private final UniversityRepository universityRepository;
	
	@Override
	public StudentResponseDto register(MultipartFile file, RegisterRequest data) {
		Student newStudent = createStudent(data);
		StudentFile processedFile = processFile(file, newStudent);
		Student save = studentRepository.save(newStudent);	
		studentFileRepository.save(processedFile);
		return new StudentResponseDto(save.getUser().getId(),save.getUser().getUsername(), save.getUser().getEmail());
	}
	
	public String login(AuthRequest loginRequest) {
		UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
		authenticationProvider.authenticate(authenticationToken);
		Optional<User> optUser = userRepository.findByEmail(loginRequest.username());
		if(optUser.isEmpty()){
			throw new BaseException(new ErrorMessage(null, MessageType.USERNAME_OR_PASSWORD_INVALID));
		}
		String token =jwtService.generateToken(optUser.get());
		Date expiryDate= jwtService.getExpirationDate(token);
		Token newToken = Token.builder()
		.token(token)
		.expiryDate(expiryDate)
		.user(optUser.get())
		.build();
		tokenRepository.save(newToken);
		return token;
	}
	
	private StudentFile processFile(MultipartFile file, Student student){
		if (file.isEmpty()) {
			throw new BaseException(new ErrorMessage("File must not be empty!", MessageType.NOT_ACCEPTABLE_REQUEST));
        }
		
		if (!Objects.equals(file.getContentType(), "application/pdf")) {
			throw new BaseException(new ErrorMessage("File type must be pdf!", MessageType.NOT_ACCEPTABLE_REQUEST));
	    }
		
		String fileName = file.getOriginalFilename();
		StudentFile newFile= new StudentFile();
		newFile.setFileName(fileName);
		try {
			newFile.setFileData(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new BaseException(new ErrorMessage("File is not properly loaded", MessageType.NOT_ACCEPTABLE_REQUEST));
		}
		newFile.setMimeType(file.getContentType());
		newFile.setStudent(student);
		return newFile;
	}
	
	private Student createStudent(RegisterRequest data) {
		User newUser = User.builder()
		.username(data.getUsername())
		.email(data.getEmail())
		.password(passwordEncoder.encode(data.getPassword()))
		.role(Role.STUDENT)
		.build();
		
		Optional<Department> optionalDepartment= departmentRepository.findById(data.getDepartmentId());
		Optional<University> optionalUniversity= universityRepository.findById(data.getUniId());
		if(optionalDepartment.isEmpty() || optionalUniversity.isEmpty()) {
			throw new BaseException(new ErrorMessage("There is no Department or University with given Id",MessageType.NOT_ACCEPTABLE_REQUEST));
		}
		Student newStudent=Student.builder()
		.user(newUser)
		.university(optionalUniversity.get())
		.department(optionalDepartment.get())
		.build();
		return newStudent;
	}
	

}
