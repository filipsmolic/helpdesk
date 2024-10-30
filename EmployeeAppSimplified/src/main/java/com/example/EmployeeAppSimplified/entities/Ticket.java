package com.example.EmployeeAppSimplified.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ticket")
public class Ticket {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ticket_description")
    private String ticketDescription;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.open;
    
    @Column(name = "creator_id")
    private int creatorId;
    
    @Column(name = "created_at")
    private Date createdAt = new Date();
    
    public Ticket() {
    	
    }
    
    public Ticket(int id, String ticketDescription, Status status, int creatorId, Date createdAt) {
		this.id = id;
		this.ticketDescription = ticketDescription;
		this.status = status;
		this.creatorId = creatorId;
		this.createdAt = createdAt;
	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTicketDescription() {
		return ticketDescription;
	}



	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}



	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}



	public int getCreatorId() {
		return creatorId;
	}



	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", ticketDescription=" + ticketDescription + ", status=" + status + ", creatorId="
				+ creatorId + ", createdAt=" + createdAt + "]";
	}
	
	
}
