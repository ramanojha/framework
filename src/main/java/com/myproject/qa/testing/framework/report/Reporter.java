package com.myproject.qa.testing.framework.report;

public class Reporter {
	public static String reportName;
	

	public static void setReportName(String reportName1) {
		reportName =  System.getProperty("user.dir")+"\\target\\"+reportName1+".pdf";
	}

	
	
}
