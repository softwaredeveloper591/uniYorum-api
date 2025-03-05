package com.enes.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "admin")
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Username cannot be empty.")
	@Size(max = 45,message = "Username cannot exceed 45 characters.")
	@Column(name = "name", length = 45, nullable = false)
	private String name;
	
	@Email(message = "Invalid email address.")
	@Size(max = 45,message = "Email cannot exceed 45 characters.")
	@Column(length=45, nullable = false, unique = true)
	private String email;
	
	@Size(max = 250, message = "Password cannot exceed 250 characters.")
	@Column(length = 250, nullable = false, unique = true)
	private String password;
	

}