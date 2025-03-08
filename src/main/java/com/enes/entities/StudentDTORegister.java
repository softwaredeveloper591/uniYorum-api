package com.enes.entities;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class StudentDTORegister {
    
    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotNull(message = "University ID is required.")
    private Long uniId;

    @NotNull(message = "Department ID is required.")
    private Long departmentId;
}
