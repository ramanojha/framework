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

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.selenium.BrowserAccess;
import com.myproject.qa.testing.framework.selenium.BrowserWait;
import com.myproject.qa.testing.framework.utils.FileUtils;
import com.myproject.qa.testing.framework.utils.ListenerUtils;

public class WebListener implements ITestListener, ISuiteListener{
	
	public Map<String, List<ITestResult>> testResults = new LinkedHashMap<String, List<ITestResult>>();
	public List<ITestResult> results;
	String stabilityTime;
	private String suiteFileName;

	
	@Override
	@Aim("On Suite start, some set up is done.")
	public void onStart(ISuite suite) {
		suite.setAttribute("startTime", System.currentTimeMillis());
		stabilityTime = suite.getParameter("stabilityTime");
	}
	
	@Override
	@Aim("On Suite Finish, PDF Report is created")
	public void onFinish(ISuite suite) {
		
		suite.setAttribute("suiteFileName", suiteFileName);
		suite.setAttribute("endTime", System.currentTimeMillis());
		try {
			createPDFArtifact(FileUtils.getPdfArtifactName(ListenerUtils.getReportName(suiteFileName)), testResults, suite);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	@Aim("On test finish, adds test name and its results in testResults")
	public void onFinish(ITestContext result){
		testResults.put(result.getCurrentXmlTest().getName(), results);
	}

	@Override
	@Aim("On test start, get Suite file Name")
	public void onStart(ITestContext result) {
		results = new ArrayList<ITestResult>();
		suiteFileName = result.getCurrentXmlTest().getSuite().getFileName();

	}

	@Override
	@Aim("NA")
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	@Aim("On test failure, screenshot of page, page titles, and required attribute are set")
	public void onTestFailure(ITestResult result) {
		try {
			result.setAttribute("pageTitles",BrowserAccess.getPageTitlesInString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setAttribute("screenshot", getScreenShot());
		result.setAttribute("exception", result.getThrowable().getMessage());
		result.setAttribute("stacktrace", result.getThrowable().getStackTrace());
		results.add(result);
	}

	@Override
	@Aim("On test skip, skipped results are added.")
	public void onTestSkipped(ITestResult result) {
		results.add(result);

	}

	@Override
	@Aim("On test step start, the stability period is setup")
	public void onTestStart(ITestResult result) {
		try {
			if(BrowserAccess.getDriver() !=null)
				BrowserWait.waitUntilPageIsLoaded();

			Thread.sleep(Integer.parseInt(stabilityTime)*1000);
		} catch(Exception e){
			e.printStackTrace();;
		}
	}

	@Override
	@Aim("On test success, results are added in arraylist")
	public void onTestSuccess(ITestResult result) {
		results.add(result);
	}


	@Aim("To take screenshot and return byte[]")
	public byte[] getScreenShot(){
		if(BrowserAccess.getDriver() == null){
			return null;
		}else{
			TakesScreenshot ss = (TakesScreenshot) BrowserAccess.getDriver();
			return ss.getScreenshotAs(OutputType.BYTES);
		}	
	}
	
	@Aim("To create PDF Results based on testResults")
	public void createPDFArtifact(String fileName, Map<String, List<ITestResult>> testResults, ISuite suite) throws Exception{
		ScriptLogger.info("ReportName :"+fileName);
		ListenerUtils.writePDF(fileName, testResults, suite);
	}
	
}
