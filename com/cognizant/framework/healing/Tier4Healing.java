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

public class Tier4Healing {

	private WebDriver driver;
	@SuppressWarnings("unused")
	private Document doc;
	private Map<String, String> alternateLocators;
	private Map<String, String> neighbourLocators;
	private Map<String, String> sibilingLocators;
	Map<String, WebElement> matchingElements = new HashMap<>();
	Map<String, WebElement> partialMatchingElements = new HashMap<>();

	public Tier4Healing(WebDriver driver, Map<String, String> alternateLocators, Map<String, String> neighbourLocators,
			Map<String, String> sibilingLocators, Document doc) {
		this.driver = driver;
		this.doc = doc;
		this.alternateLocators = alternateLocators;
		this.neighbourLocators = neighbourLocators;
		this.sibilingLocators = sibilingLocators;

	}

	public WebElement invokeParsingAlgorithm() {

		turnOffImplicitWaits();

		WebElement element = null;

		validateDOMElements();

		element = tryHealingWithMatchingElements();

		if (element == null) {
			element = tryHealingWithPartialMatching();
		}

		turnOnImplicitWaits();

		return element;
	}

	private WebElement tryHealingWithMatchingElements() {
		WebElement element = null;

		for (Map.Entry<String, WebElement> entry : matchingElements.entrySet()) {

			if (Float.valueOf(entry.getKey()) > 60) {

				return element = entry.getValue();

			}

		}

		return element;
	}

	private WebElement tryHealingWithPartialMatching() {
		WebElement element = null;

		for (Map.Entry<String, WebElement> entry : partialMatchingElements.entrySet()) {

			if (Integer.parseInt(entry.getKey()) > 50) {

				return element = entry.getValue();

			}

		}

		return element;
	}

	/**
	 * 
	 */
	private void validateDOMElements() {
		if (alternateLocators.get("ATTRIBUTES") != null) {

			List<WebElement> elements = driver.findElements(By.tagName(alternateLocators.get("TAG_NAME")));

			for (WebElement ele : elements) {

				float percentage = getPercentage(alternateLocators.get("ATTRIBUTES"), ele);

				if (percentage == 100) {

					float precedingValue = getPrecedingPercentage(ele, By.xpath("preceding-sibling::*"));
					float followingValue = getFollowingPercentage(ele, By.xpath("following-sibling::*"));
					float parentValue = getParentPercentage(ele, By.xpath(".."));

					float commonValue = getCommonMatching(precedingValue, followingValue, parentValue);
					matchingElements.put(String.valueOf(commonValue), ele);

				} else if (percentage > 60) {

					float precedingValue = getPrecedingPercentage(ele, By.xpath("preceding-sibling::*"));
					float followingValue = getFollowingPercentage(ele, By.xpath("following-sibling::*"));
					float parentValue = getParentPercentage(ele, By.xpath(".."));

					float commonValue = getCommonMatching(precedingValue, followingValue, parentValue);
					partialMatchingElements.put(String.valueOf(commonValue), ele);
				}

			}

		}
	}

	private float getPrecedingPercentage(WebElement ele, By preceding) {
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

	private float getFollowingPercentage(WebElement ele, By following) {

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

	private float getParentPercentage(WebElement ele, By following) {

		float foloowingValue = 0;
		if (sibilingLocators.get("PARENT_ATTRIBUTES") != null) {
			try {
				WebElement precedingElement = ele.findElement(following);

				foloowingValue = getPercentage(neighbourLocators.get("PARENT_ATTRIBUTES"), precedingElement);
			} catch (Exception e) {

			}
		}

		return foloowingValue;

	}

	private float getCommonMatching(float preceding, float following, float parent) {

		float common = (preceding + following + parent) / 3;

		return common;

	}

	/**
	 * @param attributeMapExpected
	 * @param ele
	 * @return
	 */
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

	private void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	private void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

}
