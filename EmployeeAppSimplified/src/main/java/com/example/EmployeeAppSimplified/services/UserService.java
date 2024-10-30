package com.example.EmployeeAppSimplified.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.EmployeeAppSimplified.entities.User;

public interface UserService extends UserDetailsService {
	
	public User findByUserName(String userName);
	
	public User findById(int id);
	
	public List<User> findAll();
	
	public void save(User user);
	
	public void updateUser(User user);
	
}
