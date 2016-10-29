package com.IgniteEvents.automation.elements.impl;

import org.openqa.selenium.Keys;

public class ContentEditTextBoxImpl extends TextBoxImpl {

	String iframeXpath;

	public ContentEditTextBoxImpl(String iframePath, String elementPath, String elementName, boolean isReq) {
		super(elementPath, elementName, isReq);
		iframeXpath = iframePath;
	}

	public void type(String value) {
		switchToFrame(findElementByXpath(iframeXpath));
		sendKeys(path, Keys.TAB);
		type(path, value);
		switchToDefaultContent();

	}

}
