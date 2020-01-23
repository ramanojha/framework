package com.myproject.qa.testing.framework.selenium;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class BrowserAssert extends InstanceAccess {

	public static void assertAllElementsDisplayed(Object locator) throws Exception {
		assertAllElementsDisplayed(LocatorAccess.getElements(locator));	
	}

	public static void assertAllElementsDisplayed(List<WebElement> elements) throws Exception {
		for(WebElement element :elements) {
			assertElementIsDisplayed(element);
		}	
	}

	public static void assertElementIsDisplayed(Object element) throws Exception{
		assertTrue(LocatorAccess.getElement(element).isDisplayed(), "Element is not displayed");
	}

	public static void assertElementIsSelected(Object element) throws Exception {
		assertTrue(LocatorAccess.getElement(element).isSelected(), "Element is selected");
	}

	public static void assertElementIsNotSelected(Object element) throws Exception {
		assertTrue(!LocatorAccess.getElement(element).isSelected(), "Element is not selected");
	}

	public static void assertAllElementsSelected(Object locator) throws Exception {
		assertAllElementsSelected(LocatorAccess.getElements(locator));
	}

	public static void assertAllElementsSelected(List<WebElement> list) throws Exception {
		for(WebElement element : list){
			assertTrue(!element.isSelected(), "Element is selected with locator");
		}
	}

	public static void assertAllElementsNotSelected(Object locator) throws Exception {
		assertAllElementsNotSelected(LocatorAccess.getElements(locator));
	}

	public static void assertAllElementsNotSelected(List<WebElement> list) throws Exception {
		for(WebElement element : list){
			assertTrue(element.isSelected(), "Element is selected with locator");
		}
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
}
