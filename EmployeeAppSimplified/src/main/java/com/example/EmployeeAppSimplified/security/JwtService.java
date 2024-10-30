package com.example.EmployeeAppSimplified.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String secretKey = "dc78a76d2bc0b6bfbc6ec612125eacdc92ea0621040f3d79e6644fe285893c47";
	
	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}
	
	public String generateToken(String username) {
		return generateToken(new HashMap<>(), username);
	}
	
	public String generateToken(Map<String, Object> extraClaims, String username) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
				
	}
	
	public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}
	
	public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
		final String username = extractUsername(jwtToken);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
	}
	
	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}
	
	private Date extractExpiration(String jwtToken) {
		return extractClaim(jwtToken, Claims::getExpiration);
	}
	
	private Claims extractAllClaims(String jwtToken) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
}
