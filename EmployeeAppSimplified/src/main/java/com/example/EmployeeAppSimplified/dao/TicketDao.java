package com.example.EmployeeAppSimplified.dao;

import java.util.List;

import com.example.EmployeeAppSimplified.entities.Ticket;

public interface TicketDao {
	
	void save(Ticket ticket);
	
	void delete(Ticket ticket);
	
	Ticket findById(int id);
	
	List<Ticket> findAllAgent();
	
	List<Ticket> findAllAdmin();
	
	public void updateTicket(Ticket ticket);
	
}
