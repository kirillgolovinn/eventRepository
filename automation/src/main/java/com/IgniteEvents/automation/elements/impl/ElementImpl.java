package com.IgniteEvents.automation.elements.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.IgniteEvents.automation.common.Browser;
import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.common.SeleneseTestCase;
import com.IgniteEvents.automation.elements.Element;

//import org.openqa.selenium.NoSuchElementException;

abstract class ElementImpl implements Element {
	String path;
	WebDriver driver;
	Logger logger;
	int cTimeOut;
	String elementName;
	int defaultTimeOut;
	boolean isRequeired = true;

	public ElementImpl(String elementPath, String name) {
		this.path = elementPath;
		driver = SeleneseTestCase.driver;
		logger = SeleneseTestCase.logger;
		cTimeOut = SeleneseTestCase.cTimeOut;
		defaultTimeOut = SeleneseTestCase.defaultTimeOut;
		elementName = name;
	}

	public ElementImpl(String elementPath, String name, boolean isReq) {
		this.path = elementPath;
		driver = SeleneseTestCase.driver;
		logger = SeleneseTestCase.logger;
		cTimeOut = SeleneseTestCase.cTimeOut;
		defaultTimeOut = SeleneseTestCase.defaultTimeOut;
		elementName = name;
		isRequeired = isReq;
	}

	@Override
	public void setImplicity(int cTimeOut) {
		driver.manage().timeouts().implicitlyWait(cTimeOut, TimeUnit.SECONDS);
	}

	@Override
	public void scrollIntoView() {
		WebElement element = findElementByXpath(path);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView();", element);
	}

	@Override
	public void clickJS() {
		logger.info("Click on " + elementName);
		WebElement element = findElementByXpath(path);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	@Override
	public void clickByTABKey() {
		logger.info("Click on " + elementName);
		clickByTABKey(path);
	}

	@Override
	public void clickByENTERKey() {
		logger.info(" Click on " + elementName + " and press ENTER button afterwards ");
		clickByENTERKey(path);
	}

	@Override
	public void click() {
		logger.info("Click on " + elementName);
		click(path);
	}

	@Override
	public boolean waitElement() {
		return waitObject(path, 30000);
	}

	@Override
	public boolean waitElement(int seconds) {
		return waitObject(path, seconds * 1000);
	}

	@Override
	public void changePath(String old, String newPath) {
		if (old.equals("")) {
			this.path = newPath;
		} else {
			this.path = this.path.replace(old, newPath);
		}

	}

	@Override
	public void addPath(String pathToAdd) {
		this.path = this.path + pathToAdd;
	}

	@Override
	public void highlight() {
		logger.info("Highlight the " + elementName);
		highlight(path);
	}

	@Override
	public boolean isVisible() {
		logger.info("Check is the " + elementName + " visible");
		return isVisible(path);
	}

	@Override
	public boolean isEnabled() {
		logger.info("Check is the " + elementName + " enabled");
		return isEnabled(path);
	}

	@Override
	public boolean isDisplayed() {
		logger.info("Check is the " + elementName + " displayed");
		return isDisplayed(path);
	}

	@Override
	public boolean isNotDisplayed() {
		logger.info("Check that " + elementName + " is not displayed.");
		return isNotDisplayed(path);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getName() {
		return elementName;
	}

	@Override
	public void moveAndClick() {
		new Actions(driver).moveToElement(findElementByXpath(path)).perform();
		click();

	}

	@Override
	public void clickByNumber(Integer number) {
		logger.info(elementName + " was clicked.");
		click(path + "[" + number + "]");

	}

	@Override
	public String getAttribute(String attrName) {
		return getAttributeValue(path, attrName);
	}

	@Override
	public boolean isNotExists() {
		logger.info("Check that " + elementName + " is not exists.");
		return isNotElementPresent(path);
	}

	@Override
	public WebElement getLastElement() {
		List<WebElement> ss = findElementsByXpath(path);
		int s = ss.size();
		return ss.get(s - 1);
	}

	@Override
	public boolean waitForNotExists(Integer timeOut) {
		for (int i = 0; i < timeOut; i++) {
			if (isNotExists()) {
				break;
			} else {
				sleep(1);
			}
		}
		return isNotExists();
	}

	@Override
	public boolean waitForNotVisible(Integer timeOut) {
		for (int i = 0; i < timeOut; i++) {
			if (!isVisible()) {
				break;
			} else {
				sleep(1);
			}
		}
		return !isVisible();
	}



	@Override
	public String setAttribute(String attName, String attValue) {
		WebElement element = findElementByXpath(path);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attName, attValue);
		return attValue;
	}

	@Override
	public void removeAttribute(String attName) {
		WebElement element = findElementByXpath(path);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].removeAttribute(arguments[1]);", element, attName);
	}

	/**
	 * @param cTimeOut
	 *            in miliseconds
	 */
	protected void sleep(int seconds) {
		try {
			logger.debug("Sleep on " + seconds + " seconds");
			Thread.sleep(seconds * 1000);
			logger.debug("Sleep on is over");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param cTimeOut
	 *            in seconds
	 */

	protected void clickACtion(String locator) {
		logger.debug("Click on < " + locator + " >.");
		// getActionBuilder().moveToElement(findElementByXpath(locator)).perform();
		getActionBuilder().click(findElementByXpath(locator)).perform();
	}

	protected void moveToElement(String locator) {
		logger.debug("Move to element < " + locator + " >.");
		getActionBuilder().moveToElement(findElementByXpath(locator)).perform();
	}

	protected void onClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	protected void onBlur(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].onblur();", element);
	}

	/* redefined methods */

	protected void click(String locator) {
		logger.debug("Click on < " + locator + " >.");
		try {
			findElementByXpath(locator).click();
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	protected void clickByTABKey(String locator) {
		logger.debug("Click on < " + locator + " >.");
		waitObject(locator, 10 * cTimeOut);
		try {
			sendKeys(locator, Keys.TAB);
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	protected void clickByENTERKey(String locator) {
		logger.debug(" Click on < " + locator + " >. ");
		waitObject(locator, 10 * cTimeOut);
		try {
			sendKeys(locator, Keys.ENTER);
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

		}

	}

	protected void sendKeys(String locator, Keys key) {
		logger.debug("Click on < " + locator + " >.");
		findElementByXpath(locator).sendKeys(key);

	}

	protected void sendKeys(String locator, String key) {
		logger.debug("Click on < " + locator + " >.");
		findElementByXpath(locator).sendKeys(key);

	}

	protected void submit(String locator) {
		waitObject(locator, cTimeOut * 30);
		try {
			driver.findElement(By.xpath(locator)).submit();
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		logger.debug("Submit on < " + locator + " >.");
	}

	protected void highlight(String locator) {
		waitObject(locator, cTimeOut * 30);
		WebElement elem = findElementByXpath(locator);
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", elem);
		}
		logger.debug("Element < " + locator + " > was highlighted.");
	}

	protected void clearTextField(String locator) {
		boolean isFile = false;
		try {
			try {
				isFile = getAttributeValue(locator, "type").equalsIgnoreCase("file");
			} catch (Exception e) {

			}
			if (!isFile) {
				findElementByXpath(locator).clear();
			}
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		logger.debug("Cleaned up a value in the <" + locator + " >.");
	}

	protected void type(String locator, String value) {
		clearTextField(locator);
		if (value != "") {
			waitObject(locator, cTimeOut * 30);
			try {
				findElementByXpath(locator).sendKeys(value);
			} catch (Exception e) {
				if (isRequeired) {
					e.printStackTrace();
					throw new ElementNotVisibleException(elementName);
				} else {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
			logger.debug("Text < " + value + " > was entered into the < " + locator + " >.");
		} else {
			logger.debug("Value is empty. Nothing was typed");
		}
	}

	protected int getXPathCount(String xpath) {
		logger.debug("Try to count xpathes " + xpath);
		return findElementsByXpath(xpath).size();// selenium.getXpathCount(xpath).intValue();
	}

	protected void select(String locator, String value) {
		logger.debug("Try to select value < " + value + " > in the < " + locator + " >.");
		try {
			new Select(findElementByXpath(locator)).selectByVisibleText(value);
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		logger.debug("Value < " + value + " > was selected into the < " + locator + " >.");
	}

	protected void select(String locator, int index) {
		logger.debug("Try to select value by index < " + index + " > in the < " + locator + " >.");
		try {
			new Select(findElementByXpath(locator)).selectByIndex(index);
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		logger.debug("Value with index < " + index + " > was selected into the < " + locator + " >.");
	}

	protected void selectAndWait(String locator, String value) throws InterruptedException {
		select(locator, value);
	}

	protected String getSelectedValue(String locator) {
		return new Select(findElementByXpath(locator)).getFirstSelectedOption().getCssValue("value");
	}

	protected String getSelectedLabel(String locator) {
		return new Select(findElementByXpath(locator)).getFirstSelectedOption().getText();
	}

	protected List<WebElement> getSelectOptions(String locator) {
		Select select = new Select(findElementByXpath(locator));
		return select.getOptions();
	}

	protected void check(String locator, boolean value) {
		if (value) {
			check(locator);
		} else {
			uncheck(locator);
		}
	}

	protected void check(String locator) {
		logger.debug("Check checkbox - < " + locator + " >.");
		try {
			WebElement el = findElementByXpath(locator);
			if (!el.isSelected()) {
				el.click();
			}
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	protected void uncheck(String locator) {
		logger.debug("Uncheck checkbox - < " + locator + " >.");
		try {
			WebElement el = findElementByXpath(locator);
			if (el.isSelected()) {
				el.click();
			}
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns URL of current page
	 * 
	 * @return String URL
	 */
	protected String getLocation() {
		String res = driver.getCurrentUrl();
		logger.debug("Get current URL location < " + res + " >.");
		return res;
	}

	protected String getText(String locator) {
		logger.debug("Get text from - <" + locator + ">. ");
		String res = null;
		try {
			res = findElementByXpath(locator).getText();
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return res;
	}

	protected String getValue(String locator) {
		logger.debug("Get value from - <" + locator + ">. ");
		String res = null;
		try {
			res = getAttributeValue(locator, "value");
		} catch (Exception e) {
			if (isRequeired) {
				e.printStackTrace();
				throw new ElementNotVisibleException(elementName);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		logger.debug("Returned text is - <" + res + ">. ");
		return res;
	}

	protected boolean isElementPresent(String path) {
		Browser.implicityWait(1);
		logger.debug("Check is element " + path + " present on the page");
		boolean is;
		is = findElementsByXpathWithOutWait(path).size() != 0;
		if (is)
			is = isDisplayed(path);
		logger.debug("isElementPresent returns " + is);
		Browser.implicityWait(SeleneseTestCase.defaultTimeOut);
		return is;
	}

	protected boolean isNotElementPresent(String path) {
		Browser.implicityWait(1);
		logger.debug("Check is element " + path + " present on the page");
		boolean is;
		is = findElementsByXpathWithOutWait(path).size() == 0;
		logger.debug("isElementPresent returns " + is);
		Browser.implicityWait(SeleneseTestCase.defaultTimeOut);
		return is;
	}

	protected boolean isVisible(String locator) {
		Browser.implicityWait(1);
		logger.debug("Check is element " + locator + " visible on the page");
		return findElementByXpath(locator).isDisplayed();
	}

	protected void setActiveFrameByXpath(String locator) {
		logger.debug("Try to set focus into frame " + locator);
		WebElement elem = findElementByXpath(locator);
		logger.debug("Focus was set into the frame " + locator);
		switchToFrame(elem);
	}

	@Override
	public void focus() {
		logger.debug("Try to set focus into element " + path);
		WebElement element = findElementByXpath(path);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].focus();", element);
	}

	protected void switchToDefaultContent() {
		logger.debug("Try to set focus into parent page");
		driver.switchTo().defaultContent();
	}

	protected WebElement findElementByXpath(String locator) {
		locator = locator.replace("[0]", "");
		logger.debug("Try to find element " + locator);
		List<WebElement> elem = findElementsByXpath(locator);

		while (elem.size() < 1) {
			if (SeleneseTestCase.isDebugMode) {
				elem = findElementsByXpath(locator);
			} else {
				throw new ElementNotVisibleException("Element was not found " +  " " + locator);
			}
		}

		if (elem.size() == 1) {
			return driver.findElement(By.xpath(locator));
		} else {
			for (int i = 0; i < elem.size(); i++) {
				if (elem.get(i).isDisplayed()) {
					return elem.get(i);
				}
			}
		}
		return driver.findElement(By.xpath(locator));
	}

	protected WebElement findElementByid(String id) {
		// implicityWait(10);
		logger.debug("Try to find element " + id);
		return driver.findElement(By.id(id));

	}

	protected WebElement findElementByCss(String css) {
		// implicityWait(10);
		logger.debug("Try to find element " + css);
		return driver.findElement(By.cssSelector(css));

	}

	protected List<WebElement> findElementsByXpath(String xpath) {
		setImplicity(30);
		List<WebElement> l = findElementsByXpathWithOutWait(xpath);
		setImplicity(defaultTimeOut);
		return l;

	}

	protected List<WebElement> findElementsByXpathWithOutWait(String xpath) {
		xpath.replace("[0]", "");
		logger.debug("Try to find elements " + xpath);
		List<WebElement> elem = driver.findElements(By.xpath(xpath));
		logger.debug(elem.size() + " elements were found");

		return elem;

	}

	protected void switchToFrame(int index) {
		logger.debug("Try to switch focus to frame with index = " + index);
		driver.switchTo().frame(index);
	}

	protected void switchToFrame(String id) {
		logger.debug("Try to switch focus to frame with Id =  " + id);
		driver.switchTo().frame(id);
	}

	protected void switchToFrame(WebElement element) {
		logger.debug("Try to switch focus to frame");
		driver.switchTo().frame(element);
	}

	protected String getCSSValue(String xpath, String CSSParametr) {
		logger.debug("Try to find CSS " + CSSParametr + " for element " + xpath);
		return driver.findElement(By.xpath(xpath)).getCssValue(CSSParametr);
	}

	protected String getAttributeValue(String xpath, String attrName) {
		logger.debug("Try to find Attribute " + attrName + " for element " + xpath);
		return driver.findElement(By.xpath(xpath)).getAttribute(attrName);
	}

	protected String findNOTBrokenPartOfXpat(String xpath) {
		String s[] = CommonUtils.getArrayFromStringBySymbol(xpath.replace("//", ""), "/");
		String path = "//" + s[0];
		for (int i = 1; i <= s.length; i++) {
			if (driver.findElements(By.xpath(path)).size() == 0) {
				logger.error("Min broken path" + xpath);
				return xpath;
			}
			if (i < s.length) {
				path = path + "/" + s[i];
			}
		}
		return "Correct part of path " + path;
	}

	/* utils------------------------------------------------------------- */

	/**
	 * 
	 * @param aIdLocator
	 * @param aTime
	 *            in mlSeconds
	 * @return
	 */
	protected boolean waitObject(String aIdLocator, int aTime) {
		try {
			for (int i = 0; i < aTime / 1000; i++) {
				boolean isPrsent = isElementPresent(aIdLocator);
				if (isPrsent) {
					boolean isVisible = false;
					try {
						isVisible = isVisible(aIdLocator);
					} catch (Exception e) {
						return true;
					}
					if (isVisible) {
						return true;
					} else {
						logger.info("Element " + aIdLocator + " is present but is not visible");
					}
				} else {

					logger.info("Waiting for element " + aIdLocator + ". " + (aTime / 1000 - i) + " seconds left.");

					Thread.sleep(1000);
					if (i == aTime / 1000 - 1) {
						if (!logger.getLevel().equals(Level.INFO)) {
							SeleneseTestCase.makeScreenshot(CommonUtils.getUnicName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception: Element " + aIdLocator + " not found!", e);
			return false;
		}
		return false;
	}

	protected void waitObjectAndFail(String aIdLocator, int aTime) throws Exception {
		if (!waitObject(aIdLocator, aTime)) {
			throw new ElementNotVisibleException(aIdLocator + "xpath");
		}

	}

	protected boolean waitTextInObject(String aIdLocator, String aText, int aTime) throws Exception {

		for (int i = 0; i < aTime / 1000; i++) {
			if (isElementPresent(aIdLocator) && getText(aIdLocator).contains(aText)) {
				if (getText(aIdLocator).indexOf(aText) != -1) {
					return true;
				} else if (i == aTime / 1000 - 1) {
					throw new Exception("Exception: Wrong text. Text '" + aText + "' is not found.");
				} else {
					sleep(1);
				}
			} else {
				sleep(1);
			}

		}
		return false;
	}

	protected Actions getActionBuilder() {
		return new Actions(driver);
	}

	protected boolean isEnabled(String path) {
		return findElementByXpath(path).isEnabled();
	}

	protected boolean isDisplayed(String path) {
		return findElementByXpath(path).isDisplayed();
	}

	protected boolean isNotDisplayed(String path) {
		return findElementsByXpath(path).size() == 0;
	}

	protected boolean waitConditionBecomesTrue(boolean condition, int timeOut) {
		if (!condition) {
			sleep(timeOut);
			return false;
		} else {
			return true;
		}
	}

}
