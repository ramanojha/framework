package com.myproject.qa.testing.framework.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class InstanceAccess {
	static WebDriver driver;
	static JavascriptExecutor jsDriver;
	static String browser;
	static int waitTime;
	
	static{
		 driver = InitializeWebDriver.getDriver();
		 jsDriver = (JavascriptExecutor)driver;
		 browser = InitializeWebDriver.getBrowser();
		 waitTime = BaseWebDriver.getWaitTime();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static JavascriptExecutor getJsDriver() {
		return jsDriver;
	}

	public static String getBrowser() {
		return browser;
	}

}
