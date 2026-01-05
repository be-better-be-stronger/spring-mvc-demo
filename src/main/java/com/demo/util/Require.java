package com.demo.util;

import com.demo.exception.BadRequestException;

public final class Require {
    private Require() {}

    public static <T> T notNull(T v, String name) {
        if (v == null) throw new BadRequestException(name + " is required");
        return v;
    }

    public static String notBlank(String v, String name) {
        if (v == null || v.isBlank()) throw new BadRequestException(name + " is required");
        return v;
    }

    public static Integer positive(Integer v, String name) {
        if (v == null) throw new BadRequestException(name + " is required");
        if (v <= 0) throw new BadRequestException(name + " must be > 0");
        return v;
    }

    public static Integer nonNegative(Integer v, String name) {
        if (v == null) throw new BadRequestException(name + " is required");
        if (v < 0) throw new BadRequestException(name + " must be >= 0");
        return v;
    }
}
