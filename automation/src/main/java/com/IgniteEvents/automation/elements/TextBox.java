package com.IgniteEvents.automation.elements;


public interface TextBox extends Element {

	String getText();

	void type(String text);

	void clear();

	void uploadAssetsImage(String filePath, String imageName);

	String getValue();
}
