package com.IgniteEvents.automation.pages.hq;

import com.IgniteEvents.automation.common.Browser;
import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.common.PropertyName;
import com.IgniteEvents.automation.common.SeleneseTestCase;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.CheckBox;
import com.IgniteEvents.automation.elements.Label;
import com.IgniteEvents.automation.elements.TextBox;
import com.IgniteEvents.automation.elements.impl.ButtonImpl;
import com.IgniteEvents.automation.elements.impl.CheckBoxImpl;
import com.IgniteEvents.automation.elements.impl.LabelImpl;
import com.IgniteEvents.automation.elements.impl.TextBoxImpl;


public class LoginPage extends Browser{

	Button	    LoginButton = new ButtonImpl("//descendant-or-self::button", "Login button");
	TextBox 	LoginField = new TextBoxImpl("//input[@name='email']", "Login field");
	TextBox 	PasswordField = new TextBoxImpl("//input[@id='password']", "Password field");
	CheckBox 	RemebmerMe = new CheckBoxImpl("//label[contains(text(), 'Remember')]/input", "Remember my email address checkbox");
	Button 		LearnMoreLink = new ButtonImpl("//a[contains(text(), 'Learn more')]", "Learn more link");
	Label 		InvalidEmailAddressOrPasswordValidationLabel = new LabelImpl("//*[text()=\"We can't find the email address and password combo you entered. Please try again.\"]", "Invalid email address or password, please try again message");
	Label 		EmailAddressandPasswordIsRequiredValidationLabel = new LabelImpl("//*[contains(text(),'An email address and password is required to sign in.')]", "An email address and password is required to sign in message");
	Button      endHelp = new ButtonImpl("//div[@class='inmplayer-popover-button-end']", "End");
	
	public LoginPage(){
	}
	
	public LoginPage(boolean doLogOut){
		if (doLogOut) {
			logOut();
		}
	}
	
	public HomePage doSuccessLogin() {
		return doSuccessLogin(CommonUtils.getProperty(PropertyName.ADMIN_EMAIL), CommonUtils.getProperty(PropertyName.ADMIN_PASSWORD));
	}
	
	public HomePage doSuccessLogin(String userName, String password) {
		open();
		sleep(3);
		if(new HomePage().dashboardTab.isNotExists()) {
			//logOut();
			LoginField.type(userName);
			PasswordField.removeAttribute("readonly");
			PasswordField.type(password);
			LoginButton.click();
			CommonUtils.setProperty(PropertyName.CURRENT_LOGIN, userName);
			CommonUtils.setProperty(PropertyName.CURRENT_PASSWORD, password);
			if (userName.equalsIgnoreCase(CommonUtils.getProperty(PropertyName.ADMIN_EMAIL))) {
				CommonUtils.setProperty(PropertyName.CURRENT_FIRST_NAME, CommonUtils.getProperty(PropertyName.ADMIN_FIRST_NAME));
				CommonUtils.setProperty(PropertyName.CURRENT_LAST_NAME, CommonUtils.getProperty(PropertyName.ADMIN_LAST_NAME));
			}else{
				CommonUtils.setProperty(PropertyName.CURRENT_FIRST_NAME, CommonUtils.getProperty(PropertyName.CM_FIRST_NAME));
				CommonUtils.setProperty(PropertyName.CURRENT_LAST_NAME, CommonUtils.getProperty(PropertyName.CM_LAST_NAME));
			}
		}
		endHelp.setImplicity(3);
		if(!endHelp.isNotDisplayed()){
			endHelp.click();
		}
		return new HomePage();
	}
	
	public LoginPage doFailLogin(String userName, String password) {
		open();
		LoginField.type(userName);
		PasswordField.removeAttribute("readonly");
		PasswordField.type(password);
		LoginButton.click();
		return this;
	}
	
	public HomePage doSuccessLoginAndRememberMe(String userName, String password) {
		RemebmerMe.check();
		return doSuccessLogin(userName, password);
	}
	
	public LoginPage openPage() {
		super.open();
		return this;
	}
	
	
	
	
	private void openWidget(String string) {
		String currentWindowHandle = super.openInNewWindow(string);		
		sleep(5);
		CommonUtils.setProperty(PropertyName.CURRENT_WINDOW_HANDLE, currentWindowHandle);	
	}
	
}
