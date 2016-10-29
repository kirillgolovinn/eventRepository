package com.IgniteEvents.automation.elements;


public interface SelectBox extends TextBox, Element {

	void selectByLabel(String value);

	void selectByIndex(int index);
}
