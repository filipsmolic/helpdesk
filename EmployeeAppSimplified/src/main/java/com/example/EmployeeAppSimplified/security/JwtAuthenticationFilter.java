package com.example.EmployeeAppSimplified.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.EmployeeAppSimplified.services.UserServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private JwtService jwtService;
	private UserServiceImpl userServiceImpl;
	
	
	@Autowired
	public JwtAuthenticationFilter(JwtService jwtService, UserServiceImpl userServiceImpl) {
		this.jwtService = jwtService;
		this.userServiceImpl = userServiceImpl;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		String jwtToken = null;
		String username = null;
		
		try {
			
			if (authHeader != null && authHeader.startsWith("Bearer")) {
				jwtToken = authHeader.substring(7);
				username = jwtService.extractUsername(jwtToken);
			} 

			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
				if (jwtService.isTokenValid(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
					System.out.println("TOKEN IS VALID\nUSERNAME: " + userDetails.getUsername());
					System.out.println("PASSWORD: " + userDetails.getPassword());
					System.out.println("ROLES: " + userDetails.getAuthorities()); 
				}
			}
			
			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			System.out.println("Error during token validation" + e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			
			response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
		}
		
	}
	
	
	
}
