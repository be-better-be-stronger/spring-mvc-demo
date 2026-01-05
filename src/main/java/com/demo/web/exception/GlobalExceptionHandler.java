package com.demo.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.exception.AppException;
import com.demo.exception.ForbiddenException;
import com.demo.security.SessionKeys;
import com.demo.web.util.Urls;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public String handleHttp(AppException ex, HttpServletRequest req) {
    	HttpSession session = req.getSession(true);

        // 1) 403: không redirect về back url (tránh loop / redirect vào vùng cấm)
        if (ex instanceof ForbiddenException) {
            req.setAttribute("status", 403);
            req.setAttribute("message", ex.getMessage());
            session.setAttribute(SessionKeys.AFTER_LOGIN_URL, Urls.fullPathWithQuery(req));
            return "error/403"; // /WEB-INF/views/error-403.jsp
            // hoặc return "redirect:/login";
        }

        // 2) 4xx nghiệp vụ: flash + redirect
        session.setAttribute(SessionKeys.FLASH_ERR, ex.getMessage());

        String target = resolveSafeRedirectTarget(req, session);

        return "redirect:" + target;
    }

    @ExceptionHandler(Exception.class)
    public String handleUnknown(Exception ex, HttpServletRequest req) {
    	ex.printStackTrace();
        // 3) 5xx hệ thống: render error.jsp
        req.setAttribute("status", 500);
        req.setAttribute("message", "Unexpected error.");
        return "error/500"; // /WEB-INF/views/error/error.jsp
    }

    // ===== enterprise redirect strategy =====
    private String resolveSafeRedirectTarget(HttpServletRequest req, HttpSession session) {

        boolean adminZone = isAdminZone(req);
        boolean adminUser = isAdminUser(req);

        // A) nếu đang ở admin zone
        if (adminZone) {
            // admin user: ưu tiên back url admin (đã lưu từ list) nếu an toàn
            if (adminUser) {
                String back = safeInternalPath((String) session.getAttribute(SessionKeys.ADMIN_PRODUCTS_LIST_URL));

                return (back != null) ? back : "/admin/products"; 
            }
            // không phải admin mà chui vào admin zone -> đưa ra ngoài
            return "/products"; // hoặc "/login"
        }

        // B) nếu đang ở user zone
        String back = safeInternalPath((String) session.getAttribute(SessionKeys.USER_PRODUCTS_LIST_URL));
        
        return (back != null && !back.startsWith("/admin")) ? back : "/products";
    }

    // Request hiện tại thuộc zone admin?
    private boolean isAdminZone(HttpServletRequest req) {
        // dùng URI đã bỏ contextPath
        String path = req.getRequestURI().substring(req.getContextPath().length());
        return path.startsWith("/admin");
    }

    // Check role admin (mày thay đúng theo app mày)
    private boolean isAdminUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s == null) return false;
        String role = (String)s.getAttribute(SessionKeys.AUTH_ROLE);
        return (role == null) ? false : "ADMIN".equalsIgnoreCase(role);
    }

    // Chặn open-redirect + chỉ nhận path nội bộ
    private String safeInternalPath(String p) {
        if (p == null) return null;
        p = p.trim();
        if (p.isEmpty()) return null;
        if (!p.startsWith("/")) return null;
        if (p.startsWith("//")) return null;
        if (p.contains("\r") || p.contains("\n")) return null;
        return p;
    }
}
