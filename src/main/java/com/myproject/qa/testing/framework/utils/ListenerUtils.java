package com.myproject.qa.testing.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.exceptions.FrameworkException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class ListenerUtils {

	private static BaseColor statusColor;
	private static BaseColor finalstatusColor;

	public static void writePDF(String fileName, Map<String, List<ITestResult>> testResults, ISuite suite) throws Exception {
		ScriptLogger.info();
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File(fileName)));
			document.open();

			document.add(setParagraph("Test Result\n\n", Element.ALIGN_CENTER));

			//Layout 001 - Stats Layout.
			document.add(setStatsLayout001(suite));
			document.add(setParagraph("\n", Element.ALIGN_CENTER));

			//Layout 001 - XML Parameters
			document.add(setXMLParamsLayout001(suite));
			document.add(setParagraph("\n", Element.ALIGN_CENTER));

			//Layout 001 - Step details Layout
			for(Map.Entry<String, List<ITestResult>> entry : testResults.entrySet()){
				document.add(setStepsDetailLayout001(entry.getKey(),entry.getValue()));
				document.add(setParagraph("\n", Element.ALIGN_CENTER));
			}

			//Layout 001 - Copy Suite.xml
			document.add(pasteSuiteXMLdataLayout001((String)suite.getAttribute("suiteFileName")));
			document.add(setParagraph("\n", Element.ALIGN_CENTER));

		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		finally{
			document.close();
		}

	}

	private static PdfPTable pasteSuiteXMLdataLayout001(String fileName) throws Exception{
		//special font sizes
		Font nameFnt = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		Font cellFnt = new Font(FontFamily.TIMES_ROMAN, 8);

		float[] columnWidths = {10f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100f);

		//Zero row
		insertCell(table, "Suite.xml", Element.ALIGN_LEFT, 1, nameFnt, "Background", finalstatusColor);
		insertCell(table, fileName, Element.ALIGN_LEFT, 1, cellFnt);
		//First row
		String fileData  = FileUtils.convertStreamToString(new FileInputStream(fileName));
		insertCell(table, fileData, Element.ALIGN_LEFT, 1, cellFnt);

		return table;
	}


	public static Paragraph setParagraph(String paragraph, int alignment, Font... font){
		if(font.length ==0){
			Paragraph p = new Paragraph(paragraph);
			p.setAlignment(alignment);
			return p;
		}else{
			Paragraph p = new Paragraph(paragraph, font[0]);
			p.setAlignment(alignment);
			return p;
		}
	}

	public static Font setFont(Object style, int fontSize){
		Font f = new Font();
		if(style instanceof Integer)
			f.setStyle((int)style);
		else
			f.setStyle((String)style);

		f.setSize(fontSize);
		return f;
	}

	public static String getStatus(int statusCode){
		switch (statusCode) {
		case 1:
			statusColor = lightGreenColor();
			return "PASS";
			
		case 2:
			statusColor = lightRedColor();
			return "FAIL";
		case 3:
			statusColor = lightyellowColor();
			return "SKIP";
		}
		return null;
	}
	
	public static PdfPTable setXMLParamsLayout001(ISuite suite) throws Exception{
		//special font sizes
		Font nameFnt = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		Font headerFnt = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
		Font cellFnt = new Font(FontFamily.TIMES_ROMAN, 8);

		float[] columnWidths = {2.5f, 2.5f, 2.5f, 2.5f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100f);

		//Zero row
		insertCell(table, "Global Parameter", Element.ALIGN_LEFT, 4, nameFnt, "Background", finalstatusColor);

		//First row
		insertCell(table, "Environment", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, suite.getParameter("env"), Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Browser", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, suite.getParameter("browser"), Element.ALIGN_CENTER, 1, cellFnt);

		//Second row
		insertCell(table, "WaitTime(Secs)", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, suite.getParameter("waitTime"), Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Stabitity Time(Secs)", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, suite.getParameter("stabilityTime"), Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Suite File", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, (String)suite.getAttribute("suiteFileName"), Element.ALIGN_LEFT, 3, cellFnt); 
		return table;
	}


	public static PdfPTable setStatsLayout001(ISuite suite) throws Exception{
		//special font sizes
		Font nameFnt = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		Font headerFnt = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
		Font cellFnt = new Font(FontFamily.TIMES_ROMAN, 8);

		float[] columnWidths = {1.7f, 1.7f, 1.7f, 1.7f, 1.6f, 1.6f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100f);

		String startTime = DateUtils.getMillisToTimeStamp((Long)suite.getAttribute("startTime"));
		String endTime = DateUtils.getMillisToTimeStamp((Long)suite.getAttribute("endTime"));

		long seconds = DateUtils.getDifferenceInDates(startTime, endTime);

		int passedCnt = 0;
		int failedCnt =0;
		int skippedCnt = 0;
		for(Map.Entry<String, ISuiteResult> entry : suite.getResults().entrySet()){
			ITestContext context = entry.getValue().getTestContext();

			passedCnt +=context.getPassedTests().getAllMethods().size();
			failedCnt +=context.getFailedTests().getAllResults().size();
			skippedCnt +=context.getSkippedTests().getAllMethods().size();
		}
		String finalStatus = (skippedCnt >0 || failedCnt >0) ? "FAIL": "PASS";
		finalstatusColor = (finalStatus.equals("PASS"))? lightGreenColor() : lightRedColor();

		//Zero row
		insertCell(table, "Test Status ", Element.ALIGN_LEFT, 4, nameFnt, "Background", finalstatusColor);
		insertCell(table, "Final Status", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, finalStatus, Element.ALIGN_CENTER, 1, cellFnt, "Background", finalstatusColor);
		
		// First row
		insertCell(table, "Total Steps", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, Integer.toString(passedCnt+failedCnt+skippedCnt), Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Date", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, DateUtils.getCurrentDate(), Element.ALIGN_CENTER, 1, cellFnt);
		
		insertCell(table, "Host name ", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, System.getProperty("hostName"), Element.ALIGN_LEFT, 1, cellFnt);

		// Second Row
		insertCell(table, "Passed Steps", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, Integer.toString(passedCnt), Element.ALIGN_CENTER, 1, cellFnt, "Background", lightGreenColor());

		insertCell(table, "Failed Steps", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, Integer.toString(failedCnt), Element.ALIGN_CENTER, 1, cellFnt, "Background", lightRedColor());

		insertCell(table, "Skipped Steps", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, Integer.toString(skippedCnt), Element.ALIGN_CENTER, 1, cellFnt, "Background", lightyellowColor());
	
		// Third Row
		insertCell(table, "Started", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, startTime, Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Finished", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, endTime, Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Interval(Secs)", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());	
		insertCell(table, Long.toString(seconds), Element.ALIGN_CENTER, 1, cellFnt);

		return table;
	}

	//Refer - https://www.mysamplecode.com/2012/10/create-table-pdf-java-and-itext.html
	public static PdfPTable setStepsDetailLayout001(String testName, List<ITestResult> results) throws Exception{
		int cnt =0;
		//special font sizes
		Font nameFnt = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		Font headerFnt = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
		Font cellFnt = new Font(FontFamily.TIMES_ROMAN, 8);

		//specify column widths
		float[] columnWidths = {.3f, 2.2f ,3.3f, 1f, 1f, 1f, .7f};

		//create PDF table with the given widths
		PdfPTable table = new PdfPTable(columnWidths);

		// set table width a percentage of the page width
		table.setWidthPercentage(100f);

		//set testname, headers,
		String testStatus = "";
		for (ITestResult res : results) {
			testStatus = getStatus(res.getStatus());
			if(testStatus.equals("FAIL"))
				break;
		}
		BaseColor finalTestStatusColor = testStatus.equals("PASS") ? lightGreenColor() : lightRedColor();
		insertCell(table, testName, Element.ALIGN_LEFT, 7, nameFnt, "Background", finalTestStatusColor);
		//setHeaders(table, headerFnt, Element.ALIGN_CENTER, "No", "TestStep", "ClassName", "StartTime", "EndTime","Interval", "Status");

		insertCell(table, "No", Element.ALIGN_CENTER, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, "TestStep", Element.ALIGN_CENTER, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, "ClassName", Element.ALIGN_CENTER, 2, headerFnt, "Background", lightBlueColor());
		insertCell(table, "StartTime", Element.ALIGN_CENTER, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, "EndTime", Element.ALIGN_CENTER, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, "Interval", Element.ALIGN_CENTER, 1, headerFnt, "Background", lightBlueColor());

		
		for (ITestResult res : results) {

			String startTime = DateUtils.getMillisToTimeStamp(res.getStartMillis());
			String endTime = DateUtils.getMillisToTimeStamp(res.getEndMillis());

			long seconds = DateUtils.getDifferenceInDates(startTime, endTime);
			String[] classNames = res.getTestClass().getName().split("\\.");
			String className = classNames[classNames.length-1];
			String methodName = res.getMethod().getMethodName();
			//String status = getStatus(res.getStatus());
			getStatus(res.getStatus());
			
			//coping test output in cells.
			insertCell(table, Integer.toString(++cnt), Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			insertCell(table, methodName, Element.ALIGN_LEFT,1,cellFnt, "Background", statusColor);
			insertCell(table, className, Element.ALIGN_LEFT,2,cellFnt, "Background", statusColor);
			insertCell(table, startTime, Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			insertCell(table, endTime, Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			insertCell(table, Long.toString(seconds), Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			//insertCell(table, status, Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);

			//parameter
			if(res.getParameters().length >0){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
				insertCell(table, "Parameters", Element.ALIGN_RIGHT,1,cellFnt, "Background", statusColor);
				String params = "";
				for(Object param : res.getParameters()){
					params += param.toString()+", ";	
				}
				insertCell(table, params.substring(0, params.length() -2), Element.ALIGN_LEFT,5,cellFnt, "Background", statusColor);
			}
			
			if(res.getAttribute("pageTitles") !=null){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt);
				insertCell(table, "Page titles", Element.ALIGN_RIGHT,1,cellFnt);
				insertCell(table, (String)res.getAttribute("pageTitles"), Element.ALIGN_LEFT,5,cellFnt);
			}

			//exception
			if((String)res.getAttribute("exception") != null){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt);
				insertCell(table, "Exception", Element.ALIGN_RIGHT,1,cellFnt);
				insertCell(table, (String)res.getAttribute("exception"), Element.ALIGN_LEFT,5,cellFnt);
			}	
			//stack trace
			if((StackTraceElement[])res.getAttribute("stacktrace") !=null){

				StringBuffer buffer=new StringBuffer();
				for (StackTraceElement ste : (StackTraceElement[])res.getAttribute("stacktrace")) {
					buffer.append(ste.toString()+"\n");
				}

				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt);
				insertCell(table, "Stacktrace", Element.ALIGN_RIGHT,1,cellFnt);
				insertCell(table, buffer.toString(), Element.ALIGN_LEFT,5,cellFnt);
			}

			//screenshot
			if((byte[])res.getAttribute("screenshot") !=null){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt);
				insertCell(table, "Screenshot", Element.ALIGN_RIGHT,1,cellFnt);
				insertImageInCell(table, (byte[])res.getAttribute("screenshot"), Element.ALIGN_CENTER, 5);
			}
		}
		return table;
	}

	public static PdfPTable setHeaders(PdfPTable table, Font font, int style, String...headers) throws Exception{
		for(String header : headers){
			insertCell(table, header, style, 1, font, "Background", lightBlueColor());
		}
		table.setHeaderRows(1);
		return table;
	}

	public static void setCells(PdfPTable table, Font font, int style, String...cells) throws Exception{
		for(String cell : cells){
			insertCell(table, cell, style, 1, font);
		}
	}

	private static void insertCell(PdfPTable table, String text, int align, int mergeColLeftToRight, Font font) throws Exception{

		try {
			//create a new cell with the specified Text and Font
			PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
			//set the cell alignment
			cell.setHorizontalAlignment(align);
			//set the cell column span in case you want to merge two or more cells
			cell.setColspan(mergeColLeftToRight);
			//in case there is no text and you wan to create an empty row
			if(text.trim().equalsIgnoreCase("")){
				cell.setMinimumHeight(10f);
			}
			cell.setBackgroundColor(lightGreyColor());
			//add the call to the table
			table.addCell(cell);
		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to insert Cell");
		}

	}
	
	private static void insertCell(PdfPTable table, String text, int align, int mergeColLeftToRight, Font font, String style, BaseColor color) throws Exception{

		try {
			//create a new cell with the specified Text and Font
			if(style.equalsIgnoreCase("ColorText")){
				font.setColor(color);
			}
			
			PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
			//set the cell alignment
			cell.setHorizontalAlignment(align);
			//set the cell column span in case you want to merge two or more cells
			cell.setColspan(mergeColLeftToRight);
			//in case there is no text and you wan to create an empty row
			if(text.trim().equalsIgnoreCase("")){
				cell.setMinimumHeight(10f);
			}
			//add the call to the table
			if(style.equalsIgnoreCase("Background"))
				cell.setBackgroundColor(color);
			else if(style.equalsIgnoreCase("Border")){
				cell.setBorder(2);
			}
			else{
				cell.setBackgroundColor(lightGreyColor());
			}
			table.addCell(cell);
		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to insert Cell");
		}

	}

	private static void insertImageInCell(PdfPTable table, byte[] screenShot, int align, int mergeColLeftToRight) throws Exception{

		try {
			Image img = Image.getInstance(screenShot);
			img.scaleAbsolute(192*2f, 108*2f);

			img.setBorder(Rectangle.BOX);
			img.setBorderColor(BaseColor.BLACK);
			img.setBorderWidth(1f);

			//create a new cell with the specified Text and Font
			PdfPCell cell = new PdfPCell(img); //(new Phrase(screenShot));
			//set the cell alignment
			cell.setHorizontalAlignment(align);

			//set the cell column span in case you want to merge two or more cells
			cell.setColspan(mergeColLeftToRight);
			//in case there is no text and you wan to create an empty row

			cell.setMinimumHeight(10f);
			cell.setBackgroundColor(lightGreyColor());
			//add the call to the table
			table.addCell(cell);
		} catch (Exception e) {
			throw new FrameworkException(e,"Unable to insert image");
		}

	}
	
	private static BaseColor lightRedColor() {
		return new BaseColor(247, 198, 214);
	}

	private static BaseColor lightGreenColor() {
		return new BaseColor(232, 255, 239);
	}
	
	private static BaseColor lightyellowColor() {
		return new BaseColor(243, 250, 199);
	}

	private static BaseColor lightGreyColor() {
		return new BaseColor(245, 239, 239);
	}
	
	private static BaseColor lightBlueColor() {
		return new BaseColor(219, 225, 255);
	}
	
	/******************************************************************************/
	
	public static void writePDFForRestReport(String fileName, Map<String, List<ITestResult>> testResults, ISuite suite) throws Exception {
		ScriptLogger.info();
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File(fileName)));
			document.open();

			document.add(setParagraph("Test Result\n\n", Element.ALIGN_CENTER));

			//Layout 001 - Stats Layout.
			document.add(setStatsLayout001(suite));
			document.add(setParagraph("\n", Element.ALIGN_CENTER));

			//Layout 001 - XML Parameters
			document.add(setXMLParamsLayoutRestReport001(suite));
			document.add(setParagraph("\n", Element.ALIGN_CENTER));

			//Layout 001 - Step details Layout
			for(Map.Entry<String, List<ITestResult>> entry : testResults.entrySet()){
				document.add(setStepsDetailLayoutRestReport001(entry.getKey(),entry.getValue()));
				document.add(setParagraph("\n", Element.ALIGN_CENTER));
			}

			//Layout 001 - Copy Suite.xml
			document.add(pasteSuiteXMLdataLayout001((String)suite.getAttribute("suiteFileName")));
			document.add(setParagraph("\n", Element.ALIGN_CENTER));

		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		finally{
			document.close();
		}

	}
	
	public static PdfPTable setXMLParamsLayoutRestReport001(ISuite suite) throws Exception{
		//special font sizes
		Font nameFnt = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		Font headerFnt = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
		Font cellFnt = new Font(FontFamily.TIMES_ROMAN, 8);

		float[] columnWidths = {2.5f, 2.5f, 2.5f, 2.5f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100f);

		//Zero row
		insertCell(table, "Global Parameter", Element.ALIGN_LEFT, 4, nameFnt, "Background", finalstatusColor);

		//First row
		insertCell(table, "Environment", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, suite.getParameter("env"), Element.ALIGN_CENTER, 1, cellFnt);
		insertCell(table, "", Element.ALIGN_CENTER, 1, cellFnt);
		insertCell(table, "", Element.ALIGN_CENTER, 1, cellFnt);
		//Second row
		insertCell(table, "WaitTime(Secs)", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, suite.getParameter("waitTime"), Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Stabitity Time(Secs)", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, suite.getParameter("stabilityTime"), Element.ALIGN_CENTER, 1, cellFnt);

		insertCell(table, "Suite File", Element.ALIGN_LEFT, 1, headerFnt, "Background", lightBlueColor());
		insertCell(table, (String)suite.getAttribute("suiteFileName"), Element.ALIGN_LEFT, 3, cellFnt); 
		return table;
	}
	
	public static PdfPTable setStepsDetailLayoutRestReport001(String testName, List<ITestResult> results) throws Exception{
		int cnt =0;
		//special font sizes
		Font nameFnt = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		Font headerFnt = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0));
		Font cellFnt = new Font(FontFamily.TIMES_ROMAN, 8);

		//specify column widths
		float[] columnWidths = {.3f, 2.2f ,3.3f, 1f, 1f, 1f, .7f};

		//create PDF table with the given widths
		PdfPTable table = new PdfPTable(columnWidths);

		// set table width a percentage of the page width
		table.setWidthPercentage(100f);

		//set testname, headers,
		String testStatus = "";
		for (ITestResult res : results) {
			testStatus = getStatus(res.getStatus());
			if(testStatus.equals("FAIL"))
				break;
		}
		BaseColor finalTestStatusColor = testStatus.equals("PASS") ? lightGreenColor() : lightRedColor();
		insertCell(table, testName, Element.ALIGN_LEFT, 7, nameFnt, "Background", finalTestStatusColor);
		setHeaders(table, headerFnt, Element.ALIGN_CENTER, "No", "TestStep", "ClassName", "StartTime", "EndTime","Interval", "Status");

		for (ITestResult res : results) {

			String startTime = DateUtils.getMillisToTimeStamp(res.getStartMillis());
			String endTime = DateUtils.getMillisToTimeStamp(res.getEndMillis());

			long seconds = DateUtils.getDifferenceInDates(startTime, endTime);
			String[] classNames = res.getTestClass().getName().split("\\.");
			String className = classNames[classNames.length-1];
			String methodName = res.getMethod().getMethodName();
			String status = getStatus(res.getStatus());

			//coping test output in cells.
			insertCell(table, Integer.toString(++cnt), Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			insertCell(table, methodName, Element.ALIGN_LEFT,1,cellFnt, "Background", statusColor);
			insertCell(table, className, Element.ALIGN_LEFT,1,cellFnt, "Background", statusColor);
			insertCell(table, startTime, Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			insertCell(table, endTime, Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			insertCell(table, Long.toString(seconds), Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
			insertCell(table, status, Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);

			//parameter
			if(res.getParameters().length >0){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
				insertCell(table, "Parameters", Element.ALIGN_RIGHT,1,cellFnt, "Background", statusColor);
				String params = "";
				for(Object param : res.getParameters()){
					params += param.toString()+", ";	
				}
				insertCell(table, params.substring(0, params.length() -2), Element.ALIGN_LEFT,5,cellFnt, "Background", statusColor);
			}
			//requestUrl
			if(res.getAttribute("Request") !=null){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
				insertCell(table, "Request", Element.ALIGN_RIGHT,1,cellFnt, "Background", statusColor);
				insertCell(table, (String)res.getAttribute("Request"), Element.ALIGN_LEFT,5,cellFnt, "Background", statusColor);
			}

			//body
			if((String)res.getAttribute("Body") != null){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
				insertCell(table, "Body", Element.ALIGN_RIGHT,1,cellFnt, "Background", statusColor);
				insertCell(table, (String)res.getAttribute("Body"), Element.ALIGN_LEFT,5,cellFnt, "Background", statusColor);
			}
			
			//response
			if((String)res.getAttribute("Response") != null){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt, "Background", statusColor);
				insertCell(table, "Response", Element.ALIGN_RIGHT,1,cellFnt, "Background", statusColor);
				insertCell(table, (String)res.getAttribute("Response"), Element.ALIGN_LEFT,5,cellFnt, "Background", statusColor);
			}
			//responseString
			if((String)res.getAttribute("Response String") != null){
				String responseStr;
				try {
					responseStr = JSONUtils.prettyPrintJSON((String)res.getAttribute("Response String"));
				} catch (Exception e) {
					responseStr = (String)res.getAttribute("Response String");
				}
		
				if (status.equals("PASS")) {
					insertCell(table, "", Element.ALIGN_CENTER, 1, cellFnt);
					insertCell(table, "Response String", Element.ALIGN_RIGHT,1, cellFnt, "Background", statusColor);
					insertCell(table, responseStr, Element.ALIGN_LEFT, 5, cellFnt, "Background", statusColor);
				}else{
					insertCell(table, "", Element.ALIGN_CENTER, 1, cellFnt);
					insertCell(table, "Response String", Element.ALIGN_RIGHT,1, cellFnt);
					insertCell(table, responseStr, Element.ALIGN_LEFT, 5, cellFnt);

				}
			}
			//exception
			if((String)res.getAttribute("exception") != null){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt);
				insertCell(table, "Exception", Element.ALIGN_RIGHT,1,cellFnt);
				insertCell(table, (String)res.getAttribute("exception"), Element.ALIGN_LEFT,5,cellFnt);
			}
			//stack trace
			if((StackTraceElement[])res.getAttribute("stacktrace") !=null){

				StringBuffer buffer=new StringBuffer();
				for (StackTraceElement ste : (StackTraceElement[])res.getAttribute("stacktrace")) {
					buffer.append(ste.toString()+"\n");
				}

				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt);
				insertCell(table, "Stacktrace", Element.ALIGN_RIGHT,1,cellFnt);
				insertCell(table, buffer.toString(), Element.ALIGN_LEFT,5,cellFnt);
			}


		}
		return table;
	}

	@Aim("To create report name based on Test Suite directory")
	public static String getReportName(String suiteFileName) {
		return suiteFileName
				.replace(System.getProperty("user.dir"), System.getProperty("user.dir")+"\\target")
				.replace(".xml", ".pdf");
	}
}
