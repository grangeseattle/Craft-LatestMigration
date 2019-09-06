package com.cognizant.framework.healing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Report;
import com.cognizant.framework.Status;
import com.cognizant.framework.Util;
import com.cognizant.framework.selenium.SeleniumReport;
import com.cognizant.framework.selenium.SeleniumTestParameters;

public class ObjectMachinate {

	private WebDriver driver;
	private FrameworkParameters frameworkParameters;
	private SeleniumTestParameters testParameters;
	private Report report;

	public ObjectMachinate(SeleniumReport report, WebDriver driver, FrameworkParameters frameworkParameters,
			SeleniumTestParameters testParameters) {

		this.report = report;
		this.driver = driver;
		this.frameworkParameters = frameworkParameters;
		this.testParameters = testParameters;
	}

	public WebElement healObject(By userDefinedLocator) {

		WebElement element;

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String fullClassName = stackTrace[3].getClassName();
		String packageName = fullClassName.split("\\.")[0];
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = stackTrace[3].getMethodName();
		int lineNumber = stackTrace[3].getLineNumber();

		if ((frameworkParameters.getStartCapturingObjects() || frameworkParameters.getHealObject())
				&& (!avoidHeal(packageName, className, lineNumber))) {

			element = getHealedElement(userDefinedLocator, className, methodName, lineNumber, packageName);

		} else {
			element = driver.findElement(userDefinedLocator);
		}

		return element;
	}

	private WebElement getHealedElement(By userDefinedLocator, String className, String methodName, int lineNumber,
			String packageName) {

		WebElement element = null;

		try {

			element = driver.findElement(userDefinedLocator);
			turnOffImplicitWaits();
			WebElement referenceElement = driver.findElement(userDefinedLocator);
			CaptureDOMEngine cap = new CaptureDOMEngine(userDefinedLocator, className, methodName, lineNumber,
					testParameters, driver, frameworkParameters, packageName, referenceElement);
			cap.captureSecondaryObjects();
			turnOnImplicitWaits();

		} catch (Exception exception) {

			if (frameworkParameters.getHealObject()) {

				report.updateTestLog("Object Identification Failed",
						" Healing started for  :" + "<br/> Object : " + userDefinedLocator, Status.WARNING);

				ObjectFortify healer = HealingEngine.getHealer(exception, userDefinedLocator, className, methodName,
						lineNumber, packageName, driver, frameworkParameters, testParameters, report);
				element = healer.heal();

			} else {
				element = driver.findElement(userDefinedLocator);
			}

		}

		return element;
	}

	private boolean avoidHeal(String packageName, String className, int lineNumber) {

		boolean avoidHealing = false;
		String code;
		String aboveLine;

		if (frameworkParameters.getForceHeal()) {
			return false;
		}

		try {
			code = Files.readAllLines(Paths.get(frameworkParameters.getRelativePath() + Util.getFileSeparator()
					+ packageName + Util.getFileSeparator() + className + ".java")).get(lineNumber - 1);

			aboveLine = Files.readAllLines(Paths.get(frameworkParameters.getRelativePath() + Util.getFileSeparator()
					+ packageName + Util.getFileSeparator() + className + ".java")).get(lineNumber - 2);

			if ((code.contains("isDisplayed") || code.contains("isEnabled") || code.contains("isSelected"))
					|| (aboveLine.contains("Avoid"))) {
				avoidHealing = true;
			} else {
				avoidHealing = false;
			}

		} catch (Exception e) {

		}

		return avoidHealing;
	}

	private void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	private void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

}
