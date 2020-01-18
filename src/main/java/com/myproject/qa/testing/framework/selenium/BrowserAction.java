package com.myproject.qa.testing.framework.selenium;


public class BrowserAction {

	public static void enterField(Object locator,String value) throws Exception {
		LocatorAccess.getElement(locator).sendKeys(value);	
	}

}
