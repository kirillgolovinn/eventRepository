package  com.IgniteEvents.automation.pages.hq;


import java.util.Set;

import com.IgniteEvents.automation.common.Browser;
import com.IgniteEvents.automation.common.CommonUtils;
import com.IgniteEvents.automation.common.Environment;
import com.IgniteEvents.automation.common.PropertyName;

import com.IgniteEvents.automation.common.SeleneseTestCase;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.Button;
import com.IgniteEvents.automation.elements.DropDown;
import com.IgniteEvents.automation.elements.Label;
import com.IgniteEvents.automation.elements.Panel;
import com.IgniteEvents.automation.elements.TextBox;
import com.IgniteEvents.automation.elements.impl.ButtonImpl;
import com.IgniteEvents.automation.elements.impl.DropDownImpl;
import com.IgniteEvents.automation.elements.impl.LabelImpl;
import com.IgniteEvents.automation.elements.impl.PanelImpl;
import com.IgniteEvents.automation.elements.impl.TextBoxImpl;



public class HomePage extends Browser{
	
	public Panel feedBackDialogPanel = new PanelImpl("//feedback-dialog/div[contains(@class, 'feedback alert-box')]", "Feedback dialog");
	public Button closeFeedbackDialog = new ButtonImpl(feedBackDialogPanel.getPath() + "/descendant::a[@class='close']", "Close feedback dialog");
	Label userlabel = new LabelImpl("//div[@id='account-info-drop']/a[text()='" + 
			CommonUtils.getProperty(PropertyName.CURRENT_FIRST_NAME) + " " + 
			CommonUtils.getProperty(PropertyName.CURRENT_LAST_NAME) +"']"
			, "Drop with user name");
	Label orglabel = new LabelImpl(userlabel.getPath() + "/span[text()='" + CommonUtils.getProperty(PropertyName.ADMIN_ORG_NAME) + "']", "Drop with organization name");
	
	//left navigation bar
	//Button audienceTab = new ButtonImpl("//a[text()='Supporters']", "Audience tab");
	Button audienceTab = new ButtonImpl("//a[@href='/#/audience/supporters']", "Audience tab");
	Button activitiesTab = new ButtonImpl("//a[@href='/#/activities']", "Activities tab");
	Button messagingTab = new ButtonImpl("//a[@href='/#/messaging']", "Messaging tab");
	Button donationTab = new ButtonImpl("//a[@href='/#/insight/donations']", "Donations tab");
	Button dashboardTab = new ButtonImpl("//a[@href='/#/dashboard']", "Dashboard tab");
	Button assetsTab = new ButtonImpl ("//a[@href='/#/assets']", "Assets tab");
	
	
	//Top navigation bar
	Button settingsTab = new ButtonImpl("//div[contains(@class, 'hide-for-small')]/descendant::a[@title='Manage']", "Manage page");
	Button alertsTab = new ButtonImpl("//div[contains(@class, 'hide-for-small')]/descendant::a[@title='Alerts']", "Alerts popup");
	Button newsTab = new ButtonImpl("//div[contains(@class, 'hide-for-small')]/descendant::a[@title='News']", "News popup");
	Button helpTab = new ButtonImpl("//div[@id='topNav_help']/a", "Help");
	
	DropDown helpItems = new DropDownImpl("//div[@id='topNav_help']/ul", "Help Items");
	

	
	//Configure new org
	Button nextButtonConfigNewOrgPage = new ButtonImpl("//div[@class='row' and @ng-show='isNewOrg']/descendant::*[contains(text(), 'Save')]/ancestor-or-self::button", "Save & Keep Going!");
	Button saveButtonConfigNewOrgPage = new ButtonImpl("//div[@class='row' and @ng-show='isNewOrg']/descendant::*[contains(text(), \"Let's go!\")]/ancestor-or-self::button", "Let's go!");
	TextBox emailConfigNewOrgPage = new TextBoxImpl("//input[@id='email']", "New org email");
	TextBox HQEmailNewOrgPage = new TextBoxImpl("//input[@id='line1']", "HQ Mailing Address", true);
	TextBox zipConfigNewOrgPage = new TextBoxImpl("//input[@id='zip']", "Zip");
	TextBox cityConfigNewOrgPage = new TextBoxImpl("//input[@id='city']", "City");
	DropDown states = new DropDownImpl("//custom-select2[@data='states']/div/ul/li", "//custom-select2[@data='states']/div/a", "States");
	//supporterStatesField.selectByID(CommonUtils.getRandomValueFromTo(0, 71, 0));
	TextBox fromNameConfigNewOrgPage = new TextBoxImpl("//input[@name='fromName']", "From Name", true); 
	TextBox fromAddressConfigNewOrgPage = new TextBoxImpl("//input[@name='fromAddress']", "From Address", true);
	TextBox replyAddressConfigNewOrgPage = new TextBoxImpl("//input[@name='replyAddress']", "Reply Address", true);
	Button buyButton = new ButtonImpl("//a[contains(@ng-click,'goToBuyNow')]", "Buy");
	Button dashboardButton = new ButtonImpl("//a[@href='/#/dashboard']", "Dashboard");

	
	public HomePage verifyUserNameDisplayed() {
		verifier.verifyElementIsDisplayed(userlabel);
		return this;
	}
	
	public HomePage verifyOrgNameDisplayed() {
		verifier.verifyElementIsDisplayed(orglabel);
		return this;
	}
	
	public HomePage verifyHomePageIsOpened() {
		sleep(10);
		if (SeleneseTestCase.USED_ENVIRONMENT.getServer().equals(Environment.LocationOfServer.REMOTE)) {
			sleep(20);
		}
		verifier.verifyTrue(getLocation().contains("dashboard"), "Wrong url " + getLocation());
		return this;
	}
	
	

	
	
	public void switchToNewWindow(){
		Set<String> windows = SeleneseTestCase.driver.getWindowHandles();
		verifier.verifyTrue(windows.size() > 0, "New window hasn't been opened");
		for (String w : windows) {
			if (!w.equals(SeleneseTestCase.driver.getWindowHandle())) {
				SeleneseTestCase.driver.switchTo().window(w);
			}
		}
	}
	
	public ActivitiesPage openActivitiesPage() {
		sleep(4);
		activitiesTab.click();
		return new ActivitiesPage();
		
	}
	
	public HomePage openDashboard() {
		dashboardButton.click();
		return new HomePage();
	}
	
	
	
}
