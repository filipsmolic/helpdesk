package com.example.EmployeeAppSimplified.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.EmployeeAppSimplified.entities.Ticket;
import com.example.EmployeeAppSimplified.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@Repository
public class UserDaoImpl implements UserDao{

	private EntityManager entityManager;

	public UserDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public User findByUserName(String theUserName) {

		TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName and enabled=true", User.class);
		theQuery.setParameter("uName", theUserName);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public List<User> findAll() {
		
		TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        
		return query.getResultList();
	}

	@Override
	public User findById(int id) {
		
		TypedQuery<User> theQuery = entityManager.createQuery("from User where id=:uId", User.class);
		theQuery.setParameter("uId", id);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
		
	}

	@Override
	public void save(User user) {
		entityManager.persist(user);
	}

	@Override
	public void updateUser(User user) {
		entityManager.merge(user);
	}
	
	
	
	
	
	
}
