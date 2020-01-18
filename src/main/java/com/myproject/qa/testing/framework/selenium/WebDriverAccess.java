package com.myproject.qa.testing.framework.selenium;

import org.openqa.selenium.WebDriver;

public class WebDriverAccess {
	
	public static WebDriver getDriver() {
		return InitializeWebDriver.getDriver();
	}
}
