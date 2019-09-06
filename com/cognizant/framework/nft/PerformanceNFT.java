package com.cognizant.framework.nft;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.NFT;
import com.cognizant.framework.Settings;

public class PerformanceNFT {

	// static File file = new
	// File(PerformanceNFT.class.getResource("resources/datafile.properties").getPath());
	static PerformanceMetrics collect = new PerformanceMetrics();
	static String siteValue = null;
	static JSONObject browserdata;
	static JSONArray output = new JSONArray();

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/media/qpaas/Disk222/chromedriver");
		WebDriver driver = null;
		driver = new ChromeDriver();
		driver.get("https://www.google.com");
		ScriptHelper scriptHelper = null;
		PerformanceNFT.evaluatePerformanceForPage(driver, "https://www.google.com", scriptHelper);
		PerformanceNFT.createPerformanceJsonFile();
	}

	/**
	 * Description:This method tests the performance of AUT page
	 * 
	 * @param driver
	 * @param testURL
	 * @param scriptHelper TODO
	 */
	public static synchronized void evaluatePerformanceForPage(WebDriver driver, String testURL, ScriptHelper scriptHelper) {
		if (NFT.executePerformance) {
			if (NFT.executePerformance) {
				Properties propertiesGlobal = Settings.getInstance();
				String environment = scriptHelper.getReport().getSeleniumTestParameters().getEnvironment();
				String scenarioTestcase = scriptHelper.getReport().getSeleniumTestParameters().getCurrentScenario()
						+ "_" + scriptHelper.getReport().getSeleniumTestParameters().getCurrentTestcase();

				try {
					// System.out.println(file.exists();
					// if it starts with / means it look into project base
					// directory but it doesnot start with / then it will look
					// into folder/package where this class present

					InputStream fileInput = PerformanceNFT.class.getResourceAsStream("datafile.properties");
					Properties properties = new Properties();
					properties.load(fileInput);

					String ENV = properties.getProperty("ENV");
					String PROJECT = properties.getProperty("PROJECT");
					boolean dynaTrace = false;
					boolean browserTimers = true;
					int ITERATIONS = 1;
					String browser = properties.getProperty("browser");
					JavascriptExecutor js = null;
					String build = properties.getProperty("BUILD");
					System.out.println("I am here");
					if (!testURL.contains("data")) {
						// Home Page - Transaction 1
						siteValue = collect.SiteNavigationwithCache(driver, browser, ENV, testURL, build, PROJECT);
						browserdata = collect.pageTimer(driver.getCurrentUrl(), siteValue, testURL, build, environment,
								scenarioTestcase);
						output.add(browserdata);
					}

				} catch (IOException | JSONException e) {
					e.printStackTrace();

				}
			}

		}
	}

	public static void createPerformanceJsonFile() {
		if(NFT.executePerformance)
		collect.CreateJson(output);
	}

}
