package com.cognizant.framework;

import java.util.List;

public class TestCaseBean {

	private String tcTestConfigurationID;
	private String tcExecutionMode;
	private String tcExecutionPlatform;
	private String tcToolName;
	private String tcDeviceName;
	private String tcMobileVersion;
	private String tcBrowser;
	private String tcBrowserVersion;
	private String tcPlatform;
	private int totalPassedSteps;
	private int totalFailedSteps;
	private String failureReason;
	private List<TestStepBean> testSteps;

	/**
	 * @return the tcTestConfigurationID
	 */
	public String getTcTestConfigurationID() {
		return tcTestConfigurationID;
	}

	/**
	 * @param tcTestConfigurationID
	 *            the tcTestConfigurationID to set
	 */
	public void setTcTestConfigurationID(String tcTestConfigurationID) {
		this.tcTestConfigurationID = tcTestConfigurationID;
	}

	/**
	 * @return the tcExecutionMode
	 */
	public String getTcExecutionMode() {
		return tcExecutionMode;
	}

	/**
	 * @param tcExecutionMode
	 *            the tcExecutionMode to set
	 */
	public void setTcExecutionMode(String tcExecutionMode) {
		this.tcExecutionMode = tcExecutionMode;
	}

	/**
	 * @return the tcExecutionPlatform
	 */
	public String getTcExecutionPlatform() {
		return tcExecutionPlatform;
	}

	/**
	 * @param tcExecutionPlatform
	 *            the tcExecutionPlatform to set
	 */
	public void setTcExecutionPlatform(String tcExecutionPlatform) {
		this.tcExecutionPlatform = tcExecutionPlatform;
	}

	/**
	 * @return the tcToolName
	 */
	public String getTcToolName() {
		return tcToolName;
	}

	/**
	 * @param tcToolName
	 *            the tcToolName to set
	 */
	public void setTcToolName(String tcToolName) {
		this.tcToolName = tcToolName;
	}

	/**
	 * @return the tcDeviceName
	 */
	public String getTcDeviceName() {
		return tcDeviceName;
	}

	/**
	 * @param tcDeviceName
	 *            the tcDeviceName to set
	 */
	public void setTcDeviceName(String tcDeviceName) {
		this.tcDeviceName = tcDeviceName;
	}

	/**
	 * @return the tcBrowser
	 */
	public String getTcBrowser() {
		return tcBrowser;
	}

	/**
	 * @param tcBrowser
	 *            the tcBrowser to set
	 */
	public void setTcBrowser(String tcBrowser) {
		this.tcBrowser = tcBrowser;
	}

	/**
	 * @return the tcPlatform
	 */
	public String getTcPlatform() {
		return tcPlatform;
	}

	/**
	 * @param tcPlatform
	 *            the tcPlatform to set
	 */
	public void setTcPlatform(String tcPlatform) {
		this.tcPlatform = tcPlatform;
	}

	/**
	 * @return the totalPassedSteps
	 */
	public int getTotalPassedSteps() {
		return totalPassedSteps;
	}

	/**
	 * @param totalPassedSteps
	 *            the totalPassedSteps to set
	 */
	public void setTotalPassedSteps(int totalPassedSteps) {
		this.totalPassedSteps = totalPassedSteps;
	}

	/**
	 * @return the totalFailedSteps
	 */
	public int getTotalFailedSteps() {
		return totalFailedSteps;
	}

	/**
	 * @param totalFailedSteps
	 *            the totalFailedSteps to set
	 */
	public void setTotalFailedSteps(int totalFailedSteps) {
		this.totalFailedSteps = totalFailedSteps;
	}

	/**
	 * @return the failureReason
	 */
	public String getFailureReason() {
		return failureReason;
	}

	/**
	 * @param failureReason
	 *            the failureReason to set
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	/**
	 * @return the testSteps
	 */
	public List<TestStepBean> getTestSteps() {
		return testSteps;
	}

	/**
	 * @param testSteps
	 *            the testSteps to set
	 */
	public void setTestSteps(List<TestStepBean> testSteps) {
		this.testSteps = testSteps;
	}

	public String getTcMobileVersion() {
		return tcMobileVersion;
	}

	public void setTcMobileVersion(String tcMobileVersion) {
		this.tcMobileVersion = tcMobileVersion;
	}

	public String getTcBrowserVersion() {
		return tcBrowserVersion;
	}

	public void setTcBrowserVersion(String tcBrowserVersion) {
		this.tcBrowserVersion = tcBrowserVersion;
	}
}
