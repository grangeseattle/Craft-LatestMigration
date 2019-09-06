package com.cognizant.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegressionReportComparison {

//	private static String reportPath = "/home/qpaas/Documents/ReleasesNew/3.2/Classic/CRAFT_Classic/Results/Regression/Run_11-Sep-2018_02-23-52_PM";
	private static String reportPath ;
	private static List<String> testInstancePros = new ArrayList<String>();
 	HTMLComparisionReport htmlcomparisionReport;

	public static synchronized void addTestsParameters(String testProp)

	{
		testInstancePros.add(testProp);

	}

	public static void setReportPath(String str) {
		reportPath = str;
	}

	
	public static String getReportPath() {
		return reportPath;
	}

	public void createReport()

	{
	int count =testInstancePros.size();
		while(count>0) {
			
			int index1=-1;
			int index2=-1;
		

			try {
				outerLoop:for (int i = 0; i < testInstancePros.size();) {
					System.out.println("Inside create report");
				
					innerLoop:for(int j=i+1;j<count; j++)
					{
						String testScenarioTestcase1= testInstancePros.get(i).replaceAll("_Instance1", "").replaceAll("_Instance2", "");
						String testScenarioTestcase2= testInstancePros.get(j).replaceAll("_Instance1", "").replaceAll("_Instance2", "");
						
						if(testScenarioTestcase1.trim().equalsIgnoreCase(testScenarioTestcase2.trim()))
						{
							String title = testInstancePros.get(i).replaceAll("_Instance1", "").replaceAll("_Instance2", "");
							
							title += "_comparison.html";
							String encryptedHtml = WhitelistingPath.cleanStringForFilePath(
									reportPath + Util.getFileSeparator() + "HTML Results" + Util.getFileSeparator() + title);
							String constructReportPath = reportPath + File.separator + "HTML Results" + File.separator + title;
							htmlcomparisionReport = new HTMLComparisionReport();
							htmlcomparisionReport.createUsingIframes(title, testInstancePros.get(i), testInstancePros.get(j),encryptedHtml);
							index2=j;
							
							break innerLoop;
						}

					}
					index1=i;
					break outerLoop;
				}
				
				
				if(index1>-1 && index2>-1)
				{
					count -=2;
					testInstancePros.remove(index1);
					testInstancePros.remove(index2-1);
					
				}
				else if(index1>-1 && !(index2 >-1))
				{
					count--;
				}
				
			} catch (Exception e) {

				e.printStackTrace();

			}
				
		}

	}
	
	
}
