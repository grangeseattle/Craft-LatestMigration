package com.cognizant.framework.healing;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.openqa.selenium.WebDriver;

import com.cognizant.framework.Settings;
import com.cognizant.framework.selenium.SeleniumTestParameters;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RetriveDataFromDB {

	public Map<String, String> alternateLocators = new HashMap<>();
	public Map<String, String> neighbourLocators = new HashMap<>();
	public Map<String, String> siblingsLocators = new HashMap<>();
	public Map<String, String> coordinateLocators = new HashMap<>();
	public Map<String, String> luckyLocators = new HashMap<>();

	private MongoClient mongoClient = null;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private Properties properties = Settings.getInstance();
	private org.jsoup.nodes.Document doc;

	public Map<String, String> getAlternatorLocators() {
		return this.alternateLocators;
	}

	public Map<String, String> getNeighbourLocators() {
		return this.neighbourLocators;
	}

	public Map<String, String> getSiblingLocators() {
		return this.siblingsLocators;
	}

	public Map<String, String> getcoordinateLocators() {
		return this.coordinateLocators;
	}

	public Map<String, String> getLuckyLocators() {
		return this.luckyLocators;
	}

	public org.jsoup.nodes.Document getJSoupDocument() {
		return this.doc;
	}

	public RetriveDataFromDB(WebDriver driver, String variableName, SeleniumTestParameters testParameters) {
		String dbHost = properties.getProperty("DBHost");
		String dbPort = properties.getProperty("DBPort");
		try {

			mongoClient = new MongoClient(dbHost, Integer.valueOf(dbPort));
			database = mongoClient.getDatabase("CRAFTCentral");

			collection = database.getCollection("ObjectDetails");

			fetchAllRecordsFromDB(variableName, testParameters);
			doc = Jsoup.parse(driver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}

	private void fetchAllRecordsFromDB(String variableName, SeleniumTestParameters testParameters) throws JSONException {

		BasicDBObject query = new BasicDBObject();
		query.put("UniqueVariableName", variableName);
		Document document = collection.find(query).first();

		JSONObject entireLocatorsObject;
		JSONObject browserObject = null;
			entireLocatorsObject = new JSONObject(document.toJson());
			 browserObject = entireLocatorsObject.getJSONObject(testParameters.getBrowser().toString());
		getAlternateLocatorsFromDB(browserObject);
		getNeighbourLocatorsFromDB(browserObject);
		getSiblingsLocatorsFromDB(browserObject);
		getCoordinateLocatorsFromDB(browserObject);
		getLuckyLoctorsFromDB(browserObject);
	}

	private void getLuckyLoctorsFromDB(JSONObject browserObject) throws JSONException {
		JSONArray luckyLocArray = browserObject.getJSONArray("LuckyLocators");

		for (int i = 0; i < luckyLocArray.length(); i++) {
			try {

				JSONObject objects = luckyLocArray.getJSONObject(i);
				luckyLocators.put(objects.getString("TYPE") + objects.getString("COUNT"), objects.getString("VALUE"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getCoordinateLocatorsFromDB(JSONObject browserObject) throws JSONException {

		JSONArray coordinateLocArray = browserObject.getJSONArray("CoordinteLocators");

		for (int i = 0; i < coordinateLocArray.length(); i++) {
			try {

				JSONObject objects = coordinateLocArray.getJSONObject(i);
				coordinateLocators.put(objects.getString("TYPE"), objects.getString("VALUE"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getSiblingsLocatorsFromDB(JSONObject browserObject) throws JSONException {

		JSONArray siblingLocArray = browserObject.getJSONArray("SiblingLocators");

		for (int i = 0; i < siblingLocArray.length(); i++) {
			try {

				JSONObject objects = siblingLocArray.getJSONObject(i);
				siblingsLocators.put(objects.getString("TYPE"), objects.getString("VALUE"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getNeighbourLocatorsFromDB(JSONObject browserObject) throws JSONException {

		JSONArray neighbourLocArray = browserObject.getJSONArray("NeighbourLocators");

		for (int i = 0; i < neighbourLocArray.length(); i++) {
			try {

				JSONObject objects = neighbourLocArray.getJSONObject(i);
				neighbourLocators.put(objects.getString("TYPE"), objects.getString("VALUE"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getAlternateLocatorsFromDB(JSONObject browserObject) throws JSONException {

		JSONArray alternateLocArray = browserObject.getJSONArray("AlternateLocators");

		for (int i = 0; i < alternateLocArray.length(); i++) {

			JSONObject objects = alternateLocArray.getJSONObject(i);
			alternateLocators.put(objects.getString("TYPE"), objects.getString("VALUE"));
		}
	}

}
