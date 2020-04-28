package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class InvalidRequestException extends Exception{

	private static final long serialVersionUID = 1L;

	@Aim("To throw an Invalid Request Exception message when a invalid request is sent")
	public InvalidRequestException(String s){
		super(s);
		ScriptLogger.error(s);
	}
	@Aim("To throw an Invalid Request Exception when a invalid request is sent")
	public InvalidRequestException(Exception e,String s){
		super(s);
		ScriptLogger.error(e,s);
	}
}
