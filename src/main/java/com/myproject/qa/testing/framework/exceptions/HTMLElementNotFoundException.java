package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class HTMLElementNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public HTMLElementNotFoundException(Exception e, String message){
		super(message);
		ScriptLogger.error(e);
	}
}
