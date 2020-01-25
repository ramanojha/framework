package com.myproject.qa.testing.framework.keyboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;

import com.myproject.qa.testing.framework.exceptions.ScriptException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;
import com.myproject.qa.testing.framework.selenium.LocatorAccess;

public class KeyBoardAction {
	public static void openNewTabUsingKeyBoard() throws Exception {
		ScriptLogger.info();
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);

		} catch (Exception e) {
			throw new ScriptException(e);
		}
	}
	
	public static void presTabKey(Object locator) throws Exception {
		ScriptLogger.info();
		LocatorAccess.getElement(locator).sendKeys(Keys.TAB);
	}
	
	public static void pressESCTest() throws Exception {
		ScriptLogger.info();
		try {

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			Thread.sleep(2000);
			robot.keyRelease(KeyEvent.VK_ESCAPE);

		} catch (Exception e) {
			throw new ScriptException(e);
		}
	}

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
			throw new ScriptException(e);
		}
	}
	
	public static void tabOut() throws Exception {
		ScriptLogger.info();
		
		try {
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);	
		} catch (Exception e) {
			throw new ScriptException(e);
		}
		
	}
}
