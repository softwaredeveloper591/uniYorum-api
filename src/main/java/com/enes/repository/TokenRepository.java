package com.enes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.enes.model.entities.Token;


public interface TokenRepository extends JpaRepository<Token, Integer>{
	
	Optional<Token> findByToken(String token);
	
	// Delete all expired tokens before fetching
    @Modifying
    @Query("DELETE FROM Token t WHERE t.expiryDate < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();

    // Fetch all valid (non-revoked) tokens of the user associated with the given token
    @Query("SELECT t FROM Token t " +
           "INNER JOIN t.user u " +
           "WHERE u.id = :userId AND t.isRevoked = false")
    List<Token> findAllValidTokensByUserToken(@Param("userId") Integer userId);
    
    

}
