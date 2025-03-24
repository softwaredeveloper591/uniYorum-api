package com.enes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.model.entities.Token;


public interface TokenRepository extends JpaRepository<Token, Integer>{
	
	Optional<Token> findByToken(String token);
	

}
