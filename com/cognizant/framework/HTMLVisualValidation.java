package com.cognizant.framework;

import java.io.File;
import java.util.List;

import com.cognizant.framework.nft.HTMLVisualValidationReport;

public class HTMLVisualValidation {
	private static String logPath, testStepRow = "", relativeLogPath;
	private static List<String> instace1Screens, inst1StepName, inst1StepStatus, inst1StepNumber;
	private static List<String> instace2Screens, inst2StepName, inst2StepStatus, inst2StepNumber;
	public static int totalComparisons = 0, visuSim = 0, visuDiff = 0;

	public static void comapreImages() {

		if (NFT.executeVisualValidation) {
			logPath = HtmlReport.currrentlogPath.split("HTML Results")[0];
			String currentEclipseProjectName = new File(System.getProperty("user.dir")).getName();
			relativeLogPath = logPath.split(currentEclipseProjectName)[1];

			instace1Screens = HtmlReport.instance1Screenshots;
			instace2Screens = HtmlReport.instance2Screenshots;

			HTMLVisualValidationReport.screenShotComapre(instace1Screens, instace2Screens,
					logPath + "Screenshots" + File.separator, "." + relativeLogPath + "HTML Results/");
		}
	}
}
