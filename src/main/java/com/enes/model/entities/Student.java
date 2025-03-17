package com.enes.model.entities;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "student")
@AllArgsConstructor
@Builder
public class Student extends User implements UserDetails {
	
	@NotBlank(message = "University ID cannot be null.")
    @Column(name = "uni_id", nullable = false)
    private Integer uniId; // Changed to Long to match Java conventions

	@NotBlank(message = "Department ID cannot be null.")
    @Column(name = "department_id", nullable = false)
    private Integer departmentId; // Changed to Long to match Java conventions

    @Column(nullable = false)
    private Boolean approved=false;

    @Size(max = 255, message = "Profile picture URL cannot exceed 255 characters.")
    @Column(name = "profilePicture", length = 255, nullable = true)
    private String profilePicture;

	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.getRole().name()));
    }
}