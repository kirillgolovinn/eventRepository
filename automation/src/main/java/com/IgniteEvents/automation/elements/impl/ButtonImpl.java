package com.IgniteEvents.automation.elements.impl;

import com.IgniteEvents.automation.common.SeleneseTestCase;

import com.IgniteEvents.automation.elements.Button;


public class ButtonImpl extends ElementImpl implements Button {
	
	
	public ButtonImpl(String elementPath, String elementName) {
		super(elementPath, elementName);
	}
	
	public ButtonImpl(String elementPath, String elementName, boolean isRequired) {
		super(elementPath, elementName, isRequired);
	}

	public ButtonImpl setLastElementPath() {
		logger.info(elementName + " was clicked.");
		path = path+ "[" + findElementsByXpath(path).size() + "]";
		path = path.replace("[0]", "");
		path = path.replace("][", "  and ");
		return this;
	}
	
	@Override
	public void onClick() {
		SeleneseTestCase.bug.add("Click on " + elementName);
		super.onClick(findElementByXpath(path));
		logger.info(elementName + " was clicked.");
	}

	@Override
	public void submit() {
		logger.info(elementName + " was submitted.");
		super.submit(path);
	}

	@Override
	public String getLabel() {
		logger.info("Label for " + elementName + " was returned.");
		return super.getText(path);
	}
}
