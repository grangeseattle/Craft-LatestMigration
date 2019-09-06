package businesscomponents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.APIReusuableLibrary.ASSERT_RESPONSE;
import com.cognizant.framework.APIReusuableLibrary.COMPARISON;
import com.cognizant.framework.APIReusuableLibrary.SERVICEFORMAT;
import com.cognizant.framework.APIReusuableLibrary.SERVICEMETHOD;
import com.cognizant.framework.Util;

import io.restassured.response.ValidatableResponse;

public class APIComponents extends ReusableLibrary {

	HeadersForAPI headers = new HeadersForAPI();

	public APIComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void validateUser() {

		Map<String, String> headersMap = headers.getHeaders3();
		ValidatableResponse response;

		String url = dataTable.getData("General_Data", "URL1");
		String postBodyContent = dataTable.getData("General_Data", "InputJsonTemplate");
		postBodyContent = apiDriver.updateContent(postBodyContent, "update_name", "murug");
		String expectedResponse = dataTable.getData("General_Data", "OutputJsonTemplate");

		response = apiDriver.sendNReceive(url, SERVICEMETHOD.POST, SERVICEFORMAT.JSON, postBodyContent, headersMap,
				201);

		apiDriver.assertIt(url, response, ASSERT_RESPONSE.BODY, "", expectedResponse, COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.TAG, "name", "murug", COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.HEADER, "", "application/json;", COMPARISON.IS_EXISTS);

	}

	public void validateDetailsAsList() {

		Object expectedList = new ArrayList<String>();
		expectedList = getList();
		ValidatableResponse response;
		Map<String, String> headersMap = null;
		String uri = dataTable.getData("General_Data", "URL2");

		response = apiDriver.sendNReceive(uri, SERVICEMETHOD.GET, headersMap, 200);
		apiDriver.assertIt(uri, response, ASSERT_RESPONSE.LIST, "MRData.CircuitTable.Circuits.circuitId", expectedList,
				COMPARISON.IS_EQUALS);

	}

	public void convertFToC() {

		Map<String, String> headersMap = headers.getHeaders2();
		ValidatableResponse response;
		String uri = "https://www.w3schools.com/xml/tempconvert.asmx";

		String postBodyContent = apiDriver.readInput(getTemplatePath() + "FtoC_Input.xml");
		postBodyContent = apiDriver.updateContent(postBodyContent, "update_fahrenheit",
				dataTable.getData(properties.getProperty("ENV") + "_Datasheet", "Fahrenheit"));
		String expectedCelcius = dataTable.getData(properties.getProperty("ENV") + "_Datasheet", "Celsius");
		String expectedResponse = apiDriver.readInput(getTemplatePath() + "FtoC_Output.xml");
		expectedResponse = apiDriver.updateContent(expectedResponse, "update_celsius", expectedCelcius);

		response = apiDriver.sendNReceive(uri, SERVICEMETHOD.POST, SERVICEFORMAT.XML, postBodyContent, headersMap, 200);
		apiDriver.assertIt(uri, response, ASSERT_RESPONSE.BODY, "", expectedResponse, COMPARISON.IS_EQUALS);
		apiDriver.assertIt(uri, response, ASSERT_RESPONSE.TAG, "//FahrenheitToCelsiusResult/text()", expectedCelcius,
				COMPARISON.IS_EQUALS);
		apiDriver.assertIt(uri, response, ASSERT_RESPONSE.HEADER, "", "text/xml;", COMPARISON.IS_EXISTS);
	}

	public void convertTemparatureInSequence() {
		Map<String, String> headersMap = headers.getHeaders2();
		Map<String, String> headersMap1 = headers.getHeaders4();
		ValidatableResponse response;
		String uri = "https://www.w3schools.com/xml/tempconvert.asmx";

		String postBodyContent = apiDriver.readInput(getTemplatePath() + "FtoC_Input.xml");
		postBodyContent = apiDriver.updateContent(postBodyContent, "update_fahrenheit", "50");
		response = apiDriver.sendNReceive(uri, SERVICEMETHOD.POST, SERVICEFORMAT.XML, postBodyContent, headersMap, 200);
		String celciusFromResponse = apiDriver.extractValue(response, "//FahrenheitToCelsiusResult/text()");
		apiDriver.assertIt(uri, response, ASSERT_RESPONSE.TAG, "//FahrenheitToCelsiusResult/text()", "10",
				COMPARISON.IS_EQUALS);

		String postBodyContent1 = apiDriver.readInput(getTemplatePath() + "CtoF_Input.xml");
		postBodyContent1 = apiDriver.updateContent(postBodyContent1, "update_celsius", celciusFromResponse);
		response = apiDriver.sendNReceive(uri, SERVICEMETHOD.POST, SERVICEFORMAT.XML, postBodyContent1, headersMap1,
				200);
		apiDriver.assertIt(uri, response, ASSERT_RESPONSE.TAG, "//CelsiusToFahrenheitResult/text()", "50",
				COMPARISON.IS_EQUALS);

	}

	private List<String> getList() {
		List<String> sampleList = new ArrayList<String>();
		sampleList.add("albert_park");
		sampleList.add("americas");
		sampleList.add("bahrain");
		sampleList.add("BAK");
		sampleList.add("catalunya");
		sampleList.add("hungaroring");
		sampleList.add("interlagos");
		sampleList.add("marina_bay");
		sampleList.add("monaco");
		sampleList.add("monza");
		sampleList.add("red_bull_ring");
		sampleList.add("rodriguez");
		sampleList.add("sepang");
		sampleList.add("shanghai");
		sampleList.add("silverstone");
		sampleList.add("sochi");
		sampleList.add("spa");
		sampleList.add("suzuka");
		sampleList.add("villeneuve");
		sampleList.add("yas_marina");

		return sampleList;
	}

	private String getTemplatePath() {
		File frameworkPath = new File(frameworkParameters.getRelativePath());

		String parentPath = frameworkPath.getParent();

		String templatePath = parentPath + Util.getFileSeparator() + "Miscellaneous_Classic" + Util.getFileSeparator()
				+ "EndPointInputs" + Util.getFileSeparator();

		return templatePath;
	}

}
