package com.IgniteEvents.automation.common;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.given;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class HttpClientMod {
	private static final Logger logger = SeleneseTestCase.logger;

	String authToken = "";
	String orgID = "";
	String orgName = "";
	String host = "hq.test.igniteaction.net";
	Response response = null;

	public HttpClientMod() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

	}

	public HttpClientMod login(String userName, String pass)
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {

		Response firstLoginEndpoint = given().config(config().sslConfig(new SSLConfig().allowAllHostnames()))
				.relaxedHTTPSValidation()
				.body("{\"header\":{},\"payload\":{\"username\":\"" + userName + "\",\"password\":\"" + pass + "\"}}")
				.when().contentType(ContentType.JSON).post("https://" + host + "/api/auth/login.json").then().extract()
				.response();
		authToken = firstLoginEndpoint.path("header.authToken");
		orgID = firstLoginEndpoint.path("payload.organization[0].id");
		orgName = firstLoginEndpoint.path("payload.organization[0].name");

		Response secondLoginEndpoint = given().config(config().sslConfig(new SSLConfig().allowAllHostnames()))
				.relaxedHTTPSValidation().header("authToken", authToken)
				.body("{\"header\":{},\"payload\":{\"organization\":[{\"id\":\"" + orgID + "\",\"name\":\"" + orgName
						+ "\"}]}}")
				.when().contentType(ContentType.JSON).post("https://" + host + "/api/auth/login.json").then().extract()
				.response();
		authToken = secondLoginEndpoint.path("header.authToken");
		System.out.println(authToken);
		return this;

	}

	public HttpClientMod login() {
		try {
			login("qb.74580786@mailosaur.in", "Gnusmas_1");
		} catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}

		return this;

	}

	public Response sendPostrequest(String jsonString, String endPoint) {
		Response postResponse = given().config(config().sslConfig(new SSLConfig().allowAllHostnames()))
				.relaxedHTTPSValidation().header("authToken", authToken).body(jsonString).log().everything().when()
				.contentType(ContentType.JSON).post("https://" + host + endPoint).then().extract().response();

		return postResponse;

	}

	public Response sendGetrequest(String endPoint) {
		Response getResponse = given().config(config().sslConfig(new SSLConfig().allowAllHostnames()))
				.relaxedHTTPSValidation().header("authToken", authToken).when().get("https://" + host + endPoint).then()
				.contentType(ContentType.JSON).extract().response();
		response = getResponse;
		System.out.println(response.asString());

		return getResponse;

	}

	public HttpClientMod createSupporter(String jsonString) {
		sendPostrequest(jsonString, "/api/person/supporter");
		logger.info("Supporter is created");
		return this;

	}

	public Map<Integer, Supporter> getSupporters() {
		Map<Integer, Supporter> data = new HashMap<Integer, Supporter>();

		Response res = sendGetrequest(
				"/api/search/supporters?criteria=&listOffset=0&listResults=250&sortField=createdDate&sortOrder=DESCENDING");
		logger.info("Getting  the list of supporters in the organization");

		int ammountofSupporters = res.path("payload.total");
		System.out.println(ammountofSupporters);
		
		 //please note , than

		for (int i = 0; i < 5; i++) {
			Supporter supporter = new Supporter();
			supporter.setEmail(res.path("payload.supporters[" + i + "].contacts[0].value"));
			supporter.setFirstName(res.path("payload.supporters[" + i + "].firstName"));
			data.put(i, supporter);
			
			data.forEach((k, v) -> System.out.println("Key : " + k  + " " + "Email" + " ------------- "
					+ v.getEmail() + "   " + "FIrstName" + "---- " + v.getFirstName()));
			
		}
		System.out.println(data.size());
		return data;

	}

	//

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String endPoint = "https://hq.test.igniteaction.net/api/person/supporter";
		String json = "{\"header\":{},\"payload\":{\"language\":\"en-US\",\"contentChannelStatus\":[{\"contentChannelName\":\"Advocacy\",\"contentChannelId\":\"c6d22535-898a-417f-8154-48e27c1cec5d\",\"contactMethod\":\"EMAIL\",\"contactStatus\":\"OPTED_IN\"},{\"contentChannelName\":\"Fundraising\",\"contentChannelId\":\"d9c229d6-2336-43fa-b824-68fad27b03b4\",\"contactMethod\":\"EMAIL\",\"contactStatus\":\"OPTED_IN\"},{\"contentChannelName\":\"General\",\"contentChannelId\":\"def510fa-d6fa-4a76-bf00-798608f7465d\",\"contactMethod\":\"EMAIL\",\"contactStatus\":\"OPTED_IN\"}],\"contacts\":[{\"type\":\"PhoneHome\",\"value\":\"\"},{\"type\":\"PhoneCell\",\"value\":\"\"},{\"type\":\"PhoneWork\",\"value\":\"\"},{\"type\":\"MessagingEmail\",\"value\":\"apitestsupporter@mail.ru\",\"status\":\"OptedIn\"},{\"type\":\"SocialFacebook\",\"value\":\"\"},{\"type\":\"SocialTwitter\",\"value\":\"\"},{\"type\":\"LINKEDIN\",\"value\":\"\"}],\"customFields\":[{\"fieldDefId\":\"b02a1547-208d-4211-a174-936486abc406\",\"value\":\"\"},{\"fieldDefId\":\"ae2f99b7-bfdc-4093-9dcb-5da927cf2b90\",\"value\":\"\"},{\"fieldDefId\":\"43ee13cb-cd36-4b5e-b868-08a5b503f853\",\"value\":\"true\"},{\"fieldDefId\":\"193b0644-a025-45b4-9a58-96f7d417acaf\",\"value\":\"\"},{\"fieldDefId\":\"3e66cbac-2118-4063-b8bc-f9105f9788d5\",\"value\":\"\"},{\"fieldDefId\":\"314aa79c-2aba-4ede-9d4d-45a6f78501cf\",\"value\":\"\"},{\"fieldDefId\":\"fe355492-c6ee-42f2-96d7-4656b24a4d3a\"},{\"fieldDefId\":\"34f51368-7ead-414d-8de2-5c40437a325f\",\"value\":\"true\"},{\"fieldDefId\":\"3bdd931a-8bc1-4a3c-9ffa-e90f20afd730\",\"value\":\"\"},{\"fieldDefId\":\"5e22ccb0-d4e2-4da7-b8fc-25a3b01869b1\",\"value\":\"\"},{\"fieldDefId\":\"a79970e0-8d67-4f2d-8993-6a265cadbb7b\",\"value\":\"\"},{\"fieldDefId\":\"62fc6ccc-348c-4b8a-8019-6b621944b9fd\",\"value\":\"true\"},{\"fieldDefId\":\"35b7ed88-73cd-4dd2-ba5a-2ab766965211\",\"value\":\"\"},{\"fieldDefId\":\"50fe337b-8811-408e-8697-9e7f70812414\",\"value\":\"\"},{\"fieldDefId\":\"b2db9f3c-1af2-4a62-9f76-c9a67844bce8\",\"value\":\"\"},{\"fieldDefId\":\"1082ab51-6e82-4796-acc9-453af9a885f4\",\"value\":\"\"},{\"fieldDefId\":\"aa5e6c75-03bf-4cec-87c4-185eb2e60b7a\",\"value\":\"\"},{\"fieldDefId\":\"547d20bb-3da2-4b08-ac78-3782a78882e5\",\"value\":\"\"},{\"fieldDefId\":\"1a14747a-c2b0-440a-8dcc-e0751477cedf\",\"value\":\"\"},{\"fieldDefId\":\"94aff783-df45-4b2a-8c7b-c574b5d6c85f\",\"value\":\"true\"},{\"fieldDefId\":\"cbd04a78-689d-489d-9587-00f6aff8711a\",\"value\":\"\"},{\"fieldDefId\":\"30a953d5-a580-4a4f-8054-3292d53553da\",\"value\":\"\"}],\"addresses\":[{\"line1\":\"\",\"line2\":\"\",\"city\":\"\",\"state\":\"\",\"zip\":\"\",\"addressType\":\"AddressHome\"}]}}";
		// new HttpClientMod().login().createSupporter(json);
		new HttpClientMod().login().getSupporters();

	}

}
