package com.myproject.qa.testing.framework.listeners;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.report.Reporter;
import com.myproject.qa.testing.framework.selenium.BrowserAccess;
import com.myproject.qa.testing.framework.selenium.BrowserWait;
import com.myproject.qa.testing.framework.utils.PDFUtils;

public class WebListener implements ITestListener, ISuiteListener{
	
	public Map<String, List<ITestResult>> testResults = new LinkedHashMap<String, List<ITestResult>>();
	public List<ITestResult> results;
	String stabilityTime;

	
	@Override
	public void onStart(ISuite suite) {
		ScriptLogger.info();
		stabilityTime = suite.getParameter("stabilityTime");

	}
	
	@Override
	public void onFinish(ISuite suite) {
		try {
			createPDFArtifact(Reporter.reportName, testResults);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onFinish(ITestContext result){
		testResults.put(result.getCurrentXmlTest().getName(), results);
	}

	@Override
	public void onStart(ITestContext result) {
		results = new ArrayList<ITestResult>();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailure(ITestResult result) {
		result.setAttribute("screenshot", getScreenShot());
		result.setAttribute("exception", result.getThrowable().getMessage());
		result.setAttribute("stacktrace", result.getThrowable().getStackTrace());
		results.add(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		results.add(result);

	}

	@Override
	public void onTestStart(ITestResult result) {
		ScriptLogger.info();
		try {
			if(BrowserAccess.getDriver() !=null)
				BrowserWait.waitUntilPageIsLoaded();

			Thread.sleep(Integer.parseInt(stabilityTime)*1000);
		} catch(Exception e){
			ScriptLogger.info();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		results.add(result);
	}



	public byte[] getScreenShot(){
		if(BrowserAccess.getDriver() == null){
			return null;
		}else{
			TakesScreenshot ss = (TakesScreenshot) BrowserAccess.getDriver();
			return ss.getScreenshotAs(OutputType.BYTES);
		}	
	}
	
	public void createPDFArtifact(String fileName, Map<String, List<ITestResult>> testResults) throws Exception{
		ScriptLogger.info("ReportName :"+fileName);
		PDFUtils.writePDF(fileName, testResults);
	}
	

}
