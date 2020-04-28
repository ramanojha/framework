package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;

public class FrameworkException extends RuntimeException {
	
	private static final long serialVersionUID = 5663296634599954510L;
	@Aim("To throw an Framework Exception when a function is not working on application expectedly")
	public FrameworkException(Exception e) {
		super(e);
	}
	@Aim("To throw an Framework Exception Message and Exception when a function is not working on application expectedly")
	public FrameworkException(Exception e, String message) {
		super(message, e);
	}
}