package com.myproject.qa.testing.framework.exceptions;

public class TimeoutException extends Exception {

	private static final long serialVersionUID = 3587539802896623789L;

	public TimeoutException(String message) {
		super(message);
	}

	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
}