package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class LocatorNotResolvedException extends Exception{
	private static final long serialVersionUID = 1L;

	@Aim("To throw an exception when Locator is not resolved.")
	public LocatorNotResolvedException(String message){
		super(message);
		ScriptLogger.error(message);
	}
	
	@Aim("To throw an exception when Locator is not resolved.")
	public LocatorNotResolvedException(Exception e, String message){
		super(message);
		ScriptLogger.error(e, message);
	}
}
