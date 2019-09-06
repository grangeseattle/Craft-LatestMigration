package com.cognizant.framework.nft;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.cognizant.framework.NFT;

import org.jsoup.nodes.Element;

public class BrokenLinks {

	protected static List<String[]> env1 = new ArrayList<String[]>();
	protected static List<String[]> env2 = new ArrayList<String[]>();
	protected static List<String[]> env2testcase = new ArrayList<String[]>();
	protected static List<String[]> env1testcase = new ArrayList<String[]>();
	public static List<Integer> list = new ArrayList<Integer>();

	protected static HttpURLConnection httpURLConnect;
	static int responseStatus;
	static int responseCode;
	public static int PassCount1 = 0;
	public static int FailCount1 = 0;
	public static int PassCount2 = 0;
	public static int FailCount2 = 0;

	public static synchronized void saveBrokenLinkValidationForReport(String[] responseArray) {
		if (responseArray[3] == null || responseArray[4] == null) {
			env1.add(responseArray);
		} else if (responseArray[3].equalsIgnoreCase("Instance2")) {
			env2.add(responseArray);
		} else if (responseArray[3].equalsIgnoreCase("Instance1")) {
			env1.add(responseArray);
		}
	}

	//function for CITS
    public static void createhtml() 
    {
    	  Document htmlFile = null;
          try {
              htmlFile = Jsoup.parse(new File("Test//BrokenLinks.html"), "ISO-8859-1");
        
          String title = htmlFile.title();
        
          Element div = htmlFile.getElementById("tblBody");
         // String cssClass = div.className(); // getting class form HTML element
         // div.append("<tr><td>1</td><td>http://newtours.demoaut.com/</td><td>Response code : 200 - OK</td><td class='PASS'>PASS</td><td><img class='zoom' src='./newtours.demoaut.com.jpg'></td></tr>");
          //Document doc = Jsoup.parse(htmlString);
          div.append(getScript(env1));
          System.out.println("title : " + htmlFile.text());
			WriteToFile(htmlFile, "Test//newTest//BrokenLinks.html");
         
          System.out.println("Jsoup can also parse HTML file directly");
        
          System.out.println("class of div tag : " );
          } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } // right
     }

	public static void createBrokenLinkReport(String pathOfReport) {
		try {
			String htmlString = "<!DOCTYPE html>\n" + "<html style=\"\n" + "    background-color: #f2f7f8;\n" + "\">"
					+ getCSS() + "    <body style='background-color:#f2f7f8'>\n" + "        <div class='row' style=\"\n"
					+ "    background-color: #f2f7f8;\n" + "\"> \n" + "            <div id='loadContent1'>\n"
					+ "                <table id='instance1'>\n" + "                    <thead>\n"
					+ "                        <tr class='heading'>\n"
					+ "                            <th class='theadBL'>Step No</th>\n"
					+ "<th class='theadBL'>Link</th>\n"
					+ "                            <th class='theadBL'>Description</th>\n"
					+ "                            <th class='theadBL'>Status</th>\n"
					+ "                            <th class='theadBL'>ScreenShot</th>\n" + "</tr>\n"
					+ "                    </thead>\n" + "                    <tbody class='instance1'>"
					+ getScript(env1) + "</tbody>\n" + "                </table>\n" + "</div>\n" + "</div>\n"
					+ "    </body>\n" + "</html>";
			Document doc = Jsoup.parse(htmlString);
			WriteToFile(doc, pathOfReport + "/BrokenLinks.html");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Inside the exception");
		}
	}

	public static void createBrokenLinkComparisionReport(String pathOfReport) {
		if (NFT.executeBrokenLinks) {
		try {
			String htmlString = "<!DOCTYPE html>\n" + "<html style=\"\n" + "    background-color: #f2f7f8;\n" + "\">"
					+ getCSS() + "    <body style='background-color:#f2f7f8'>\n" + brokenLinkSummary()
					+ "        <div class='row' style=\"\n" + "    background-color: #f2f7f8;\n" + "\"> \n"
					+ "            <div class=\"col-6\" id='loadContent1'>\n"
					+ "                <table id='instance1'>\n" + "                    <thead>\n"
					+ "                        <tr class='heading'>\n"
					+ "                            <th class='theadBL'>Step No</th>\n"
					+ "                            <th class='theadBL'>TestCase Name</th>\n"
					+ "                            <th class='theadBL'>Step Name</th>\n"
					+ "                            <th class='theadBL'>Description</th>\n"
					+ "                            <th class='theadBL'>Status</th>\n"
					+ "                            <th class='theadBL'>ScreenShot</th>\n"
					+ "                        </tr>\n" + "                    </thead>\n"
					+ "                    <tbody class='instance1'>" + getScriptForComparison(env1) + "</tbody>\n"
					+ "                </table>\n" + "            </div>\n"
					+ "            <div class='col-6' id='loadContent2'>\n" + "                <table id='instance2'>\n"
					+ "                    <thead>\n" + "                        <tr class='heading'>\n"
					+ "                            <th class='theadBL'>Step No</th>\n"
					+ "                            <th class='theadBL'>TestCase Name</th>\n"
					+ "                            <th class='theadBL'>Step Name</th>\n"
					+ "                            <th class='theadBL'>Description</th>\n"
					+ "                            <th class='theadBL'>Status</th>\n"
					+ "                            <th class='theadBL'>ScreenShot</th>\n"
					+ "                        </tr>\n" + "                    </thead>\n"
					+ "                    <tbody class='instance2'>" + getScriptForComparison(env2) + "</tbody>\n"
					+ "                </table>\n" + "            </div>\n" + "        </div>\n" + "    </body>\n"
					+ "</html>";
			Document doc = Jsoup.parse(htmlString);
			WriteToFile(doc, pathOfReport + "/BrokenLinks.html");
		} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Inside the exception");
			}
		}
	}

	public static String getCSS() {
		return "<head>\n" + "    <meta charset='UTF-8'>\n" + "    <title>BrokenLinks</title>\n"
				+ "    <style type='text/css'>\n" + "        body {\n" + "            background-color: #ffffff;\n"
				+ "            font-family: Verdana, Geneva, sans-serif;\n" + "            text-align: center;\n"
				+ "        }\n" + "\n" + "        small {\n" + "            font-size: 0.7em;\n" + "        }\n" + "\n"
				+ "        table {\n" + "            width: 95%;\n" + "            margin-left: auto;\n"
				+ "            margin-right: auto;\n" + "        }\n" + "\n" + "        tr.heading {\n"
				+ "            background-color: #A9D0F5;\n" + "            color: #000000;\n"
				+ "            font-size: 0.6em;\n" + "            font-weight: bold;\n" + "        }\n" + "\n"
				+ "        tr.subheading {\n" + "            background-color: #E0E6F8;\n"
				+ "            color: #34495E;\n" + "            font-weight: bold;\n"
				+ "            font-size: 0.6em;\n" + "            text-align: justify;\n" + "        }\n" + "\n"
				+ "        tr.section {\n" + "            background-color: #E0E6F8;\n"
				+ "            color: #333300;\n" + "            cursor: pointer;\n"
				+ "            font-weight: bold;\n" + "            font-size: 0.6em;\n"
				+ "            text-align: justify;\n" + "        }\n" + "\n" + "        tr.subsection {\n"
				+ "            background-color: #EDEEF0;\n" + "            cursor: pointer;\n" + "        }\n" + "\n"
				+ "        tr.content {\n" + "            background-color: #EDEEF0;\n"
				+ "            color: #000000;\n" + "            font-size: 0.6em;\n" + "        }\n" + "\n"
				+ "        td {\n" + "            padding: 4px;\n" + "            text-align: center;\n"
				+ "            word-wrap: break-word;\n" + "            max-width: 450px;\n" + "        }\n" + "\n"
				+ "        th {\n" + "            padding: 4px;\n" + "            text-align: center;\n"
				+ "            max-width: 450px;\n" + "        }\n" + "        th.theadBL {\n"
				+ "            text-align: center;\n" + "        }\n" + "        td.justified {\n"
				+ "            text-align: center;\n" + "        }\n" + "\n" + "        td.PASS {\n"
				+ "            font-weight: bold;\n" + "            color: green;\n" + "        }\n" + "\n"
				+ "        td.FAIL {\n" + "            font-weight: bold;\n" + "            color: red;\n"
				+ "        }\n" + "\n" + "        td.done,\n" + "        td.screenshot {\n"
				+ "            font-weight: bold;\n" + "            color: black;\n" + "        }\n" + "\n"
				+ "        td.debug {\n" + "            font-weight: bold;\n" + "            color: blue;\n"
				+ "        }\n" + "\n" + "#loadContent1{ width:100%}" + "        td.warning {\n"
				+ "            font-weight: bold;\n" + "            color: orange;\n" + "        }\n"
				+ "        .col-sm-6 {\n" + "            background-color: #ffffff;\n" + "        }\n"
				+ "        .col-6 {\n" + "            background-color: #ffffff;\n" + "        }\n"
				+ "                img { \n" + "                    width:400px; \n"
				+ "                    height:300px; \n" + "                } \n"
				+ ".zoom {padding: 0px;background-color: green;transition: transform .1s;width:150px;height:150px;margin: 0 auto;}"
				+ ".zoom:hover {-ms-transform: scale(6);-webkit-transform: scale(6);transform: scale(6);e viewport)}"
				+ "    </style>\n"
				+ "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css'>"
				+ "<script src='https://code.jquery.com/jquery-3.2.1.min.js'></script>"
				+ "<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>"
				+ "<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js'></script>"
				+ "</head>";
	}

	public static String getScript(List<String[]> env) {
		String htmlTableCode = "";
		for (int i = 0; i < env.size(); i++) {
			htmlTableCode += "<tr class='content'><td>" + (i + 1) + "</td>" + "<td>" + env.get(i)[0].toString()
					+ "</td>" + "<td >" + env.get(i)[5].toString() + "</td><td class=" + env.get(i)[6].toString() + ">"
					+ env.get(i)[6].toString() + "</td>" + "<td><img class='zoom' src='" + env.get(i)[7]
					+ "'/></td></tr>";
		}
		return htmlTableCode;
	}

	public static String getScriptForComparison(List<String[]> env) {
		String htmlTableCode = "";

		for (int i = 0; i < env.size(); i++) {
			htmlTableCode += "<tr class='content'><td>" + (i + 1) + "</td>" + "<td>" + env.get(i)[4].toString()
					+ "</td>" + "<td >" + env.get(i)[0].toString() + "</td><td>" + env.get(i)[5].toString() + "</td>"
					+ "<td class=" + env.get(i)[6].toString() + ">" + env.get(i)[6].toString() + "</td>"
					+ "<td><img class='zoom' src='" + env.get(i)[7] + "'/></td></tr>";
		}

		return htmlTableCode;
	}

	/**
	 * Function to check the Specific broken Link
	 * 
	 * @param Url
	 */
	public static void brokenLinkValidator(String Url, WebDriver driver, String pathOfScreenshot, String proxyUrl,
			int proxyPort) {
		saveBrokenLinkValidationForReport(
				urlLinkStatus(validationOfLinks(Url, proxyUrl, proxyPort), driver, pathOfScreenshot));
	}

	public static void brokenLinkValidator(String Url, String instance, String testcase, WebDriver driver,
			String pathOfScreenshot, String proxyUrl, int proxyPort) {
		saveBrokenLinkValidationForReport(urlLinkStatus(validationOfLinks(Url, instance, testcase, proxyUrl, proxyPort),
				driver, pathOfScreenshot));
	}

	// Code for generating BL Comparison report
	private static String[] validationOfLinks(String urlToValidate, String proxyUrl, int proxyPort) {
		String[] responseArray = new String[8];
		try {
			Proxy proxy;
			URL url = new URL(urlToValidate);

			if (proxyUrl != null && proxyUrl != "") {
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort));
				httpURLConnect = (HttpURLConnection) url.openConnection(proxy);
			} else {
				httpURLConnect = (HttpURLConnection) url.openConnection();
			}

			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();
			responseStatus = httpURLConnect.getResponseCode();
			responseCode = responseStatus / 100;
		} catch (Exception e) {
		}
		responseArray[0] = urlToValidate;
		responseArray[1] = String.valueOf(responseCode);
		responseArray[2] = String.valueOf(responseStatus);
		responseArray[3] = null;
		responseArray[4] = null;

		return responseArray;
	}

	private static String[] validationOfLinks(String urlToValidate, String instance, String testcase, String proxyUrl,
			int proxyPort) {
		String[] responseArray = new String[8];
		try {
			Proxy proxy;
			URL url = new URL(urlToValidate);
			System.out.println("proxy ---> " + proxyUrl);
			if (proxyUrl != null && proxyUrl.length() != 0) {
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort));
				httpURLConnect = (HttpURLConnection) url.openConnection(proxy);
			} else {
				httpURLConnect = (HttpURLConnection) url.openConnection();
			}
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();
			responseStatus = httpURLConnect.getResponseCode();
			responseCode = responseStatus / 100;
		} catch (Exception e) {
		}
		responseArray[0] = urlToValidate;
		responseArray[1] = String.valueOf(responseCode);
		responseArray[2] = String.valueOf(responseStatus);
		responseArray[3] = instance;
		responseArray[4] = testcase;
		return responseArray;
	}

	// Code for generating BL Comparison report
	private static String[] urlLinkStatus(String[] responseArray, WebDriver driver, String pathOfScreenshot) {
		try {
			String linkValue = responseArray[0];
			String[] linksplits = linkValue.split("//");
			String[] linksplistArray = linksplits[1].split("/");
			String screenShot = pathOfScreenshot + linksplistArray[0] + ".jpg";
			takeSnapShot(driver, screenShot);
			String responseValue = responseArray[1];
			responseCode = Integer.valueOf(responseValue);
			String responseStatus = responseArray[2];
			switch (responseCode) {
			case 2:
				responseArray[5] = "Response code : " + responseStatus + " - OK";
				responseArray[6] = "PASS";
				responseArray[7] = screenShot;
				break;
			case 3:
				responseArray[5] = "Unknown Responce Code";
				responseArray[6] = "FAIL";
				responseArray[7] = screenShot;
				break;
			case 4:
				responseArray[5] = "Response code : " + responseStatus + " - Client error";
				responseArray[6] = "FAIL";
				responseArray[7] = screenShot;
				break;

			case 5:
				responseArray[5] = "\"Response code : " + responseStatus + " - Internal Server Error\"";
				responseArray[6] = "FAIL";
				responseArray[7] = screenShot;
				break;
			default:

				responseArray[5] = "Unknown Responce Code";
				responseArray[6] = "FAIL";
				responseArray[7] = screenShot;
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpURLConnect.disconnect();

		}
		return responseArray;
	}

	public static void WriteToFile(Document doc, String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		FileUtils.writeStringToFile(file, doc.outerHtml(), "UTF-8");

	}

	public static String brokenLinkSummary() {
		try {
			System.out.println(env1.size() + env2.size());

			for (int i = 0; i < env1.size(); i++) {
				if (env1.get(i)[6].toString().equalsIgnoreCase("PASS")) {
					PassCount1++;
				} else {
					FailCount1++;
				}
			}
			for (int i = 0; i < env2.size(); i++) {
				if (env2.get(i)[6].toString().equalsIgnoreCase("PASS")) {
					PassCount2++;
				} else {
					FailCount2++;
				}
			}

			list.add(PassCount1);
			list.add(FailCount1);
			list.add(PassCount2);
			list.add(FailCount2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "<div class='row' style='margin-top:30px;'>" + "<div class='col-sm-6' id='BLloadContent1'>"
				+ "<table id='BLSummary1'><thead>" + "<tr class='heading'>"
				+ "<th colspan='2' class='theadBL'>Environment1</th>" + "</tr>" + "<tr class='heading'>"
				+ "<th class='theadBL'>BrokenLinks</th>" + "<th class='theadBL'>UnbrokenLinks</th>" + "</tr>"
				+ "</thead>" + "<tbody>" + "<tr class='content'>" + "<td>" + FailCount1 + "</td>" + "<td>" + PassCount1
				+ "</td>" + "</tr>" + "</tbody>" + "</table>" + "</div>" + "<div class='col-sm-6' id='BLloadContent2'>"
				+ "<table id='BLSummary2'><thead>" + "<tr class='heading'>"
				+ "<th colspan='2' class='theadBL' >Environment2</th>" + "</tr>" + "<tr class='heading'>"
				+ "<th class='theadBL'>BrokenLinks</th>" + "<th class='theadBL'>UnbrokenLinks</th>" + "</tr>"
				+ "</thead>" + "<tbody>" + "<tr class='content'>" + "<td>" + FailCount2 + "</td>" + "<td>" + PassCount2
				+ "</td>" + "</tr>" + "</tbody>" + "</table>" + "</div>" + "</div>";

	}

	public static void takeSnapShot(WebDriver driver, String fileWithPath) throws Exception {
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		File DestFile = new File(fileWithPath);
		// Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);

	}

}
