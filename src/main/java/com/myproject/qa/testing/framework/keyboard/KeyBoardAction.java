package com.myproject.qa.testing.framework.keyboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;

import com.myproject.qa.testing.framework.annotations.Aim;
import com.myproject.qa.testing.framework.exceptions.FrameworkException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.selenium.LocatorAccess;

public class KeyBoardAction {
	@Aim("Open New tab by pressing 'ctrl + t'")
	public static void openNewTabUsingKeyBoard() throws Exception {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);

		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to open new tab using Keyboard");
		}
	}
	
	@Aim("Press the 'Tab' Key")
	public static void presTabKey(Object locator) throws Exception {
		LocatorAccess.getElement(locator).sendKeys(Keys.TAB);
	}
	
	@Aim("Press 'ESC' key")
	public static void pressESCTest() throws Exception {
		ScriptLogger.info();
		try {

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_ESCAPE);

		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
	@Aim("To press 'alt + F4'")
	public static void pressAltF4Test() throws Exception {
		ScriptLogger.info();
		try {

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_F4);

		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
	
	@Aim("To do tab out by pressing tab key")
	public static void tabOut() throws Exception {
		ScriptLogger.info();
		
		try {
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);	
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		
	}
	
	@Aim("To do tab out by pressing Enter key")
	public static void pressEnterKey() throws Exception {
		ScriptLogger.info();
		
		try {
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);	
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		
	}
}

