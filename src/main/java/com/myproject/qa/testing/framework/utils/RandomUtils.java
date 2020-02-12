package com.myproject.qa.testing.framework.utils;

import java.security.SecureRandom;

import com.myproject.qa.testing.framework.exceptions.ScriptException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class RandomUtils {

	public static String generateRandomMobileNumberTest() throws Exception {
		ScriptLogger.info();
		try {
			long number = (long) Math.floor(Math.random() * 1_000_000_000L) + 9_000_000_000L;
			return Long.toString(number);
		}catch(Exception e) {
			throw new ScriptException(e,"Unable to generate random 10 digit mobile number.");
		}
	}


	public static String generateRandomEmailTest(String serverUrl) throws Exception {
		ScriptLogger.info();
		try {
			return "Email"+generateRandomString(5)+serverUrl;
		} catch (Exception e) {
			throw new ScriptException(e, "Unable to enter email in textbox name");
		}
	}


	public static String enterRandomPhoneTest() throws Exception {
		ScriptLogger.info();
		try {
			long number= generateRandomNumber(10);
			return String.valueOf(number);
		} catch (Exception e) {
			throw new ScriptException(e,"Unable to enter email in textbox name");
		}
	}

	public static String generateRandomString(int len) {

		String alphaNum = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(alphaNum.charAt(rnd.nextInt(alphaNum.length())));
		}
		return sb.toString();
	}
	
	public static long generateRandomNumber(int len) {
		String alphaNum = "0123456789";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(alphaNum.charAt(rnd.nextInt(alphaNum.length())));
		}
		return Long.parseLong(sb.toString());

	}
}
