package com.IgniteEvents.automation.elements;

import org.openqa.selenium.WebElement;

public interface Element {

	boolean isVisible();

	boolean isEnabled();

	boolean isDisplayed();

	boolean isNotDisplayed();

	void highlight();

	String getPath();

	void changePath(String old, String newPath);

	void scrollIntoView();

	void addPath(String pathToAdd);

	void setImplicity(int cTimeOut);

	void click();

	void clickJS();

	void clickByTABKey();
	
	void clickByENTERKey();

	boolean waitElement();
	
	boolean waitElement(int seconds);

	String getName();

	boolean isNotExists();

	void clickByNumber(Integer number);

	String getAttribute(String attrName);

	void moveAndClick();

	WebElement getLastElement();

	String setAttribute(String attName, String attValue);

	void removeAttribute(String string);

	boolean waitForNotExists(Integer timeOut);
	
	boolean waitForNotVisible(Integer timeOut);

	void focus();
}
