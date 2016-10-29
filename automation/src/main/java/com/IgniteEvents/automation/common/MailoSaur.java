package com.IgniteEvents.automation.common;

import org.apache.logging.log4j.Logger;

import com.mailosaur.MailboxApi;
import com.mailosaur.exception.MailosaurException;
import com.mailosaur.model.Email;
//import com.sun.mail.smtp.SMTPTransport;
//import com.sun.mail.smtp.SMTPTransport;

//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.gson.GsonFactory;

public class MailoSaur {
private String mbox = "74580786";
	public String apikey = "b4e4d2b193b5eb2";
	MailboxApi mBoxAPI;
	private String suffix = "@mailosaur.in";
		private static final Logger logger = SeleneseTestCase.logger;

	MailboxApi mailbox = new MailboxApi(mbox, apikey);

	public Email[] getEmailsByRecipient(String rec) throws MailosaurException {
		return getClient().getEmailsByRecipient(rec);
	}

	public Email[] getEmails() throws MailosaurException {
		return mailbox.getEmails();

	}

	private MailboxApi getClient() {
		return new MailboxApi(mbox, apikey);
	}

	public String getEmailBox(String name) {
		return name + "." + mbox + suffix;
	}



	public static void main(String[] args) throws MailosaurException {

		Email[] email = new MailoSaur().getClient().getEmails();
		 System.out.println(email.length);

	
	}

}
