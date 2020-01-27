package com.myproject.qa.testing.framework.logs;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;

import com.myproject.qa.testing.framework.utils.FileUtils;

public class ScriptLogger {
		
		public static Logger logger = Logger.getLogger(ScriptLogger.class);
		
		static{
			setHostNameInLog4jProperties();
			PropertyConfigurator.configure(FileUtils.getMavenProjectPath("framework")+"\\resources\\log4j.properties");
			logger = Logger.getLogger(ScriptLogger.class);
			//logger.info("Test Running on.. - "+System.getProperty("hostName").split("\\.")[0]);
		}
		
		public static void info(){
			logger.setLevel(Level.INFO);
			logger.info(getClassMethodInfo());
		}

		public static void info(String message){
			logger.setLevel(Level.INFO);
			logger.info(getClassMethodInfo()+" - "+message);
		}
		
		public static void fatal(){
			logger.setLevel(Level.FATAL);
			logger.fatal(getClassMethodInfo());
		}

		public static void fatal(String message){
			logger.setLevel(Level.FATAL);
			logger.fatal(getClassMethodInfo()+" - "+message);
		}
		
		public static void warn(){
			logger.setLevel(Level.WARN);
			logger.warn(getClassMethodInfo());
		}

		public static void warn(String message){
			logger.setLevel(Level.WARN);
			logger.warn(getClassMethodInfo()+" - "+message);
		}
		
		public static void debug(){
			logger.setLevel(Level.DEBUG);
			logger.debug(getClassMethodInfo());
		}

		public static void debug(String message){
			logger.setLevel(Level.DEBUG);
			logger.debug(getClassMethodInfo()+" - "+message);
		}

		public static void error(Exception e){
			logger.setLevel(Level.ERROR);
			StringBuffer buffer=new StringBuffer("\n\n"+e.getClass().getName()+":"+e.getMessage()+"\n");
			for (StackTraceElement ste : e.getStackTrace()) {
				buffer.append(ste.toString()+"\n");
			}
			logger.error(buffer.toString());
		}
		
		public static void error(String message){
			logger.setLevel(Level.ERROR);
			logger.error(getClassMethodInfo()+" - "+message);
		}
		
		public static void error(Exception e,String message){
			error(message);
			error(e);
			
		}
		
		private static String getClassMethodInfo() {

			String s="";
			final StackTraceElement[] ste=Thread.currentThread().getStackTrace();

			String classname=ste[3].getClassName();
			String methodname=ste[3].getMethodName();
			int lineNo = ste[3].getLineNumber();

			s=classname+":"+methodname+"() #"+lineNo;
			return s;
		}

		//Fetching HostName and setting HostName in Log4j.properties
		private static void setHostNameInLog4jProperties() {
			try {
				System.setProperty("hostName", InetAddress.getLocalHost().getHostName());
				MDC.put("hostName", System.getProperty("hostName").split("\\.")[0]);
			}
			catch (UnknownHostException e) {
				ScriptLogger.info("Host name in Log4j is not replaced as per user system");
				e.printStackTrace();
			}
		}
	}
