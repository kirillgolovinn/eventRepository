package com.IgniteEvents.automation.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import  com.IgniteEvents.automation.elements.Button;
import  com.IgniteEvents.automation.elements.impl.ButtonImpl;
import com.IgniteEvents.automation.pages.hq.LoginPage;
import com.google.common.base.Function;

public abstract class Browser {
	protected static WebDriver driver;
	protected static Logger logger;
	protected static Verifier verifier = new Verifier();
	int cTimeOut;
	String elementName;
	Integer defaultTimeOut;

	public Browser() {
		driver = SeleneseTestCase.driver;
		logger = SeleneseTestCase.logger;
		cTimeOut = SeleneseTestCase.cTimeOut;
		defaultTimeOut = SeleneseTestCase.defaultTimeOut;
	}

	protected String getBrowser() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		return browserName;
	}

	protected void open() {
		open(SeleneseTestCase.USED_ENVIRONMENT.getBaseTestUrl());
	}

	public LoginPage logOut() {
		deletecoockies();
		open(SeleneseTestCase.USED_ENVIRONMENT.getBaseTestUrl() + "/#/logout");
		sleep(5);
		return new LoginPage();
	}

	

	protected void open(String url) {
		url = url.replaceFirst("hq.uat.igniteaction.net", "hq.uat.ignite.net");
		logger.info("Try to open URL - " + url);
		SeleneseTestCase.bug.add("Open " + url);
		driver.navigate().to(url);
		driver.manage().window().maximize();
		if (driver instanceof InternetExplorerDriver) {
			Button link = new ButtonImpl("//a[@name='overridelink']", "Continue");
			sleep(15);
			if (!link.isNotExists()) {
				link.click();
			}
		}
	}

	protected String openInNewWindow(String url) {
		String currentWindowHandle = getWindowHandle();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open()");
		switchToPopupWindow(currentWindowHandle);
		open(url);
		return currentWindowHandle;
	}

	protected void refresh() {
		logger.info("Try to refresh the page");
		driver.navigate().refresh();
	}

	protected void close() {
		SeleneseTestCase.close();
	}

	protected void closeWindow() {
		SeleneseTestCase.closeWindow();
	}

	protected void deletecoockies() {
		SeleneseTestCase.deletecoockies();
		sleep(5);
	}

	protected Set<Cookie> getCoockies() {
		return SeleneseTestCase.getCoockies();
	}

	/**
	 * @param timeOut
	 *            in seconds
	 */
	public static void implicityWait(Integer timeOut) {
		SeleneseTestCase.implicityWait(timeOut);
	}

	protected String getWindowHandle() {
		return driver.getWindowHandle();
	}

	protected void switchToWindow(String popUpWindowHandle) {
		logger.debug("Try to switch focus to window " + popUpWindowHandle);
		driver.switchTo().window(popUpWindowHandle);
	}

	protected void switchToPopupWindow(String currentWindowHandle) {
		String popUpWindowHandle = null;
		Set<String> openWindowsList = driver.getWindowHandles();
		for (String windowHandle : openWindowsList) {
			if (!windowHandle.equals(currentWindowHandle))
				popUpWindowHandle = windowHandle;
		}
		switchToWindow(popUpWindowHandle);
	}

	protected void switchToFrame(String xpath) {
		driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
	}

	protected void switchDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected void test_fail() {
		logger.debug("Failure the test. Function is called force quit the test. ");
		Assert.assertTrue(false);
	}

	protected void closeGoogleSession() {
		logger.info("LogOut from google and back to the test page");
		deletecoockies();
		driver.navigate().to("https://mail.google.com/mail/?logout&hl=ru");
		sleep(3);

	}

	public boolean waitConditionBecomesTrue(boolean condition, Integer timeOut) {
		if (!condition) {
			sleep(timeOut);
			return false;
		} else {
			return true;
		}

	}

	public boolean waitConditionBecomesTrueWithRefersh(boolean condition, Integer timeOut) {
		if (!condition) {
			logger.info("Wait for condition");
			sleep(timeOut);
			refresh();
			sleep(3);
			return false;
		} else {
			return true;
		}

	}

	public boolean waitConditionBecomesTrueWithRefersh(boolean condition, Integer timeOut, String message) {
		logger.info(message);
		return waitConditionBecomesTrueWithRefersh(condition, timeOut);

	}

	protected void sleep(int seconds) {
		try {
			logger.info("Wait for " + seconds + " seconds");
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	protected String getLocation() {
		logger.info("Get current URL");
		return driver.getCurrentUrl();
	}


	protected Alert switchToAlert() {
		return driver.switchTo().alert();
	}
	
	protected void waitForVisibilityOfElement(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
	}
	
	protected void invisiblityOfElement(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	}

	
	
	protected void waitForIframedAndSwitchToIt(String xpathExpresion) {
		WebElement el = driver.findElement(By.xpath(xpathExpresion));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(el));
	}
	
	protected void waitForElementToBeClickable(String xpathExpresion) {
		WebElement el = driver.findElement(By.xpath(xpathExpresion));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(el));
	}
	
	


	protected void waitForElement() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath("//div[@id='manageticket']//a"));
			}
		});

	}
	

}
