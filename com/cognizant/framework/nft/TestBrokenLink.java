package com.cognizant.framework.nft;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBrokenLink {
	public static void main(String args[]) throws IOException
	{
		System.out.println("Please include this jar in your project ...");
		System.setProperty("webdriver.chrome.driver","F:\\Cogni\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("http://newtours.demoaut.com/");
		BrokenLinks.brokenLinkValidator("http://newtours.demoaut.com/",driver,"./","",0);
		BrokenLinks.brokenLinkValidator("http://abc.demoaut.com/",driver,"./","",0);
		BrokenLinks.brokenLinkValidator("http://efg.demoaut.com/",driver,"./","",0);
	//	BrokenLinks.createBrokenLinkReport("./");
		BrokenLinks.createhtml();
		driver.quit();
	}
}
