package com.IgniteEvents.automation.elements.impl;

import java.io.File;

import org.openqa.selenium.interactions.Actions;

import com.IgniteEvents.automation.common.SeleneseTestCase;
import com.IgniteEvents.automation.elements.TextBox;

public class TextBoxImpl extends ElementImpl implements TextBox {

	public TextBoxImpl(String elementPath, String elementName) {
		super(elementPath, elementName);
	}

	public TextBoxImpl(String elementPath, String elementName, boolean isReq) {
		super(elementPath, elementName, isReq);
	}

	public void focus() {
		logger.info("Focus on " + elementName);
		new Actions(driver).click(findElementByXpath(path)).build().perform();

	}

	@Override
	public String getText() {
		logger.info("Get text from " + elementName);
		return super.getText(path);
	}

	@Override
	public String getValue() {
		logger.info("Get value from " + elementName);
		return super.getValue(path);
	}

	@Override
	public void type(String value) {
		logger.info("Type " + value + " in to the " + elementName);
		SeleneseTestCase.bug.add("Type " + value + " in to the " + elementName);
		super.type(path, value);
	}

	@Override
	public void clear() {
		logger.info("Clear the " + elementName);
		super.clearTextField(path);
	}

	@Override
	public void uploadAssetsImage(String filePath, String imageName) {

		logger.info("Trying to upload to Assets image called " + imageName);
		File image = new File(filePath);
		if (image.exists()) {
			setAttribute("class", "ng ng-valid ng-dirty");
			type(image.getAbsolutePath());
			logger.info("Image called " + imageName + " is being uploaded to Assets");
		} else {
			logger.error("Image called " + imageName + " was NOT found in /images directory");
		}

	}

}
