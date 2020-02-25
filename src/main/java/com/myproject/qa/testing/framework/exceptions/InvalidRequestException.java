package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class InvalidRequestException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String s){
		super(s);
		ScriptLogger.error(s);
	}
	
	public InvalidRequestException(Exception e,String s){
		super(s);
		ScriptLogger.error(e,s);
	}
}
