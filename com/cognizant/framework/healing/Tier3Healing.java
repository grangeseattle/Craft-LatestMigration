package com.cognizant.framework.healing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cognizant.framework.Util;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import us.codecraft.xsoup.Xsoup;

public class Tier3Healing {

	private Map<String, String> finalLocatorsForTier3 = new HashMap<>();
	private WebDriver driver;
	public By healedLocator;
	private Document doc;

	public Tier3Healing(WebDriver driver, Document doc) {
		this.driver = driver;
		this.doc = doc;

	}

	public WebElement invokeTier3Process(Map<String, String> map) {

		WebElement element = null;

		turnOffImplicitWaits();
		validateParentElements(map);
		validateChildElements(map);

		element = tryHealingWithNewLocators(finalLocatorsForTier3);
		turnOnImplicitWaits();
		return element;
	}

	private void validateParentElements(Map<String, String> map) {

		if (map.get("PARENT_XPATH") != null) {

			try {

				if (doc.select(map.get("PARENT_XPATH")).size() == 1) {
					Element nextElement = Xsoup.compile(getFilteredXpath(map.get("PARENT_XPATH"))).evaluate(doc)
							.getElements().get(0);
					Elements currentJsoupElement = nextElement.children();

					if (currentJsoupElement != null) {

						String currentCSSSelector = currentJsoupElement.get(0).cssSelector();

						finalLocatorsForTier3.put("CHILD_CSS_SELECTOR", currentCSSSelector);

						try {
							WebElement currentDriverElement = driver.findElement(By.cssSelector(currentCSSSelector));
							String currentXpath = getAbsoluteXPath(currentDriverElement);
							finalLocatorsForTier3.put("CHILD_XPATH", currentXpath);
//							WebElement currentDriverElement = driver.findElement(By.xpath(map.get("PARENT_XPATH")));
//							List<WebElement> children = currentDriverElement.findElements(By.xpath(".//*"));
//							java.util.Iterator<WebElement> i = children.iterator();
//							while (i.hasNext()) {
//								WebElement element = i.next();
//								if (getMatchingElement(element)) {
//									String currentXpath = getAbsoluteXPath(currentDriverElement);
//									finalLocatorsForTier3.put("CHILD_XPATH", currentXpath);
//								}
//							}

						} catch (Exception e) {

						}
					}

				}

			} catch (Exception e) {

			}

		}
	}

	private void validateChildElements(Map<String, String> map) {

		if (map.get("CHILD_XPATH") != null) {

			try {

				if (doc.select(map.get("CHILD_XPATH")).size() == 1) {
					Element nextElement = Xsoup.compile(getFilteredXpath(map.get("CHILD_XPATH"))).evaluate(doc)
							.getElements().get(0);
					Elements currentJsoupElement = nextElement.children();

					if (currentJsoupElement != null) {

						String currentCSSSelector = currentJsoupElement.get(0).cssSelector();
						finalLocatorsForTier3.put("PARENT_CSS_SELECTOR", currentCSSSelector);

						try {

							WebElement currentDriverElement = driver.findElement(By.cssSelector(currentCSSSelector));
							String currentXpath = getAbsoluteXPath(currentDriverElement);
							finalLocatorsForTier3.put("PARENT_XPATH", currentXpath);

						} catch (Exception e) {

						}
					}

				}

			} catch (Exception e) {

			}
		}
	}

	private WebElement tryHealingWithNewLocators(Map<String, String> newLocators) {

		WebElement element = null;

		try {
			if (newLocators.get("CHILD_CSS_SELECTOR") != null) {
				element = driver.findElement(By.cssSelector(newLocators.get("CHILD_CSS_SELECTOR")));
				healedLocator = By.cssSelector(newLocators.get("CHILD_CSS_SELECTOR"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("CHILD_XPATH") != null) {
				element = driver.findElement(By.xpath(newLocators.get("CHILD_XPATH")));
				healedLocator = By.xpath(newLocators.get("CHILD_XPATH"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("PARENT_CSS_SELECTOR") != null) {
				element = driver.findElement(By.cssSelector(newLocators.get("PARENT_CSS_SELECTOR")));
				healedLocator = By.cssSelector(newLocators.get("PARENT_CSS_SELECTOR"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("PARENT_XPATH") != null) {
				element = driver.findElement(By.xpath(newLocators.get("PARENT_XPATH")));
				healedLocator = By.xpath(newLocators.get("PARENT_XPATH"));
				return element;
			}
		} catch (Exception e) {

		}

		return element;

	}

	@SuppressWarnings("deprecation")
	private String getAbsoluteXPath(WebElement element) {

		String fileContents = null;
		try {

			fileContents = Files.toString(new File(Util.getResourcePathOfFramework() + Util.getFileSeparator() + "files"
					+ Util.getFileSeparator() + "XpathCode.js"), Charsets.UTF_8);
			return (String) ((JavascriptExecutor) driver).executeScript(fileContents, element);
		} catch (Exception e) {

		}
		return fileContents;
	}

	private void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	private void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public By getHealedLocator() {
		return this.healedLocator;
	}

	private String getFilteredXpath(String map) {

		String xpath;
		if (map.contains("html[1]")) {

			xpath = map.replace("html[1]", "html");
		} else {
			xpath = map;
		}
		return xpath;
	}

}
