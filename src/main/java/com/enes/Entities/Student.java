package com.enes.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Username cannot be empty.")
	@Size(max = 45,message = "Username cannot exceed 45 characters.")
	@Column(name = "username", length = 45, nullable = false)
	private String username;
	
	@Email(message = "Invalid email address.")
	@Size(max = 45,message = "Email cannot exceed 45 characters.")
	@Column(length=45, nullable = false, unique = true)
	private String email;
	
	@Size(max = 250, message = "Password cannot exceed 250 characters.")
	@Column(length = 250, nullable = false, unique = true)
	private String password;
	
	@NotBlank(message = "University ID cannot be null.")
    @Column(name = "uni_id", nullable = false)
    private Long uniId; // Changed to Long to match Java conventions

	@NotBlank(message = "Department ID cannot be null.")
    @Column(name = "department_id", nullable = false)
    private Long departmentId; // Changed to Long to match Java conventions

    @Column(nullable = false)
    private Boolean approved = false;

    @Size(max = 255, message = "Profile picture URL cannot exceed 255 characters.")
    @Column(name = "profilePicture", length = 255, nullable = true)
    private String profilePicture;
	
	
}
