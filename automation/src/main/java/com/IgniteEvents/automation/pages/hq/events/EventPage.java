package com.IgniteEvents.automation.pages.hq.events;

import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.common.PropertyName;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.DropDown;
import com.IgniteEvents.automation.elements.SelectBox;
import com.IgniteEvents.automation.elements.impl.ButtonImpl;
import com.IgniteEvents.automation.elements.impl.DropDownImpl;
import com.IgniteEvents.automation.elements.impl.SelectBoxImpl;
import com.IgniteEvents.automation.pages.hq.ActivitiesPage;

import com.IgniteEvents.automation.elements.Table;
import com.IgniteEvents.automation.elements.TextBox;
import com.IgniteEvents.automation.elements.impl.TableImpl;
import com.IgniteEvents.automation.elements.impl.TextBoxImpl;

public class EventPage extends ActivitiesPage {
	Button registerButton = new ButtonImpl("//a[contains(@ignite-transition,'register')]", "Event Register Button");
	Button iCanNotMakeIt = new ButtonImpl("//a[contains(text(),'I Ca')]", "Event Register Button");
	Button addToCalendar = new ButtonImpl("//a[contains(text(),'Add')]", "Event Register Button");
	TextBox eventVisibleName = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'PUBLIC_NAME')]",
			"Event visible name");
	TextBox eventStartDate = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'START_DATE')]",
			"Event start date");
	TextBox eventVenueName = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'EVENT_VENUE_NAME')]",
			"Event location name");
	TextBox eventAddressLine = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'EVENT_ADDRESS_LINE1')]",
			"Event address ");
	TextBox eventCity = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'EVENT_CITY')]", "Event city ");
	TextBox eventState = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'EVENT_STATE')]", "Event state ");
	TextBox eventZipCode = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'EVENT_ZIPCODE')]", "Event state ");
	Button createEventButton = new ButtonImpl("//button[@autotest-id='btn_create_ticketed_event_dashboard']",
			"Create Event Button");
	String loadingSpinner = "//div[@class='sli-loading-spinner']";
	String purchaseButton = "//button[contains(text(),'Purchase')]";

	Table eventTable = new TableImpl("//table[contains(@class, 'sli-checkout-summary-table')]", "Register");
	SelectBox selectBox = new SelectBoxImpl("//table/tbody/tr[1]/td[5]/select", " Ticket Select Box");
	Button registerEventButton = new ButtonImpl("//table/tbody//a[contains(text(),'Register')]",
			"Event Register Button on the second Step");
	Button checoutButton = new ButtonImpl("	//button[contains(text(),'Checkout')]",
			"Event Checkout Button on the second Step");
	String checkOut = "//button[contains(text(),'Checkout')]";

	Table numberOfTicketsTable = new TableImpl("//table[@class='stack']", "Registration Table");
	TextBox thankYouMessage = new TextBoxImpl("//h3[contains(text(), You)]", "Thank you message  ");
	TextBox eventPublicName = new TextBoxImpl("//span[contains(@ignite-mergefield-id, 'PUBLIC_NAME')]",
			"Event Public Name   ");

	public AddEventPage openAddEventPage() {
		createEventButton.click();
		return new AddEventPage();
	}

	public EventPage openEvent() {
		String link = CommonUtils.getProperty(linkProperty);
		String currentWindowHandle = getWindowHandle();
		this.openInNewWindow(link);
		logger.info("Event  public page is opened");
		CommonUtils.setProperty(PropertyName.CURRENT_WINDOW_HANDLE, currentWindowHandle);
		invisiblityOfElement(loadingSpinner);

		return new EventPage();
	}

	public EventPage verifyElementsAreDisplayLandingPage() {
		verifier.verifyElementIsDisplayed(true, registerButton, iCanNotMakeIt, addToCalendar, eventVisibleName,
				eventAddressLine, eventCity, eventStartDate, eventStartDate, eventState, eventVenueName);
		;
		return this;
	}

	public EventPage verifyTicketsTotalAmount() {
		verifier.verifyEquals(numberOfTicketsTable.getCellValue(1, 2), "2", "Number of selected tickets is incorrect");
		verifier.verifyEquals(numberOfTicketsTable.getCellValue(1, 4).replace("$", ""), "40.00", "Amount is incorect");
		return this;
	}

	public DonationWidget registerInTheEvevnt() {
		registerButton.click();
		switchToFrame("//iframe[contains(@id, 'ticketFrame')]");
		selectBox.selectByIndex(2);
		registerEventButton.click();
		waitForVisibilityOfElement(checkOut);
		verifyTicketsTotalAmount();
		checoutButton.click();
		invisiblityOfElement(loadingSpinner);
		switchDefaultContent();
		return new DonationWidget();
	}

	public void verifyDonationSuccess() {
		verifier.verifyElementIsDisplayed(thankYouMessage, eventPublicName);

	}

}
