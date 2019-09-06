package com.cognizant.framework.selenium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Settings;

public class PerfectoDriverFactory {

	private static Properties mobileProperties;

	private PerfectoDriverFactory() {
		// To prevent external instantiation of this class
	}

	private static URL getUrl(String remoteUrl) {
		URL url;
		try {
			url = new URL(remoteUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified remote URL is malformed");
		}
		return url;
	}

	/**
	 * Function to return the Perfecto MobileCloud {@link RemoteWebDriver}
	 * object based on the parameters passed
	 * 
	 * @param deviceId
	 *            The ID of the Perfecto MobileCloud device to be used for the
	 *            test execution
	 * @param browser
	 *            The {@link Browser} to be used for the test execution
	 * @param remoteUrl
	 *            The Perfecto MobileCloud URL to be used for the test execution
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getPerfectoRemoteWebDriver(String deviceId, Browser browser, String remoteUrl) {
		DesiredCapabilities desiredCapabilities = getPerfectoExecutionCapabilities(browser);
		desiredCapabilities.setCapability("deviceName", deviceId);

		URL url = getUrl(remoteUrl);

		return new RemoteWebDriver(url, desiredCapabilities);
	}

	private static DesiredCapabilities getPerfectoExecutionCapabilities(Browser browser) {
		validatePerfectoSupports(browser);

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setBrowserName(browser.getValue());
		desiredCapabilities.setPlatform(Platform.ANY);
		desiredCapabilities.setJavascriptEnabled(true); // Pre-requisite for
														// remote execution

		mobileProperties = Settings.getMobilePropertiesInstance();
		desiredCapabilities.setCapability("user", mobileProperties.getProperty("PerfectoUser"));
		desiredCapabilities.setCapability("password", mobileProperties.getProperty("PerfectoPassword"));

		return desiredCapabilities;
	}

	private static void validatePerfectoSupports(Browser browser) {
		switch (browser) {
		case INTERNET_EXPLORER:
		case FIREFOX:
			throw new FrameworkException(
					"The browser " + browser.toString() + " is not supported on the Perfecto MobileCloud");

		default:
			break;
		}
	}

	/**
	 * Function to return the Perfecto MobileCloud {@link RemoteWebDriver}
	 * object based on the parameters passed
	 * 
	 * @param deviceId
	 *            The device Name
	 * @param osVersion
	 *            The device platform version to be used for the test execution
	 * @param browser
	 *            The {@link Browser} to be used for the test execution
	 * @param remoteUrl
	 *            The Perfecto MobileCloud URL to be used for the test execution
	 * @param executionPlatform
	 *            The MobileExecutionPlatform {@link MobileExecutionPlatform}
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getPerfectoRemoteWebDriverByDevicePlatform(String deviceId, String osVersion,
			Browser browser, String remoteUrl, MobileExecutionPlatform executionPlatform) {
		String platformName = "";
		if (executionPlatform.equals("WEB_ANDROID")) {
			platformName = "Android";
		} else if (executionPlatform.equals("WEB_IOS")) {
			platformName = "ios";
		}
		DesiredCapabilities desiredCapabilities = getPerfectoExecutionCapabilities(browser);
		desiredCapabilities.setBrowserName(browser.getValue());
		desiredCapabilities.setCapability("platformName", platformName);
		desiredCapabilities.setCapability("platformVersion", osVersion);

		URL url = getUrl(remoteUrl);

		return new RemoteWebDriver(url, desiredCapabilities);
	}

	/**
	 * Function to return the Perfecto MobileCloud {@link RemoteWebDriver}
	 * object based on the parameters passed
	 * 
	 * @param manufacturer
	 *            The manufacturer of the device to be used for the test
	 *            execution (Samsung, Apple, etc.)
	 * @param model
	 *            The device model to be used for the test execution (Galaxy S6,
	 *            iPad Air, etc.)
	 * @param browser
	 *            The {@link Browser} to be used for the test execution
	 * @param remoteUrl
	 *            The Perfecto MobileCloud URL to be used for the test execution
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getPerfectoRemoteWebDriverByDeviceModel(String manufacturer, String model, Browser browser,
			String remoteUrl) {
		DesiredCapabilities desiredCapabilities = getPerfectoExecutionCapabilities(browser);
		desiredCapabilities.setCapability("manufacturer", manufacturer);
		desiredCapabilities.setCapability("model", model);

		URL url = getUrl(remoteUrl);

		return new RemoteWebDriver(url, desiredCapabilities);
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver}
	 * object
	 * 
	 * @param executionPlatform
	 *            executionPlatform{@link MobileExecutionPlatform}
	 * @param deviceName
	 *            The deviceName
	 * @param perfectoURL
	 *            Host URL of Perfecto
	 * 
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static AppiumDriver getPerfectoAppiumDriver(MobileExecutionPlatform executionPlatform, String deviceName,
			String perfectoURL) {

		AppiumDriver driver = null;
		mobileProperties = Settings.getMobilePropertiesInstance();

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("user", mobileProperties.getProperty("PerfectoUser"));
		desiredCapabilities.setCapability("password", mobileProperties.getProperty("PerfectoPassword"));
		try {
			switch (executionPlatform) {

			case ANDROID:

				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("appPackage",
						mobileProperties.getProperty("Application_Package_Name"));
				desiredCapabilities.setCapability("appActivity",
						mobileProperties.getProperty("Application_MainActivity_Name"));
				// desiredCapabilities.setCapability("app",
				// "PUBLIC:appium/apiDemos.apk");
				try {
					driver = new AndroidDriver(new URL(perfectoURL), desiredCapabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver invokation has problem, please re-check the capabilities and check the perfecto details URL, Username and Password ");
				}

				break;

			case IOS:

				desiredCapabilities.setCapability("platformName", "ios");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("newCommandTimeout", 120);
				desiredCapabilities.setCapability("bundleId", mobileProperties.getProperty("PerfecttoIosBundleID"));
				// desiredCapabilities.setCapability("app",
				// "PUBLIC:appium/apiDemos.ipa");

				try {
					driver = new IOSDriver(new URL(perfectoURL), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invokation has problem, please re-check the capabilities and check the perfecto details URL, Username and Password ");
				}
				break;

			case WEB_ANDROID:

				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("browserName", "Chrome");

				try {
					driver = new AndroidDriver(new URL(perfectoURL), desiredCapabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver/browser invokation has problem, please re-check the capabilities and check the perfecto details URL, Username and Password ");
				}
				break;

			case WEB_IOS:

				desiredCapabilities.setCapability("platformName", "ios");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("automationName", "Appium");
				desiredCapabilities.setCapability("browserName", "Safari");

				try {
					driver = new IOSDriver(new URL(perfectoURL), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invokation/browser has problem, please re-check the capabilities and check the perfecto details URL, Username and Password ");
				}
				break;

			default:
				throw new FrameworkException("Unhandled ExecutionMode!");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FrameworkException(
					"The perfecto appium driver invocation created a problem , please check the capabilities");
		}
		return driver;

	}

	/**
	 * Function to return the object for RemoteWebDriver {@link RemoteWebDriver}
	 * object
	 * 
	 * @param testParameters
	 * 
	 * @return Instance of the {@link RemoteWebDriver} object
	 */
	public static WebDriver getPerfectoRemoteWebDriverForDesktop(SeleniumTestParameters testParameters) {

		RemoteWebDriver driver = null;
		String browserName = getBrowerName(testParameters);
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("user", mobileProperties.getProperty("PerfectoUser"));
		desiredCapabilities.setCapability("password", mobileProperties.getProperty("PerfectoPassword"));
		desiredCapabilities.setCapability("platformName", "Windows");
		desiredCapabilities.setCapability("platformVersion", testParameters.getPlatformVersion());
		desiredCapabilities.setCapability("browserName", browserName);
		desiredCapabilities.setCapability("browserVersion", testParameters.getBrowserVersion());
		desiredCapabilities.setCapability("resolution", mobileProperties.getProperty("Resolution"));
		desiredCapabilities.setCapability("location", mobileProperties.getProperty("Location"));
		desiredCapabilities.setCapability("takesScreenshot", true);
		try {
			driver = new RemoteWebDriver(new URL(mobileProperties.getProperty("PerfectoHost")), desiredCapabilities);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}

	private static String getBrowerName(SeleniumTestParameters testParameters) {
		String browserName = null;
		if (testParameters.getBrowser().equals(Browser.CHROME)) {
			browserName = "Chrome";
		} else if (testParameters.getBrowser().equals(Browser.FIREFOX)) {
			browserName = "Firefox";
		} else if (testParameters.getBrowser().equals(Browser.INTERNET_EXPLORER)) {
			browserName = "Internet Explorer";
		} else if (testParameters.getBrowser().equals(Browser.EDGE)) {
			browserName = "Edge";
		}
		return browserName;
	}
}
