package com.IgniteEvents.automation.elements.impl;

import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.List;


public class ListImpl extends ElementImpl implements List {
	
	
	public ListImpl(String elementPath, String elementName) {
		super(elementPath, elementName);
	}

	private Button getChildItemByLabel(String label) {
		logger.debug("Get item with label " + label);
		return new ButtonImpl(path + "/descendant::*[text()='" + label + "']", label + " item");

	}

	@Override
	public void selectByLabel(String value) {
		if (value.contains(":")) {
			logger.info("Select values by labels " + value + " in the " + elementName);
			String[] values = CommonUtils.getArrayFromStringBySymbol(value, ":");
			for (int i = 0; i < values.length; i++) {
				this.getChildItemByLabel(values[i]).click();
			}
		}else {
			logger.info("Select value by label " + value + " in the " + elementName);
			this.getChildItemByLabel(value).click();
		}		
	}
}
