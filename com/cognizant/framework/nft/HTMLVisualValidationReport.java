package com.cognizant.framework.nft;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.frontendtest.components.ImageComparison;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class HTMLVisualValidationReport{
	private static String testStepRow="";
	private static List<String> instace1Screens;
	private static List<String> instace2Screens;
	public static int totalComparisons=0,visuSim=0,visuDiff=0;	
	public static void screenShotComapre(List<String>instace1Screens,List<String>instace2Screens,String pathOfScreenshotDir,String pathOfReportDir) {
		System.out.println("##### Visual Validation Started #####");
		for(int i =0; i<instace1Screens.size(); i++){
			String imgOriginal = pathOfScreenshotDir+instace1Screens.get(i);
			String imgToCompareWithOriginal = pathOfScreenshotDir+instace2Screens.get(i);
			String imgOutputDifferences = pathOfScreenshotDir+"Diff_"+instace2Screens.get(i);
			String[] imgOutputDifferencesArray = imgOutputDifferences.split("\\.");
			imgOutputDifferences=imgOutputDifferencesArray[0]+".jpg";
			
			System.out.println("Comparing "+imgOriginal+" --> "+ imgToCompareWithOriginal);
			if(new File(imgOriginal).exists() && new File(imgToCompareWithOriginal).exists()) {
				try {
					totalComparisons++;
					ImageComparison imageComparison = new ImageComparison(30,30,0.01);
					
					if(imageComparison.fuzzyEqual(imgOriginal,imgToCompareWithOriginal,imgOutputDifferences)) {
						String[] instancejpg = instace2Screens.get(i).split("\\.");
						String imageInstanceJPG = instancejpg[0]+".jpg";
						testStepRow+= "\t\t\t\t\t \n"
								+ "\t\t\t\t\t <td><img class='zoom' src='"+pathOfScreenshotDir+instace2Screens.get(i)+"'/></td> \n" 
										+ "\t\t\t\t\t <td><img class='zoom' src='"+pathOfScreenshotDir+instace1Screens.get(i)+"'/></td> \n" 
								+ "\t\t\t\t\t <td><img class='zoom' src='"+pathOfScreenshotDir+"/Diff_"+imageInstanceJPG+"'/></td> \n";	

						testStepRow+="\t\t\t\t\t <td style='color:green'>Passed</td> \n" +"\t\t\t\t </tr> \n";
					visuSim++;
					}
					else {
						String[] instancejpg = instace2Screens.get(i).split("\\.");
						String imageInstanceJPG = instancejpg[0]+".jpg";
						testStepRow+= "\t\t\t\t\t \n"
								+ "\t\t\t\t\t <td><img class='zoom' src='"+pathOfScreenshotDir+instace2Screens.get(i)+"'/></td> \n" 
										+ "\t\t\t\t\t <td><img class='zoom' src='"+pathOfScreenshotDir+instace1Screens.get(i)+"'/></td> \n" 
								+ "\t\t\t\t\t <td><img class='zoom' src='"+pathOfScreenshotDir+"/Diff_"+imageInstanceJPG+"'/></td> \n";	
					testStepRow+="\t\t\t\t\t <td style='color:red'>Failed</td> \n" +"\t\t\t\t </tr> \n";
					visuDiff++;
					}
			
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
					
        }
		createUsingIframes(pathOfReportDir);
	}
	
	private static void  createUsingIframes(String pathOfReportDir)
	    {   	
	    	try {
	    		String resultSummaryTableHeading = "\t\t\t <table><thead> \n" + "\t\t\t\t <tr class='heading'> \n"
						+ "\t\t\t\t\t <th>Screenshot Instance 1</th> \n"
						+ "\t\t\t\t\t <th>Screenshot Instance 2</th> \n"
						+ "\t\t\t\t\t <th>Screenshot Diff</th> \n"
						+ "\t\t\t\t\t <th>Diff Status</th> \n" +"\t\t\t\t </tr> \n" + "\t\t\t </thead> \n\n<tbody>";
	    		
	    		String tableFooter = "\t\t\t </tbody> \n\t\t </table>";
	    		
	      		String htmlString ="<html>\n"+
	    	        		"    <head>\n"+
	    	        		"            <meta charset='UTF-8'> \n"+
	    	        		"            <title>Visual Validation</title> \n"+
	    	        		"            <style type='text/css'> \n" + 
	    	        		getCSS()   +
	    	        		"   	<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css'>\n" + 
	    	        		"       <script src='https://code.jquery.com/jquery-3.2.1.min.js'></script>\n" + 
	    	        		"      <script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>\n" + 
	    	        		"      <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js' ></script>\n" + 
	    	        		"</head>\n" + 
	    	        		"    <body>\n" + 
	    	        		"        <div class='row'>\n" +
	    	        		resultSummaryTableHeading+testStepRow+tableFooter+
	    	        		"        </div>\n" +
	    	        		"    </body>\n" + 
	    	        		"     \n" +
	    	        		"</html>\n" ;
	        Document doc = Jsoup.parse(htmlString);
	        WriteToFile(doc,pathOfReportDir+"Visual_Validation_Report.html");
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Inside the exception");
	    } 
	    }
	    
	    public static void WriteToFile(Document doc, String fileName) throws IOException {
	        File file = new File(fileName);
	        file.createNewFile();
	        FileUtils.writeStringToFile(file, doc.outerHtml(), "UTF-8");
	        System.out.println("##### Visual Validation Finished #####");
	    }
	    
	    public static String getCSS()
	    {
	    	return "                body { \n" + 
	        		"                    background-color: #C1E1E2; \n" + 
	        		"                    font-family: Verdana, Geneva, sans-serif; \n" + 
	        		"                    text-align: center; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                small { \n" + 
	        		"                    font-size: 0.7em; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                table { \n" + 
	        		"                    width: 95%; \n" + 
	        		"                    margin-left: auto; \n" + 
	        		"                    margin-right: auto; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                tr.heading { \n" + 
	        		"                    background-color: #A9D0F5; \n" + 
	        		"                    color: #000000; \n" + 
	        		"                    font-size: 0.7em; \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                tr.subheading { \n" + 
	        		"                    background-color: #E0E6F8; \n" + 
	        		"                    color: #34495E; \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                    font-size: 0.7em; \n" + 
	        		"                    text-align: justify; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                tr.section { \n" + 
	        		"                    background-color: #E0E6F8; \n" + 
	        		"                    color: #333300; \n" + 
	        		"                    cursor: pointer; \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                    font-size: 0.7em; \n" + 
	        		"                    text-align: justify; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                tr.subsection { \n" + 
	        		"                    background-color: #EDEEF0; \n" + 
	        		"                    cursor: pointer; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                tr.content { \n" + 
	        		"                    background-color: #EDEEF0; \n" + 
	        		"                    color: #000000; \n" + 
	        		"                    font-size: 0.7em; \n" + 
	        		"                    display: none; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		
	        		"                td { \n" + 
	        		"                    padding: 4px; \n" + 
	        		"                    text-align: inherit\\0/; \n" + 
	        		"                    word-wrap: break-word; \n" + 
	        		"                    max-width: 450px; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                th { \n" + 
	        		"                    padding: 4px; \n" + 
	        		"                    text-align: inherit\\0/; \n" + 
	        		"                    max-width: 450px; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                td.justified { \n" + 
	        		"                    text-align: justify; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                td.pass { \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                    color: green; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                td.fail { \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                    color: red; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                td.done, td.screenshot { \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                    color: black; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                td.debug { \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                    color: blue; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		"                td.warning { \n" + 
	        		"                    font-weight: bold; \n" + 
	        		"                    color: orange; \n" + 
	        		"                } \n" + 
	        		"                img { \n" + 
	        		"                    width:400px; \n" + 
	        		"                    height:300px; \n" + 
	        		"                } \n" + 
	        		"                th.perfColor { \n" + 
	        		"                    color: darkorchid; \n" + 
	        		"                } \n" + 
	        		"   \n" + 
	        		".zoom {padding: 0px;background-color: green;transition: transform .1s;width:150px;height:150px;margin: 0 auto;}"+
	        		".zoom:hover {-ms-transform: scale(6);-webkit-transform: scale(6);transform: scale(6);e viewport)}"+
	        		"	iframe{"+
					"height: 100%;"+
	    			"width: 100%;"+
				" }"+
	        		"            </style> \n" ;
	    }
}
