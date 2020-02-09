package com.myproject.qa.testing.framework.utils;

import java.io.IOException;
import java.io.InputStream;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class ShellUtils {
	public static Process run(String type, String command) throws IOException, InterruptedException {

		Runtime runtime = Runtime.getRuntime();
		switch(type) {
		case "CMD" :
			return runtime.exec(new String[] {command});
		case "CSH" :
			return runtime.exec(new String[]{"csh","-c",command});	
		case "BASH":
			return runtime.exec(new String[]{"bash","-c",command});
		case "ZSH":
			return runtime.exec(new String[]{"zsh","-c",command});
		}
		return null;
	}

	//Exceute shell command
	public static String execute(String type, String command) throws Exception {
		ScriptLogger.info("Entered command : "+command);
		Process process = run(type, command);
		process.waitFor();
		InputStream is = (process.exitValue() !=0) ? process.getErrorStream() : process.getInputStream();
		return commandLineLogs(FileUtils.convertStreamToString(is));
	}
	
	private static String commandLineLogs(String logs) {
		if(!logs.isEmpty()) 
			ScriptLogger.info(logs);
		return logs;
	}
}
