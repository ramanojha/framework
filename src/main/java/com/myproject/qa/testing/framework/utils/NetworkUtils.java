package com.myproject.qa.testing.framework.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.myproject.qa.testing.framework.exceptions.ApplicationException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class NetworkUtils {
	public static String getCountryByIpTest() throws Exception {
		String IPAddress = "";
		try (@SuppressWarnings("resource")
		Scanner s = new Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
			IPAddress = s.next();
		} catch (IOException e) {
			e.printStackTrace();
		}
		URL myURL = new URL("https://ipinfo.io/"+IPAddress);
		HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();

		myURLConnection.setRequestMethod("GET");
		myURLConnection.setRequestProperty("Accept", "application/json");
		String jsonResponse = FileUtils.convertStreamToString(myURLConnection.getInputStream());
		System.out.println(jsonResponse);

		String country = jsonResponse.substring(jsonResponse.indexOf("country")+10, jsonResponse.indexOf("loc")-1).replace("\"", "").replace(",", "");
		ScriptLogger.info(country);
		return country;
	}
	
	public static int getLinkStatus(String link) throws Exception {
		URL url = new URL(link.trim());
		HttpURLConnection connection =(HttpURLConnection)url.openConnection();
		connection.setRequestProperty("Content-Type", "text/html; charset=utf-8");
		int status = 0;
		try {
			connection.connect();
			status = connection.getResponseCode();
			connection.disconnect();
			return status;
		} 
		catch (Exception e) {	
			ScriptLogger.info("Link Not working : "+status+" "+link);
			throw new ApplicationException("Link Not working : "+status+" "+link);
		}
	}
}
