package businesscomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;
import com.cognizant.framework.nft.AccessibilityNFT;
import com.cognizant.framework.nft.PerformanceNFT;
import com.cognizant.framework.nft.SecurityNFT;


public class PetclinicComponents extends ReusableLibrary  {

	public PetclinicComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	
	//add 
	public void addOwner()
	{
		
		name="ganesh";
		int i=0;
		String applicationurl = null;
		if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance1")) {
			applicationurl = properties.getProperty("ApplicationUrl1");
		} else if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance2")) {
			applicationurl = properties.getProperty("ApplicationUrl2");
		}	 
		String webAppURL1 = System.getProperty("ApplicationUrl1");
		String webAppURL2 = System.getProperty("ApplicationUrl2");
		
		
			
			if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance1")) {
				if ((webAppURL1 != null && !webAppURL1.isEmpty() && !webAppURL1.equals(""))) 
				{
					applicationurl = webAppURL1;
					System.out.println("System property-Application URL 1:"+webAppURL1);
				}
				
				
			} else if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance2")) {
				if(webAppURL2 != null && !webAppURL2.isEmpty() && !webAppURL2.equals("")) 
				{
					applicationurl = webAppURL2;
					System.out.println("System property-Application URL 2:"+webAppURL2);
				}
				

			}
			
//			
//			applicationurl = "http://localhost:8080/petclinic/";
//		 }else
//		 {
//			 applicationurl=applicationurl;
//		 }
		
		driver.get(applicationurl);
		
	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		report.updateTestLog("Navigate", "Petclinic", Status.PASS);
		 brokenLinkValidatorReusable(applicationurl, driver.getWebDriver());
		
		
			 AccessibilityNFT.evaluatePageAccessibilityTest(driver.getWebDriver(),applicationurl,scriptHelper);
		 if (Boolean.parseBoolean(properties.getProperty("performanceReport"))) {
			 PerformanceNFT.evaluatePerformanceForPage(driver.getWebDriver(),driver.getCurrentUrl(),scriptHelper);
		 }
		 
		 if (Boolean.parseBoolean(properties.getProperty("securityReport"))) {
				SecurityNFT.evaluateSecurityForPage(driver.getCurrentUrl(),
						scriptHelper);

		 }

		
		
		
		
		
		
		
		String homeLabel = driver.findElement(By.xpath("//*[text()='Home']")).getText();
		Assert.assertEquals("HOME", homeLabel);
		report.updateTestLog("Asser", "Home Page Label", Status.PASS);
		
		
		driver.findElement(By.xpath("//*[text()='Find owners']")).click();
		report.updateTestLog("Click", "Add Owner Label", Status.PASS);
		driver.findElement(By.linkText("Add Owner")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		WebElement element = driver.findElement(By.xpath("//input[@name='firstName']"));
		element.clear();
		element.sendKeys("John");
		
		
		report.updateTestLog("Input", "First Name", Status.PASS);
		
		WebElement element1 = driver.findElement(By.xpath("//input[@name='lastName']"));
		element1.clear();
		element1.sendKeys("Cena");
		report.updateTestLog("Input", "Last Name", Status.PASS);
		
		WebElement element2 = driver.findElement(By.xpath("//input[@name='address']"));
		element2.clear();
		element2.sendKeys("xyz");
		
		report.updateTestLog("Input", "Address", Status.PASS);
		WebElement element3 = driver.findElement(By.xpath("//input[@name='city']"));
		element3.clear();
		element3.sendKeys("Pune");
		report.updateTestLog("Input", "City Name", Status.PASS);
		
		WebElement element4 = driver.findElement(By.xpath("//input[@name='telephone']"));
		element4.clear();
		element4.sendKeys("7798662305");
		
		report.updateTestLog("Input", "Telephone Number", Status.PASS);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isTelphoneDisplayed = driver.findElement(By.xpath("//*[text()='7798662305']")).isDisplayed();
		Assert.assertEquals(isTelphoneDisplayed, true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		report.updateTestLog("Assert", "Telephone Number", Status.PASS);
	}
	public void editOwner()
	{
		String myname=name;
		System.out.println("-----------------------"+myname);
		
		String webAppURL = System.getProperty("web.app.docker.ip");
		
		if (!(webAppURL != null && !webAppURL.isEmpty() && !webAppURL.equals(""))) {
			webAppURL = "http://10.120.100.42:8080/petclinic/";
		 }
		
		driver.get(webAppURL);
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		report.updateTestLog("Navigate", "Petclinic", Status.PASS);
		
		
		
		
		
		String homeLabel = driver.findElement(By.xpath("//*[text()='Home']")).getText();
		Assert.assertEquals("HOME", homeLabel);
		report.updateTestLog("Asser", "Home Page Label", Status.PASS);
		
		

		driver.findElement(By.xpath("//*[text()='Find owners']")).click();
		report.updateTestLog("Navigate", "Search Owner", Status.PASS);
		
		
		WebElement element1 = driver.findElement(By.xpath("//input[@name='lastName']"));
		element1.clear();
		element1.sendKeys("Cena");
		report.updateTestLog("Input", "Last Name", Status.PASS);
		
		
		driver.findElement(By.xpath("//*[text()='Find Owner']")).click();
		report.updateTestLog("Navigate", "Search Owner", Status.PASS);
		
		driver.findElements(By.xpath("//a[text()='John Cena']")).get(0).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		report.updateTestLog("Navigate", "Click on Edit Owner", Status.PASS);
		driver.findElement(By.xpath("//a[text()='Edit Owner']")).click();
		
		
		
		WebElement element = driver.findElement(By.xpath("//input[@name='firstName']"));
		element.clear();
		element.sendKeys("John");
		
		
		report.updateTestLog("Input", "First Name", Status.PASS);
		
		WebElement element10 = driver.findElement(By.xpath("//input[@name='lastName']"));
		element10.clear();
		element10.sendKeys("Cena");
		report.updateTestLog("Input", "Last Name", Status.PASS);
		
		WebElement element2 = driver.findElement(By.xpath("//input[@name='address']"));
		element2.clear();
		element2.sendKeys("xyz");
		
		report.updateTestLog("Input", "Address", Status.PASS);
		WebElement element3 = driver.findElement(By.xpath("//input[@name='city']"));
		element3.clear();
		element3.sendKeys("Pune");
		report.updateTestLog("Input", "City Name", Status.PASS);
		
		WebElement element4 = driver.findElement(By.xpath("//input[@name='telephone']"));
		element4.clear();
		element4.sendKeys("7798662305");
		
		report.updateTestLog("Input", "Telephone Number", Status.PASS);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isTelphoneDisplayed = driver.findElement(By.xpath("//*[text()='7798662305']")).isDisplayed();
		Assert.assertEquals(isTelphoneDisplayed, true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		report.updateTestLog("Assert", "Telephone Number", Status.PASS);
	}

}
