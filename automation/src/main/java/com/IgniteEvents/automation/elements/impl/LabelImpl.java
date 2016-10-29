package com.IgniteEvents.automation.elements.impl;

import com.IgniteEvents.automation.elements.Label;

public class LabelImpl extends ElementImpl implements Label {

	public LabelImpl(String elementPath, String elementName) {
		super(elementPath, elementName);
	}

	public Integer getElementsAmount() {
		return findElementsByXpath(path).size();
	}

	@Override
	public String getText() {
		logger.info("Get text from " + elementName);
		logger.info("Text is " + super.getText(path));
		return super.getText(path);
	}
}
