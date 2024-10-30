package com.example.EmployeeAppSimplified.services;

import java.util.List;

import com.example.EmployeeAppSimplified.entities.Ticket;

public interface TicketService {
	public List<Ticket> findAllAgent();
	
	public List<Ticket> findAllAdmin();
	
	public Ticket findById(int id);
	
	public void save(Ticket ticket);
	
	public void delete(Ticket ticket);
	
	public void updateTicket(Ticket ticket);
	
	public List<Ticket> sortTicketsByIdDesc(List<Ticket> tickets);
}
