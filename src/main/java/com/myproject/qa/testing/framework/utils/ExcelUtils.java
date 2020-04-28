package com.myproject.qa.testing.framework.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.myproject.qa.testing.framework.annotations.Aim;

public class ExcelUtils {
	@Aim({"prepare the excel data where map of exceldata, has sheetName as Key, Sheetdata(2d object) as a sheet data.",
		  "To write the data in excel file, refer the FileHandling.writeExcel()."})
	public static XSSFWorkbook prepareWorkbook(Map<String, Object[][]> exceldata) {	
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFCellStyle defaultCellStyle = workbookStyle("DEFAULT",workbook);
		XSSFCellStyle boldCellStyle = workbookStyle("BOLD", workbook);
		for(Map.Entry<String, Object[][]> entry : exceldata.entrySet()) {
			String sheetName = entry.getKey();
			XSSFSheet sheet = workbook.createSheet(sheetName);
			sheet = prepareSheet(sheet, entry.getValue(), defaultCellStyle, boldCellStyle);	
		}

		return workbook;
	}


	@Aim("prepare the sheet data")
	private static XSSFSheet prepareSheet(XSSFSheet sheet, Object[][] data, XSSFCellStyle defaultCellStyle, XSSFCellStyle boldCellStyle) {
		int rowNum = 0;
		for(Object[] rowObject : data) {
			Row row = sheet.createRow(rowNum++);

			int cellNum =0;
			for(Object colVal: rowObject) {
				Cell cell = row.createCell(cellNum++);
				setCellValue(cell, colVal);	

				//Styles
				if(rowNum ==1) cell.setCellStyle(boldCellStyle);
				else cell.setCellStyle(defaultCellStyle);
				sheet.autoSizeColumn(cellNum);
			}
		}	
		return sheet;
	}

	@Aim("set cell value")
	private static void setCellValue(Cell cell, Object obj) {
		if(obj instanceof String) 
			cell.setCellValue((String) obj);
		else if(obj instanceof Boolean) 
			cell.setCellValue((boolean) obj);
		else if(obj instanceof Calendar) 
			cell.setCellValue((Calendar) obj);
		else if(obj instanceof Date) 
			cell.setCellValue((Date) obj);
		else if(obj instanceof Integer ) 
			cell.setCellValue((String) obj.toString());	
		else if(obj instanceof Double ) 
			cell.setCellValue((String) obj.toString());
		else if(obj instanceof RichTextString) 
			cell.setCellValue((RichTextString) obj);
		else if(obj instanceof Byte ) 
			cell.setCellErrorValue((byte) obj);	

	}

	@Aim("My Workbook default Style")
	private static XSSFCellStyle workbookStyle(String type, XSSFWorkbook workbook) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();

		//Alignment
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		//border style
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);

		//font style
		XSSFFont font = workbook.createFont();
		font.setFontName("Calibri");;
		font.setFontHeight(12);
		if(type.equals("BOLD")) font.setBold(true);

		//dataformat
		cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#.#"));
		cellStyle.setFont(font);
		return cellStyle;
	}


	@Aim("readSheet from a workbook")
	public static XSSFSheet getSheetByIndex(XSSFWorkbook workbook, String sheetNo) {
		return workbook.getSheetAt(Integer.parseInt(sheetNo));
	}

	@Aim("readSheet from a workbook")
	public static XSSFSheet getSheetByName(XSSFWorkbook workbook, String sheetName) {	
		return workbook.getSheet(sheetName);
	}

	@Aim("read a cell")
	public static Object readACell(XSSFSheet sheet, int rowNum, int cellNum){
		return sheet.getRow(rowNum).getCell(cellNum);
	}

	@Aim("Read a row")
	public static Row readARow(XSSFSheet sheet, int rowNum){
		return sheet.getRow(rowNum);
	}

	@Aim("return total row count only if data is present on a row")
	public static int totalRows(XSSFSheet sheet) {
		return sheet.getPhysicalNumberOfRows();
	}

	@Aim("return indexed return (totalrowsInSheet-1)")
	public static int totalIndexedRows(XSSFSheet sheet) {
		return sheet.getLastRowNum(); 
	}

	@Aim("return indexed return (totalcolsInRow-1)")
	public static int totalIndexedCells(XSSFSheet sheet, int rowNum) {
		return sheet.getRow(rowNum).getLastCellNum();
	}
}
