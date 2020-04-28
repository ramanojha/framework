package com.myproject.qa.testing.framework.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.exceptions.FrameworkException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class FileUtils {
	//Refer - https://www.happycoders.eu/java/how-to-write-files-quickly-and-easily/
	//Refer - https://www.happycoders.eu/java/how-to-read-files-easily-and-fast/
	public static String convertStreamToString(InputStream is) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to convert Streem to String");
		} finally {
			is.close();
			reader.close();
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
		try {
			return path.substring(0, path.lastIndexOf("\\")+1)+projectName;
		} catch (Exception e) {
			throw new FrameworkException(e, projectName=" is not in same workspace");
		}		
	}
	
	public static boolean ifFileExist(String filepath) throws Exception {
		return (new File(filepath)).exists();
	}

	public static boolean deleteFile(String filepath) throws Exception {
		return (new File(filepath)).delete();
	}
	
	public static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		return bufferedReader(br);
	}

	//Read a excel and return workbook
	public static XSSFWorkbook readExcel(String fileName) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(fileName));
		return new XSSFWorkbook(inputStream);
	}
	

	//Write a File(read character by character)
	public static void writeFile(String fileName, String str) throws IOException {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName)))){
			bufferedWriter.write(str);
		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to write file at "+fileName);
		}
	}

	//Write a File(Read file by file)
	public static void writeFileFromStreem(String fileName, String str) throws IOException {
		FileOutputStream outputStream =new FileOutputStream(fileName);
		outputStream.write(str.getBytes());
		outputStream.close();
	}

	//Format json
	public static String formatJSON(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Object jsonObject = mapper.readValue(jsonString, Object.class);
		String formattedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
		return formattedJson;
	}

	//Write formatted Json
	public static void writeFormatedJSON(String jsonString, String fileName) throws JsonParseException, JsonMappingException, IOException {
		String formatedJson = formatJSON(jsonString);
		writeFile(fileName, formatedJson);
	}

	//Write Excel
	public static void writeExcel(String filename, XSSFWorkbook workbook) throws IOException {
		try ( FileOutputStream stream = new FileOutputStream(new File(filename));){
			workbook.write(stream);	
		} catch (Exception e) {
			throw new FrameworkException(e, filename+" can't be written");
		}
	}
	
	//List of fileNames in a folder
	public static List<String> filesInDirectory(String relativePath) {

		File folder = new File(relativePath);
		File[] files = folder.listFiles();
		List<String> fileNames = new ArrayList<String>();
		for(File file : files) {
			if(!file.isDirectory())
			fileNames.add(file.getName());
		}
		return fileNames;
	}

	//PropertyFile
	public static Properties propertyFile(String fileName) throws Exception {
	
		try (InputStream inStream = new FileInputStream(fileName);){
			Properties prop = new Properties();
			prop.load(inStream);
			return prop;
		}
		catch (Exception e) {
			throw new FrameworkException(e, fileName+" can't be loaded");
		}
	}

	//Read InputStream
	public static String readInputStream(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		return bufferedReaderWithNewLine(br);
	}
	
	public static String readInputStreaminKB(InputStream inputStream) throws IOException {
		
		try {
			byte[] bytes=new byte[1024];
			int i;
			String output = "";
			while((i =inputStream.read(bytes, 0, 1024)) != -1) {
				output +=  new String(bytes, 0, i);
			}
			inputStream.close();
			return output;
		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to read Input streem and store");
		}
	}
	
	//Read Buffer and doesn't append new line.
	public static String  bufferedReader(BufferedReader bufferedReader) throws IOException {
		StringBuffer sb = new StringBuffer();
		
		String line = bufferedReader.readLine();
		while(line != null) {
			sb.append(line);
			line = bufferedReader.readLine();
		} 
		bufferedReader.close(); 
		return sb.toString();
	}
	
	//Read Buffer and append new line.
	public static String  bufferedReaderWithNewLine(BufferedReader bufferedReader) throws IOException {
		
		String line = bufferedReader.readLine();
		StringBuffer sb = new StringBuffer();
		while(line != null) {
			sb.append(line).append("\n");
			line = bufferedReader.readLine();
		} 
		bufferedReader.close(); 
		return sb.toString();
	}
	
	@Aim("To create a directory if not exist.")
	public static void createDirectoryIfNotExist(String directoryPath) {
		File file = new File(directoryPath);
		if(file.isDirectory()){
			System.out.println(true);
		}else{
			file.mkdirs();
		}
	}
	
	@Aim("To get the PDF ArtifactName")
	public static String getPdfArtifactName(String fileName) {
		String directoryPath = fileName.substring(0, fileName.lastIndexOf('\\'));
		createDirectoryIfNotExist(directoryPath);
		String reportName = fileName
				.substring(fileName.lastIndexOf('\\')+1, fileName.length())
				.split("\\.")[0]
				.toUpperCase()+".pdf";
				
				
		return directoryPath+"\\"+reportName;
	}
}


