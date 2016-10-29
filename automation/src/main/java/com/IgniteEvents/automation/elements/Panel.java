package com.IgniteEvents.automation.elements;


public interface Panel extends Element {

	int isValueExists(String addressLine1);

	Integer getElementsCount();

	String getChildElementPath(String elementPath, int setNumder);
}
