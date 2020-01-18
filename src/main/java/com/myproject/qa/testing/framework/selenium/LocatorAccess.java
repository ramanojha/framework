package com.myproject.qa.testing.framework.selenium;

import java.util.List;

import javax.lang.model.element.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.myproject.qa.testing.framework.exceptions.LocatorNotResolvedException;
import com.myproject.qa.testing.framework.exceptions.LocatorTypeNotResolvedException;

public class LocatorAccess {
	public static By getLocator(Object element) throws Exception {
		
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
			if(locator instanceof Enum)
				return WebDriverAccess.getDriver().findElement(getLocator(locator));
			else if(locator instanceof By)
				return WebDriverAccess.getDriver().findElement((By) locator);
			else
				return (WebElement) locator;
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found -" +((Enum<?>)locator).name());
		}
	}
	
	public static List<WebElement> getElements(Object locator) throws Exception {	
		try {
			if(locator instanceof Enum)
				return WebDriverAccess.getDriver().findElements(getLocator(locator));
			if(locator instanceof By)
				return WebDriverAccess.getDriver().findElements((By) locator);
		} catch (Exception e) {
			throw new LocatorNotResolvedException(e, "Locator not Found -"+((Enum<?>)locator).name());
		}
		return null;
	}
}
