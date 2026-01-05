package com.demo.service;

import com.demo.entity.User;

public interface AuthService {
    User login(String email, String password);
}
