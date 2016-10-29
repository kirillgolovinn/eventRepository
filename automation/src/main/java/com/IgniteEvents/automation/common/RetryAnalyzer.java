 package com.IgniteEvents.automation.common;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	
	private int retryCount = 0;
	private int maxRetryCount = Integer.parseInt(CommonUtils.getProperty(PropertyName.MAX_RETRY_COUNT, "2"));

	public boolean retry(ITestResult result) {
		if (willRetry(result)) {
			retryCount++;
			SeleneseTestCase.deletecoockies();
			return true;
		}
		return false;
	}
	
	public boolean willRetry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			return true;
		}
		return false;
	}
}