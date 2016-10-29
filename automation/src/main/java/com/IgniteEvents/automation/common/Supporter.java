package com.IgniteEvents.automation.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public class Supporter {
	/*
	 * public String finalEMAIL = ""; private String cPhone = "32165498765";
	 * private String emailDomain = "@devnull.test.ignite.net"; private String
	 * importedEmail = "importedsup"; private String subscribedEmail =
	 * "subscribedsup"; private String manuallEmail = "munuallysup"; private
	 * String facebook = "fbv"; private String home_Phone = "98765432112";
	 * private String preferredLanguage = "English (United States)"; private
	 * String twitter = "twv"; private String zipCode = "20147"; private String
	 * middleName = "MName";
	 */

	private String firstName;
	private String email;
	private String city;
	private String addressLine1;
	private String zipCode;
	private String state;
	private String lastorOrgName;
	private String lastName;
	
	private String source;
	private String postalCode;
	
	
	public  static Supporter generateSupporter(){
		Supporter supporter = new Supporter();
		supporter.setEmail(new MailoSaur().getEmailBox(RandomStringUtils.randomAlphabetic(5)));
		supporter.setFirstName("FirstName"+RandomStringUtils.randomAlphabetic(5));
		supporter.setLastName("LastName"+RandomStringUtils.randomAlphabetic(5));
		supporter.setAddressLine1("addressLine"+ RandomStringUtils.randomAlphabetic(5));
		supporter.setCity("city_"+ RandomStringUtils.randomAlphabetic(5));
		supporter.setZipCode("20009");
		return supporter;
		
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public String getLastorOrgName() {
		return lastorOrgName;
	}


	public void setLastorOrgName(String lastorOrgName) {
		this.lastorOrgName = lastorOrgName;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getAddressLine1() {
		return addressLine1;
	}


	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getState() {
		return state;
	}


	public void setSetState(String state) {
		this.state = state;
	}




	public Map<Integer, Supporter> getSupportersFromSystem(String host, String login, String pass,
			Integer amountOfSupporters, String source) throws KeyManagementException, ClientProtocolException,
					NoSuchAlgorithmException, KeyStoreException, JSONException, URISyntaxException, IOException {
		return new HttpClient(host).login(login, pass).getSupporters(source, amountOfSupporters);

	}

}
