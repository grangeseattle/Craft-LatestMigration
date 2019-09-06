package com.cognizant.framework;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.cognizant.framework.Settings;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class RESTclient {
	private static Properties properties = Settings.getInstance();
	public static final String JIRA_URL = properties.getProperty("Jira_Url");
	public static final String JIRA_USERNAME = properties
			.getProperty("Jira_UserName");
	public static final String JIRA_PASSWORD = properties
			.getProperty("Jira_Password");
	public static final String JIRA_PROJECT_ID = properties
			.getProperty("Jira_Project_ID");
	public static final String JIRA_ISSUE_REPORTER = properties
			.getProperty("Jira_Issue_Reporter");
	public static final String JIRA_ISSUE_TYPE = properties
			.getProperty("Jira_Issue_Type");

	public static void defectLog(String summary, String description,
			String screenShotPath) {
		try {
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(JIRA_USERNAME,
					JIRA_PASSWORD));
			WebResource webResource = client.resource(JIRA_URL
					+ "/rest/api/2/issue");
			String input = "{\"fields\":{\"project\":{\"id\":\""
					+ JIRA_PROJECT_ID + "\"},\"summary\":\"" + summary
					+ "\",\"description\":\"" + description
					+ "\", \"reporter\": {\"name\": \"" + JIRA_ISSUE_REPORTER
					+ "\"},\"issuetype\":{\"name\":\"" + JIRA_ISSUE_TYPE
					+ "\"}}}";
			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, input);
			String output = response.getEntity(String.class);
			String[] parts1 = output.split(":");
			String issueKey = parts1[2];
			String[] parts2 = issueKey.split(",");
			issueKey = parts2[0];
			issueKey = issueKey.replace("\"", "");

			// For Add attachment to Jira issue
			try {
				addAttachmentToIssue(issueKey, screenShotPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static boolean addAttachmentToIssue(String issueKey,
			String screenShotPath) throws IOException {
		String jira_attachment_authentication = new String(
				org.apache.commons.codec.binary.Base64
						.encodeBase64((JIRA_USERNAME + ":" + JIRA_PASSWORD)
								.getBytes()));

		CloseableHttpClient httpclient = HttpClients.createSystem();
		HttpPost httppost = new HttpPost(JIRA_URL + "/rest/api/2/issue/"
				+ issueKey + "/attachments");
		httppost.setHeader("X-Atlassian-Token", "no-check");
		httppost.setHeader("Authorization", "Basic "
				+ jira_attachment_authentication);
		String encryptedPath = WhitelistingPath.cleanStringForFilePath(screenShotPath);
		File fileToUpload = new File(encryptedPath);
		FileBody fileBody = new FileBody(fileToUpload);
		HttpEntity entity = MultipartEntityBuilder.create()
		.addPart("file", fileBody).build();
		httppost.setEntity(entity);
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httppost);
		} finally {
			httpclient.close();
		}
		if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("ScreenShot: " + screenShotPath
					+ " attached to the issue " + issueKey);
			return true;
		} else {
			System.out.println("ScreenShot: " + screenShotPath
					+ " not attached to the issue " + issueKey);
			return false;
		}

	}

}