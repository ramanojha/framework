package com.myproject.qa.testing.framework.rest.api;

import java.io.IOException;

import com.myproject.qa.testing.framework.logs.ReportLogger;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BuildHttpOkCalls {
	static final MediaType JSON = MediaType.get("application/json; charset=utf-8") ; //MediaType.get("application/json; charset=utf-8");
	static OkHttpClient CLIENT = new OkHttpClient();

	//switch request
	private static Request swtichRequest(String type, Builder builder, String json) {
		switch(type) {
		case "POST" :
			return builder.post(jsonBody(json)).build();
		case "PUT" :
			return builder.put(jsonBody(json)).build();
		case "DELETE" :
			return (json !=null) ? builder.delete(jsonBody(json)).build() : builder.delete().build();	
		case "GET" :
			return builder.get().build();
		}
		return null;
	}
	
	// Request body
	public static RequestBody jsonBody(String json) {
		RequestBody body = RequestBody.create(JSON, json);
		ReportLogger.setBody(body.toString());
		return body;
	}

	//url with single key value
	//Pass keyValue Pipe Saperated
	public static String url(String url, String... keysValuesPipeSaperated) {
		okhttp3.HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
		for(String keyValue : keysValuesPipeSaperated){
			builder.addQueryParameter(keyValue.split("\\|")[0], keyValue.split("\\|")[1]);
		}
		return (keysValuesPipeSaperated.length >0) ?builder.build().toString(): url;
	}

	//return response
	public static Response reponse(Request request) throws IOException {
		return CLIENT.newCall(request).execute();
	}

	//Request
	public static Request request(String type, String url, String json, String... headersValuesPipeSaperated) {
		okhttp3.Request.Builder builder = new Request.Builder();
		for(String headValue : headersValuesPipeSaperated){
			builder = builder.header(headValue.split("\\|")[0], headValue.split("\\|")[0]);
		}
		builder = builder.url(url);
		return swtichRequest(type, builder, json);
	}
	
}
