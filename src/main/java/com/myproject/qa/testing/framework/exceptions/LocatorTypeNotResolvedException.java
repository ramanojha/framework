package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class LocatorTypeNotResolvedException extends Exception{
	private static final long serialVersionUID = 1L;

	@Aim("To throw an exception when Locator type is not resolved due to no suffix ex. _XPATH, _NAME, _ID etc.")
	public LocatorTypeNotResolvedException(String message){
		super(message);
		ScriptLogger.error(message);
	}
	
}
