package com.cognizant.framework;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cognizant.framework.nft.AccessibilityNFT;
import com.cognizant.framework.nft.BrokenLinks;
import com.cognizant.framework.nft.PerformanceNFT;
import com.cognizant.framework.nft.SecurityNFT;

/**
 * 
 * @author 368731 Ganesh Tidke
 *         <h1>This class works on non functional testing for web pages inlcudes
 *         Accessibility,Performance and Security</h1>
 *
 */
public class NFT {

	
	public static boolean executeFunctional=false;
	public static boolean executeAccessibility=false;
	public static boolean executePerformance=false;
	public static boolean executeSecurity=false;
	public static boolean executeVisualValidation=false;
	public static boolean executeBrokenLinks=false;
	
	
	
	static Properties properties = Settings.getInstance();
	static String siteValue = null;
	static JSONObject browserdata;
	static JSONArray output = new JSONArray();

	private static boolean isSecurityExecutedForEnv1 = false;
	private static boolean isSecurityExecutedForEnv2 = false;

	private static String Env1 = "";
	private static String Env2 = "";

	public static void copyNFTReport() {
		String projPath = WhitelistingPath.cleanStringForFilePath(System.getProperty("user.dir"));
		if (NFT.executeAccessibility) {

			File renamedFile = new File(projPath + "/donut.json");
			try {
				FileUtils.copyFileToDirectory(renamedFile, new File(projPath + "/Results/NFTReports"), true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			File renamedviolationsFile = new File(projPath + "/violations.json");
			try {
				FileUtils.copyFileToDirectory(renamedviolationsFile, new File(projPath + "/Results/NFTReports"), true);
			} catch (IOException e) {
				e.printStackTrace();

			}
		}

		// Added to copy the NFT report to respective report template
		if (Boolean.parseBoolean(properties.getProperty("performanceReport"))) {

			File renamedperformanceFile = new File(projPath + "/performance.json");
			try {
				FileUtils.copyFileToDirectory(renamedperformanceFile, new File(projPath + "/Results/NFTReports"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (Boolean.parseBoolean(properties.getProperty("securityReport"))) {

			SecurityNFT.copyReportTo("Results/NFTReports");

		}

	}

	public static void copyNFTReportToRun() {
		// TODO Auto-generated method stub
		
		String projPath = WhitelistingPath.cleanStringForFilePath(System.getProperty("user.dir"));
		String reportPath=RegressionReportComparison.getReportPath();
		try {
			FileUtils.copyDirectoryToDirectory(new File(projPath + "/Results/NFTReports"), new File(reportPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void setExecutionFlags() {

		String functional = System.getProperty("Functional");
		if (functional != null && !functional.isEmpty() && !functional.equals("")) {
			if (functional.equalsIgnoreCase("yes"))
				executeFunctional = true;
		}else if (Boolean.parseBoolean(properties.getProperty("functionalReport"))) {
			executeFunctional = true;

		}
		
		
		String security = System.getProperty("Security");
		if (security != null && !security.isEmpty() && !security.equals("")) {
			if (security.equalsIgnoreCase("yes"))
				executeSecurity = true;
		}else if (Boolean.parseBoolean(properties.getProperty("securityReport"))) {
			executeSecurity = true;

		}
		
		String performance = System.getProperty("Performance");
		if (performance != null && !performance.isEmpty() && !performance.equals("")) {
			if (performance.equalsIgnoreCase("yes"))
				executePerformance = true;
		} else if (Boolean.parseBoolean(properties.getProperty("performanceReport"))) {
			executePerformance = true;

		}

		String accessibility = System.getProperty("Accessibility");
		if (accessibility != null && !accessibility.isEmpty() && !accessibility.equals("")) {
			if (accessibility.equalsIgnoreCase("yes")) {
				executeAccessibility = true;
			}

		} else if (Boolean.parseBoolean(properties.getProperty("accessibilityReport"))) {
			executeAccessibility = true;
		}

		String visualComparison = System.getProperty("VisualComparison");
		if (visualComparison != null && !visualComparison.isEmpty() && !visualComparison.equals("")) {
			if (visualComparison.equalsIgnoreCase("yes")) {
				executeVisualValidation = true;
			}

		} else if (Boolean.parseBoolean(properties.getProperty("visualValidationReport"))) {
			executeVisualValidation = true;
		}

		String brokenLink = System.getProperty("BrokenLink");
		if (brokenLink != null && !brokenLink.isEmpty() && !brokenLink.equals("")) {
			if (brokenLink.equalsIgnoreCase("yes"))
				executeBrokenLinks = true;
		} else if (Boolean.parseBoolean(properties.getProperty("brokenLinksReport"))) {
			executeBrokenLinks = true;
		}

	}

}
