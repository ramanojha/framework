package com.myproject.qa.testing.framework.rest.api;

import okhttp3.Request;
import okhttp3.Response;

import com.myproject.qa.testing.framework.exceptions.InvalidRequestException;
import com.myproject.qa.testing.framework.exceptions.InvalidResponseException;
import com.myproject.qa.testing.framework.logs.ReportLogger;
import com.myproject.qa.testing.framework.utils.ToString;

public class API {
	
	public static String url(String url, String... keysValuesPipeSaperated) {
		url = BuildHttpOkCalls.url(url, keysValuesPipeSaperated);
		ReportLogger.setUrl(url);
		return url;
	}
	
	public static Request get(String url, String... headersValuesPipeSaperated) throws Exception {
		Request request;
		try {
			request = BuildHttpOkCalls.request("GET", url, null, headersValuesPipeSaperated);
		} catch (Exception e) {
			String headersValues = (headersValuesPipeSaperated.length>0)? ToString.arrayTOString(headersValuesPipeSaperated) :"None";
			throw new InvalidRequestException(e, "GET, URL : "+url+" HeaderValues: "+headersValues);
		}
	   	ReportLogger.setRequest(request.toString());
    	return request;
	}

	public static Request post(String url, String bodyJson, String... headersValuesPipeSaperated) throws Exception {
		Request request;
		try {
			request = BuildHttpOkCalls.request("POST", url, bodyJson, headersValuesPipeSaperated);
		} catch (Exception e) {
			String headersValues = (headersValuesPipeSaperated.length>0)? ToString.arrayTOString(headersValuesPipeSaperated) :"None";
			throw new InvalidRequestException(e, "POST, URL : "+url+" HeaderValues: "+headersValues);
		}
		ReportLogger.setRequest(request.toString());
		return request;
	}
	
	public static Request put(String url, String bodyJson, String... headersValuesPipeSaperated) throws Exception {
		Request request;
		try {
			request = BuildHttpOkCalls.request("PUT", url, bodyJson, headersValuesPipeSaperated);
		} catch (Exception e) {
			String headersValues = (headersValuesPipeSaperated.length>0)? ToString.arrayTOString(headersValuesPipeSaperated) :"None";
			throw new InvalidRequestException(e, "PUT, URL : "+url+" HeaderValues: "+headersValues);

		}
		ReportLogger.setRequest(request.toString());
		return request;
	}
	
	public static Request delete(String url, String... headersValuesPipeSaperated) throws Exception {
		Request request;
		try {
			request = BuildHttpOkCalls.request("DELETE", url, null, headersValuesPipeSaperated);
		} catch (Exception e) {
			String headersValues = (headersValuesPipeSaperated.length>0)? ToString.arrayTOString(headersValuesPipeSaperated) :"None";
			throw new InvalidRequestException(e, "GET, URL : "+url+" HeaderValues: "+headersValues);
		}
		ReportLogger.setRequest(request.toString());
		return request;
	}
	
	public static Request delete(String url, String bodyJson, String... headersValuesPipeSaperated) throws Exception {
		Request request;
		try {
			request = BuildHttpOkCalls.request("DELETE", url, bodyJson, headersValuesPipeSaperated);
		} catch (Exception e) {
			String headersValues = (headersValuesPipeSaperated.length>0)? ToString.arrayTOString(headersValuesPipeSaperated) :"None";
			throw new InvalidRequestException(e, "DELETE, URL : "+url+" HeaderValues: "+headersValues);

		}
		ReportLogger.setRequest(request.toString());
		return request;
	}
	
	public static Response reponse(Request request) throws Exception {
		Response response;
		try {
			response = BuildHttpOkCalls.reponse(request);
		} catch (Exception e) {
			throw new InvalidResponseException(e, "Invalid Response");
		}
		ReportLogger.setResponse(response.toString());
		ReportLogger.setResposeString(response.body().string());
		return response;
	}
}
