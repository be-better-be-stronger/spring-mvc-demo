package com.demo.exception;

public class NotFoundException extends AppException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(404, message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(404, message, cause);
	}
}
