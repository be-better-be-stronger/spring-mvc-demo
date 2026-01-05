package com.demo.security;

import com.demo.exception.ForbiddenException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthRequests {
	 private AuthRequests() {}

	    public static Integer getUserId(HttpServletRequest req) {
	        HttpSession ss = req.getSession(false);
	        Integer v = (ss == null) ? null : (Integer)ss.getAttribute(SessionKeys.AUTH_USER_ID);
	        if(v == null) throw new ForbiddenException("Login required!");
	        return v;
	    }
}
