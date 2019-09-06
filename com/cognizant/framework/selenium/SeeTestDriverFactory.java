package com.cognizant.framework.selenium;

import com.cognizant.framework.FrameworkException;
import com.experitest.client.Client;
import com.experitest.selenium.MobileWebDriver;

public class SeeTestDriverFactory {

	public static Client client;

	private SeeTestDriverFactory() {

	}

	/**
	 * Function to return the appropriate {@link MobileWebDriver} object based
	 * on the parameters passed
	 * 
	 * @param host
	 * @param port
	 * @param projectBaseDirectory
	 * @param reportFormat
	 * @param testName
	 * @param executionPlatform
	 * @param AndroidAppName
	 * @param iOSAppName
	 * @param androidWebAppName
	 * @param iosWebAppName
	 * @param deviceName
	 * @return The corresponding {@link MobileWebDriver} object
	 */
	public static MobileWebDriver getSeeTestDriver(String host, int port, String projectBaseDirectory,
			String reportFormat, String reportDirectory, String testName, MobileExecutionPlatform executionPlatform,
			String AndroidAppName, String iOSAppName, String androidWebAppName, String iosWebAppName,
			String deviceName) {
		MobileWebDriver driver = null;
		try {

			driver = new MobileWebDriver(host, port, projectBaseDirectory, reportFormat, reportDirectory, testName);
			client = driver.client;
			String androidPrefix = "adb:" + deviceName;
			String iosPrefix = "ios_app:" + deviceName;
			switch (executionPlatform) {
			case ANDROID:
				// client.setDevice(androidPrefix);
				driver.setDevice(androidPrefix);
				driver.application(AndroidAppName).launch(true, true);
				break;
			case IOS:
				// client.setDevice(iosPrefix);
				driver.setDevice(iosPrefix);
				driver.application(iOSAppName).launch(true, true);
				break;
			case WEB_ANDROID:
				driver.setDevice(androidPrefix);
				driver.application("chrome:" + androidWebAppName).launch(false, true);
				break;
			case WEB_IOS:
				// yet to implement
				driver.setDevice(iosPrefix);
				driver.application("safari:" + iosWebAppName).launch(true, true);
				break;
			default:
				break;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FrameworkException(
					"The SeeTest driver invocation created a problem , please check the capabilities");
		}

		return driver;
	}

}
