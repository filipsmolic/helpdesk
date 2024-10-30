package com.example.EmployeeAppSimplified.dao;

import java.util.List;

import com.example.EmployeeAppSimplified.entities.User;

public interface UserDao {

	User findByUserName(String userName);
	
	User findById(int id);
	
	void save(User user);
	
	void updateUser(User user);
	
	List<User> findAll();
	
}
