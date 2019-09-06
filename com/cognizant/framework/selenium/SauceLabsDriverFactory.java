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

public class SauceLabsDriverFactory {

	private static Properties mobileProperties;

	private SauceLabsDriverFactory() {
		// To prevent external instantiation of this class
	}

	/**
	 * Function to return the Saucelabs DesktopCloud {@link RemoteWebDriver}
	 * object based on the parameters passed
	 * 
	 * @param sauceUrl
	 *            The Saucelabs URL to be used for the test execution
	 * @param browser
	 *            Browser {@link Browser}
	 * @param browserVersion
	 *            The browser version to be used for the test execution
	 * @param platformName
	 *            The platform to be used for the test execution (Windows, Mac,
	 *            etc.)
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getSauceRemoteWebDriver(String sauceURL, Browser browser, String browserVersion,
			Platform platformName, SeleniumTestParameters testParameters) {
		WebDriver driver = null;
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platform", platformName);
		desiredCapabilities.setCapability("version", browserVersion);
		desiredCapabilities.setCapability("browserName", browser);
		// desiredCapabilities.setCapability("screen-resolution","800x600");
		desiredCapabilities.setCapability("name", testParameters.getCurrentTestcase());
		try {
			driver = new RemoteWebDriver(new URL(sauceURL), desiredCapabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FrameworkException(
					"The RemoteWebDriver driver invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
		}
		return driver;
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver}
	 * object
	 * 
	 * @param executionPlatform
	 *            executionPlatform{@link MobileExecutionPlatform}
	 * @param deviceName
	 *            The deviceName
	 * @param sauceURL
	 *            Sauce URL
	 * @param testParameters
	 *            Test Parameters {@link SeleniumTestParameters}
	 * 
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static AppiumDriver getSauceAppiumDriver(MobileExecutionPlatform executionPlatform, String deviceName,
			String sauceURL, SeleniumTestParameters testParameters) {

		AppiumDriver driver = null;

		mobileProperties = Settings.getMobilePropertiesInstance();

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		try {
			switch (executionPlatform) {

			case ANDROID:
				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("app", mobileProperties.getProperty("SauceAndroidIdentifier"));
				desiredCapabilities.setCapability("name", testParameters.getCurrentTestcase());
				try {
					driver = new AndroidDriver(new URL(sauceURL), desiredCapabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}

				break;

			case IOS:

				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
				desiredCapabilities.setCapability("platformName", "ios");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("browserName", "");
				desiredCapabilities.setCapability("name", testParameters.getCurrentTestcase());
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
				desiredCapabilities.setCapability("app", mobileProperties.getProperty("SauceIosBundleID"));

				try {
					driver = new IOSDriver(new URL(sauceURL), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}
				break;

			case WEB_ANDROID:
				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("deviceOrientation", "portrait");
				desiredCapabilities.setCapability("browserName", "chrome");
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("name", testParameters.getCurrentTestcase());

				try {
					driver = new AndroidDriver(new URL(sauceURL), desiredCapabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver/browser invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}
				break;

			case WEB_IOS:
				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
				desiredCapabilities.setCapability("platformName", "ios");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("name", testParameters.getCurrentTestcase());
				desiredCapabilities.setCapability("browserName", "Safari");
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());

				try {
					driver = new IOSDriver(new URL(sauceURL), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invokation/browser has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}
				break;

			default:
				throw new FrameworkException("Unhandled ExecutionMode!");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FrameworkException(
					"The Sauce appium driver invocation created a problem , please check the capabilities");
		}
		return driver;

	}

	@SuppressWarnings("rawtypes")
	public static AppiumDriver getTestObjectAppiumDriver(MobileExecutionPlatform executionPlatform, String deviceName,
			String sauceURL, SeleniumTestParameters testParameters) {

		AppiumDriver driver = null;

		mobileProperties = Settings.getMobilePropertiesInstance();

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		try {
			switch (executionPlatform) {

			case ANDROID:
				desiredCapabilities.setCapability("testobject_api_key",
						mobileProperties.getProperty("AccessKeyAndroid"));
				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("TestObjectAppiumVersion"));
				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("testobject_suite_name", testParameters.getCurrentScenario());
				desiredCapabilities.setCapability("testobject_test_name", testParameters.getCurrentTestcase());
				desiredCapabilities.setCapability("noReset", "true");
				desiredCapabilities.setCapability("testobject_app_id",
						mobileProperties.getProperty("TestObjectAndroidAppKey"));

				try {
					driver = new AndroidDriver(new URL(sauceURL), desiredCapabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}

				break;

			case IOS:

				desiredCapabilities.setCapability("testobject_api_key", mobileProperties.getProperty("AccessKeyiOS"));
				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("TestObjectAppiumVersion"));
				desiredCapabilities.setCapability("platformName", "iOS");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
				desiredCapabilities.setCapability("testobject_suite_name", testParameters.getCurrentScenario());
				desiredCapabilities.setCapability("testobject_test_name", testParameters.getCurrentTestcase());
				desiredCapabilities.setCapability("noReset", "true");
				desiredCapabilities.setCapability("testobject_app_id",
						mobileProperties.getProperty("TestObjectiOSAppKey"));

				try {
					driver = new IOSDriver(new URL(sauceURL), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}
				break;

			case WEB_ANDROID:
				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("TestObjectAppiumVersion"));
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("deviceOrientation", "portrait");
				desiredCapabilities.setCapability("browserName", "chrome");
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("name", testParameters.getCurrentTestcase());

				try {
					driver = new AndroidDriver(new URL(sauceURL), desiredCapabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The android driver/browser invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}
				break;

			case WEB_IOS:
				desiredCapabilities.setCapability("appiumVersion",
						mobileProperties.getProperty("TestObjectAppiumVersion"));
				desiredCapabilities.setCapability("platformName", "ios");
				desiredCapabilities.setCapability("deviceName", deviceName);
				desiredCapabilities.setCapability("name", testParameters.getCurrentTestcase());
				desiredCapabilities.setCapability("browserName", "Safari");
				desiredCapabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());

				try {
					driver = new IOSDriver(new URL(sauceURL), desiredCapabilities);

				} catch (MalformedURLException e) {
					throw new FrameworkException(
							"The IOS driver invokation/browser has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
				}
				break;

			default:
				throw new FrameworkException("Unhandled ExecutionMode!");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FrameworkException(
					"The Sauce appium driver invocation created a problem , please check the capabilities");
		}
		return driver;

	}

}
