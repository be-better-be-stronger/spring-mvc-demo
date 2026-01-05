package com.demo.web.controller;

import com.demo.entity.User;
import com.demo.exception.AppException;
import com.demo.security.SessionKeys;
import com.demo.service.AuthService;
import com.demo.web.auth.PostLoginHandler;
import com.demo.web.support.RedirectPolicy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Login / Logout controller.
 *
 * - GET  /login  : render form, giữ next
 * - POST /login  : authenticate, set session, redirect next
 * - POST /logout : clear session
 */
@Controller
public class AuthController {

	private final AuthService authService;
    private final PostLoginHandler postLoginHandler;
    private final RedirectPolicy redirectPolicy;

    public AuthController(
            AuthService authService,
            PostLoginHandler postLoginHandler,
            RedirectPolicy redirectPolicy
    ) {
        this.authService = authService;
        this.postLoginHandler = postLoginHandler;
        this.redirectPolicy = redirectPolicy;
    }

    @GetMapping("/login")
    public String showLogin( HttpServletRequest req, Model model ) {
        HttpSession s = req.getSession(false);

        String next = (s == null) ? null : (String) s.getAttribute(SessionKeys.AFTER_LOGIN_URL);
        if (next == null || next.isBlank()) next = "/products";

        model.addAttribute("next", next);
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "next", required = false) String next,
            HttpServletRequest req,
            Model model
    ) {
        try {
            User u = authService.login(email, password);            
            
            HttpSession session = req.getSession(true);
            session.setAttribute(SessionKeys.AUTH_USER_ID, u.getId());
            session.setAttribute(SessionKeys.AUTH_ROLE, u.getRole());
                        
            String target = postLoginHandler.handle(session, u.getId());

            return "redirect:" + redirectPolicy.safe(target, req);
            
        } catch (AppException ex) {
            model.addAttribute("error", ex.getMessage()); 
            model.addAttribute("next", redirectPolicy.safe(next, req));
            model.addAttribute("email", email);
            return "auth/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {    	
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/products";
    }

  
}
