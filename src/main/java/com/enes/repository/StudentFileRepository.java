package com.enes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.model.entities.StudentFile;

public interface StudentFileRepository extends JpaRepository<StudentFile, Integer>{
	
	

}
