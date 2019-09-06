package com.cognizant.framework.healing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Report;
import com.cognizant.framework.selenium.SeleniumTestParameters;

public class TimeoutHealer extends ObjectFortify {

	public TimeoutHealer(Exception exception, By userDefinedLocator, String className, String methodName,
			int lineNumber, String packageName, WebDriver driver, FrameworkParameters frameworkParameters,
			SeleniumTestParameters testParameters, Report report) {
		super(exception, userDefinedLocator, className, methodName, lineNumber, packageName, driver,
				frameworkParameters, testParameters, report);
	}

	@Override
	public WebElement heal() {
		WebElement element = null;

		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(userDefinedLocator));
			element = driver.findElement(userDefinedLocator);
			return element;
		} catch (Exception ex) {

		}
		return element;
	}

}
