package com.enes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.model.entities.Admin;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	 
	 Optional<Admin> findByEmail(String email);

}
