package com.myproject.qa.testing.framework.selenium;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.Select;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.exceptions.ScriptException;
import com.myproject.qa.testing.framework.keyboard.KeyBoardAction;
import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.properties.SeleniumProperties;
import com.myproject.qa.testing.framework.utils.ShellUtils;


public class BrowserAction extends InstanceAccess{

	public static final String WEBDRIVER = "WebDriver";
	public static final String JAVASCRIPT = "JavaScript";
	public static final String NEWLINE = "NewLine";
	public static final String CTRL_A_DEL = "CtrlADel";
	public static final String CTRL_A_DEL_TAB = "CtrlADelTab";
	public static final String KEYS = "SendKeys";
	public static final String CTRL_A_KEYS = "CtrAKeys";
	public static final String DRAG_N_DROP_CLICK_N_HOLD = "clickAndHold";
	public static String parentWindowHandler = null;
	public static String TERMINAL_TYPE = "CMD";

	public static void openBrowser(String url) throws Exception {
		driver.get(url);
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
		Object element = LocatorAccess.getElement(locator);
		click(element, style1);
		clear(element, style2);
	}

	public static void clickAndClear( Object locator) 	throws Exception {
		Object element = LocatorAccess.getElement(locator);
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

	public static void dragAndDrop(Object source, Object target, String... style) throws Exception {
		Actions builder = new Actions(driver);
		if (style.equals(DRAG_N_DROP_CLICK_N_HOLD)) {
			builder.clickAndHold(LocatorAccess.getElement(source))
					.moveToElement(LocatorAccess.getElement(target))
					.release(LocatorAccess.getElement(target)).build()
					.perform();
		}else{
			builder.dragAndDrop(LocatorAccess.getElement(source), LocatorAccess.getElement(target)).build().perform();
		}
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
	

	public static void launchURL(String url) {
		driver.get(url);	
	}

	public static void navigateURL(String url) {
		driver.navigate().to(url);
	}

	
	public static void forwardPage(String url) {
		driver.navigate().forward();
	}

	public static void backPage(String url) {
		driver.navigate().back();
	}

	public static void maximizeWindow() {
		driver.manage().window().maximize();
	}
	
	public static void takeScreenShot(String path) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(path));
	}

	public static void takeScreenShot() throws IOException{
		TakesScreenshot ss = (TakesScreenshot) driver;
		File src = ss.getScreenshotAs(OutputType.FILE);

		String pngFileName = new SimpleDateFormat("yyyyMMddHHmm'.png'").format(new Date());
		String screenshotFolder = System.getProperty("user.dir") + "/Screenshots/"+pngFileName;
		FileUtils.copyFile(src, new File(screenshotFolder));

	}


	public static void uploadFile(Object upload, Object uploadFileButton, String filepath) throws Exception {
		LocatorAccess.getElement(upload).sendKeys(filepath);
		LocatorAccess.getElement(uploadFileButton).click();	
	}
	
	public static void downloadFile(String downLoadDestination, Object downloadButton, String ...teminaltype) throws Exception {
		//Sample in bash : /usr/local/bin/wget -P /Users/ramanojha/Documents/java-utils --no-check-certificate https://resumegenius.com/wp-content/uploads/2014/07/Template-DL-Basic-Classic-RWD.zip
		//Sample in CMD  : cmd /c C:\\Wget\\wget.exe -P D: --no-check-certificate http://demo.guru99.com/selenium/msgr11us.exe
		String sourceLocation = LocatorAccess.getElement(downloadButton).getAttribute("href");
		String terminal, type ;
		if(teminaltype.length ==0 || teminaltype[0].equals("cmd")){
			terminal = "cmd /c "+SeleniumProperties.WGET_EXE_PATH.getValue();
			type = "CMD";
		}
		else{
			terminal = "/usr/local/bin/wget";
			type = teminaltype[0];
		}
		String wgetCommand = terminal+" -P "+downLoadDestination+" --no-check-certificate "+sourceLocation;
		ShellUtils.execute(type, wgetCommand);

	}

	public static void multiSelectDropDown(Object element, int... indexes) throws Exception {
		Select select = new Select(LocatorAccess.getElement(element));
		select.getOptions();
		select.deselectAll();//first deselecting, if anything selected already
		for(int index : indexes) {
			select.selectByIndex(index);
		}
	}

	public static void multiSelectDropDownByVisibleText(Object element, String ... dropdowns) throws Exception {
		Select select = new Select(LocatorAccess.getElement(element));
		select.getOptions();
		select.deselectAll();
		for(String dropdown : dropdowns) {
			select.selectByVisibleText(dropdown);
		}
	}

	//MultiSelect DropDown
	public static void multiSelectDropDownByValue(Object element, String... dropdowns) throws Exception {
		Select select = new Select(LocatorAccess.getElement(element));
		select.getOptions();
		select.deselectAll();
		for(String dropdown : dropdowns) {
			select.selectByValue(dropdown);
		}
	}

	//Get select dropdowns
	public static List<WebElement> getSelectedDropDowns(Object element) throws Exception {
		Select select = new Select(LocatorAccess.getElement(element));
		select.getOptions();
		return select.getAllSelectedOptions();
	}

	//Get first select dropdown
	public static Object getFirstSelectedDropDown(Object element) throws Exception {
		Select select = new Select(LocatorAccess.getElement(element));
		select.getOptions();
		return select.getFirstSelectedOption();
	}

	//Dropdown by Action class
	public static void selectDropDownAction(Object dropdown, Object target) throws Exception {
		Actions actions = new Actions(driver);
		actions.moveToElement(LocatorAccess.getElement(dropdown));
		actions.moveToElement(LocatorAccess.getElement(target)).build();
		actions.click().perform();
	}

	//Mouse over
	public static void mouseOver(Object mouseover) throws Exception {
		Actions actions = new Actions(driver);
		actions.moveToElement(LocatorAccess.getElement(mouseover)).build().perform();
	}

	//Double click
	public static void doubleClick() {
		Actions actions = new Actions(driver);
		actions.doubleClick().build().perform();
	}

	//Right click
	public static void rightClick(Object target, int clickElementPosition) throws Exception {
		Actions actions= new Actions(driver);
		actions.contextClick(LocatorAccess.getElement(target));
		for(int i=1; i <= clickElementPosition; i++) {
			if(clickElementPosition==i)
				actions.sendKeys(Keys.RETURN);
			else
				actions.sendKeys(Keys.ARROW_DOWN);
		}
		actions.build().perform();	
	}


	//Right click
	public static void rightClick(int clickElementPosition) {
		Actions actions= new Actions(driver);
		actions.contextClick();
		for(int i=1; i <= clickElementPosition; i++) {
			if(clickElementPosition==i)
				actions.sendKeys(Keys.RETURN);
			else
				actions.sendKeys(Keys.ARROW_DOWN);
		}
		actions.build().perform();	
	}

	//resize window
	//slider
	public static void resizeWindowByOffset(Object target, int xOffset, int yOffset) throws Exception {
		Actions actions = new Actions(driver);
		actions.clickAndHold(LocatorAccess.getElement(target))
		.moveByOffset(xOffset, yOffset)
		.release(LocatorAccess.getElement(target)).build().perform();
	}

	//switch to alert
	public static void swtichToAlert(String acceptOrdismiss) {
		Alert alert = driver.switchTo().alert();
		if(acceptOrdismiss.equals("ACCEPT"))
			alert.accept();  
		else
			alert.dismiss();	
	}	

	//send Keys to Alert
	public static void sendkeysToAlert(String keys) {
		driver.switchTo().alert().sendKeys(keys);  
	}

	//get text of alert
	public static String getAlertText(String keys) {
		return driver.switchTo().alert().getText();
	}

	//previous tab
	public static void previousTab() {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).keyDown(Keys.TAB).build().perform();
	}

	//forward tab
	public static void forwardTab() {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).keyDown(Keys.TAB).build().perform();
	}

	//new tab
	public static void newTab() {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys("t").build().perform();
	}

	//new tab which was closed
	public static void oldTab() {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys("t").build().perform();
	}

	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public static String getTitle() {
		return driver.getTitle();
	}

	public static String getPageSource() {
		return driver.getPageSource();
	}
	
	public static Object activeElements() {
		return driver.switchTo().activeElement();	
	}

	public static void switchParentFrame() {
		driver.switchTo().parentFrame();	
	}

	public static void switchFrame(int index) {
		driver.switchTo().frame(index);	
	}
	
	public static void switchFrame(String frame) {
		driver.switchTo().frame(frame);
	}
	
	public static void switchFrame(Object element) throws Exception {
		driver.switchTo().frame(LocatorAccess.getElement(element));
	}

	public static void switchDefaultContent() {
		driver.switchTo().defaultContent();
	}
	
	//to get color of the Object
	
	
	
	//change color and hold for 20 milli seconds.
	public static void changeColor(String color, Object element ) throws Exception {
		jsDriver.executeScript(" = '"+color+"'",  LocatorAccess.getElement(element));
		Thread.sleep(20);
	}

	//draw border on webpage
	public static void drawBorder(Object element ) throws Exception{
		jsDriver.executeScript("arguments[0].style.border='3px solid red'", LocatorAccess.getElement(element));
	}

	//generate alert
	public static void generateAlert(String message){
		jsDriver.executeScript("alert('"+message+"')");

	}

	//click on any element by using JS executor
	//click on hidden element.
	public static void clickElementByJS(Object element) throws Exception{
		jsDriver.executeScript("arguments[0].click();", LocatorAccess.getElement(element));

	}

	//refresh webpage
	public static void refreshBrowserByJS(){
		jsDriver.executeScript("history.go(0)");
	}

	//get Title
	public static String getTitleByJS(){
		return jsDriver.executeScript("return document.title;").toString();
	}

	// scroll down page till last point
	public static void scrollPageDown(){
		jsDriver.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	// scroll down page till element is not matcheda
	public static void scrollIntoView(Object element ) throws Exception{
		jsDriver.executeScript("arguments[0].scrollIntoView(true);", LocatorAccess.getElement(element));
	}

	//flash the web Element
	public static void flash(Object element ) throws Exception {
		String bgcolor  = LocatorAccess.getElement(element).getCssValue("backgroundColor");
		for (int i = 0; i <  10; i++) {
			changeColor("rgb(0,200,0)", element);//1
			changeColor(bgcolor, element);//2
		}
	}

	public static void openLinkinNewTab(int index, String url) throws Exception{
		jsDriver.executeScript("window.parent = window.open('"+url+"');");
		switchToNewTabTest();
	}


	// click on submenu, which is only visible by mouse hover
	public static void clickSubmenu(Object element){
		jsDriver.executeScript("$('ul.menus.menu-secondary.sf-js-enabled.sub-menu li').hover()", element);
	}

	//scroll down by coordinates
	public static void scrollbyCoordinates(Object element){
		jsDriver.executeScript("window.scrollBy(300,2000);");
	}


	//get URL
	//navigate to different webpage	
	public static void getURL(String url) throws InterruptedException{
		jsDriver.executeScript("window.location = \'"+url+"\'");
	}

	//handle checkbox
	public void checkbox(Object element, boolean checkbox){
		jsDriver.executeScript("arguments[0].checked="+checkbox+";", element);
		//js.executeScript("document.getElementById('enter element id').checked=true;");
	}


	//highlight Object
	public static void highlightObject(Object element ) throws Exception {
		jsDriver.executeScript("arguements[0].setAttribute('style', arguments[1]);", LocatorAccess.getElement(element), 
				"background:yellow; "
						+ "color: Red; "
						+ "border: 4px dotted solid yellow;"
				);
		jsDriver.executeScript("arguements[0].setAttribute('style', arguments[1]);", LocatorAccess.getElement(element), 
				"background:yellow; color: Red; border: 4px dotted solid yellow;");
	}

	//gererate confirm
	public static void generateConfirm(String message){
		jsDriver.executeScript("confirm('"+message+"')");

	}
	//generate prompt
	public static void generatePrompt(String message){
		jsDriver.executeScript("prompt('"+message+"')");

	}

	//Ristrict link to NewTab
	public static void ristrictToNewTab(Object element ) throws Exception{
		jsDriver.executeScript("arguments[0].setAttribute('target','_self');", LocatorAccess.getElement(element));
		LocatorAccess.getElement(element).click();
	}

	//Right click 
	public static void rightClickJS(Object element) throws Exception {
		String javaScript = "var evt = document.createEvent('MouseEvents');"
				+ "var RIGHT_CLICK_BUTTON_CODE = 2;"
				+ "evt.initMouseEvent('contextmenu', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, RIGHT_CLICK_BUTTON_CODE, null);"
				+ "arguments[0].dispatchEvent(evt)";
		jsDriver.executeScript(javaScript, LocatorAccess.getElement(element));	
	}

	//Enter text in disabled text box
	public static Object enableTextbox(Object element) throws Exception {	
		String todisable = "arguements[0].setAttribute('disabled', 'true');";
		jsDriver.executeScript(todisable, LocatorAccess.getElement(element));
		return LocatorAccess.getElement(element);
	}
	
	public static Object disableTextbox(Object element) throws Exception {	
		String todisable = "arguements[0].setAttribute('disabled', 'false');";
		jsDriver.executeScript(todisable, LocatorAccess.getElement(element));
		return LocatorAccess.getElement(element);
	}

	public static void inputDateInField(Object element, String formatedDate) throws Exception {
		jsDriver.executeScript("arguements[0].setAttribute('"+formatedDate+"')", LocatorAccess.getElement(element));
	}
	
	public static void hoverAndClickElement(Object hoverElement, Object clickableLocator, String...style) throws Exception{
		hoverOverElement(hoverElement);
		click(clickableLocator, style);
	}
	
	public static void clickElementFromListWhichHas(Object locator, String value, String... style) throws Exception{
		click(LocatorAccess.getElementFromListWhichHas(LocatorAccess.getElements(locator), value), style);
	}
	
	public static void clickElementFromListWhichHas(List<WebElement> list, String value, String... style) throws Exception {
		click(LocatorAccess.getElementFromListWhichHas(list, value), style);
	}
	
	public static void clickElementFromListWhichContains(Object locator, String value, String... style) throws Exception{
		click(LocatorAccess.getElementFromListWhichContains(LocatorAccess.getElements(locator), value), style);
	}
	
	public static void getElementFromListWhichContains(List<WebElement> list, String value, String...style) throws Exception {
		click(LocatorAccess.getElementFromListWhichContains(list, value), style);
	}
	
	@Aim("Hit Allow on multiple File Download pop up")
	public static void allowMultipleFileDownload() throws Exception{
		KeyBoardAction.tabOut(); Thread.sleep(2000);
		KeyBoardAction.tabOut(); Thread.sleep(2000);
		KeyBoardAction.tabOut(); Thread.sleep(2000);
		KeyBoardAction.pressEnterKey();
	}
}
