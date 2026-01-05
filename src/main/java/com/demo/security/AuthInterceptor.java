package com.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import com.demo.exception.ForbiddenException;
import com.demo.web.session.PendingAddToCart;

public class AuthInterceptor implements HandlerInterceptor {

    private boolean redirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	
    	HttpSession s = req.getSession(true);
    	String method = req.getMethod();
        String path = req.getRequestURI().substring(req.getContextPath().length()); // "/cart/add" ...

        // nếu có query string thì gắn vào path (không có contextPath)
        String qs = req.getQueryString();
        String fullPath = (qs == null || qs.isBlank()) ? path : (path + "?" + qs);

        // ===== CASE: POST add-to-cart =====
        if ("POST".equalsIgnoreCase(method) && "/cart/add".equals(path)) {
            // lấy data từ form
            int productId = Integer.parseInt(req.getParameter("productId"));
            int qty = Integer.parseInt(req.getParameter("qty"));
            s.setAttribute(SessionKeys.PENDING_ADD_TO_CART, new PendingAddToCart(productId, qty));
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        
     // ===== CASE: POST place-order =====
        if ("POST".equalsIgnoreCase(method) && "/orders/place".equals(path)) {
            s.setAttribute(SessionKeys.PENDING_PLACE_ORDER, "flag");
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }

        // ===== CASE: request GET page bình thường =====
        s.setAttribute(SessionKeys.AFTER_LOGIN_URL, fullPath);
        resp.sendRedirect(req.getContextPath() + "/login");
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        HttpSession session = req.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute(SessionKeys.AUTH_USER_ID);
        String role = (session == null) ? null : (String) session.getAttribute(SessionKeys.AUTH_ROLE);

        // 1) Chưa login
        if (userId == null) {
            return redirectToLogin(req, resp);
        }

        // 2) Check role theo path (ví dụ)
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // /admin/** chỉ cho ADMIN
        if (path.startsWith("/admin")) {
            if (!"ADMIN".equalsIgnoreCase(role)) throw new ForbiddenException("Access denied!");
        }

        return true;
    }
}
