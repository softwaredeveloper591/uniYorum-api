package com.enes.jwt;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.enes.exception.BaseException;
import com.enes.model.entities.Token;
import com.enes.repository.TokenRepository;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final TokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException  {
		String header;
		String token;
		String username;
		
		header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		token = header.substring(7);
		try {
			username = jwtService.getUsernameByToken(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Optional<Token> optToken = tokenRepository.findByToken(token);
				boolean isRevoked=false;
				if(optToken.isPresent() || optToken.get().isRevoked()) isRevoked=true; // token must not be revoked in db
				if (userDetails != null && !jwtService.isTokenExpired(token) && !isRevoked) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							username, null, userDetails.getAuthorities());
					authentication.setDetails(userDetails);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (ExpiredJwtException e) {
			System.out.println("Tokes is expired : " + e.getMessage());
		}
		catch(Exception e) {
			System.out.println("A general error has occured in generating authentication with the token : " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

}