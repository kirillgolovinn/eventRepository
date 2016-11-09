package com.IgniteEvents.automation.pages.hq.events;

import javax.xml.xpath.XPath;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.common.SeleneseTestCase;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.CheckBox;
import com.IgniteEvents.automation.elements.DropDown;

import com.IgniteEvents.automation.elements.TextBox;
import com.IgniteEvents.automation.elements.impl.ButtonImpl;
import com.IgniteEvents.automation.elements.impl.CheckBoxImpl;
import com.IgniteEvents.automation.elements.impl.DropDownImpl;
import com.IgniteEvents.automation.elements.impl.SelectBoxImpl;
import com.IgniteEvents.automation.elements.impl.TabsImpl;
import com.IgniteEvents.automation.elements.impl.TextBoxImpl;
import com.IgniteEvents.automation.pages.hq.ActivitiesPage;
import com.IgniteEvents.automation.pages.hq.HomePage;
import com.google.common.base.Function;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.Calendar;
import com.IgniteEvents.automation.elements.Table;
import com.IgniteEvents.automation.elements.impl.TableImpl;

public class AddEventPage extends ActivitiesPage {

	Button createEventPage = new ButtonImpl("//button[@autotest-id='btn_create_ticketed_event_dashboard']",
			"Create Event Button");
	TextBox eventRefferenceName = new TextBoxImpl("//input[contains(@placeholder, 'Enter a nice')]",
			"Event Refferance Name");
	TextBox eventDescription = new TextBoxImpl("//textarea", "Event Description ");
	TextBox visibleEventName = new TextBoxImpl("//input[contains(@placeholder, 'The name')]", "Event Visible Name ");
	TextBox eventLocationName = new TextBoxImpl("//input[contains(@name, 'locationName')]", "Event Location Name ");
	TextBox eventLocationAddress = new TextBoxImpl("//input[contains(@name, 'locationLine1')]",
			"Event Location address ");
	TextBox startDate = new TextBoxImpl("//input[@id='datepickerStart']", "ENDTime");
	TextBox endDate = new TextBoxImpl("//input[@id='datepickerEnd']", "EndDate");
	DropDown selectTimeZone = new DropDownImpl("//custom-select2[contains(@default, 'Select Time Zone')]/div/ul/li",
			"//custom-select2[contains(@default, 'Select Time Zone')]/div/a", "DropDown timezones");
	TextBox eventLocationCity = new TextBoxImpl("//input[contains(@name, 'locationCity')]", "Event Location address ");
	DropDown selectState = new DropDownImpl("//custom-select2[contains(@default, 'State')]/div/ul/li",
			"//custom-select2[contains(@default, 'State')]/div/a", "DropDown states");
	TextBox locationzip = new TextBoxImpl("//input[contains(@name, 'locationZip')]", "Even  Location Zip");
	Button nextTickets = new ButtonImpl(".//*[@id='btnGo-setup-tickets']", "Next Tickets Button");
	Button createEventButton = new ButtonImpl(".//*[@id='manageticket']//a", "Create Ticket Button");

	// Ticket Related
	TextBox ticketName = new TextBoxImpl("//input[@id='ticket_name']", "Ticket name Field");
	TextBox ticketDescribtion = new TextBoxImpl("//textarea[@id='ticket_description']", "Ticket description Field");
	TextBox ticketPrice = new TextBoxImpl("//input[@id='ticket_price']", "Ticket price  Field");
	TextBox ticketDeductibleAmount = new TextBoxImpl("//input[@id='ticket_deductible_amount']",
			"Ticket deductible Amount Field");
	CheckBox limitAvailibility = new CheckBoxImpl("//input[@id='availability_limit']", "availability_limit checkbox");
	TextBox totalAvailible = new TextBoxImpl("//input[@id='ticket_total_available']", "totalAvailible Field");
	CheckBox restrictions = new CheckBoxImpl("//input[@id='restrictions']", "restrictions checkbox");
	TextBox ticketSaleStartDate = new TextBoxImpl("//input[@id='ticket_sale_start']", "ticketSaleStartDate Field");
	TextBox ticketSalesEndDate = new TextBoxImpl("//input[@id='ticket_sale_end']", "ticketSalesEndDate Field");
	Button buttonSaveTicket = new ButtonImpl("//input[@class='button submitbtn']", "Save ticket  Button");
	Button ticketContinueButton = new ButtonImpl("//a[@class='button expanded interim_button_holder']",
			"Continue Ticket  Button");
	String ticketContinueButtonXpath = "//a[@class='button expanded interim_button_holder']";
	Table activitiesTable = new TableImpl("//table[@class='stacked hover sortable']", "Tickets Table ");
	Button composeButton = new ButtonImpl("	//button[@id='btnGo-tickets-compose']", "Compose Button");
	String paymentGateway = "//h4[contains(text(),  'Which payment gateway should we use?')]";
	Button nextAutoresponders = new ButtonImpl("//button[@id='btnGo-compose-autoresponders']", "Compose Button");
	public final String LAYOUT_SIDEBAR = "Sidebar layout";
	public String loadingSpinner = "//div[contains(@class, 'loading_spinner')]";
	
	//ve elemenets
	Button elementsVe = new ButtonImpl("//button[contains(@title,  'Content Elements')]", "Elemenets Tab");
	Button eventPublicName = new ButtonImpl("", "Button element");
	

	public AddEventPage openAddEventPage() {
		createEventPage.click();

		return new AddEventPage();
	}

	public AddEventPage fillSetUpPage(String eventName, String eventDesc, String eventVisibleName, String timeZone,
			String locationName, String address, String city, String state, String zip) {
		eventRefferenceName.type(eventName);
		eventDescription.type(eventDesc);
		visibleEventName.type(eventVisibleName);
		eventLocationName.type(locationName);
		startDate.type(CommonUtils.getTodayDate());
		endDate.type(CommonUtils.addDaysToCurrentDate(5));
		selectTimeZone.selectByLabelJS(timeZone);
		eventLocationAddress.type(address);
		eventLocationCity.type(city);
		selectState.selectByLabelJS(state);
		locationzip.type(zip);
		nextTickets.click();
		waitForIframedAndSwitchToIt("//iframe");
		return this;
	}

	public AddEventPage createTicket(String ticketname, String ticketDescription, String price, String dedAmount,
			String totalAvailibleTickets) {
		createEventButton.click();
		ticketName.type(ticketname);
		ticketDescribtion.type(ticketDescription);
		ticketPrice.type(price);
		ticketDeductibleAmount.type(dedAmount);
		limitAvailibility.check();
		totalAvailible.type(totalAvailibleTickets);
		restrictions.check();
		ticketSaleStartDate.scrollIntoView();
		ticketSaleStartDate.type(CommonUtils.getTodayDate());
		ticketSalesEndDate.type(CommonUtils.addDaysToCurrentDate(5));
		buttonSaveTicket.scrollIntoView();
		buttonSaveTicket.click();
		waitForVisibilityOfElement(ticketContinueButtonXpath);
		ticketContinueButton.click();
		return this;
	}

	public void verifyTicketInTheTable(String name, String price) {
		verifier.verifyEquals(activitiesTable.getCellValue(1, 2), name, "Name  is not correct");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 3).replace("$", ""), price, "Price is not correct");
		verifier.verifyEquals(activitiesTable.getCellValue(1, 4),
				CommonUtils.getTodayDate() + " " + "-" + " " + CommonUtils.addDaysToCurrentDate(5),
				"End_start end Date  is not correct");

	}

	public AddEventPage configureLayout() {
		switchDefaultContent();
		composeButton.click();
		selectLayoutStep(LAYOUT_SIDEBAR);
		invisiblityOfElement(loadingSpinner);
		dragandDropElement();
	
		
		
		nextAutoresponders.click();
		return this;

	}
	
	public void dragandDropElement() {
		Actions action = new Actions(driver);
		elementsVe.click();
		WebElement source = driver.findElement(By.xpath("//span[@class='icon-icon-button']"));
		WebElement target = driver.findElement(By.xpath("//span[contains(@ignite-mergefield-id, 'PUB')]/ancestor::div[@class='content-render-content schemeStyles']"));
		action.clickAndHold(source).moveToElement(target).release().perform();
			
		logger.info("Button element is Dragged into the Event Section");
	}
	
	

}
