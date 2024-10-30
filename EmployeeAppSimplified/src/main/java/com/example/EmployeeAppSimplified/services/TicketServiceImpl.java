package com.example.EmployeeAppSimplified.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeAppSimplified.dao.TicketDao;
import com.example.EmployeeAppSimplified.entities.Ticket;

import jakarta.transaction.Transactional;


@Service
public class TicketServiceImpl implements TicketService{

	private TicketDao ticketDao;

	@Autowired
	public TicketServiceImpl(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}
	
	@Override
	public List<Ticket> findAllAdmin() {
		return ticketDao.findAllAdmin();
	}
	
	@Override
	public List<Ticket> findAllAgent() {
		return ticketDao.findAllAgent();
	}

	@Override
	public Ticket findById(int id) {
		Ticket ticket = ticketDao.findById(id);
		return ticket;
	}

	@Override
	@Transactional
	public void save(Ticket ticket) {
		ticketDao.save(ticket);
	}
	
	@Override
	@Transactional
	public void delete(Ticket ticket) {
		ticketDao.delete(ticket);
	}

	@Override
	@Transactional
	public void updateTicket(Ticket ticket) {
		ticketDao.updateTicket(ticket);
	}
	
	public List<Ticket> sortTicketsByIdDesc(List<Ticket> tickets) {
        Collections.sort(tickets, Comparator.comparing(Ticket::getId).reversed());
        return tickets;
    }
	

}
