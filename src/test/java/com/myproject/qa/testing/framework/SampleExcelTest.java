package com.myproject.qa.testing.framework;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.utils.ExcelUtils;
import com.myproject.qa.testing.framework.utils.FileUtils;

public class SampleExcelTest {
	
	@Test(enabled=false)
	@Aim("Sample Writing of ExcelData")
	public void writeExcel() throws IOException {
		Map<String, Object[][]> excelData = new HashMap<>();
		
		Object[][] sheet1Data = {
				{"Type","catagory","example"},
				{"int","primitive", 1},
				{"float", "primitive", 1.00},
				{"String", "non-primitive", "abcd"},
				{"boolean", "primitive", "true"}			
		};
		
		Object[][] sheet2Data = {
				{"Type","catagory","example"},
				{"int","primitive", 1},
				{"float", "primitive", 1.0},
				{"boolean", "primitive", "true"}			
		};
		
		Object[][] sheet3Data = {
				{"Type","catagory","example"},
				{"String", "non-primitive", "abcd"}
		};
		
		excelData.put("sheet1", sheet1Data);
		excelData.put("primitive", sheet2Data);
		excelData.put("non_primitive", sheet3Data);
		
		XSSFWorkbook workbook = ExcelUtils.prepareWorkbook(excelData);
		FileUtils.writeExcel(System.getProperty("user.dir")+"data.xlsx", workbook);
	}
	
	@Test(enabled=false)
	@Aim("Sample read excel")
	public static void readExcel() throws IOException {
		XSSFWorkbook workbook = FileUtils.readExcel(System.getProperty("user.dir")+"data.xlsx");
		XSSFSheet sheet = ExcelUtils.getSheetByName(workbook, "sheet1");
		ScriptLogger.info(sheet.toString());
		ScriptLogger.info("Total rows : "+ExcelUtils.totalRows(sheet));
		ScriptLogger.info("Total cols : "+ExcelUtils.totalIndexedCells(sheet, 1));
	}
}
