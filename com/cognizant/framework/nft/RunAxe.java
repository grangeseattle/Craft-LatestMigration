package com.cognizant.framework.nft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.deque.axe.AXE;

public class RunAxe {

	public static JSONArray run_axe(WebDriver driver, URL scriptUrl) throws JSONException {
		JSONArray violations = null;
		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
		violations = responseJSON.getJSONArray("violations");
		return violations;
	}

	public static String Sub_String_url(String urlString, int count) {
		String urlString1 = null;
		;
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urlString1 = url.getHost().replaceFirst("^[^\\.]+\\.([^\\.]+)\\..*$", "$1");
		String newfile = urlString1 + count + ".json";
		return newfile;
	}

	public static String dynamic_filecreation(String map, JSONArray results) {
		File file;
		FileWriter writerhome;
		String successmessage;
		// creating new file
		file = new File(map);
		try {
			writerhome = new FileWriter(file);
			writerhome.write(results.toString());
			writerhome.flush();
			writerhome.close();
			if (file.exists()) {
				successmessage = "file is created";
				System.out.println(successmessage);
			} else {
				successmessage = "file not created";
				System.out.println(successmessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public List<String> filelist1(List<String> filename) {
		return filename;
	}

	public static void main(String[] args) {
	}

}
