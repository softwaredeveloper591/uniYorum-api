package com.enes.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	 private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	 
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException  {
		String header;
		String token;
		String username;
		
		String requestUri = request.getRequestURI();
        logger.info("Incoming request to: {}", requestUri);

		header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			logger.info("authentication for {}", requestUri);
			filterChain.doFilter(request, response);
			return;
		}
		token = header.substring(7);
		try {
			username = jwtService.getUsernameByToken(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (userDetails != null && !jwtService.isTokenExpired(token)) {
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