package com.IgniteEvents.automation.common;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;

import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;

public class TestListener extends SeleneseTestCase implements ITestListener, IInvokedMethodListener, IAnnotationTransformer {
	Properties props = new Properties();
	static String propFileName = "properties.prop";
	boolean updateTC = Boolean.valueOf(CommonUtils.getProperty(PropertyName.UPDATE_TC, "false"));
	String planName = CommonUtils.getProperty(PropertyName.PLAN_NAME, "false");
	String buildName = CommonUtils.getProperty(PropertyName.BUILD_VERSION, "false");

	// Run when class ends
	@Override
	public void onFinish(ITestContext arg0) {

	}

	// Run when class starts
	@Override
	public void onStart(ITestContext arg0) {
		SeleneseTestCase.logger.debug("Start " + arg0.getName() + " class");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		SeleneseTestCase.logger.debug("Test " + result.getName() + " is succes but within Success %");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		File f = makeScreenshot(result.getName());
		String steps = "";
		for (int i = 0; i < bug.size(); i++) {
			steps = steps + " \n   " + (i + 1) + ". " + bug.get(i);
		}
		if (updateTC && result.getMethod().getDescription() != null) {
			String[] ids = CommonUtils.getArrayFromStringBySymbol(result.getMethod().getDescription(), ":");
			Reporter.log("Execution failed. " + ids[2]);
			try {
				TestLink api = new TestLink(planName, buildName);
				Integer internalID = new Integer(ids[0]), externalID = new Integer(ids[1]);
				api.updateTestCaseResult(internalID, externalID, ExecutionStatus.FAILED, api.getLastExecutionId(externalID, internalID) + " - Execution failed. " + ids[2] + " \n Steps:" + steps);
				api.uploadExecutionAttachment(f);
			} catch (NumberFormatException e) {
				SeleneseTestCase.logger.error("", e);
			} catch (Exception e) {
				SeleneseTestCase.logger.error("", e);
			}
		}

		SeleneseTestCase.logger.error("Test " + result.getName() + " is FAILED");

		Reporter.log("Steps:");
		for (int i = 0; i < bug.size(); i++) {
			Reporter.log(bug.get(i));
		}
		Reporter.log("<a href='file:///" + f.getAbsolutePath() + "'>Screenshot</a>");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		SeleneseTestCase.logger.debug("Test " + result.getName() + " is SKIPPED");
		if (updateTC && result.getMethod().getDescription() != null) {
			try {
				String[] ids = CommonUtils.getArrayFromStringBySymbol(result.getMethod().getDescription(), ":");
				TestLink api = new TestLink(planName, buildName);
				Integer internalID = new Integer(ids[0]), externalID = new Integer(ids[1]);

				if (api.getLastExecutionResult(externalID, internalID).equals(ExecutionStatus.FAILED) && api.getLastExecutionNotes(externalID, internalID).contains(api.getLastExecutionId(externalID, internalID).toString())) {
					return;
				} else {
					api.updateTestCaseResult(internalID, externalID, api.getLastExecutionResult(externalID, internalID), api.getLastExecutionId(externalID, internalID) + " - Was not run");
				}

			} catch (NumberFormatException e) {
				SeleneseTestCase.logger.error("", e);
			} catch (Exception e) {
				SeleneseTestCase.logger.error("", e);
			}
		}

	}

	@Override
	public void onTestStart(ITestResult test) {
		SeleneseTestCase.logger.info("------------------------------------------------------------------------------------------------------------");
		SeleneseTestCase.logger.info("Run " + test.getName() + " test");
		bug.clear();
		CommonUtils.setParam("testResult", "true");
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		SeleneseTestCase.logger.info("Test " + result.getName() + " is SUCCESS");

		if (updateTC && result.getMethod().getDescription() != null) {
			String[] ids = CommonUtils.getArrayFromStringBySymbol(result.getMethod().getDescription(), ":");
			Reporter.log(ids[2].replace(" NOT", ""));
			Integer internalID = new Integer(ids[0]), externalID = new Integer(ids[1]);
			try {
				new TestLink(planName, buildName).updateTestCaseResult(internalID, externalID, ExecutionStatus.PASSED, ids[2].replace(" NOT", ""));
			} catch (NumberFormatException e) {
				SeleneseTestCase.logger.error("", e);
			} catch (NullPointerException e) {
				SeleneseTestCase.logger.error("", e);
			} catch (Exception e) {
				SeleneseTestCase.logger.error("", e);
			}
		}

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {

	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {

	}

	@Override
	public void transform(ITestAnnotation arg0, @SuppressWarnings("rawtypes") Class arg1, @SuppressWarnings("rawtypes") Constructor arg2, Method arg3) {
		// TODO Auto-generated method stub

	}

}
