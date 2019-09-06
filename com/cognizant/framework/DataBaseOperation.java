package com.cognizant.framework;

import java.util.Properties;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Settings;
import com.cognizant.framework.TimeStamp;
import com.cognizant.framework.selenium.SeleniumTestParameters;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataBaseOperation {

	private SeleniumTestParameters testParameters;
	private static Properties properties;
	private TestCaseBean testCaseBean;
	private String buildName;
	private String engineName;
	private String projectName;
	private String runConfigName;
	private String approach;
	private String onError;
	private boolean saveDataToDB;

	private MongoClient mongoclient;
	private DB database;
	private DBCollection collection;
	private DBObject testCaseDetails;

	public DataBaseOperation() {
		properties = Settings.getInstance();
		this.saveDataToDB = Boolean.parseBoolean(properties.getProperty("SaveDataToDB"));
		if (saveDataToDB) {
			createCollections();
		}

	}

	public void initializeTestParameters(SeleniumTestParameters testParameters) {
		this.testParameters = testParameters;
	}

	@SuppressWarnings("deprecation")
	private void createCollections() {

		String dbHost = properties.getProperty("DBHost");
		String dbPort = properties.getProperty("DBPort");

		try {
			// Check if that Collection exists
			// CognitiveDatabase - DB
			// ExecutionDetails - Collections
			// createCollection();
			// Creating collection if not exist if exist it will get the
			// collection

			String mongodbIP = System.getProperty("dashboard.docker.ip");
			if (mongodbIP != null && !mongodbIP.isEmpty() && !mongodbIP.equals("")) {
				mongoclient = new MongoClient(mongodbIP, 27017);
			} else {
				mongoclient = new MongoClient(dbHost, Integer.valueOf(dbPort));
			}

			database = mongoclient.getDB("CognitiveDatabase");
			collection = database.getCollection("CraftExecutionDetails");
			testCaseDetails = new BasicDBObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException("Error while Creating Data base" + e.getMessage());
		} finally {

		}
	}

	public void updateMongoDB(String engineName, TestCaseBean testCaseBean, String executionTime,
			String testCaseStatus) {

		if (saveDataToDB) {
			this.engineName = engineName;
			this.testCaseBean = testCaseBean;

			updateTestCaseDetailsMongo(executionTime, testCaseStatus);
			updateTestStepDetailsMongo();
		}

	}

	private void updateTestCaseDetailsMongo(String executionTime, String testStatus) {
		try {
			buildName = properties.getProperty("BuildName");
			projectName = properties.getProperty("ProjectName");
			approach = properties.getProperty("Approach");
			onError = properties.getProperty("OnError");
			runConfigName = properties.getProperty("RunConfiguration");

			// two more column
			String runName = TimeStamp.getInstance();
			runName = runName.substring(runName.lastIndexOf('\\') + 1);
			String[] a = runName.split("_");
			String currentDate = a[a.length - 3];
			String timeOfExecution = a[a.length - 2] + "_" + a[a.length - 1];

			// adding to DB Object
			String username = System.getProperty("Login_Username");
			if (username != null && !username.isEmpty() && !username.equals("")) {
				testCaseDetails.put("UserName", username);
			} else {
				testCaseDetails.put("UserName", "Admin");
			}

			// Constant Values

			String projectNameSysProp = System.getProperty("ProjectName");
			if (projectNameSysProp != null && !projectNameSysProp.isEmpty() && !projectNameSysProp.equals("")) {
				testCaseDetails.put("Project Name", projectNameSysProp);
			} else {
				testCaseDetails.put("Project Name", projectName);
			}
			testCaseDetails.put("BuildName", buildName);

			testCaseDetails.put("Engine Name", engineName);
			testCaseDetails.put("Approach", approach);
			testCaseDetails.put("OnError", onError);

			// Changing values
			testCaseDetails.put("Runconfiguration Name", runConfigName);
			testCaseDetails.put("ExecutionDate", currentDate);
			testCaseDetails.put("ExecutionTime", timeOfExecution);
			testCaseDetails.put("RunName", runName);
			testCaseDetails.put("TestScenario", testParameters.getCurrentScenario());
			testCaseDetails.put("TestCase", testParameters.getCurrentTestcase());
			testCaseDetails.put("TestInstance", testParameters.getCurrentTestInstance());

			// added enviroment for comparison report
			if (testParameters.getCurrentTestInstance().equalsIgnoreCase("instance1")) {
				testCaseDetails.put("Environment", properties.getProperty("Environment_1"));
			} else {
				testCaseDetails.put("Environment", properties.getProperty("Environment_2"));
			}

			testCaseDetails.put("TestDescription", testParameters.getCurrentTestDescription());
			testCaseDetails.put("TestIterationMode", testParameters.getIterationMode().toString());

			switch (testParameters.getExecutionMode()) {
			case LOCAL:
			case GRID:

				testCaseDetails.put("TestExecutionMode", testParameters.getExecutionMode().toString());
				testCaseDetails.put("TestBrowser", testParameters.getBrowser().toString());
				testCaseDetails.put("TestPlatform", testParameters.getPlatform().toString());
				if (testParameters.getBrowserVersion() == null) {
					testCaseDetails.put("TestBrowserVersion", "NA");
				} else {
					testCaseDetails.put("TestBrowserVersion", testParameters.getBrowserVersion());
				}
				testCaseDetails.put("TestToolName", "NA");
				testCaseDetails.put("TestMobilePlatform", "NA");
				testCaseDetails.put("TestMobileVersion", "NA");
				testCaseDetails.put("TestDeviceName", "NA");
				break;

			case MOBILE:
			case TESTOBJECT:
			case SAUCELABS:
			case PERFECTO:
			case BROWSERSTACK:
			case FASTEST:
			case SEETEST:

				testCaseDetails.put("TestExecutionMode", testParameters.getExecutionMode().toString());
				testCaseDetails.put("TestBrowser", "NA");
				testCaseDetails.put("TestPlatform", "NA");
				testCaseDetails.put("TestBrowserVersion", "NA");
				testCaseDetails.put("TestToolName", testParameters.getMobileToolName().toString());
				testCaseDetails.put("TestMobilePlatform", testParameters.getMobileExecutionPlatform().toString());
				testCaseDetails.put("TestMobileVersion", testParameters.getMobileOSVersion());
				testCaseDetails.put("TestDeviceName", testParameters.getDeviceName());

				break;
			default:
				throw new FrameworkException("Unhandled Execution Mode!");
			}

			testCaseDetails.put("TotalPassedSteps", testCaseBean.getTotalPassedSteps());
			testCaseDetails.put("TotalFailedSteps", testCaseBean.getTotalFailedSteps());
			testCaseDetails.put("FailureReason", testCaseBean.getFailureReason());
			testCaseDetails.put("TotalExecutionTime", executionTime);
			testCaseDetails.put("TestStatus", testStatus);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateTestStepDetailsMongo() {
		try {

			BasicDBList db1 = new BasicDBList();

			for (TestStepBean tsb : testCaseBean.getTestSteps()) {
				DBObject testStep = new BasicDBObject();
				testStep.put("StepName", tsb.getTestStepName());
				testStep.put("StepNo", tsb.getTestStepNumber());
				testStep.put("StepDescription", tsb.getTestStepDescription());
				testStep.put("StepStatus", tsb.getTestStepStatus());
				testStep.put("ExecutionTime", tsb.getTestStepExectuionTime());
				testStep.put("Iteration", tsb.getIteration());
				testStep.put("SubIteration", tsb.getSubIteration());
				testStep.put("BusinessComponent", tsb.getBusinessComponent());
				// Failure Details
				testStep.put("FailureReason", tsb.getFailureReason());
				testStep.put("MethodName", tsb.getMethodName());
				testStep.put("ClassName", tsb.getClassName());
				testStep.put("StackTrace", tsb.getStackTrace());
				testStep.put("BriefErroMessage", tsb.getBriefErrorMesg());
				db1.add(testStep);
			}
			testCaseDetails.put("Steps", db1);
			collection.insert(testCaseDetails);
			mongoclient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLiveExecutionStatus() {

		synchronized (DataBaseOperation.class) {

		}
	}

	public static class LiveExecutionInfo {

		private static int totalTCCount;
		private static int passedCount;
		private static int failedCount;
		private static int pendingCount;
		private static String runningTCInfo;

		public static int getTotalTCCount() {
			return totalTCCount;
		}

		public static void addTotalTCCount(int totalCount) {
			synchronized (LiveExecutionInfo.class) {
				totalTCCount = totalCount;
				pendingCount = totalCount;
			}

		}

		public synchronized static void updateRunningTCInfoInDB(boolean needToupdtateDB, boolean needToUpdateInfo,
				boolean tcExecStatus, String info) {
			properties = Settings.getInstance();
			if (Boolean.parseBoolean(properties.getProperty("SaveDataToDB"))) {

				if (needToUpdateInfo) {
					if (tcExecStatus) {
						passedCount++;
						pendingCount--;
					} else {
						failedCount++;
						pendingCount--;

					}

				}

				runningTCInfo = info;
				if (needToupdtateDB) {
					try {

						String dbHost = properties.getProperty("DBHost");
						String dbPort = properties.getProperty("DBPort");
						DBObject executionObj = new BasicDBObject();
						DBObject executionDetails = new BasicDBObject();
						DB database;
						DBCollection collection;
						MongoClient mongoclient;
						String mongodbIP = System.getProperty("dashboard.docker.ip");
						if (mongodbIP != null && !mongodbIP.isEmpty() && !mongodbIP.equals("")) {
							mongoclient = new MongoClient(mongodbIP, 27017);
						} else {
							mongoclient = new MongoClient(dbHost, Integer.parseInt(dbPort));
						}
						database = mongoclient.getDB("CognitiveDatabase");
						collection = database.getCollection("CraftExecutionDetails");
						DBObject query = new BasicDBObject();
						query.put("ID", "CurrentExecutionInfo");
						collection.remove(query);
						// Constant Values
						String username = System.getProperty("Login_Username");
						if (username != null && !username.isEmpty() && !username.equals("")) {
							executionDetails.put("UserName", username);
						} else {
							executionDetails.put("UserName", "karan");
						}

						String projectName = System.getProperty("ProjectName");
						if (projectName != null && !projectName.isEmpty() && !projectName.equals("")) {
							executionDetails.put("ProjectName", projectName);
						} else {
							executionDetails.put("ProjectName", "PROJECT-1.0");
						}

						executionDetails.put("TotalTCCount", totalTCCount);
						executionDetails.put("PassedCount", passedCount);
						executionDetails.put("FailedCount", failedCount);
						executionDetails.put("PendingCount", pendingCount);
						executionDetails.put("ExecutionStatus", runningTCInfo);
						if (totalTCCount - (passedCount + failedCount) == 0) {
							executionDetails.put("IsScriptRunning", "false");
						} else {
							executionDetails.put("IsScriptRunning", "true");
						}

						executionDetails.put("ID", "CurrentExecutionInfo");
						collection.insert(executionDetails);
						mongoclient.close();
					} catch (Exception exception) {
						exception.printStackTrace();

					}
				}

			}
		}

	}
}
