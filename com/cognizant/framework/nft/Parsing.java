package com.cognizant.framework.nft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Parsing
{
	private static final String IndexName1 = null;
	ArrayList<JSONArray> json = new ArrayList<JSONArray>();
	JSONArray json1 = new JSONArray();
	String help = null;
	String RunId = "1";
	String description = null;
	String id = null;
	String helpUrl = null;
	String html = null;
	String impact = null;
	JSONArray nodes2 = null;
	String message = null;
	String TransportClient=null;
	String project_name = null;
	String run_id = null;
	
	JSONArray obj5;
	JSONArray obj6;
	List<String> obj7;

	JSONArray newarray;
	JSONObject object1;
	
	//Declaration for github
	
	

	// trying

	JSONObject counterobject1 = new JSONObject();
	JSONArray counterarray1 = new JSONArray();
	JSONObject mainObject = new JSONObject();
	JSONObject runID = new JSONObject();
	JSONArray counterarray = new JSONArray();

	public ArrayList<JSONArray> parsingfile(List<String> filelist, ArrayList<String> pagename, ArrayList<String> envList, ArrayList<String> scenarioTCList)throws FileNotFoundException, IOException, ParseException  {
		

		for (int m = 1; m <= filelist.size(); m++) {

			object1 = new JSONObject();
			String filename = filelist.get(m - 1);
			String pagenames = pagename.get(m - 1);
			String env = envList.get(m - 1);
			String scenarioTestcase = scenarioTCList.get(m - 1);
			//System.out.println("filelist.size():"+filename);
			newarray = new JSONArray();
			
			

			JSONObject countobject = new JSONObject();
			JSONParser parser = new JSONParser();
			JSONArray obj = (JSONArray) parser.parse(new FileReader(filename)); // the
																				// location
																				// of
																				// the
																				// file
			
			//System.out.println("json result :" + obj);
			JSONArray array1 = (JSONArray) obj;
			// System.out.println("Helpval :"+array1.size());
			int counter = 0;
			int counter1 = 0;
			int counter2 = 0;
			int counter3 = 0;
			for (int i = 0; i < array1.size(); i++) {

				JSONObject obj1 = (JSONObject) array1.get(i);
				help = (String) obj1.get("help");
				description = (String) obj1.get("description");
				id = (String) obj1.get("id");
				helpUrl = (String) obj1.get("helpUrl");

				JSONArray nodes = (JSONArray) obj1.get("nodes");
				// System.out.println("nodes :"+nodes.size());
				
				obj5 = new JSONArray();
				obj6 = new JSONArray();
				obj7 = new ArrayList<String>();

				for (int j = 1; j <= nodes.size(); j++) {
					JSONObject obj2 = (JSONObject) nodes.get(j - 1);
					// System.out.println("nodes :"+nodes);
					html = (String) obj2.get("html");
					obj6.add(html);
					impact = (String) obj2.get("impact");

					nodes2 = (JSONArray) obj2.get("target");
					String obj223 = (String) nodes2.get(0);
					// System.out.println("target :"+nodes2);
					obj5.add(obj223);

					JSONArray nodesnone = (JSONArray) obj2.get("none");
					for (int l = 1; l <= nodesnone.size(); l++) {
						// System.out.println("nodesize 1:"+nodes.size());
						JSONObject obj22 = (JSONObject) nodesnone.get(l - 1);
						message = (String) obj22.get("message");
						// System.out.println("any1 :"+message);
						if (message != null) {
							obj7.add(message);

						}
					}

					JSONArray nodes11 = (JSONArray) obj2.get("any");

					// System.out.println("nodesize :"+nodes.size());
					for (int k = 1; k <= nodes11.size(); k++) {
						// System.out.println("nodesize 1:"+nodes.size());
						JSONObject obj22 = (JSONObject) nodes11.get(k - 1);
						message = (String) obj22.get("message");
						// System.out.println("any1 :"+message);
						if (message != null) {
							obj7.add(message);
						}

						// System.out.println("messagecoming :"+obj4);
					}
					// JSONArray target = (JSONArray)obj1.get("target");
				}

				// System.out.println("countervalue :"+counter);
				JSONArray tags = (JSONArray) obj1.get("tags");
				// System.out.println("Guidelines :"+tags);

				if (impact.equalsIgnoreCase("critical")) {
					counter++;

				} else if (impact.equalsIgnoreCase("serious")) {
					counter1++;
				} else if (impact.equalsIgnoreCase("moderate")) {
					counter2++;
				} else if (impact.equalsIgnoreCase("minor")) {
					counter3++;
				}
				// System.out.println("Formatted JSON counter:" +counter);
				// System.out.println("Formatted JSON counter1:" +counter1);
				// System.out.println("Formatted JSON counter2:" +counter2);

				JSONObject object = new JSONObject();
				String recommendations="--> Element does not have an alt attribute" +"\n"  +
						"--> aria-label attribute does not exist or is empty" +"\n"  +
						"--> aria-labelledby attribute does not exist, references elements that do not exist or references elements that are empty or not visible" +"\n"  +
						"--> Element has no title attribute or the title attribute is empty" +"\n"  +
						"--> Element's default semantics were not overridden with role=presentation" +"\n"  +
						"--> Element's default semantics were not overridden with role=none" ;
				
				recommendations=recommendations.replaceAll("\"", "");
				
				String heading="--> Element does not have text that is visible to screen readers" +"\n"  +
						"--> Element's default semantics were not overridden with role=presentation" +"\n"  +
						"--> Element's default semantics were not overridden with role=none" ;
				
				heading=heading.replaceAll("\"", "");
				
				
				String frames="--> aria-label attribute does not exist or is empty" +"\n"  +
						"--> aria-labelledby attribute does not exist, references elements that do not exist or references elements that are empty or not visible" +"\n"  +
						"--> Element has no title attribute or the title attribute is empty" +"\n"  +
						"--> Element's default semantics were not overridden with role=presentation" +"\n"  +
						"--> Element's default semantics were not overridden with role=none" ;
				
				frames=frames.replaceAll("\"", "");
				
				
				String forms="--> aria-label attribute does not exist or is empty" +"\n"  +
						"--> aria-labelledby attribute does not exist, references elements that do not exist or references elements that are empty or not visible" +"\n"  +
						"--> Form element does not have an implicit (wrapped) <label>" +"\n"  +
						"--> Form element does not have an explicit <label>" +"\n"  +
						"--> Element has no title attribute or the title attribute is empty" ;
				
				forms=forms.replaceAll("\"", "");
				
				
				String links="--> Element is in tab order and does not have accessible text" +"\n"  +
						"--> Element does not have text that is visible to screen readers" +"\n"  +
						"--> aria-label attribute does not exist or is empty" +"\n"  +
						"--> aria-labelledby attribute does not exist, references elements that do not exist or references elements that are empty or not visible" +"\n"  +
						"--> Element's default semantics were not overridden with role=presentation" +"\n"  + 
				        "--> Element's default semantics were not overridden with role=none" ;
				links=links.replaceAll("\"", "");
				
				
				String colorcontrast="--> Element has insufficient color contrast of 2.74 (foreground color: #00bced, background color: #52656a, font size: 19.5pt, font weight: normal)" +"\n"  +
						"--> Expected contrast ratio of 3:1" +"\n"  +
						"--> Element has insufficient color contrast of 4.4 (foreground color: #ffffff, background color: #3c8834, font size: 17.3pt, font weight: normal)." +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 4.4 (foreground color: #ffffff, background color: #3c8834, font size: 13.5pt, font weight: normal). " +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 3.01 (foreground color: #ffffff, background color: #5da62d, font size: 15.0pt, font weight: normal)." +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 3.28 (foreground color: #ffffff, background color: #4298b5, font size: 12.0pt, font weight: bold). " +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 3.28 (foreground color: #ffffff, background color: #4298b5, font size: 12.0pt, font weight: bold). " +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 3.28 (foreground color: #ffffff, background color: #4298b5, font size: 11.3pt, font weight: normal). " +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 2.37 (foreground color: #ebebeb, background color: #999999, font size: 8.3pt, font weight: bold). " +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 4.13 (foreground color: #3385ad, background color: #ffffff, font size: 9.0pt, font weight: normal)." +"\n"  +
						"--> Expected contrast ratio of 4.5:1" +"\n"  +
						"--> Element has insufficient color contrast of 2.65 (foreground color: #999999, background color: #f7f7f7, font size: 8.3pt, font weight: normal). " +"\n"  +
						"--> Expected contrast ratio of 4.5:1";
				       
				        colorcontrast=colorcontrast.replaceAll("\"", "");
				        
				        String color="-- >Ensures the contrast between foreground and background colors meets WCAG 2 AA contrast ratio thresholds." +"\n"  +
								"--> Expected contrast ratio of 4.5:1 for the below mentioned issues" ;
				        
				        color=color.replaceAll("\"", "");
				
				        String idattribute="--> Document has multiple elements with the same id attribute: hidden-content" +"\n"  +
								"--> Document has multiple elements with the same id attribute: back" +"\n"  +
								"--> Document has multiple elements with the same id attribute: Page-1" +"\n"  +
								"--> Document has multiple elements with the same id attribute: Shape " +"\n"  +
								"--> Document has multiple elements with the same id attribute: videoCode_odd" +"\n"  +
								"--> Document has multiple elements with the same id attribute: segments-we-serve" +"\n"  +
								"--> Document has multiple elements with the same id attribute: bluepage" +"\n"  +
								"--> Document has multiple elements with the same id attribute: OLT" +"\n"  +
								"--> Document has multiple elements with the same id attribute: callout" +"\n"  +
								"--> Document has multiple elements with the same id attribute: segments" +"\n"  +
								"--> Document has multiple elements with the same id attribute: callout-tab" +"\n"  + 
						        "--> Document has multiple elements with the same id attribute: videoCode" ;
				        idattribute=idattribute.replaceAll("\"", "");
						
				        String groups="--> List element has direct children that are not allowed inside <dt> or <dd> elements" ;
						
				        groups=groups.replaceAll("\"", "");
				        
				        String lang="--> The <html> element does not have a lang attribute" ;
						
				        lang=lang.replaceAll("\"", "");
				        
				        String zoom="--> <meta> tag disables zooming on mobile devices" ;
						
				        zoom=zoom.replaceAll("\"", "");
				        
				        String zero="--> Element has a tabindex greater than 0" ;
						
				        zero=zero.replaceAll("\"", "");
				        
				        String checkbox="--> All elements with the name \"quesImage\" do not reference the same element with aria-labelledby"+","+"\n"+"Fieldset does not have a legend as its first child" ;
						
				        checkbox=checkbox.replaceAll("\"", "");
				        
				        String aria="--> ARIA attribute is not allowed: aria-selected=true" ;
						
				        aria=aria.replaceAll("\"", "");
				        
				        String certainaria="--> Required ARIA child role not present: tab" ;
						
				        certainaria=certainaria.replaceAll("\"", "");
						
						
						
				System.out.println(recommendations);
					object.put("Help", help);
					object.put("description", description);
					object.put("id", id);
					object.put("helpUrl", helpUrl);
					if(!obj7.isEmpty() || obj7!=null)
					{
					if(help.equalsIgnoreCase("Images must have alternate text"))
					{
					object.put("message", recommendations);
					}
					else if(help.equalsIgnoreCase("Headings must not be empty"))
					{
					object.put("message", heading);
					}
					else if(help.equalsIgnoreCase("Frames must have title attribute"))
					{
					object.put("message", frames);
					}
					else if(help.equalsIgnoreCase("Form elements must have labels"))
					{
					object.put("message", forms);
					}
					else if(help.equalsIgnoreCase("Links must have discernible text"))
					{
					object.put("message", links);
					}
					else if(help.equalsIgnoreCase("Elements must have sufficient color contrast"))
					{
					object.put("message", color);
					}
					else if(help.equalsIgnoreCase("id attribute value must be unique"))
					{
					object.put("message", idattribute);
					}
					else if(help.equalsIgnoreCase("elements must only directly contain properly-ordered and groups"))
					{
					object.put("message", groups);
					}
					else if(help.equalsIgnoreCase("element must have a lang attribute"))
					{
					object.put("message", lang);
					}
					else if(help.equalsIgnoreCase("Zooming and scaling must not be disabled"))
					{
					object.put("message", zoom);
					}
					else if(help.equalsIgnoreCase("Elements should not have tabindex greater than zero"))
					{
					object.put("message", zero);
					}
					else if(help.equalsIgnoreCase("Checkbox inputs with the same name attribute value must be part of a group"))
					{
					object.put("message", checkbox);
					}
					else if(help.equalsIgnoreCase("Elements must only use allowed ARIA attributes"))
					{
					object.put("message", aria);
					}
					else if(help.equalsIgnoreCase("Certain ARIA roles must contain particular children"))
					{
					object.put("message", certainaria);
					}
					else{
						object.put("message", obj7.toString().replaceAll(",", "\n\n").replaceAll("\"", "").replaceAll("[\\[\\]]", ""));
					}
					//if(obj7.toString().equalsIgnoreCase("Element"))
					}
					else
					{
						object.put("message", "Not Available");
						
					}
					
					//System.out.println(recommendations.replaceAll("\"", "")+"------------------"+);
					object.put("html", obj6.toJSONString().replaceAll(",", "\n\n").replaceAll("\"", "").replaceAll("[\\[\\]]", ""));
					System.out.println(obj6.toJSONString().replaceAll(",", "\n\n").replaceAll("\"", "").replaceAll("[\\[\\]]", ""));
					object.put("impact", impact);
					object.put("Guidelines", tags);
					object.put("Target", obj5);
					object1.put("pagename", pagenames);
					object1.put("environment", env);
					object1.put("scenariotestcase", scenarioTestcase);
					newarray.add(object);
				
				
			}
			countobject.put("pagename", pagenames);
			countobject.put("critical", counter);
			countobject.put("serious", counter1);
			countobject.put("moderate", counter2);
			countobject.put("minor", counter3);
			countobject.put("environment", env);
			countobject.put("scenariotestcase", scenarioTestcase);
			counterarray.add(countobject);

			newarray.add(object1);
			
			json.add(newarray);
			//json1.add(counterarray);

		}
                
		
		System.out.println("Violation Result:" + json);
         
		//Declaration for github
			
	       
        
        File myfile = new File( "violations.json");
        myfile.createNewFile();
        
        FileWriter writerviolationsfile = new FileWriter(myfile);
        writerviolationsfile.write(json.toString());
		writerviolationsfile.flush();
		writerviolationsfile.close();
       
		/*Properties properties = new Properties();
		FileInputStream propertiesFile;

		propertiesFile = new FileInputStream("C:/Jar/datafile.properties");
		properties.load(propertiesFile);

		System.out.println(properties.getProperty("URL"));*/
		
            
		    mainObject.put("rundetails", counterarray);
			//mainObject.put("runid",1);
			
			json1.add(mainObject);
		
			System.out.println("Severity Count Result:" + json1);
			
			File myfile1 = new File("donut.json");
	        myfile1.createNewFile();
	        
	        FileWriter writerviolationsfile1 = new FileWriter(myfile1);
	        writerviolationsfile1.write(mainObject.toString());
			writerviolationsfile1.flush();
			writerviolationsfile1.close();
			
			
	       
	       
			//return json1;
			return counterarray;
	

	}
}
