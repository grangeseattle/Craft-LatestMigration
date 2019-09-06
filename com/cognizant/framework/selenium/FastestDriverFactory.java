package com.cognizant.framework.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Settings;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class FastestDriverFactory {

	private static Properties mobileProperties;

	private FastestDriverFactory() {
		// To prevent external instantiation of this class
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver}
	 * object
	 * 
	 * @param executionPlatform
	 *            executionPlatform{@link MobileExecutionPlatform}
	 * @param deviceName
	 *            The deviceName
	 * @param host
	 *            Host URL of Mint Platform
	 * @param mobileVersion
	 *            The Mobile Device OS Version
	 * 
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static AppiumDriver getMintAppiumDriver(MobileExecutionPlatform executionPlatform, String deviceName,
			String host, String mobileVersion) {

		AppiumDriver driver = null;
		mobileProperties = Settings.getMobilePropertiesInstance();
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("username", mobileProperties.getProperty("MintUsername"));
		desiredCapabilities.setCapability("password", mobileProperties.getProperty("MintPassword"));
		desiredCapabilities.setCapability("serviceReqId", mobileProperties.getProperty("MintServiceRequestId"));
		try {
			switch (executionPlatform) {

			case ANDROID:

				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("deviceName", deviceName);
				// desiredCapabilities.setCapability("deviceID",deviceName);
				desiredCapabilities.setCapability("platformVersion", mobileVersion);
				desiredCapabilities.setCapability("app", mobileProperties.getProperty("MintAndroidApplicationName"));

				try {
					driver = new AndroidDriver(new URL(host), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver invocation has problem, please re-check the capabilities and check the Mint details URL,username, and password");
				}
				break;

			case IOS:

				desiredCapabilities.setCapability("platformName", "iOS");
				desiredCapabilities.setCapability("deviceName", deviceName);
				// desiredCapabilities.setCapability("deviceID",deviceName);
				desiredCapabilities.setCapability("platformVersion", mobileVersion);
				desiredCapabilities.setCapability("app", mobileProperties.getProperty("MintiOSApplicationName"));
				try {
					driver = new IOSDriver(new URL(host), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invocation has problem, please re-check the capabilities and check the Mint details URL,username, and password");
				}
				break;

			case WEB_ANDROID:

				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("deviceName", deviceName);
				// desiredCapabilities.setCapability("deviceID",deviceName);
				desiredCapabilities.setCapability("platformVersion", mobileVersion);
				desiredCapabilities.setCapability("browserName", "Chrome");
				try {
					driver = new AndroidDriver(new URL(host), desiredCapabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver invocation has problem, please check the capabilities and check the Mint details URL,username, and password");
				}
				break;

			case WEB_IOS:

				desiredCapabilities.setCapability("platformName", "iOS");
				desiredCapabilities.setCapability("deviceName", deviceName);
				// desiredCapabilities.setCapability("deviceID",deviceName);
				desiredCapabilities.setCapability("platformVersion", mobileVersion);

				desiredCapabilities.setCapability("browserName", "Safari");
				try {
					driver = new IOSDriver(new URL(host), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invocation has problem, please check the capabilities and check the Mint details URL,username, and password");
				}
				break;

			default:
				throw new FrameworkException("Unhandled ExecutionMode!");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FrameworkException(
					"The Mint appium driver invocation created a problem , please check the capabilities");
		}

		return driver;

	}

	/**
	 * Function to return the {@link RemoteWebDriver} object based on the
	 * parameters passed
	 * 
	 * @param browser
	 *            The {@link Browser} to be used for the test execution
	 * @param browserVersion
	 *            The browser version to be used for the test execution
	 * @param platform
	 *            The {@link Platform} to be used for the test execution
	 * @param remoteUrl
	 *            The URL of the remote machine to be used for the test
	 *            execution
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getRemoteWebDriver(Browser browser, String browserVersion, Platform platform,
			String remoteUrl, String testcaseName) {

		mobileProperties = Settings.getMobilePropertiesInstance();

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("username", mobileProperties.getProperty("FastestUserame"));
		desiredCapabilities.setCapability("password", mobileProperties.getProperty("FastestPassword"));
		desiredCapabilities.setCapability("servicerequestid", mobileProperties.getProperty("FastestServiceRequestId"));
		desiredCapabilities.setCapability("packagename", testcaseName);
		desiredCapabilities.setBrowserName(browser.getValue());

		if (browserVersion != null) {
			desiredCapabilities.setVersion(browserVersion);
		}
		if (platform != null) {
			desiredCapabilities.setPlatform(platform);
		}

		desiredCapabilities.setJavascriptEnabled(true); // Pre-requisite for
														// remote execution

		URL url = getUrl(remoteUrl);

		return new RemoteWebDriver(url, desiredCapabilities);
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

}