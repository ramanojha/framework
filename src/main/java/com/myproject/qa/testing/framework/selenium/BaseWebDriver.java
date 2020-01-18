package com.myproject.qa.testing.framework.selenium;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.myproject.qa.testing.framework.test.env.TestEnvironment;



public class BaseWebDriver {
	
	@BeforeSuite
	@Parameters({"env", "browser"})
	public static void intDriver(String env, String browser){
		TestEnvironment.setEnvConfigsTest(env);
		InitializeWebDriver.setDriver(browser);
		
		 
	}
	
	@AfterSuite
	public static void quitDriver(){
		WebDriverAccess.getDriver().quit();
	}
	
	@BeforeTest
	@Parameters({"thinkTime"})
	public static void waitForStability(int thinkTime) throws Exception{
		Thread.sleep(thinkTime*1000);
	}

	
	

}
