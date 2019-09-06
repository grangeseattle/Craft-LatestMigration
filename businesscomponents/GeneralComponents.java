package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;
import com.cognizant.framework.nft.AccessibilityNFT;
import com.cognizant.framework.nft.PerformanceNFT;
import com.cognizant.framework.nft.SecurityNFT;

import pages.FlightFinderPage;
import pages.SignOnPage;

/**
 * Class for storing general purpose business components
 * 
 * @author Cognizant
 */
public class GeneralComponents extends ReusableLibrary {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public GeneralComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);

	}

	public void invokeApplication() {
		String applicationurl = null;
		if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance1")) {
			applicationurl = properties.getProperty("ApplicationUrl1");
		} else if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance2")) {
			applicationurl = properties.getProperty("ApplicationUrl2");
		}

		driver.get(applicationurl);
		report.updateTestLog("Invoke Application",
				"Launched browser with URL:" + scriptHelper.getReport().getSeleniumTestParameters().getEnvironment(),
				Status.PASS);
		AccessibilityNFT.evaluatePageAccessibilityTest(driver.getWebDriver(), applicationurl,scriptHelper);
		PerformanceNFT.evaluatePerformanceForPage(driver.getWebDriver(),driver.getCurrentUrl(),scriptHelper);
		 SecurityNFT.evaluateSecurityForPage(driver.getCurrentUrl(),scriptHelper);
		//
	}

	public void loginAsValidUser() {

		SignOnPage signOnPage = new SignOnPage(scriptHelper);
		signOnPage.loginAsValidUser();
		// NFT.evaluatePageAccessibilityTest(driver.getWebDriver(),
		// driver.getCurrentUrl());
		// The login succeeds if the flight finder page is displayed
		// Hence no further verification is required
		report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
	}

	public void loginAsInvalidUser() {
		SignOnPage signOnPage = new SignOnPage(scriptHelper);
		signOnPage = signOnPage.loginAsInvalidUser();
		// NFT.evaluatePageAccessibilityTest(driver.getWebDriver(),
		// driver.getCurrentUrl());
		// The login fails if the sign-on page is displayed again
		// Hence no further verification is required
		report.updateTestLog("Verify Login", "Login failed for invalid user", Status.PASS);
	}

	public void logoutFromTours() {
		FlightFinderPage flightFinderPage = new FlightFinderPage(scriptHelper);
		flightFinderPage.logout();
		// NFT.evaluatePageAccessibilityTest(driver.getWebDriver(),
		// driver.getCurrentUrl());
	}
}