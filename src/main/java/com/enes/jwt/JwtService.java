package com.enes.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.enes.model.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	public static final String SECRET_KEY = "5N+6yAw9UJlZGIE3ivXxkQlxnb9BauSkvcdSJ447DQE=";

	public String generateToken(User user) {
		Map<String, Object> claimsMap   =  new HashMap<>();
		claimsMap.put("role", "ROLE_"+user.getRole().name());
		return Jwts.builder()
		.setSubject(user.getEmail())
		.addClaims(claimsMap)
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*2))
		.signWith(getKey(), SignatureAlgorithm.HS256)
		.compact();
	}
	
	
	public  Object getClaimsByKey(String token , String key) {
		Claims claims =  getClaims(token);
		return claims.get(key);
	}
	
	
	
	public  Claims getClaims(String token) {
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token).getBody();
		return claims;
	}
	
	public <T> T exportToken(String token , Function<Claims, T> claimsFunction) {
		Claims claims =  getClaims(token);
		return claimsFunction.apply(claims);
	}
	
	public String getUsernameByToken(String token) {
		return exportToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDate(String token) {
		return exportToken(token, Claims::getExpiration);
	}
	
	
	public boolean isTokenExpired(String token) {
		Date expiredDate= getExpirationDate(token);
		return new Date().after(expiredDate);
	}
	
	private  Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
