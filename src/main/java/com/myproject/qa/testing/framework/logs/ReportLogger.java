package com.myproject.qa.testing.framework.logs;

public class ReportLogger {

	private static String request;
	private static String body;
	private static String response;
	private static String url;
	private static String resposeString;
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String seturl) {
		url = seturl;
		ScriptLogger.info("Url		:"+url);
	}
	public static String getRequest() {
		return request;
	}
	public static void setRequest(String req) {
		request = req;
		ScriptLogger.info("Request	:"+request);
	}
	public static String getBody() {
		return body;
	}
	public static void setBody(String bodyy) {
		body = bodyy;
		ScriptLogger.info("Body		:"+body);
	}
	public static String getResponse() {
		return response;
	}
	public static void setResponse(String resp) {
		response = resp;
		ScriptLogger.info("Response	:"+resp);
	}
	public static void setAllfieldsToNull() {
		url = null;
		request = null;
		response = null;
		resposeString = null;
		body = null;
		
	}

	public static String getResposeString() {
		return resposeString;
	}
	public static void setResposeString(String resposeString) {
		ReportLogger.resposeString = resposeString;
		ScriptLogger.info("ResponseString	:"+resposeString);
	}

}
