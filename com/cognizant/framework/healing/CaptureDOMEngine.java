package com.cognizant.framework.healing;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;
import com.cognizant.framework.Util;
import com.cognizant.framework.selenium.SeleniumTestParameters;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import us.codecraft.xsoup.Xsoup;

public class CaptureDOMEngine {

	private By userDefinedLocator;
	private WebElement userWebElement;
	private String packageName;
	private String className;
	private String methodName;
	private int lineNumber;
	private SeleniumTestParameters testParameters;
	private WebDriver driver;
	private FrameworkParameters frameworkParameters;
	private MongoClient mongoclient;
	private DB database;
	private DBCollection collection;
	private DBObject objectDetails;
	// private MongoClient mongoclient;
	// private MongoDatabase database;
	// private MongoCollection<org.bson.Document> collection;
	private Document doc;
	private Properties properties = Settings.getInstance();
	private String uniqueVariableName;
	private Map<String, BasicDBList> totalLocatorList;

	private Params params;

	public CaptureDOMEngine(By userDefinedLocator, String className, String methodName, int lineNumber,
			SeleniumTestParameters testParameters, WebDriver driver, FrameworkParameters frameworkParameters,
			String packageName, WebElement userWebElement) {

		this.userDefinedLocator = userDefinedLocator;
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
		this.testParameters = testParameters;
		this.driver = driver;
		this.frameworkParameters = frameworkParameters;
		this.params = new Params();
		this.userWebElement = userWebElement;
		setLocatorType(userDefinedLocator);
	}

	public void captureSecondaryObjects() {

		Map<String, String> alternateLocators = new HashMap<String, String>();
		List<Map<String, String>> neighbourLocators = new ArrayList<Map<String, String>>();
		Map<String, String> siblingsLocators = new HashMap<String, String>();
		Map<String, String> elementCoordinates = new HashMap<String, String>();

		Element primaryLocatorElement = traverseDOMForSecondaryLocators(userDefinedLocator, params.getLocatorType());

		alternateLocators = getAlternateLocators(primaryLocatorElement, params.getLocatorType());
		neighbourLocators = getNeighbourLocators(primaryLocatorElement);
		siblingsLocators = getSiblingLocators(userWebElement);
		elementCoordinates = getCoordinatesOfElement(userWebElement);

		storeToDB(className, methodName, lineNumber, testParameters, userDefinedLocator, alternateLocators,
				neighbourLocators, packageName, siblingsLocators, elementCoordinates);

	}

	private Element traverseDOMForSecondaryLocators(By userDefinedLocator, LocatorType locatorType) {

		doc = Jsoup.parse(driver.getPageSource());
		Element primaryLocatorElement = null;

		String removeString = getLocatorType(userDefinedLocator, locatorType);

		// String value =
		// userDefinedLocator.toString().split("\\.")[1].split(":")[1].trim();

		String value = userDefinedLocator.toString().replace(removeString, "").trim();

		switch (locatorType) {

		case ID:
			primaryLocatorElement = doc.getElementById(value);
			break;

		case NAME:
			primaryLocatorElement = doc.getElementsByAttributeValue("name", value).get(0);
			break;

		case LINK_TEXT:
			primaryLocatorElement = doc.getElementsMatchingOwnText(value).get(0);

			break;

		case PARTIAL_LINKTEXT:
			primaryLocatorElement = doc.getElementsContainingOwnText(value).get(0);
			break;

		case TAG_NAME:
			primaryLocatorElement = doc.getElementsByTag(value).get(0);
			break;

		case CLASS_NAME:

			primaryLocatorElement = doc.getElementsByAttributeValue("class", value).get(0);
			break;

		case CSS_SELECTOR:
			primaryLocatorElement = doc.select(value).get(0);
			break;

		case XPATH:

			primaryLocatorElement = Xsoup.compile(getFilteredXpath(value)).evaluate(doc).getElements().get(0);
			break;

		default:
			break;

		}
		return primaryLocatorElement;

	}

	private String getLocatorType(By userDefinedLocator, LocatorType locatorType) {

		String finalLocatorType = null;

		switch (locatorType) {

		case ID:
			finalLocatorType = "By.id:";
			break;
		case NAME:
			finalLocatorType = "By.name:";
			break;
		case CLASS_NAME:
			finalLocatorType = "By.className:";
			break;
		case LINK_TEXT:
			finalLocatorType = "By.linkText:";
			break;
		case PARTIAL_LINKTEXT:
			finalLocatorType = "By.partialLinkText:";
			break;
		case TAG_NAME:
			finalLocatorType = "By.tagName:";
			break;
		case CSS_SELECTOR:
			finalLocatorType = "By.cssSelector:";
			break;
		case XPATH:
			finalLocatorType = "By.xpath:";
			break;
		default:
			break;

		}

		return finalLocatorType;
	}

	private Map<String, String> getAlternateLocators(Element primaryLocatorElement, LocatorType locatorType) {

		Map<String, String> locators = new HashMap<>();

		switch (locatorType) {
		case ID:
			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("PARTIAL_LINKTEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		case NAME:
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("PARTIAL_LINKTEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		case LINK_TEXT:
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		case PARTIAL_LINKTEXT:
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		case TAG_NAME:

			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("PARTIAL_LINKTEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		case CLASS_NAME:

			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("PARTIAL_LINKTEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		case CSS_SELECTOR:

			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("PARTIAL_LINKTEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		case XPATH:

			locators.put("ID", primaryLocatorElement.attr("id"));
			locators.put("NAME", primaryLocatorElement.attr("name"));
			locators.put("LINK_TEXT", primaryLocatorElement.text());
			locators.put("PARTIAL_LINKTEXT", primaryLocatorElement.text());
			locators.put("TAG_NAME", primaryLocatorElement.tagName());
			locators.put("CLASS_NAME", primaryLocatorElement.attr("class"));
			locators.put("CSS_SELECTOR", primaryLocatorElement.cssSelector());
			locators.put("XPATH", getAbsoluteXPath(userWebElement));

			break;

		default:
			break;

		}
		locators.put("ATTRIBUTES", getAllAttributes(userWebElement));
		return locators;

	}

	private List<Map<String, String>> getNeighbourLocators(Element primaryLocatorElement) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Element parent = primaryLocatorElement.previousElementSibling();

		if (parent != null) {
			list.add(getParentLocators(parent));
		}

		Element child = primaryLocatorElement.nextElementSibling();

		if (child != null) {
			list.add(getChildLocators(child));
		}
		return list;
	}

	private Map<String, String> getParentLocators(Element parent) {
		Map<String, String> parentLocators = new HashMap<>();
		WebElement precedingSiblings = null;
		try {
			// Delay Logic
			precedingSiblings = userWebElement.findElement(By.xpath("preceding-sibling::*"));
			parentLocators.put("PRECEDING_XPATH", getAbsoluteXPath(precedingSiblings));
			parentLocators.put("PRECEDING_ATTRIBUTES", getAllAttributes(precedingSiblings));
		} catch (Exception e) {
		}

		parentLocators.put("PRECEDING_ID", parent.attr("id"));
		parentLocators.put("PRECEDING_NAME", parent.attr("name"));
		parentLocators.put("PRECEDING_LINK_TEXT", parent.text());
		parentLocators.put("PRECEDING_PARTIAL_LINKTEXT", parent.text());
		parentLocators.put("PRECEDING_TAG_NAME", parent.tagName());
		parentLocators.put("PRECEDING_CLASS_NAME", parent.attr("class"));
		parentLocators.put("PRECEDING_CSS_SELECTOR", parent.cssSelector());

		return parentLocators;
	}

	private Map<String, String> getChildLocators(Element child) {

		Map<String, String> childLocators = new HashMap<>();
		WebElement followingSiblings = null;
		try {
			// Delay Logic
			followingSiblings = userWebElement.findElement(By.xpath("following-sibling::*"));
			childLocators.put("FOLLOWING_XPATH", getAbsoluteXPath(followingSiblings));
			childLocators.put("FOLLOWING_ATTRIBUTES", getAllAttributes(followingSiblings));
		} catch (Exception e) {
		}

		childLocators.put("FOLLOWING_ID", child.attr("id"));
		childLocators.put("FOLLOWING_NAME", child.attr("name"));
		childLocators.put("FOLLOWING_LINK_TEXT", child.text());
		childLocators.put("FOLLOWING_PARTIAL_LINKTEXT", child.text());
		childLocators.put("FOLLOWING_TAG_NAME", child.tagName());
		childLocators.put("FOLLOWING_CLASS_NAME", child.attr("class"));
		childLocators.put("FOLLOWING_CSS_SELECTOR", child.cssSelector());

		return childLocators;
	}

	private Map<String, String> getSiblingLocators(WebElement userWebElement) {

		Map<String, String> siblingLocators = new HashMap<>();

		try {

			// Delay Logic

			WebElement parentElement = userWebElement.findElement(By.xpath(".."));
			siblingLocators.put("PARENT_XPATH", getAbsoluteXPath(parentElement));
			siblingLocators.put("PARENT_ATTRIBUTES", getAllAttributes(parentElement));
			Element parentEle = Xsoup.compile(getAbsoluteXPath(parentElement)).evaluate(doc).getElements().get(0);
			siblingLocators.put("PARENT_CSS_SELECTOR", parentEle.cssSelector());

		} catch (Exception e) {

		}

		try {
			// Delay Logic

			List<WebElement> children = userWebElement.findElements(By.xpath(".//*"));
			siblingLocators.put("CHILD_XPATH", getAbsoluteXPath(children.get(0)));
			siblingLocators.put("CHILD_ATTRIBUTES", getAllAttributes(children.get(0)));
			Element childElement = Xsoup.compile(getAbsoluteXPath(children.get(0))).evaluate(doc).getElements().get(0);
			siblingLocators.put("CHILD_CSS_SELECTOR", childElement.cssSelector());
		} catch (Exception e) {

		}

		return siblingLocators;
	}

	private void setLocatorType(By arg0) {

		String type = arg0.toString().split("\\.")[1].split(":")[0].trim();

		if (type.equalsIgnoreCase("id")) {
			params.setLocatorType(LocatorType.ID);

		} else if (type.equalsIgnoreCase("name")) {
			params.setLocatorType(LocatorType.NAME);

		} else if (type.equalsIgnoreCase("linktext")) {
			params.setLocatorType(LocatorType.LINK_TEXT);

		} else if (type.equalsIgnoreCase("partiallinktext")) {
			params.setLocatorType(LocatorType.PARTIAL_LINKTEXT);

		} else if (type.equalsIgnoreCase("tagname")) {
			params.setLocatorType(LocatorType.TAG_NAME);

		} else if (type.equalsIgnoreCase("classname")) {
			params.setLocatorType(LocatorType.CLASS_NAME);

		} else if (type.equalsIgnoreCase("cssselector")) {
			params.setLocatorType(LocatorType.CSS_SELECTOR);

		} else if (type.equalsIgnoreCase("xpath")) {
			params.setLocatorType(LocatorType.XPATH);
		}

	}

	private void storeToDB(String className, String methodName, int lineNumber, SeleniumTestParameters testParameters,
			By primaryLocator, Map<String, String> alternateLocators, List<Map<String, String>> neighbourLocators,
			String packageName, Map<String, String> siblingsLocators, Map<String, String> elementCoordinates) {
		createCollection();

		updateObjectDetails(testParameters, className, methodName, lineNumber, packageName);
		try {
			storeLuckyLocator();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateAllLocators(alternateLocators, neighbourLocators, siblingsLocators, elementCoordinates);

	}

	private void updateAllLocators(Map<String, String> alternateLocators, List<Map<String, String>> neighbourLocators,
			Map<String, String> siblingsLocators, Map<String, String> elementCoordinates) {

		// totalLocatorList = new HashMap<>();
		// Alternate Locators
		BasicDBList alternateList = new BasicDBList();

		for (Map.Entry<String, String> entry : alternateLocators.entrySet()) {
			DBObject alternateObjects = new BasicDBObject();
			alternateObjects.put("TYPE", entry.getKey());
			alternateObjects.put("VALUE", entry.getValue());
			alternateList.add(alternateObjects);
		}

		totalLocatorList.put("AlternateLocators", alternateList);

		// Neighbor Locators
		BasicDBList neighbourList = new BasicDBList();

		for (int i = 0; i < neighbourLocators.size(); i++) {

			for (Map.Entry<String, String> entry : neighbourLocators.get(i).entrySet()) {
				DBObject neighbourObjects = new BasicDBObject();
				neighbourObjects.put("TYPE", entry.getKey());
				neighbourObjects.put("VALUE", entry.getValue());
				neighbourList.add(neighbourObjects);
			}
		}

		totalLocatorList.put("NeighbourLocators", neighbourList);

		// Sibling Locators
		BasicDBList siblingList = new BasicDBList();

		for (Map.Entry<String, String> entry : siblingsLocators.entrySet()) {
			DBObject siblingsObjects = new BasicDBObject();
			siblingsObjects.put("TYPE", entry.getKey());
			siblingsObjects.put("VALUE", entry.getValue());
			siblingList.add(siblingsObjects);

		}

		totalLocatorList.put("SiblingLocators", siblingList);

		// Neighbor Locators
		BasicDBList coordinateList = new BasicDBList();

		for (Map.Entry<String, String> entry : elementCoordinates.entrySet()) {
			DBObject coordinateObjects = new BasicDBObject();
			coordinateObjects.put("TYPE", entry.getKey());
			coordinateObjects.put("VALUE", entry.getValue());
			coordinateList.add(coordinateObjects);

		}

		totalLocatorList.put("CoordinteLocators", coordinateList);

		objectDetails.put(testParameters.getBrowser().toString(), totalLocatorList);

		upsertData();

		mongoclient.close();
	}

	private void updateObjectDetails(SeleniumTestParameters testParameters, String className, String methodName,
			int lineNumber, String packageName) {

		List<String> classNmethodName = GetClassNMethodNames.getVaribleName(lineNumber, className, methodName,
				frameworkParameters.getRelativePath(), packageName);

		String uniqueVariableName = classNmethodName.get(0).toUpperCase() + "_" + classNmethodName.get(1).toUpperCase();

		objectDetails.put("UniqueVariableName", uniqueVariableName);
		objectDetails.put("TestCase", testParameters.getCurrentTestcase());
		objectDetails.put("Scenario", testParameters.getCurrentScenario());
		objectDetails.put("Instance", testParameters.getCurrentTestInstance());
		objectDetails.put("ClassName", className);
		objectDetails.put("MethodName", methodName);
		objectDetails.put("LineNumber", lineNumber);

	}

	private void upsertData() {

		BasicDBObject query = new BasicDBObject();
		query.put("UniqueVariableName", uniqueVariableName);

		// collection.update(query, objectDetails);
		collection.update(query, new BasicDBObject("$set", objectDetails), true, false);

	}

	@SuppressWarnings("deprecation")
	private void createCollection() {
		String dbHost = properties.getProperty("DBHost");
		String dbPort = properties.getProperty("DBPort");

		try {
			// Check if that Collection exists
			// CognitiveDatabase - DB
			// ExecutionDetails - Collections
			// createCollection();
			// Creating collection if not exist if exist it will get the
			// collection
			mongoclient = new MongoClient(dbHost, Integer.valueOf(dbPort));
			database = mongoclient.getDB("CRAFTCentral");
			collection = database.getCollection("ObjectDetails");
			// mongoclient = new MongoClient(dbHost, Integer.valueOf(dbPort));
			// database = mongoclient.getDatabase("CRAFTCentral");

			// collection = database.getCollection("ObjectDetails");
			// collection.setObjectClass(CustomChainData.class);
			objectDetails = new BasicDBObject();

			List<String> classNmethodName = GetClassNMethodNames.getVaribleName(lineNumber, className, methodName,
					frameworkParameters.getRelativePath(), packageName);

			uniqueVariableName = classNmethodName.get(0).toUpperCase() + "_" + classNmethodName.get(1).toUpperCase();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

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

	private Map<String, String> getCoordinatesOfElement(WebElement element) {

		Map<String, String> coordinates = new HashMap<>();

		// Used points class to get x and y coordinates of element.
		Point elementLocation = element.getLocation();
		int xcordi = elementLocation.getX();
		int ycordi = elementLocation.getY();
		coordinates.put("x", Integer.toString(xcordi));
		coordinates.put("y", Integer.toString(ycordi));
		return coordinates;

	}

	private String getAllAttributes(WebElement element) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Object elementAttributes = executor.executeScript(
				"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
				element);
		return elementAttributes.toString();

	}

	private void storeLuckyLocator() throws JSONException {

		totalLocatorList = new HashMap<>();
		BasicDBList luckyLocatorList = new BasicDBList();
		BasicDBObject query = new BasicDBObject();
		BasicDBObject object = null;
		query.put("UniqueVariableName", uniqueVariableName);

		DBCursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			object = (BasicDBObject) cursor.next();
		}

		if (object == null) {

			// BasicDBList luckyLocators = new BasicDBList();
			DBObject luckyLocators = new BasicDBObject();
			luckyLocators.put("TYPE", params.getLocatorType().toString());
			luckyLocators.put("VALUE", userDefinedLocator.toString().split("\\.")[1].split(":")[1].trim());
			luckyLocators.put("COUNT", "1");
			luckyLocatorList.add(luckyLocators);

		} else {
			JSONObject locatorObject = new JSONObject(object.toJson());

			JSONObject browserObject = locatorObject.getJSONObject(testParameters.getBrowser().toString());

			try {
				JSONArray luckyLocatorsArray = browserObject.getJSONArray("LuckyLocators");

				for (int i = 0; i < luckyLocatorsArray.length(); i++) {

					JSONObject objects = luckyLocatorsArray.getJSONObject(i);

					if (objects.getString("TYPE").equals(params.getLocatorType().toString())
							&& objects.getString("VALUE")
									.equals(userDefinedLocator.toString().split("\\.")[1].split(":")[1].trim())) {
						DBObject luckyLocators = new BasicDBObject();
						luckyLocators.put("TYPE", params.getLocatorType().toString());
						luckyLocators.put("VALUE", userDefinedLocator.toString().split("\\.")[1].split(":")[1].trim());
						luckyLocators.put("COUNT", String.valueOf((Integer.parseInt(objects.getString("COUNT")) + 1)));
						luckyLocatorList.add(luckyLocators);
					}
				}

			} catch (Exception e) {
				DBObject luckyLocators = new BasicDBObject();
				luckyLocators.put("TYPE", params.getLocatorType().toString());
				luckyLocators.put("VALUE", userDefinedLocator.toString().split("\\.")[1].split(":")[1].trim());
				luckyLocators.put("COUNT", "1");
				luckyLocatorList.add(luckyLocators);
			}
		}

		totalLocatorList.put("LuckyLocators", luckyLocatorList);

	}

	private String getFilteredXpath(String value) {

		String xpath;
		if (value.contains("html[1]")) {

			xpath = value.replace("html[1]", "html");
		} else {
			xpath = value;
		}
		return xpath;
	}

}
