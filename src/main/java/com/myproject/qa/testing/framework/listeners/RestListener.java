package com.myproject.qa.testing.framework.listeners;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ReportLogger;
import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.report.Reporter;
import com.myproject.qa.testing.framework.test.env.TestEnvironment;
import com.myproject.qa.testing.framework.utils.ListenerUtils;

public class RestListener implements ITestListener, ISuiteListener{
	
	public Map<String, List<ITestResult>> testResults = new LinkedHashMap<String, List<ITestResult>>();
	public List<ITestResult> results;
	String stabilityTime;
	private String suiteFileName;

	
	@Override
	@Aim("Perform setup actions when the suite is started.")
	public void onStart(ISuite suite) {
		suite.setAttribute("startTime", System.currentTimeMillis());
		TestEnvironment.setEnvConfigsTest(suite.getParameter("env"));
		stabilityTime = suite.getParameter("stabilityTime");
	}
	
	@Override
	@Aim("Perform PDF Report creation when Suite is Finished.")
	public void onFinish(ISuite suite) {
		suite.setAttribute("suiteFileName", suiteFileName);
		suite.setAttribute("endTime", System.currentTimeMillis());
		try {
			Reporter.setReportName((String)suite.getParameter("reportName"));
			createPDFArtifactForRestAPI(Reporter.reportName, testResults, suite);
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
	@Aim("On test start, create a new ArrayList.")
	public void onStart(ITestContext result) {
		results = new ArrayList<ITestResult>();
		suiteFileName = result.getCurrentXmlTest().getSuite().getFileName();
	}

	@Override
	@Aim("NA")
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	@Aim("On test fails, set certain attribute required for PDF login & add result.")
	public void onTestFailure(ITestResult result) {
		setAttributesToLogInPDF(result);
		result.setAttribute("exception", result.getThrowable().getMessage());
		result.setAttribute("stacktrace", result.getThrowable().getStackTrace());
		results.add(result);
		ReportLogger.setAllfieldsToNull();
	}

	@Override
	@Aim("On test skip, results are added.")
	public void onTestSkipped(ITestResult result) {
		setAttributesToLogInPDF(result);
		results.add(result);
		ReportLogger.setAllfieldsToNull();

	}

	@Override
	@Aim("On test start, application rest for stability period.")
	public void onTestStart(ITestResult result) {
		try {
			Thread.sleep(Integer.parseInt(stabilityTime)*1000);
		} catch(Exception e){
			e.printStackTrace();;
		}
	}

	@Override
	@Aim("On test success, adds it results and makes all field null")
	public void onTestSuccess(ITestResult result) {
		setAttributesToLogInPDF(result);
		results.add(result);
		ReportLogger.setAllfieldsToNull();
	}
	
	@Aim("To create PDF artifact with the help of test results of suite ran.")
	public void createPDFArtifactForRestAPI(String fileName, Map<String, List<ITestResult>> testResults, ISuite suite) throws Exception{
		ScriptLogger.info("ReportName :"+fileName);
		ListenerUtils.writePDFForRestReport(fileName, testResults, suite);
	}
	
	@Aim("To set the attribute which are required for PDF results")
	public void setAttributesToLogInPDF(ITestResult result){
		result.setAttribute("Request", ReportLogger.getRequest());
		result.setAttribute("Body", ReportLogger.getBody());
		result.setAttribute("Response", ReportLogger.getResponse());
		result.setAttribute("Response String", ReportLogger.getResposeString());
	}
	
}
