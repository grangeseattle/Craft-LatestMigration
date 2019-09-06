package com.cognizant.framework.healing;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Tier6Healing {

	private WebDriver driver;
	public By healedLocator;
	private Map<String, String> finalLocators6 = new HashMap<String, String>();

	public Tier6Healing(WebDriver driver, Document doc) {
		this.driver = driver;
	}

	public WebElement invokeTier6Process(Map<String, String> luckyLocators) {
		WebElement element = null;
		validateLuckyLocators(luckyLocators);

		element = tryHealingWithNewLocators(finalLocators6);
		return element;

	}

	private void validateLuckyLocators(Map<String, String> luckyLocators) {
		int count = 0;
		for (Map.Entry<String, String> entry : luckyLocators.entrySet()) {

			String[] part = entry.getKey().split("(?<=\\D)(?=\\d)");

			String locatorType = part[0];
			int locatorCount = Integer.parseInt(part[1]);

			if (locatorCount > count) {
				count = Integer.parseInt(entry.getKey());
				finalLocators6.put("TYPE", locatorType);
				finalLocators6.put("VALUE", entry.getValue());
			}
		}

	}

	private WebElement tryHealingWithNewLocators(Map<String, String> finalLocators6) {
		WebElement element = null;

		if (finalLocators6.get("TYPE").equalsIgnoreCase("id")) {

			element = validateUsingDriver(By.id(finalLocators6.get("VALUE")));
		} else if (finalLocators6.get("TYPE").equalsIgnoreCase("name")) {

			element = validateUsingDriver(By.name(finalLocators6.get("VALUE")));
		} else if (finalLocators6.get("TYPE").equalsIgnoreCase("linktext")) {

			element = validateUsingDriver(By.linkText(finalLocators6.get("VALUE")));
		} else if (finalLocators6.get("TYPE").equalsIgnoreCase("partiallinktext")) {

			element = validateUsingDriver(By.partialLinkText(finalLocators6.get("VALUE")));
		} else if (finalLocators6.get("TYPE").equalsIgnoreCase("tagname")) {

			element = validateUsingDriver(By.tagName(finalLocators6.get("VALUE")));
		} else if (finalLocators6.get("TYPE").equalsIgnoreCase("classname")) {

			element = validateUsingDriver(By.className(finalLocators6.get("VALUE")));
		} else if (finalLocators6.get("TYPE").equalsIgnoreCase("cssselector")) {

			element = validateUsingDriver(By.cssSelector(finalLocators6.get("VALUE")));
		} else if (finalLocators6.get("TYPE").equalsIgnoreCase("xpath")) {

			element = validateUsingDriver(By.xpath(finalLocators6.get("VALUE")));
		}

		return element;
	}

	private WebElement validateUsingDriver(By type) {
		WebElement element = null;

		try {
			element = driver.findElement(type);
		} catch (Exception e) {

		}

		return element;

	}

	public By getHealedLocator() {
		return this.healedLocator;
	}

}
