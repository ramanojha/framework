package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class ApplicationException extends Exception{

	private static final long serialVersionUID = 1L;

	public ApplicationException(String s){
		super(s);
		ScriptLogger.error(s);
	}
	
	public ApplicationException(Exception e,String s){
		super(s);
		ScriptLogger.error(e);
	}
}
