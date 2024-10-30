package com.example.EmployeeAppSimplified.dtos;

import org.springframework.stereotype.Component;

@Component
public class JwtResponseDTO {
	private String accessToken;

	public JwtResponseDTO() {
		
	}
	
	public JwtResponseDTO(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "JwtResponseDTO [accessToken=" + accessToken + "]";
	}
	
	
	
}
