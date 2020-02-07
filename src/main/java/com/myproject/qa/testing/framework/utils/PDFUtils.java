package com.myproject.qa.testing.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

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
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class PDFUtils {

	public static void writePDF(String fileName, Map<String, List<ITestResult>> testResults ) throws Exception {
		ScriptLogger.info();
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File(fileName)));
			document.open();

			document.add(setParagraph("Test Result\n\n", Element.ALIGN_CENTER));

			//Layout 01
			for(Map.Entry<String, List<ITestResult>> entry : testResults.entrySet()){
				document.add(setTableLayout001(entry.getKey(),entry.getValue()));
				document.add(setParagraph("\n", Element.ALIGN_CENTER));
			}

		} catch (Exception e) {
			throw new ScriptException(e);
		}
		finally{
			document.close();
		}

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
			return "PASS";
		case 2:
			return "FAIL";
		case 3:
			return "SKIP";
		}
		return null;
	}
	//Refer - https://www.mysamplecode.com/2012/10/create-table-pdf-java-and-itext.html
	public static PdfPTable setTableLayout001(String testName, List<ITestResult> results) throws Exception{
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
		insertCell(table, testName, Element.ALIGN_LEFT, 7, nameFnt);
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
			insertCell(table, Integer.toString(cnt+1), Element.ALIGN_CENTER,1,cellFnt);
			insertCell(table, methodName, Element.ALIGN_LEFT,1,cellFnt);
			insertCell(table, className, Element.ALIGN_LEFT,1,cellFnt);
			insertCell(table, startTime, Element.ALIGN_CENTER,1,cellFnt);
			insertCell(table, endTime, Element.ALIGN_CENTER,1,cellFnt);
			insertCell(table, Long.toString(seconds), Element.ALIGN_CENTER,1,cellFnt);
			insertCell(table, status, Element.ALIGN_CENTER,1,cellFnt);
			
			//parameter
			if(res.getParameters().length >0){
				insertCell(table, "", Element.ALIGN_CENTER,1,cellFnt);
				insertCell(table, "Parameters", Element.ALIGN_RIGHT,1,cellFnt);
				String params = "";
				for(Object param : res.getParameters()){
					params += param.toString();	
				}
				insertCell(table, params, Element.ALIGN_LEFT,5,cellFnt);
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
			insertCell(table, header, style, 1, font);
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
			  //add the call to the table
			  table.addCell(cell);
		} catch (Exception e) {
			throw new ScriptException("Unable to insert Cell");
		}
		   
	}
	 
	 private static void insertImageInCell(PdfPTable table, byte[] screenShot, int align, int mergeColLeftToRight) throws Exception{
		 
		 try {
			Image img = Image.getInstance(screenShot);
			  img.scaleAbsolute(192*2f, 108*2f);
			  
			  img.setBorder(Rectangle.BOX);
			  img.setBorderColor(BaseColor.RED);
			  img.setBorderWidth(1f);
			  
			  //create a new cell with the specified Text and Font
			  PdfPCell cell = new PdfPCell(img); //(new Phrase(screenShot));
			  //set the cell alignment
			  cell.setHorizontalAlignment(align);
			  
			  //set the cell column span in case you want to merge two or more cells
			  cell.setColspan(mergeColLeftToRight);
			  //in case there is no text and you wan to create an empty row
			 
			  cell.setMinimumHeight(10f);
			  
			  //add the call to the table
			  table.addCell(cell);
		} catch (Exception e) {
			throw new ScriptException("Unable to insert image");
		}
		   
	}
}
