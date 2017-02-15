package com.IgniteEvents.automation.common;

import org.testng.Assert;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;

import java.util.Map;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;

import static org.hamcrest.CoreMatchers.containsString;

public class RestAssured {
	
	String authToken = "";
	String orgID = "";
	String orgName = "";
	//String host = "hq.salsalabs.org";

	@Test
	public void makeSyreThatstatusCode200() {
		Response res = given().when().get("https://httpbin.org/get");
		Assert.assertEquals(res.statusCode(), 200);

		assertThat(res, Matchers.notNullValue());
		assertThat(res.statusCode(), Matchers.lessThan(300));
		assertThat(res.statusCode(), Matchers.equalTo(200));
		assertThat(res.statusCode(), Matchers.equalTo(200));

		// super cool
		assertThat(res.path("headers"), Matchers.hasEntry("Host", "httpbin.org"));
		assertThat(res.path("headers"), Matchers.hasKey("Host"));
	}

	@Test
	public void extractResponseasString() {
		Response res = given().when().get("https://httpbin.org/get").then().contentType(ContentType.JSON).extract()
				.response();
		String response = res.asString();
		assertThat(response, containsString("httpbin.org"));
		Assert.assertTrue(response.contains("httpbin.org"));

	}

	@Test
	public void gettingAJson() {
		// do not forget to specify base uri RestAssured.baseURI =
		// "https://maps.googleapis.com"
		// and later use Endpoints like this .get
		// ("/maps/api/place/textsearch/json").

		given().when().get("https://httpbin.org/get").then().body(containsString("gzip,deflate")).body("headers.Host",
				equalTo("httpbin.org"));

	}

	/*
	 * @Test () public void gettingAValueFromExtractedResponse() {
	 * 
	 * 
	 * Response resp = given().when().get("https://httpbin.org/get").then()
	 * .contentType(ContentType.JSON) .extract().response();
	 * 
	 * 
	 * 
	 * // Verification using assert EXAMLE String response = resp.asString();
	 * assertThat(response, containsString("gzip,deflate"));
	 * assertThat(response, containsString("https://httpbin.org/get"));
	 * assertThat(response, containsString("91.234.43.58"));
	 * 
	 * }
	 */

	@Test
	public void gettingdataFromJsonResponse() {

		Response resp = given().when().get("https://httpbin.org/get").then().contentType(ContentType.JSON).extract()
				.response();

		String url = resp.path("url");
		String host = resp.path("headers.Host");
		Map<String, String> headers = new HashMap<String, String>();
		headers = resp.path("headers");

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}

		System.out.println(url);
		System.out.println(host);
		System.out.println(headers);

		// String response = resp.asString();

	}

	/*
	 * @Test public void checkAllRidesAreOpen() { // put all of the ride states
	 * into a list List<String> rideStates = response.path("state"); // check
	 * that they are all 'open' for (String rideState : rideStates) {
	 * assertThat(rideState, equalTo("open")); } }
	 */

	@Test
	public void loginTest()
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {

		Response firstLoginEndpoint = given().config(config().sslConfig(new SSLConfig().allowAllHostnames()))
				.relaxedHTTPSValidation()
				.body("{\"header\":{},\"payload\":{\"username\":\"qb.74580786@mailosaur.in\",\"password\":\"Gnusmas_1\"}}")
				.when().contentType(ContentType.JSON).post("https://hq.test.igniteaction.net/api/auth/login.json")
				.then().extract().response();
		authToken = firstLoginEndpoint.path("header.authToken");
		 orgID = firstLoginEndpoint.path("payload.organization[0].id");
		 orgName = firstLoginEndpoint.path("payload.organization[0].name");

			Response secondLoginEndpoint = 	given().config(config().sslConfig(new SSLConfig().allowAllHostnames()))
				.relaxedHTTPSValidation().header("authToken", authToken)
				.body("{\"header\":{},\"payload\":{\"organization\":[{\"id\":\"" + orgID + "\",\"name\":\"" + orgName + "\"}]}}")
				.when().contentType(ContentType.JSON).post("https://hq.test.igniteaction.net/api/auth/login.json")
				.then()
				.extract()
				.response();
			authToken = secondLoginEndpoint.path("header.authToken");
			
			System.out.println("This is Final working AuthToken" + " " + authToken  );
			
			Response createSupporter = 	given().config(config().sslConfig(new SSLConfig().allowAllHostnames()))
					.relaxedHTTPSValidation().header("authToken", authToken)
					.body("{\"header\":{},\"payload\":{\"language\":\"en-US\",\"contentChannelStatus\":[{\"contentChannelName\":\"Advocacy\",\"contentChannelId\":\"c6d22535-898a-417f-8154-48e27c1cec5d\",\"contactMethod\":\"EMAIL\",\"contactStatus\":\"OPTED_IN\"},{\"contentChannelName\":\"Fundraising\",\"contentChannelId\":\"d9c229d6-2336-43fa-b824-68fad27b03b4\",\"contactMethod\":\"EMAIL\",\"contactStatus\":\"OPTED_IN\"},{\"contentChannelName\":\"General\",\"contentChannelId\":\"def510fa-d6fa-4a76-bf00-798608f7465d\",\"contactMethod\":\"EMAIL\",\"contactStatus\":\"OPTED_IN\"}],\"contacts\":[{\"type\":\"PhoneHome\",\"value\":\"\"},{\"type\":\"PhoneCell\",\"value\":\"\"},{\"type\":\"PhoneWork\",\"value\":\"\"},{\"type\":\"MessagingEmail\",\"value\":\"apitestsupporter@mail.ru\",\"status\":\"OptedIn\"},{\"type\":\"SocialFacebook\",\"value\":\"\"},{\"type\":\"SocialTwitter\",\"value\":\"\"},{\"type\":\"LINKEDIN\",\"value\":\"\"}],\"customFields\":[{\"fieldDefId\":\"b02a1547-208d-4211-a174-936486abc406\",\"value\":\"\"},{\"fieldDefId\":\"ae2f99b7-bfdc-4093-9dcb-5da927cf2b90\",\"value\":\"\"},{\"fieldDefId\":\"43ee13cb-cd36-4b5e-b868-08a5b503f853\",\"value\":\"true\"},{\"fieldDefId\":\"193b0644-a025-45b4-9a58-96f7d417acaf\",\"value\":\"\"},{\"fieldDefId\":\"3e66cbac-2118-4063-b8bc-f9105f9788d5\",\"value\":\"\"},{\"fieldDefId\":\"314aa79c-2aba-4ede-9d4d-45a6f78501cf\",\"value\":\"\"},{\"fieldDefId\":\"fe355492-c6ee-42f2-96d7-4656b24a4d3a\"},{\"fieldDefId\":\"34f51368-7ead-414d-8de2-5c40437a325f\",\"value\":\"true\"},{\"fieldDefId\":\"3bdd931a-8bc1-4a3c-9ffa-e90f20afd730\",\"value\":\"\"},{\"fieldDefId\":\"5e22ccb0-d4e2-4da7-b8fc-25a3b01869b1\",\"value\":\"\"},{\"fieldDefId\":\"a79970e0-8d67-4f2d-8993-6a265cadbb7b\",\"value\":\"\"},{\"fieldDefId\":\"62fc6ccc-348c-4b8a-8019-6b621944b9fd\",\"value\":\"true\"},{\"fieldDefId\":\"35b7ed88-73cd-4dd2-ba5a-2ab766965211\",\"value\":\"\"},{\"fieldDefId\":\"50fe337b-8811-408e-8697-9e7f70812414\",\"value\":\"\"},{\"fieldDefId\":\"b2db9f3c-1af2-4a62-9f76-c9a67844bce8\",\"value\":\"\"},{\"fieldDefId\":\"1082ab51-6e82-4796-acc9-453af9a885f4\",\"value\":\"\"},{\"fieldDefId\":\"aa5e6c75-03bf-4cec-87c4-185eb2e60b7a\",\"value\":\"\"},{\"fieldDefId\":\"547d20bb-3da2-4b08-ac78-3782a78882e5\",\"value\":\"\"},{\"fieldDefId\":\"1a14747a-c2b0-440a-8dcc-e0751477cedf\",\"value\":\"\"},{\"fieldDefId\":\"94aff783-df45-4b2a-8c7b-c574b5d6c85f\",\"value\":\"true\"},{\"fieldDefId\":\"cbd04a78-689d-489d-9587-00f6aff8711a\",\"value\":\"\"},{\"fieldDefId\":\"30a953d5-a580-4a4f-8054-3292d53553da\",\"value\":\"\"}],\"addresses\":[{\"line1\":\"\",\"line2\":\"\",\"city\":\"\",\"state\":\"\",\"zip\":\"\",\"addressType\":\"AddressHome\"}]}}").log().everything()
					.when().contentType(ContentType.JSON).post("https://hq.test.igniteaction.net/api/person/supporter")
					.then()
					.extract()
					.response();
				
				
				System.out.println("1111111111111111111"+ createSupporter.asString() + "11111111111111111111111111111111111");


	}

}
