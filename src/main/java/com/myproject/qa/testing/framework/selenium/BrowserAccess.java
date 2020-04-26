package com.myproject.qa.testing.framework.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
public class BrowserAccess extends InstanceAccess{
	
	public static String getElementInnerHtml(Object locator)  throws Exception {
		return (String)jsDriver.executeScript("return arguments[0].innerHTML;" ,LocatorAccess.getElement(locator));
	}
	
	public static String getElementOuterHtml(Object locator)  throws Exception {
		return (String)jsDriver.executeScript("return arguments[0].outerHTML;" ,LocatorAccess.getElement(locator));
	}
	
	public static String getElementAttributeValue(Object locator, String attribute) throws Exception {
		 return LocatorAccess.getElement(locator).getAttribute(attribute);
	}
	
	//get page text
	public static String getPageInnerText(){
		return jsDriver.executeScript("return document.documentElement.innerText;").toString();
	}

	public static String getElementText(Object locator, boolean... js)  throws Exception {
		if(js.length > 0) {	// default is JavaScript
			if(browser.equalsIgnoreCase("ie"))
				return (String)jsDriver.executeScript("return arguments[0].innerText;", LocatorAccess.getElement(locator));
			else
				return (String)jsDriver.executeScript("return arguments[0].textContent;", LocatorAccess.getElement(locator));
		}
		return LocatorAccess.getElement(locator).getText();
	}
	
	public static List<String> getElementsText(Object locator, boolean... js)  throws Exception {
		return getElementsText(LocatorAccess.getElements(locator), js); 
	}	
	
	public static List<String> getElementsText(List<WebElement> elements, boolean... js) throws Exception {
		List<String> result = new ArrayList<String>();
		for(WebElement each : elements)
			result.add(getElementText(each, js).trim());
		return result;
	}
	
	public static String getPageTitlesInString() throws Exception {
		String currentTitle = driver.getTitle();
		String currentHandle = driver.getWindowHandle();
		String pageTitles = "";
		for(String handle : driver.getWindowHandles()){
			driver.switchTo().window(handle); 
			String title = driver.getTitle();
			if(title.equals(currentTitle))
				pageTitles += "("+title+"), ";
			else
				pageTitles += title+", ";
		}
		driver.switchTo().window(currentHandle);
		return pageTitles.substring(0, pageTitles.length()-2);
	}
	
	public static String getPageTitle() throws Exception {
		return driver.getTitle();
	}
	
	public static String getWindowHandle() throws Exception {
		return driver.getWindowHandle();
	}
	
	public static Set<String> getWindowHandles() throws Exception {
		return driver.getWindowHandles();
	}

	public static String removeAttribute(Object locator,String...js) throws Exception {
		return (String)jsDriver.executeScript("arguments[0].removeAttribute('"+js[0]+"','"+js[1]+"')", LocatorAccess.getElement(locator));	
	}

	public static String setAttribute(Object locator,String...js) throws Exception {
		return (String)jsDriver.executeScript("arguments[0].setAttribute('"+js[0]+"','"+js[1]+"')", LocatorAccess.getElement(locator));	
	}
	
	public static String getPageSource() throws Exception {
		return driver.getPageSource();
	}
	
	public static String getNetworkLogEntries() throws Exception{
		String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
		return jsDriver.executeScript(scriptToExecute).toString();	
	}
	//https://www.rapidtables.com/convert/color/hex-to-rgb.html
	public static String getCssValue(WebElement object, String cssAttribute) throws Exception {
		return LocatorAccess.getElement(object).getCssValue(cssAttribute);
	}
	
	//Get All url Links
	public static List<String> getAllPageLinks() throws Exception{
		List<WebElement> links= new ArrayList<>();
		List<String> finalList= new ArrayList<>();
		links.addAll(LocatorAccess.getElements(By.tagName("a")));
		links.addAll(LocatorAccess.getElements(By.tagName("img")));
		links.forEach(link ->{
			if(link.getAttribute("href") !=null) {
				finalList.add(link.getAttribute("href"));
			}
			else if(link.getAttribute("src") !=null && link.getAttribute("src").contains("http")) {
				finalList.add(link.getAttribute("src"));
			}
		});	
		return finalList;	
	}
	// get domain
	public String getDomain(Object element, String value){
		return jsDriver.executeScript("return document.domain;").toString();
	}

	// get url
	public String getURL(Object element, String value){
		return jsDriver.executeScript("return document.URL;").toString();
	}
	
	// get url
	public int getTotalFramesOnPage(Object element, String value){
		return Integer.parseInt(jsDriver.executeScript("return window.length").toString());
	}
	
	
}
