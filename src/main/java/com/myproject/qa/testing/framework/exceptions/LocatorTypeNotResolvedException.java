package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class LocatorTypeNotResolvedException extends Exception{
	private static final long serialVersionUID = 1L;

	public LocatorTypeNotResolvedException(String message){
		super(message);
		ScriptLogger.error(message);
	}
	
}
