package com.enes.model.entities;

import com.enes.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@MappedSuperclass
@Getter
@Setter
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username cannot be empty.")
    @Size(max = 45, message = "Username cannot exceed 45 characters.")
    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @Email(message = "Invalid email address.")
    @Size(max = 45, message = "Email cannot exceed 45 characters.")
    @Column(length = 45, nullable = false, unique = true)
    private String email;

    @Size(max = 250, message = "Password cannot exceed 250 characters.")
    @Column(length = 250, nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;  // To differentiate between ADMIN and STUDENT

    
}