package com.cognizant.framework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLComparisionReport {

    
    void createUsingIframes( String title, String firstFile, String secondFile ,String constructReportPath)
    {
    	
    	try {
       String htmlString ="<html>\n" + 
        		"    <head>\n" + 
        		"            <meta charset='UTF-8'> \n" + 
        		"            <title>"+title+"</title> \n" + 
        		"            <style type='text/css'> \n" + 
        		getCSS()   +
        		"   	<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css'>\n" + 
        		"       <script src='https://code.jquery.com/jquery-3.2.1.min.js'></script>\n" + 
        		"      <script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>\n" + 
        		"      <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js' ></script>\n" + 
        		"</head>\n" + 
        		"    <body>\n" + 
        		"        <div class='row'>\n" + 
        		"        <div class='col-6' id='loadContent1'>\n" + "<iframe src=./"+firstFile+".html></iframe>"+
        		"        </div>\n" + 
        		"        <div class='col-6' id='loadContent2'>\n" + "<iframe src=./"+secondFile+".html></iframe>"+
        		"            </div>\n" + 
        		"        </div>\n" + 
        		"    </body>\n" + 
        		"     \n" + 

        		"</html>\n" ;
        Document doc = Jsoup.parse(htmlString);
        WriteToFile(doc,constructReportPath);
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Inside the exception");
    } 
    }
    
    public static void WriteToFile(Document doc, String fileName) throws IOException {
       // String projectPath = System.getProperty("user.dir");
        File file = new File(fileName);
        file.createNewFile();
        FileUtils.writeStringToFile(file, doc.outerHtml(), "UTF-8");

    }
    
    public String getCSS()
    {
    	return  "                body { \n" + 
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
        		".zoom {padding: 0px;background-color: green;transition: transform .1s;width:100px;height:100px;margin: 0 auto;}"+
        		".zoom:hover {-ms-transform: scale(6);-webkit-transform: scale(6);transform: scale(6);e viewport)}"+
        		"	iframe{"+
				"height: 100vh;"+
    			"width: 100%;"+
			" }"+
        		"            </style> \n" ;
    }
}
