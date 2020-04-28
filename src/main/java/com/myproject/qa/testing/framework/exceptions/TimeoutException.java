package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;

public class TimeoutException extends Exception {

	private static final long serialVersionUID = 3587539802896623789L;

	@Aim("To throw an exception if script fails due to TimeOut")
	public TimeoutException(String message) {
		super(message);
	}

	@Aim("To throw an exception if script fails due to TimeOut")
	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
}