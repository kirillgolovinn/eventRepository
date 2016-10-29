package com.IgniteEvents.automation.elements;


import java.util.List;

public interface Table extends Element {

	void clickInCell(int row, int col);

	void clickInCell(int row, int col, String tag);

	void sendKeysInCell(int row, int col, String tag, String key);

	void clickInCell(String rowHeader, String colHeader);
	
	void clickInCell(String rowHeader, String colHeader, String tag);

	String getCellValue(int row, int col);

	String getCellValue(int row, String headerName);

	Integer getColumnNumberByHeader(String header);

	Integer getRowsCount();

	Integer getColsCount();

	Integer getColNumberByValue(String value);

	Integer getRowsNumberByValue(String value);

	String getPathToChildElement(Integer row, Integer col, String elementType);

	int isValueExists(String source);

	List<String> getHeaders();

}
