package com.cognizant.framework.healing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

public class Tier7Healing {

	private WebDriver driver;
	public By healedLocator;
	@SuppressWarnings("unused")
	private Map<String, String> finalLocators7 = new HashMap<String, String>();
	@SuppressWarnings("unused")
	private Document doc;

	public Tier7Healing(WebDriver driver, Document doc) {
		this.driver = driver;
		this.doc = doc;
	}

	public WebElement invokeTier7Process(Map<String, String> siblingsLocators, Map<String, String> neighbourLocators,
			Map<String, String> alternateLocators) {
		WebElement element = null;

		turnOffImplicitWaits();

		element = validateParentsChildren(siblingsLocators, neighbourLocators, alternateLocators);

		turnOnImplicitWaits();

		return element;

	}

	private WebElement validateParentsChildren(Map<String, String> siblingsLocators,
			Map<String, String> neighbourLocators, Map<String, String> alternateLocators) {
		WebElement element = null;

		if (siblingsLocators.get("PARENT_XPATH") != null) {

			try {
				WebElement parentElement = driver.findElement(By.xpath(siblingsLocators.get("PARENT_XPATH")));
				List<WebElement> children = parentElement.findElements(By.xpath(".//*"));
				for (WebElement ele : children) {

					if (ele.getTagName().equals(alternateLocators.get("TAG_NAME"))) {

						float percentage = getPercentage(alternateLocators.get("ATTRIBUTES"), ele);

						if (percentage == 100) {
							return ele;
						} else {
							float precedingValue = getPrecedingPercentage(ele, By.xpath("preceding-sibling::*"),
									neighbourLocators);
							float followingValue = getFollowingPercentage(ele, By.xpath("following-sibling::*"),
									neighbourLocators);

							float commonValue = getCommonMatching(precedingValue, followingValue);

							if (commonValue > 50) {
								return ele;
							}
						}

					}

				}
			} catch (Exception e) {

			}
		}

		if (siblingsLocators.get("PARENT_CSS_SELECTOR") != null) {

			try {
				WebElement parentElement = driver
						.findElement(By.cssSelector(siblingsLocators.get("PARENT_CSS_SELECTOR")));
				List<WebElement> children = parentElement.findElements(By.xpath(".//*"));
				for (WebElement ele : children) {
					if (ele.getTagName().equals(alternateLocators.get("TAG_NAME"))) {
						float percentage = getPercentage(alternateLocators.get("ATTRIBUTES"), ele);
						if (percentage == 100) {
							return ele;
						} else {
							float precedingValue = getPrecedingPercentage(ele, By.xpath("preceding-sibling::*"),
									neighbourLocators);
							float followingValue = getFollowingPercentage(ele, By.xpath("following-sibling::*"),
									neighbourLocators);

							float commonValue = getCommonMatching(precedingValue, followingValue);
							if (commonValue > 50) {
								return ele;
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}

		if (siblingsLocators.get("CHILD_XPATH") != null) {

			try {
				WebElement childElement = driver.findElement(By.xpath(siblingsLocators.get("CHILD_XPATH")));
				WebElement parentElement = childElement.findElement(By.xpath(".."));
				if (parentElement.getTagName().equals(alternateLocators.get("TAG_NAME"))) {
					float percentage = getPercentage(alternateLocators.get("ATTRIBUTES"), parentElement);
					if (percentage == 100) {
						return parentElement;
					} else {
						float precedingValue = getPrecedingPercentage(parentElement, By.xpath("preceding-sibling::*"),
								neighbourLocators);
						float followingValue = getFollowingPercentage(parentElement, By.xpath("following-sibling::*"),
								neighbourLocators);

						float commonValue = getCommonMatching(precedingValue, followingValue);
						if (commonValue > 50) {
							return parentElement;
						}
					}
				}

			} catch (Exception e) {

			}
		}

		if (siblingsLocators.get("CHILD_CSS_SELECTOR") != null) {

			try {
				WebElement childElement = driver
						.findElement(By.cssSelector(siblingsLocators.get("CHILD_CSS_SELECTOR")));
				WebElement parentElement = childElement.findElement(By.xpath(".."));
				if (parentElement.getTagName().equals(alternateLocators.get("TAG_NAME"))) {
					float percentage = getPercentage(alternateLocators.get("ATTRIBUTES"), parentElement);
					if (percentage == 100) {
						return parentElement;
					} else {
						float precedingValue = getPrecedingPercentage(parentElement, By.xpath("preceding-sibling::*"),
								neighbourLocators);
						float followingValue = getFollowingPercentage(parentElement, By.xpath("following-sibling::*"),
								neighbourLocators);

						float commonValue = getCommonMatching(precedingValue, followingValue);
						if (commonValue > 50) {
							return parentElement;
						}
					}
				}

			} catch (Exception e) {

			}
		}

		return element;
	}

	private float getPercentage(String expectedAttributes, WebElement element) {

		Map<String, String> attributeMapExpected = getAttribuesAsMap(expectedAttributes);

		String getDesiredElementAttributes = getAllAttributes(element);

		Map<String, String> attributeMapActual = getAttribuesAsMap(getDesiredElementAttributes);

		MapDifference<String, String> diff = Maps.difference(attributeMapActual, attributeMapExpected);
		Map<String, String> mapDiff = diff.entriesInCommon();

		float percentage = (float) mapDiff.size() / (float) attributeMapExpected.size();

		percentage = percentage * 100;
		return percentage;
	}

	private Map<String, String> getAttribuesAsMap(String attributes) {

		attributes = attributes.substring(1, attributes.length() - 1);

		String[] values = attributes.split(",");

		Map<String, String> attributeMap = new HashMap<String, String>();

		for (String value : values) {

			String[] breakAttributes = value.split("=");

			if (breakAttributes.length > 1) {
				attributeMap.put(breakAttributes[0], breakAttributes[1]);
			}

		}
		return attributeMap;
	}

	private String getAllAttributes(WebElement element) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Object elementAttributes = executor.executeScript(
				"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
				element);
		return elementAttributes.toString();

	}

	@SuppressWarnings("unused")
	private boolean compareAttributes(Map<String, String> attributeMap, Map<String, String> attributeMapTarget) {
		boolean match = false;

		if (attributeMap.size() == attributeMapTarget.size()) {

			match = attributeMap.entrySet().stream()
					.filter(value -> attributeMapTarget.entrySet().stream().anyMatch(
							value1 -> (value1.getKey() == value.getKey() && value1.getValue() == value.getValue())))
					.findAny().isPresent();

		}

		return match;
	}

	private float getPrecedingPercentage(WebElement ele, By preceding, Map<String, String> neighbourLocators) {
		float precedingValue = 0;
		if (neighbourLocators.get("PRECEDING_ATTRIBUTES") != null) {

			try {
				WebElement precedingElement = ele.findElement(preceding);

				precedingValue = getPercentage(neighbourLocators.get("PRECEDING_ATTRIBUTES"), precedingElement);
			} catch (Exception e) {

			}
		}

		return precedingValue;

	}

	private float getFollowingPercentage(WebElement ele, By following, Map<String, String> neighbourLocators) {

		float foloowingValue = 0;
		if (neighbourLocators.get("FOLLOWING_ATTRIBUTES") != null) {
			try {
				WebElement precedingElement = ele.findElement(following);

				foloowingValue = getPercentage(neighbourLocators.get("FOLLOWING_ATTRIBUTES"), precedingElement);
			} catch (Exception e) {

			}
		}

		return foloowingValue;

	}

	private float getCommonMatching(float preceding, float following) {

		float common = (preceding + following) / 2;

		return common;

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

}
