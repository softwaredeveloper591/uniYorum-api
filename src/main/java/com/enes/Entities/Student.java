package com.enes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
public class Student extends User {
	
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
