package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class InvalidResponseException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Aim("To throw an Invalid Response Exception when a invalid response is sent")
	public InvalidResponseException(String s){
		super(s);
		ScriptLogger.error(s);
	}
	
	@Aim("To throw an Invalid Response Exception when a invalid response is sent")
	public InvalidResponseException(Exception e,String message){
		super(message);
		ScriptLogger.error(e, message);
	}
}
