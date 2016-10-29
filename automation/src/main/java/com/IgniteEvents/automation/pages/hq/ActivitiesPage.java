package com.IgniteEvents.automation.pages.hq;

import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.common.PropertyName;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.Table;
import com.IgniteEvents.automation.elements.impl.ButtonImpl;
import com.IgniteEvents.automation.elements.impl.TableImpl;
import com.IgniteEvents.automation.pages.hq.events.AddEventPage;
import com.IgniteEvents.automation.pages.hq.events.EventPage;



public class ActivitiesPage extends HomePage {

	Button allActivitiesTab = new ButtonImpl("//a[@autotest-id='ALL']", "All Activities");

	Button deleteButton = new ButtonImpl("//a[@ng-click='confirmDelete()']", "Delete Selected");
	Button confirmDeletionBtn = new ButtonImpl("//*[@id='formConfigModal']//button[2]", "Yes, delete already!");
	Button rejectDeletionBtn = new ButtonImpl("//*[@id='formConfigModal']/div[2]/button[1]", "Nevermind, leave it be!");
	Table activitiesTable = new TableImpl("//table[contains(@id,'JColResizer')]", "Activities Table");
	Button eventsTab = new ButtonImpl("//a[@autotest-id='TICKETED_EVENT']", "Ticketted Events");
	Button composeButton = new ButtonImpl(".//*[@id='btnCompose']", "Ticketted Events");
	Button publishButton = new ButtonImpl(".//*[@id='btnGo-autoresponders-publish']", "Publish Event Button");
	protected String linkProperty = PropertyName.SUBSCRIBE_WIDGET;

	public ActivitiesPage verifyURL() {
		verifier.verifyTrue(getLocation().contains("activities"), "Current URL is not contains Activities");
		return this;
	}

	public EventPage openEventPage() {
		eventsTab.click();
		return new EventPage();
	}

	public ActivitiesPage openAllActivitiesTab() {
		allActivitiesTab.click();
		sleep(2);
		return this;
	}

	public ActivitiesPage selectLayoutStep(String type) {
		Button lay = new ButtonImpl("(//img[@alt ='" + type + "'])", type);
		lay.click();
		composeButton.click();
		return this;
	}

	public ActivitiesPage publishForm() {
		publishButton.click();
		return this;
	}
	
	public ActivitiesPage verifyFormUrl(String windgetName ){
		waitForVisibilityOfElement(".//*[@id='btnGo-publish-results']");
		Button activityLink = new ButtonImpl("//a[contains(text(), '"+ windgetName.toLowerCase()+"')]", "WidgetLink");
		verifier.verifyElementIsDisplayed(true, activityLink);
		CommonUtils.setProperty(linkProperty, activityLink.getAttribute("href"));
		return this;
	}
	

	public void verifyActivityIsPresentInTableAllActivities(String type, String handyReferenceName, String description, String status) {
		verifier.verifyEquals(activitiesTable.getCellValue(1, 2), type, "Widget is not present in table (type)");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 3), handyReferenceName, "Widget is not present in table (name)");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 4), description, "Widget is not present in table (description)");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 5), status, "Widget is not present in table (status)");
	}
	

	public ActivitiesPage verifyWidgetVisible() {
		String link = CommonUtils.getProperty(linkProperty);
		String primaryHandle = getWindowHandle();
		openInNewWindow(link);
		//verifyWidgetElements(visibleForCm, visibleForSupporter);
		closeWindow();
		switchToWindow(primaryHandle);
		return this;
	}
	
	public <T> T openFormFromTable(Class<T> clazz) {
		new ButtonImpl(activitiesTable.getPath() + "/tbody/tr[1]/td[2]/div/span/span", "First Row").click();
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("", e);
		}
		return null;
	}
	
	
	public AddEventPage openEventWidgetFromTable() {
		return openFormFromTable(AddEventPage.class);
	}
	
	
	public void verifyWidgetIsPresentInTableForms(String widgetName, String description, String status, String visibility) {
		verifier.verifyEquals(activitiesTable.getCellValue(1, 2), widgetName, "Widget is not present in table (name)");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 3), description, "Widget is not present in table (description)");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 4), status, "Widget is not present in table (status)");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 5), visibility, "Widget is not present in table (visibility)");
	}
	
	
	
	
}
