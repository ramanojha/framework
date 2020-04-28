package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;


public class ScriptException extends Exception{

	private static final long serialVersionUID = 1L;

	@Aim("To throw an exception if script fails due to multiple steps")
	public ScriptException(Exception e) {
		super("Automated Script Exception-Please check error log");
		ScriptLogger.error(e);
	}
	
	@Aim("To throw an exception if script fails due to multiple steps")
	public ScriptException(String message){
		super("Automated Script Exception-Please check error log");
		ScriptLogger.error(new Exception(message));
	}
	
	@Aim("To throw an exception if script fails due to multiple steps")
	public ScriptException(Exception e, String message){
		super("Automated Script Exception-Please check error log");
		ScriptLogger.error(new Exception(message));
		ScriptLogger.error(e);
	}
}
