package com.demo.exception;

public class ConflictException extends AppException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String message) {
		super(409, message);
	}

	public ConflictException(String message, Throwable cause) {
		super(409, message, cause);
	}

}
