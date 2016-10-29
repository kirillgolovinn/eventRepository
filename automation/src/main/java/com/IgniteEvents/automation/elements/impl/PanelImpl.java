package  com.IgniteEvents.automation.elements.impl;

import  com.IgniteEvents.automation.elements.Panel;

public class PanelImpl extends ElementImpl implements Panel {

	public PanelImpl(String elementPath, String elementName) {
		super(elementPath, elementName);
	}

	@Override
	public Integer getElementsCount() {
		return findElementsByXpath(path).size();
	}

	public PanelImpl(String elementPath, String elementName, int number) {
		super(elementPath, elementName);
	}

	@Override
	public String getChildElementPath(String elementPath, int setNumder) {
		return path + "[" + setNumder + "]/descendant::" + elementPath;
	}
	
	public int isValueExists(String value) {
		return findElementsByXpath(path + "/descendant::*[contains(text(), '" + value + "')]").size();
	}

}
