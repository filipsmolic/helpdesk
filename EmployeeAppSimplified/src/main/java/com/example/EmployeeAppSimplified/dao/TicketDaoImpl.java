package com.example.EmployeeAppSimplified.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EmployeeAppSimplified.entities.Status;
import com.example.EmployeeAppSimplified.entities.Ticket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class TicketDaoImpl implements TicketDao{
	
	private EntityManager entityManager;
	
	@Autowired
	public TicketDaoImpl(EntityManager entityManager) {
		this.entityManager=entityManager;
	}

	@Override
	public void save(Ticket ticket) {
		entityManager.persist(ticket);
	}

	@Override
	public Ticket findById(int id) {
		return entityManager.find(Ticket.class, id);
	}

	@Override
	public List<Ticket> findAllAgent() {
		TypedQuery<Ticket> query = entityManager.createQuery("FROM Ticket WHERE status=:openStatus OR status=:inProgressStatus ORDER BY createdAt desc", Ticket.class);
		
		query.setParameter("openStatus", Status.open);
        query.setParameter("inProgressStatus", Status.in_progress);
        
		return query.getResultList();
	}

	@Override
	public List<Ticket> findAllAdmin() {
		TypedQuery<Ticket> query = entityManager.createQuery("FROM Ticket ORDER BY createdAt desc", Ticket.class);
        
		return query.getResultList();
	}
	
	@Override
	public void updateTicket(Ticket ticket) {
		entityManager.merge(ticket);
	}

	@Override
	public void delete(Ticket ticket) {
		entityManager.remove(ticket);
	}
	
	
	
}
