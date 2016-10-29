package com.IgniteEvents.automation.elements.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.IgniteEvents.automation.elements.Table;

public class TableImpl extends ElementImpl implements Table {

	public TableImpl(String elementPath, String elementName) {
		super(elementPath, elementName);
	}

	@Override
	public void clickInCell(int row, int col) {
		clickInCell(row, col, "input");
	}

	@Override
	public void clickInCell(int row, int col, String tag) {
		logger.info(elementName + " was clicked in cell, row " + row + " and column " + col);
		String pathEl = path + "/tbody/descendant::tr[" + row + "]/td[" + col + "]/" + tag;
		super.click(pathEl.replace("[0]", ""));
	}

	@Override
	public void sendKeysInCell(int row, int col, String tag, String key) {
		logger.info(elementName + " was clicked in cell, row " + row + " and column " + col);
		String pathEl = path + "/descendant::tr[" + row + "]/td[" + col + "]/" + tag;
		super.sendKeys(pathEl.replace("[0]", ""), key);
	}

	@Override
	public void clickInCell(String rowHeader, String colHeader) {
		clickInCell(rowHeader, colHeader, "input");
	}

	@Override
	public void clickInCell(String rowHeader, String colHeader, String tag) {
		logger.info(elementName + " was clicked in cell, row " + rowHeader + " and column " + colHeader);
		Integer rowNumber;
		Integer colNumber;
		try {
			rowNumber = Integer.valueOf(rowHeader);
		} catch (NumberFormatException e) {
			rowNumber = getRowsNumberByValue(rowHeader);
		}
		try {
			colNumber = Integer.valueOf(colHeader);
		} catch (NumberFormatException e) {
			colNumber = getColNumberByValue(colHeader);
		}
		clickInCell(rowNumber, colNumber, tag);
	}

	@Override
	public String getCellValue(int row, int col) {
		return getText(path + "/tbody/tr[" + row + "]/td[" + col + "]");
	}

	@Override
	public String getCellValue(int row, String headerName) {
		return getCellValue(row, getColumnNumberByHeader(headerName));
	}

	@Override
	public Integer getColumnNumberByHeader(String header) {

		return this.getHeaders().indexOf(header) + 1;
	}

	@Override
	public Integer getRowsCount() {

		return findElementsByXpath(path + "/tbody/tr").size();
	}

	@Override
	public Integer getColsCount() {

		return findElementsByXpath(path + "/tbody/tr[1]/td").size();
	}

	@Override
	public Integer getColNumberByValue(String value) {
		if (isValueExists(value) <= 0) {
			return -1;
		}
		Integer colsCount = getColsCount();
		for (int i = 1; i <= colsCount; i++) {
			if (findElementsByXpathWithOutWait(path + "/descendant::tr/th[" + i + "]/descendant-or-self::*[contains(text(), '" + value + "')]").size() > 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Integer getRowsNumberByValue(String value) {
		if (isValueExists(value) <= 0) {
			return -1;
		}
		Integer rowsCount = getRowsCount() + 1;
		for (int i = 1; i <= rowsCount; i++) {
			String elementPath = path + "/tbody/descendant::tr[" + i + "]/descendant::*[contains(text(), '" + value + "')]";
			if (findElementsByXpathWithOutWait(elementPath.replace("[0]", "")).size() > 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String getPathToChildElement(Integer row, Integer col, String elementType) {

		return path + "/tbody/tr[" + row + "]/td[" + col + "]/descendant::" + elementType;
	}

	/**
	 * If true, it searches <a> in the header tag
	 * 
	 * @param isClickable
	 * @return
	 */
	@Override
	public List<String> getHeaders() {

		Iterator<WebElement> columns = findElementsByXpath(path + "/thead/tr/th/descendant-or-self::a").iterator();
		if (!columns.hasNext()) {
			columns = findElementsByXpath(path + "/thead/tr/th/descendant-or-self::*").iterator();
		}

		ArrayList<String> headers = new ArrayList<String>();
		while (columns.hasNext()) {
			WebElement column = columns.next();
			if (!column.getText().equals("") && !headers.contains(column.getText())) {
				headers.add(column.getText());
			}
		}
		return headers;
	}
	
	public int isValueExists(String value) {
		return findElementsByXpath(path + "/descendant::*[contains(text(), '" + value + "')]").size();
	}
}

	