package com.IgniteEvents.automation;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.IgniteEvents.automation.common.RetryAnalyzer;
import com.IgniteEvents.automation.common.SeleneseTestCase;
import com.IgniteEvents.automation.common.Supporter;
import com.IgniteEvents.automation.pages.hq.ActivitiesPage;
import com.IgniteEvents.automation.pages.hq.LoginPage;
import com.IgniteEvents.automation.pages.hq.events.AddEventPage;
import com.IgniteEvents.automation.pages.hq.events.DonationWidget;
import com.IgniteEvents.automation.pages.hq.events.EventPage;

public class CreateEventTest extends SeleneseTestCase {

	private AddEventPage addEventPage;
	private ActivitiesPage activitiesPage;
	private EventPage eventPage;

	@Parameters({ "eventRefferanceName", "eventDescription", "eventVisiblename", "timeZone", "eventLocation",
			"eventAddress", "eventCity", "eventState", "eventZip", "ticketName", "ticketDescription", "price",
			"deductibleAmount", "totalAvailible" })
	@Test(enabled = true, groups = { "createAndPublishEvent" }, retryAnalyzer = RetryAnalyzer.class)

	public void createEventTest(String eventRefferanceName, String eventDescription, String eventVisiblename,
			String timeZone, String eventLocation, String eventAddress, String eventCity, String eventState,
			String eventZip, String ticketName, String ticketDescription, String price, String deductibleAmount,
			String totalAvailible) {

		eventRefferanceName = eventRefferanceName + RandomStringUtils.randomAlphanumeric(5);
		eventDescription = eventDescription + RandomStringUtils.randomAlphanumeric(5);
		eventVisiblename = eventVisiblename + RandomStringUtils.randomAlphanumeric(5);
		eventVisiblename = eventVisiblename + RandomStringUtils.randomAlphanumeric(5);
		doLoginOpenEventAddPage();
		addEventPage.fillSetUpPage(eventRefferanceName, eventDescription, eventVisiblename, timeZone, eventLocation,
				eventAddress, eventCity, eventState, eventZip);
		addEventPage.createTicket(ticketName, ticketDescription, price, deductibleAmount, totalAvailible);
		addEventPage.verifyTicketInTheTable(ticketName, price);
		addEventPage.configureLayout();
		addEventPage.publishForm();
		addEventPage.verifyFormUrl(eventRefferanceName);
		activitiesPage = addEventPage.openActivitiesPage().openAllActivitiesTab();
		activitiesPage.verifyActivityIsPresentInTableAllActivities("Event", eventRefferanceName, eventDescription,
				"PUBLISHED");
		// activitiesPage.verifyWidgetVisible();
		activitiesPage.openEventPage().verifyWidgetIsPresentInTableForms(eventRefferanceName, eventDescription,
				"PUBLISHED", "PUBLIC");

	}

	@Test(enabled = true, groups = {
			"createAndPublishEvent" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = { "createEventTest" })
	public void eventCheckout() {
		eventPage = new LoginPage().doSuccessLogin().openActivitiesPage().openEventPage();
		eventPage.openEvent().verifyElementsAreDisplayLandingPage().registerInTheEvevnt()
				.fillDonationForm(Supporter.generateSupporter()).clickPurchaseButton().verifyDonationSuccess();
	}

	private void doLoginOpenEventAddPage() {
		addEventPage = new LoginPage().doSuccessLogin().openActivitiesPage().openEventPage().openAddEventPage();
	}

}
