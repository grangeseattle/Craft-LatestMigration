package com.cognizant.framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cognizant.framework.nft.BrokenLinks;
import com.cognizant.framework.nft.HTMLVisualValidationReport;
import com.cognizant.framework.selenium.ResultSummaryManager;

public class ConsolidatedReportTemplate {

	private ResultSummaryManager resultSummaryManager = ResultSummaryManager.getInstance();

	private String functionalSummary = "<tr>Please enable 'Functional Test' from properties file</tr>",
			functionalReportHtml = "", functionClass = "isDisabled";
	private String visualValidationSummaryHtml = "<tr>Please enable 'Visual Validation' from properties file</tr>",
			visualValidationReportHtml = "", visualClass = "isDisabled",

			brokenLinkSummaryHtml = "<tr>Please enable 'BrokenLink Validation' from properties file</tr>",
			brokenLinkReportHtml = "", brokenLinkClass = "isDisabled",

			performanceSummaryHtml = "<tr>Please enable 'Performance Validation' from properties file</tr>",
			performanceReportHtml = "", performanceClass = "isDisabled",

			securitySummaryHtml = "<tr>Please enable 'Security Validation' from properties file</tr>",
			securityReportHtml = "", securityClass = "isDisabled",

			accessibilitySummaryHtml = "<tr>Please enable 'Accesibility Validation' from properties file</tr>",
			accessibilityReportHtml = "", accessibilityClass = "isDisabled";

	public static TestParameters isTestAvailableInCompInfo(TestParameters parameters) {
		TestParameters temp = null;
		for (Entry<TestParameters, ArrayList<Object>> entry : Report.comparisonTestsInfoForOtherReports.entrySet()) {
			TestParameters testParameters = entry.getKey();
			if (testParameters.getCurrentScenario().equals(parameters.getCurrentScenario())
					&& testParameters.getCurrentTestcase().equals(parameters.getCurrentTestcase())
					&& !testParameters.getCurrentTestInstance().equals(parameters.getCurrentTestInstance())) {
				temp = testParameters;
				break;
			}

		}
		return temp;
	}

	public void createConsolidatedReport() {
		String projRunReportPath = RegressionReportComparison.getReportPath();
		Properties properties = Settings.getInstance();
		try {
			String encryptedPath = WhitelistingPath.cleanStringForFilePath(System.getProperty("user.dir"));
			String relativePath = new File(encryptedPath).getAbsolutePath();
			if (relativePath.contains("supportlibraries")) {
				relativePath = new File(encryptedPath).getParent();
			}
			FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
			frameworkParameters.setRelativePath(relativePath);
			HashMap<TestParameters, ArrayList<Object>> comparisonTestsInfo = Report.comparisonTestsInfoForOtherReports;

			int totalCountEnv1 = 0;
			int passedCountEnv1 = 0;
			int failedCountEnv1 = 0;

			int totalCountEnv2 = 0;
			int passedCountEnv2 = 0;
			int failedCountEnv2 = 0;

			// to count failed and passed test case summary
			try {
				int size = comparisonTestsInfo.size();
				int count = size;
				TestParameters parameters = null;
				TestParameters parameters2 = null;
				outerLoop: for (int i = 0; i < size; i++) {

					if (count > 0) {
						HashMap<TestParameters, ArrayList<Object>> tempMap = comparisonTestsInfo;
						innerLoop: for (Entry<TestParameters, ArrayList<Object>> entry : tempMap.entrySet()) {
							parameters = entry.getKey();
							parameters2 = isTestAvailableInCompInfo(parameters);
							break innerLoop;
						}

						if (parameters != null && parameters2 != null) {
							ArrayList<Object> paraValue1 = comparisonTestsInfo.get(parameters);
							ArrayList<Object> paraValue2 = comparisonTestsInfo.get(parameters2);
							String testStatus1 = (String) paraValue1.get(3);
							String testStatus2 = (String) paraValue2.get(3);

							if (parameters.getCurrentTestInstance().equalsIgnoreCase("Instance1")) {
								if (testStatus1.equalsIgnoreCase("passed")) {
									passedCountEnv1++;
								} else if (testStatus1.equalsIgnoreCase("failed")) {
									failedCountEnv1++;
								}
							} else {

								if (testStatus1.equalsIgnoreCase("passed")) {
									passedCountEnv2++;
								} else if (testStatus1.equalsIgnoreCase("failed")) {
									failedCountEnv2++;
								}
							}

							if (parameters2.getCurrentTestInstance().equalsIgnoreCase("Instance2")) {
								if (testStatus2.equalsIgnoreCase("passed")) {
									passedCountEnv2++;
								} else if (testStatus2.equalsIgnoreCase("failed")) {
									failedCountEnv2++;
								}
							} else {

								if (testStatus2.equalsIgnoreCase("passed")) {
									passedCountEnv1++;
								} else if (testStatus2.equalsIgnoreCase("failed")) {
									failedCountEnv1++;
								}
							}

							totalCountEnv1++;
							totalCountEnv2++;

							comparisonTestsInfo.remove(parameters);
							comparisonTestsInfo.remove(parameters2);
							count -= 2;
						} else if (parameters != null && parameters2 == null) {

							ArrayList<Object> paraValue1 = comparisonTestsInfo.get(parameters);
							ReportType reportType1 = (ReportType) paraValue1.get(0);
							String testReportName1 = (String) paraValue1.get(1);
							String executionTime1 = (String) paraValue1.get(2);
							String testStatus1 = (String) paraValue1.get(3);

//						reportType1.updateResultSummary(parameters, testReportName1, executionTime1, testStatus1);
							comparisonTestsInfo.remove(parameters);
							count -= 1;

							if (parameters.getCurrentTestInstance().equalsIgnoreCase("Instance1")) {
								if (testStatus1.equalsIgnoreCase("passed")) {
									passedCountEnv1++;
								} else if (testStatus1.equalsIgnoreCase("failed")) {
									failedCountEnv1++;
								}

								totalCountEnv1++;
							} else {

								if (testStatus1.equalsIgnoreCase("passed")) {
									passedCountEnv2++;
								} else if (testStatus1.equalsIgnoreCase("failed")) {
									failedCountEnv2++;
								}

								totalCountEnv2++;
							}

						} else if (comparisonTestsInfo.size() == 0) {

							break outerLoop;
						}

					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			JSONObject jsonObject1 = null, jsonObject2 = null;
			String env1Security = null, env2Security = null;

			if (NFT.executeSecurity) {
				try {
					File envListFile = new File(
							projRunReportPath + File.separator + "NFTReports" + File.separator + "environments.json");
					JSONArray envList = null;
					if (envListFile.exists()) {
						JSONParser jsonParser = new JSONParser();

						try (FileReader reader = new FileReader(envListFile)) {
							// Read JSON file
							Object obj = jsonParser.parse(reader);
							envList = (JSONArray) obj;
							boolean isFound = false;

							if (envList.size() == 2) {
								env1Security = envList.get(0).toString().split("--")[0];
								env2Security = envList.get(1).toString().split("--")[0];
							} else {
								env1Security = envList.get(0).toString().split("--")[0];
							}
							reader.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					File envFile1 = null;
					JSONParser jsonParser = new JSONParser();
					FileReader fileReader1 = null, fileReader2 = null;
					if (env1Security != null) {
						envFile1 = new File(projRunReportPath + File.separator + "NFTReports" + File.separator + "zap_"
								+ env1Security + ".json");
						fileReader1 = new FileReader(envFile1);
						jsonObject1 = (JSONObject) ((JSONArray) ((JSONObject) jsonParser.parse(fileReader1))
								.get("securitydetails")).get(0);
						System.out.println("jsonObject1 " + jsonObject1.toJSONString());
					}
					File envFile2 = null;
					if (env2Security != null) {
						envFile2 = new File(projRunReportPath + File.separator + "NFTReports" + File.separator + "zap_"
								+ env2Security + ".json");
						fileReader2 = new FileReader(envFile2);
						jsonObject2 = (JSONObject) ((JSONArray) ((JSONObject) jsonParser.parse(fileReader2))
								.get("securitydetails")).get(0);
						System.out.println("jsonObject2 " + jsonObject2.toJSONString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			long criticalEnv1 = 0l;
			long moderateEnv1 = 0;
			long seriousEnv1 = 0;
			long minorEnv1 = 0;

			int criticalEnv2 = 0;
			int moderateEnv2 = 0;
			int seriousEnv2 = 0;
			int minorEnv2 = 0;
			String env1Accessibility = null, env2Accessibility = null;
			if (NFT.executeAccessibility) {
				try {

					File donutFile = new File(
							projRunReportPath + File.separator + "NFTReports" + File.separator + "donut.json");
					JSONParser jsonParser = new JSONParser();
					FileReader fileReader = new FileReader(donutFile);
					JSONArray jsonArray = (JSONArray) ((JSONObject) jsonParser.parse(fileReader)).get("rundetails");
					for (Object object : jsonArray) {
						JSONObject jsonObject = (JSONObject) object;
						String env = (String) jsonObject.get("environment");
						Long critical = (Long) jsonObject.get("critical");
						Long moderate = (Long) jsonObject.get("moderate");
						Long serious = (Long) jsonObject.get("serious");
						Long minor = (Long) jsonObject.get("minor");
						if (env1Accessibility == null) {
							env1Accessibility = env;

						}

						if (env2Accessibility == null
								&& (env1Accessibility != null && !env.equalsIgnoreCase(env1Accessibility))) {
							env2Accessibility = env;
						}
						if (env1Accessibility != null) {
							if (env1Accessibility.equalsIgnoreCase(env)) {
								criticalEnv1 += critical;
								moderateEnv1 += moderate;
								seriousEnv1 += serious;
								minorEnv1 += minor;
								continue;
							}
						}

						if (env2Accessibility != null) {
							if (env2Accessibility.equalsIgnoreCase(env)) {
								criticalEnv2 += critical;
								moderateEnv2 += moderate;
								seriousEnv2 += serious;
								minorEnv2 += minor;
							}
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

//			 [  { x:  1, y: 5678, label: "Test 1" }]
			JSONArray jsonArrayEnv1 = new JSONArray();
			JSONArray jsonArrayEnv2 = new JSONArray();

			if (NFT.executePerformance) {
				try {

					String env1 = null, env2 = null;
					File donutFile = new File(
							projRunReportPath + File.separator + "NFTReports" + File.separator + "performance.json");
					JSONParser jsonParser = new JSONParser();
					FileReader fileReader = new FileReader(donutFile);
					JSONArray jsonArray = (JSONArray) (jsonParser.parse(fileReader));
					int countEnv1 = 0;
					int countEnv2 = 0;

					for (Object object : jsonArray) {
						JSONObject jsonObject = (JSONObject) object;
						String env = (String) jsonObject.get("Environment");
						String totalLoadTime = (String) jsonObject.get("TotalLoadTime");
						String testURL = (String) jsonObject.get("TestURL");
						String scenarioTestcase = (String) jsonObject.get("ScenarioTestcase");
						if (env1 == null) {
							env1 = env;
						}

						if (env2 == null && (env1 != null && !env.equalsIgnoreCase(env1))) {
							env2 = env;
						}
						if (env1 != null) {
							if (env1.equalsIgnoreCase(env)) {
								JSONObject jsobObjectJS = new JSONObject();
								jsobObjectJS.put("x", ++countEnv1);
								jsobObjectJS.put("y", Integer.parseInt(totalLoadTime));
								jsobObjectJS.put("label", scenarioTestcase);

								jsonArrayEnv1.add(jsobObjectJS);
								continue;
							}
						}

						if (env2 != null) {
							if (env2.equalsIgnoreCase(env)) {

								JSONObject jsobObjectJS = new JSONObject();
								jsobObjectJS.put("x", ++countEnv2);
								jsobObjectJS.put("y", Integer.parseInt(totalLoadTime));
								jsobObjectJS.put("label", scenarioTestcase);

								jsonArrayEnv2.add(jsobObjectJS);
							}
						}

					}

					File performanceHTMLTemplate = new File(projRunReportPath + File.separator + "NFTReports"
							+ File.separator + "performance_summary_graph_template.html");
					StringBuilder builder = new StringBuilder();
					FileReader performanceFileReader = new FileReader(performanceHTMLTemplate);
					BufferedReader performanceBufferedReader = new BufferedReader(performanceFileReader);
					String line = "";
					while ((line = performanceBufferedReader.readLine()) != null) {
						builder.append(line);
						builder.append("\n");
					}

					line = builder.toString().replaceAll("ENV_1",
							File.separator + properties.getProperty("Environment_1") + File.separator);
					line = line.replaceAll("ENV_2",
							File.separator + properties.getProperty("Environment_2") + File.separator);
					line = line.replaceAll("DATA_POINTS_1", jsonArrayEnv1.toJSONString());
					line = line.replaceAll("DATA_POINTS_2", jsonArrayEnv2.toJSONString());

					performanceBufferedReader.close();
					performanceFileReader.close();
					File performanceHTML = new File(projRunReportPath + File.separator + "NFTReports" + File.separator
							+ "performance_summary_graph.html");
					FileWriter performanceFileWriter = new FileWriter(performanceHTML);
					BufferedWriter performanceBufferedWriter = new BufferedWriter(performanceFileWriter);
					performanceBufferedWriter.write(line);
					;

					performanceFileWriter.flush();
					performanceBufferedWriter.flush();
					performanceBufferedWriter.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				if (NFT.executeVisualValidation) {
					visualValidationSummaryHtml = "              <tbody> " + "                <tr> "
							+ "                <td>Total Visual Comparision</td> " + "                <td>"
							+ HTMLVisualValidationReport.totalComparisons + "</td> " + "                </tr>"
							+ "                <tr> " + "                <td>Visual Resemblence</td> "
							+ "                <td>" + HTMLVisualValidationReport.visuSim + "</td> "
							+ "                </tr>" + "                <tr> "
							+ "                <td>Visual Contrast</td> " + "                <td>"
							+ HTMLVisualValidationReport.visuDiff + "</td> " + "                </tr>"
							+ "                <tr> " + "                <td>Resemblence Percentage</td> "
							+ "                <td>"
							+ (HTMLVisualValidationReport.visuSim / HTMLVisualValidationReport.totalComparisons) * 100
							+ "</td> " + "                </tr>     " + "                <tr> "
							+ "                <td>Contrast Percentage</td> " + "                <td>"
							+ (HTMLVisualValidationReport.visuDiff / HTMLVisualValidationReport.totalComparisons) * 100
							+ "</td> " + "                </tr> " + "              </tbody> ";
					visualValidationReportHtml = "    <div id='visual' class='w3-container w3-padding-large' style='margin-bottom:32px;background-color: #f2f7f8;height:960px' >"
							+ "      <div id='view' class='tab-pane fade in active'>"
							+ "        <iframe  src='./Visual_Validation_Report.html' style='width: 100%; margin-top:30px; background-color: #f2f7f8;height: 710px; overflow: auto; border:none;'></iframe>"
							+ "      </div>" + "    </div>";
					visualClass = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (NFT.executeBrokenLinks) {

				int totalCount1 = BrokenLinks.list.get(1) + BrokenLinks.list.get(0);
				int totalCount2 = BrokenLinks.list.get(1) + BrokenLinks.list.get(0);

				if (totalCount1 != 0 && totalCount2 != 0) {
					brokenLinkSummaryHtml = "            <thead> " + "              <tr> " + "              <th></th> "
							+ "              <th>" + properties.getProperty("Environment_1") + "</th> "
							+ "              <th>" + properties.getProperty("Environment_2") + "</th> "
							+ "              </tr> " + "            </thead> " + "            <tbody> "
							+ "              <tr> " + "              <th>BrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(1) + "</td> " + "              <td>" + BrokenLinks.list.get(3)
							+ "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">UnbrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(0) + "</td> " + "              <td>" + BrokenLinks.list.get(2)
							+ "</td> " + "              </tr> " + "            </tbody> ";

				} else if (totalCount1 != 0 && totalCount2 == 0) {
					brokenLinkSummaryHtml = "            <thead> " + "              <tr> " + "              <th></th> "
							+ "              <th>" + properties.getProperty("Environment_1") + "</th> "
							+ "              </tr> " + "            </thead> " + "            <tbody> "
							+ "              <tr> " + "              <th>BrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(1) + "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">UnbrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(0) + "</td> " + "              </tr> " + "            </tbody> ";
				} else if (totalCount1 == 0 && totalCount2 != 0) {
					brokenLinkSummaryHtml = "            <thead> " + "              <tr> " + "              <th></th> "
							+ "              <th>" + properties.getProperty("Environment_2") + "</th> "
							+ "              </tr> " + "            </thead> " + "            <tbody> "
							+ "              <tr> " + "              <th>BrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(3) + "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">UnbrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(2) + "</td> " + "              </tr> " + "            </tbody> ";
				} else if (totalCount1 == 0 && totalCount2 == 0) {
					brokenLinkSummaryHtml = "            <thead> " + "              <tr> " + "              <th></th> "
							+ "              <th>" + properties.getProperty("Environment_1") + "</th> "
							+ "              <th>" + properties.getProperty("Environment_2") + "</th> "
							+ "              </tr> " + "            </thead> " + "            <tbody> "
							+ "              <tr> " + "              <th>BrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(1) + "</td> " + "              <td>" + BrokenLinks.list.get(3)
							+ "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">UnbrokenLinks</th> " + "              <td>"
							+ BrokenLinks.list.get(0) + "</td> " + "              <td>" + BrokenLinks.list.get(2)
							+ "</td> " + "              </tr> " + "            </tbody> ";

				}

				brokenLinkReportHtml = "    <div id='broken' class='w3-container w3-padding-large' style='margin-bottom:32px;background-color: #f2f7f8;height:960px' >"
						+ "      <div id='view' class='tab-pane fade in active'>"
						+ "        <iframe  src='./BrokenLinks.html' style='width: 100%; margin-top:30px; background-color: #f2f7f8;height: 710px; overflow: auto; border:none;'></iframe>"
						+ "      </div>" + "    </div>";
				brokenLinkClass = "";

			}
			if (NFT.executePerformance) {
				performanceSummaryHtml = "<thead>" + "<tr>"
						+ "<td>TransactionName</td><td id='env1'>Load-T-E1</td><td id='env2'>Load-T-E2</td>"
						+ "<tr><td>URL-1</td><td id='medium'>3085</td><td id='medium2'>3606</td></tr>"
						+ "<tr><td>URL-2</td><td id='info'>3174</td><td id='info2'>3698</td></tr>" + "</tr>"
						+ "</thead>";
				performanceReportHtml = "    <div id='performance' class='w3-container w3-padding-large' style='margin-bottom:32px;background-color: #f2f7f8;height:960px' >"
						+ "      <div id='view' class='tab-pane fade in active'>" + "        <iframe  src='"
						+ projRunReportPath
						+ "/NFTReports/performance.html' scrolling='no' style='width: 100%; margin-top:30px; background-color: #f2f7f8;height: 807px; overflow: auto; border:none;'></iframe>"
						+ "      </div>" + "    </div>";
				performanceClass = "";
			}
			if (NFT.executeSecurity) {
				if (jsonObject1 != null && jsonObject2 != null) {
					securitySummaryHtml = "<thead>" + "<tr>" + "<td>Risk Level</td><td id='env1'>Alerts-" + env1Security
							+ "</td><td id='env2'>Alerts-" + env2Security + "</td></tr>"
							+ "<tr><td>High</td><td id='high'>" + jsonObject1.get("High") + "</td><td id='high2'>"
							+ jsonObject2.get("High") + "</td>" + "</tr>" + "<tr><td>Medium</td><td id='medium'>"
							+ jsonObject1.get("Medium") + "</td><td id='medium2'>" + jsonObject2.get("Medium")
							+ "</td></tr>" + "<tr><td>Low</td><td id='low'>" + jsonObject1.get("Low")
							+ "</td><td id='low2'>" + jsonObject2.get("Low") + "</td></tr>"
							+ "<tr><td>info</td><td id='info'>" + jsonObject1.get("info") + "</td><td id='info2'>"
							+ jsonObject2.get("info") + "</td>" + "</tr>" + "</thead>";

				} else if (jsonObject1 != null && jsonObject2 == null) {
					securitySummaryHtml = "<thead>" + "<tr>" + "<td>Risk Level</td><td id='env1'>" + env1Security
							+ "</td></tr>" + "<tr><td>High</td><td id='high'>" + jsonObject1.get("High") + "</td></tr>"
							+ "<tr><td>Medium</td><td id='medium'>" + jsonObject1.get("Medium") + "</td></tr>"
							+ "<tr><td>Low</td><td id='low'>" + jsonObject1.get("Low") + "</td></tr>"
							+ "<tr><td>info</td><td id='info'>" + jsonObject1.get("info") + "</td>" + "</tr>"
							+ "</thead>";

				} else if (jsonObject1 == null && jsonObject2 != null) {
					securitySummaryHtml = "<thead>" + "<tr>" + "<td>Risk Level</td><td id='env1'>" + env2Security
							+ "</td></tr>" + "<tr><td>High</td><td id='high'>" + jsonObject2.get("High") + "</td></tr>"
							+ "<tr><td>Medium</td><td id='medium'>" + jsonObject2.get("Medium") + "</td></tr>"
							+ "<tr><td>Low</td><td id='low'>" + jsonObject2.get("Low") + "</td></tr>"
							+ "<tr><td>info</td><td id='info'>" + jsonObject2.get("info") + "</td>" + "</tr>"
							+ "</thead>";

				}
				securityReportHtml = "  <div id='security' class='w3-container w3-padding-large' style='margin-bottom:32px;background-color: #f2f7f8;height:960px' >"
						+ "    <div id='view' class='tab-pane fade in active'>" + "      <iframe  src='"
						+ projRunReportPath
						+ "/NFTReports/zap.html' style='width: 100%; margin-top:30px; background-color: #f2f7f8;height: 710px; overflow: auto; border:none;'></iframe>"
						+ "    </div>" + "  </div>";
				securityClass = "";
			}
			if (NFT.executeAccessibility) {
				if (env1Accessibility != null && env2Accessibility != null) {
					accessibilitySummaryHtml = "            <thead> " + "              <tr> "
							+ "              <th></th> " + "              <th>" + env1Accessibility + "</th> "
							+ "              <th>" + env2Accessibility + "</th> " + "              </tr> "
							+ "            </thead> " + "            <tbody> " + "              <tr> "
							+ "              <th>Critical Case</th> " + "              <td>" + criticalEnv1 + "</td> "
							+ "              <td>" + criticalEnv2 + "</td> " + "              </tr> "
							+ "              <tr> " + "              <th style=\"align:left\">Serious Case</th> "
							+ "              <td>" + seriousEnv1 + "</td> " + "              <td>" + seriousEnv2
							+ "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">moderate Case</th> " + "              <td>"
							+ moderateEnv1 + "</td> " + "              <td>" + moderateEnv2 + "</td> "
							+ "              </tr> " + "            </tbody> ";
				} else if (env1Accessibility != null && env2Accessibility == null) {
					accessibilitySummaryHtml = "            <thead> " + "              <tr> "
							+ "              <th></th> " + "              <th>" + env1Accessibility + "</th> "
							+ "              </tr> " + "            </thead> " + "            <tbody> "
							+ "              <tr> " + "              <th>Critical Case</th> " + "              <td>"
							+ criticalEnv1 + "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">Serious Case</th> " + "              <td>"
							+ seriousEnv1 + "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">moderate Case</th> " + "              <td>"
							+ moderateEnv1 + "</td> " + "              </tr> " + "            </tbody> ";
				} else if (env1Accessibility == null && env2Accessibility != null) {
					accessibilitySummaryHtml = "            <thead> " + "              <tr> "
							+ "              <th></th> " + "              <th>" + env1Accessibility + "</th> "
							+ "              </tr> " + "            </thead> " + "            <tbody> "
							+ "              <tr> " + "              <th>Critical Case</th> " + "              <td>"
							+ criticalEnv1 + "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">Serious Case</th> " + "              <td>"
							+ seriousEnv1 + "</td> " + "              </tr> " + "              <tr> "
							+ "              <th style=\"align:left\">moderate Case</th> " + "              <td>"
							+ moderateEnv1 + "</td> " + "              </tr> " + "            </tbody> ";
				}
				accessibilityReportHtml = "  <div id='accessibility' class='w3-container w3-padding-large' style='margin-bottom:32px;background-color: #f2f7f8;height:960px' >"
						+ "    <div id='view' class='tab-pane fade in active'>" + "      <iframe  src='"
						+ projRunReportPath
						+ "/NFTReports/accesibility.html' style='width: 100%; margin-top:30px; background-color: #f2f7f8;height: 710px; overflow: auto; border:none;'></iframe>"
						+ "    </div>" + "  </div>";
				accessibilityClass = "";
			}

			String isFunctionalTest = "false";
			if (totalCountEnv1 != 0 && totalCountEnv2 != 0 && NFT.executeFunctional) {

				functionalSummary = "				<thead>" + "				<tr>" + "<td>Params</td><td>"
						+ properties.getProperty("Environment_1") + "</td><td>"
						+ properties.getProperty("Environment_2") + "</td>" + "</tr>" + "</thead>"
						+ "              <tbody> " + "                <tr> "
						+ "                <td>Total Test Cases</td> " + "                <td>" + (totalCountEnv1)
						+ "</td> " + "<td>" + (totalCountEnv2) + "</td> " + "                </tr>"
						+ "                <tr> " + "                <td>Test Case Pass</td> " + "                <td>"
						+ passedCountEnv1 + "</td> " + "<td>" + passedCountEnv2 + "</td> " + "                </tr>"
						+ "                <tr> " + "                <td>Test Case Fail</td> " + "                <td>"
						+ failedCountEnv1 + "</td> " + "<td>" + failedCountEnv2 + "</td> " + "                </tr>"
						+ "                <tr> " + "                <td>Pass Percentage</td> " + "                <td>"
						+ (passedCountEnv1 / (totalCountEnv1)) * 100 + "</td> " + "<td>"
						+ (passedCountEnv2 / (totalCountEnv2)) * 100 + "</td> " + "                </tr>     "
						+ "                <tr> " + "                <td>Fail Percentage</td> " + "                <td>"
						+ (failedCountEnv1 / (totalCountEnv1)) * 100 + "</td> " + "<td>"
						+ (failedCountEnv2 / (totalCountEnv2)) * 100 + "</td> " + "                </tr> "
						+ "              </tbody> ";
				functionClass = "";

			} else if (totalCountEnv2 == 0 && NFT.executeFunctional) {

				functionalSummary = "				<thead>" + "				<tr>" + "<td>Params</td><td>"
						+ properties.getProperty("Environment_1") + "</td>" + "</tr>" + "</thead>"
						+ "              <tbody> " + "                <tr> "
						+ "                <td>Total Test Cases</td> " + "                <td>" + (totalCountEnv1)
						+ "</td> " + "                </tr>" + "                <tr> "
						+ "                <td>Test Case Pass</td> " + "                <td>" + passedCountEnv1
						+ "</td> " + "                </tr>" + "                <tr> "
						+ "                <td>Test Case Fail</td> " + "                <td>" + failedCountEnv1
						+ "</td> " + "                </tr>" + "                <tr> "
						+ "                <td>Pass Percentage</td> " + "                <td>"
						+ (passedCountEnv1 / (totalCountEnv1)) * 100 + "</td> " + "                </tr>     "
						+ "                <tr> " + "                <td>Fail Percentage</td> " + "                <td>"
						+ (failedCountEnv1 / (totalCountEnv1)) * 100 + "</td> " + "                </tr> "
						+ "              </tbody> ";

				functionClass = "";
			} else if (totalCountEnv1 == 0 && NFT.executeFunctional) {

				functionalSummary = "				<thead>" + "				<tr>" + "<td>Params</td><td>"
						+ properties.getProperty("Environment_2") + "</td>" + "</tr>" + "</thead>"
						+ "              <tbody> " + "                <tr> "
						+ "                <td>Total Test Cases</td> " + "                <td>" + (totalCountEnv2)
						+ "</td> " + "                </tr>" + "                <tr> "
						+ "                <td>Test Case Pass</td> " + "                <td>" + passedCountEnv2
						+ "</td> " + "                </tr>" + "                <tr> "
						+ "                <td>Test Case Fail</td> " + "                <td>" + failedCountEnv2
						+ "</td> " + "                </tr>" + "                <tr> "
						+ "                <td>Pass Percentage</td> " + "                <td>"
						+ (passedCountEnv2 / (totalCountEnv2)) * 100 + "</td> " + "                </tr>     "
						+ "                <tr> " + "                <td>Fail Percentage</td> " + "                <td>"
						+ (failedCountEnv2 / (totalCountEnv2)) * 100 + "</td> " + "                </tr> "
						+ "              </tbody> ";

				functionClass = "";
			}

			if (NFT.executeFunctional) {
				functionalReportHtml = "<div class='w3-container' id='functional' style='background-color: #f2f7f8;height:960px'>"
						+ "      <div id='view1' class='tab-pane fade in active'>"
						+ "        <iframe src='./Summary.html' style='width: 100%; margin-top:30px; height: 710px; overflow: auto; border:none;'></iframe>"
						+ "      </div>" + "    </div>";
				functionClass = "";
			}
			String htmlString = "<!DOCTYPE html>" + "<html style='background-color: #f2f7f8'>"
					+ "<title>CraftReport</title>" + "<meta charset='UTF-8'>"
					+ "<meta name='viewport' content='width=device-width, initial-scale=1'>"
					+ "<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>"
					+ "<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Raleway'>"
					+ "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>"
					+ "<style>" + "  body,h1,h2,h3,h4,h5,h6" + "  {" + "    font-family: 'Raleway', sans-serif" + "  }"
					+ "  table  thead  tr  th {" + "    text-align : center" + "  }" + ".isDisabled {"
					+ "color: currentColor;" + "opacity: 0.5;" + "text-decoration: none;" + "pointer-events: none" + "}"
					+ "</style>" + "" + "<body class='w3-light-grey w3-content' style='max-width:1600px'>" + ""
					+ "  <nav class='w3-sidebar w3-collapse w3-white w3-animate-left' style='z-index:3;width:300px;' id='mySidebar'><br>"
					+ "    <div class='w3-container'>" + "      <h4><b>Cloud Anchor</b></h4>"
					+ "      <p class='w3-text-grey'>Report Dashboard</p>" + "    </div>"
					+ "    <div class='w3-bar-block'>"
					+ "      <a href='#home' onclick='w3_close()' class='w3-bar-item w3-button w3-padding w3-text-teal'><i class='fa fa-th-large fa-fw w3-margin-right'></i>HOME</a>"
					+ "      <a href='#functional' onclick='w3_close()' class='w3-bar-item w3-button w3-padding w3-text-teal "
					+ functionClass + "'><i class='fa fa-th-large fa-fw w3-margin-right'></i>FUNCTIONAL</a>"
					+ "      <a href='#performance' onclick='w3_close()' class='w3-bar-item w3-button w3-padding w3-text-teal "
					+ performanceClass + "'><i class='fa fa-th-large fa-fw w3-margin-right'></i>PERFORMANCE</a>"
					+ "      <a href='#security' onclick='w3_close()' class='w3-bar-item w3-button w3-padding w3-text-teal "
					+ securityClass + "'><i class='fa fa-th-large fa-fw w3-margin-right'></i>SECURITY</a>"
					+ "      <a href='#accessibility' onclick='w3_close()' class='w3-bar-item w3-button w3-padding w3-text-teal "
					+ accessibilityClass + "'><i class='fa fa-th-large fa-fw w3-margin-right'></i>ACCESSIBILITY</a>"
					+ "      <a href='#visual' onclick='w3_close()' class='w3-bar-item w3-button w3-padding w3-text-teal "
					+ visualClass + "'><i class='fa fa-th-large fa-fw w3-margin-right'></i>VISUAL</a>"
					+ "      <a href='#broken' onclick='w3_close()' class='w3-bar-item w3-button w3-padding w3-text-teal "
					+ brokenLinkClass + "'><i class='fa fa-th-large fa-fw w3-margin-right'></i>BROKENLINKS</a>"
					+ "    </div>" + "  </nav>" + ""
					+ "  <div class='w3-overlay w3-hide-large w3-animate-opacity' onclick='w3_close()' style='cursor:pointer' title='close side menu' id='myOverlay'></div>"
					+ "" + "<div class=\"w3-main\" style=\"margin-left:300px;background-color: #f2f7f8\"> "
					+ "   <div id=\"home\" style=\"height:980px\"> " + "    <header> "
					+ "<span class=\"w3-button w3-hide-large w3-xxlarge w3-hover-text-grey\" onclick=\"w3_open()\"><i class=\"fa fa-bars\"></i></span>"
					+ "     <div class=\"w3-container\" style=\"background-color: #f2f7f8\"> "
					+ "      <h1><b>Execution Summary</b></h1> " + "     </div> " + "    </header> "
					+ "    <!-- test the tables1--> " + "    <div class=\"w3-row-padding\" >"
					+ "      <div class=\"w3-third w3-margin-bottom\">"
					+ "        <ul class=\"w3-ul w3-border w3-white w3-center\">"
					+ "          <li class=\"w3-teal w3-xlarge w3-padding-30\">Functional</li>"
					+ "           <table class=\"table table-bordered\" style=\"width:100%; height:221px\"> " +

					functionalSummary +

					"            </table> " + "        </ul>" + "      </div>" + "      "
					+ "      <div class=\"w3-third w3-margin-bottom\">"
					+ "        <ul class=\"w3-ul w3-border w3-white w3-center\">"
					+ "          <li class=\"w3-black w3-xlarge w3-padding-30\">Accesibility</li>"
					+ "           <table class=\"table table-bordered\" style=\"width:100%;  line-height: 213%; height:221px\"> "
					+ accessibilitySummaryHtml + "            </table> " + "        </ul>" + "      </div>" +

					"      " + "      <div class=\"w3-third\">"
					+ "        <ul class=\"w3-ul w3-border w3-white w3-center\">"
					+ "          <li class=\"w3-teal w3-xlarge w3-padding-30\">Security</li>"
					+ "           <table class=\"table table-bordered\" style=\"width:100%; line-height: 225%; height:221px\"> "
					+ securitySummaryHtml + "            </table> " + "        </ul>" + "      </div>" + "    </div>"
					+ "    <div class=\"w3-container\" id=\"graph\" style=\"background-color: #f2f7f8;height:400px\">"
					+ "       <div id=\"view1\" class=\"tab-pane fade in active\">" +
//"       <iframe src=\"./../../../NFTReports/performance_summary_graph.html\" style=\"width: 100%; margin-top:30px; height: 710px; overflow: auto; border:none;\"></iframe>"+ 
					"</div>" +

					"    <!-- first row ended -->" + "    <div class=\"w3-row-padding\" >" + "      "
					+ "      <div class=\"w3-third w3-margin-bottom\">"
					+ "        <ul class=\"w3-ul w3-border w3-white w3-center\">"
					+ "          <li class=\"w3-black w3-xlarge w3-padding-30\">Visual Validation</li>"
					+ "           <table class=\"table table-bordered\" style=\"width:100%; height:221px\"> " +

					visualValidationSummaryHtml + "            </table> " + "        </ul>" + "      </div>" + "      "
					+ "      <div class=\"w3-third\">" + "        <ul class=\"w3-ul w3-border w3-white w3-center\">"
					+ "          <li class=\"w3-teal w3-xlarge w3-padding-30\">BrokenLinks</li>"
					+ "           <table class=\"table table-bordered\" style=\"width:100%;  line-height: 213%; height:221px\"> "
					+

					brokenLinkSummaryHtml + "            </table> " + "        </ul>" + "      </div>"
					+ "    <div class=\"w3-third\" >" + "      "
					+ "        <ul class=\"w3-ul w3-border w3-white w3-center\">"
					+ "          <li class=\"w3-black w3-xlarge w3-padding-30\">Performance</li>"
					+ "           <table class=\"table table-bordered\" style=\"width:100%;line-height: 465% ;height:221px\"> "
					+

					performanceSummaryHtml + "            </table> " + "        </ul>" + "      </div>" + "      "
					+ "    </div>" + "    <!-- last row ended-->" + "  </div>" + functionalReportHtml
					+ performanceReportHtml + "" + securityReportHtml + "" + accessibilityReportHtml + ""
					+ visualValidationReportHtml + "" + brokenLinkReportHtml + ""
					+ "    <footer class='w3-container w3-padding-32 w3-dark-grey' style='background-color: #f2f7f8'>"
					+ "    </footer>" + "  </div>" + "  <script>" + "    function w3_open() {"
					+ "      document.getElementById('mySidebar').style.display = 'block';"
					+ "      document.getElementById('myOverlay').style.display = 'block';" + "    }"
					+ "   function w3_close() {" + "      document.getElementById('mySidebar').style.display = 'none';"
					+ "      document.getElementById('myOverlay').style.display = 'none';" + "    }" + "  </script>"
					+ "</body>" + "</html>";

			Document doc = Jsoup.parse(htmlString);
			HTMLComparisionReport.WriteToFile(doc, RegressionReportComparison.getReportPath() + File.separator
					+ "HTML Results" + File.separator + "ConsolidatedReportTemplate.html");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Inside the exception");
		}
	}

}
