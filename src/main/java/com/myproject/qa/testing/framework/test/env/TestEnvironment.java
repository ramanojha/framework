package com.myproject.qa.testing.framework.test.env;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class TestEnvironment {

	private static String host;
	private static String siteUrl;
	private static String adminUrl;
	
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
			break;
			
		case "env2":
			host = "";
			siteUrl = "";
			adminUrl = "";
			break;
			
		default:
			host = "";
			siteUrl = "https://www.google.com/";
			adminUrl = "";
			break;
		}
	}
}
