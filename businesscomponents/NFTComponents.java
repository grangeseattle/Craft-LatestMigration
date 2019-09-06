package businesscomponents;

import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.nft.AccessibilityNFT;
import com.cognizant.framework.nft.PerformanceNFT;
import com.cognizant.framework.nft.SecurityNFT;

public class NFTComponents extends ReusableLibrary {

	public NFTComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void testNFTFeature() {

		String applicationurl = null;
		if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance1")) {
			applicationurl = properties.getProperty("ApplicationUrl1");
		} else if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance2")) {
			applicationurl = properties.getProperty("ApplicationUrl2");
		}

		AccessibilityNFT.evaluatePageAccessibilityTest(driver.getWebDriver(), applicationurl, scriptHelper);
		PerformanceNFT.evaluatePerformanceForPage(driver.getWebDriver(), applicationurl, scriptHelper);
		SecurityNFT.evaluateSecurityForPage(applicationurl, scriptHelper);
	}

}
