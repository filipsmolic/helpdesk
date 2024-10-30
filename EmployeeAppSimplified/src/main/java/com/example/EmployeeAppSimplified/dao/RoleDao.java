package com.example.EmployeeAppSimplified.dao;

import com.example.EmployeeAppSimplified.entities.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
