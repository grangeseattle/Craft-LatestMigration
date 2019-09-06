package com.cognizant.framework.healing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Report;
import com.cognizant.framework.Settings;
import com.cognizant.framework.Status;
import com.cognizant.framework.selenium.SeleniumTestParameters;

public abstract class ObjectFortify {

	protected By userDefinedLocator;
	protected String packageName;
	protected String className;
	protected String methodName;
	protected int lineNumber;
	protected Exception exception;
	protected WebDriver driver;
	protected FrameworkParameters frameworkParameters;
	protected SeleniumTestParameters testParameters;
	protected Report report;
	protected By healedLocator = null;

	public abstract WebElement heal();

	public ObjectFortify(Exception exception, By userDefinedLocator, String className, String methodName,
			int lineNumber, String packageName, WebDriver driver, FrameworkParameters frameworkParameters,
			SeleniumTestParameters testParameters, Report report) {

		this.userDefinedLocator = userDefinedLocator;
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
		this.exception = exception;
		this.driver = driver;
		this.frameworkParameters = frameworkParameters;
		this.testParameters = testParameters;
		this.report = report;

	}

	protected WebElement invokeHealingProcess() {

		WebElement element = null;

		try {

			List<String> variableName = GetClassNMethodNames.getVaribleName(lineNumber, className, methodName,
					frameworkParameters.getRelativePath(), packageName);

			String uniqueVariableName = variableName.get(0).toUpperCase() + "_" + variableName.get(1).toUpperCase();

			RetriveDataFromDB dataFromDB = new RetriveDataFromDB(driver, uniqueVariableName, testParameters);

			element = launchTierStaging(dataFromDB);

			updateReport(element);

			updateLocatorsToDB(element);

			highLighting(element);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FrameworkException("Healed Failed",
					"Couldn't heal this element, please inspect element accordingly & try again");
		}
		return element;
	}

	private WebElement launchTierStaging(RetriveDataFromDB dataFromDB) {
		WebElement element = null;

		Tier1Healing tier1Healing = new Tier1Healing(driver, dataFromDB.getJSoupDocument());
		element = tier1Healing.invokeTier1Process(dataFromDB.alternateLocators);

		if (element != null) {
			healedLocator = tier1Healing.getHealedLocator();
			return element;
		}

		/*
		 * Once Tier7 Logic is Tested, we can replace this with Tier2 & Tier3 and reduce
		 * total Stages to 5
		 */

		/*
		 * Tier7Healing tier7Healing = new Tier7Healing(driver,
		 * dataFromDB.getJSoupDocument()); element =
		 * tier7Healing.invokeTier7Process(dataFromDB.siblingsLocators,
		 * dataFromDB.neighbourLocators, dataFromDB.alternateLocators);
		 * 
		 * if (element != null) { return element; }
		 */

		Tier2Healing tier2Healing = new Tier2Healing(driver, dataFromDB.getJSoupDocument());

		element = tier2Healing.invokeTier2Process(dataFromDB.neighbourLocators);

		if (element != null) {
			healedLocator = tier2Healing.getHealedLocator();
			return element;
		}

		Tier3Healing tier3Healing = new Tier3Healing(driver, dataFromDB.getJSoupDocument());

		element = tier3Healing.invokeTier3Process(dataFromDB.siblingsLocators);

		if (element != null) {
			healedLocator = tier3Healing.getHealedLocator();
			return element;
		}

		Tier4Healing tier4Healing = new Tier4Healing(driver, dataFromDB.alternateLocators, dataFromDB.neighbourLocators,
				dataFromDB.siblingsLocators, dataFromDB.getJSoupDocument());
		element = tier4Healing.invokeParsingAlgorithm();

		if (element != null) {
			return element;
		}

		Tier5Healing tier5Healing = new Tier5Healing(driver);

		element = tier5Healing.invokeTier5Process(dataFromDB.coordinateLocators, GetClassNMethodNames
				.getCodeLine(lineNumber, className, methodName, frameworkParameters.getRelativePath(), packageName));

		if (element != null) {
			return element;
		}

		Tier6Healing tier6Healing = new Tier6Healing(driver, dataFromDB.getJSoupDocument());
		element = tier6Healing.invokeTier6Process(dataFromDB.luckyLocators);

		if (element != null) {
			healedLocator = tier6Healing.getHealedLocator();
			return element;
		}

		return element;
	}

	private void updateLocatorsToDB(WebElement element) {
		if (healedLocator != null) {
			CaptureDOMEngine cap = new CaptureDOMEngine(healedLocator, className, methodName, lineNumber,
					testParameters, driver, frameworkParameters, packageName, element);
			cap.captureSecondaryObjects();

			updateCode();
		}
	}

	private void updateCode() {
		java.util.Properties properties = Settings.getInstance();

		if (Boolean.parseBoolean(properties.getProperty("UpdateCodeWithNewLocators"))) {
			try {
				UpdateLocatorInCode.findPageNUpdate(lineNumber, className, healedLocator,
						frameworkParameters.getRelativePath(), packageName);
				report.updateTestLog("Update File", "Updated File </br>" + "Class Name : " + className
						+ " </br> MethodName: " + methodName + "</br> Line Number " + lineNumber, Status.DONE);
			} catch (Exception e) {
				report.updateTestLog(
						"Update File Issue", "Please update manually </br>" + "Class Name : " + className
								+ " </br> MethodName: " + methodName + "</br> Line Number " + lineNumber,
						Status.WARNING);
			}

		}
	}

	/**
	 * @param element
	 * @throws InterruptedException
	 */
	private void highLighting(WebElement element) throws InterruptedException {
		if (element != null) {
			JavascriptExecutor js = highLightElement(element);

			Thread.sleep(500);

			unHighLightElement(element, js);
		}
	}

	/**
	 * @param element
	 * @param js
	 */
	private void unHighLightElement(WebElement element, JavascriptExecutor js) {
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
	}

	/**
	 * @param element
	 * @return
	 */
	private JavascriptExecutor highLightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
				"color: red; border: 2px solid red;");
		return js;
	}

	private void updateReport(WebElement element) {
		if (element != null & healedLocator != null) {
			report.updateTestLog("Healed Locator", "Healed for new Locator <br/>" + healedLocator, Status.DONE);
		} else if (element != null & healedLocator == null) {
			report.updateTestLog("Healed Locator", "Healed Sucessfully", Status.DONE);
		}
	}

}
