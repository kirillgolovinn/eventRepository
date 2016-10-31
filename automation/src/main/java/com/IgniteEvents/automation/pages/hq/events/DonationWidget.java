package com.IgniteEvents.automation.pages.hq.events;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

import com.IgniteEvents.automation.common.Browser;
import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.common.Supporter;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.SelectBox;
import com.IgniteEvents.automation.elements.TextBox;
import com.IgniteEvents.automation.elements.impl.ButtonImpl;
import com.IgniteEvents.automation.elements.impl.SelectBoxImpl;
import com.IgniteEvents.automation.elements.impl.TextBoxImpl;

public class DonationWidget extends Browser {

	TextBox nameOnCardField = new TextBoxImpl("//input[contains(@id, 'name_on_card')]", "Name on Card");
	TextBox cardNumber = new TextBoxImpl("//input[contains(@id, 'card_number')]", "cardNumber input field");
	TextBox cvv = new TextBoxImpl("//input[contains(@id, 'cvv')]", "cardNumber input field", true);
	SelectBox expirationMonth = new SelectBoxImpl("//select[contains(@id, 'expiry_month')]", "Expiration month");
	SelectBox expirationYear = new SelectBoxImpl("//select[contains(@id, 'expiry_year')]", "Expiration year");
	TextBox firtName = new TextBoxImpl("//input[@class='sli-input sli-input-person-firstname']", "First name ");
	TextBox lastName = new TextBoxImpl("//input[@class='sli-input sli-input-person-lastname']", "Lasn name ");
	TextBox email = new TextBoxImpl("//input[@class='sli-input sli-input-contact-email']", "Email  ");
	TextBox addrress = new TextBoxImpl("//input[@class='sli-input sli-input-address-line1']", "addrress lin 1 ");
	TextBox city = new TextBoxImpl("//input[@class='sli-input sli-input-address-city']", "addrress lin 1 ");
	SelectBox selectState = new SelectBoxImpl("//label[contains(text(), 'State')]/following::select",
			"Select State implemenatation ");
	TextBox zip = new TextBoxImpl("//input[@class='sli-input sli-input-address-zip']", "zip code");
	Button purchaseButton = new ButtonImpl("//button[contains(@onclick, 'submitFunc(event)')]", "Purchase Button");

	public String getxpath(String donationAmount) {
		String xpath = "//ul/li/label[contains(text()," + donationAmount + ")] ";
		return xpath;
	}

	public DonationWidget fillDonationForm(Supporter sup) {
		String[] donAmounts = new String[] { "5", "10", "15", "20", "25" };
		String donAmunt = donAmounts[new Random().nextInt(donAmounts.length)];
		nameOnCardField.type("name card");
		cardNumber.type("4111111111111111");
		cvv.type("123");
		expirationMonth.selectByLabel("11");
		expirationYear.selectByLabel("2017");
		firtName.type(sup.getFirstName());
		lastName.type(sup.getLastName());
		email.type(sup.getEmail());
		addrress.type(sup.getAddressLine1());
		city.type(sup.getCity());
		selectState.selectByIndex(4);
		zip.type(sup.getZipCode());
		return this;
	}

	public EventPage clickPurchaseButton() {
		purchaseButton.click();
		return new EventPage();
	}
	
	//merge conflict

}
