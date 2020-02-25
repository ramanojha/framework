package com.myproject.qa.testing.framework.test.env;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class TestEnvironment {

	private static String host;
	private static String siteUrl;
	private static String adminUrl;
	private static String serviceHost1;
	private static String serviceHost2;
	private static String serviceHost3;
	private static String serviceHost4;
	
	public static String getServiceHost1() {
		return serviceHost1;
	}

	public static String getServiceHost2() {
		return serviceHost2;
	}

	public static String getServiceHost3() {
		return serviceHost3;
	}

	public static String getServiceHost4() {
		return serviceHost4;
	}

	public static String getSiteUrl() {
		ScriptLogger.info("url "+siteUrl);
		return siteUrl;
	}
	
	public static String getHost() {
		ScriptLogger.info("url "+host);
		return host;
	}
	
	public static String getAdminUrl() {
		ScriptLogger.info("url "+adminUrl);
		return adminUrl;
	}

	public static void setEnvConfigsTest(String env){
		switch (env) {
		case "env1":
			host = "";
			siteUrl = "";
			adminUrl = "";
			serviceHost1 = "";
			serviceHost2 = "";
			serviceHost3 = "";
			serviceHost4 = "";
			break;
			
		case "env2":
			host = "";
			siteUrl = "";
			adminUrl = "";
			serviceHost1 = "";
			serviceHost2 = "";
			serviceHost3 = "";
			serviceHost4 = "";
			break;
			
		default:
			host = "";
			siteUrl = "https://www.google.com/";
			adminUrl = "";
			serviceHost1 = "https://reqres.in";
			serviceHost2 = "http://dummy.restapiexample.com";
			serviceHost3 = "";
			serviceHost4 = "";
			break;
		}
	}
}
