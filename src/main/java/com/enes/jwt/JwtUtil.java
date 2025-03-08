package com.enes.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.enes.entities.Student;
import com.enes.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	
	private static final String SECRET_KEY ="q+th0KA402e2HZba0XDrDwNz/BDZhNdFHvzs+xBKasY=";
	
	public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user instanceof Student ? "STUDENT" : "ADMIN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) 
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public  Claims getClaims(String token) {
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token).getBody();
		return claims;
	}
    
    public  Object getClaimsByKey(String token , String key) {
		Claims claims =  getClaims(token);
		return claims.get(key);
	}
    
    public <T> T exportToken(String token, Function<Claims, T> claimsFunction) {
    	Claims claims =  getClaims(token);
		return claimsFunction.apply(claims);
    }
    
  //also I could return (String) jwtService.getClaimsByKey(token, "sub"); here
  	public String getUsernameByToken(String token) {
  		return exportToken(token, Claims::getSubject);
  	}
  	
  	//also I could return (String) jwtService.getClaimsByKey(token, "exp"); here
  	public boolean isTokenExpired(String token) {
  		Date expiredDate= exportToken(token, Claims::getExpiration);
  		return new Date().before(expiredDate);
  	}
    
    public Key getKey()  {
    	byte[] decodedBytes = Decoders.BASE64.decode(SECRET_KEY);
    	return Keys.hmacShaKeyFor(decodedBytes);
    }
}

