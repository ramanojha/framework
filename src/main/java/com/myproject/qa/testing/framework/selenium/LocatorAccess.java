package com.myproject.qa.testing.framework.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.myproject.qa.testing.framework.exceptions.LocatorNotResolvedException;
import com.myproject.qa.testing.framework.exceptions.LocatorTypeNotResolvedException;

public class LocatorAccess extends InstanceAccess{
	private static By getLocator(Object element) throws Exception {
		
		String elementName = ((Enum<?>) element).name();
		String elementValue = element.toString();
		
		if (elementName.endsWith("_ID")) {
			return By.id(elementValue);
		} else if (elementName.endsWith("_NAME")) {
			return By.name(elementValue);
		} else if (elementName.endsWith("_CLASS")) {
			return By.className(elementValue);
		} else if (elementName.endsWith("_CSS")) {
			return By.cssSelector(elementValue);
		} else if (elementName.endsWith("_LINK")) {
			return By.linkText(elementValue);
		} else if (elementName.endsWith("_PLINK")) {
			return By.partialLinkText(elementValue);
		} else if (elementName.endsWith("_TAG")) {
			return By.tagName(elementValue);
		} else if (elementName.endsWith("_XPATH")) {
			return By.xpath(elementValue);
		}
		throw new LocatorTypeNotResolvedException("Unable to handle the locator type: " + elementName+ ". Locator name should end with _ID/_NAME/" + "_CLASS/_CSS/_LINK/_PLINK/_TAG/_XPATH");
	}

	public static WebElement getElement(Object locator) throws Exception {
		try {
			if(locator instanceof Enum){
				return driver.findElement(getLocator(locator));
			}
			else if(locator instanceof By)
				return driver.findElement((By) locator);
			else
				return (WebElement) locator;
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found -" +((Enum<?>)locator).name());
		}
	}
	
	public static List<WebElement> getElements(Object locator) throws Exception {	
		try {
			if(locator instanceof Enum)
				return driver.findElements(getLocator(locator));
			if(locator instanceof By)
				return driver.findElements((By) locator);
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found -"+((Enum<?>)locator).name());
		}
		return null;
	}
	
	public static List<WebElement> getElementsContains(String tag, String value) throws Exception{
		String xpath = "//"+tag+"[contains(.,'"+value+"')]";
		try {
			return driver.findElements(By.xpath(xpath));
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found using xpath"+xpath);
		}
	}
	
	public static WebElement getElementContains(String tag, String value) throws Exception{
		String xpath ="//"+tag+"[contains(.,'"+value+"')]";
		try {
			return driver.findElement(By.xpath(xpath));
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found using xpath"+xpath);
		}
	}
	
	public static List<WebElement> getElements(String tag, String value) throws Exception{
		String xpath = "//"+tag+"[.='"+value+"']";
		try {
			return driver.findElements(By.xpath(xpath));
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found using xpath"+xpath);
		}
	}
	
	public static WebElement getElement(String tag, String value) throws Exception{
		String xpath = "//"+tag+"[.='"+value+"']";
		try {
			return driver.findElement(By.xpath("//"+tag+"[.='"+value+"']"));
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found using xpath"+xpath);
		}
	}
	
	public static WebElement getElementFromListWhichHas(Object locator, String value) throws Exception{
		return getElementFromListWhichHas(LocatorAccess.getElements(locator), value);
	}
	
	public static WebElement getElementFromListWhichHas(List<WebElement> list, String value) throws Exception {
		for(WebElement e : list){
			if(e.getText().equals(value)){
				return e;
			}		
		}
		throw new LocatorNotResolvedException("No WebElement equaly Matched In List which has "+value);
		
	}
	
	public static WebElement getElementFromListWhichContains(Object locator, String value) throws Exception{
		return getElementFromListWhichHas(LocatorAccess.getElements(locator), value);
	}
	
	public static WebElement getElementFromListWhichContains(List<WebElement> list, String value) throws Exception {
		for(WebElement e : list){
			if(e.getText().contains(value)){
				return e;
			}		
		}
		throw new LocatorNotResolvedException("No WebElement matched In List which has "+value);

	}
}
