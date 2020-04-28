package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class ApplicationException extends Exception{

	private static final long serialVersionUID = 1L;

	@Aim("To throw an Application Exception Message when a function is not working on application expectedly")
	public ApplicationException(String s){
		super(s);
		ScriptLogger.error(s);
	}
	
	@Aim("To throw an Application Exception message and Exception when a function is not working on application expectedly")
	public ApplicationException(Exception e,String s){
		super(s);
		ScriptLogger.error(e);
	}
}
