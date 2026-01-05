package com.demo.web.util;

import jakarta.servlet.http.HttpServletRequest;

public final class Urls {
    private Urls() {}

    public static String fullPathWithQuery(HttpServletRequest req) {
    	String uri = req.getRequestURI();      // /spring-mvc/admin/products
        String ctx = req.getContextPath();     // /spring-mvc

        // bỏ contextPath
        if (uri.startsWith(ctx)) {
            uri = uri.substring(ctx.length()); // /admin/products
        }

        String qs = req.getQueryString();
        return (qs == null || qs.isBlank()) ? uri : uri + "?" + qs;
    }
    
    public static String backUrl(HttpServletRequest req, String sessionKey, String fallback) {
        Object v = req.getSession(false) == null
                ? null
                : req.getSession(false).getAttribute(sessionKey);

        if (v == null) {
            return req.getContextPath() + fallback;
        }

        String url = v.toString();
        return (url.isBlank())
                ? req.getContextPath() + fallback
                : url;
    }
}
