package com.cognizant.craft;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.cognizant.framework.APIReusuableLibrary;
import com.cognizant.framework.CraftDataTable;
import com.cognizant.framework.selenium.CraftDriver;
import com.cognizant.framework.selenium.SeleniumReport;
import com.cognizant.framework.selenium.WebDriverUtil;

/**
 * Wrapper class for common framework objects, to be used across the entire test
 * case and dependent libraries
 * 
 * @author Cognizant
 */
public class ScriptHelper {

	private final CraftDataTable dataTable;
	private final SeleniumReport report;
	private CraftDriver craftDriver;
	private WebDriverUtil driverUtil;
	private APIReusuableLibrary apiDriver;
	private ExtentTest extentTest;
	private Map<String, String> reusableHandle;

	/**
	 * Constructor to initialize all the objects wrapped by the
	 * {@link ScriptHelper} class
	 * 
	 * @param dataTable
	 *            The {@link CraftDataTable} object
	 * @param report
	 *            The {@link SeleniumReport} object
	 * @param craftDriver
	 *            The {@link CraftDriver} object
	 * @param driverUtil
	 *            The {@link WebDriverUtil} object
	 * @param apiDriver
	 *            The reusable API driver {@link APIReusuableLibrary }
	 * @param extentTest
	 *            The extent Test object{@link ExtentTest }
	 * @param reusableHandle
	 *            The Reusable handle
	 */

	public ScriptHelper(CraftDataTable dataTable, SeleniumReport report, CraftDriver craftDriver,
			WebDriverUtil driverUtil, APIReusuableLibrary apiDriver, ExtentTest extentTest,
			Map<String, String> reusableHandle) {
		this.dataTable = dataTable;
		this.report = report;
		this.craftDriver = craftDriver;
		this.driverUtil = driverUtil;
		this.apiDriver = apiDriver;
		this.extentTest = extentTest;
		this.reusableHandle = reusableHandle;
        
        
	}
	
	public String getTestInstance()
	{
		return report.getSeleniumTestParameters().getCurrentTestInstance();
	}

	/**
	 * Function to get the {@link CraftDataTable} object
	 * 
	 * @return The {@link CraftDataTable} object
	 */
	public CraftDataTable getDataTable() {
		return dataTable;
	}

	/**
	 * Function to get the {@link SeleniumReport} object
	 * 
	 * @return The {@link SeleniumReport} object
	 */
	public SeleniumReport getReport() {
		return report;
	}

	/**
	 * Function to get the {@link CraftDriver} object
	 * 
	 * @return The {@link CraftDriver} object
	 */
	public CraftDriver getcraftDriver() {
		return craftDriver;
	}

	/**
	 * Function to get the {@link WebDriverUtil} object
	 * 
	 * @return The {@link WebDriverUtil} object
	 */
	public WebDriverUtil getDriverUtil() {
		return driverUtil;
	}

	/**
	 * Function to get the {@link APIReusuableLibrary} object
	 * 
	 * @return The {@link APIReusuableLibrary} object
	 */
	public APIReusuableLibrary getApiDriver() {
		return apiDriver;
	}

	/**
	 * Function to get the {@link ExtentTest} object
	 * 
	 * @return The {@link ExtentTest} object
	 */
	public ExtentTest getExtentTest() {
		return extentTest;
	}

	/**
	 * Function to maintain any Key value pairs within Test case execution
	 * 
	 * @return The Key Value Pairs
	 */
	public Map<String, String> getReusablehandle() {
		return reusableHandle;
	}
}