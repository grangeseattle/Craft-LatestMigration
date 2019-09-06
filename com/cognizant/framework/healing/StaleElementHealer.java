package com.cognizant.framework.healing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Report;
import com.cognizant.framework.selenium.SeleniumTestParameters;

public class StaleElementHealer extends ObjectFortify {

	public StaleElementHealer(Exception exception, By userDefinedLocator, String className, String methodName,
			int lineNumber, String packageName, WebDriver driver, FrameworkParameters frameworkParameters,
			SeleniumTestParameters testParameters, Report report) {
		super(exception, userDefinedLocator, className, methodName, lineNumber, packageName, driver,
				frameworkParameters, testParameters, report);
	}

	@Override
	public WebElement heal() {
		WebElement element = null;

		try {

			new WebDriverWait(driver, 60).until(ExpectedConditions
					.refreshed(ExpectedConditions.stalenessOf(driver.findElement(userDefinedLocator))));
		} catch (Exception ex) {

		}

		for (int i = 0; i <= 4; i++) {
			try {
				element = driver.findElement(userDefinedLocator);
				Thread.sleep(500);
				break;
			} catch (Exception e) {
				if (i == 4) {

				}
			}
		}

		if (element != null) {
			return element;
		}

		element = invokeHealingProcess();

		return element;
	}

}
