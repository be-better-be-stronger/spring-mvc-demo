package com.demo.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.dao.UserDao;
import com.demo.entity.User;
import com.demo.entity.UserStatus;
import com.demo.exception.BadRequestException;
import com.demo.exception.ForbiddenException;
import com.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String email, String password) {

        String e = normalizeEmail(email);
        String p = (password == null) ? "" : password;

        if (e.isBlank() || p.isBlank()) {
            throw new BadRequestException("Invalid email or password");
        }

        User u = userDao.findByEmail(e)
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        // DB đang lưu bcrypt -> phải match, KHÔNG equals
        if (!passwordEncoder.matches(p, u.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        // status đang là String (ACTIVE/INACTIVE...)
        if (u.getStatus() == null || !UserStatus.ACTIVE.name().equalsIgnoreCase(u.getStatus())) {
            throw new ForbiddenException("User is inactive");
        }
        
        return u;
    }

    private String normalizeEmail(String email) {
        if (email == null) return "";
        return email.trim().toLowerCase();
    }
}