package com.cognizant.framework.healing;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Report;
import com.cognizant.framework.selenium.SeleniumTestParameters;

public class HealingEngine {

	public static ObjectFortify getHealer(Exception e, By userDefinedLocator, String className, String methodName,
			int lineNumber, String packageName, WebDriver driver, FrameworkParameters frameworkParameters,
			SeleniumTestParameters testParameters, Report report) {

		ObjectFortify healer = null;
		if (e instanceof NoSuchElementException) {

			healer = new NoSuchElementHealer(e, userDefinedLocator, className, methodName, lineNumber, packageName,
					driver, frameworkParameters, testParameters, report);
		} else if (e instanceof ElementNotVisibleException) {

			healer = new NoElementVisibilityHealer(e, userDefinedLocator, className, methodName, lineNumber,
					packageName, driver, frameworkParameters, testParameters, report);

		} else if (e instanceof ElementNotSelectableException) {

			healer = new NotSelectableHealer(e, userDefinedLocator, className, methodName, lineNumber, packageName,
					driver, frameworkParameters, testParameters, report);
		}

		else if (e instanceof StaleElementReferenceException) {

			healer = new StaleElementHealer(e, userDefinedLocator, className, methodName, lineNumber, packageName,
					driver, frameworkParameters, testParameters, report);
		}

		else if (e instanceof TimeoutException) {

			healer = new TimeoutHealer(e, userDefinedLocator, className, methodName, lineNumber, packageName, driver,
					frameworkParameters, testParameters, report);
		}

		else if (e instanceof WebDriverException) {

			e.printStackTrace();

		} else if (e instanceof Exception) {

			e.printStackTrace();
		}

		return healer;
	}

}
