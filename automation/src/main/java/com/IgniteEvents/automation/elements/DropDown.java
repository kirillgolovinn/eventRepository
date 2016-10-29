package com.IgniteEvents.automation.elements;


public interface DropDown extends Element, List {

	void selectByLabelJS(String value);

	void selectByID(String id);
}
