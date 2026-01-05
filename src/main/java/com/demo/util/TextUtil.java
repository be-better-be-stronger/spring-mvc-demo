package com.demo.util;

import com.demo.exception.BadRequestException;

public final class TextUtil {
    private TextUtil() {}

    // Chuẩn hóa: trim + collapse spaces
    public static String normalizeName(String name) {
    	if (name == null || name.isBlank()) throw new BadRequestException("name is required");       
        if (name.trim().length() > 100) throw new BadRequestException("name too long (max 100)");
        return name;
    }
}
