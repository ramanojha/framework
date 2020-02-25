package com.myproject.qa.testing.framework.utils;

public class ToString {

	public static String arrayTOString(String[] arr){
		String arrStr = "";
		for(String key : arr){
			 arrStr += key+", ";
		}
		return arrStr.substring(0, arrStr.length()-2);
	}
}
