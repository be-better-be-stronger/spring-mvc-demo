package com.demo.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class Redirects {

    private Redirects() {}

    /** 
     * Redirect về URL đã lưu trong sessionKey; nếu không có thì fallbackPath.
     * sessionKey lưu dạng path + query, KHÔNG bao gồm contextPath.
     */
    public static String back(HttpServletRequest req, String sessionKey, String fallbackPath) {
        HttpSession session = req.getSession(false);

        String back = (session == null) ? null : (String) session.getAttribute(sessionKey);
        if (back == null || back.isBlank()) back = fallbackPath;

        // đảm bảo fallback có leading slash
        if (!back.startsWith("/")) back = "/" + back;

        return "redirect:" + back;
    }
}
