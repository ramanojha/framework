package com.myproject.qa.testing.framework.exceptions;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class HTMLElementNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Aim("To throw an HTML Element Not Found Exception when a HTML Element is not working/displayed/changed on application expectedly")
	public HTMLElementNotFoundException(Exception e, String message){
		super(message);
		ScriptLogger.error(e);
	}
}
