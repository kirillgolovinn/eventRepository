package com.IgniteEvents.automation.common;

import java.util.Set;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class RetryTestListener extends TestListenerAdapter {
	
	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		RetryAnalyzer retryAnalizer = (RetryAnalyzer) result.getMethod().getRetryAnalyzer();
		if (retryAnalizer != null && retryAnalizer.willRetry(result)) {
			result.setStatus(ITestResult.SKIP);
			SeleneseTestCase.logger.warn("Error in " + result.getName() + ". Retrying...", result.getThrowable());
			SeleneseTestCase.logger.info("Setting test run attempt status to SKIPPED");
		} else {
			SeleneseTestCase.logger.error("Retry limit exceeded for " + result.getName());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	}

	@Override
	public synchronized void onFinish(ITestContext testContext) {
		Set<ITestResult> skippedTests = testContext.getSkippedTests().getAllResults();
		for (ITestResult result : skippedTests) {
			if (result.getStatus() != ITestResult.SKIP) {
				SeleneseTestCase.logger.info("The status should be skipped but it is: " + result.getStatus());
				result.setStatus(ITestResult.SKIP);
			}
		}
	}
}