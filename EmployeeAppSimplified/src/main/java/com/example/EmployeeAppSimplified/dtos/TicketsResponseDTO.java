package com.example.EmployeeAppSimplified.dtos;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.EmployeeAppSimplified.entities.Status;

@Component
public class TicketsResponseDTO {

	private int id;
	private String ticketDescription;
	private Status status;
	private int creatorId;
	private Date createdAt;
	private String username;
	
	public TicketsResponseDTO() {
		
	}
	
	public TicketsResponseDTO(int id, String ticketDescription, Status status, int creatorId, Date createdAt,
			String username) {
		super();
		this.id = id;
		this.ticketDescription = ticketDescription;
		this.status = status;
		this.creatorId = creatorId;
		this.createdAt = createdAt;
		this.username = username;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
