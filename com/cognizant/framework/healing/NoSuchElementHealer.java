package com.cognizant.framework.healing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Report;
import com.cognizant.framework.selenium.SeleniumTestParameters;

public class NoSuchElementHealer extends ObjectFortify {

	public NoSuchElementHealer(Exception e, By userDefinedLocator, String className, String methodName, int lineNumber,
			String packageName, WebDriver driver, FrameworkParameters frameworkParameters,
			SeleniumTestParameters testParameters, Report report) {
		super(e, userDefinedLocator, className, methodName, lineNumber, packageName, driver, frameworkParameters,
				testParameters, report);
	}

	@Override
	public WebElement heal() {

		return invokeHealingProcess();
	}

}
