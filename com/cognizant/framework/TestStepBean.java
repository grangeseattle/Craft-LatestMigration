package com.cognizant.framework;

public class TestStepBean {

	private int iteration;
	private int subIteration;
	private int testStepNumber;
	private String testStepName;
	private String testStepDescription;
	private String testStepStatus;
	private String testStepExectuionTime;
	private String failureReason;
	private String businessComponent;
	private String lineNo;
	private String methodName;
	private String className;
	private String stackTrace;
	private String briefErrorMesg;

	/**
	 * @return the iteration
	 */
	public int getIteration() {
		return iteration;
	}

	/**
	 * @param iteration
	 *            the iteration to set
	 */
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	/**
	 * @return the subIteration
	 */
	public int getSubIteration() {
		return subIteration;
	}

	/**
	 * @param subIteration
	 *            the subIteration to set
	 */
	public void setSubIteration(int subIteration) {
		this.subIteration = subIteration;
	}

	/**
	 * @return the testStepNumber
	 */
	public int getTestStepNumber() {
		return testStepNumber;
	}

	/**
	 * @param testStepNumber
	 *            the testStepNumber to set
	 */
	public void setTestStepNumber(int testStepNumber) {
		this.testStepNumber = testStepNumber;
	}

	/**
	 * @return the testStepName
	 */
	public String getTestStepName() {
		return testStepName;
	}

	/**
	 * @param testStepName
	 *            the testStepName to set
	 */
	public void setTestStepName(String testStepName) {
		this.testStepName = testStepName;
	}

	/**
	 * @return the testStepDescription
	 */
	public String getTestStepDescription() {
		return testStepDescription;
	}

	/**
	 * @param testStepDescription
	 *            the testStepDescription to set
	 */
	public void setTestStepDescription(String testStepDescription) {
		this.testStepDescription = testStepDescription;
	}

	/**
	 * @return the testStepStatus
	 */
	public String getTestStepStatus() {
		return testStepStatus;
	}

	/**
	 * @param testStepStatus
	 *            the testStepStatus to set
	 */
	public void setTestStepStatus(String testStepStatus) {
		this.testStepStatus = testStepStatus;
	}

	/**
	 * @return the testStepExectuionTime
	 */
	public String getTestStepExectuionTime() {
		return testStepExectuionTime;
	}

	/**
	 * @param testStepExectuionTime
	 *            the testStepExectuionTime to set
	 */
	public void setTestStepExectuionTime(String testStepExectuionTime) {
		this.testStepExectuionTime = testStepExectuionTime;
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

	public String getBusinessComponent() {
		return businessComponent;
	}

	public void setBusinessComponent(String businessComponent) {
		this.businessComponent = businessComponent;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getBriefErrorMesg() {
		return briefErrorMesg;
	}

	public void setBriefErrorMesg(String briefErrorMesg) {
		this.briefErrorMesg = briefErrorMesg;
	}

}
