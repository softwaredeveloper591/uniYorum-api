package com.enes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.model.entities.User;



public interface UserRepository extends JpaRepository<User, Integer> {
	 Optional<User> findByEmail(String email);
	 Optional<User> findByUsername(String username);

}
