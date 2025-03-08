package com.enes.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
    private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			
		String header = request.getHeader(AUTHORIZATION_HEADER);
		
		if (header == null || !header.startsWith(BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = header.substring(BEARER_PREFIX.length());
		try {
            String username = jwtService.getUsernameByToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null && !jwtService.isTokenExpired(token)) {
                    setAuthentication(username, userDetails);
                }
            }
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
	}
	
	private void setAuthentication(String username, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, null, userDetails.getAuthorities());
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
