package com.demo.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.demo.dao.UserDao;
import com.demo.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

	@Override
	public Optional<User> findByEmail(String email) {
		if (email == null) return Optional.empty();

        String normalized = email.trim().toLowerCase();
        if (normalized.isBlank()) return Optional.empty();
        
		var list =  em.createQuery("""
	            select u
	            from User u
	            where lower(u.email) = lower(:email)
	            """, User.class)
	        .setParameter("email", email)
	        .setMaxResults(1)
	        .getResultList();
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}
}
