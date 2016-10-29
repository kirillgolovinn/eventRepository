package com.IgniteEvents.automation.elements;

public interface Tabs extends Element {

	String getSelectedTabName();

	void selectTab(String tabName);
}
