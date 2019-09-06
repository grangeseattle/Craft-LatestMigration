package com.cognizant.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cognizant.framework.selenium.ExecutionMode;

/**
 * Class to encapsulate the HTML report generation functions of the framework
 * 
 * @author Cognizant
 */
public class HtmlReport implements ReportType {
	private String testLogPath, resultSummaryPath;
	private ReportSettings reportSettings;
	private ReportTheme reportTheme;

	private boolean isTestLogHeaderTableCreated = false;
	private boolean isTestLogMainTableCreated = false;
	private boolean isResultSummaryHeaderTableCreated = false;
	private boolean isResultSummaryMainTableCreated = false;

	private String currentSection = "";
	private String currentSubSection = "";
	private int currentContentNumber = 1;
	private String currentInstance;
	private Properties properties = Settings.getInstance();

	public static List<String> instance1Screenshots = new ArrayList<String>();
	public static List<String> instance1StepNumber = new ArrayList<String>();
	public static List<String> instance1StepName = new ArrayList<String>();
	public static List<String> instance1StepStatus = new ArrayList<String>();

	public static List<String> instance2Screenshots = new ArrayList<String>();
	public static List<String> instance2StepNumber = new ArrayList<String>();
	public static List<String> instance2StepName = new ArrayList<String>();
	public static List<String> instance2StepStatus = new ArrayList<String>();

	public static String currrentlogPath = null;

	/**
	 * Constructor to initialize the HTML report
	 * 
	 * @param reportSettings The {@link ReportSettings} object
	 * @param reportTheme    The {@link ReportTheme} object
	 */
	public HtmlReport(ReportSettings reportSettings, ReportTheme reportTheme) {
		this.reportSettings = reportSettings;
		this.reportTheme = reportTheme;

		testLogPath = WhitelistingPath.cleanStringForFilePath(reportSettings.getReportPath() + Util.getFileSeparator()
				+ "HTML Results" + Util.getFileSeparator() + reportSettings.getReportName() + ".html");

		resultSummaryPath = WhitelistingPath.cleanStringForFilePath(reportSettings.getReportPath()
				+ Util.getFileSeparator() + "HTML Results" + Util.getFileSeparator() + "Summary" + ".html");
		currrentlogPath = resultSummaryPath;

//		if(Boolean.parseBoolean(properties.getProperty("visualValidationReport"))) {
//				instance1Screenshots = new ArrayList<String>();
//				instance1StepNumber = new ArrayList<String>();
//				instance1StepName = new ArrayList<String>();
//				instance1StepStatus = new ArrayList<String>();
//				
//				instance2Screenshots = new ArrayList<String>();
//				instance2StepNumber = new ArrayList<String>();
//				instance2StepName = new ArrayList<String>();
//				instance2StepStatus = new ArrayList<String>();
//		}

	}

	private String getThemeCss2() {
		return "\t\t <style type='text/css'> \n" + "\t\t\t body { \n" + "\t\t\t\t background-color: "
				+ reportTheme.getContentForeColor() + "; \n" + "\t\t\t\t font-family: Verdana, Geneva, sans-serif; \n"
				+ "\t\t\t\t text-align: center; \n" + "\t\t\t } \n\n" +

				"\t\t\t small { \n" + "\t\t\t\t font-size: 0.7em; \n" + "\t\t\t } \n\n" +

				"\t\t\t table { \n" +
				// "\t\t\t\t border: 1px solid #4D7C7B; \n" +
				// "\t\t\t\t border-collapse: collapse; \n" +
				// "\t\t\t\t border-spacing: 0px; \n" +
				"\t\t\t\t width: 95%; \n" + "\t\t\t\t margin-left: auto; \n" + "\t\t\t\t margin-right: auto; \n"
				+ "\t\t\t } \n\n" +

				"\t\t\t tr.heading { \n" + "\t\t\t\t background-color: " + reportTheme.getHeadingBackColor() + "; \n"
				+ "\t\t\t\t color: " + reportTheme.getHeadingForeColor() + "; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t font-weight: bold; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.subheading { \n" + "\t\t\t\t background-color: " + reportTheme.getsubHeadingBackColor()
				+ "; \n" + "\t\t\t\t color: " + reportTheme.getsubHeadingForeColor() + "; \n"
				+ "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t text-align: justify; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.section { \n" + "\t\t\t\t background-color: " + reportTheme.getSectionBackColor() + "; \n"
				+ "\t\t\t\t color: " + reportTheme.getSectionForeColor() + "; \n" + "\t\t\t\t cursor: pointer; \n"
				+ "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t text-align: justify; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.subsection { \n" + "\t\t\t\t background-color: " + reportTheme.getsubSectionBackColor()
				+ "; \n" + "\t\t\t\t cursor: pointer; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.content { \n" + "\t\t\t\t background-color: " + reportTheme.getsubSectionBackColor() + "; \n"
				+ "\t\t\t\t color: " + reportTheme.getContentBackColor() + "; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t display: table-row; \n" + "\t\t\t } \n\n" +

				"\t\t\t td { \n" + "\t\t\t\t padding: 4px; \n" + "\t\t\t\t text-align: inherit\\0/; \n"
				+ "\t\t\t\t word-wrap: break-word; \n" + "\t\t\t\t max-width: 450px; \n" + "\t\t\t } \n\n" +

				"\t\t\t th { \n" + "\t\t\t\t padding: 4px; \n" + "\t\t\t\t text-align: inherit\\0/; \n"
				// + "\t\t\t\t word-break: break-all; \n"
				+ "\t\t\t\t max-width: 450px; \n" + "\t\t\t } \n\n" +

				"\t\t\t td.justified { \n" + "\t\t\t\t text-align: justify; \n" + "\t\t\t } \n\n" +

				"\t\t\t td.pass { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: green; \n" + "\t\t\t } \n\n"
				+

				"\t\t\t td.fail { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: red; \n" + "\t\t\t } \n\n" +

				"\t\t\t td.done, td.screenshot { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: black; \n"
				+ "\t\t\t } \n\n" +

				"\t\t\t td.debug { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: blue; \n" + "\t\t\t } \n\n"
				+

				"\t\t\t td.warning { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: orange; \n"
				+ "\t\t\t } \n" + "\t\t\t img { \n" + "\t\t\t\t width:" + reportSettings.getWidth() + "; \n"
				+ "\t\t\t\t height:" + reportSettings.getHeight() + "; \n" + "\t\t\t } \n" + "\t\t\t th.perfColor { \n"
				+ "\t\t\t\t color: darkorchid; \n" + "\t\t\t } \n\n"
				+ ".zoom {padding: 0px;background-color: green;transition: transform .1s;width:100px;height:100px;margin: 0 auto;}"
				+ ".zoom:hover {-ms-transform: scale(6);-webkit-transform: scale(6);transform: scale(6);e viewport)}"

				+ "\t\t </style> \n\n";
	}

	private String getThemeCss() {
		return "\t\t <style type='text/css'> \n" + "\t\t\t body { \n" + "\t\t\t\t background-color: "
				+ reportTheme.getContentForeColor() + "; \n" + "\t\t\t\t font-family: Verdana, Geneva, sans-serif; \n"
				+ "\t\t\t\t text-align: center; \n" + "\t\t\t } \n\n" +

				"\t\t\t small { \n" + "\t\t\t\t font-size: 0.7em; \n" + "\t\t\t } \n\n" +

				"\t\t\t table { \n" +
				// "\t\t\t\t border: 1px solid #4D7C7B; \n" +
				// "\t\t\t\t border-collapse: collapse; \n" +
				// "\t\t\t\t border-spacing: 0px; \n" +
				"\t\t\t\t width: 95%; \n" + "\t\t\t\t margin-left: auto; \n" + "\t\t\t\t margin-right: auto; \n"
				+ "\t\t\t } \n\n" +

				"\t\t\t tr.heading { \n" + "\t\t\t\t background-color: " + reportTheme.getHeadingBackColor() + "; \n"
				+ "\t\t\t\t color: " + reportTheme.getHeadingForeColor() + "; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t font-weight: bold; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.subheading { \n" + "\t\t\t\t background-color: " + reportTheme.getsubHeadingBackColor()
				+ "; \n" + "\t\t\t\t color: " + reportTheme.getsubHeadingForeColor() + "; \n"
				+ "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t text-align: justify; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.section { \n" + "\t\t\t\t background-color: " + reportTheme.getSectionBackColor() + "; \n"
				+ "\t\t\t\t color: " + reportTheme.getSectionForeColor() + "; \n" + "\t\t\t\t cursor: pointer; \n"
				+ "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t text-align: justify; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.subsection { \n" + "\t\t\t\t background-color: " + reportTheme.getsubSectionBackColor()
				+ "; \n" + "\t\t\t\t cursor: pointer; \n" + "\t\t\t } \n\n" +

				"\t\t\t tr.content { \n" + "\t\t\t\t background-color: " + reportTheme.getsubSectionBackColor() + "; \n"
				+ "\t\t\t\t color: " + reportTheme.getContentBackColor() + "; \n" + "\t\t\t\t font-size: 0.6em; \n"
				+ "\t\t\t\t display: none; \n" + "\t\t\t } \n\n" +

				"\t\t\t td { \n" + "\t\t\t\t padding: 4px; \n" + "\t\t\t\t text-align: inherit\\0/; \n"
				+ "\t\t\t\t word-wrap: break-word; \n" + "\t\t\t\t max-width: 450px; \n" + "\t\t\t } \n\n" +

				"\t\t\t th { \n" + "\t\t\t\t padding: 4px; \n" + "\t\t\t\t text-align: inherit\\0/; \n"
				// + "\t\t\t\t word-break: break-all; \n"
				+ "\t\t\t\t max-width: 450px; \n" + "\t\t\t } \n\n" +

				"\t\t\t td.justified { \n" + "\t\t\t\t text-align: justify; \n" + "\t\t\t } \n\n" +

				"\t\t\t td.pass { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: green; \n" + "\t\t\t } \n\n"
				+

				"\t\t\t td.fail { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: red; \n" + "\t\t\t } \n\n" +

				"\t\t\t td.done, td.screenshot { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: black; \n"
				+ "\t\t\t } \n\n" +

				"\t\t\t td.debug { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: blue; \n" + "\t\t\t } \n\n"
				+

				"\t\t\t td.warning { \n" + "\t\t\t\t font-weight: bold; \n" + "\t\t\t\t color: orange; \n"
				+ "\t\t\t } \n" + "\t\t\t img { \n" + "\t\t\t\t width:" + reportSettings.getWidth() + "; \n"
				+ "\t\t\t\t height:" + reportSettings.getHeight() + "; \n" + "\t\t\t } \n" + "\t\t\t th.perfColor { \n"
				+ "\t\t\t\t color: darkorchid; \n" + "\t\t\t } \n\n"
				+ ".zoom {padding: 0px;background-color: green;transition: transform .1s;width:100px;height:100px;margin: 0 auto;}"
				+ ".zoom:hover {-ms-transform: scale(6);-webkit-transform: scale(6);transform: scale(6);e viewport)}"

				+ "\t\t </style> \n\n";
	}

	private String getJavascriptFunctions() {
		return "\t\t <script> \n" + "\t\t\t function toggleMenu(objID) { \n"
				+ "\t\t\t\t if (!document.getElementById) return; \n"
				+ "\t\t\t\t var ob = document.getElementById(objID).style; \n"
				+ "\t\t\t\t if(ob.display === 'none') { \n" + "\t\t\t\t\t try { \n"
				+ "\t\t\t\t\t\t ob.display='table-row-group'; \n" + "\t\t\t\t\t } catch(ex) { \n"
				+ "\t\t\t\t\t\t ob.display='block'; \n" + "\t\t\t\t\t } \n" + "\t\t\t\t } \n" + "\t\t\t\t else { \n"
				+ "\t\t\t\t\t ob.display='none'; \n" + "\t\t\t\t } \n" + "\t\t\t } \n" +

				"\t\t\t function toggleSubMenu(objId) { \n" + "\t\t\t\t for(i=1; i<10000; i++) { \n"
				+ "\t\t\t\t\t var ob = document.getElementById(objId.concat(i)); \n" + "\t\t\t\t\t if(ob === null) { \n"
				+ "\t\t\t\t\t\t break; \n" + "\t\t\t\t\t } \n"
				+ "\t\t\t\t\t if(ob.style.display === 'none' || ob.style.display==='') { \n" + "\t\t\t\t\t\t try { \n"
				+ "\t\t\t\t\t\t\t ob.style.display='table-row'; \n" + "\t\t\t\t\t\t } catch(ex) { \n"
				+ "\t\t\t\t\t\t\t ob.style.display='block'; \n" + "\t\t\t\t\t\t } \n" + "\t\t\t\t\t } \n"
				+ "\t\t\t\t\t else { \n" + "\t\t\t\t\t\t ob.style.display='none'; \n" + "\t\t\t\t\t } \n"
				+ "\t\t\t\t } \n" + "\t\t\t } \n" + "\t\t </script> \n";
	}

	/* TEST LOG FUNCTIONS */

	@Override
	public void initializeTestLog() {
		File testLogFile = new File(testLogPath);
		try {
			testLogFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while creating HTML test log file");
		}

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(testLogFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("Cannot find HTML test log file");
		}
		PrintStream printStream = new PrintStream(outputStream);

		String testLogHeadSection;

		testLogHeadSection = "<!DOCTYPE html> \n" + "<html> \n" + "\t <head> \n" + "\t\t <meta charset='UTF-8'> \n"
				+ "\t\t <title>" + reportSettings.getProjectName() + " - " + reportSettings.getReportName()
				+ " Automation Execution Results" + "</title> \n\n" + getThemeCss() + getJavascriptFunctions()
				+ "\t </head> \n";

		printStream.println(testLogHeadSection);
		printStream.close();
	}

	@Override
	public void addTestLogHeading(String heading) {
		if (!isTestLogHeaderTableCreated) {
			createTestLogHeaderTable();
			isTestLogHeaderTableCreated = true;
		}

		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			String testLogHeading = "\t\t\t\t <tr class='heading'> \n"
					+ "\t\t\t\t\t <th colspan='4' style='font-family:Copperplate Gothic; font-size:1.4em;'> \n"
					+ "\t\t\t\t\t\t " + heading + " \n" + "\t\t\t\t\t </th> \n" + "\t\t\t\t </tr> \n";
			bufferedWriter.write(testLogHeading);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding heading to HTML test log");
		}
	}

	private void createTestLogHeaderTable() {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			String testLogHeaderTable = "\t <body> \n" + "\t\t <table id='header'> \n" + "\t\t\t <thead> \n";
			bufferedWriter.write(testLogHeaderTable);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding header table to HTML test log");
		}
	}

	@Override
	public void addTestLogSubHeading(String subHeading1, String subHeading2, String subHeading3, String subHeading4) {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			String testLogSubHeading = "\t\t\t\t <tr class='subheading'> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading1.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading2.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading3.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading4.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t </tr> \n";
			bufferedWriter.write(testLogSubHeading);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding sub-heading to HTML test log");
		}
	}

	private void createTestLogMainTable() {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			String testLogMainTable = "\t\t\t </thead> \n" + "\t\t </table> \n\n" +

					"\t\t <table id='main'> \n";

			bufferedWriter.write(testLogMainTable);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding main table to HTML test log");
		}
	}

	@Override
	public void addTestLogTableHeadings() {
		if (!isTestLogMainTableCreated) {
			createTestLogMainTable();
			isTestLogMainTableCreated = true;
		}

		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			String testLogTableHeading;

			if (!reportSettings.getIsAPIAutomation()) {
				testLogTableHeading = "\t\t\t <thead> \n" + "\t\t\t\t <tr class='heading'> \n"
						+ "\t\t\t\t\t <th>Step No</th> \n" + "\t\t\t\t\t <th>Step Name</th> \n"
						+ "\t\t\t\t\t <th>Description</th> \n" + "\t\t\t\t\t <th>Status</th> \n"
						+ "\t\t\t\t\t <th>Step Time</th> \n" + "\t\t\t\t\t <th>ScreenShot</th> \n" + "\t\t\t\t </tr> \n"
						+ "\t\t\t </thead> \n\n";
			} else {
				testLogTableHeading = "\t\t\t <thead> \n" + "\t\t\t\t <tr class='heading'> \n"
						+ "\t\t\t\t\t <th>Step No</th> \n" + "\t\t\t\t\t <th>EndPoints</th> \n"
						+ "\t\t\t\t\t <th>Expected Result</th> \n" + "\t\t\t\t\t <th>Actual Result</th> \n"
						+ "\t\t\t\t\t <th>Status</th> \n" + "\t\t\t\t </tr> \n" + "\t\t\t </thead> \n\n";
			}

			bufferedWriter.write(testLogTableHeading);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding main table headings to HTML test log");
		}
	}

	@Override
	public void addTestLogSection(String section, String currentInstance) {
		String testLogSection = "";
		if (!"".equals(currentSection)) {
			testLogSection = "\t\t\t </tbody>";
		}

		currentSection = section.replaceAll("[^a-zA-Z0-9]", "");

		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			testLogSection += "\t\t\t <tbody> \n" + "\t\t\t\t <tr class='section'> \n" + "\t\t\t\t\t <td colspan='"
					+ reportSettings.getColumnCount() + "' onclick=\"toggleMenu('" + currentSection + currentInstance
					+ "')\">+ " + section + "</td> \n" + "\t\t\t\t </tr> \n" + "\t\t\t </tbody> \n"
					+ "\t\t\t <tbody id='" + currentSection + "' style='display:table-row-group'> \n";
			bufferedWriter.write(testLogSection);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding section to HTML test log");
		}
	}

	@Override
	public void addTestLogSubSection(String subSection, String currentInstance) {
		currentSubSection = subSection.replaceAll("[^a-zA-Z0-9]", "");
		currentContentNumber = 1;
		this.currentInstance = currentInstance;
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			String testLogSubSection = "\t\t\t\t <tr class='subheading subsection' > \n" + "\t\t\t\t\t <td colspan='"
					+ reportSettings.getColumnCount() + "' onclick=\"toggleSubMenu('" + currentSection + currentInstance
					+ currentSubSection + "')\">&nbsp;+ " + subSection + "</td> \n" + "\t\t\t\t </tr> \n";
			bufferedWriter.write(testLogSubSection);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding sub-section to HTML test log");
		}
	}

	@Override
	public void updateTestLog(String stepNumber, String endPoint, Object expectedValue, Object actualValue,
			Status stepStatus) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			String testStepRow = "\t\t\t\t <tr class='content' id='" + currentSection + currentSubSection
					+ currentContentNumber + "'> \n" + "\t\t\t\t\t <td>" + stepNumber + "</td> \n"
					+ "\t\t\t\t\t <td class='justified'>" + endPoint + "</td> \n";
			currentContentNumber++;

			switch (stepStatus) {
			case PASS:

				testStepRow += getApiTestStep(expectedValue, actualValue, stepStatus);
				break;
			case FAIL:

				testStepRow += getApiTestStep(expectedValue, actualValue, stepStatus);

				break;
			default:
				break;
			}
			bufferedWriter.write(testStepRow);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while updating HTML test log");
		}
	}

	private String getApiTestStep(Object expectedValue, Object actualValue, Status stepStatus) {
		String testStepRow;
		expectedValue = ((Object) expectedValue).toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		actualValue = ((Object) actualValue).toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		testStepRow = "\t\t\t\t\t <td class='justified'>" + expectedValue + "</td> \n"
				+ "\t\t\t\t\t <td class='justified'>" + actualValue + "</td> \n" +

				"\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + stepStatus + "</td> \n"
				+ "\t\t\t\t </tr> \n";

		return testStepRow;
	}

	@Override
	public void updateTestLog(String stepNumber, String stepName, String stepDescription, Status stepStatus,
			String screenShotName) {
		if (screenShotName.contains("Instance1") && NFT.executeVisualValidation) {
			instance1Screenshots.add(screenShotName);
			instance1StepNumber.add(stepNumber);
			instance1StepName.add(stepName);
			instance1StepStatus.add(stepStatus.toString());
		}
		if (screenShotName.contains("Instance2") && NFT.executeVisualValidation) {
			instance2Screenshots.add(screenShotName);
			instance2StepNumber.add(stepNumber);
			instance2StepName.add(stepName);
			instance2StepStatus.add(stepStatus.toString());
		}
		System.out.println(stepName + "^^^^^^^^^^^^^^^^^^^" + screenShotName);

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));

			String testStepRow = "\t\t\t\t <tr class='content' id='" + currentSection + this.currentInstance
					+ currentSubSection + currentContentNumber + "'> \n" + "\t\t\t\t\t <td>" + stepNumber + "</td> \n"
					+ "\t\t\t\t\t <td class='justified'>" + stepName + "</td> \n";
			currentContentNumber++;

			switch (stepStatus) {
			case FAIL:
				if (reportSettings.shouldTakeScreenshotFailedStep()) {
					testStepRow += getTestStepWithScreenshot(stepDescription, stepStatus, screenShotName);
				} else {
					testStepRow += getTestStepWithoutScreenshot(stepDescription, stepStatus);
				}
				break;

			case PASS:
				if (reportSettings.shouldTakeScreenshotPassedStep()) {
					testStepRow += getTestStepWithScreenshot(stepDescription, stepStatus, screenShotName);
				} else {
					testStepRow += getTestStepWithoutScreenshot(stepDescription, stepStatus);
				}
				break;

			case SCREENSHOT:
				testStepRow += getTestStepWithScreenshot(stepDescription, stepStatus, screenShotName);
				break;

			case DONE:
				testStepRow += getTestStepWithoutScreenshot(stepDescription, stepStatus);
				break;

			default:
				testStepRow += getTestStepWithoutScreenshot(stepDescription, stepStatus);
				break;
			}

			bufferedWriter.write(testStepRow);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while updating HTML test log");
		}
	}

	private String getTestStepWithScreenshot(String stepDescription, Status stepStatus, String screenShotName) {
		String testStepRow;
		if (reportSettings.getisWebAutomation()) {
			if (reportSettings.shouldLinkScreenshotsToTestLog()) {
				testStepRow = "\t\t\t\t\t <td class='justified'>" + stepDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + stepStatus
						+ "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n"
						+ "\t\t\t\t\t <td>" + "N/A" + "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n"
						+ "\t\t\t\t\t <td>" + "<small>"
						+ Util.getCurrentFormattedTime(reportSettings.getDateFormatString()) + "</small>" + "</td> \n"
						+ "\t\t\t\t\t <td>" + "<img  src='..\\Screenshots\\" + screenShotName + "'>" + "</img>"
						+ "</td> \n" + "\t\t\t\t </tr> \n";
			} else {
				testStepRow = "\t\t\t\t\t <td class='justified'>" + stepDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + stepStatus
						+ "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n"
						+ "\t\t\t\t\t <td>" + "N/A" + "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n"
						+ "\t\t\t\t\t <td>" + "<small>"
						+ Util.getCurrentFormattedTime(reportSettings.getDateFormatString()) + "</small>" + "</td> \n"
						+ "\t\t\t\t\t <td>" + " (Refer Screenshot @ " + screenShotName + ")" + "</td> \n"
						+ "\t\t\t\t </tr> \n";
			}
		} else {
			if (reportSettings.shouldLinkScreenshotsToTestLog()) {
				testStepRow = "\t\t\t\t\t <td class='justified'>" + stepDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + stepStatus
						+ "</td> \n" + "\t\t\t\t\t <td>" + "<small>"
						+ Util.getCurrentFormattedTime(reportSettings.getDateFormatString()) + "</small>" + "</td> \n"
						+ "\t\t\t\t\t <td>" + "<img class='zoom' src='..\\Screenshots\\" + screenShotName + "'>"
						+ "</img>" + "</td> \n" + "\t\t\t\t </tr> \n";
			} else {
				testStepRow = "\t\t\t\t\t <td class='justified'>" + stepDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + stepStatus
						+ "</td> \n" + "\t\t\t\t\t <td>" + "<small>"
						+ Util.getCurrentFormattedTime(reportSettings.getDateFormatString()) + "</small>" + "</td> \n"
						+ "\t\t\t\t\t <td>" + " (Refer Screenshot @ " + screenShotName + ")" + "</td> \n"
						+ "\t\t\t\t </tr> \n";
			}
		}

		return testStepRow;
	}

	private String getTestStepWithoutScreenshot(String stepDescription, Status stepStatus) {
		String testStepRow;
		if (reportSettings.getisWebAutomation()) {
			testStepRow = "\t\t\t\t\t <td class='justified'>" + stepDescription + "</td> \n" + "\t\t\t\t\t <td class='"
					+ stepStatus.toString().toLowerCase() + "'>" + stepStatus + "</td> \n" + "\t\t\t\t\t <td>" + "N/A"
					+ "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n" + "\t\t\t\t\t <td>" + "N/A" + "</td> \n"
					+ "\t\t\t\t\t <td>" + "N/A" + "</td> \n" + "\t\t\t\t\t <td>" + "<small>"
					+ Util.getCurrentFormattedTime(reportSettings.getDateFormatString()) + "</small>" + "</td> \n"
					+ "\t\t\t\t\t <td>" + " N/A " + "</td> \n" + "\t\t\t\t </tr> \n";
		} else {
			testStepRow = "\t\t\t\t\t <td class='justified'>" + stepDescription + "</td> \n" + "\t\t\t\t\t <td class='"
					+ stepStatus.toString().toLowerCase() + "'>" + stepStatus + "</td> \n" + "\t\t\t\t\t <td>"
					+ "<small>" + Util.getCurrentFormattedTime(reportSettings.getDateFormatString()) + "</small>"
					+ "</td> \n" + "\t\t\t\t\t <td>" + " N/A " + "</td> \n" + "\t\t\t\t </tr> \n";
		}

		return testStepRow;
	}

	@Override
	public void addTestLogFooter(String executionTime, int nStepsPassed, int nStepsFailed) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			String testLogFooter;

			testLogFooter = "\t\t\t </tbody> \n" + "\t\t </table> \n\n" + "\t\t <table id='footer'> \n"
					+ "\t\t\t <colgroup> \n" + "\t\t\t\t <col style='width: 25%' /> \n"
					+ "\t\t\t\t <col style='width: 25%' /> \n" + "\t\t\t\t <col style='width: 25%' /> \n"
					+ "\t\t\t\t <col style='width: 25%' /> \n" + "\t\t\t </colgroup> \n\n" + "\t\t\t <tfoot> \n"
					+ "\t\t\t\t <tr class='heading'> \n" + "\t\t\t\t\t <th colspan='4'>Execution Duration: "
					+ executionTime + "</th> \n" + "\t\t\t\t </tr> \n" + "\t\t\t\t <tr class='subheading'> \n"
					+ "\t\t\t\t\t <td class='pass'>&nbsp;Steps passed</td> \n" + "\t\t\t\t\t <td class='pass'>&nbsp;: "
					+ nStepsPassed + "</td> \n" + "\t\t\t\t\t <td class='fail'>&nbsp;Steps failed</td> \n"
					+ "\t\t\t\t\t <td class='fail'>&nbsp;: " + nStepsFailed + "</td> \n" + "\t\t\t\t </tr> \n"
					+ "\t\t\t </tfoot> \n" + "\t\t </table> \n" + "\t </body> \n" + "</html>";

			bufferedWriter.write(testLogFooter);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding footer to HTML test log");
		}
	}

	/* RESULT SUMMARY FUNCTIONS */

	@Override
	public void initializeResultSummary() {
		File resultSummaryFile = new File(resultSummaryPath);

		try {
			resultSummaryFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while creating HTML result summary file");
		}

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(resultSummaryFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("Cannot find HTML result summary file");
		}
		PrintStream printStream = new PrintStream(outputStream);

		String resultSummaryHeader;
		resultSummaryHeader = "<!DOCTYPE html> \n" + "<html> \n" + "\t <head> \n" + "\t\t <meta charset='UTF-8'> \n"
				+ "\t\t <title>" + reportSettings.getProjectName() + " - Automation Execution Results Summary"
				+ "</title> \n\n" + getThemeCss2() + getJavascriptFunctions() + "\t </head> \n";

		printStream.println(resultSummaryHeader);
		printStream.close();
	}

	@Override
	public void addResultSummaryHeading(String heading) {
		if (!isResultSummaryHeaderTableCreated) {
			createResultSummaryHeaderTable();
			isResultSummaryHeaderTableCreated = true;
		}

		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));

			String resultSummaryHeading = "\t\t\t\t <tr class='heading'> \n"
					+ "\t\t\t\t\t <th colspan='4' style='font-family:Copperplate Gothic; font-size:1.4em;'> \n"
					+ "\t\t\t\t\t\t " + heading + " \n" + "\t\t\t\t\t </th> \n" + "\t\t\t\t </tr> \n";
			bufferedWriter.write(resultSummaryHeading);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding heading to HTML result summary");
		}
	}

	private void createResultSummaryHeaderTable() {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));

			String resultSummaryHeaderTable = "\t <body> \n" + "\t\t <table id='header'> \n" + "\t\t\t <thead> \n";
			bufferedWriter.write(resultSummaryHeaderTable);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding header table to HTML result summary");
		}
	}

	@Override
	public void addResultSummarySubHeading(String subHeading1, String subHeading2, String subHeading3,
			String subHeading4) {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));

			String resultSummarySubHeading = "\t\t\t\t <tr class='subheading'> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading1.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading2.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading3.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t\t <th>&nbsp;"
					+ subHeading4.replace(" ", "&nbsp;") + "</th> \n" + "\t\t\t\t </tr> \n";
			bufferedWriter.write(resultSummarySubHeading);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding sub-heading to HTML result summary");
		}
	}

	private void createResultSummaryMainTable() {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));

			String resultSummaryMainTable = "\t\t\t </thead> \n" + "\t\t </table> \n\n" +

					"\t\t <table id='main'> \n" + "\t\t\t <colgroup> \n";

			bufferedWriter.write(resultSummaryMainTable);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding main table to HTML result summary");
		}
	}

	@Override
	public void addResultSummaryTableHeadings(String cloudNM) {
		if (!isResultSummaryMainTableCreated) {
			createResultSummaryMainTable();
			isResultSummaryMainTableCreated = true;
		}

		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));
			String resultSummaryTableHeading;
			if(cloudNM.equals("Cloud Native Application")) {
				resultSummaryTableHeading = "\t\t\t <thead> \n" + "\t\t\t\t <tr class='heading'> \n"
						+ "\t\t\t\t\t <th>Test Scenario</th> \n" + "\t\t\t\t\t <th>Test Case</th> \n"
						+ "\t\t\t\t\t <th>Test Instance</th> \n" + "\t\t\t\t\t <th>Test Description</th> \n"
						+ "\t\t\t\t\t <th>Additional Details</th> \n" + "\t\t\t\t\t <th>Execution Time</th> \n"
						+ "\t\t\t\t\t <th>Test Status</th> \n" + "\t\t\t\t </tr> \n" + "\t\t\t </thead> \n\n";
			}else {
				resultSummaryTableHeading = "\t\t\t <thead> \n" + "\t\t\t\t <tr class='heading'> \n"
						+ "\t\t\t\t\t <th>Test Scenario</th> \n" + "\t\t\t\t\t <th>Test Case</th> \n"
						+ "\t\t\t\t\t <th>Test Instance</th> \n" + "\t\t\t\t\t <th>Test Description</th> \n"
						+ "\t\t\t\t\t <th>Additional Details</th> \n" + "\t\t\t\t\t <th>Execution Time</th> \n"
						+ "\t\t\t\t\t <th>Test Status</th> \n" + "\t\t\t\t\t <th>Comparison Link</th> \n"
						+ "\t\t\t\t </tr> \n" + "\t\t\t </thead> \n\n";
			}
			
			bufferedWriter.write(resultSummaryTableHeading);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding main table headings to HTML result summary");
		}
	}

	@Override
	public void updateComparisonResultSummary(String cloudNM,TestParameters testParameters1, String testReportName1,
			String executionTime1, String testStatus1, TestParameters testParameters2, String testReportName2,
			String executionTime2, String testStatus2) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));

			String testcaseRow;
			String scenarioName = testParameters1.getCurrentScenario();
			String testcaseName = testParameters1.getCurrentTestcase();
			String testInstanceName = testParameters1.getCurrentTestInstance();
			String testcaseDescription = testParameters1.getCurrentTestDescription();
			String additionalDetails = testParameters1.getAdditionalDetails();

			if (reportSettings.shouldLinkTestLogsToSummary()) {
				testcaseRow = "\t\t\t\t <tr class='content' > \n" + "\t\t\t\t\t <td class='justified'>" + scenarioName
						+ "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'><a href='" + testReportName1 + ".html' "
						+ "target='about_blank'>" + testInstanceName + "</a>" + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + additionalDetails + "</td> \n" + "\t\t\t\t\t <td>"
						+ executionTime1 + "</td> \n";
			} else {
				testcaseRow = "\t\t\t\t <tr class='content' > \n" + "\t\t\t\t\t <td class='justified'>" + scenarioName
						+ "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testInstanceName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + additionalDetails + "</td> \n" + "\t\t\t\t\t <td>"
						+ executionTime1 + "</td> \n";
			}

			if ("passed".equalsIgnoreCase(testStatus1)) {
				testcaseRow += "\t\t\t\t\t <td class='pass'>" + testStatus1 + "</td> \n";
			} else {
				testcaseRow += "\t\t\t\t\t <td class='fail'>" + testStatus1 + "</td> \n";
			}
			
			if(cloudNM.equals("Cloud Native Application")) {
				// don't add comparison link
			}else {
				testcaseRow += "\t\t\t\t\t <td  rowspan='2' class='justified'><a href='./"
						+ testParameters1.getCurrentScenario() + "_" + testParameters1.getCurrentTestcase()
						+ "_comparison.html' " + "target='about_blank'>link</a>" + "</td> \n" + "\t\t\t\t </tr> \n";
			}
			

			scenarioName = testParameters2.getCurrentScenario();
			testcaseName = testParameters2.getCurrentTestcase();
			testInstanceName = testParameters2.getCurrentTestInstance();
			testcaseDescription = testParameters2.getCurrentTestDescription();
			additionalDetails = testParameters2.getAdditionalDetails();

			if (reportSettings.shouldLinkTestLogsToSummary()) {
				testcaseRow += "\t\t\t\t <tr class='content' > \n" + "\t\t\t\t\t <td class='justified'>" + scenarioName
						+ "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'><a href='" + testReportName2 + ".html' "
						+ "target='about_blank'>" + testInstanceName + "</a>" + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + additionalDetails + "</td> \n" + "\t\t\t\t\t <td>"
						+ executionTime1 + "</td> \n";
			} else {
				testcaseRow = "\t\t\t\t <tr class='content' > \n" + "\t\t\t\t\t <td class='justified'>" + scenarioName
						+ "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testInstanceName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + additionalDetails + "</td> \n" + "\t\t\t\t\t <td>"
						+ executionTime2 + "</td> \n";
			}

			if ("passed".equalsIgnoreCase(testStatus2)) {
				testcaseRow += "\t\t\t\t\t <td class='pass'>" + testStatus2 + "</td> \n";
			} else {
				testcaseRow += "\t\t\t\t\t <td class='fail'>" + testStatus2 + "</td> \n";
			}

			bufferedWriter.write(testcaseRow);

			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while updating HTML result summary");
		}
	}

	@Override
	public void updateResultSummary(TestParameters testParameters, String testReportName, String executionTime,
			String testStatus) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));

			String testcaseRow;
			String scenarioName = testParameters.getCurrentScenario();
			String testcaseName = testParameters.getCurrentTestcase();
			String testInstanceName = testParameters.getCurrentTestInstance();
			String testcaseDescription = testParameters.getCurrentTestDescription();
			String additionalDetails = testParameters.getAdditionalDetails();

			if (reportSettings.shouldLinkTestLogsToSummary()) {
				testcaseRow = "\t\t\t\t <tr class='content' > \n" + "\t\t\t\t\t <td class='justified'>" + scenarioName
						+ "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'><a href='" + testReportName + ".html' "
						+ "target='about_blank'>" + testInstanceName + "</a>" + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + additionalDetails + "</td> \n" + "\t\t\t\t\t <td>"
						+ executionTime + "</td> \n";
			} else {
				testcaseRow = "\t\t\t\t <tr class='content' > \n" + "\t\t\t\t\t <td class='justified'>" + scenarioName
						+ "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testInstanceName + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n"
						+ "\t\t\t\t\t <td class='justified'>" + additionalDetails + "</td> \n" + "\t\t\t\t\t <td>"
						+ executionTime + "</td> \n";
			}

			if ("passed".equalsIgnoreCase(testStatus)) {
				testcaseRow += "\t\t\t\t\t <td class='pass'>" + testStatus + "</td> \n";
			} else {
				testcaseRow += "\t\t\t\t\t <td class='fail'>" + testStatus + "</td> \n";
			}

			bufferedWriter.write(testcaseRow);

			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while updating HTML result summary");
		}
	}

	@Override
	public void addResultSummaryFooter(String totalExecutionTime, int nTestsPassed, int nTestsFailed) {

		try {
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(resultSummaryPath, true));
			String resultSummaryFooter;

			resultSummaryFooter = "\t\t\t </tbody> \n" + "\t\t </table> \n\n" + "\t\t <table id='footer'> \n"
					+ "\t\t\t <colgroup> \n" + "\t\t\t\t <col style='width: 25%' /> \n"
					+ "\t\t\t\t <col style='width: 25%' /> \n" + "\t\t\t\t <col style='width: 25%' /> \n"
					+ "\t\t\t\t <col style='width: 25%' /> \n" + "\t\t\t </colgroup> \n\n" + "\t\t\t <tfoot> \n"
					+ "\t\t\t\t <tr class='heading'> \n" + "\t\t\t\t\t <th colspan='4'>Total Duration: "
					+ totalExecutionTime + "</th> \n" + "\t\t\t\t </tr> \n" + "\t\t\t\t <tr class='subheading'> \n"
					+ "\t\t\t\t\t <td class='pass'>&nbsp;Tests passed</td> \n" + "\t\t\t\t\t <td class='pass'>&nbsp;: "
					+ nTestsPassed + "</td> \n" + "\t\t\t\t\t <td class='fail'>&nbsp;Tests failed</td> \n"
					+ "\t\t\t\t\t <td class='fail'>&nbsp;: " + nTestsFailed + "</td> \n" + "\t\t\t\t </tr> \n"
					+ "\t\t\t </tfoot> \n" + "\t\t </table> \n" + "\t </body> \n" + "</html>";

			bufferWriter.write(resultSummaryFooter);
			bufferWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding footer to HTML result summary");
		}
	}

}