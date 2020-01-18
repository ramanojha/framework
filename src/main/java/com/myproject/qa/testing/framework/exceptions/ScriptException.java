package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.logs.ScriptLogger;


public class ScriptException extends Exception{

	private static final long serialVersionUID = 1L;

	public ScriptException(Exception e) {
		super("Automated Script Exception-Please check error log");
		ScriptLogger.error(e);
	}

	public ScriptException(String message){
		super("Automated Script Exception-Please check error log");
		ScriptLogger.error(new Exception(message));
	}
}
