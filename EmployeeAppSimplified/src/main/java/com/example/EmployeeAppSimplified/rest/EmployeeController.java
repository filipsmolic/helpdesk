package com.example.EmployeeAppSimplified.rest;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeAppSimplified.dtos.AuthRequestDTO;
import com.example.EmployeeAppSimplified.dtos.JwtResponseDTO;
import com.example.EmployeeAppSimplified.dtos.TicketsResponseDTO;
import com.example.EmployeeAppSimplified.dtos.UsersCreateDTO;
import com.example.EmployeeAppSimplified.dtos.UsersResponseDTO;
import com.example.EmployeeAppSimplified.entities.Status;
import com.example.EmployeeAppSimplified.entities.Ticket;
import com.example.EmployeeAppSimplified.entities.User;
import com.example.EmployeeAppSimplified.security.JwtService;
import com.example.EmployeeAppSimplified.services.TicketService;
import com.example.EmployeeAppSimplified.services.UserService;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	//private EmployeeService employeeService;
	private UserService userService;
	private TicketService ticketService;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
	private JwtResponseDTO jwtResponseDTO;
	
	@Autowired
	public EmployeeController(TicketService ticketService, 
			AuthenticationManager authenticationManager, 
			UserService userService,
			JwtService jwtService,
			JwtResponseDTO jwtResponseDTO) {
		this.ticketService= ticketService;
		this.authenticationManager = authenticationManager;
		this.userService=userService;
		this.jwtService = jwtService;
		this.jwtResponseDTO = jwtResponseDTO;
	}
	

	@PostMapping("/login")
	public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
		
		if (authentication.isAuthenticated()){
			
			UserDetails userDetails = userService.loadUserByUsername(authRequestDTO.getUsername());
			
			Map<String, Object> claims = new HashMap<>();
			List<String> roles = userDetails.getAuthorities().stream()
				    .map(GrantedAuthority::getAuthority) 
				    .collect(Collectors.toList());
			claims.put("roles", roles);
			
			jwtResponseDTO.setAccessToken(jwtService.generateToken(claims, authRequestDTO.getUsername()));
			
			System.out.println("New token generated and sent to user");
			
			return jwtResponseDTO;
			
		} else {
			throw new UsernameNotFoundException("invalid user request..!!");
		}
	} 
	
	@PreAuthorize("hasAuthority('ROLE_VIEWER')")
	@GetMapping("/tickets")
	public Collection<TicketsResponseDTO> viewerTickets() {
		UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findByUserName(loggedUser.getUsername());
		Collection<Ticket> tickets =  ticketService.sortTicketsByIdDesc(new ArrayList<Ticket>(user.getTickets()));
		Collection<TicketsResponseDTO> trDTO = new ArrayList<>();
		for (Ticket ticket : tickets) {
			User userIter = userService.findById(ticket.getCreatorId());
			TicketsResponseDTO trDTOobj = new TicketsResponseDTO(
						ticket.getId(), 
						ticket.getTicketDescription(),
						ticket.getStatus(),
						ticket.getCreatorId(),
						ticket.getCreatedAt(),
						userIter.getUserName()
					);
			trDTO.add(trDTOobj);
		}
		return trDTO;
	}
	
	
	
	@PreAuthorize("hasAuthority('ROLE_VIEWER')")
	@PostMapping("/tickets")
	public Ticket newTicket(@RequestBody Ticket ticket) {
		ticket.setId(0);
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ticket.setCreatorId(userService.findByUserName(user.getUsername()).getId().intValue());
		ticketService.save(ticket);
		return ticket;
	}
	
	@PreAuthorize("hasAuthority('ROLE_AGENT')")
	@GetMapping("/agent/tickets")
	public Collection<TicketsResponseDTO> agentTickets() {
		Collection<Ticket> tickets = ticketService.findAllAgent();
		
		Collection<TicketsResponseDTO> trDTO = new ArrayList<>();
		for (Ticket ticket : tickets) {
			User userIter = userService.findById(ticket.getCreatorId());
			TicketsResponseDTO trDTOobj = new TicketsResponseDTO(
						ticket.getId(), 
						ticket.getTicketDescription(),
						ticket.getStatus(),
						ticket.getCreatorId(),
						ticket.getCreatedAt(),
						userIter.getUserName()
					);
			trDTO.add(trDTOobj);
		}
		return trDTO;
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/admin/tickets")
	public Collection<TicketsResponseDTO> adminTickets() {
		Collection<Ticket> tickets = ticketService.findAllAdmin();
		
		Collection<TicketsResponseDTO> trDTO = new ArrayList<>();
		for (Ticket ticket : tickets) {
			User userIter = userService.findById(ticket.getCreatorId());
			TicketsResponseDTO trDTOobj = new TicketsResponseDTO(
						ticket.getId(), 
						ticket.getTicketDescription(),
						ticket.getStatus(),
						ticket.getCreatorId(),
						ticket.getCreatedAt(),
						userIter.getUserName()
					);
			trDTO.add(trDTOobj);
		}
		return trDTO;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/admin/tickets/{id}")
	public Ticket deleteTicket(@PathVariable int id) {
		Ticket ticket = ticketService.findById(id);
		ticketService.delete(ticket);
		return ticket;
	}
	
	@PreAuthorize("hasAuthority('ROLE_AGENT')")
	@PutMapping("/agent/tickets/start/{id}")
	public Ticket startTicket(@PathVariable int id) {
		Ticket ticket = ticketService.findById(id);
		ticket.setStatus(Status.in_progress);
		ticketService.updateTicket(ticket);
		return ticket;
	}
	
	@PreAuthorize("hasAuthority('ROLE_AGENT')")
	@PutMapping("/agent/tickets/close/{id}")
	public Ticket closeTicket(@PathVariable int id) {
		Ticket ticket = ticketService.findById(id);
		ticket.setStatus(Status.closed);
		ticketService.updateTicket(ticket);
		return ticket;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/admin/tickets/{id}")
	public Ticket openTicket(@PathVariable int id) {
		Ticket ticket = ticketService.findById(id);
		ticket.setStatus(Status.open);
		ticketService.updateTicket(ticket);
		return ticket;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/users")
	public List<UsersResponseDTO> allUsers() {
		List<UsersResponseDTO> emptyUsers = new ArrayList<>();
		
		for (User user : userService.findAll()) {
			UsersResponseDTO newUser = new UsersResponseDTO(user.getId(), user.getUserName(), user.isEnabled(), user.getRoles());
			emptyUsers.add(newUser);
		}
		
		return emptyUsers;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/users/enable/{id}")
	public UsersResponseDTO enableUser(@PathVariable int id) {
		
		User user = userService.findById(id);
		user.setEnabled(true);
		userService.updateUser(user);
		UsersResponseDTO newUser = new UsersResponseDTO(user.getId(), user.getUserName(), user.isEnabled(), user.getRoles());
		return newUser;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/users/disable/{id}")
	public UsersResponseDTO disableUser(@PathVariable int id) {
		
		User user = userService.findById(id);
		user.setEnabled(false);
		userService.updateUser(user);
		UsersResponseDTO newUser = new UsersResponseDTO(user.getId(), user.getUserName(), user.isEnabled(), user.getRoles());
		return newUser;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/users")
	public User addUser(@RequestBody UsersCreateDTO userCreateDTO) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		User user = new User();
		
		user.setUserName(userCreateDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
		user.setRoles(userCreateDTO.getRoles());
		
		userService.save(user);
		
		return userService.findByUserName(userCreateDTO.getUsername());
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/users")
	public User updateUser(@RequestBody UsersCreateDTO userCreateDTO) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		User user = userService.findByUserName(userCreateDTO.getUsername());
		
		if ((userCreateDTO.getPassword() != null) && (userCreateDTO.getPassword() != "")) {
			user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
		}
		
		user.setRoles(userCreateDTO.getRoles());
		
		userService.updateUser(user);
		
		return user;
		
	}

}
