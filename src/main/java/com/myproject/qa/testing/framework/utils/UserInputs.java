package com.myproject.qa.testing.framework.utils;
import groovy.util.ScriptException;

import java.util.Scanner;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class UserInputs {
	static Scanner sc = new Scanner(System.in);
	public static String takeInput(String message) throws Exception {
		ScriptLogger.info();
		ScriptLogger.info(message);
		String inputTaken;
		try {
			inputTaken = sc.nextLine();
			ScriptLogger.info("You entered : "+inputTaken);
		} catch (Exception e) {
			throw new ScriptException("Could not take user input");
		}
		return inputTaken;
	}
}
