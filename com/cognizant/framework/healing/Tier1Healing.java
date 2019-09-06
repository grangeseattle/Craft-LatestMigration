package com.cognizant.framework.healing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import us.codecraft.xsoup.Xsoup;

public class Tier1Healing {

	private Map<String, String> finalLocatorsForTier1 = new HashMap<>();
	private WebDriver driver;
	public By healedLocator;
	private Document doc;

	public Tier1Healing(WebDriver driver, Document doc) {
		this.driver = driver;
		this.doc = doc;

	}

	public WebElement invokeTier1Process(Map<String, String> map) {

		WebElement element = null;
		validateID(map);
		validateName(map);
		validateClassName(map);
		validateCSSSelector(map);
		validateXpath(map);

		element = tryHealingWithNewLocators(finalLocatorsForTier1);

		return element;
	}

	private void validateID(Map<String, String> map) {

		if (map.get("ID") != null) {

			try {

				doc.getElementById((map.get("ID")));

				finalLocatorsForTier1.put("ID", map.get("ID"));

			} catch (Exception e) {

			}

		}
	}

	private void validateName(Map<String, String> map) {

		if (map.get("NAME") != null) {

			try {

				if (doc.getElementsByAttributeValue("name", map.get("NAME")).size() == 1) {

					finalLocatorsForTier1.put("NAME", map.get("NAME"));
				}

			} catch (Exception e) {

			}

		}
	}

	private void validateClassName(Map<String, String> map) {

		if (map.get("CLASS_NAME") != null) {

			try {

				if (doc.getElementsByAttributeValue("class", map.get("CLASS_NAME")).size() == 1) {

					finalLocatorsForTier1.put("CLASS_NAME", map.get("CLASS_NAME"));
				}
			} catch (Exception e) {

			}

		}

	}

	private void validateCSSSelector(Map<String, String> map) {

		if (map.get("CSS_SELECTOR") != null) {

			try {

				if (doc.select(map.get("CSS_SELECTOR")).size() == 1) {
					finalLocatorsForTier1.put("CSS_SELECTOR", map.get("CSS_SELECTOR"));
				}

			} catch (Exception e) {

			}

		}
	}

	private void validateXpath(Map<String, String> map) {

		if (map.get("XPATH") != null) {

			try {

				if (Xsoup.compile(getFilteredXpath(map)).evaluate(doc).getElements().size() == 1) {

					finalLocatorsForTier1.put("XPATH", map.get("XPATH"));
				}

			} catch (Exception e) {

			}

		}

	}

	private WebElement tryHealingWithNewLocators(Map<String, String> newLocators) {

		WebElement element = null;

		turnOffImplicitWaits();

		try {
			if (newLocators.get("ID") != null) {
				element = driver.findElement(By.id(newLocators.get("ID")));
				healedLocator = By.id(newLocators.get("ID"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("NAME") != null) {
				element = driver.findElement(By.name(newLocators.get("NAME")));
				healedLocator = By.name(newLocators.get("NAME"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("CLASS_NAME") != null) {
				element = driver.findElement(By.className(newLocators.get("CLASS_NAME")));
				healedLocator = By.className(newLocators.get("CLASS_NAME"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("CSS_SELECTOR") != null) {
				element = driver.findElement(By.cssSelector(newLocators.get("CSS_SELECTOR")));
				healedLocator = By.cssSelector(newLocators.get("CSS_SELECTOR"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("XPATH") != null) {
				element = driver.findElement(By.xpath(newLocators.get("XPATH")));
				healedLocator = By.xpath(newLocators.get("XPATH"));
				return element;
			}
		} catch (Exception e) {

		}

		turnOnImplicitWaits();
		return element;

	}

	public By getHealedLocator() {
		return this.healedLocator;
	}

	private void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	private void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	private String getFilteredXpath(Map<String, String> map) {

		String xpath;
		if (map.get("XPATH").contains("html[1]")) {

			xpath = map.get("XPATH").replace("html[1]", "html");
		} else {
			xpath = map.get("XPATH");
		}
		return xpath;
	}

}
