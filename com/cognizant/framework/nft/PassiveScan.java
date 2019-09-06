
package com.cognizant.framework.nft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;


public class PassiveScan {
	public static ClientApi api;
	public static JSONObject alert;
	public static Properties properties = new Properties();

	public static void zap_passivescan(String environment, String testURL)
			throws ClientApiException, IOException, ParserConfigurationException, SAXException, InterruptedException {

		InputStream fileInput = PerformanceNFT.class.getResourceAsStream("datafile_sec.properties");
		Properties properties = new Properties();
		properties.load(fileInput);
		String ZAP_ADDRESS = properties.getProperty("ZAP_ADDRESS");
		int ZAP_PORT = Integer.parseInt(properties.getProperty("ZAP_PORT"));
		String ZAP_API_KEY = properties.getProperty("ZAP_API_KEY");

		System.out.println(properties.getProperty("path"));
		api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

		api.pscan.enableAllScanners();
		// Thread.sleep(20000L);

		System.out.println("Passive Scan Completed");

		File myfile1 = new File("htmlreport_" + environment + ".html");
		myfile1.createNewFile();

		FileWriter htmlfile = new FileWriter(myfile1);
		htmlfile.write(new String(api.core.htmlreport()));
		htmlfile.flush();
		htmlfile.close();

		File myfile = new File("xmlreport_" + environment + ".xml");
		myfile.createNewFile();

		FileWriter xmlfile = new FileWriter(myfile);
		xmlfile.write(new String(api.core.xmlreport()));
		xmlfile.flush();
		xmlfile.close();

		File inputFile = new File("xmlreport_" + environment + ".xml");

		System.out.println(inputFile.toString());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		NodeList nl = doc.getElementsByTagName("riskcode");
		System.out.println(nl.getLength());
		int low = 0;
		int med = 0;
		int high = 0;
		int info = 0;
		for (int temp = 0; temp < nl.getLength(); temp++) {
			Node nNode = nl.item(temp);

			System.out.println(nNode.getTextContent());
			if (nNode.getTextContent().equalsIgnoreCase("1")) {
				low++;
				System.out.println(low);
			} else if (nNode.getTextContent().equalsIgnoreCase("2")) {
				med++;
				System.out.println(med);
			} else if (nNode.getTextContent().equalsIgnoreCase("3")) {
				high++;
				System.out.println(high);
			}
		}
		nl = doc.getElementsByTagName("riskdesc");
		System.out.println(nl.getLength());
		for (int temp = 0; temp < nl.getLength(); temp++) {
			Node nNode = nl.item(temp);

			System.out.println(nNode.getTextContent());
			if (nNode.getTextContent().contains("Informational")) {
				info++;
			}
		}
		System.out.println("info" + info);

		String export = "<risks><low count=\"" + low + "\"></low><med count=\"" + med + "\"></med><high  count=\""
				+ high + "\"></high><info count=\"" + info + "\"></info></risks>";

		JSONArray securitydetails = new JSONArray();
		JSONObject jsonString1 = new JSONObject();
		JSONObject jsonString = new JSONObject();
		jsonString.put("High", Integer.valueOf(high));
		jsonString.put("Medium", Integer.valueOf(med));
		jsonString.put("Low", Integer.valueOf(low));
		jsonString.put("info", Integer.valueOf(info));
		securitydetails.add(jsonString);
		jsonString1.put("securitydetails", securitydetails);
		System.out.println(jsonString1);

		PrintWriter writer = new PrintWriter("zap_" + environment + ".json", "UTF-8");
		writer.println(jsonString1.toString());

		writer.close();
		writer.flush();

		System.out.println("XML Parsing Completed");

		parseHTML(environment);

		System.out.println("HTML Parsing Completed");
		addEnvToFile(environment,testURL);

	}

	private static void addEnvToFile(String environment,  String testURL) {

		File envListFile = new File("environments.json");
		JSONArray envList = null;
		if (envListFile.exists()) {
			JSONParser jsonParser = new JSONParser();

			try (FileReader reader = new FileReader(envListFile)) {
				// Read JSON file
				Object obj = jsonParser.parse(reader);
				envList = (JSONArray) obj;
				boolean isFound = false;
				for (Object object : envList) {
					String env = (String) object;
					if (env.contains(environment)) {
						isFound = true;
						break;
					}
				}
				System.out.println(envList);
				if (!isFound) {
					envList.add(environment + "--" + testURL);
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			try (FileWriter file = new FileWriter(envListFile)) {

				file.write(envList.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			try (FileWriter file = new FileWriter(envListFile)) {
				envList = new JSONArray();
				envList.add(environment+"--"+testURL);
				file.write(envList.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void parseHTML(String env) {
		try {
			String localPath = "xmlreport_" + env+ ".xml";
			File fXmlFile = new File(localPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("alertitem");

			NodeList nList1 = doc.getElementsByTagName("instances");

			System.out.println("----------------------------" + nList.getLength());
			System.out.println("----------------------------" + nList1.getLength());

			JSONArray alertarray = new JSONArray();
			JSONObject mainobj = new JSONObject();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == 1) {
					Element eElement = (Element) nNode;
					if (eElement.getElementsByTagName("alert").item(0) != null) {
						System.out
								.println("alert : " + eElement.getElementsByTagName("alert").item(0).getTextContent());
					}
					if (eElement.getElementsByTagName("riskdesc").item(0) != null) {
						System.out.println(
								"riskdesc : " + eElement.getElementsByTagName("riskdesc").item(0).getTextContent());
					}
					if (eElement.getElementsByTagName("desc").item(0) != null) {
						System.out.println("Description : " + eElement.getElementsByTagName("desc").item(0)
								.getTextContent().replaceAll("<p>", "").replaceAll("</p>", ""));
					}
					for (int i = 0; i < eElement.getElementsByTagName("instance").getLength(); i++) {
						if (eElement.getElementsByTagName("uri").item(0) != null) {
							System.out
									.println("URL : " + eElement.getElementsByTagName("uri").item(0).getTextContent());
						}
						if (eElement.getElementsByTagName("method").item(0) != null) {
							System.out.println(
									"Method : " + eElement.getElementsByTagName("method").item(0).getTextContent());
						}
						if (eElement.getElementsByTagName("param").item(0) != null) {
							System.out.println(
									"Parameter : " + eElement.getElementsByTagName("param").item(0).getTextContent());
						}
					}
					if (eElement.getElementsByTagName("count").item(0) != null) {
						System.out.println(
								"Instances : " + eElement.getElementsByTagName("count").item(0).getTextContent());
					}
					if (eElement.getElementsByTagName("solution").item(0) != null) {
						System.out.println("Solution : " + eElement.getElementsByTagName("solution").item(0)
								.getTextContent().replaceAll("<p>", "").replaceAll("</p>", ""));
					}
					if (eElement.getElementsByTagName("otherinfo").item(0) != null) {
						System.out.println("*********************"
								+ eElement.getElementsByTagName("otherinfo").item(0).getTextContent());
						System.out.println("OtherInformation : " + eElement.getElementsByTagName("otherinfo").item(0)
								.getTextContent().replaceAll("<p>", "").replaceAll("</p>", ""));
					}
					alert = new JSONObject();
					if (eElement.getElementsByTagName("alert").item(0) != null) {
						alert.put("alert", eElement.getElementsByTagName("alert").item(0).getTextContent());
					} else {
						alert.put("OtherInformation", "Not Available");
					}
					if (eElement.getElementsByTagName("riskdesc").item(0) != null) {
						alert.put("riskdesc", eElement.getElementsByTagName("riskdesc").item(0).getTextContent());
					} else {
						alert.put("OtherInformation", "Not Available");
					}
					if (eElement.getElementsByTagName("desc").item(0) != null) {
						alert.put("Description", eElement.getElementsByTagName("desc").item(0).getTextContent()
								.replaceAll("<p>", "").replaceAll("</p>", ""));
					} else {
						alert.put("OtherInformation", "Not Available");
					}
					if (eElement.getElementsByTagName("uri").item(0) != null) {
						alert.put("URL", eElement.getElementsByTagName("uri").item(0).getTextContent());
					} else {
						alert.put("OtherInformation", "Not Available");
					}
					if (eElement.getElementsByTagName("method").item(0) != null) {
						alert.put("Method", eElement.getElementsByTagName("method").item(0).getTextContent());
					} else {
						alert.put("OtherInformation", "Not Available");
					}
					if (eElement.getElementsByTagName("param").item(0) != null) {
						alert.put("Parameter", eElement.getElementsByTagName("param").item(0).getTextContent());
					} else {
						alert.put("Parameter", "Not Available");
					}
					if (eElement.getElementsByTagName("count").item(0) != null) {
						alert.put("Instances", eElement.getElementsByTagName("count").item(0).getTextContent());
					} else {
						alert.put("OtherInformation", "Not Available");
					}
					if (eElement.getElementsByTagName("solution").item(0) != null) {
						alert.put("Solution", eElement.getElementsByTagName("solution").item(0).getTextContent()
								.replaceAll("<p>", "").replaceAll("</p>", ""));
					} else {
						alert.put("OtherInformation", "Not Available");
					}
					if (eElement.getElementsByTagName("otherinfo").item(0) != null) {
						alert.put("OtherInformation", eElement.getElementsByTagName("otherinfo").item(0)
								.getTextContent().replaceAll("<p>", "").replaceAll("</p>", ""));
					} else {
						alert.put("OtherInformation", "Not Available");
					}
				}
				alertarray.add(alert);
			}
			mainobj.put("instances", alertarray);

			System.out.println("alertarray" + mainobj);

			String zapfile = "zapraw_" + env + ".json";
			File impact = new File(zapfile);
			if (impact.exists()) {
				System.out.println("zapraw.json File is created");
			}
			FileWriter writerfile = new FileWriter(impact);
			writerfile.write(mainobj.toString());
			writerfile.flush();
			writerfile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
