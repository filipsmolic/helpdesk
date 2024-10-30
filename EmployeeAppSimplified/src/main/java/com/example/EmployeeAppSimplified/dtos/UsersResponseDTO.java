package com.example.EmployeeAppSimplified.dtos;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.EmployeeAppSimplified.entities.Role;


@Component
public class UsersResponseDTO {
	
	private Long id;
	private String username;
	private boolean enabled;
	private Collection<Role> roles;
	
	public UsersResponseDTO() {

	}

	public UsersResponseDTO(Long id, String username, boolean enabled, Collection<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.enabled = enabled;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
