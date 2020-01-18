package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class LocatorNotResolvedException extends Exception{
	private static final long serialVersionUID = 1L;

	public LocatorNotResolvedException(String message){
		super(message);
		ScriptLogger.error(message);
	}
	
	public LocatorNotResolvedException(Exception e, String message){
		super(message);
		ScriptLogger.error(e, message);
	}
}
