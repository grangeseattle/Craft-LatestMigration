package com.cognizant.framework.healing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cognizant.framework.Util;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import us.codecraft.xsoup.Xsoup;

public class Tier2Healing {

	private Map<String, String> finalLocatorsForTier2 = new HashMap<>();
	private WebDriver driver;
	public By healedLocator;
	private Document doc;

	public Tier2Healing(WebDriver driver, Document doc) {
		this.driver = driver;
		this.doc = doc;

	}

	public WebElement invokeTier2Process(Map<String, String> map) {

		WebElement element = null;
		turnOffImplicitWaits();
		validateFollowingCSSSelector(map);
		validateFollowingXpath(map);
		validatePrecedingCSSSelector(map);
		validatePrecedingXpath(map);
		element = tryHealingWithNewLocators(finalLocatorsForTier2);
		turnOnImplicitWaits();
		return element;
	}

	private void validateFollowingCSSSelector(Map<String, String> map) {

		if (map.get("FOLLOWING_CSS_SELECTOR") != null) {

			try {

				if (doc.select(map.get("FOLLOWING_CSS_SELECTOR")).size() == 1) {
					Element nextElement = doc.selectFirst(map.get("FOLLOWING_CSS_SELECTOR"));
					Element currentJsoupElement = nextElement.previousElementSibling();

					if (currentJsoupElement != null) {

						String currentCSSSelector = currentJsoupElement.cssSelector();

						finalLocatorsForTier2.put("NEXT_CSS_SELECTOR", currentCSSSelector);

						try {
							WebElement currentDriverElement = driver.findElement(By.cssSelector(currentCSSSelector));
							String currentXpath = getAbsoluteXPath(currentDriverElement);
							finalLocatorsForTier2.put("NEXT_XPATH", currentXpath);

						} catch (Exception e) {

						}
					}

				}

			} catch (Exception e) {

			}

		}
	}

	private void validateFollowingXpath(Map<String, String> map) {

		if (map.get("FOLLOWING_XPATH") != null) {

			try {

				if (doc.select(map.get("FOLLOWING_XPATH")).size() == 1) {
					Element nextElement = Xsoup.compile(getFilteredXpath(map.get("FOLLOWING_XPATH"))).evaluate(doc).getElements()
							.get(0);
					Element currentJsoupElement = nextElement.previousElementSibling();

					if (currentJsoupElement != null) {

						String currentCSSSelector = currentJsoupElement.cssSelector();

						finalLocatorsForTier2.put("NEXT_CSS_SELECTOR1", currentCSSSelector);

						try {
							WebElement currentDriverElement = driver.findElement(By.cssSelector(currentCSSSelector));
							String currentXpath = getAbsoluteXPath(currentDriverElement);
							finalLocatorsForTier2.put("NEXT_XPATH1", currentXpath);

						} catch (Exception e) {

						}
					}

				}

			} catch (Exception e) {

			}

		}
	}

	private void validatePrecedingCSSSelector(Map<String, String> map) {

		if (map.get("PRECEDING_CSS_SELECTOR") != null) {

			try {

				if (doc.select(map.get("PRECEDING_CSS_SELECTOR")).size() == 1) {
					Element previousElement = doc.selectFirst(map.get("PRECEDING_CSS_SELECTOR"));
					Element currentJsoupElement = previousElement.nextElementSibling();

					if (currentJsoupElement != null) {

						String currentCSSSelector = currentJsoupElement.cssSelector();

						finalLocatorsForTier2.put("PREVIOUS_CSS_SELECTOR", currentCSSSelector);

						try {
							WebElement currentDriverElement = driver.findElement(By.cssSelector(currentCSSSelector));
							String currentXpath = getAbsoluteXPath(currentDriverElement);
							finalLocatorsForTier2.put("PREVIOUS_XPATH", currentXpath);

						} catch (Exception e) {

						}
					}

				}

			} catch (Exception e) {

			}

		}

	}

	private void validatePrecedingXpath(Map<String, String> map) {

		if (map.get("PARENT_XPATH") != null) {

			try {

				if (doc.select(map.get("PARENT_XPATH")).size() == 1) {
					Element previousElement = Xsoup.compile(getFilteredXpath(map.get("PARENT_XPATH"))).evaluate(doc)
							.getElements().get(0);
					Element currentJsoupElement = previousElement.nextElementSibling();

					if (currentJsoupElement != null) {

						String currentCSSSelector = currentJsoupElement.cssSelector();

						finalLocatorsForTier2.put("PREVIOUS_CSS_SELECTOR1", currentCSSSelector);

						try {
							WebElement currentDriverElement = driver.findElement(By.cssSelector(currentCSSSelector));
							String currentXpath = getAbsoluteXPath(currentDriverElement);
							finalLocatorsForTier2.put("PREVIOUS_XPATH1", currentXpath);

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
			if (newLocators.get("NEXT_CSS_SELECTOR") != null) {
				element = driver.findElement(By.cssSelector(newLocators.get("NEXT_CSS_SELECTOR")));
				healedLocator = By.cssSelector(newLocators.get("NEXT_CSS_SELECTOR"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("NEXT_XPATH") != null) {
				element = driver.findElement(By.xpath(newLocators.get("NEXT_XPATH")));
				healedLocator = By.xpath(newLocators.get("NEXT_XPATH"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("NEXT_CSS_SELECTOR1") != null) {
				element = driver.findElement(By.cssSelector(newLocators.get("NEXT_CSS_SELECTOR1")));
				healedLocator = By.cssSelector(newLocators.get("NEXT_CSS_SELECTOR1"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("NEXT_XPATH1") != null) {
				element = driver.findElement(By.xpath(newLocators.get("NEXT_XPATH1")));
				healedLocator = By.xpath(newLocators.get("NEXT_XPATH1"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("PREVIOUS_CSS_SELECTOR") != null) {
				element = driver.findElement(By.cssSelector(newLocators.get("PREVIOUS_CSS_SELECTOR")));
				healedLocator = By.cssSelector(newLocators.get("PREVIOUS_CSS_SELECTOR"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("PREVIOUS_XPATH") != null) {
				element = driver.findElement(By.xpath(newLocators.get("PREVIOUS_XPATH")));
				healedLocator = By.xpath(newLocators.get("PREVIOUS_XPATH"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("PREVIOUS_CSS_SELECTOR1") != null) {
				element = driver.findElement(By.cssSelector(newLocators.get("PREVIOUS_CSS_SELECTOR1")));
				healedLocator = By.cssSelector(newLocators.get("PREVIOUS_CSS_SELECTOR1"));
				return element;
			}
		} catch (Exception e) {

		}

		try {
			if (newLocators.get("PREVIOUS_XPATH1") != null) {
				element = driver.findElement(By.xpath(newLocators.get("PREVIOUS_XPATH1")));
				healedLocator = By.xpath(newLocators.get("PREVIOUS_XPATH1"));
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
