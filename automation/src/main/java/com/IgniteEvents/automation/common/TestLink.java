package com.IgniteEvents.automation.common;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.Attachment;
import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.model.Platform;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;


public class TestLink{
	TestLinkAPI api = null;
	String url = "https://corp-testlink1.corp.salsalabs.net/lib/api/xmlrpc/v1/xmlrpc.php";
    String devKey = "a0bf6b83d90e1e721da8765ecf456873"; 
    String projectName = "QA SandBox";
    String planName = "BuildAcceptanceTest";
    String buildName = "Ignite 0.15";
    String plaformName = "";
    Integer executionID;
    
    TestProject project;
    TestPlan plan;
    Build build;
    Platform platform;

    
	public TestLink(String planName, String buildName) throws Exception {
		this.planName = planName;
		this.buildName = buildName;
        URL testlinkURL = null;
        
        try     {
                testlinkURL = new URL(url);
        } catch ( MalformedURLException mue )   {
                mue.printStackTrace( System.err );
                System.exit(-1);
        }
        
     // Create a trust manager that does not validate certificate chains
 		TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
 				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
 					return null;
 				}
 				public void checkClientTrusted(X509Certificate[] certs, String authType) {
 				}
 				public void checkServerTrusted(X509Certificate[] certs, String authType) {
 				}
 			}
 		};

 		// Install the all-trusting trust manager
 		SSLContext sc = SSLContext.getInstance("SSL");
 		sc.init(null, trustAllCerts, new java.security.SecureRandom());
 		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

 		// Create all-trusting host name verifier
 		HostnameVerifier allHostsValid = new HostnameVerifier() {
 			public boolean verify(String hostname, SSLSession session) {
 				return true;
 			}
 		};
 		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
 		//HttpsURLConnection.
 		api = new TestLinkAPI(testlinkURL, devKey);
 		project = getProjectByName();
 		plan = getTestPlanByName();
 		build = getBuildByName();
 		platform =  getPlatformByName ();
	}
	
	public Integer updateTestCaseResult(int externalId, int id, ExecutionStatus status, String note){
		TestPlan plan = api.getTestPlanByName(planName, projectName);
		executionID = reportTCResult(externalId,
				id,
        		plan.getId(),
        		status,
        		build.getId(),
        		buildName,
        		note,
        		false,
        		null,
        		null,
        		null,
        		null,
        		true).getExecutionId();
		return executionID;
   }
	
	private Build getBuildByName () {	
		
		Build[] builds = api.getBuildsForTestPlan(plan.getId());
		for (int i = 0; i < builds.length; i++) {
			if (buildName.equalsIgnoreCase(builds[i].getName())) {
				return builds[i];
			}
		}
		return null;
	}
	
	private TestProject getProjectByName () {	
		TestProject[] s = api.getProjects();
		for (int i = 0; i < s.length; i++) {
			if (projectName.equalsIgnoreCase(s[i].getName())) {
				return s[i];
			}
		}
		return null;
	}
	
	private TestPlan getTestPlanByName () {	
		TestPlan[] s = api.getProjectTestPlans(project.getId());
		for (int i = 0; i < s.length; i++) {
			if (planName.equalsIgnoreCase(s[i].getName())) {
				return s[i];
			}
		}
		return null;
	}
	
	private Platform getPlatformByName () {	
		Platform[] s = api.getProjectPlatforms(project.getId());
		for (int i = 0; i < s.length; i++) {
			if (plaformName.equalsIgnoreCase(s[i].getName())) {
				return s[i];
			}
		}
		return null;
	}
	
	public ExecutionStatus getLastExecutionResult (Integer testCaseExternalId, Integer testCaseId) {	
		return api.getLastExecutionResult(plan.getId(), testCaseId, testCaseExternalId).getStatus();
	}
	
	public Integer getLastExecutionId (Integer testCaseExternalId, Integer testCaseId) {	
		return api.getLastExecutionResult(plan.getId(), testCaseId, testCaseExternalId).getId();
	}
	
	public String getLastExecutionNotes(Integer testCaseExternalId, Integer testCaseId) {	
		return api.getLastExecutionResult(plan.getId(), testCaseId, testCaseExternalId).getNotes();
	}
		
	private ReportTCResultResponse reportTCResult(Integer testCaseId,
    Integer testCaseExternalId,
    Integer testPlanId,
    ExecutionStatus status,
    Integer buildId,
    String buildName,
    String notes,
    boolean guess,
    String bugId,
    Integer platformId,
    String platformName,
    Map<String,String> customFields,
    boolean overwrite) {
		return api.reportTCResult(testCaseId, testCaseExternalId, testPlanId, status, buildId, buildName, notes, guess, bugId, platformId, platformName, customFields, overwrite);
	}
	
	public Attachment uploadExecutionAttachment(File file) {
		String fileContent = null;
        
        try {
                byte[] byteArray = FileUtils.readFileToByteArray(file);
        fileContent = new String(Base64.encodeBase64(byteArray));
        } catch (IOException e) {
                e.printStackTrace( System.err );
                System.exit(-1);
        }
		return api.uploadExecutionAttachment(executionID, "Screenshot", "Screenshot of failed step", file.getName(), "image/png", fileContent);
		
	}

}
