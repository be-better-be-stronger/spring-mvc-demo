package com.demo.exception;

public abstract class AppException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;
	
	private final int status;

    protected AppException(int status, String message) {
        super(message);
        this.status = status;
    }

    protected AppException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

