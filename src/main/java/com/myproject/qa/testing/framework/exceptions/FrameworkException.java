package com.myproject.qa.testing.framework.exceptions;

public class FrameworkException extends RuntimeException {
	
	private static final long serialVersionUID = 5663296634599954510L;

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}
}