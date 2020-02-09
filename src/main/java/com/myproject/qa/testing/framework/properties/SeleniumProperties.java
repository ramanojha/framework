package com.myproject.qa.testing.framework.properties;

public enum SeleniumProperties {

	WGET_EXE_PATH("");
	;
	
	private final String value;
	SeleniumProperties(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
