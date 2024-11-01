package com.example.EmployeeAppSimplified.dtos;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.EmployeeAppSimplified.entities.Role;


@Component
public class UsersCreateDTO {
	
	private String username;
	private String password;
	private Collection<Role> roles;
	
	public UsersCreateDTO() {

	}

	public UsersCreateDTO(String username, String password, Collection<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
}

