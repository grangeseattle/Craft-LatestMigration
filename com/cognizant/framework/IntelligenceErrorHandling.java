package com.cognizant.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IntelligenceErrorHandling {

	@SuppressWarnings("resource")
	public String captureErrorFromErroLog(String reportPath) {

		String failureReason = null;
		File file = new File(reportPath + "\\ErrorLog.txt");

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("Exception")) {
					failureReason = getIntelligentError(line);
					break;
				} else {
					failureReason = "NA";
				}
			}
		} catch (FileNotFoundException e) {
		}

		return failureReason;
	}

	@SuppressWarnings("resource")
	public String captureErrorFromErroLog(Exception ex) {
		String failureReason = null;
		try {
			Scanner scanner = new Scanner(ex.toString());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("Exception")) {
					failureReason = getIntelligentError(line);
					break;
				} else {
					failureReason = "NA";
				}
			}
		} catch (Exception e) {
		}

		return failureReason;

	}

	public String getIntelligentError(String line) {

		String errorType;

		if (checkIfFrameworkException(line)) {

			errorType = ErrorTypes.Framework_Error.toString();

		} else if (checkIfElementException(line)) {

			errorType = ErrorTypes.ElementIdentify_Error.toString();

		} else if (checkIfToolException(line)) {

			errorType = ErrorTypes.Tool_Error.toString();

		} else if (checkIfApplicationException(line)) {

			errorType = ErrorTypes.Application_Error.toString();

		} else if (checkIfJavaError(line)) {

			errorType = ErrorTypes.Java_Error.toString();

		} else {

			errorType = ErrorTypes.Others.toString();

		}

		return errorType;
	}

	private boolean checkIfFrameworkException(String line) {
		boolean isFrameworkError = false;
		if (line.contains("com.cognizant.framework")
				|| line.contains("not found within any class inside the businesscomponents package")
				|| line.contains("Unhandled OnError option!")
				|| line.contains("Unhandled Execution Mode!")
				|| line.contains("The data reference identifier must be a single character")
				|| line.contains("CraftDataTable.currentTestCase is not set!")
				|| line.contains("CraftDataTable.currentIteration is not set!")
				|| line.contains("CraftDataTable.currentSubIteration is not set!")
				|| line.contains("is not found in the test data sheet")
				|| line.contains("is not found in the common test data sheet!")
				|| line.contains("ExcelDataAccess.datasheetName is not set!")
				|| line.contains(" does not exist!")
				|| line.contains("Error while opening the specified Excel workbook")
				|| line.contains("Error while writing into the specified Excel workbook")
				|| line.contains("The specified column header")
				|| line.contains("The specified column header")
				|| line.contains("Specified cell is empty! ")
				|| line.contains("The specified test case is not found!")
				|| line.contains("Error while instantiating the specified test script")
				|| line.contains("is not found in the Business Flow sheet!")) {

		}
		return isFrameworkError;
	}

	private boolean checkIfElementException(String line) {
		boolean isElementError = false;
		if (line.contains("ElementNotSelectableException")
				|| line.contains("ElementNotVisibleException")
				|| line.contains("InvalidSelectorException")
				|| line.contains("InvalidSwitchToTargetException")
				|| line.contains("NoAlertPresentException")
				|| line.contains("NoSuchAttributeException")
				|| line.contains("NoSuchElementException")
				|| line.contains("NoSuchFrameException")
				|| line.contains("NoSuchWindowException")
				|| line.contains("StaleElementReferenceException")
				|| line.contains("UnexpectedAlertPresentException")
				|| line.contains("UnexpectedTagNameException")
				|| line.contains("Exception caught while executing click")
				|| line.contains("List could not be identified")) {
			isElementError = true;
		}
		return isElementError;
	}

	private boolean checkIfToolException(String line) {
		boolean isToolError = false;
		if (line.contains("WebDriverException")
				|| line.contains("RemoteDriverServerException")
				|| line.contains("The driver executable does not exist")
				|| line.contains("Exception caught while executing setDevice")
				|| line.contains("Exception caught while executing launch")
				|| line.contains("The device is offline")
				|| line.contains("Object repository doesn't contain")
				|| line.contains("Failed to execute command webpage info")
				|| line.contains("Install_Failed_Insufficient_Storage")
				|| line.contains("Could not start a new session")
				|| line.contains("connection refused")
				|| line.contains("A new session could not be created")
				|| line.contains("org.openqa.selenium.remote.SessionNotFoundException")
				|| line.contains("org.openqa.selenium.remote.UnreachableBrowserException")
				|| line.contains("Could not start a new session. Possible causes are invalid address of the remote server or browser start-up failure")
				|| line.contains("Not yet implemented")
				|| line.contains("org.openqa.selenium.UnsupportedCommandException")
				|| line.contains("org.openqa.selenium.InvalidSelectorException")
				|| line.contains("Could not initialize ideviceinstaller")
				|| line.contains("An unknown server-side error occurred while processing the command")) {
			isToolError = true;
		}
		return isToolError;
	}

	private boolean checkIfApplicationException(String line) {
		boolean isApplicationError = false;
		if (line.contains("TimeoutException")
				|| line.contains("ErrorInResponseException")
				|| line.contains("Invalid activity value")
				|| line.contains("could not retrieve objects")
				|| line.contains("Install completed with error")) {
			isApplicationError = true;
		}
		return isApplicationError;
	}

	private boolean checkIfJavaError(String line) {
		boolean ifJavaError = false;
		if (line.contains("java.lang.NullPointerException")
				|| line.contains("java.lang.NoClassDefFoundError")) {
			ifJavaError = true;
		}
		return ifJavaError;
	}

}
