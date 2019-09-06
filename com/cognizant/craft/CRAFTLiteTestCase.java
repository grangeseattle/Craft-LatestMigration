package com.cognizant.craft;

import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cognizant.framework.APIReusuableLibrary;
import com.cognizant.framework.CraftDataTable;
import com.cognizant.framework.DataBaseOperation;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;
import com.cognizant.framework.TestCaseBean;
import com.cognizant.framework.Util;
import com.cognizant.framework.selenium.CraftDriver;
import com.cognizant.framework.selenium.ResultSummaryManager;
import com.cognizant.framework.selenium.SeleniumReport;
import com.cognizant.framework.selenium.SeleniumTestParameters;
import com.cognizant.framework.selenium.WebDriverUtil;

/**
 * Abstract base class for all the test cases to be automated
 * 
 * @author Cognizant
 */
public abstract class CRAFTLiteTestCase {

	protected static ExtentHtmlReporter htmlReporter;
	protected static ExtentReports extentReport;

	/**
	 * The current scenario
	 */
	protected String currentScenario;
	/**
	 * The current test case
	 */
	protected String currentTestcase;

	private ResultSummaryManager resultSummaryManager = ResultSummaryManager.getInstance();

	/**
	 * The {@link CraftliteDataTable} object (passed from the Driver script)
	 */
	protected CraftDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the Driver script)
	 */
	protected SeleniumReport report;
	/**
	 * The {@link CraftDriver} object (passed from the Driver script)
	 */
	protected CraftDriver driver;
	/**
	 * The {@link WebDriverUtil} object (passed from the Driver script)
	 */
	protected WebDriverUtil driverUtil;

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable
	 * library from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();

	/**
	 * The {@link APIReusuableLibrary} object
	 */
	protected APIReusuableLibrary apiDriver;

	/**
	 * The {@link ExtentTest} object
	 */
	protected ExtentTest extentTest;

	/**
	 * The {@link ExtentTest} object Reusable method to set Key & value and can
	 * be used within test case
	 */
	protected Map<String, String> reusableHandle;

	/**
	 * Function to initialize the various objects that may beed to be used with
	 * a test script
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object
	 */
	public void initialize(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		this.driver = scriptHelper.getcraftDriver();
		this.driverUtil = scriptHelper.getDriverUtil();
		this.apiDriver = scriptHelper.getApiDriver();
		this.extentTest = scriptHelper.getExtentTest();
		this.reusableHandle = scriptHelper.getReusablehandle();

		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
	}

	/**
	 * Function to do the required framework setup activities before executing
	 * the overall test suite
	 * 
	 * @param testContext
	 *            The TestNG {@link ITestContext} of the current test suite
	 */
	@BeforeSuite
	public void setUpTestSuite(ITestContext testContext) {
		resultSummaryManager.setRelativePath();
		resultSummaryManager.initializeTestBatch(testContext.getSuite().getName());

		int nThreads;
		if ("false".equalsIgnoreCase(testContext.getSuite().getParallel())) {
			nThreads = 1;
		} else {
			nThreads = testContext.getCurrentXmlTest().getThreadCount();
		}

		// Note: Separate threads may be spawned through usage of DataProvider
		// testContext.getSuite().getXmlSuite().getDataProviderThreadCount();
		// This will be at test case level (multiple instances on same test case
		// in parallel)
		// This level of threading will not be reflected in the summary report

		resultSummaryManager.initializeSummaryReport(nThreads,"");
		resultSummaryManager.setupErrorLog();
		generateExtentReports();
	}

	/**
	 * Function to do the required framework setup activities before executing
	 * each test case
	 */
	@BeforeMethod
	public void setUpTestRunner() {
		if (frameworkParameters.getStopExecution()) {
			tearDownTestSuite();

			// Throwing TestNG SkipException within a configuration method
			// causes all subsequent test methods to be skipped/aborted
			throw new SkipException("Aborting all subsequent tests!");
		}
	}

	/**
	 * Function to handle any pre-requisite steps required before beginning the
	 * test case execution <br>
	 * <u>Note</u>: This function can be left blank if not applicable
	 */
	public abstract void setUp();

	/**
	 * Function to handle the core test steps required as part of the test case
	 */
	public abstract void executeTest();

	/**
	 * Function to handle any clean-up steps required after completing the test
	 * case execution <br>
	 * <u>Note</u>: This function can be left blank if not applicable
	 */
	public abstract void tearDown();

	/**
	 * Function to do the required framework teardown activities after executing
	 * each test case
	 * 
	 * @param testParameters
	 *            The {@link SeleniumTestParameters} object passed from the test
	 *            case
	 * @param driverScript
	 *            The {@link DriverScript} object passed from the test case
	 */
	protected synchronized void tearDownTestRunner(SeleniumTestParameters testParameters, DriverScript driverScript) {
		TestCaseBean testCaseBean = new TestCaseBean();
		String testReportName = driverScript.getReportName();
		String executionTime = driverScript.getExecutionTime();
		String testStatus = driverScript.getTestStatus();

		resultSummaryManager.updateResultSummary(testParameters, testReportName, executionTime, testStatus);
		/* DB-Updating reports to database */
		DataBaseOperation dbOperation = new DataBaseOperation();
		dbOperation.initializeTestParameters(testParameters);
		dbOperation.updateMongoDB("Run Manager", testCaseBean, executionTime, testStatus);

		if ("Failed".equalsIgnoreCase(testStatus)) {
			Assert.fail(driverScript.getFailureDescription());
		}
	}

	/**
	 * Function to do the required framework teardown activities after executing
	 * the overall test suite
	 */
	@AfterSuite
	public void tearDownTestSuite() {
		resultSummaryManager.wrapUp(true);
		extentReport.flush();
		resultSummaryManager.launchResultSummary();
	}

	/**
	 * Function to set Extent Report Path within Framework and initialze extent
	 * objects
	 */
	private void generateExtentReports() {
		Properties properties = Settings.getInstance();
		htmlReporter = new ExtentHtmlReporter(resultSummaryManager.getReportPath() + Util.getFileSeparator()
				+ "Extent Result" + Util.getFileSeparator() + "ExtentReport.html");
		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);
		extentReport.setSystemInfo("Project Name", properties.getProperty("ProjectName"));
		extentReport.setSystemInfo("Framework", "CRAFT Classic");
		extentReport.setSystemInfo("Framework Version", "3.2");
		extentReport.setSystemInfo("Author", "Cognizant");

		htmlReporter.config().setDocumentTitle("CRAFT Extent Report");
		htmlReporter.config().setReportName("Extent Report for CRAFT");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
	}
}