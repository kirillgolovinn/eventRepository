package com.IgniteEvents.automation.elements.impl;

import com.IgniteEvents.automation.elements.CheckBox;


public class CheckBoxImpl extends ElementImpl implements CheckBox {

	public CheckBoxImpl(String elementPath, String elementName) {
		super(elementPath, elementName);
	}

	@Override
	public void check() {
		logger.info(elementName + " was checked.");
		super.check(path, true);

	}
	
	@Override
	public void check(boolean isCheck) {
		logger.info(elementName + " was checked.");
		super.check(path, isCheck);	
	}

	@Override
	public void unCheck() {
		logger.info(elementName + " was unchecked.");
		super.check(path, false);

	}

	@Override
	public void changeState() {
		logger.info("State for" + elementName + " was chenged.");
		if (isChecked()) {
			unCheck();
		}else{
			check();
		}
	}

	@Override
	public boolean isChecked() {
		logger.info(elementName + " was checked.");
		return findElementByXpath(path).isSelected();
	}
}
