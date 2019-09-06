package com.cognizant.framework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BrokenLinks_old {

	protected static List<String[]> env1 = new ArrayList<String[]>();
	protected static List<String[]> env1testcase = new ArrayList<String[]>();
	protected static List<String[]> env2 = new ArrayList<String[]>();
	protected static List<String[]> env2testcase = new ArrayList<String[]>();
	public static List<Integer> list = new ArrayList<Integer>();
	public static synchronized void saveBrokenLinkValidationForReport(String[] responseArray) {
		if (responseArray[3].equalsIgnoreCase("Instance1")) {
			env1.add(responseArray);
		} else if (responseArray[3].equalsIgnoreCase("Instance2")) {
			env2.add(responseArray);
		}
	}

	public void createBLComparisionReport() {
	      try {String htmlString ="";
	    		if(env1.size()!=0 && env2.size()!=0)
	    		{
	    			 htmlString = "<!DOCTYPE html>\n" + 
	    					"<html style=\"\n" + 
	    					"    background-color: #f2f7f8;\n" + 
	    					"\">"+getCSS()+ 
	    					"    <body style='background-color:#f2f7f8'>\n" +
	    					brokenLinkSummary()+
	    					"        <div class='row' style=\"\n" + 
	    					"    background-color: #f2f7f8;\n" + 
	    					"\"> \n" + 
	    					"            <div class=\"col-6\" id='loadContent1'>\n" + 
	    					"                <table id='instance1'>\n" + 
	    					"                    <thead>\n" + 
	    					"                        <tr class='heading'>\n" + 
	    					"                            <th>Step No</th>\n" + 
	    					"                            <th>TestCase Name</th>\n" + 
	    					"                            <th>Step Name</th>\n" + 
	    					"                            <th>Description</th>\n" + 
	    					"                            <th>Status</th>\n" + 
	    					"                            <th>ScreenShot</th>\n" + 
	    					"                        </tr>\n" + 
	    					"                    </thead>\n" + 
	    					"                    <tbody class='instance1'>"+getScript(env1)+"</tbody>\n" + 
	    					"                </table>\n" + 
	    					"            </div>\n" + 
	    					"            <div class='col-6' id='loadContent2'>\n" + 
	    					"                <table id='instance2'>\n" + 
	    					"                    <thead>\n" + 
	    					"                        <tr class='heading'>\n" + 
	    					"                            <th>Step No</th>\n" + 
	    					"                            <th>TestCase Name</th>\n" + 
	    					"                            <th>Step Name</th>\n" + 
	    					"                            <th>Description</th>\n" + 
	    					"                            <th>Status</th>\n" + 
	    					"                            <th>ScreenShot</th>\n" + 
	    					"                        </tr>\n" + 
	    					"                    </thead>\n" + 
	    					"                    <tbody class='instance2'>"+getScript(env2)+"</tbody>\n" + 
	    					"                </table>\n" + 
	    					"            </div>\n" + 
	    					"        </div>\n" + 
	    					"    </body>\n" + 
	    					"</html>";
	    		}else if(env1.size()!=0 && env2.size()==0) 
	    		{
	    			 htmlString = "<!DOCTYPE html>\n" + 
		    					"<html style=\"\n" + 
		    					"    background-color: #f2f7f8;\n" + 
		    					"\">"+getCSS()+ 
		    					"    <body style='background-color:#f2f7f8'>\n" +
		    					brokenLinkSummary()+
		    					"        <div class='row' style=\"\n" + 
		    					"    background-color: #f2f7f8;\n" + 
		    					"\"> \n" + 
		    					"            <div class=\"col-6\" id='loadContent1'>\n" + 
		    					"                <table id='instance1'>\n" + 
		    					"                    <thead>\n" + 
		    					"                        <tr class='heading'>\n" + 
		    					"                            <th>Step No</th>\n" + 
		    					"                            <th>TestCase Name</th>\n" + 
		    					"                            <th>Step Name</th>\n" + 
		    					"                            <th>Description</th>\n" + 
		    					"                            <th>Status</th>\n" + 
		    					"                            <th>ScreenShot</th>\n" + 
		    					"                        </tr>\n" + 
		    					"                    </thead>\n" + 
		    					"                    <tbody class='instance1'>"+getScript(env1)+"</tbody>\n" + 
		    					"                </table>\n" + 
		    					"            </div>\n" + 
		    					"        </div>\n" + 
		    					"    </body>\n" + 
		    					"</html>";
	    		}
	
			Document doc = Jsoup.parse(htmlString);
			HTMLComparisionReport.WriteToFile(doc, RegressionReportComparison.getReportPath() + File.separator
					+ "HTML Results" + File.separator + "BrokenLinks.html");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Inside the exception");
		}
	}

	public static String brokenLinkSummary()
	{
		int PassCount1=0; int FailCount1=0; int PassCount2=0; int FailCount2=0;
		try {
			System.out.println(env1.size() + env2.size());
			
			for(int i=0;i<env1.size();i++)
			{
				if(env1.get(i)[5].toString().equalsIgnoreCase(
						"PASS")) {
					PassCount1++;
				}
				else
				{
					FailCount1++;
				}
			}
			for(int i=0;i<env2.size();i++)
			{
				if(env2.get(i)[5].toString().equalsIgnoreCase(
						"PASS")) {
					PassCount2++;
				}
				else
				{
					FailCount2++;
				}
			}
			
			list.add(PassCount1);
			list.add(FailCount1);
			list.add(PassCount2);
			list.add(FailCount2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(env1.size()!=0 && env2.size()!=0)
		return  "<div class='row' style='margin-top:30px;'>"
        +"<div class='col-sm-6' id='BLloadContent1'>"
			+"<table id='BLSummary1'><thead>" 
                                +"<tr class='heading'>" 
                                    +"<th colspan='2' class='theadBL'>Environment1</th>" 
                                +"</tr>" 
								+"<tr class='heading'>" 
                                    +"<th class='theadBL'>BrokenLinks</th>" 
									+"<th class='theadBL'>UnbrokenLinks</th>" 
                                +"</tr>"
                            +"</thead>" 
                            +"<tbody>"
                                +"<tr class='content'>" 
                                    +"<td>"+FailCount1+"</td>" 
                                    +"<td>"+PassCount1+"</td>"
                                +"</tr>" 
                            +"</tbody>" 
                        +"</table>"+"</div>"
        +"<div class='col-sm-6' id='BLloadContent2'>"
			+"<table id='BLSummary2'><thead>" 
                                +"<tr class='heading'>" 
                                +"<th colspan='2' class='theadBL' >Environment2</th>" 
                                +"</tr>" 
								+"<tr class='heading'>" 
                                    +"<th class='theadBL'>BrokenLinks</th>" 
									+"<th class='theadBL'>UnbrokenLinks</th>" 
                                +"</tr>"
                            +"</thead>" 
                            +"<tbody>"
                                +"<tr class='content'>" 
                                    +"<td>"+FailCount2+"</td>" 
                                    +"<td>"+PassCount2+"</td>"
                                +"</tr>" 
                            +"</tbody>" 
                        +"</table>"+
		"</div>"
        +"</div>";
	
		else if(env1.size()!=0 && env2.size()==0)
		
			return  "<div class='row' style='margin-top:30px;'>"
			        +"<div class='col-sm-6' id='BLloadContent1'>"
						+"<table id='BLSummary1'><thead>" 
			                                +"<tr class='heading'>" 
			                                    +"<th colspan='2' class='theadBL'>Environment1</th>" 
			                                +"</tr>" 
											+"<tr class='heading'>" 
			                                    +"<th class='theadBL'>BrokenLinks</th>" 
												+"<th class='theadBL'>UnbrokenLinks</th>" 
			                                +"</tr>"
			                            +"</thead>" 
			                            +"<tbody>"
			                                +"<tr class='content'>" 
			                                    +"<td>"+FailCount1+"</td>" 
			                                    +"<td>"+PassCount1+"</td>"
			                                +"</tr>" 
			                            +"</tbody>" 
			                        +"</table>"+"</div>"
			           +"</div>";
		return "";
					
			
	}
	
	public String getCSS() {
		return"<head>\n" + 
				"    <meta charset='UTF-8'>\n" + 
				"    <title>BrokenLinks</title>\n" + 
				"    <style type='text/css'>\n" + 
				"        body {\n" + 
				"            background-color: #ffffff;\n" + 
				"            font-family: Verdana, Geneva, sans-serif;\n" + 
				"            text-align: center;\n" + 
				"        }\n" + 
				"\n" + 
				"        small {\n" + 
				"            font-size: 0.7em;\n" + 
				"        }\n" + 
				"\n" + 
				"        table {\n" + 
				"            width: 95%;\n" + 
				"            margin-left: auto;\n" + 
				"            margin-right: auto;\n" + 
				"        }\n" + 
				"\n" + 
				"        tr.heading {\n" + 
				"            background-color: #A9D0F5;\n" + 
				"            color: #000000;\n" + 
				"            font-size: 0.6em;\n" + 
				"            font-weight: bold;\n" + 
				"        }\n" + 
				"\n" + 
				"        tr.subheading {\n" + 
				"            background-color: #E0E6F8;\n" + 
				"            color: #34495E;\n" + 
				"            font-weight: bold;\n" + 
				"            font-size: 0.6em;\n" + 
				"            text-align: justify;\n" + 
				"        }\n" + 
				"\n" + 
				"        tr.section {\n" + 
				"            background-color: #E0E6F8;\n" + 
				"            color: #333300;\n" + 
				"            cursor: pointer;\n" + 
				"            font-weight: bold;\n" + 
				"            font-size: 0.6em;\n" + 
				"            text-align: justify;\n" + 
				"        }\n" + 
				"\n" + 
				"        tr.subsection {\n" + 
				"            background-color: #EDEEF0;\n" + 
				"            cursor: pointer;\n" + 
				"        }\n" + 
				"\n" + 
				"        tr.content {\n" + 
				"            background-color: #EDEEF0;\n" + 
				"            color: #000000;\n" + 
				"            font-size: 0.6em;\n" + 
				"        }\n" + 
				"\n" + 
				"        td {\n" + 
				"            padding: 4px;\n" + 
				"            text-align: center;\n" + 
				"            word-wrap: break-word;\n" + 
				"            max-width: 450px;\n" + 
				"        }\n" + 
				"\n" + 
				"        th {\n" + 
				"            padding: 4px;\n" + 
				"            text-align: center;\n" + 
				"            max-width: 450px;\n" +
				"        }\n" + 
				"        th.theadBL {\n" + 
				"            text-align: center;\n" +  
				"        }\n" + 
				"        td.justified {\n" + 
				"            text-align: center;\n" + 
				"        }\n" + 
				"\n" + 
				"        td.PASS {\n" + 
				"            font-weight: bold;\n" + 
				"            color: green;\n" + 
				"        }\n" + 
				"\n" + 
				"        td.FAIL {\n" + 
				"            font-weight: bold;\n" + 
				"            color: red;\n" + 
				"        }\n" + 
				"\n" + 
				"        td.done,\n" + 
				"        td.screenshot {\n" + 
				"            font-weight: bold;\n" + 
				"            color: black;\n" + 
				"        }\n" + 
				"\n" + 
				"        td.debug {\n" + 
				"            font-weight: bold;\n" + 
				"            color: blue;\n" + 
				"        }\n" + 
				"\n" + 
				"        td.warning {\n" + 
				"            font-weight: bold;\n" + 
				"            color: orange;\n" + 
				"        }\n" + 
				"        .col-sm-6 {\n" + 
				"            background-color: #ffffff;\n" + 
				"        }\n" + 
				"        .col-6 {\n" + 
				"            background-color: #ffffff;\n" + 
				"        }\n" + 
				"                img { \n" + 
        		"                    width:400px; \n" + 
        		"                    height:300px; \n" + 
        		"                } \n" + 
        		".zoom {padding: 0px;background-color: green;transition: transform .1s;width:150px;height:150px;margin: 0 auto;}"+
        		".zoom:hover {-ms-transform: scale(6);-webkit-transform: scale(6);transform: scale(6);e viewport)}"+
				"    </style>\n" + 
			    "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css'>"
			    +"<script src='https://code.jquery.com/jquery-3.2.1.min.js'></script>"
			    +"<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>"
			    +"<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js'></script>"
				+"</head>";
	}

	public String getScript(List<String[]> env) {
		
		System.out.print("Size of env2" + env.get(0)[0].toString());
		String htmlTableCode = "";

		for (int i = 0; i < env.size(); i++) {
			htmlTableCode +="<tr class='content'><td>"+(i+1)+"</td>"+
					"<td>"+env.get(i)[4].toString()+"</td>"
			+"<td >"+env.get(i)[0].toString()+
			"</td><td>"+ env.get(i)[2].toString()+
			"</td><td class="+env.get(i)[5].toString()+">"+env.get(i)[5].toString()+"</td>"+
			"<td><img class='zoom' src='../Screenshots/"+env.get(i)[6]+"'/></td></tr>";
		}

		return htmlTableCode;
	}
}
