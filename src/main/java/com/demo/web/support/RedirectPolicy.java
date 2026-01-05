package com.demo.web.support;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RedirectPolicy {

    public String safe(String target, HttpServletRequest req) {
        if (target == null || target.isBlank()) return "/products";

        // reject absolute url
        if (target.contains("://") || target.startsWith("//")) {
            return "/products";
        }

        // chỉ cho phép path nội bộ
        if (!target.startsWith("/")) {
            return "/products";
        }

        // tránh loop login
        if (target.startsWith("/login")) {
            return "/products";
        }

        return target;
    }
}
//chặn open-redirect