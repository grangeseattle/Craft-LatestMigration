package com.cognizant.framework.selenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.TestParameters;

import org.openqa.selenium.Platform;

/**
 * Class to encapsulate various input parameters required for each test script
 * 
 * @author Cognizant
 */
public class SeleniumTestParameters extends TestParameters {
	private ExecutionMode executionMode;
	private Browser browser;
	private String browserVersion;
	private Platform platform;
	private String deviceName;
	private boolean installApplication;
	private MobileExecutionPlatform mobileExecutionPlatform;
	private ToolName toolName;
	private String mobileOsVersion;
	private String platformVersion;

	private ExtentTest extentTest;
	private ExtentReports extentReport;

	public SeleniumTestParameters(String currentScenario, String currentTestcase) {
		super(currentScenario, currentTestcase);
		installApplication = false;
	}

	/**
	 * Function to get the {@link ExecutionMode} for the test being executed
	 * 
	 * @return The {@link ExecutionMode} for the test being executed
	 */
	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	/**
	 * Function to set the {@link ExecutionMode} for the test being executed
	 * 
	 * @param executionMode
	 *            The {@link ExecutionMode} for the test being executed
	 */
	public void setExecutionMode(ExecutionMode executionMode) {
		this.executionMode = executionMode;
	}

	/**
	 * Function to get the {@link MobileExecutionPlatform} for the test being
	 * executed
	 * 
	 * @return The {@link MobileExecutionPlatform} for the test being executed
	 */
	public MobileExecutionPlatform getMobileExecutionPlatform() {
		return mobileExecutionPlatform;
	}

	/**
	 * Function to set the {@link MobileExecutionPlatform} for the test being
	 * executed
	 * 
	 * @param mobileExecutionPlatform
	 *            The {@link MobileExecutionPlatform} for the test being
	 *            executed
	 */
	public void setMobileExecutionPlatform(MobileExecutionPlatform mobileExecutionPlatform) {
		this.mobileExecutionPlatform = mobileExecutionPlatform;
	}

	/**
	 * Function to get the {@link ToolName} for the test being executed
	 * 
	 * @return The {@link ToolName} for the test being executed
	 */
	public ToolName getMobileToolName() {
		return toolName;
	}

	/**
	 * Function to set the {@link ToolName} for the test being executed
	 * 
	 * @param mobileToolName
	 *            The {@link ToolName} for the test being executed
	 */
	public void setMobileToolName(ToolName mobileToolName) {
		this.toolName = mobileToolName;
	}

	/**
	 * Function to get a Boolean value indicating whether to install application
	 * in Device
	 * 
	 * @return Boolean value indicating whether to install Application in device
	 */
	public boolean shouldInstallApplication() {
		return installApplication;
	}

	/**
	 * Function to set a Boolean value indicating whether to install application
	 * in Device
	 * 
	 * @param installApplication
	 *            Boolean value indicating whether to install application in
	 *            Device
	 */
	public void setInstallApplication(boolean installApplication) {
		this.installApplication = installApplication;
	}

	/**
	 * Function to get the {@link Browser} on which the test is to be executed
	 * 
	 * @return The {@link Browser} on which the test is to be executed
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * Function to set the {@link Browser} on which the test is to be executed
	 * 
	 * @param browser
	 *            The {@link Browser} on which the test is to be executed
	 */
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	/**
	 * Function to get the OS Version of device on which the test is to be
	 * executed
	 * 
	 * @return The OS Version of device Version on which the test is to be
	 *         executed
	 */
	public String getMobileOSVersion() {
		return mobileOsVersion;
	}

	/**
	 * Function to set the OS Version of device Version on which the test is to
	 * be executed
	 * 
	 * @param mobileOsVersion
	 *            The OS Version of device Version on which the test is to be
	 *            executed
	 */
	public void setmobileOSVersion(String mobileOsVersion) {
		this.mobileOsVersion = mobileOsVersion;
	}

	/**
	 * Function to get the Browser Version on which the test is to be executed
	 * 
	 * @return The Browser Version on which the test is to be executed
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Function to set the Browser Version on which the test is to be executed
	 * 
	 * @param version
	 *            The Browser Version on which the test is to be executed
	 */
	public void setBrowserVersion(String version) {
		this.browserVersion = version;
	}

	/**
	 * Function to get the {@link Platform} on which the test is to be executed
	 * 
	 * @return The {@link Platform} on which the test is to be executed
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * Function to set the {@link Platform} on which the test is to be executed
	 * 
	 * @param platform
	 *            The {@link Platform} on which the test is to be executed
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	/**
	 * Function to get the browser and platform on which the test is to be
	 * executed
	 * 
	 * @return The browser and platform on which the test is to be executed
	 */
	public String getBrowserAndPlatform() {
		if (this.browser == null) {
			throw new FrameworkException("The browser has not been initialized!");
		}

		String browserAndPlatform = this.browser.toString();
		if (this.browserVersion != null) {
			browserAndPlatform += " " + browserVersion;
		}
		if (this.platform != null) {
			browserAndPlatform += " on " + this.platform;
		}

		return browserAndPlatform;
	}

	/**
	 * Function to get the name of the mobile device on which the test is to be
	 * executed
	 * 
	 * @return The name of the mobile device on which the test is to be executed
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Function to set the name of the mobile device on which the test is to be
	 * executed<br>
	 * <br>
	 * If the ExecutionMode is PERFECTO_REMOTEWEBDRIVER, this function also sets
	 * the device's Perfecto MobileCloud ID, by accessing the Perfecto Device
	 * List within the Global Settings.properties file
	 * 
	 * @param deviceName
	 *            The name of the mobile device on which the test is to be
	 *            executed
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	private String seeTestPort;

	/**
	 * Function to get the See Test Port on which the test is to be executed
	 * 
	 * @return The See Test Port on which the test is to be executed
	 */
	public String getSeeTestPort() {
		return seeTestPort;
	}

	/**
	 * Function to set the See Test Port on which the test is to be executed
	 * 
	 * @param seeTestPort
	 *            The See Test Port on which the test is to be executed
	 */
	public void setSeeTestPort(String seeTestPort) {
		this.seeTestPort = seeTestPort;
	}

	@Override
	public String getAdditionalDetails() {
		String additionalDetails = super.getAdditionalDetails();

		if ("".equals(additionalDetails)) {
			switch (this.executionMode) {
			case API:
				additionalDetails = "Rest Assured API";
				break;

			case MOBILE:
				additionalDetails = this.getMobileDeviceDetails();
				break;
			case PERFECTO:
				additionalDetails = this.getMobileDeviceDetails();
				break;
			case SEETEST:
				additionalDetails = this.getMobileDeviceDetails();
				break;
			case SAUCELABS:
				additionalDetails = this.getMobileDeviceDetails();
				break;
			case TESTOBJECT:
				additionalDetails = this.getMobileDeviceDetails();

			case BROWSERSTACK:
				additionalDetails = this.getMobileDeviceDetails();
				break;
			case FASTEST:
				additionalDetails = this.getMobileDeviceDetails();
				break;

			default:
				additionalDetails = this.getBrowserAndPlatform();
			}
		}

		return additionalDetails;
	}

	private String getMobileDeviceDetails() {
		String details;
		if (this.deviceName == null && this.browser == null) {
			throw new FrameworkException("The Mobile Device ID or Run Manger has not been Set in Run Manager!");
		} else {

			if (ToolName.APPIUM.equals(this.toolName)) {
				details = this.deviceName + "-" + this.toolName;
			} else {
				details = this.browser.toString() + "-" + this.platform.toString();
			}

			return details;
		}
	}

	/**
	 * Function to get the Platform Version on which the test is to be executed
	 * 
	 * @return The Platform Version on which the test is to be executed
	 */
	public String getPlatformVersion() {
		return platformVersion;
	}

	/**
	 * Function to set the Platform Version on which the test is to be executed
	 * 
	 * @param platformVersion
	 *            The Platform Version on which the test is to be executed
	 */
	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	/**
	 * Function to get the Extent Test Object {@link ExtentTest}
	 * 
	 * @return The Instance of ExtentTest Object
	 */
	public ExtentTest getExtentTest() {
		return extentTest;
	}

	/**
	 * Function to set the Extent Test Object
	 * 
	 * @param extentTest
	 *            Extent Test object {@link ExtentTest}
	 */
	public void setExtentTest(ExtentTest extentTest) {
		this.extentTest = extentTest;

	}

	/**
	 * Function to get the Extent Report Object {@link ExtentReports}
	 * 
	 * @return The Instance of Extent Report Object
	 */
	public ExtentReports getExtentReport() {
		return extentReport;
	}

	/**
	 * Function to set the Extent ReportF Object
	 * 
	 * @param extentReport
	 *            Extent Report object {@link ExtentReports}
	 */
	public void setExtentReport(ExtentReports extentReport) {
		this.extentReport = extentReport;
	}

}