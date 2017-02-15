package com.IgniteEvents.automation.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import com.mailosaur.MailboxApi;
import com.mailosaur.exception.MailosaurException;
import com.mailosaur.model.Email;
//import com.sun.mail.smtp.SMTPTransport;
//import com.sun.mail.smtp.SMTPTransport;
import com.mailosaur.model.Link;

//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.gson.GsonFactory;

public class MailoSaur {

	MailoSaur() {
		mBoxAPI = new MailboxApi(mbox, apikey);
	}

	private String mbox = "74580786";
	public String apikey = "b4e4d2b193b5eb2";
	MailboxApi mBoxAPI;
	private String suffix = "@mailosaur.in";

	public Email[] getEmailsByRecipient(String rec) throws MailosaurException, IOException {
		return mBoxAPI.getEmailsByRecipient(rec);
	}

	public Email[] getEmails() throws MailosaurException, IOException {
		return mBoxAPI.getEmails();

	}

	private MailboxApi getClient() {
		return new MailboxApi(mbox, apikey);
	}

	public String getEmailBox(String name) {
		return name + "." + mbox + suffix;
	}

	public Email[] getEmailByRecepient(String recepient) throws MailosaurException, IOException {
		return mBoxAPI.getEmailsByRecipient(recepient);
	}

	public List<Email> getEmailsBySubject(String subj) {
		ArrayList<Email> listEmail = new ArrayList<>();
		try {
			Email[] e = mBoxAPI.getEmails();

			for (Email email : e) {
				if (email.subject.equalsIgnoreCase(subj)) {
					listEmail.add(email);
				}

			}

		} catch (MailosaurException | IOException e) {

			e.printStackTrace();
		}
		return listEmail;

	}

	public Email getEmailBySubject(String subj) {
		List<Email> emails = getEmailsBySubject(subj);
		if (!emails.isEmpty()) {
			return emails.get(0);
		}

		return null;

	}

	public String getUrlFormTheEmail(String emailRecepient, String domain) throws MailosaurException, IOException {
		Email e = getEmailByRecepient(emailRecepient)[1];
		Pattern pattern = Pattern.compile("http://" + domain + "/account/email/" + "\\w*");
		Matcher matcher = pattern.matcher(e.html.body);
		if (matcher.find())
			return matcher.group(0);
		return null;

	}

	public String getUrlFormTheEmailByLinkUsingRegExp(String emailRecepient) throws MailosaurException, IOException {
		Email e = getEmailByRecepient(emailRecepient)[0];

		Pattern patternTag = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
		Matcher matcherTag = patternTag.matcher(e.html.body);
		if (matcherTag.find()) {
			return matcherTag.group(1).replace("\"", "");
		}

		return null;
	}

	public String generateEmail(String name) {
		return name + "." + mbox + suffix;

	}

	// get a A link by AhrefTag

	public String getLinkByText(String emailRecepient) throws MailosaurException, IOException {
		Email email = getEmailByRecepient(emailRecepient)[1];

		Link[] l = ((Email) email).html.links;
		for (int j = 0; j < l.length; j++) {

			return l[j].href;
		}

		return null;
	}

	public static void main(String[] args) throws MailosaurException {

		try {
			Email[] email = new MailoSaur().getEmails();
			// System.out.println(email.length);

			MailoSaur mailoSaurTest = new MailoSaur();
			System.out.println("Do not forget to lock the screen :)");
			 System.out.println(mailoSaurTest.getEmailBySubject("Payment confirmation"));
			  System.out.println(mailoSaurTest.getEmailByRecepient("push.74580786@mailosaur.in")[1]);
			System.out.println(
					mailoSaurTest.getUrlFormTheEmail("mailosaur.745807 86@mailosaur.in", "incubator.insart.com:13080"));
			//System.out.println(mailoSaurTest.getUrlFormTheEmailByLinkUsingRegExp("io.74580786@mailosaur.in"));
			System.out.println(mailoSaurTest.getLinkByText("mailosaur.74580786@mailosaur.in"));

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
