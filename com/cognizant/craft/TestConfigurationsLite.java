package com.cognizant.craft;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;

import com.cognizant.framework.Status;
import com.cognizant.framework.selenium.Browser;
import com.cognizant.framework.selenium.ExecutionMode;
import com.cognizant.framework.selenium.MobileExecutionPlatform;
import com.cognizant.framework.selenium.SeleniumParametersBuilders;
import com.cognizant.framework.selenium.ToolName;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class TestConfigurationsLite extends CRAFTLiteTestCase {

	int responseStatus;
	int responseCode;
	private HttpURLConnection httpURLConnect;

	protected Map<String, Object> perfectoCommand = new HashMap<>();
	Dimension winSize;

	@DataProvider(name = "DesktopBrowsers")
	public Object[][] desktopBrowsers(Method currentMethod) {

		String[] currentPackageSplit = currentMethod.getDeclaringClass().getPackage().toString().split("testscripts.");
		currentScenario = currentPackageSplit[1];
		currentTestcase = currentMethod.getDeclaringClass().getSimpleName();

		return new Object[][] { { new SeleniumParametersBuilders(currentScenario, currentTestcase)
				.extentReport(extentReport).extentTest(extentTest).testInstance("Instance1")
				.executionMode(ExecutionMode.LOCAL).browser(Browser.CHROME).build() } };
	}

	@DataProvider(name = "MobileDevice")
	public Object[][] mobileDevice(Method currentMethod) {
		String[] currentPackageSplit = currentMethod.getDeclaringClass().getPackage().toString().split("testscripts.");
		currentScenario = currentPackageSplit[1];
		currentTestcase = currentMethod.getDeclaringClass().getSimpleName();

		return new Object[][] { { new SeleniumParametersBuilders(currentScenario, currentTestcase)
				.testInstance("Instance1").extentReport(extentReport).extentTest(extentTest)
				.executionMode(ExecutionMode.MOBILE).mobileExecutionPlatform(MobileExecutionPlatform.ANDROID)
				.toolName(ToolName.APPIUM).deviceName("4d005cb2c4938197").build() } };
	}

	@DataProvider(name = "API")
	public Object[][] api(Method currentMethod) {
		String[] currentPackageSplit = currentMethod.getDeclaringClass().getPackage().toString().split("testscripts.");
		currentScenario = currentPackageSplit[1];
		currentTestcase = currentMethod.getDeclaringClass().getSimpleName();

		return new Object[][] {
				{ new SeleniumParametersBuilders(currentScenario, currentTestcase).testInstance("Instance1")
						.extentReport(extentReport).extentTest(extentTest).executionMode(ExecutionMode.API).build() } };
	}

	/**
	 * All reusuable Appium Functions with Perfecto
	 */

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param context
	 *            - Context of App like NATIVE_APP or WEB
	 * @param appName
	 *            - Name of the App as displayed in Mobile
	 */
	protected void openApp(final String context, final String appName) {
		if (context.equals("NATIVE_APP")) {
			final Map<String, Object> perfectoCommand = new HashMap<>();
			perfectoCommand.put("name", appName);
			driver.getAppiumDriver().executeScript("mobile:application:open", perfectoCommand);
		}
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param context
	 *            - Context of App like NATIVE_APP or WEB
	 * @param appName
	 *            - Identifier of the App.
	 */
	protected void openAppWithIdentifier(final String context, final String identifer) {
		if (context.equals("NATIVE_APP")) {
			perfectoCommand.put("identifier", identifer);
			driver.getAppiumDriver().executeScript("mobile:application:open", perfectoCommand);
			perfectoCommand.clear();
		}
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param type
	 *            - Type of report like pdf
	 */
	protected byte[] downloadReport(final String type) throws IOException {
		final String command = "mobile:report:download";
		final Map<String, String> params = new HashMap<>();
		params.put("type", type);
		final String report = (String) (driver.getRemoteWebDriver()).executeScript(command, params);
		final byte[] reportBytes = OutputType.BYTES.convertFromBase64Png(report);
		return reportBytes;
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 */
	protected byte[] downloadWTReport() {
		final String reportUrl = (String) driver.getAppiumDriver().getCapabilities()
				.getCapability("windTunnelReportUrl");
		String returnString = "<html><head><META http-equiv=\"refresh\" content=\"0;URL=";
		returnString = returnString + reportUrl + "\"></head><body /></html>";

		return returnString.getBytes();
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param context
	 *            - Context of App like NATIVE_APP or WEB
	 * @param appName
	 *            - Name of the App.
	 */
	protected void closeApp(final String context, final String appName) {
		if (context.equals("NATIVE_APP")) {
			perfectoCommand.put("name", appName);
			try {
				driver.getAppiumDriver().executeScript("mobile:application:close", perfectoCommand);
			} catch (final WebDriverException e) {
			}
		}
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param context
	 *            - Context of App like NATIVE_APP or WEB
	 * @param appName
	 *            - Identifier of the App.
	 */
	protected void closeAppWithIdentifier(final String context, final String bundleId) {
		if (context.equals("NATIVE_APP")) {
			perfectoCommand.put("identifier", bundleId);
			try {
				driver.getAppiumDriver().executeScript("mobile:application:close", perfectoCommand);
			} catch (final WebDriverException e) {
			}
		}
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param textToFind
	 *            - text that has to be searched
	 * @param timeout
	 */
	protected Boolean textCheckpoint(final String textToFind, final Integer timeout) {
		perfectoCommand.put("content", textToFind);
		perfectoCommand.put("timeout", timeout);
		final Object result = driver.getAppiumDriver().executeScript("mobile:checkpoint:text", perfectoCommand);
		final Boolean resultBool = Boolean.valueOf(result.toString());
		perfectoCommand.clear();
		return resultBool;
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param textToFind
	 *            - text that has to be searched
	 * @param timeout
	 */
	protected void textClick(final String textToFind, final Integer timeout) {
		perfectoCommand.put("content", textToFind);
		perfectoCommand.put("timeout", timeout);
		driver.getAppiumDriver().executeScript("mobile:text:select", perfectoCommand);
		perfectoCommand.clear();

	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param label
	 *            - text that has to be searched
	 * @param threshold
	 */
	protected void visualScrollToClick(final String label, final Integer threshold) {
		perfectoCommand.put("label", label);
		perfectoCommand.put("threshold", threshold);
		perfectoCommand.put("scrolling", "scroll");
		driver.getAppiumDriver().executeScript("mobile:button-text:click", perfectoCommand);
		perfectoCommand.clear();
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param label
	 *            - text that has to be searched
	 * @param timeout
	 * @param threshold
	 */
	protected void visualClick(final String label, final Integer timeout, final Integer threshold) {
		perfectoCommand.put("label", label);
		perfectoCommand.put("threshold", threshold);
		perfectoCommand.put("timeout", timeout);
		driver.getAppiumDriver().executeScript("mobile:button-text:click", perfectoCommand);
		perfectoCommand.clear();
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param label
	 *            - text that has to be searched
	 * @param timeout
	 * @param threshold
	 * @param labelDirection
	 * @param labelOffset
	 */
	protected void visualClick(final String label, final Integer timeout, final Integer threshold,
			final String labelDirection, final String labelOffset) {
		perfectoCommand.put("label", label);
		perfectoCommand.put("threshold", threshold);
		perfectoCommand.put("timeout", timeout);
		perfectoCommand.put("label.direction", labelDirection);
		perfectoCommand.put("label.offset", labelOffset);
		driver.getAppiumDriver().executeScript("mobile:button-text:click", perfectoCommand);
		perfectoCommand.clear();
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param imagePath
	 */
	protected void imageClick(String imagePath) {
		perfectoCommand.put("content", imagePath);
		perfectoCommand.put("timeout", "5");
		perfectoCommand.put("screen.top", "0%");
		perfectoCommand.put("screen.height", "100%");
		perfectoCommand.put("screen.left", "0%");
		perfectoCommand.put("screen.width", "100%");
		driver.executeScript("mobile:image:select", perfectoCommand);
		perfectoCommand.clear();
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param imagePath
	 */
	protected Boolean imageCheckpoint(String imagePath) {
		perfectoCommand.put("content", imagePath);
		perfectoCommand.put("threshold", "90");
		perfectoCommand.put("screen.top", "0%");
		perfectoCommand.put("screen.height", "100%");
		perfectoCommand.put("screen.left", "0%");
		perfectoCommand.put("screen.width", "100%");
		Object result = driver.executeScript("mobile:image:find", perfectoCommand);
		final Boolean resultBool = Boolean.valueOf(result.toString());
		perfectoCommand.clear();
		return resultBool;
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param repositoryFile
	 * @param handsetFile
	 */
	protected void putFileOnDevice(final String repositoryFile, final String handsetFile) {
		perfectoCommand.put("repositoryFile", repositoryFile);
		perfectoCommand.put("handsetFile", handsetFile);
		driver.getAppiumDriver().executeScript("mobile:media:put", perfectoCommand);
		perfectoCommand.clear();

	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param handsetFile
	 * @param repositoryFile
	 */
	protected void getFileOnDevice(final String handsetFile, final String repositoryFile) {
		perfectoCommand.put("repositoryFile", repositoryFile);
		perfectoCommand.put("handsetFile", handsetFile);
		driver.getAppiumDriver().executeScript("mobile:media:get", perfectoCommand);
		perfectoCommand.clear();

	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param handsetFile
	 */
	protected void deleteFromDevice(final String handsetFile) {
		perfectoCommand.put("handsetFile", handsetFile);
		driver.getAppiumDriver().executeScript("mobile:media:delete", perfectoCommand);
		perfectoCommand.clear();

	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param repositoryFile
	 */
	protected void deleteFromRepository(final String repositoryFile) {
		perfectoCommand.put("repositoryFile", repositoryFile);
		driver.getAppiumDriver().executeScript("mobile:media:delete", perfectoCommand);
		perfectoCommand.clear();

	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param keyPress
	 */
	protected void deviceKeyPress(final String keyPress) {

		perfectoCommand.put("keySequence", keyPress);
		driver.getAppiumDriver().executeScript("mobile:presskey", perfectoCommand);
		perfectoCommand.clear();
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	protected void swipe(final String x1, final String y1, final String x2, final String y2) {
		final List<String> swipeCoordinates = new ArrayList<>();
		swipeCoordinates.add(x1 + ',' + y1);
		swipeCoordinates.add(x2 + ',' + y2);
		perfectoCommand.put("location", swipeCoordinates);
		driver.getAppiumDriver().executeScript("mobile:touch:drag", perfectoCommand);
		perfectoCommand.clear();
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param textToFind
	 */
	protected void swipeTillText(String textToFind) {
		perfectoCommand.put("content", textToFind);
		perfectoCommand.put("scrolling", "scroll");
		perfectoCommand.put("maxscroll", "10");
		perfectoCommand.put("next", "SWIPE_UP");
		driver.executeScript("mobile:text:select", perfectoCommand);
		perfectoCommand.clear();
	}

	/**
	 * Function Applicable to Pause the Script, Generic Application
	 * 
	 * @param How_Long_To_Pause
	 *            How Long to Pause
	 */
	public void PauseScript(int How_Long_To_Pause) {
		// convert to seconds
		How_Long_To_Pause = How_Long_To_Pause * 1000;

		try {
			Thread.sleep(How_Long_To_Pause);
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * All reusuable Selenium Functions with Perfecto
	 */

	/**
	 * Function to switch the Context
	 * 
	 * @param driver
	 * @RemoteWebDriver
	 * @param context
	 */
	protected static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param driver
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	protected void scrollChecker(IOSDriver driver, String[] list) {
		for (int i = 0; i < list.length; i++) {

			MobileElement me = (MobileElement) driver.findElement(By.xpath("//UIAPickerWheel[" + (i + 1) + "]"));
			int mget = getMonthInt(me.getText().split(",")[0]);

			if (i == 0) {
				if (mget > getMonthInt(list[i])) {
					scrollAndSearch(driver, list[i], me, true);
				} else {
					scrollAndSearch(driver, list[i], me, false);
				}
			} else {
				if (Integer.parseInt(me.getText().split(",")[0]) > Integer.parseInt(list[i])) {
					scrollAndSearch(driver, list[i], me, true);
				} else {
					scrollAndSearch(driver, list[i], me, false);
				}
			}
		}
	}

	// Used to get the integer for a month based on the string of the month
	private int getMonthInt(String month) {
		int monthInt = 0;
		switch (month) {
		case "Jan":
			monthInt = 1;
			break;
		case "January":
			monthInt = 1;
			break;
		case "February":
			monthInt = 2;
			break;
		case "Feb":
			monthInt = 2;
			break;
		case "March":
			monthInt = 3;
			break;
		case "Mar":
			monthInt = 3;
			break;
		case "April":
			monthInt = 4;
			break;
		case "Apr":
			monthInt = 4;
			break;
		case "May":
			monthInt = 5;
			break;
		case "June":
			monthInt = 6;
			break;
		case "Jun":
			monthInt = 6;
			break;
		case "July":
			monthInt = 7;
			break;
		case "Jul":
			monthInt = 7;
			break;
		case "August":
			monthInt = 8;
			break;
		case "Aug":
			monthInt = 8;
			break;
		case "September":
			monthInt = 9;
			break;
		case "Sep":
			monthInt = 9;
			break;
		case "October":
			monthInt = 10;
			break;
		case "Oct":
			monthInt = 10;
			break;
		case "November":
			monthInt = 11;
			break;
		case "Nov":
			monthInt = 11;
			break;
		case "December":
			monthInt = 12;
			break;
		case "Dec":
			monthInt = 12;
			break;
		}
		return monthInt;
	}

	// Code here shouldn't be modified
	@SuppressWarnings("rawtypes")
	private void scrollAndSearch(IOSDriver driver, String value, MobileElement me, Boolean direction) {
		String x = getLocationX(me);
		String y = getLocationY(me);
		while (!driver.findElementByXPath(getXpathFromElement(me)).getText().contains(value)) {
			swipe(driver, x, y, direction);
		}
	}

	// Performs the swipe and search operation
	// Code here shouldn't be modified
	@SuppressWarnings("rawtypes")
	private void swipe(IOSDriver driver, String start, String end, Boolean up) {
		String direction;
		if (up) {
			direction = start + "," + (Integer.parseInt(end) + 70);
		} else {
			direction = start + "," + (Integer.parseInt(end) - 70);
		}

		Map<String, Object> params1 = new HashMap<>();
		params1.put("location", start + "," + end);
		params1.put("operation", "down");
		driver.executeScript("mobile:touch:tap", params1);

		Map<String, Object> params2 = new HashMap<>();
		List<String> coordinates2 = new ArrayList<>();

		coordinates2.add(direction);
		params2.put("location", coordinates2);
		params2.put("auxiliary", "notap");
		params2.put("duration", "3");
		driver.executeScript("mobile:touch:drag", params2);

		Map<String, Object> params3 = new HashMap<>();
		params3.put("location", direction);
		params3.put("operation", "up");
		driver.executeScript("mobile:touch:tap", params3);
	}

	// Gets the objects X location in pixels
	private String getLocationX(MobileElement me) {
		int x = me.getLocation().x;
		int width = (Integer.parseInt(me.getAttribute("width")) / 2) + x;
		return width + "";
	}

	// Gets the objects X location in pixels
	private String getLocationY(MobileElement me) {
		int y = me.getLocation().y;
		int height = (Integer.parseInt(me.getAttribute("height")) / 2) + y;
		return height + "";
	}

	// Parses webelement to retrieve the xpath used for identification
	private String getXpathFromElement(MobileElement me) {
		return (me.toString().split("-> xpath: ")[1]).substring(0, (me.toString().split("-> xpath: ")[1]).length() - 1);
	}

	/**
	 * Function Applicable only when the ExecutionMode used is <b>PERFECTO
	 * 
	 * @param letter
	 */
	protected void drawLetter(final String letter) {
		final List<String> coordinates = new ArrayList<>();

		switch (letter) {
		case "A":

			break;
		case "B":

			break;
		case "C":

			break;
		case "D":

			break;
		case "E":
			coordinates.add("42%,40%");
			coordinates.add("42%,60%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("42%,40%");
			coordinates.add("52%,40%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("42%,48%");
			coordinates.add("52%,48%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("42%,56%");
			coordinates.add("52%,56%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			break;
		case "F":

			break;
		case "G":

			break;
		case "H":

			break;
		case "I":

			break;
		case "J":

			break;
		case "K":

			break;
		case "L":

			break;
		case "M":

			break;
		case "N":

			break;
		case "O":

			break;
		case "P":
			coordinates.add("30%,40%");
			coordinates.add("30%,60%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("30%,40%");
			coordinates.add("40%,40%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("38%,40%");
			coordinates.add("38%,52%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("38%,48%");
			coordinates.add("28%,48%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			break;
		case "Q":

			break;
		case "R":
			coordinates.add("54%,40%");
			coordinates.add("54%,60%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("54%,40%");
			coordinates.add("64%,40%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("62%,40%");
			coordinates.add("62%,52%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("62%,48%");
			coordinates.add("52%,48%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			coordinates.add("54%,48%");
			coordinates.add("64%,60%");
			perfectoCommand.put("location", coordinates);
			driver.executeScript("mobile:touch:drag", perfectoCommand);
			perfectoCommand.clear();
			coordinates.clear();
			break;
		case "S":

			break;
		case "T":

			break;
		case "U":

			break;
		case "V":

			break;
		case "W":

			break;
		case "X":

			break;
		case "Y":

			break;
		case "Z":

			break;
		}
	}

	/**
	 * Function to check the Specific broken Link
	 * 
	 * @param Url
	 */
	protected void brokenLinkValidator(String Url) {
		urlLinkStatus(validationOfLinks(Url));
	}

	private String[] validationOfLinks(String urlToValidate) {
		String[] responseArray = new String[3];
		try {
			URL url = new URL(urlToValidate);
			httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();
			responseStatus = httpURLConnect.getResponseCode();
			responseCode = responseStatus / 100;
		} catch (Exception e) {
		}
		responseArray[0] = urlToValidate;
		responseArray[1] = String.valueOf(responseCode);
		responseArray[2] = String.valueOf(responseStatus);
		return responseArray;
	}

	private void urlLinkStatus(String[] responseArray) {
		try {
			String linkValue = responseArray[0];
			String responseValue = responseArray[1];
			responseCode = Integer.valueOf(responseValue);
			String responseStatus = responseArray[2];
			switch (responseCode) {
			case 2:
				report.updateTestLog(linkValue, "Response code : " + responseStatus + " - OK", Status.PASS);
				break;
			case 3:
				report.updateTestLog(linkValue, "Unknown Responce Code", Status.FAIL);
				break;
			case 4:
				report.updateTestLog(linkValue, "Response code : " + responseStatus + " - Client error", Status.FAIL);
				break;

			case 5:

				report.updateTestLog(linkValue, "Response code : " + responseStatus + " - Internal Server Error",
						Status.FAIL);
				break;
			default:
				report.updateTestLog(linkValue, "Unknown Responce Code", Status.FAIL);

				break;
			}

		} catch (Exception e) {

		} finally {
			httpURLConnect.disconnect();

		}
	}

	/**
	 * Function to check the All Broken Links available in the Page
	 * 
	 */
	protected void validateAllLinksInPage() {

		String url;
		int responseCode;

		List<WebElement> links = driver.findElements(By.tagName("a"));

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			if (url == null || url.isEmpty()) {
				continue;
			}

			try {
				httpURLConnect = (HttpURLConnection) (new URL(url).openConnection());

				httpURLConnect.setRequestMethod("HEAD");

				httpURLConnect.connect();

				responseCode = httpURLConnect.getResponseCode();

				if (responseCode >= 400) {
					report.updateTestLog(url, "Response code : " + responseStatus + " - BROKEN", Status.WARNING);
				} else {
					report.updateTestLog(url, "Response code : " + responseStatus + " - OK", Status.DONE);
				}

			} catch (MalformedURLException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);

			} catch (IOException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);
			}
		}

	}

	/**
	 * Function to check the All Broken Image Links available in the Page
	 * 
	 */
	protected void validateAllImageLinksInPage() {

		String url;
		int responseCode;

		List<WebElement> links = driver.findElements(By.tagName("img"));

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			if (url == null || url.isEmpty()) {
				continue;
			}

			try {
				httpURLConnect = (HttpURLConnection) (new URL(url).openConnection());

				httpURLConnect.setRequestMethod("HEAD");

				httpURLConnect.connect();

				responseCode = httpURLConnect.getResponseCode();

				if (responseCode >= 400) {
					report.updateTestLog(url, "Response code : " + responseStatus + " - BROKEN", Status.WARNING);
				} else {
					report.updateTestLog(url, "Response code : " + responseStatus + " - OK", Status.DONE);
				}

			} catch (MalformedURLException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);

			} catch (IOException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);
			}
		}
	}

	@Override
	public void setUp() {

	}

	@Override
	public void executeTest() {

	}

	@Override
	public void tearDown() {

	}
}