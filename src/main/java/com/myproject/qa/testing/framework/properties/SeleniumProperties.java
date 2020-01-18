package com.myproject.qa.testing.framework.properties;

public enum SeleniumProperties {
	
	CHROME_WEB_DRIVER_PATH("C:\\browserdrivers"),
	CHROME_WEB_DRIVER_EXE("webdriver.chrome.driver")
	;
	
	private final String value;
	SeleniumProperties(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
