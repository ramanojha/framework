package com.myproject.qa.testing.framework.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.Select;

import com.myproject.qa.testing.framework.exceptions.ScriptException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;


public class BrowserAction extends InstanceAccess{

	public static final String WEBDRIVER = "WebDriver";
	public static final String JAVASCRIPT = "JavaScript";
	public static final String NEWLINE = "NewLine";
	public static final String CTRL_A_DEL = "CtrlADel";
	public static final String CTRL_A_DEL_TAB = "CtrlADelTab";
	public static final String KEYS = "SendKeys";
	public static final String CTRL_A_KEYS = "CtrAKeys";

	public static String parentWindowHandler = null;

	public static void openBrowser(String url) throws Exception {
		driver.get(url);
	}

	public static String getCurrentUrl() throws Exception {
		return driver.getCurrentUrl();
	}
	public static void closeBrowser() {
		driver.quit();
	}
	public static void refresh() throws Exception {
		driver.navigate().refresh();
	}

	public static void click(Object locator, String... style) 	throws Exception {
		WebElement el = LocatorAccess.getElement(locator);	
		if(style.length >0){
			switch (style[0]) {
			case JAVASCRIPT:
				jsDriver.executeScript("(arguments[0]).click()", el);
				break;
			}
		}else{
			el.click();
		}
	}

	public static void clear( Object locator, String... style) 	throws Exception {
		WebElement element = LocatorAccess.getElement(locator);	
		if (style.length >0) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a")); 
			element.sendKeys(Keys.DELETE);
			if(style[0].equals(CTRL_A_DEL)){

			}else if(style[0].equals(CTRL_A_DEL_TAB)){	
				element.sendKeys(Keys.TAB);
			}
		}
		else {
			element.clear();
		}
	}

	public static void clickAndClear( Object locator, String style1, String style2) 	throws Exception {
		WebElement element = LocatorAccess.getElement(locator);
		click(element, style1);
		clear(element, style2);
	}

	public static void clickAndClear( Object locator) 	throws Exception {
		WebElement element = LocatorAccess.getElement(locator);
		click(element);
		clear(element);
	}
	
	public static void enterFieldValue( Object locator, String value, String clickStyle, String sendStyle) throws Exception {
		WebElement el = LocatorAccess.getElement(locator);	
		if(clickStyle.equals(JAVASCRIPT) && sendStyle.equals(CTRL_A_KEYS)) {
			click(el, JAVASCRIPT);
			el.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			el.sendKeys(value);
		}
		else if(sendStyle.equals(KEYS)) {
			el.click();
			el.sendKeys(value);
		} else if(sendStyle.equals(JAVASCRIPT))  {
			jsDriver.executeScript("document.getElementById('"+el+"').value = '"+value+"';");
		}
	}

	
	public static void enterFieldValue( Object locator, String value) throws Exception {
		LocatorAccess.getElement(locator).click();
		LocatorAccess.getElement(locator).sendKeys(value);
	}
	
	public static void selectDropDownOptionByValue( Object locator, String value) throws Exception {
		Select select = new Select(LocatorAccess.getElement(locator));
		select.selectByValue(value);
	}

	public static void selectDropDownOptionByText(Object locator, String text) throws Exception {
		Select select = new Select(LocatorAccess.getElement(locator));
		select.selectByVisibleText(text);
	}

	public static void hoverOverElement(Object locator, String... styles) throws Exception {
		WebElement element = LocatorAccess.getElement(locator);
		String click = styles.length > 0 ? styles[0] : WEBDRIVER; // default is standard click 
		if (click.equals(JAVASCRIPT)){ 
			jsDriver.executeScript("$(arguments[0]).mouseover();", element);
		} else { 
			new Actions(driver).moveToElement(element).build().perform();	
		}
	}

	public  static void maximizeCurrentWindow() throws Exception {		
		if( browser.equalsIgnoreCase("ie") || browser.equalsIgnoreCase("firefox") || browser.equalsIgnoreCase("chrome"))
			driver.manage().window().maximize();
	}

	public  static void goBackToPrevious() throws Exception {		
		if(browser.equalsIgnoreCase("ie") || browser.equalsIgnoreCase("firefox") || browser.equalsIgnoreCase("chrome"))
			driver.navigate().back();
	}

	public static void closeActiveWindow() throws Exception {
		driver.close();
	}

	public static void closeAllExceptActiveTab() throws Exception {
		String originalHandle = driver.getWindowHandle();
		for(String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}
		driver.switchTo().window(originalHandle);
	}

	public static void switchToTabByName(String tabName) throws Exception {
		parentWindowHandler = driver.getWindowHandle();
		for(String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			if (driver.getTitle().equals(tabName))
				break;
		}
	}


	public static void switchToFrame(Object nameOrNumberOrLocator) throws Exception {
		if(nameOrNumberOrLocator instanceof String)
			driver.switchTo().frame((String)nameOrNumberOrLocator);
		else if(nameOrNumberOrLocator instanceof Integer)
			driver.switchTo().frame((int) nameOrNumberOrLocator);	
		else
			driver.switchTo().frame(LocatorAccess.getElement(nameOrNumberOrLocator));
	}

	public static void switchToDefaultContent() throws Exception {
		driver.switchTo().defaultContent();
	}

	public static Alert switchToAlertBox() throws Exception{
		return driver.switchTo().alert();
	}

	public static void switchToNewTabTest() throws Exception {
		switchToLastTab();
	}

	public static void switchToParentTab() throws Exception {
		driver.switchTo().window(parentWindowHandler);	
	}

	public static void switchToLastTab() throws Exception {
		switchToTabByIndex(driver.getWindowHandles().size()-1);
	}

	public static void closeLastTab() throws Exception {
		closeTabByIndex(driver.getWindowHandles().size()-1);
	}

	public static void closeFirstTab() throws Exception {
		closeTabByIndex(driver.getWindowHandles().size()-1);
	}

	public static void closeTabByIndex(int index) throws Exception {
		parentWindowHandler = driver.getWindowHandle();
		List<String> handles = new ArrayList<String>(driver.getWindowHandles());
		if(parentWindowHandler.equals(handles.get(index))){
			if(driver.getWindowHandles().size() >1){
				ScriptLogger.info("Closed Tab is: " +driver.getTitle());
				driver.close();
				switchToFirstTab();
			}
			else{
				throw new ScriptException("Closing only tab");
			}
		}
		else{
			driver.switchTo().window(handles.get(index));
			ScriptLogger.info("Closed Tab is: " +driver.getTitle());
			driver.close();
			driver.switchTo().window(parentWindowHandler);
		}
	}

	public static void switchToFirstTab() throws Exception {
		switchToTabByIndex(0);
	}

	public static void switchToTabByIndex(int index) throws Exception {
		parentWindowHandler = driver.getWindowHandle();
		List<String> handles = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(handles.get(index));
		ScriptLogger.info("Current page is: " +driver.getTitle());
	}

	public static void deleteAllCookies() throws Exception {
		driver.manage().deleteAllCookies();
	}

	public static void scrollPageToTop() throws Exception{
		jsDriver.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public static void scrollElementDown(Object element) throws Exception{
		jsDriver.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	//scroll into view
	public static void scrollElementIntoView(Object locator) throws Exception{
		jsDriver.executeScript("arguments[0].scrollIntoView(true);", LocatorAccess.getElement(locator));
		Thread.sleep(500); 
	}

	public static boolean verifyActiveElementTest(Object element) throws Exception{
		return driver.switchTo().activeElement().equals(LocatorAccess.getElement(element));
	}	


	public static ScreenOrientation getDeviceOrientation() throws Exception {
		WebDriver d = new Augmenter().augment(driver);
		Rotatable rotator = ((Rotatable) d);
		return rotator.getOrientation();
	}


	public static void multiSelect( Object locator,  String... textItems) throws Exception {
		Select select = new Select(LocatorAccess.getElement(locator));
		for (String textItem:textItems){
			select.selectByVisibleText(textItem);
		}

	}

	public static void doubleClick(Object el) throws Exception{
		Actions action = new Actions(driver);
		action.doubleClick(LocatorAccess.getElement(el)).perform();
	}

	public static void rightClick(Object el) throws Exception{
		Actions actions = new Actions(driver);    
		actions.contextClick(LocatorAccess.getElement(el)).build().perform();
	}

	public static void dragAndDrop(Object source, Object target) throws Exception {
		Actions builder = new Actions(driver);
		builder.clickAndHold(LocatorAccess.getElement(source))
		.moveToElement(LocatorAccess.getElement(target))
		.release(LocatorAccess.getElement(target)).build().perform();
	}

	public static void dragAndDrop( Object source,  int xOffset,  int yOffset)
			throws Exception {
		Actions builder = new Actions(driver);
		builder.clickAndHold(LocatorAccess.getElement(source))
		.moveByOffset(xOffset, yOffset)
		.release().build().perform();
	}

	public static String getFullCurrentUrl() throws Exception {
		try {
			Thread.sleep(3000);
			return jsDriver.executeScript("return window.location.href").toString();
		} catch (Exception e) {
			throw new ScriptException(e);
		}

	}
	public static void enterField(Object locator,String value) throws Exception {
		LocatorAccess.getElement(locator).sendKeys(value);	
	}

	public static void openElementInNewTab(Object locator) throws Exception {
		String keyString = Keys.CONTROL+Keys.SHIFT.toString()+Keys.ENTER.toString();
		LocatorAccess.getElement(locator).sendKeys(keyString);
		switchToNewTabTest();
	}

	public static void launchUrlInNewTab(String url) throws Exception {
		jsDriver.executeScript("window.open('"+url+"','_blank');");
		switchToNewTabTest();
	}
}
