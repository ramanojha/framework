package com.myproject.qa.testing.framework.selenium;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserWait extends InstanceAccess{

	public static void waitUntilElementIsDisplayed(Object... locators) throws Exception {
		for(Object each: locators)
			waitUntilElementIsDisplayed(LocatorAccess.getElement(each), waitTime);
	}

	public static void waitUntilElementIsDisplayed(Object locator, int time) {
		(new WebDriverWait(driver, time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {
					return Boolean.valueOf(LocatorAccess.getElement(locator).isDisplayed());
				} catch (Exception e) {		
					return Boolean.valueOf(false); 	
				}
			}
		});
	}

	public static void waitUntilElementIsNotDisplayed(Object ... elements) {
		for(Object each: elements)
			waitUntilElementIsNotDisplayed(each, waitTime);
	}

	public static void waitUntilElementIsNotDisplayed(Object locator, int time) {
		(new WebDriverWait(driver, time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {
					return Boolean.valueOf(!LocatorAccess.getElement(locator).isDisplayed());
				} catch (Exception e) { 
					return Boolean.valueOf(true);	
				}
			}
		});
	}

	public static void waitUntilCountOfElementsPresent(List<WebElement> locators, int count) {
		waitUntilCountOfElementsPresent(locators, count, waitTime);
	}

	public static void waitUntilCountOfElementsPresent(List<WebElement> locators, final int count, int time) {
		(new WebDriverWait(driver, time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {
					return Boolean.valueOf(locators.size() == count);
				} catch (Exception e) {	 
					return Boolean.valueOf(false);	
				}				
			}
		});
	}

	public static void waitUntilCountOfElementsPresent(Object locator, int count) throws Exception {
		waitUntilCountOfElementsPresent(LocatorAccess.getElements(locator), count, waitTime);
	}

	public static void waitUntilCountOfElementsPresent(Object locator, final int count, int time) throws Exception {
		waitUntilCountOfElementsPresent(LocatorAccess.getElements(locator), count, time);
	}

	public static void waitUntilIndexedElementIsDisplayed(Object locator, int index) throws Exception {
		waitUntilIndexedElementIsDisplayed(LocatorAccess.getElements(locator), index, waitTime);
	}

	public static void waitUntilIndexedElementIsDisplayed(Object locator, final int index, int time) throws Exception {
		waitUntilIndexedElementIsDisplayed(LocatorAccess.getElements(locator), index, time);
	}

	public static void waitUntilIndexedElementIsDisplayed(List<WebElement> locators, int index) {
		waitUntilIndexedElementIsDisplayed(locators, index, waitTime);
	}

	public static void waitUntilIndexedElementIsDisplayed(List<WebElement> locators, final int index, int time) {
		(new WebDriverWait(driver, time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {
					return Boolean.valueOf(locators.get(index-1).isDisplayed());
				} catch (Exception e) {	
					return Boolean.valueOf(false);		
				}				
			}
		});
	}

	public static void waitUntilPageTitle(final String title) {
		waitUntilPageTitle(title, waitTime);
	}

	public static void waitUntilPageTitle(final String title, int time) {
		(new WebDriverWait(driver, time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {	
					return Boolean.valueOf(d.getTitle().trim().contains(title)); 
				}
				catch(Exception e) { 
					return Boolean.valueOf(false); 
				}
			}
		});
	}

	/*********************** Wait until specified text is present **********************/	

	public static void waitUntilText(final String... text)  {
		for(String each: text)
			try {
				waitUntilText(each, waitTime);
			} catch (Exception e) {
				each = each.replaceAll("<", "&lt;")
						.replaceAll("&", "&amp;")
						.replaceAll(">", "&gt;")
						.replaceAll("\"", "&quot;")
						.replaceAll("'", "&apos;");
				waitUntilText(each, waitTime);
			}
	}


	public static void waitUntilText(final String text,	int time) {
		(new WebDriverWait(driver, time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {	return Boolean.valueOf(d.getPageSource().contains(text)); }
				catch(Exception e) { return Boolean.valueOf(false); }
			}
		});
	}

	/*********************** Wait until specified text is not present **********************/	

	public static void waitUntilNotText(final String... text) throws Exception {
		for(String each: text)
			waitUntilNotText(each, waitTime);
	}

	public static void waitUntilNotText(final String text,	int time) {
		(new WebDriverWait(driver, time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try {	return Boolean.valueOf(!d.getPageSource().contains(text)); }
				catch(Exception e) { return Boolean.valueOf(false); }
			}
		});
	}

	public static void waitUntilElementIsClickable(Object locator, int time) throws Exception {
		(new WebDriverWait(driver, time)).until(ExpectedConditions.elementToBeClickable(LocatorAccess.getElement(locator)));
	}


	public static void waitUntilNewWindowIsLoaded(final List<String> existingWindowHandles){

		(new WebDriverWait(driver,waitTime)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {       
				Set<String> windowHandles=d.getWindowHandles();
				windowHandles.removeAll(existingWindowHandles);
				return windowHandles.iterator().hasNext();
			}
		});

	}

	public static void waitUntilPageIsLoaded() throws Exception {
		String documentState=(String)jsDriver.executeScript("return document.readyState");
		while (!documentState.equalsIgnoreCase("complete")) {
			documentState=(String)jsDriver.executeScript("return document.readyState");
			Thread.sleep(1000);
		} 
	}


	public static void waitForAlertBox() {
		new WebDriverWait(driver,waitTime).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try{
					d.switchTo().alert();
					return Boolean.valueOf(true);
				}
				catch(Exception e){
					return Boolean.valueOf(false);
				}
			}
		});
	}

	public static void waitUntilAlertBoxIsClosed() {
		new WebDriverWait(driver,waitTime).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				try{
					d.switchTo().alert();
					return Boolean.valueOf(false);
				}
				catch(Exception e){
					return Boolean.valueOf(true);
				}
			}
		});
	}

	public static void hoverAndVerifyElements(Object hover, Object... elements ) throws Exception {
		BrowserAction.hoverOverElement(hover);
		for (Object locator : elements) {
			BrowserWait.waitUntilElementIsDisplayed(LocatorAccess.getElement(locator), waitTime);
		}
	}

}
