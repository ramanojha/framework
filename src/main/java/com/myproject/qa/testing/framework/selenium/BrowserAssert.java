package com.myproject.qa.testing.framework.selenium;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.myproject.qa.testing.framework.exceptions.ApplicationException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.utils.ImageUtils;

public class BrowserAssert extends InstanceAccess {

	public static boolean ifElementIsDisplayed(Object element) throws Exception{
		try {
			return LocatorAccess.getElement(element).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	public static void assertElementIsDisplayed(Object element) throws Exception{
		assertTrue(ifElementIsDisplayed(element), "Element is not displayed");
	}

	public static boolean ifAllElementsDisplayed(List<WebElement> elements) throws Exception {
		for(WebElement element :elements) {
			if(!ifElementIsDisplayed(element)){
				return false;
			}
		}
		return true;
	}

	public static boolean ifAllElementsDisplayed(Object locator) throws Exception {
		return ifAllElementsDisplayed(LocatorAccess.getElements(locator));
	}

	public static void assertAllElementsDisplayed(Object locator) throws Exception {
		assertAllElementsDisplayed(LocatorAccess.getElements(locator));	
	}

	public static void assertAllElementsDisplayed(List<WebElement> elements) throws Exception {
		assertTrue(ifAllElementsDisplayed(elements), "All Elements are not displayed");
	}

	public static boolean ifElementIsSelected(Object element) throws Exception {
		try {
			return LocatorAccess.getElement(element).isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	public static void assertElementIsSelected(Object element) throws Exception {
		assertTrue(ifElementIsSelected(element), "Element is selected");
	}

	public static void assertElementIsNotSelected(Object element) throws Exception {
		assertFalse(ifAllElementsDisplayed(element), "Element is not selected");
	}

	public static void assertAllElementsSelected(Object locator) throws Exception {
		assertAllElementsSelected(LocatorAccess.getElements(locator));
	}

	public static void assertAllElementsSelected(List<WebElement> list) throws Exception {
		assertTrue(ifAllElementsSelected(list), "Element is selected with locator");
	}

	public static boolean ifAllElementsSelected(Object locator) throws Exception {
		return ifAllElementsSelected(LocatorAccess.getElements(locator));
	}

	public static boolean ifAllElementsSelected(List<WebElement> list) throws Exception {
		for(WebElement element : list){
			if(!ifElementIsSelected(element))
				return false;
		}
		return true;
	}

	public static void assertAllElementsNotSelected(Object locator) throws Exception {
		assertAllElementsNotSelected(LocatorAccess.getElements(locator));
	}

	public static void assertAllElementsNotSelected(List<WebElement> list) throws Exception {
		assertTrue(ifAllElementsSelected(list), "Some elements are selected.");
	}

	public static <T> void assertEquals(T actual, T expected, String message) throws Exception {
		Assert.assertEquals(actual, expected, message); 
	}

	public static void assertTrue(Boolean condition, String message) throws Exception {
		Assert.assertTrue(condition, message);
	}

	public static void assertFalse(Boolean condition, String message) throws Exception {
		Assert.assertFalse(condition, message);	
	}

	public static <T> void assertEquals(T actual, T expected) {
		Assert.assertEquals(actual, expected);	
	}

	public static void assertElementsSizeIsNotZero(Object locator) throws Exception {
		assertElementsSizeIsNotZero(LocatorAccess.getElements(locator));
	}

	public static void assertElementsSizeIsNotZero(List<WebElement> list) throws Exception {
		assertEquals(list.size(), 0, " Size not is Zero");	
	}

	public static void assertTotalKeywordDisplayedOnHTML(String keyword, int i) throws Exception {

		int count = getTotalMatchedKeywordInPageSource(BrowserAccess.getPageSource(), keyword);
		assertEquals(count, i);	
	}

	public static int getTotalMatchedKeywordInPageSource(String bodyText, String keyword) {
		int count = 0;
		while(bodyText.contains(keyword)){
			int index = bodyText.indexOf(keyword)+ keyword.length();
			bodyText = bodyText.substring(index);
			index = -1;
			count++;
		}
		return count;
	}

	public static void assertTextsNAcceptInAlert(String... alertTexts) throws Exception {

		Alert alert=BrowserAction.switchToAlertBox();
		for(String text : alertTexts){
			BrowserAssert.assertTrue(alert.getText().contains(text), "Application exception: Invalid alert message text"+text);	
		}
		alert.accept();
		BrowserAction.switchToDefaultContent();
	}

	public static void assertTextsNDismissInAlert(String... alertTexts) throws Exception {

		Alert alert=BrowserAction.switchToAlertBox();
		for(String text : alertTexts){
			BrowserAssert.assertTrue(alert.getText().contains(text), "Application exception: Invalid alert message text"+text);	
		}
		alert.dismiss();
		BrowserAction.switchToDefaultContent();
	}

	public static boolean assertActiveElementTest(Object element) throws Exception{
		return driver.switchTo().activeElement().equals(LocatorAccess.getElement(element));
	}

	public static void assertElementIsChecked(Object locator) throws Exception {
		assertEquals(LocatorAccess.getElement(locator).getAttribute("checked"), "true", "WebElement is not checked");
	}
	public static void assertElementIsNotChecked(Object locator) throws Exception {
		assertAttributeNotEquals(locator, "checked", "_blank", "Link will not open in new tab.");
	}

	public static void assertAttributeEquals(Object locator, String attribute, String value) throws Exception {
		assertAttributeEquals(locator, attribute, value, "WebElement attribute is not matching" );
	}

	public static void assertAttributeEquals(Object locator, String attribute, String value, String message) throws Exception {
		assertEquals(LocatorAccess.getElement(locator).getAttribute(attribute), value, message);
	}
	
	public static void assertAttributeNotEquals(Object locator, String attribute, String value, String message) throws Exception {
		assertTrue(!LocatorAccess.getElement(locator).getAttribute(attribute).equals(value), message);
	}
	
	public static void assertAttributeNotEquals(Object locator, String attribute, String value) throws Exception {
		assertAttributeNotEquals(locator, attribute, value, "Web Element attribute is not equal");
	}
	
	public static void assertLinkOpenableInNewTabTest(Object locator) throws Exception {
		assertAttributeEquals(locator, "target", "_blank", "Link will not open in new tab.");
	}


	public static void verifyPageTitleOfOpenableLinkInNewTabTest(Object locator, String pageTitle) throws Exception {
		String link;
		WebElement element  = LocatorAccess.getElement(locator);
		String url = BrowserAction.getCurrentUrl();
		String hostname = url.split("/")[0]+"//"+url.split("/")[2]+"/";
		link = (element.getAttribute("href").contains(hostname))? element.getAttribute("href") : hostname+element.getAttribute("href") ;

		assertLinkOpenableInNewTabTest(link, pageTitle);

	}

	public static void assertLinkOpenableInNewTabTest(String link, String pageTitle) throws Exception {
		URL url = new URL(link.trim());
		HttpURLConnection connection =(HttpURLConnection)url.openConnection();
		connection.setRequestProperty("Content-Type", "text/html; charset=utf-8");
		int status = 0;
		boolean hastitle;
		try {
			connection.connect();
			status = connection.getResponseCode();
			hastitle = false;

			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = br.readLine()) != null){
				if(line.contains(pageTitle)) {
					hastitle = true;
					break;
				}
			}
		} 
		catch (Exception e) {	
			ScriptLogger.info("Link Not working : "+status+" "+link);
			throw new ApplicationException("Link Not working : "+status+" "+link);
		}
		finally {
			connection.disconnect();
		}
		BrowserAssert.assertTrue(hastitle && status == 200, "Link Not working : "+status+" "+link);
	}

	public static void assertUrlContainsText(String string) throws Exception {
		assertTrue(!BrowserAction.getCurrentUrl().contains(string),string+"not found in "+BrowserAction.getFullCurrentUrl());
	}

	public static boolean ifTextFound(String... texts) {
		boolean status = true;
		for (String text : texts) {
			try {
				BrowserWait.waitUntilText(text);
			} catch (Exception e) {
				ScriptLogger.info(text + " not matched in body");
				status = false;
				break;
			} 
		}
		return status;	
	}

	public static void assertBase64URLContainsText(String string) throws Exception {
		boolean status = !ImageUtils.decodeBase64(BrowserAction.getFullCurrentUrl().split("q=")[1]).contains(string);
		assertTrue(status, string+" String not found in URL");
	}

	
	public static boolean isTextBoxEmpty(Object element) throws Exception{
		String textInsideInputBox = LocatorAccess.getElement(element).getAttribute("value");
		return (textInsideInputBox.isEmpty())? true : false;	
	}
	public static void assertTextBoxIsEmpty(Object element) throws Exception{
		assertTrue(isTextBoxEmpty(element), "Text box is not empty");
	}
}
