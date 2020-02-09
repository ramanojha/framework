package com.myproject.qa.testing.framework.exceptions;

public class FrameworkException extends RuntimeException {
	
	private static final long serialVersionUID = 5663296634599954510L;

	public FrameworkException(Exception e) {
		super(e);
	}

	public FrameworkException(Throwable cause, String message) {
		super(message, cause);
	}
}