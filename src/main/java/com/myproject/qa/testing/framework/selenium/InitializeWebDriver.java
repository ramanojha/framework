package com.myproject.qa.testing.framework.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class InitializeWebDriver {

	private static WebDriver driver;
	private static String browser;
	public static void setDriver(String driverType) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		browser = driverType;
		
		if(driver == null){
			switch (browser) {
			case "chrome" :
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				WebDriverManager.chromedriver().setup();
				
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
				
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver  = new FirefoxDriver();
				
			default:
				break;
			}
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

}
