package com.myproject.qa.testing.framework.selenium;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.myproject.qa.testing.framework.report.Reporter;
import com.myproject.qa.testing.framework.test.env.TestEnvironment;


public class BaseWebDriver {
	
	private static int waitTime;

	@BeforeSuite
	@Parameters({"env", "browser", "waitTime", "reportName"})
	public static void initSetup(@Optional("qa")String env, @Optional("chrome")String browser, @Optional("5")int waitPeriod, @Optional("TestReport")String reportName) throws Exception{
		waitTime = waitPeriod;
		Reporter.setReportName(reportName);
		TestEnvironment.setEnvConfigsTest(env);
		InitializeWebDriver.setDriver(browser);	 
	}
	
	@AfterSuite
	public static void quitDriver(){
		InstanceAccess.getDriver().quit();
	}
	
	@BeforeMethod
	@Parameters({"stabilityTime"})
	public static void waitForStability(@Optional("1")int stabilityTime) throws Exception{
		BrowserWait.waitUntilPageIsLoaded();
		Thread.sleep(stabilityTime*1000);
	}
	
	
	public static int getWaitTime() {
		return waitTime*10;
	}


	
	

}
