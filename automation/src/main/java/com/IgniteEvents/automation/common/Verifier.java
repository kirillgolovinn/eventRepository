package com.IgniteEvents.automation.common;

import junit.framework.AssertionFailedError;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import  com.IgniteEvents.automation.elements.Element;


public class Verifier {

	private static final Logger logger = SeleneseTestCase.logger;

	public void verifyElementIsDisplayed(Element... element) {
		verifyElementIsDisplayed(false, element);
	}
	
	public void verifyElementIsDisplayed(boolean fail, Element... element) {
		if (element != null) {
			for (Element e : element) {
				verifyTrue(e.isDisplayed(), e.getName() + " is not displayed.", fail);
			}
		}
	}
	
	public void verifyElementIsNotDisplayed(Element... element) {
		verifyElementIsNotDisplayed(false, element);
	}
	
	public void verifyElementIsNotDisplayed(boolean fail, Element... element) {
		if (element != null) {
			for (Element e : element) {
				verifyTrue(e.isNotDisplayed(), e.getName() + " still exists.", fail);
			}
		}
	}
	
	public void verifyElementIsVisible(Element... element) {
		if (element != null) {
			for (Element e : element) {
				verifyTrue(e.isVisible(), e.getName() + " is not visible.", false);
			}
		}
	}
	
	public void verifyElementIsNotVisible(Element... element) {
		verifyElementIsNotVisible(false, element);
	}
	
	public void verifyElementIsNotVisible(boolean fail, Element... element) {
		if (element != null) {
			for (Element e : element) {
				verifyFalse(e.isVisible(), e.getName() + " still exists.", fail);
			}
		}
	}

	public void verifyEquals(Object actual, Object expected) {
		verifyEquals(actual, expected, "", false);
	}
	
	public void verifyTrue(Object actual, String message) {
		verifyTrue(actual, message, false);
	}

	public void verifyTrue(Object actual, String message, boolean fail) {
		verifyEquals(actual, true, message, fail);
	}
	
	public void verifyFalse(Object actual, String message) {
		verifyFalse(actual, message, false);
	}

	public void verifyFalse(Object actual, String message, boolean fail) {
		verifyEquals(actual, false, message, fail);
	}

	public void verifyEquals(Object actual, Object expected, String message) {
		verifyEquals(actual, expected, message, false);
	}

	public void verifyNotEquals(Object actual, Object expected, String message) {
		verifyNotEquals(actual, expected, message, true);
	}

	public void verifyEquals(Object actual, Object expected, String message, boolean fail) {
		message = message.replace(".", "_");
		logger.debug("Check equality of two objects. ");
		try {
			Assert.assertEquals(actual, expected);
			SeleneseTestCase.bug.add("Success " + ". Expected [" + expected + "] was [" + actual + "]");
		} catch (AssertionError e) {
			if (fail) {
				throw new AssertionFailedError(message + " - " + e.getMessage());
			} else {
				SeleneseTestCase.bug.add("Error " + message + ". Expected [" + expected + "] but was [" + actual + "]" + " <a href='file:///" + SeleneseTestCase.makeScreenshot(message).getAbsolutePath() + "'> Screenshot </a>");
				logger.error("Verification error: " + message + " - " + e.getMessage());
				CommonUtils.setParam("testResult", "fail");
			}
		}
	}

	public void verifyNotEquals(Object actual, Object expected, String message, boolean Fail) {
		try {
			Assert.assertNotEquals(actual, expected);
		} catch (AssertionError e) {
			CommonUtils.setParam("testResult", "fail");
			if (Fail) {
				throw new AssertionFailedError(message + " - " + e.getMessage());
			} else {
				logger.error("Verification error: " + message + " - " + e.getMessage());
			}
		}
	}
}
