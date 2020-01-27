package com.myproject.qa.testing.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class FileUtils {
	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static boolean isDirectoryEmpty(String path){
		 File directory = new File(path);
	      if (directory.isDirectory()) {
	         String[] files = directory.list();
	         if (files.length == 0) 
	        	 return true;
	         else 
	        	 return false;
	      }
	      else{
	    	  ScriptLogger.info("Not a directory "+path);
	    	  return false;
	      }
	}
	
	public static boolean isFile(String path){
		 File file = new File(path);
	     if(file.isFile())
	    	 return true;
	      else{
	    	  return false;
	      }
	}
	
	public static String getMavenProjectPath(String projectName){
		String path = System.getProperty("user.dir");
		return path.substring(0, path.lastIndexOf("\\")+1)+projectName;		
	}
	
	public static boolean ifFileExist(String filepath) throws Exception {
		return (new File(filepath)).exists();
	}

	public static boolean deleteFile(String filepath) throws Exception {
		return (new File(filepath)).delete();
	}
}


