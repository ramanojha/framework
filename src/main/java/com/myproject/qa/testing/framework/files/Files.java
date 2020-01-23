package com.myproject.qa.testing.framework.files;

import java.io.File;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class Files {

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
}
