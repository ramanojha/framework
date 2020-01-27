package com.myproject.qa.testing.framework.selenium;



import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.properties.SeleniumProperties;
import com.myproject.qa.testing.framework.utils.FileUtils;

public class InitializeWebDriver {

	private static WebDriver driver;
	private static String browser;
	public static void setDriver(String driverType){
		browser = driverType;
		if(driver == null){
			switch (browser) {
			case "chrome" :
				System.setProperty(SeleniumProperties.CHROME_WEB_DRIVER_EXE.getValue(), getdriverPath(browser));
				ChromeOptions options = new ChromeOptions();
				ChromeDriverService service = ChromeDriverService.createDefaultService(); 
				options.addArguments("--disable--notifications");
				options.addArguments("-incognito");
				options.addArguments("start-maximized"); 
				options.addArguments("--disable-popup-blocking");
				options.addArguments("disable-infobars");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("password_manager_enabled", false); 
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("profile.default_content_setting_values.automatic_downloads", 1 );
				prefs.put("download.prompt_for_download", false);
				prefs.put("download.default_directory", "C:\\TestData\\Downloads");
				options.setExperimentalOption("prefs", prefs);
				driver  = new ChromeDriver(service, options); 
				break;

			default:
				break;
			}

			driver.manage().window().maximize();
		}else{
			ScriptLogger.info("Driver is already initialized");	
		}
	}

	public static WebDriver getDriver(){
		return driver;
	}
	
	public static String getBrowser(){
		return browser;
	}
	

	private static String getdriverPath(String browser){
		String path = null ;
		switch (browser) {
		case "chrome":
			path = SeleniumProperties.CHROME_WEB_DRIVER_PATH.getValue()+"\\chromedriver.exe";
			break;

		default:
			break;
		}	
		if(FileUtils.isFile(path))
			return path;
		else
			ScriptLogger.debug("Setup this path :"+path);
		return null;
	}
}
