package com.demo.dao;

import java.util.Optional;

import com.demo.entity.User;

public interface UserDao {
	Optional<User> findById(Integer id);
	Optional<User> findByEmail(String email);
}
