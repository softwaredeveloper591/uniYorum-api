package com.enes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.model.entities.Student;


public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	Optional<Student> findByEmail(String email);

}
