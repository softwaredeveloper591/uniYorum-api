package com.enes.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@OneToOne
	@NotNull(message = "University ID cannot be null.")
    @Column(name = "uni_id", nullable = false)
    private University university; // Changed to Long to match Java conventions

	@OneToOne
	@JoinColumn(name = "department_id", referencedColumnName = "department_id")
	@NotNull(message = "Department ID cannot be null.")
    private Department department;

    @Column(nullable = false)
    private Boolean approved=false;

    @Size(max = 255, message = "Profile picture URL cannot exceed 255 characters.")
    @Column(name = "profilePicture", length = 255, nullable = true)
    private String profilePicture;
}