package com.demo.exception;

public class ForbiddenException extends AppException {
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
		super(403, message);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(403, message, cause);
	}
}
