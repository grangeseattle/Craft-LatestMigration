package com.cognizant.framework.nft;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.HTMLVisualValidation;
import com.cognizant.framework.NFT;
import com.cognizant.framework.Settings;

public class AccessibilityNFT {
	private static URL scriptUrl = AccessibilityNFT.class.getResource("axe.min.js");

	static ArrayList<String> filelist = new ArrayList<String>();
	static ArrayList<String> pagename = new ArrayList<String>();
	static ArrayList<String> envList = new ArrayList<String>();
	static ArrayList<String> scenarioTCList = new ArrayList<String>();

	private static Parsing parsing = new Parsing();

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\368731\\Downloads\\chromedriver_win32_2\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com");
		ScriptHelper scriptHelper=null;
		AccessibilityNFT.evaluatePageAccessibilityTest(driver, "https://www.google.com", scriptHelper);
		AccessibilityNFT.createAccessibilityJsonFile();
	}

	/**
	 * Descriptiop:This method tests the page for accessibility of elements over
	 * page
	 * 
	 * @param driver
	 * @param url
	 * @param scriptHelper TODO
	 */
	public static synchronized void evaluatePageAccessibilityTest(WebDriver driver, String url, ScriptHelper scriptHelper) {

		if (NFT.executeAccessibility) {


			String environment = scriptHelper.getReport().getSeleniumTestParameters().getEnvironment();
			String scenarioTestcase = scriptHelper.getReport().getSeleniumTestParameters().getCurrentScenario() + "_"
					+ scriptHelper.getReport().getSeleniumTestParameters().getCurrentTestcase();
			Properties properties = Settings.getInstance();

			org.json.JSONArray jsonresults;
			try {
				String currentUrl = driver.getCurrentUrl();
				
				jsonresults = RunAxe.run_axe(driver, scriptUrl);
				System.out.println("jsonresult: " + jsonresults);

				if (currentUrl.contains("data"))

				{
					driver.get(url);

				}
				String result1 = RunAxe.Sub_String_url(driver.getCurrentUrl(), 1);

				String fileresult = RunAxe.dynamic_filecreation(result1, jsonresults);

				System.out.println(jsonresults);
				filelist.add(fileresult);
				pagename.add(url);
				envList.add(environment);
				scenarioTCList.add(scenarioTestcase);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void createAccessibilityJsonFile() {

		if (NFT.executeAccessibility) {
			System.out.println(pagename + "\n" + filelist);
			try {
				parsing.parsingfile(filelist, pagename, envList, scenarioTCList);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
