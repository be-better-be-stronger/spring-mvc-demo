package com.demo.exception;

public class BadRequestException extends AppException {
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) { super(400, message); }
    public BadRequestException(String message, Throwable cause) { super(400, message, cause); }
}
