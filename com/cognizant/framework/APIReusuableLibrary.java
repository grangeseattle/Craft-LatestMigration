package com.cognizant.framework;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cognizant.framework.selenium.SeleniumReport;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class APIReusuableLibrary {

	private SeleniumReport report;

	public enum SERVICEMETHOD {
		GET, POST, PUT, DELETE;
	}

	public enum SERVICEFORMAT {
		JSON, XML, TEXT, FILE;
	}

	public enum ASSERT_RESPONSE {
		BODY, TAG, HEADER, LIST;

	}

	public enum COMPARISON {
		IS_EXISTS, IS_EQUALS, IS_CONTAINS;
	}

	/**
	 * Function to set the Report Object
	 * 
	 * @param report
	 *            report object {@link Report}
	 * 
	 */
	public void setRport(SeleniumReport report) {
		this.report = report;
	}

	/**
	 * Function to read input file from given Path
	 * 
	 * @param inputFilePath
	 *            The Path of the given File
	 * @return The String of the input File
	 * 
	 */
	public String readInput(String inputFilePath) {
		String intputFileContent = "";
		BufferedReader bufferReader = null;
		try {
			String line;
			File inputFile = new File(inputFilePath);
			FileReader fileReader = new FileReader(inputFile);
			bufferReader = new BufferedReader(fileReader);
			while ((line = bufferReader.readLine()) != null) {
				intputFileContent += line.trim();
			}
		} catch (FileNotFoundException x) {
			throw new FrameworkException(x.getMessage());
		} catch (IOException ex) {
			throw new FrameworkException(ex.getMessage());
		} finally {
			try {
				if (bufferReader != null) {
					bufferReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return intputFileContent;

	}

	/**
	 * Function to get value from given XML Tag
	 * 
	 * @param response
	 *            The Response of the API
	 * @param tagToValidate
	 *            The XML Tag/Node from which value has to be fetched
	 * @return The String value
	 * 
	 */
	public String extractValue(ValidatableResponse response, String tagToValidate) {

		if (!tagToValidate.contains("//")) {
			return jsonParser(response, tagToValidate);
		} else {
			return xmlParser(response, tagToValidate);
		}
	}

	/**
	 * Function to get the response of an API
	 * 
	 * @param url
	 *            The URL of the Application
	 * @param methodType
	 *            The Service Method GET,POST, PUT ,DELETE {@link SERVICEMETHOD}
	 * @param headersMap
	 *            The headers passed as Map object , refer to
	 *            {@link HeadersForAPI} to create custom Headers
	 * @param statusCode
	 *            The Expected Status Code
	 * @return The Response {@link ValidatableResponse}
	 * 
	 */
	public ValidatableResponse sendNReceive(String url, SERVICEMETHOD methodType, Map<String, String> headersMap,
			int statusCode){
		ValidatableResponse response = null;
		try {
			switch (methodType) {
			case GET:

				if (headersMap != null) {
					response = RestAssured.given().relaxedHTTPSValidation().headers(headersMap).get(url).then()
							.assertThat().statusCode((Integer) statusCode);
					report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode, Status.PASS);
				} else {
					response = RestAssured.given().relaxedHTTPSValidation().get(url).then().assertThat()
							.statusCode((Integer) statusCode);
					report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode, Status.PASS);
				}

				break;

			case DELETE:
				if (headersMap != null) {
					response = RestAssured.given().relaxedHTTPSValidation().headers(headersMap).delete(url).then()
							.assertThat().statusCode((Integer) statusCode);
					report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode, Status.PASS);
				} else {
					response = RestAssured.given().relaxedHTTPSValidation().headers(headersMap).delete(url).then()
							.assertThat().statusCode((Integer) statusCode);
					report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode, Status.PASS);
				}
				break;

			default:
				break;
			}
		}

		catch (AssertionError x) {
			report.updateTestLog(url, "StatusCode: " + statusCode, x.getMessage() + "please check the request",
					Status.FAIL);
		} catch (Exception ex) {
			report.updateTestLog(url, "StatusCode: " + statusCode, ex.getMessage() + "please check the request",
					Status.FAIL);
		}
		return response;
	}

	/**
	 * Function to get the response of an API
	 * 
	 * @param url
	 *            The URL of the Application
	 * @param methodType
	 *            The Service Method GET,POST, PUT ,DELETE {@link SERVICEMETHOD}
	 * @param postBodyType
	 *            The Format of Post Body {@link SERVICEFORMAT}
	 * @param postBodyContent
	 *            The Post Body Content
	 * @param headersMap
	 *            The headers passed as Map object , refer to
	 *            {@link HeadersForAPI} to create custom Headers
	 * @param statusCode
	 *            The Expected Status Code
	 * @return The Response {@link ValidatableResponse}
	 * 
	 */
	public ValidatableResponse sendNReceive(String url, SERVICEMETHOD methodType, SERVICEFORMAT postBodyType,
			String postBodyContent, Map<String, String> headersMap, int statusCode) {
		ValidatableResponse response = null;
		try {
			Object postBody = getPostBodyContent(postBodyContent, postBodyType);
			ContentType contentType = getPostContentType(postBodyType);
			switch (methodType) {
			case POST:

				if (postBody instanceof File) {/* File */
					if (headersMap != null) {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation()
								.body((File) postBody).headers(headersMap).post(url).then().assertThat()
								.statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					} else {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation()
								.body((File) postBody).post(url).then().assertThat().statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					}
				} else if (postBody instanceof String) {/* String */
					if (headersMap != null) {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation().body(postBody)
								.headers(headersMap).post(url).then().assertThat().statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					} else {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation().body(postBody)
								.post(url).then().assertThat().statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					}
				}
				break;

			case PUT:

				if (postBody instanceof File) {/* File */
					if (headersMap != null) {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation()
								.body((File) postBody).headers(headersMap).put(url).then().assertThat()
								.statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					} else {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation()
								.body((File) postBody).put(url).then().assertThat().statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					}
				} else if (postBody instanceof String) {/* String */
					if (headersMap != null) {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation().body(postBody)
								.headers(headersMap).put(url).then().assertThat().statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					} else {
						response = RestAssured.given().contentType(contentType).relaxedHTTPSValidation().body(postBody)
								.put(url).then().assertThat().statusCode((Integer) statusCode);
						report.updateTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode,
								Status.PASS);
					}
				}
				break;

			default:
				break;
			}
		}

		catch (AssertionError x) {
			report.updateTestLog(url, "StatusCode: " + statusCode, x.getMessage() + "please check the request",
					Status.FAIL);
		} catch (Exception ex) {
			report.updateTestLog(url, "StatusCode: " + statusCode, ex.getMessage() + "please check the request",
					Status.FAIL);
		}
		return response;
	}

	/**
	 * Function to get updated String value
	 * 
	 * @param inputString
	 *            The input String
	 * @param paramVar
	 *            The Variable
	 * @param paramValue
	 *            The Variable Value to be updated
	 * @return The updated String
	 * 
	 */
	public String updateContent(String inputString, String paramVar, String paramValue) {
		String updatedString = null;
		try {
			updatedString = inputString.replaceAll("%%" + paramVar + "%%", paramValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedString;
	}

	/**
	 * Function to get the response of an API
	 * 
	 * @param url
	 *            The URL of the Application
	 * @param assertResponse
	 *            The Type of Assertion required for Response
	 *            {@link ASSERT_RESPONSE}
	 * @param tagToValidate
	 *            The Tag to be validated
	 * @param expectedResponse
	 *            The Expected Response
	 * @param comparison
	 *            The Type of Comparison to be done {@link COMPARISON }
	 * 
	 */
	public void assertIt(String url, ValidatableResponse response, ASSERT_RESPONSE assertResponse, String tagToValidate,
			Object expectedResponse, COMPARISON comparison) {
		Object actualResponse = null;
		switch (assertResponse) {

		case BODY:
			actualResponse = response.extract().body().asString();
			if (compareIt(comparison, actualResponse, expectedResponse))
				report.updateTestLog(url, expectedResponse, actualResponse, Status.PASS);
			else
				report.updateTestLog(url, expectedResponse, actualResponse, Status.FAIL);
			break;

		case TAG:
			if (!tagToValidate.contains("//"))
				actualResponse = jsonParser(response, tagToValidate);
			else
				actualResponse = xmlParser(response, tagToValidate);
			if (compareIt(comparison, actualResponse, expectedResponse))
				report.updateTestLog(url, expectedResponse, actualResponse, Status.PASS);
			else
				report.updateTestLog(url, expectedResponse, actualResponse, Status.FAIL);
			break;

		case HEADER:
			actualResponse = response.extract().contentType();
			if (compareIt(comparison, actualResponse, expectedResponse))
				report.updateTestLog(url, expectedResponse, actualResponse, Status.PASS);
			else
				report.updateTestLog(url, expectedResponse, actualResponse, Status.FAIL);
			break;

		case LIST:
			if (!tagToValidate.contains("//"))
				actualResponse = jsonParserAsList(response, tagToValidate);
			else
				actualResponse = xmlParserAsList(response, tagToValidate);
			if (compareIt(comparison, actualResponse, expectedResponse))
				report.updateTestLog(url, expectedResponse, actualResponse, Status.PASS);
			else
				report.updateTestLog(url, expectedResponse, actualResponse, Status.FAIL);
			break;

		default:
			break;

		}
	}

	/**
	 * Function to estabilish Connection to access DB
	 * 
	 * 
	 */
	public String establishConnectionNExecute(String dbConnection, String dbUname, String dbPassword, String sqlQuery) {
		String queryOutput = null;
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(dbConnection, dbUname, dbPassword);
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				queryOutput = rs.getString(1);
			}
		} catch (SQLException sq) {

		} catch (ClassNotFoundException cs) {

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return queryOutput;

	}

	/**
	 * Function to estabilish Connection to access DB
	 * 
	 * 
	 */
	public List<String> establishConnectionNExecuteNGetList(String dbConnection, String dbUname, String dbPassword,
			String sqlQuery) {
		List<String> queryOutput = new ArrayList<String>();
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(dbConnection, dbUname, dbPassword);
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			ResultSetMetaData rsmt = rs.getMetaData();
			int columnCount = rsmt.getColumnCount();
			List<List<String>> rowList = new LinkedList<List<String>>();
			while (rs.next()) {
				rowList.add(queryOutput);
				for (int column = 1; column <= columnCount; column++) {
					final Object value = rs.getObject(column);
					String valueAsString = String.valueOf(value);
					if (!valueAsString.contains("."))
						valueAsString = valueAsString + ".0";
					queryOutput.add(valueAsString.toUpperCase());
				}
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		} catch (ClassNotFoundException cf) {
			cf.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return queryOutput;
	}

	private Object getPostBodyContent(String postBodyContent, SERVICEFORMAT postBodyType) {

		if (postBodyType.equals(SERVICEFORMAT.FILE)) {
			File file = new File(postBodyContent);
			return file;
		} else {
			return postBodyContent;
		}
	}

	private ContentType getPostContentType(SERVICEFORMAT contentTypes) {
		ContentType contentType = null;
		switch (contentTypes) {
		case FILE:
			contentType = ContentType.BINARY;
			break;
		case JSON:
			contentType = ContentType.JSON;
			break;
		case XML:
			contentType = ContentType.XML;
			break;
		case TEXT:
			contentType = ContentType.TEXT;
			break;
		default:
			break;
		}
		return contentType;
	}

	private String xmlParser(ValidatableResponse response, String tagToValidate) {

		return response.extract().xmlPath().getString(tagToValidate);
	}

	private String jsonParser(ValidatableResponse response, String tagToValidate) {

		return response.extract().jsonPath().getString(tagToValidate);
	}

	private List<String> xmlParserAsList(ValidatableResponse response, String tagToValidate) {

		return response.extract().xmlPath().getList(tagToValidate);
	}

	private Object jsonParserAsList(ValidatableResponse response, String tagToValidate) {

		return response.extract().jsonPath().getList(tagToValidate);
	}

	private Boolean compareIt(COMPARISON comparator, Object actualResponse, Object expectedResponse) {
		switch (comparator) {
		case IS_EQUALS:
			if ((actualResponse).equals(expectedResponse))
				return true;
			else
				return false;

		case IS_CONTAINS:
			if (((String) actualResponse).contains((String) expectedResponse))
				return true;
			else
				return false;

		case IS_EXISTS:
			if (((String) actualResponse).contains((String) expectedResponse))
				return true;
			else
				return false;
		default:
			break;
		}

		return null;
	}

	public String xmlParserByTag(ValidatableResponse response, String tagToValidate) throws SAXException {

		String elementValue = null;
		String inputXml = response.extract().asString();
		DocumentBuilder builder;
		Document doc;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(false);
			InputStream in = new ByteArrayInputStream(inputXml.getBytes("utf-8"));
			builder = factory.newDocumentBuilder();
			doc = builder.parse(in);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(tagToValidate);
			elementValue = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (Exception e) {
			throw new FrameworkException("API Error", e.getMessage());
		} finally {
		}
		return elementValue;

	}

}