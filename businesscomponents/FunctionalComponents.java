package businesscomponents;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;


/**
 * Functional Components class
 * @author pmani
 */
public class FunctionalComponents extends ReusableLibrary
{
	/*************** Common Public Variables, functions and Objects ********************************************************
	 * 
	 */
		public static String LoB;
		public static String EffDate;
		public static String BindDate;
		public static String PolicyNumber;
		public static String TestCase;
		public static String Transaction;
		public static String State;
		public static int billFlag = 0;
		public static String parentWindowHandle;
		public static WebDriver popup;
		public static String NamedInsured;
		
		
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 15); 
		Alert alert;
		
		public boolean isAlertPresent() throws InterruptedException 
	    { 
	        try 
	        { 
	        	Thread.sleep(2000);
	        	driver.switchTo().alert().accept();
	            return true; 
	        }   
	        catch (NoAlertPresentException Ex) 
	        { 
	            return false; 
	        }   
	    }
		public boolean existsElement(String tag,String id,String text) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			try {
		    	
		    	if(tag.isEmpty()==false && text.isEmpty()==false && id.isEmpty()==false)
		    	{
		    		driver.findElement(By.xpath("//" + tag+ "[contains(@id, '"+id+"') and .//text()='"+text+"']"));
		    		return true;
		    	}
		    	if(text.isEmpty()==false && id.isEmpty()==false)
		    	{
		    		driver.findElement(By.xpath("//*[contains(@id, '"+id+"') and .//text()='"+text+"']"));
		    	}
		    	if(id.isEmpty()==true && text.isEmpty()==false)
		    	{
		    		driver.findElement(By.xpath("//*[contains(text(),'"+text+"')]"));
		    	}
		    	if(id.isEmpty()==false && text.isEmpty()==true)
		    		driver.findElement(By.xpath("//*[contains(@id, '"+id+"')]"));
		    	//driver.findElement(By.xpath("//*[contains(@id, 'AddToolStipButtonMiddle']"));
		    } catch (Exception exception) {
		        return false;
		    }
		    return true;
		}
		
		
				
		public void highlightElement(WebElement element) {
		    for (int i = 0; i < 2; i++) {
		        JavascriptExecutor js = (JavascriptExecutor) driver;
		        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
		                element, "color: yellow; border: 2px solid yellow;");
		        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
		                element, "");
		    }
		}

	/**
	 * 	
	 ***********************************************************************************************************************/
	/**
	 * Constructor to initialize the component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public FunctionalComponents(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}

	/*******************************************************************************************************************************
	 *  Function Automated		-	Invoke GOQuote		
	 *  Function Description	-	To Invoke Diamond Application
	 *  Author					-	jkumar
	 *  Script Created on		-	08/06/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void invokeGQ() throws Exception 
	{	
		
		TestCase = dataTable.getData("General_Data", "TC_ID");
		LoB = dataTable.getData("General_Data", "LoB").toUpperCase().trim();
		
		driver.get(properties.getProperty("GQUrl"));
		String S1=properties.getProperty("GQPass");
		
		//To encrypt the Password.
		byte[] encodedPwdBytes = Base64.encodeBase64(S1.getBytes());

				String encodedPwd= new String(encodedPwdBytes);
				System.out.println(encodedPwd);
		byte[] decodedPwdBytes = Base64.decodeBase64(S1);
		System.out.println(decodedPwdBytes); 
		String decodedPwd= new String(decodedPwdBytes);
		 
		System.out.println(decodedPwd); 
		report.updateTestLog("Invoke GoQuote", "Invoke the application under test @ " +
								properties.getProperty("GQUrl"), Status.DONE);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'PageContent_txtUsername')]")));
		driver.findElement(By.xpath("//input[contains(@id,'PageContent_txtUsername')]")).click();
		actions.sendKeys("automation").perform();
		//Tab stopped working - GPE 9/21/16
		//actions.sendKeys(Keys.TAB).perform();
		Thread.sleep(250);
		driver.findElement(By.xpath("//input[contains(@id,'PageContent_txtPassword')]")).click();
		actions.sendKeys(decodedPwd).perform();
		actions.sendKeys(Keys.TAB).build().perform();
		driver.findElement(By.xpath("//*[@id='PageContent_btnLogIn']")).click();
		//actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id,'PageContent_btnLogIn')]"))).doubleClick().build().perform();
		Thread.sleep(1000);
				
		driver.findElement(By.partialLinkText("GO Quote!")).click();
		Thread.sleep(500);
		if (LoB.equals("DWELLINGFIRE")) 
		{
			driver.findElement(By.partialLinkText("Dwelling Fire")).click();
			Thread.sleep(500);
		}
		if (LoB.equals("HOME")) 
		{
			driver.findElement(By.partialLinkText("Homeowners")).click();
			Thread.sleep(500);
		}
		if (LoB.equalsIgnoreCase("UMBRELLA")) 
		{
			driver.findElement(By.partialLinkText("Umbrella")).click();
			Thread.sleep(500);
		}
		if (LoB.equalsIgnoreCase("PERSONAL AUTO")) 
		{
			driver.findElement(By.partialLinkText("Personal Auto")).click();
			Thread.sleep(500);
		}
		
		if (LoB.equalsIgnoreCase("COMMERCIAL FARM")) 
		{

			//actions.moveToElement(driver.findElement(By.xpath("//*[@id='PageContent_divStaticContent']/div/ul/li[5]/a/div/h3"))).click().build().perform();
			actions.sendKeys(Keys.PAGE_DOWN).build().perform();
			driver.findElement(By.partialLinkText("Farm")).click();
			Thread.sleep(500);
		}
		 parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
	       popup = null;
	      Set<String> windowIterator = driver.getWindowHandles();
	      Iterator<String> itererator = windowIterator.iterator();   
	      while(itererator.hasNext()) { 
	        String windowHandle = itererator.next(); 
	        popup = driver.switchTo().window(windowHandle);
	        if (popup.getTitle().contains("Quote Index")) {
	          break;
	        }
	      }

		Thread.sleep(3000);
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'Content_uxbtnCreate')]")));
		
		
		
	}
	

	
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Invoke Diamond		
	 *  Function Description	-	To Invoke Diamond Application
	 *  Author					-	pmani
	 *  Script Created on		-	03/05/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void invokeDiamond() throws Exception 
	{	
		String applicationurl = null;
		TestCase = dataTable.getData("General_Data", "TC_ID");
		System.out.println(scriptHelper.getTestInstance().equalsIgnoreCase("Instance1"));
		 if(scriptHelper.getTestInstance().equalsIgnoreCase("Instance1"))
 		{
 	applicationurl= properties.getProperty("ApplicationUrl1");
 		} 
else if(scriptHelper.getTestInstance().equalsIgnoreCase("Instance2"))
 {
 	applicationurl= properties.getProperty("ApplicationUrl2");
 } 


		//driver.get(properties.getProperty("ApplicationUrl"));
		driver.get(applicationurl);
		scriptHelper.getReport().getSeleniumTestParameters().getCurrentScenario(); 
		 report.updateTestLog("Invoke Diamond", "Invoke the application under test @ " +
				 applicationurl, Status.PASS);
		 report.updateTestLog("Invoke Diamond", "Invoke the application under test @ " +
				 applicationurl, Status.SCREENSHOT);
		//return;
	}
	
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Application Submission		
	 *  Function Description	-	To Enter Applicant name, address details and submit the application
	 *  Author					-	pmani
	 *  Script Created on		-	03/05/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	public void applicationSubmission(String trans) throws Exception 
	{
		driver.findElement(By.id("PoliciesMenu")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='PoliciesSubMenu_0']/tbody/tr/td[2]/div/a"))); 
		WebElement menuHoverLink = driver.findElement(By.xpath("//*[@id='PoliciesSubMenu_0']/tbody/tr/td[2]/div/a"));
		actions.moveToElement(menuHoverLink).perform();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='NewPolicySubMenu_0']/tbody/tr/td[2]/a")));
		WebElement subLink = driver.findElement(By.xpath("//*[@id='NewPolicySubMenu_0']/tbody/tr/td[2]/a"));
		actions.moveToElement(subLink).perform();
		actions.click();
		actions.perform();
		Thread.sleep(200);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		/*String Nametype = dataTable.getData("Application_Submission", "NameType",trans);
		String Name1 = dataTable.getData("Application_Submission", "CommercialName1",trans);
		String Name2 = dataTable.getData("Application_Submission", "CommercialName2",trans);
		//LegalEntity
		String LegalEntity = dataTable.getData("Application_Submission", "LegalEntity",trans);*/
		String FirstName = dataTable.getData("Application_Submission", "FirstName",trans);
		String LastName = dataTable.getData("Application_Submission", "LastName",trans);
		String DoB = dataTable.getData("Application_Submission", "DoB",trans);
		String SSN = dataTable.getData("Application_Submission", "SSN",trans);
		String Gender = dataTable.getData("Application_Submission", "Gender",trans);
		String MarStatus = dataTable.getData("Application_Submission", "MarStatus",trans);
		//if(Nametype.equalsIgnoreCase("Personal Name")==true)
		{
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_First")));
		driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_First")).sendKeys(FirstName);
		driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_Last")).sendKeys(LastName);
		actions.sendKeys(Keys.TAB);
		driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_PersonalLegalEntityTypeCombo_D_I")).sendKeys("Corporation");
		actions.sendKeys(Keys.TAB).build().perform();
		DoB= DoB.replaceAll("/", "");
		String DoBdefaultVal = driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_BirthDate_BirthDate_I")).getAttribute("value");
		do {
			driver.findElement(By.xpath("//input[contains(@id,'P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_BirthDate_BirthDate_I')]")).sendKeys(DoB);
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_BirthDate_BirthDate_I")).sendKeys(Keys.CONTROL, "a");
			Thread.sleep(500);
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_BirthDate_BirthDate_I")).sendKeys(DoB);
			DoBdefaultVal = driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_BirthDate_BirthDate_I")).getAttribute("value");
			DoBdefaultVal = DoBdefaultVal.replaceAll("/", "");
		} while (DoBdefaultVal.equalsIgnoreCase(DoB) == false);
		
		//SSN
		String SSNdefaultVal = driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_PersonalTaxNumber_PersonalTaxNumber")).getAttribute("value");
		do {
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_PersonalTaxNumber_PersonalTaxNumber")).click();
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_PersonalTaxNumber_PersonalTaxNumber")).sendKeys(Keys.CONTROL, "a");
			Thread.sleep(500);
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_PersonalTaxNumber_PersonalTaxNumber")).sendKeys(SSN);
			SSNdefaultVal = driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_PersonalTaxNumber_PersonalTaxNumber")).getAttribute("value");
			SSNdefaultVal = SSNdefaultVal.replaceAll("-", "");
		} while (SSNdefaultVal.equalsIgnoreCase(SSN) == false);
		
		//Gender
		String defGender="";
		do {
		actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_Sex_D_I"))).click().perform();
		Thread.sleep(250);
		//actions.sendKeys(Gender).build().perform();
		if(Gender.equalsIgnoreCase("M Male")==true)
		{
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(250);
		//driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_Sex_D_I")).sendKeys(Gender);
		actions.sendKeys(Keys.ENTER).build().perform();
		}
		else if(Gender.equalsIgnoreCase("F Female")==true)
		{
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(250);
			actions.sendKeys(Keys.ENTER).build().perform();
		}
		
		defGender=driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_Sex_D_I")).getAttribute("value");
		Thread.sleep(250);
		
		} while (defGender.equalsIgnoreCase(Gender)==false);
		
		//MarStatus
		
		String defMarStatus="";
		do
		{
		actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_MaritalStatus_D_I"))).click().perform();
		
		actions.sendKeys(MarStatus).build().perform();
		Thread.sleep(300);
		actions.sendKeys(Keys.ENTER).build().perform();
		defMarStatus=driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_MaritalStatus_D_I")).getAttribute("value");
		System.out.println(defMarStatus);
		if(defMarStatus.equalsIgnoreCase(MarStatus)==false)
		{
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(250);
			actions.sendKeys(Keys.ENTER).build().perform();
		}
		Thread.sleep(250);
		}while(defMarStatus.equalsIgnoreCase(MarStatus)==false);
		
		//State
		State = dataTable.getData("Application_Submission", "State",trans);
		String DLN=dataTable.getData("Application_Submission", "DLN",trans);
		
		actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_LicenseNumber_LicenseNumber"))).click().perform();
		actions.sendKeys(DLN).build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_LicenseState_D"))).click().perform();
		actions.sendKeys(State).build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		}
		
		/*else if(Nametype.equalsIgnoreCase("Commerical Name")==true)
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_CommercialNameOption"))).click();
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_CommercialName1")).click();
			actions.sendKeys(Name1).build().perform();
			Thread.sleep(500);
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_LegalEntityTypeInsCombo_D_I")).click();
			actions.sendKeys(LegalEntity).build().perform();Thread.sleep(500);
		}*/
		
		
		//Line of Business
		LoB = dataTable.getData("General_Data", "LoB").toUpperCase().trim();
		
		String StreetNumber = dataTable.getData("Application_Submission", "StreetNumber",trans);
		String Street = dataTable.getData("Application_Submission", "Street",trans);
		String City = dataTable.getData("Application_Submission", "City",trans);
		
		String ZIP = dataTable.getData("Application_Submission", "ZIP",trans);
		String County = dataTable.getData("Application_Submission", "County",trans);
		 
		
		
		
		if (LoB.equals("DWELLINGFIRE")) {
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_AddressTypeRadioButtonList_1")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//PO-Box Address
				String POBox = dataTable.getData("Application_Submission", "POBox",trans);
				driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddressPOBox_PostOfficeBox")).sendKeys(POBox);
				driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddressPOBox_City")).sendKeys(City);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddressPOBox_AddressState_D_I"))).click().perform();
				actions.sendKeys(State).perform();
				actions.build();
		        actions.perform();
		        Thread.sleep(500);
		        actions.sendKeys(Keys.TAB).perform();
		        Thread.sleep(1000);
				actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddressPOBox_ZipCode_mtxtMain"))).click().perform();
		        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddressPOBox_ZipCode_mtxtMain")).clear();
		        Thread.sleep(1000);
				actions.sendKeys(ZIP).perform();
				actions.build();
		        actions.perform();
		        Thread.sleep(500);
		                

	        //Physical Address
		        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1PhysicalAddress_HouseNumber")).sendKeys(StreetNumber);
		        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1PhysicalAddress_StreetName")).sendKeys(Street);
		        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1PhysicalAddress_City")).sendKeys(City);
		        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1PhysicalAddress_AddressState_D_I"))).click().perform();
				actions.sendKeys(State).perform();
				actions.build();
		        actions.perform();
		        Thread.sleep(500);
		        actions.sendKeys(Keys.TAB).perform();
		        Thread.sleep(1000);
		        //actions.sendKeys(ZIP).perform();
		        //Thread.sleep(1000);
		        //actions.sendKeys(Keys.TAB).perform();
		       //Replacing above 3 lines 
				actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1PhysicalAddress_ZipCode_mtxtMain"))).click().perform();
		        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1PhysicalAddress_ZipCode_mtxtMain")).clear();
		        Thread.sleep(1000);
				actions.sendKeys(ZIP).perform();
				actions.build();
		        actions.perform();
		        Thread.sleep(500);

		}
		else
		{
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_HouseNumber")).sendKeys(StreetNumber);
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_StreetName")).sendKeys(Street);
			driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_City")).sendKeys(City);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement stateVal = driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_AddressState_D_I"));
			actions.moveToElement(stateVal).click().perform(); 
			actions.sendKeys(State).perform();
			actions.build();
	        actions.perform();
	        Thread.sleep(1500);
	        actions.sendKeys(Keys.TAB).build().perform();
	        actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_ZipCode_mtxtMain"))).click().perform();
	        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_ZipCode_mtxtMain")).clear();
	        Thread.sleep(1000);
			actions.sendKeys(ZIP).perform();
			actions.build();
	        actions.perform();
	        Thread.sleep(500);
	        
	        if (County.equalsIgnoreCase("")==false)
	        {
	        	actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_County"))).click().perform();
	        	actions.sendKeys(County).build().perform();
	        	Thread.sleep(1000);
	        }
	        /*
	        Thread.sleep(500);
	        actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_First"))).click().build().perform();
	        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_ZipCode_mtxtMain")).clear();
	        Thread.sleep(500);
	        driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_ZipCode_mtxtMain")).click();
	         defaultZip = driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_ZipCode_mtxtMain")).getAttribute("value");
	        do{
	        	Thread.sleep(500);
	        	driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_ZipCode_mtxtMain")).sendKeys(ZIP);
	        	defaultZip = driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_ClientAddress_ZipCode_mtxtMain")).getAttribute("value");
	        } while(defaultZip.equalsIgnoreCase("00000-0000") == true);*/
			
		}
		Thread.sleep(500);		
		
		//boolean check = existsElement("td", "ContinueInsImageButtonMiddle", "Continue");
		//driver.findElement(By.xpath("//*[contains(text(),'Continue')]")).click();
		actions.sendKeys(Keys.PAGE_UP).perform();Thread.sleep(250);
		actions.moveToElement(driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_First"))).click().perform();	
		Thread.sleep(250);	actions.sendKeys(Keys.PAGE_UP).perform();Thread.sleep(250);
		actions.moveToElement(driver.findElement(By.id("ContinueInsImageButtonMiddle"))).click().perform();Thread.sleep(2500);
		
		report.updateTestLog("User and Address Details", "User and address details updated successfully", Status.DONE);
		report.updateTestLog("User and Address Details", "User and address details updated successfully", Status.SCREENSHOT);
		//Application Submission Request 
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_VersionEffectiveDateInsDateTime_VersionEffectiveDateInsDateTime")));
		EffDate = dataTable.getData("General_Data", "EffectiveDate");
		EffDate= EffDate.replaceAll("/", "");
		String defaultVal = driver.findElement(By.id("P_L_Application_0X0X0X0X00_VersionEffectiveDateInsDateTime_VersionEffectiveDateInsDateTime")).getAttribute("value");
		do {
			driver.findElement(By.id("P_L_Application_0X0X0X0X00_VersionEffectiveDateInsDateTime_VersionEffectiveDateInsDateTime")).click();
			driver.findElement(By.id("P_L_Application_0X0X0X0X00_VersionEffectiveDateInsDateTime_VersionEffectiveDateInsDateTime")).sendKeys(Keys.CONTROL, "a");
			Thread.sleep(500);
			driver.findElement(By.id("P_L_Application_0X0X0X0X00_VersionEffectiveDateInsDateTime_VersionEffectiveDateInsDateTime")).sendKeys(EffDate);
			Thread.sleep(1000);
			defaultVal = driver.findElement(By.id("P_L_Application_0X0X0X0X00_VersionEffectiveDateInsDateTime_VersionEffectiveDateInsDateTime")).getAttribute("value");
			defaultVal = defaultVal.replaceAll("/", "");
			Thread.sleep(500);
		} while (defaultVal.equalsIgnoreCase(EffDate) == false);
		Thread.sleep(3500);
		
		
		//Select Company
		String Company = dataTable.getData("Application_Submission", "Company",trans);
		String Agency = dataTable.getData("Application_Submission", "Agency",trans);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_CompanyInsCombo_D_I")));
		driver.findElement(By.id("P_L_Application_0X0X0X0X00_CompanyInsCombo_D_I")).click();
		Thread.sleep(1500);
		actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_CompanyInsCombo_D_I"))).click().perform();
		//actions.sendKeys(Company).perform();
		if(Company.equalsIgnoreCase("Grange Insurance Association"))
		{
		//actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(1500);
		}
		if(Company.equalsIgnoreCase("Granwest Property & Casualty"))
		{
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(1500);
		}
		//Select State
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_StateInsCombo_D_I")));
		driver.findElement(By.id("P_L_Application_0X0X0X0X00_StateInsCombo_D_I")).click();
		
		String StateVal = (State.substring((State.indexOf(" ")+1),State.length())).toString();
		Thread.sleep(500);
        String defStateVal;
        do{
        	actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_StateInsCombo_D_I"))).click().perform();
        	
        	actions.sendKeys(Keys.HOME).perform();
        	actions.sendKeys(StateVal).perform();
    		actions.build();
            actions.perform();
            actions.sendKeys(Keys.TAB).perform();
            Thread.sleep(1000);
            defStateVal = driver.findElement(By.id("P_L_Application_0X0X0X0X00_StateInsCombo_D_I")).getAttribute("value");
           // Thread.sleep(1000);
        } while(defStateVal.trim().equalsIgnoreCase(StateVal.trim()) == false);
		
		
		//Select Line of Business
        //Not sure what the element below is - GPE 1/19/16
        //driver.findElement(By.id("P_L_Application_0X0X0X0X000000000000000000000000_ApplicationUpdatePanel")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_LobInsCombo_D_I")));
		if(LoB.equals("DWELLINGFIRE")){
			actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_LobInsCombo_D_I"))).click().perform(); 
			actions.sendKeys("Dwelling Fire Personal").perform();
		}else if (LoB.equals("HOME")){
			actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_LobInsCombo_D_I"))).click().perform(); 
			actions.sendKeys("Home Personal").perform();
		}else if (LoB.equals("AUTO")){
			actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_LobInsCombo_D_I"))).click().perform(); 
			actions.sendKeys("Auto Personal").perform();
		}
		else if (LoB.equals("COMMERCIAL FARM")){
			//*[@id="P_L_Application_0X0X0X0X00_LobInsCombo_D_I"]
			actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_LobInsCombo_D_I"))).click().perform(); 
			actions.sendKeys("Commercial Farm").perform();
		}
		
		else if (LoB.equals("UMBRELLA")){
			actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_LobInsCombo_D_I"))).click().perform(); 
			actions.sendKeys("Umbrella Personal").perform();
		}
		
		actions.sendKeys(Keys.ENTER).perform();
		actions.sendKeys(Keys.TAB).perform();
		//Select Agency and Agency Code
		//String Agency = dataTable.getData("Application_Submission", "Agency");
		
		String AgencyDef;
        do{
        	//*[@id="P_L_Application_0X0X0X0X00_AgencyInsCombo_D_I"]
    		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_AgencyInsCombo_D_I")));
    		Thread.sleep(1500);
    		actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_AgencyInsCombo_D_I"))).click().build().perform(); //P_L_Application_0X0X0X0X000000000000000000000000_AgencyInsCombo_AgencyInsCombo_B-1Img
    		Thread.sleep(1000);
    		actions.sendKeys(Agency).build().perform();
    		//actions.sendKeys(Keys.DOWN).build().perform();
    		Thread.sleep(1000);
    		//actions.sendKeys(Keys.DOWN).build().perform();
    		//Thread.sleep(1000);
    		actions.sendKeys(Keys.TAB).build().perform();
    		Thread.sleep(3000);
    		AgencyDef = driver.findElement(By.id("P_L_Application_0X0X0X0X00_AgencyInsCombo_D_I")).getAttribute("value");
    		System.out.println(AgencyDef);
        } while(AgencyDef.equalsIgnoreCase(Agency) == false);
		
		//Select Producer
				wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_ProducerInsCombo_D_I")));
				//String Producer = dataTable.getData("Application_Submission", "Producer");
				actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_ProducerInsCombo_D_I"))).click().build().perform(); 
				Thread.sleep(2000);
				actions.sendKeys(Keys.DOWN).build().perform();
				Thread.sleep(1000);
				actions.sendKeys(Keys.DOWN).build().perform();
				Thread.sleep(1000);
				actions.sendKeys(Keys.ENTER).build().perform();
				Thread.sleep(1000);
				//actions.moveToElement(driver.findElement(By.id("P_L_Application_0X0X0X0X00_ProducerInsCombo_D_I"))).click().perform();
				//Thread.sleep(1000);
				
				//Not sure what the element below is - GPE 1/19/16
				//driver.findElement(By.id("P_L_Application_0X0X0X0X000000000000000000000000_ApplicationUpdatePanel")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_PolicyTermInsCombo_D_I")));
				String PolicyTerm = dataTable.getData("Application_Submission", "PolicyTerm",trans);
				
				do {
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'PolicyTermInsCombo_D_I')]")));
					actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'PolicyTermInsCombo_D_I')]"))).click().perform(); 
				actions.sendKeys(PolicyTerm).perform();
				actions.sendKeys(Keys.ENTER).build().perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(2000);
				defaultVal=driver.findElement(By.xpath("//*[contains(@id, 'PolicyTermInsCombo_D_I')]")).getAttribute("value");
				} while (defaultVal.equalsIgnoreCase(PolicyTerm) == false);
				
		
				/* Put expiration day code here */
				//if(PolicyTerm.equalsIgnoreCase("Short Term"))
					if(PolicyTerm.equalsIgnoreCase("Short Term") || PolicyTerm.equalsIgnoreCase("6 Month Short Term"))
				{
						/*String ExpDate = dataTable.getData("General_Data", "ExpirationDate");
						if(ExpDate.isEmpty()==false)
						{
						ExpDate= ExpDate.replaceAll("/", "");
						//*[@id="P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime"]
						String defaultVal1 = driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).getAttribute("value");
						do {
							driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).click();
							driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).sendKeys(Keys.CONTROL, "a");
							Thread.sleep(500);
							driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).sendKeys(ExpDate);
							Thread.sleep(1000);
							defaultVal1 = driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).getAttribute("value");
							defaultVal1 = defaultVal.replaceAll("/", "");
							Thread.sleep(500);
						} while (defaultVal1.equalsIgnoreCase(ExpDate) == false);
						Thread.sleep(3500);
						}*/
					wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")));
					String expDate="";String transEffDate="";		
					transEffDate=EffDate.toString();
					DateFormat df = new SimpleDateFormat("MMddyyyy"); 
					Date transDate = df.parse(transEffDate);
					Calendar now = Calendar.getInstance();
					now.setTime(transDate); 
					now.add(Calendar.DAY_OF_MONTH, 1);
					expDate = df.format((now.getTime()));
					
					defaultVal = driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).getAttribute("value");
					do {
						driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).click();
						driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).sendKeys(Keys.CONTROL, "a");
						Thread.sleep(500);
						driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).sendKeys(expDate);
						defaultVal = driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).getAttribute("value");
						defaultVal = defaultVal.replaceAll("/", "");
					} while (defaultVal.equalsIgnoreCase(expDate) == false);
					
					Date dt = new Date();
					Calendar c = Calendar.getInstance(); 
					c.setTime(dt); 
					c.add(Calendar.DATE, 1);
					dt = c.getTime();
					DateFormat ex = new SimpleDateFormat("MM/dd/yyyy"); 
					String tomDate = ex.format(dt);
					
					//dataTable.putPolicyData("General_Data", "RNR_VerifyDate", tomDate) ;
				}
				
				
		//Capture Expiration Date
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Application_0X0X0X0X00_PolicyTermInsCombo_D_I")));
		Thread.sleep(2000);
		//String ExpDate = driver.findElement(By.id("P_L_Application_0X0X0X0X00_ExpirationDateInsDateTime_ExpirationDateInsDateTime")).getAttribute("value");
		//dataTable.putData("General_Data", "ExpirationDate", ExpDate) ;
		//dataTable.putPolicyData("General_Data", "ExpirationDate", ExpDate) ;
		//Not sure what the element below is - GPE 1/19/16
		//driver.findElement(By.id("P_L_Application_0X0X0X0X000000000000000000000000_ApplicationUpdatePanel")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SubmitToolStripButtonMiddle']/a")));
		actions.moveToElement(driver.findElement(By.xpath("//*[@id='SubmitToolStripButtonMiddle']/a"))).click();
		actions.build().perform();
		//Click a second time for short term policies - GPE 9/20/16
		/*if(PolicyTerm.equalsIgnoreCase("Short Term"))
		{
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SubmitToolStripButtonMiddle']/a")));
			actions.moveToElement(driver.findElement(By.xpath("//*[@id='SubmitToolStripButtonMiddle']/a"))).click();
			actions.build().perform();		
		}*/
		Thread.sleep(15000);
		if(existsElement("*", "P_L_InsEmployeeView_DetailTreeViewn0Nodes", "")==false)
			Thread.sleep(6000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='P_L_V_DetailTreeViewn0Nodes']/table[2]/tbody/tr/td[3]/span/a[2]")));
		if (driver.findElements(By.xpath("//*[contains(@id, 'PolicyInfoHeaderLabel')]")).size() > 0){
			
			PolicyNumber = driver.findElement(By.xpath("//*[contains(@id, 'PolicyNumberInsLabel')]")).getText();
			
			report.updateTestLog("Application Submission", "Application submitted successfuly and Policy Number is "+PolicyNumber, Status.DONE);
			report.updateTestLog("Application Submission", "Application submitted successfuly and Policy Number is "+PolicyNumber, Status.SCREENSHOT);
			Transaction="New Business";
			//dataTable.putData("General_Data", "PolicyNumber_NewBusiness", PolicyNumber) ;
			//dataTable.putPolicyData("General_Data", "PolicyNumber_NewBusiness", PolicyNumber) ;
			NamedInsured = driver.findElement(By.xpath("//*[contains(@id, 'PolicyholderOneDisplayNameInsLabel')]")).getText();
			//dataTable.putData("General_Data", "NamedInsured", NamedInsured) ;
		}else{
			report.updateTestLog("Application Submission", "Application not submitted successfully", Status.FAIL);
			report.updateTestLog("Application Submission", "Application not submitted successfully", Status.SCREENSHOT);
		}
		
	return;
	}
	
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Applicant info		
	 *  Function Description	-	To enter applicant information in policy info page
	 *  Author					-	pmani
	 *  Script Created on		-	03/05/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void applicantInfo() throws Exception
	{
		//Click Applicants link
		
		//actions.moveToElement(driver.findElement(By.xpath("//*[@id='P_L_V_DetailTreeViewt3']/table[3]/tbody/tr/td[3]/span/a[2]"))).click().perform();
		//actions.moveToElement(driver.findElement(By.xpath("//*[@id='P_L_InsEmployeeView_DetailTreeViewn0Nodes']/table[3]/tbody/tr/td[3]/span/a[2]"))).click().perform();
		//actions.moveToElement(driver.findElement(By.xpath("//*[@id='P_L_V_DetailTreeViewt3']//a[contains(text(),'Applicants')]"))).click().perform();
		//driver.findElement(By.linkText("Applicants")).click();
		driver.findElement(By.partialLinkText("Applicants")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='AddApplicantToolStripButtonMiddle']/a")));
		actions.moveToElement(driver.findElement(By.xpath("//*[@id='AddApplicantToolStripButtonMiddle']/a"))).click().perform();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='SelectNameToolStripButtonMiddle']/a")));
		driver.findElement(By.xpath("//td[@id='SelectNameToolStripButtonMiddle']/a")).click();
		
		Thread.sleep(1000);
		Thread.sleep(2000);
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'P_L_V_v11w9_t13_c0w0_PC_t1i0_RelationshipTypeInsCombo_D')]")));
																 
		actions.moveToElement(driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a"))).click().perform();
		
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(2000);
		
		if (driver.findElements(By.xpath("//*[contains(@id, 'ApplicantInformationGridView')]/tbody/tr[2]/td[11]")).size() > 0){
			report.updateTestLog("Applicant Info", "Applicant Info entered successfuly", Status.DONE);
			report.updateTestLog("Applicant Info", "Applicant Info entered successfuly", Status.SCREENSHOT);
			Thread.sleep(2000);
		}else{
			report.updateTestLog("Applicant Info", "Applicant Info not entered successfully", Status.FAIL);
			report.updateTestLog("Applicant Info", "Applicant Info not entered successfully", Status.SCREENSHOT);
			Thread.sleep(2000);
		}
		return;
	}
	
	
	/*******************************************************************************************************************************
	 *  Function Automated	-	Rate Policy		
	 *  Function Description	-	Rate Policy
	 *  Author	-	pmani
	 *  Script Created on	-	03/10/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void policyRate() throws Exception
	{
		
		String validationMsg = null;
		//Save Location
		Thread.sleep(2500);
		if (driver.findElements(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).size()>0){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")));
			driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).click();
			Thread.sleep(3000);
		}
		
		
		
		//if(existsElement("td", "ContinueInsValidationButtonMiddle", ""))
		//{
			
			//driver.findElement(By.id("ContinueInsValidationButtonMiddle")).click();
		//}

		if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
		{
			driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			Thread.sleep(3000);
		}

		//If Validation messages alert box thrown
		/*if(driver.findElement(By.id("P_L_InsEmployeeView_ValidationPopUp_HeaderLabel")).isDisplayed()){
			report.updateTestLog("Property Information", "Property information incomplete", Status.FAIL);
			
			WebElement table_element = driver.findElement(By.id("P_L_InsEmployeeView_ValidationPopUp_ErrorInsDataGridView"));
	        List<WebElement> tr_collection=table_element.findElements(By.xpath("//table[@id='P_L_InsEmployeeView_ValidationPopUp_ErrorInsDataGridView']/tbody/tr"));

	        System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	        int row_num,col_num;
	        row_num=1;
	        for(WebElement trElement : tr_collection)
	        {
	            List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
	            System.out.println("NUMBER OF COLUMNS = "+td_collection.size());
	            col_num=1;
	            for(WebElement tdElement : td_collection)
	            {
	                System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
	                validationMsg = tdElement.getText();
	                report.updateTestLog("Save Location", "Save Location Failed. Reason: "+validationMsg, Status.FAIL);
	                col_num++;
	            }
	            row_num++;
	        }
		}*/
		//click RAte Button
		
			actions.moveToElement(driver.findElement(By.id("P_L_V_RateToolStripButton"))).click().perform();	
			Thread.sleep(15000);
		
	
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD']//a")));
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD")));
		if(driver.findElements(By.xpath("//td[contains(text(), 'Property coverage is required with Farm Combination Coverage')]")).size()>0)
		{
			if(driver.findElements(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD")).size()>0)
			{
				Thread.sleep(1000);
				actions.moveToElement(driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD"))).click().perform();
				Thread.sleep(1000);
			}
			driver.findElement(By.linkText("Policy Level Coverage Info")).click();
			Thread.sleep(2000);
			actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, '_t0_c0w0_t0_modifier_id_60_12_1_60_S')]"))).click().build().perform();
			Thread.sleep(1500);
			driver.findElement(By.linkText("Policy Info")).click();
			Thread.sleep(2000);
			actions.moveToElement(driver.findElement(By.id("P_L_V_RateToolStripButton"))).click().perform();	
			Thread.sleep(15000);
		}
		
		if(driver.findElements(By.xpath("//td[contains(text(), 'Rating Successful')]")).size()>0)
		{
			if(driver.findElements(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD")).size()>0)
			{
				Thread.sleep(1000);
				actions.moveToElement(driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD"))).click().perform();
				Thread.sleep(1000);
			}
			else if(driver.findElements(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).size()>0)
				actions.moveToElement(driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD"))).click().perform();
			report.updateTestLog("Rate Policy", "Policy Rating Success", Status.DONE);
			report.updateTestLog("Rate Policy", "Policy Rating Success", Status.SCREENSHOT);
		}
		else
			report.updateTestLog("Rate Policy", "Policy Rating Failed", Status.FAIL);
		report.updateTestLog("Rate Policy", "Policy Rating Failed", Status.SCREENSHOT);
	return;
	}
	

	/*******************************************************************************************************************************
	 *  Function Automated		-	Issue Policy		
	 *  Function Description	-	Issue Policy and Billing Information update
	 *  Author					-	pmani
	 *  Script Created on		-	03/11/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void saveLocation() throws Exception
	{
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(2000);
		
	}
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Issue Policy		
	 *  Function Description	-	Issue Policy and Billing Information update
	 *  Author					-	pmani
	 *  Script Created on		-	03/11/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void policyIssue() throws Exception
	{
		String validationMsg = null;
		//Save Location
		Thread.sleep(2500);
		if (driver.findElements(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).size()>0){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")));
			driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).click();
			Thread.sleep(3000);
		}
		
		if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
		{
			driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			Thread.sleep(3000);
		}
		//Billing Information
		if(billFlag != 1 || Transaction.equalsIgnoreCase("New Business")){
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='P_L_V_DetailTreeViewn0Nodes']//a[contains(text(),'Billing Info')]")));
		Thread.sleep(500);
		//driver.findElement(By.xpath("//*[@id='P_L_V_DetailTreeViewn0Nodes']//a[contains(text(),'Billing Information')]")).click();
		driver.findElement(By.partialLinkText("Billing Info")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")));
		String flag1 = driver.findElement(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]")).getAttribute("value");
		if (flag1.isEmpty())
		{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")));
		
		//String Method = dataTable.getData("Billing_Information", "Method");
		String PayPlan = dataTable.getData("Billing_Information", "PayPlan","Trans1");
		String BillTo = dataTable.getData("Billing_Information", "BillTo","Trans1");
		String defplan="";
		 defplan = driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")).getAttribute("value");
		do
		{
			actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]"))).click().build().perform();
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(2500);
			actions.sendKeys(Keys.ENTER).build().perform();
			defplan = driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")).getAttribute("value");
			Thread.sleep(2500);
		}while (defplan.equalsIgnoreCase(PayPlan) == false);
		
		//created on 05/02/2019 by mvijayakumar
		String RoutingNo = dataTable.getData("Billing_Information", "RoutingNo","Trans1");
		String AccNo = dataTable.getData("Billing_Information", "AccNo","Trans1");
		String AccType = dataTable.getData("Billing_Information", "AccType","Trans1");
		String DedDay = dataTable.getData("Billing_Information", "DedDay","Trans1");
		

		if(PayPlan.equalsIgnoreCase("EFT")==true)
		{
			driver.findElement(By.xpath("//*[contains(@id,'_CreditCardButton')]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[contains(@id, '_EftInfoControl_BankRoutingNumberInsTextBox_I')]")).click();
			actions.sendKeys(RoutingNo).build().perform();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[contains(@id, '_EftInfoControl_BankAccountNumberInsTextBox_I')]")).click();
			actions.sendKeys(AccNo).build().perform();
			Thread.sleep(1000);
			//*[@id="P_L_V_v22w22_t15_EftInfoControl_BankAccountTypeCombo_D_I"]
			driver.findElement(By.xpath("//*[contains(@id, '_EftInfoControl_BankAccountTypeCombo_D_I')]")).click();
			actions.sendKeys(AccType).build().perform();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id='SaveEFTToolStripButtonMiddle']/a")).click();
			Thread.sleep(1000);
		
			/*if(DedDay.equalsIgnoreCase("")==false)
			{
				//*[@id="P_L_V_v22w22_t15_EftInfoControl_DeductionDayDxNumeric_DeductionDayDxNumeric_I"]
			driver.findElement(By.xpath("//*[contains(@id, '_EftInfoControl_DeductionDayDxNumeric_DeductionDayDxNumeric_I')]")).click();
			actions.sendKeys(DedDay).build().perform();
			Thread.sleep(1000);
			}*/
		}
		
		
		
		
		
		
		
		
		/*do {
			//*[@id="P_L_V_v30w22_t15_PayPlanInsCombo_D_I"]
		driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")).click();
		//actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]"))).click().perform();
		//actions.sendKeys(PayPlan).build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		Thread.sleep(500);
		actions.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(500);
		defplan=driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")).getAttribute("value");
		Thread.sleep(500);
		}while(defplan.equalsIgnoreCase(PayPlan)==false);*/
		//actions.sendKeys(Keys.TAB).build().perform();Thread.sleep(1000);
		//driver.findElement(By.xpath("//*[contains(@id, 'BillingInformationUpdatePanel')]")).click();
		
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]")).clear();
		Thread.sleep(1000);
		actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]"))).click().perform();
		actions.sendKeys(BillTo).build().perform();
		Thread.sleep(500);
		actions.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(500);
		actions.sendKeys(Keys.TAB).build().perform();
		//driver.findElement(By.xpath("//*[contains(@id, 'BillingInformationUpdatePanel')]")).click();
		Thread.sleep(3000);
		if(BillTo.equalsIgnoreCase("Other")==true)
		{
			//*[@id="P_L_V_v25w22_t15_BillToControl_BillingAddressButton"]
			driver.findElement(By.xpath("//*[contains(@id,'_BillToControl_BillingAddressButton')]")).click();
			Thread.sleep(1000);
			String BillFN = dataTable.getData("Billing_Information", "BillFN","Trans1");
			String BillMN = dataTable.getData("Billing_Information", "BillMN","Trans1");
			String BillLN = dataTable.getData("Billing_Information", "BillLN","Trans1");
			String BillStreetNo = dataTable.getData("Billing_Information", "BillStreetNo","Trans1");
			String BillStreet = dataTable.getData("Billing_Information", "BillStreet","Trans1");
			String BillCity = dataTable.getData("Billing_Information", "BillCity","Trans1");
			String BillState = dataTable.getData("Billing_Information", "BillState","Trans1");
			String BillZip = dataTable.getData("Billing_Information", "BillZip","Trans1");
			
			
			if(BillFN.isEmpty()==false)
			{
				//*[@id="P_L_V_v25w22_t15_BillToControl_BillingAddressButton"]
				driver.findElement(By.xpath("//input[contains(@id,'_BillToControl_BillingName_First')]")).click();
				actions.sendKeys(BillFN).build().perform();
				Thread.sleep(1000);
				
			}
			
			if(BillLN.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'BillToControl_BillingName_Last')]")).click();
				actions.sendKeys(BillLN).build().perform();
				Thread.sleep(1000);
				
			}
			
			if(BillStreetNo.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'BillToControl_BillingName_HouseNumber')]")).click();
				actions.sendKeys(BillStreetNo).build().perform();
				Thread.sleep(1000);
				
			}
			
			if(BillStreet.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'BillToControl_BillingName_StreetName')]")).click();
				actions.sendKeys(BillStreet).build().perform();
				Thread.sleep(1000);
				
			}
			if(BillCity.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'BillToControl_BillingName_City')]")).click();
				actions.sendKeys(BillCity).build().perform();
				Thread.sleep(1000);
				
			}
			if(BillState.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_BillToControl_BillingName_AddressState_D_I')]")).click();
				actions.sendKeys(BillState).build().perform();
				Thread.sleep(1000);
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1000);
				
			}
			if(BillZip.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'BillToControl_BillingName_ZipCode_mtxtMain')]")).click();
				driver.findElement(By.xpath("//input[contains(@id,'BillToControl_BillingName_ZipCode_mtxtMain')]")).clear();
				Thread.sleep(1000);
				actions.sendKeys(BillZip).build().perform();
				Thread.sleep(1000);
				
			}
		}
		
		
		
		
		}
		billFlag = 1;
		}
		//Issue
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_IssueToolStripButton")));
		Thread.sleep(1000);
		driver.findElement(By.id("P_L_V_IssueToolStripButton")).click();
		Thread.sleep(1000);
		if(existsElement("", "P_L_V_EmployeeViewASPxPopupControl_UpdateClient_IssueButton_CD", "")==true)
			driver.findElement(By.id("P_L_V_EmployeeViewASPxPopupControl_UpdateClient_IssueButton_CD")).click();
		Thread.sleep(50000);
		
		if(existsElement("", "P_L_V_ValidationPopUp_MyASPxPopupControl_MainTD", "")==false)
			Thread.sleep(8000);
		  wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_MainTD")));
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_InsEmployeeView_ValidationPopUp_OtherInsDataGridView")));
				
		if(driver.findElements(By.xpath("//td[text()='Policy was successfully issued.']")).size()>0)
		{
			report.updateTestLog("Issue", "Policy Issued Successfully", Status.PASS);
			report.updateTestLog("Issue", "Policy Issued Successfully", Status.SCREENSHOT);
			if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
				driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			else 
				driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD")).click();
				
			Thread.sleep(5000);
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TaskListControl_UpdatePanel")));
		//This changed with the 531.006 build - GPE 7/5/16
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, '_t0_UpdatePanel')]")));
		
		}
		else if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
		{
			report.updateTestLog("Issue", "Policy Issued Successfully", Status.PASS);
			report.updateTestLog("Issue", "Policy Issued Successfully", Status.SCREENSHOT);
			driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			Thread.sleep(3000);
			//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TaskListControl_UpdatePanel")));
			//This changed with the 531.006 build - GPE 7/5/16
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, '_t0_UpdatePanel')]")));
		}
		else
		{
		WebElement table_element = driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ErrorInsDataGridView"));
	    List<WebElement> tr_collection=table_element.findElements(By.xpath("//table[@id='P_L_V_ValidationPopUp_MyASPxPopupControl_ErrorInsDataGridView']/tbody/tr"));
		
		int row_num,col_num;
		row_num=1;
		for(WebElement trElement : tr_collection)
		{
			List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
			//System.out.println("NUMBER OF COLUMNS = "+td_collection.size());
			col_num=1;
			for(WebElement tdElement : td_collection)
			{
				System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
				validationMsg = tdElement.getText();
				report.updateTestLog("Issue", "Issue Failed. Reason: "+validationMsg, Status.FAIL);
				report.updateTestLog("Issue", "Issue Failed. Reason: "+validationMsg, Status.SCREENSHOT);
				col_num++;
			}
			row_num++;
		}
		
		}
	return;
	}
	
	
	/*******************************************************************************************************************************
	 *  Function Automated		-		Search Policy		
	 *  Function Description	-		Search Policy
	 *  Author					-		pmani
	 *  Script Created on		-		03/26/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	
	public void searchPolicy() throws Exception{
			String defVal="";
			wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickPolicyLookupControl_FireLookupImageButton")));
			//Click Search Button
			driver.findElement(By.id("QuickPolicyLookupControl_FireLookupImageButton")).click();
			
			wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")));
			Thread.sleep(4000);
			
		
			String PN="";
			if(PolicyNumber == null)
			{
				driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_OkButton_CD")).click();
				Thread.sleep(4000);
			}
			else
			{
			do
			{
				actions.moveToElement(driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I"))).click().build().perform();
				Thread.sleep(250);
				driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")).clear();
				actions.sendKeys(Keys.HOME,Keys.SHIFT,Keys.END).build().perform();
				Thread.sleep(250);
				//5233048529 | Jake Kelly
				PN=PolicyNumber;
				PN=""+PolicyNumber+ " | " +NamedInsured+"";
				System.out.println(PN);
				actions.sendKeys(PN).build().perform();
				Thread.sleep(250);
				actions.sendKeys(Keys.TAB).build().perform();
				defVal=driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")).getAttribute("value");
			
			}while(defVal.equalsIgnoreCase(PN)==false);
			
			
			
			//Thread.sleep(4000);
			driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_OkButton_CD")).click();
			Thread.sleep(4000);
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickLinksExpandCollapseImage")));
			
			//PolicyNumber = dataTable.getData("General_Data", "PolicyNumber_NewBusiness");
			if (driver.findElements(By.id("QuickLinksExpandCollapseImage")).size() > 0){
				report.updateTestLog("Policy Search", "Policy Search Success", Status.DONE);
				report.updateTestLog("Policy Search", "Policy Search Success", Status.SCREENSHOT);
			}else{
				report.updateTestLog("Policy Search", "Policy Search Failed", Status.FAIL);
				report.updateTestLog("Policy Search", "Policy Search Failed", Status.SCREENSHOT);
			}
	}
		
	/*******************************************************************************************************************************
	 *  Function Automated		-		Quick Search Policy		
	 *  Function Description	-		Search only by New Business Policy Number
	 *  Author					-		gencke
	 *  Script Created on		-		03/07/2017
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	
	public void quickSearchPolicy() throws Exception{
			String defVal="";
			wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickPolicyLookupControl_FireLookupImageButton")));
			//Click Search Button
			driver.findElement(By.id("QuickPolicyLookupControl_FireLookupImageButton")).click();
			
			wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")));
			Thread.sleep(4000);
			String PolicyNumber = dataTable.getData("General_Data", "PolicyNumber_NewBusiness");
			
			String PN="";
			do
			{
				actions.moveToElement(driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I"))).click().build().perform();
				Thread.sleep(250);
				driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")).clear();
				actions.sendKeys(Keys.HOME,Keys.SHIFT,Keys.END).build().perform();
				Thread.sleep(250);
				//5233048529 | Jake Kelly
				//PN=""+PolicyNumber+" | Jake Kelly";
				PN=""+PolicyNumber+ " | " +NamedInsured+"";
				System.out.println(PN);
				actions.sendKeys(PN).build().perform();
				Thread.sleep(250);
				actions.sendKeys(Keys.TAB).build().perform();
				defVal=driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")).getAttribute("value");
			//}while(defVal.equalsIgnoreCase(PolicyNumber)==false);
			}while(defVal.equalsIgnoreCase(PN)==false);
			
			
			
			//Thread.sleep(4000);
			//Below is what is in apptest03 - gpe 3/27/17
			driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_OkButton_CD")).click();
			//Below is what is in apptest02 and production - gpe 3/27/17
			//driver.findElement(By.id("OkInsImageButtonMiddle")).click();
			Thread.sleep(4000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickLinksExpandCollapseImage")));
			
			//PolicyNumber = dataTable.getData("General_Data", "PolicyNumber_NewBusiness");
			if (driver.findElements(By.id("QuickLinksExpandCollapseImage")).size() > 0){
				report.updateTestLog("Policy Search", "Policy Search Success", Status.DONE);
			}else{
				report.updateTestLog("Policy Search", "Policy Search Failed", Status.FAIL);
			}
		}
		
	
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Bind Policy		
	 *  Function Description	-	Bind Policy and Billing Information update
	 *  Author					-	jkumar
	 *  Script Created on		-	05/07/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void policyBind() throws Exception
	{
		String validationMsg = null;
		//Save Location
		Thread.sleep(2500);
		if (driver.findElements(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).size()>0){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")));
			driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).click();
			Thread.sleep(3000);
		}

		
		//Billing Information
//		if(billFlag != 1 ){
			/*wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='P_L_InsEmployeeView_DetailTreeViewn0Nodes']//a[contains(text(),'Billing Information')]")));
			actions.moveToElement(driver.findElement(By.xpath("//*[@id='P_L_InsEmployeeView_DetailTreeViewn0Nodes']//a[contains(text(),'Billing Information')]"))).click().perform();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")));
			String flag1 = driver.findElement(By.xpath("//*[contains(@id, 'BillToInsComboBox_BillToInsComboBox_I')]")).getAttribute("value");
			if (flag1.isEmpty())
			{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'PayPlanInsCombo_PayPlanInsCombo_I')]")));
			
			//String Method = dataTable.getData("Billing_Information", "Method");
			String PayPlan = dataTable.getData("Billing_Information", "PayPlan","Trans1");
			String BillTo = dataTable.getData("Billing_Information", "BillTo","Trans1");
			
			driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_PayPlanInsCombo_I')]")).clear();
			actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_PayPlanInsCombo_I')]"))).click().perform();
			actions.sendKeys(PayPlan).build().perform();
			actions.sendKeys(Keys.ENTER).build().perform();
			actions.sendKeys(Keys.TAB).build().perform();Thread.sleep(1000);
			//driver.findElement(By.xpath("//*[contains(@id, 'BillingInformationUpdatePanel')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'BillToControl_BillToInsComboBox_BillToInsComboBox_I')]")));
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[contains(@id, 'BillToControl_BillToInsComboBox_BillToInsComboBox_I')]")).clear();
			Thread.sleep(1000);
			actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'BillToControl_BillToInsComboBox_BillToInsComboBox_I')]"))).click().perform();
			actions.sendKeys(BillTo).build().perform();
			actions.sendKeys(Keys.ENTER).build().perform();
			actions.sendKeys(Keys.TAB).build().perform();
			//driver.findElement(By.xpath("//*[contains(@id, 'BillingInformationUpdatePanel')]")).click();
			Thread.sleep(3000);
			}
			billFlag = 1;
		//	}*/
		//Bind
		
		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Billing Info")));
		Thread.sleep(500);
		//driver.findElement(By.xpath("//*[@id='P_L_V_DetailTreeViewn0Nodes']//a[contains(text(),'Billing Information')]")).click()
		driver.findElement(By.partialLinkText("Billing Info")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")));
		String flag1 = driver.findElement(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]")).getAttribute("value");
		if (flag1.isEmpty())
		{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")));
		
		//String Method = dataTable.getData("Billing_Information", "Method");
		String PayPlan = dataTable.getData("Billing_Information", "PayPlan","Trans1");
		String BillTo = dataTable.getData("Billing_Information", "BillTo","Trans1");
		String defplan="";
		defplan = driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")).getAttribute("value");
		do
		{
			actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]"))).click().build().perform();
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(2500);
			actions.sendKeys(Keys.ENTER).build().perform();
			defplan = driver.findElement(By.xpath("//*[contains(@id, 'PayPlanInsCombo_D_I')]")).getAttribute("value");
			Thread.sleep(2500);
		}while (defplan.equalsIgnoreCase(PayPlan) == false);
		//actions.sendKeys(Keys.TAB).build().perform();Thread.sleep(1000);
		//driver.findElement(By.xpath("//*[contains(@id, 'BillingInformationUpdatePanel')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]")).clear();
		Thread.sleep(1000);
		actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'BillToInsComboBox_D_I')]"))).click().perform();
		actions.sendKeys(BillTo).build().perform();
		Thread.sleep(500);
		actions.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(500);
		actions.sendKeys(Keys.TAB).build().perform();
		//driver.findElement(By.xpath("//*[contains(@id, 'BillingInformationUpdatePanel')]")).click();
		Thread.sleep(5000);
		}
		//billFlag = 1;
		//*[@id="P_L_V_BindToolStripButton"]
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_BindToolStripButton")));
		actions.moveToElement(driver.findElement(By.id("P_L_V_BindToolStripButton"))).click().perform();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Bind_ReasonInsCombo_D_I")));
		if (driver.findElements(By.id("P_L_Bind_ReasonInsCombo_D_I")).size() >0  ){
		//String bindDate = dataTable.getData("HO_Section_I", "Description");
		
		 //BindDate = driver.findElement(By.id("P_L_Bind_TransactionEffectiveDate_TransactionEffectiveDate")).getAttribute("value");
			driver.findElement(By.cssSelector("input[name*='TransactionEffectiveDate']")).sendKeys(Keys.CONTROL,"a");	
		BindDate = driver.findElement(By.cssSelector("input[name*='TransactionEffectiveDate']")).getAttribute("value");
		// dataTable.putData("General_Data", "BindDate", BindDate);
		 BindDate= BindDate.replaceAll("/", "");
		actions.moveToElement(driver.findElement(By.id("P_L_Bind_ReasonInsCombo_D_I"))).click().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.ENTER).build().perform();
		//actions.sendKeys("Underwriting Reasons").build().perform();
		Thread.sleep(500);
		actions.sendKeys(Keys.TAB).build().perform();
		//actions.moveToElement(driver.findElement(By.id("P_L_Bind_RemarkRichTextBox"))).click().perform();
		//actions.sendKeys("Bind").build().perform();
		Thread.sleep(500);
		actions.moveToElement(driver.findElement(By.id("SaveBindToolstripButtonMiddle"))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Bind_BindPolicyInsMessageBox_MyASPxPopupControl_YesInsMessageBoxButton_CD"))).click();
		//driver.findElement(By.id("YesInsMessageBoxButtonMiddle")).click();
		Thread.sleep(7500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_CloseToolStripButton")));
		}
					
	}
	/*******************************************************************************************************************************
	 *  Function Automated		-	Deny Policy		
	 *  Function Description	-	Deny Policy
	 *  Author					-	jkumar
	 *  Script Created on		-	05/07/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void policyDeny() throws Exception
	{
	//	String validationMsg = null;
		/*
		//Billing Information
		if(billFlag != 1){
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='P_L_InsEmployeeView_DetailTreeViewn0Nodes']/table[6]/tbody/tr/td[3]/span/a[2]")));
		actions.moveToElement(driver.findElement(By.xpath("//*[@id='P_L_InsEmployeeView_DetailTreeViewn0Nodes']/table[6]/tbody/tr/td[3]/span/a[2]"))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_PayPlanInsCombo_PayPlanInsCombo_I")));
																	P_L_InsEmployeeView_InsWorkflowwv13ww10default_BillingInformationControlwv13ww10wt16_MethodComboBox_MethodComboBox_I
		//String Method = dataTable.getData("Billing_Information", "Method");
		String PayPlan = dataTable.getData("Billing_Information", "PayPlan");
		String BillTo = dataTable.getData("Billing_Information", "BillTo");
		
		
		
		driver.findElement(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_PayPlanInsCombo_PayPlanInsCombo_I")).clear();
		actions.moveToElement(driver.findElement(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_PayPlanInsCombo_PayPlanInsCombo_I"))).click().perform();
		actions.sendKeys(PayPlan).build().perform();
		driver.findElement(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_BillingInformationUpdatePanel")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_BillToControl_BillToInsComboBox_BillToInsComboBox_I")));
		
		
		driver.findElement(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_BillToControl_BillToInsComboBox_BillToInsComboBox_I")).clear();
		actions.moveToElement(driver.findElement(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_BillToControl_BillToInsComboBox_BillToInsComboBox_I"))).click().perform();
		actions.sendKeys(BillTo).build().perform();
		driver.findElement(By.id("P_L_InsEmployeeView_InsWorkflowwv17ww10default_BillingInformationControlwv17ww10wt15_BillingInformationUpdatePanel")).click();
		Thread.sleep(500);
		billFlag = 1;
		}
		
		*/
		//Deny
		String validationMsg = null;
		//Save Location
		Thread.sleep(2500);
		if (driver.findElements(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).size()>0){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")));
			driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).click();
			Thread.sleep(3000);
		}
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_DenyToolStripButton"))).click();
		//actions.moveToElement(driver.findElement(By.id("P_L_InsEmployeeView_BindToolStripButton"))).click().perform();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Deny_ReasonInsCombo_D_I")));
		
	//	if (driver.findElements(By.id("P_L_Deny_ReasonInsCombo_ReasonInsCombo_I")).size() >0  ){
		//String Reason = dataTable.getData("Deny", "Reason");
		//String Remarks = dataTable.getData("Deny", "Remarks");
		String Reason = "Company Error";
		String Remarks = "Policy Denied";
		
		String defreason="";
		do
		{
		actions.moveToElement(driver.findElement(By.id("P_L_Deny_ReasonInsCombo_D_I"))).click().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		Thread.sleep(2000);
		actions.sendKeys(Keys.TAB).build().perform();
		defreason=driver.findElement(By.id("P_L_Deny_ReasonInsCombo_D_I")).getAttribute("value");
		Thread.sleep(500);
		}while(defreason.equalsIgnoreCase(Reason)==false);
		
		actions.moveToElement(driver.findElement(By.id("P_L_Deny_RemarkRichTextBox"))).click().perform();
		actions.sendKeys(Remarks).build().perform();
		
		actions.moveToElement(driver.findElement(By.id("SaveDenyToolstripButtonMiddle"))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_Deny_DenyPolicyInsMessageBox_MyASPxPopupControl_YesInsMessageBoxButton_CD"))).click();
		//driver.findElement(By.id("YesInsMessageBoxButtonMiddle"))).click();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_CloseToolStripButton")));
		//}
				
	}
	
	/*******************************************************************************************************************************
	 *  Function Automated		-		Policy Transaction	
	 *  Function Description	-		Perform Policy Transaction
	 *  Author					-		jkumar
	 *  Script Created on		-		05/08/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
public void policyTransaction(String trans) throws Exception{ 
		
		Transaction = dataTable.getData("Policy_Transaction", "Transaction",trans);
		String Days = dataTable.getData("Policy_Transaction", "Days",trans);
		String Reason = dataTable.getData("Policy_Transaction", "Reason",trans);
		Thread.sleep(2000);
		
		if(existsElement(null,"", "Transaction Links")==false)
		{
			searchPolicy();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(),'Transaction Links')]")));
		}
		
		/*if(driver.findElements(By.xpath("//td[contains(text(),'Transaction Links')]")).size()==0)
		{
			searchPolicy();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(),'Transaction Links')]")));
		}*/
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(),'Transaction Links')]"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='P_L_V_TransactionLinksPanel']/div/a[text()='"+Transaction.trim() +"']"))).click();
		String strTransDate="";String transEffDate="";
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")));
		if(Days.isEmpty() || Days.equalsIgnoreCase("")==false){
			 try 
			    {  
				 if(Reason.equalsIgnoreCase("Reinstatement of NB Decline")==false){
				  EffDate = dataTable.getData("General_Data", "EffectiveDate");
				  EffDate= EffDate.replaceAll("/", "");
			       transEffDate=EffDate.toString();
			      DateFormat df = new SimpleDateFormat("MMddyyyy"); 
			      Date transDate = df.parse(transEffDate);
			      System.out.println("Date, with the default formatting: " + transDate);	      
			      Calendar now = Calendar.getInstance();
			      now.setTime(transDate); 
			      now.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Days));
			      System.out.println(now.getTime());
			       strTransDate = df.format((now.getTime()));
			      System.out.println("Date in format MM/dd/yyyy: " + strTransDate);
			      
				 }
				 else
				 {
					 strTransDate=BindDate;
					/* driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")).click();
				 driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")).sendKeys(Keys.CONTROL,"a");
				//actions.sendKeys(BindDate).perform(); 
				strTransDate=BindDate;*/
			    } }
			    catch(Exception exception){report.updateTestLog("Policy Transaction", "Policy Transaction Failed. Exception"+exception, Status.FAIL);}
			      
			      String defaultVal = driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")).getAttribute("value");
				if(Transaction.equalsIgnoreCase("Renewal")==false){
					do {						
						driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate_I")).click();
						driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate_I")).sendKeys(Keys.CONTROL, "a");
						Thread.sleep(500);
						//driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")).sendKeys(strTransDate);
						actions.sendKeys(strTransDate).build().perform();
						Thread.sleep(200);
						defaultVal = driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate_I")).getAttribute("value");
						defaultVal = defaultVal.replaceAll("/", "");
						//actions.sendKeys(Keys.TAB).build().perform();
						Thread.sleep(200);
					} while (defaultVal.equalsIgnoreCase(strTransDate) == false);
					Thread.sleep(200);
					}
			    
		 }
		 String TransactionSource = dataTable.getData("Policy_Transaction", "TransactionSource",trans);
		// String Reason = dataTable.getData("Policy_Transaction", "Reason",trans);
		 String defVal="";
		 do
		 {
			 //driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")).click();
			 //wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate"))).click();
			 //actions.sendKeys(TransactionSource).build().perform();
			 actions.moveToElement(driver.findElement(By.id("P_L_TransactionControl_TransactionSourceInsCombo_D_I"))).click().perform();
			 Thread.sleep(200);
			 actions.sendKeys(TransactionSource).build().perform();
			 Thread.sleep(200);
			 actions.sendKeys(Keys.ENTER).build().perform();Thread.sleep(250);
			 //actions.sendKeys(Reason).build().perform();
			 Thread.sleep(200);
			 actions.sendKeys(Keys.TAB).build().perform();
			 defVal=driver.findElement(By.id("P_L_TransactionControl_TransactionSourceInsCombo_D_I")).getAttribute("value");
			 Thread.sleep(200);
		 }while(defVal.equalsIgnoreCase(TransactionSource)==false);
		 Thread.sleep(200);
		 
		 do
		 {
			 //driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")).click();
		//	 wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate"))).click();
			 //actions.sendKeys(TransactionSource).build().perform();
			driver.findElement(By.id("P_L_TransactionControl_TransactionReasonInsCombo_D_I")).click();
			// actions.sendKeys(Keys.CONTROL+"a").perform();Thread.sleep(250);
			 //actions.sendKeys(Keys.DELETE).perform();
			 Thread.sleep(500);
			 actions.moveToElement(driver.findElement(By.id("P_L_TransactionControl_TransactionReasonInsCombo_D_I"))).click().perform();
			 actions.sendKeys(Keys.CONTROL+"a");Thread.sleep(250);
			 actions.sendKeys(Keys.CONTROL+"a").perform();Thread.sleep(250);
			 driver.findElement(By.id("P_L_TransactionControl_TransactionReasonInsCombo_D_I")).clear();
			 actions.sendKeys(Reason).build().perform();
			 Thread.sleep(200);
			 actions.sendKeys(Keys.ENTER).build().perform();Thread.sleep(250);
			 actions.sendKeys(Keys.TAB).build().perform();
			 defVal=driver.findElement(By.id("P_L_TransactionControl_TransactionReasonInsCombo_D_I")).getAttribute("value");
			 Thread.sleep(200);
			// actions.moveToElement(driver.findElement(By.id("P_L_TransactionControl_TransactionSourceInsCombo_TransactionSourceInsCombo_I"))).click().perform();
			// actions.sendKeys(Keys.TAB).build().perform();
		 }while(defVal.equalsIgnoreCase(Reason)==false);
		 
		 Thread.sleep(200);
		 
		 String Remarks = dataTable.getData("Policy_Transaction", "Remarks",trans);
		 //wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate"))).click();
		 driver.findElement(By.id("P_L_TransactionControl_RemarksInsTextBox")).clear();
		 Thread.sleep(500);
		 //driver.findElement(By.id("P_L_TransactionControl_TransactionEffectiveInsDate_TransactionEffectiveInsDate")).click();
		 actions.moveToElement(driver.findElement(By.id("P_L_TransactionControl_RemarksInsTextBox"))).click();
		 actions.sendKeys(Remarks).build().perform();
		 Thread.sleep(200);
		 driver.findElement(By.xpath("//td[@id='SubmitToolStripButtonMiddle']/a")).click();
		 Thread.sleep(20000);
		 //try{
		 
		 //For OOS
			if(Remarks.toUpperCase().contains("OOS")==true){
				 //wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[@id='P_L_TransactionControl_ValidationMessageBox_MyASPxPopupControl_YesInsMessageBoxButton_CD']/a")))).click(); 
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("P_L_TransactionControl_ValidationMessageBox_MyASPxPopupControl_YesInsMessageBoxButton_CD")))).click();
			 }
			 
			 else if(Transaction.equalsIgnoreCase("Cancellation")==true || Transaction.equalsIgnoreCase("Reinstatement")==true){
				 Thread.sleep(6000);	 
				  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'OKInsMessageBoxButton_CD')]")));
				  driver.findElement(By.xpath("//*[contains(@id,'OKInsMessageBoxButton_CD')]")).click();
			 Thread.sleep(6000);
			 wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickPolicyLookupControl_FireLookupImageButton")));
			 searchPolicy();
			 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//td[contains(text(),'Quick Links')]"))));
			 }
			 else if(Transaction.equalsIgnoreCase("Non-Renewal")==true){
				  
				 Thread.sleep(4000);
				 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(),'Transaction Links')]")));
				 
				 }
			  else{
				  Thread.sleep(4000);
				  
				  if(Transaction.equalsIgnoreCase("Cancel-Rewrite")==true ){
					  if(existsElement("td", "OKInsMessageBoxButton_CD", ""))
						{
							
						  driver.findElement(By.xpath("//*[contains(@id,'OKInsMessageBoxButton_CD')]")).click();
						  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
							 Thread.sleep(5000);
						}
					
				  }
				  Thread.sleep(2000);
			 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("P_L_V_RateToolStripButton"))));
			 //If RewriteFull or Cancel Rewrite
			// if(driver.findElement(By.id("P_L_InsEmployeeView_RateToolStripButton")).isEnabled()){
			
			 
			 
			
			 
				 if(Transaction.equalsIgnoreCase("Rewrite Full") || Transaction.equalsIgnoreCase("Cancel-Rewrite")){
					 String rewrittenPolicy = driver.findElement(By.id("P_L_V_DetailTreeViewt0")).getText();
						System.out.println(rewrittenPolicy);
						//Taking out line below, dwelling fire is 13 characters
						//rewrittenPolicy=rewrittenPolicy.substring(0,10);
						System.out.println(rewrittenPolicy); 
					if(Transaction.equalsIgnoreCase("Rewrite Full")){
						//dataTable.putData("General_Data", "PolicyNumber_RewriteFull", rewrittenPolicy) ;
					}else if(Transaction.equalsIgnoreCase("Cancel-Rewrite")){
						//dataTable.putData("General_Data", "PolicyNumber_CancelRewrite", rewrittenPolicy) ;
					}
				 }
				 
				 report.updateTestLog("Policy Transaction", "Policy Transaction '"+Transaction+"' Success", Status.DONE);
			// }else{
				 //report.updateTestLog("Policy Transaction", "Policy Transaction Failed", Status.FAIL);
			 //}
			 }
		 //}catch(Exception exception){report.updateTestLog("Policy Transaction", "Policy Transaction '"+Transaction+"' Failed. Exception"+exception, Status.FAIL);}
	}
	
	
	
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Send Mail	
	 *  Function Description	-	To send Email to Greg to verify Reinstatement NR scenarios
	 *  Author					-	jkumar
	 *  Script Created on		-	08/06/2014
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void sendMail() throws Exception 
	{	
		
		
		
		driver.get("https://mail.cognizant.com");
		Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='username']")));
		driver.findElement(By.xpath("//input[@id='username']")).click();
		actions.sendKeys("262106").perform();
		driver.findElement(By.xpath("//input[@id='password']")).click();
		actions.sendKeys("stoploss+7485").perform();Thread.sleep(250);
		actions.sendKeys(Keys.ENTER).perform();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='newmsgc']")));
		
		if(existsElement("a", "divBtnDismissall", "")==false)
		{
			driver.findElement(By.xpath("//a[@id='divBtnDismissall']")).click();
			Thread.sleep(2000);
		}
		
		
		String mwh=driver.getWindowHandle();
		
		driver.findElement(By.xpath("//a[@id='newmsgc']")).click();
		Set s=driver.getWindowHandles();
		//this method will gives you the handles of all opened windows

		Iterator ite=s.iterator();

		while(ite.hasNext())
		{
		    String popupHandle=ite.next().toString();
		    if(!popupHandle.contains(mwh))
		    {
		                driver.switchTo().window(popupHandle);
		              
		                driver.switchTo().window(mwh);
		    }
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divTo']")));
		driver.findElement(By.xpath("//textarea[@id='txtBdy']")).click();
		
		actions.sendKeys("Hai").perform();;;
		
		
		String S1=properties.getProperty("GQPass");
		//actions.hashCode()
		report.updateTestLog("Invoke GoQuote", "Invoke the application under test @ " +
								properties.getProperty("GQUrl"), Status.DONE);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'username')]")));
		driver.findElement(By.xpath("//input[contains(@id,'AuthenticationSignin_txtUsername')]")).click();
		actions.sendKeys(properties.getProperty("GQPass"));
		actions.sendKeys(properties.getProperty("GQPass"));
		
	}
	
		
	
	/* Go Quote Actions  */
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Go Quote Application Submission
	 *  Function Description	-	To submit the application in GO Quote
	 *  Author					-	jkumar
	 *  Script Created on		-	08/09/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void gqAppSubmit(String trans) throws Exception 
	{	
		
		String FirstName = dataTable.getData("Application_Submission", "FirstName",trans);
		String Middle = dataTable.getData("Application_Submission", "Middle",trans);
		String LastName = dataTable.getData("Application_Submission", "LastName",trans);
		String Suffix = dataTable.getData("Application_Submission", "Suffix",trans);
		String DoB = dataTable.getData("Application_Submission", "DoB",trans);
		String MarStatus = dataTable.getData("Application_Submission", "MarStatus",trans);
		String Gender = dataTable.getData("Application_Submission", "Gender",trans);
		String SSN = dataTable.getData("Application_Submission", "SSN",trans);
		LoB = dataTable.getData("General_Data", "LoB").toUpperCase().trim();
		String FirstName2 = dataTable.getData("Application_Submission", "FirstName2",trans);
		
		
		String State = dataTable.getData("Application_Submission", "State",trans);
		String StateVal = (State.substring((State.indexOf(" ")+1),State.length())).toString();
		Gender=(Gender.substring((Gender.indexOf(" ")+1),Gender.length())).toString();
		
		String EffDate = dataTable.getData("General_Data", "EffectiveDate");
		EffDate= EffDate.replaceAll("/", "");
		DoB=DoB.replaceAll("/", "");
		String StreetNumber = dataTable.getData("Application_Submission", "StreetNumber",trans);
		String Street = dataTable.getData("Application_Submission", "Street",trans);

		String AptUnit = dataTable.getData("Application_Submission", "AptUnit",trans);
		String City = dataTable.getData("Application_Submission", "City",trans);
		String ZIP = dataTable.getData("Application_Submission", "ZIP",trans);
		String County = dataTable.getData("Application_Submission", "County",trans);
		
		String Area = dataTable.getData("Application_Submission", "Area",trans);
		
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'ctl00_Content_uxbtnCreate')]")));
		
		driver.findElement(By.xpath("//*[contains(@id,'ctl00_Content_uxbtnCreate')]")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[contains(@id,'ctl00_Content_uxddlRatingState')]")));
		new Select(driver.findElement(By.id("ctl00_Content_uxddlRatingState"))).selectByVisibleText(StateVal);
		Thread.sleep(250);
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_deEffective_txtDate')]")).click();
		actions.sendKeys(EffDate).perform();
		Thread.sleep(250);
		
		driver.findElement(By.xpath("//*[contains(@id,'ctl00_Content_uxbtnNavigateNext')]")).click();
		
		if (LoB.equalsIgnoreCase("COMMERCIAL FARM")==false)
		{
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtFirstName')]")));
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtFirstName')]")).click();
		actions.sendKeys(FirstName).perform();
		Thread.sleep(250);
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtMiddleName')]")).click();
		actions.sendKeys(Middle).perform();
		Thread.sleep(250);
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLastName')]")).click();
		actions.sendKeys(LastName).perform();
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_uxddlNameSuffix"))).selectByVisibleText(Suffix);
		Thread.sleep(250);
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxdeBirthDate_txtDate')]")).click();
		actions.sendKeys(DoB).perform();
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_uxddlMaritalStatusId"))).selectByVisibleText(MarStatus);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_uxddlGenderId"))).selectByVisibleText(Gender);
		Thread.sleep(250);
		if (LoB.equalsIgnoreCase("COMMERCIAL FARM")) 
		{
			new Select(driver.findElement(By.id("ctl00_Content_uxddlLegalEntityPersonal"))).selectByVisibleText("Corporation");
			Thread.sleep(250);
		}
		//Add SSN -JP
		//We're removing SSN field - GPE 10/10/17
		//if (!SSN.isEmpty())
		//{
			//driver.findElement(By.id("ctl00_Content_uxbtnSupplementUpdate")).click();
			//Thread.sleep(500);
			//driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtSupplement')]")).click();
			//Thread.sleep(500);
			//actions.sendKeys(SSN).perform();
			//Thread.sleep(500);
			//driver.findElement(By.id("ctl00_Content_uxbtnSupplementSave")).click();
			//Thread.sleep(250);
		//}
			
		
		//Add Second Namesd insured -JP
		if(FirstName2.isEmpty() == false || FirstName2.equalsIgnoreCase("") == false )
		{
			String MiddleName2 = dataTable.getData("Application_Submission", "MiddleName2",trans);
			String LastName2 = dataTable.getData("Application_Submission", "LastName2",trans);
			String Suffix2 = dataTable.getData("Application_Submission", "Suffix2",trans);
			String DoB2 = dataTable.getData("Application_Submission", "DoB2",trans);
			String MarStatus2 = dataTable.getData("Application_Submission", "MarStatus2",trans);
			String Relationship = dataTable.getData("Application_Submission", "Relationship",trans);
			String Gender2 = dataTable.getData("Application_Submission", "Gender2",trans);
			String SSN2 = dataTable.getData("Application_Submission", "SSN2",trans);
			
			driver.findElement(By.id("ctl00_Content_uxbtnAddSecondInsured")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxt2FirstName')]")).click();
			actions.sendKeys(FirstName2).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxt2MiddleName')]")).click();
			actions.sendKeys(MiddleName2).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxt2LastName')]")).click();
			actions.sendKeys(LastName2).perform();
			Thread.sleep(250);
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddl2NameSuffix"))).selectByVisibleText(Suffix2);
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxde2BirthDate_txtDate')]")).click();
			actions.sendKeys(DoB2).perform();
			Thread.sleep(250);			
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddl2MaritalStatusId"))).selectByVisibleText(MarStatus2);
			Thread.sleep(250);
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddl2Relation"))).selectByVisibleText(Relationship);
			Thread.sleep(250);
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddl2GenderId"))).selectByVisibleText(Gender2);
			Thread.sleep(250);
			//SSN has been removed - GPE 10/16/17
			//if (!SSN2.isEmpty())
			//{
				//driver.findElement(By.id("ctl00_Content_uxbtn2SupplementUpdate")).click();
				//driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtSupplement')]")).click();
				//actions.sendKeys(SSN2).perform();
				//driver.findElement(By.id("ctl00_Content_uxbtnSupplementSave")).click();
				//Thread.sleep(250);
			//}
		}
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLocationStreetNumber')]")).click();
		actions.sendKeys(StreetNumber).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLocationStreetName')]")).click();
		actions.sendKeys(Street).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLocationAptUnit')]")).click();
		actions.sendKeys(AptUnit).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLocationCityName')]")).click();
		actions.sendKeys(City).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLocationPostalCode')]")).click();
		actions.sendKeys(ZIP).perform();
		Thread.sleep(250);		

		if(County.isEmpty()==false)
		{
		new Select(driver.findElement(By.id("ctl00_Content_uxddlLocationCounties"))).selectByVisibleText(County);
		Thread.sleep(500);
		}
		
		String MailingAddress = dataTable.getData("Application_Submission", "SeperateMailing",trans);
		
		new Select(driver.findElement(By.id("ctl00_Content_uxddlLocationMailingAddress"))).selectByVisibleText(MailingAddress);
		Thread.sleep(500);	
		
		actions.sendKeys(Keys.TAB).perform();
		Thread.sleep(500);	
		
		if (MailingAddress.equalsIgnoreCase("No"))
		{
			String PoBox = dataTable.getData("Application_Submission", "POBox",trans);
			String M_StreetNumber = dataTable.getData("Application_Submission", "M_StreetNumber",trans);
			String M_StreetName = dataTable.getData("Application_Submission", "M_StreetName",trans);
			String M_AppUnit = dataTable.getData("Application_Submission", "M_AppUnit",trans);
			String M_City = dataTable.getData("Application_Submission", "M_City",trans);
			String M_State = dataTable.getData("Application_Submission", "M_State",trans);
			String M_Zip = dataTable.getData("Application_Submission", "M_Zip",trans);

			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPoBox')]")).click();
			actions.sendKeys(PoBox).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtStreetNumber')]")).click();
			actions.sendKeys(M_StreetNumber).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtStreetName')]")).click();
			actions.sendKeys(M_StreetName).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtAptUnit')]")).click();
			actions.sendKeys(M_AppUnit).perform();
			Thread.sleep(250);		
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtCityName')]")).click();
			actions.sendKeys(M_City).perform();
			Thread.sleep(250);		
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddlStateAbbr"))).selectByVisibleText(M_State);
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPostalCode')]")).click();
			actions.sendKeys(M_Zip).perform();
			Thread.sleep(250);	
			
			
		}
		
		}
		 
		if (LoB.equalsIgnoreCase("COMMERCIAL FARM")==true){
			
			String CommercialName = dataTable.getData("Application_Submission", "CommercialName",trans);
			String DBAName = dataTable.getData("Application_Submission", "DBAName",trans);
			String LegalEntity = dataTable.getData("Application_Submission", "LegalEntity",trans);
			
			driver.findElement(By.id("ctl00_Content_uxrblInsuredType_0")).click();
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtCommercialName')]")));
			
			driver.findElement(By.id("ctl00_Content_uxtxtCommercialName")).click();
			actions.sendKeys(CommercialName).build().perform();
			
			driver.findElement(By.id("ctl00_Content_uxtxtDBAName")).click();
			actions.sendKeys(DBAName).build().perform();
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddlLegalEntityCommercial"))).selectByVisibleText(LegalEntity);
			Thread.sleep(250);
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtStreetNumber')]")).click();
		actions.sendKeys(StreetNumber).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtStreetName')]")).click();
		actions.sendKeys(Street).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtAptUnit')]")).click();
		actions.sendKeys(AptUnit).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtCityName')]")).click();
		actions.sendKeys(City).perform();
		Thread.sleep(250);

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPostalCode')]")).click();
		actions.sendKeys(ZIP).perform();
		Thread.sleep(250);		

		if(County.isEmpty()==false)
		{
		new Select(driver.findElement(By.id("ctl00_Content_uxddlCounty"))).selectByVisibleText(County);
		Thread.sleep(500);
		}
		
		}
		

		
	
		if(LoB.equals("HOME")) 
		{
			//String gqyears = dataTable.getData("Application_Submission", "Years",trans);
			new Select(driver.findElement(By.id("ctl00_Content_uxddlAddressYears"))).selectByVisibleText("10 years or more");
			Thread.sleep(250);
		}
		
		if(Area.isEmpty()==false)
		{
			
			String PhonePrefix = dataTable.getData("Application_Submission", "PhonePrefix",trans);
			String PhoneSequence = dataTable.getData("Application_Submission", "PhoneSequence",trans);
			String Ext = dataTable.getData("Application_Submission", "Ext",trans);
			String Email = dataTable.getData("Application_Submission", "Email",trans);
			
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPhoneArea')]")).click();
		actions.sendKeys(Area).perform();
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPhonePrefix')]")).click();
		actions.sendKeys(PhonePrefix).perform();
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPhoneSequence')]")).click();
		actions.sendKeys(PhoneSequence).perform();
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPhoneExtension')]")).click();
		actions.sendKeys(Ext).perform();
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtEmailAddress')]")).click();
		actions.sendKeys(Email).perform();
		
		}
		
		driver.findElement(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")).click();
		Thread.sleep(250);
		
		/*if (LoB.equalsIgnoreCase("HOME")==true)
		{
		//Putting in another click to cover address changed by the system - GPE 3/16/16
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")));
		driver.findElement(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")).click();
		Thread.sleep(250);
		
		//Putting in another click to cover WSRB failure - GPE 3/30/16
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")));
		driver.findElement(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")).click();
		Thread.sleep(250);
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'ctl00_Content_uxddlProgramType')]")));
		
		}*/
		if (LoB.equalsIgnoreCase("DWELLINGFIRE")==true)
		{
		//Putting in another click to cover address changed by the system - GPE 3/16/16
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")));
		driver.findElement(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")).click();
		Thread.sleep(250);
		
		//Putting in another click to cover WSRB failure - GPE 3/30/16
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")));
		driver.findElement(By.xpath("//*[contains(@id,'ctl00_uxbtnNavigateNext')]")).click();
		Thread.sleep(250);
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'ctl00_Content_uxddlProgramType')]")));
		
		}
		Thread.sleep(2000);
		report.updateTestLog("GQ Application Submission", "Application submitted successfully", Status.DONE);
		
	}
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Import Go Quote Policy
	 *  Function Description	-	To import GO Quote policy into diamond
	 *  Author					-	vbabu
	 *  Script Created on		-	06/18/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void importgqPolicy() throws Exception 
	{
		PolicyNumber = dataTable.getData("General_Data", "PolicyNumber_NewBusiness");
		
		//System.setProperty("webdriver.chrome.driver", "K:\\Apps\\Selenium\\ChromeDriver\\chromedriver.exe");
		
		
		driver.get(properties.getProperty("ApplicationUrl"));
		
		//driver.navigate().to("http://broker-test3.grange.com/DiamondWeb/controlloader.aspx?p=Headquarters");
		
		 
		
		
		String defVal="";
		wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickPolicyLookupControl_TypeInsCombo_D_I")));
		//Click Search Button
		
		driver.findElement(By.id("QuickPolicyLookupControl_FireLookupImageButton")).click();
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")));
		Thread.sleep(4000);
		do
		{
				driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")).clear();
				driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")).sendKeys(PolicyNumber);
				Thread.sleep(250);
				//actions.moveToElement(driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I"))).click().build().perform();
				//actions.sendKeys(Keys.HOME,Keys.SHIFT,Keys.END).build().perform();
				Thread.sleep(250);
				//actions.sendKeys(PolicyNumber).build().perform();
				Thread.sleep(250);
				actions.sendKeys(Keys.TAB).build().perform();
				defVal=driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_PolicyNumberInsCombo_D_I")).getAttribute("value");
		}while(defVal.equalsIgnoreCase(PolicyNumber)==false);
		
		//Thread.sleep(4000);
		driver.findElement(By.id("QuickPolicyLookupControl_QuickLookupASPxPopupControl_OkButton_CD")).click();
		Thread.sleep(4000);
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("QuickLinksExpandCollapseImage")));
		
		
		
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='YesInsMessageBoxButtonMiddle']/a")));
		//driver.findElement(By.xpath("//*[@id='YesInsMessageBoxButton_CD']/div")).click();
		
		if(existsElement("", "P_L_ControlLoaderInsMessageBox_MyASPxPopupControl_YesInsMessageBoxButton_CD", "")==true)
		{
		driver.findElement(By.id("P_L_ControlLoaderInsMessageBox_MyASPxPopupControl_YesInsMessageBoxButton_CD")).click();
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='P_L_InsEmployeeView_RateToolStripButton']")));
		}
		
		if (driver.findElements(By.id("QuickLinksExpandCollapseImage")).size() > 0){
				report.updateTestLog("Policy Search", "Policy Search Success", Status.DONE);
		}else{
				report.updateTestLog("Policy Search", "Policy Search Failed", Status.FAIL);
		}
	}	
	
	/*******************************************************************************************************************************
	 *  Function Automated	-	Quick Rate Policy		
	 *  Function Description	-	Rate Policy - I pruned this down for CCI tests
	 *  Author	-	gencke
	 *  Script Created on	-	03/10/2017
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void quickPolicyRate() throws Exception
	{
		String validationMsg = null;
		//Save Location
		Thread.sleep(1250);
		if (driver.findElements(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).size()>0){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")));
			driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).click();
			Thread.sleep(1500);
		}
		

		if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
		{
			driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			Thread.sleep(750);
		}
		
			//actions.moveToElement(driver.findElement(By.id("P_L_V_RateToolStripButton"))).click().perform();	
			//Thread.sleep(2550);
			if(existsElement("td", "P_L_V_RateToolStripButton", ""))
			{
				actions.moveToElement(driver.findElement(By.id("P_L_V_RateToolStripButton"))).click().perform();
				Thread.sleep(2550);
			}	
				else{
				actions.moveToElement(driver.findElement(By.id("P_L_V_PreviousImageToolStripButton"))).click().perform();	
				Thread.sleep(600);
				actions.moveToElement(driver.findElement(By.id("P_L_V_RateToolStripButton"))).click().perform();
				Thread.sleep(2550);
				}
		if(driver.findElements(By.xpath("//td[contains(text(), 'Rating Successful')]")).size()>0)
		{
			if(driver.findElements(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD")).size()>0)
			{
				Thread.sleep(500);
				actions.moveToElement(driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD"))).click().perform();
				Thread.sleep(500);
			}
			else if(driver.findElements(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).size()>0)
				actions.moveToElement(driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD"))).click().perform();
			report.updateTestLog("Rate Policy", "Policy Rating Success", Status.DONE);
			

		}
		else
			report.updateTestLog("Rate Policy", "Policy Rating Failed", Status.FAIL);
	
	}
	
	/*******************************************************************************************************************************
	 *  Function Automated		-	Quick Issue Policy		
	 *  Function Description	-	Issue Policy with no Billing Information update
	 *  Author					-	gencke
	 *  Script Created on		-	03/10/2017
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void quickPolicyIssue() throws Exception
	{
		String validationMsg = null;
		//Save Location
		Thread.sleep(1250);
		if (driver.findElements(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).size()>0){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")));
			driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).click();
			Thread.sleep(1500);
		}
		
		if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
		{
			driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			Thread.sleep(1500);
		}
		
		//Issue
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_IssueToolStripButton")));
		Thread.sleep(500);
		driver.findElement(By.id("P_L_V_IssueToolStripButton")).click();
		Thread.sleep(500);
		if(existsElement("", "P_L_V_EmployeeViewASPxPopupControl_UpdateClient_IssueButton_CD", "")==true)
			driver.findElement(By.id("P_L_V_EmployeeViewASPxPopupControl_UpdateClient_IssueButton_CD")).click();
		Thread.sleep(7500);
		
		if(existsElement("", "P_L_V_ValidationPopUp_MyASPxPopupControl_MainTD", "")==false)
			Thread.sleep(2000);
		  wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_MainTD")));
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_InsEmployeeView_ValidationPopUp_OtherInsDataGridView")));
				
		if(driver.findElements(By.xpath("//td[text()='Policy was successfully issued.']")).size()>0)
		{
			report.updateTestLog("Issue", "Policy Issued Successfully", Status.PASS);
			if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
				driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			else 
				driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_OKInsValidationButton_CD")).click();
				
			Thread.sleep(1000);
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TaskListControl_UpdatePanel")));
		//This changed with the 531.006 build - GPE 7/5/16
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_G_MainASPxCallbackPanel_r1w0_t0_UpdatePanel")));	
		}
		else if(existsElement("td", "P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD", ""))
		{
			report.updateTestLog("Issue", "Policy Issued Successfully", Status.PASS);
			driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ContinueInsValidationButton_CD")).click();
			Thread.sleep(1000);
			//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_TaskListControl_UpdatePanel")));
			//This changed with the 531.006 build - GPE 7/5/16
			wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_G_MainASPxCallbackPanel_r1w0_t0_UpdatePanel")));
		}
		else
		{
		WebElement table_element = driver.findElement(By.id("P_L_V_ValidationPopUp_MyASPxPopupControl_ErrorInsDataGridView"));
	    List<WebElement> tr_collection=table_element.findElements(By.xpath("//table[@id='P_L_V_ValidationPopUp_MyASPxPopupControl_ErrorInsDataGridView']/tbody/tr"));
		
		int row_num,col_num;
		row_num=1;
		for(WebElement trElement : tr_collection)
		{
			List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
			//System.out.println("NUMBER OF COLUMNS = "+td_collection.size());
			col_num=1;
			for(WebElement tdElement : td_collection)
			{
				System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
				validationMsg = tdElement.getText();
				report.updateTestLog("Issue", "Issue Failed. Reason: "+validationMsg, Status.FAIL);
				
				col_num++;
			}
			row_num++;
		}
		
		}
	}

	/*******************************************************************************************************************************
	 *  Function Automated		-	Verify GQ Error Message Text
	 *  Function Description	-	To Verify Error Messages in GQ
	 *  Author					-	jprak
	 *  Script Created on		-	06/01/2015	
	 * @throws Exception
	 *******************************************************************************************************************************/	
	
	public void gqVerifyText(String trans) throws Exception 
	{
		String ErrorMessage1 = dataTable.getData("Verify_Text", "ErrorMessage1",trans);
		String ErrorMessage2 = dataTable.getData("Verify_Text", "ErrorMessage2",trans);
		String pageSource = driver.findElement(By.tagName("body")).getText();
		Thread.sleep(500);
				
		
		if (pageSource.contains(ErrorMessage1))
			report.updateTestLog("GQ Message Verify", "Expected Text of "+ErrorMessage1+ " Found. SUCCESS.", Status.PASS); 
		else
			report.updateTestLog("GQ Message Verify", "Expected Text of "+ErrorMessage1+ " Not Found. FAILED.", Status.FAIL);
		
		if(ErrorMessage2.isEmpty()==false)
		{	
			if (pageSource.contains(ErrorMessage2))
				report.updateTestLog("GQ Message Verify", "Expected Text of "+ErrorMessage2+ " Found. SUCCESS.", Status.PASS); 
			else
				report.updateTestLog("GQ Message Verify", "Expected Text of "+ErrorMessage2+ " Not Found. FAILED.", Status.FAIL);
		}
	}
}
	

	
	





 





















	
	
	
	
	
	
	
	/* ----- Sample functions ---------------------
	public void invokeApplication() 
	{
		driver.get(properties.getProperty("ApplicationUrl"));
		report.updateTestLog("Invoke Application", "Invoke the application under test @ " +
								properties.getProperty("ApplicationUrl"), Status.DONE);
	}
	public void login()
	{
		String userName = dataTable.getData("General_Data", "Username");
		String password = dataTable.getData("General_Data", "Password");
		
		driver.findElement(By.name("userName")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).click();
		
		report.updateTestLog("Login", "Enter login credentials: " +
										"Username = " + userName + ", " +
										"Password = " + password, Status.DONE);
	}
	
	public void registerUser()
	{
		driver.findElement(By.linkText("REGISTER")).click();
		driver.findElement(By.name("firstName")).sendKeys(dataTable.getData("RegisterUser_Data", "FirstName"));
		driver.findElement(By.name("lastName")).sendKeys(dataTable.getData("RegisterUser_Data", "LastName"));		
		driver.findElement(By.name("phone")).sendKeys(dataTable.getData("RegisterUser_Data", "Phone"));		
		driver.findElement(By.name("userName")).sendKeys(dataTable.getData("RegisterUser_Data", "Email"));	
		driver.findElement(By.name("address1")).sendKeys(dataTable.getData("RegisterUser_Data", "Address"));
		driver.findElement(By.name("city")).sendKeys(dataTable.getData("RegisterUser_Data", "City"));
		driver.findElement(By.name("state")).sendKeys(dataTable.getData("RegisterUser_Data", "State"));
		driver.findElement(By.name("postalCode")).sendKeys(dataTable.getData("RegisterUser_Data", "PostalCode"));
		driver.findElement(By.name("email")).sendKeys(dataTable.getData("General_Data", "Username"));
		String password = dataTable.getData("General_Data", "Password");
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("confirmPassword")).sendKeys(password);
		driver.findElement(By.name("register")).click();
		report.updateTestLog("Registration", "Enter user details for registration", Status.DONE);
	}
	
	public void clickSignIn()
	{
		driver.findElement(By.linkText("sign-in")).click();
		report.updateTestLog("Click sign-in", "Click the sign-in link", Status.DONE);
	}
	
	public void findFlights()
	{
		driver.findElement(By.name("passCount")).sendKeys((dataTable.getData("Passenger_Data", "PassengerCount")));
		driver.findElement(By.name("fromPort")).sendKeys((dataTable.getData("Flights_Data", "FromPort")));
		driver.findElement(By.name("fromMonth")).sendKeys((dataTable.getData("Flights_Data", "FromMonth")));
		driver.findElement(By.name("fromDay")).sendKeys((dataTable.getData("Flights_Data", "FromDay")));
		driver.findElement(By.name("toPort")).sendKeys((dataTable.getData("Flights_Data", "ToPort")));
		driver.findElement(By.name("toMonth")).sendKeys((dataTable.getData("Flights_Data", "ToMonth")));
		driver.findElement(By.name("toDay")).sendKeys((dataTable.getData("Flights_Data", "ToDay")));
		driver.findElement(By.name("airline")).sendKeys((dataTable.getData("Flights_Data", "Airline")));
		driver.findElement(By.name("findFlights")).click();
		report.updateTestLog("Find Flights", "Search for flights using given test data", Status.DONE);
	}
	
	public void selectFlights()
	{
		driver.findElement(By.name("reserveFlights")).click();
		report.updateTestLog("Select Flights", "Select the first available flights", Status.DONE);
	}
	
	public void bookFlights()
	{
		String[] passengerFirstNames = dataTable.getData("Passenger_Data", "PassengerFirstNames").split(",");
		String[] passengerLastNames = dataTable.getData("Passenger_Data", "PassengerLastNames").split(",");
		int passengerCount = Integer.parseInt(dataTable.getData("Passenger_Data", "PassengerCount"));
		
		for(int i=0; i<passengerCount; i++) {
			driver.findElement(By.name("passFirst" + i)).sendKeys(passengerFirstNames[i]);
			driver.findElement(By.name("passLast" + i)).sendKeys(passengerLastNames[i]);
		}
		driver.findElement(By.name("creditCard")).sendKeys(dataTable.getData("Passenger_Data", "CreditCard"));
		driver.findElement(By.name("creditnumber")).sendKeys(dataTable.getData("Passenger_Data", "CreditNumber"));
		
		report.updateTestLog("Book Tickets", "Enter passenger details and book tickets", Status.SCREENSHOT);
		
		driver.findElement(By.name("buyFlights")).click();
	}
	
	public void backToFlights()
	{
		driver.findElement(By.xpath("//a/img")).click();
	}
	
	public void logout()
	{
		driver.findElement(By.linkText("SIGN-OFF")).click();
		report.updateTestLog("Logout", "Click the sign-off link", Status.DONE);
	}
} --------------------------------------*/


/***************************************************************************************************************************************
 * Search policy Old
 actions.moveToElement(driver.findElement(By.id("SearchesMenu"))).click().perform();
		//Policy Search
		actions.moveToElement(driver.findElement(By.xpath("//*[@id='SearchesSubMenu_1']/tbody/tr/td[2]/a"))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_AdvancedLookup_MyTabContainer_CriteriaTabPanel_MyCriteriaControl_FieldInsCombo_FieldInsCombo_I")));
		
		actions.moveToElement(driver.findElement(By.id("P_L_AdvancedLookup_MyTabContainer_CriteriaTabPanel_MyCriteriaControl_FieldInsCombo_FieldInsCombo_I"))).click().perform();
		actions.sendKeys("Policy Number").build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(200);
		actions.sendKeys("Any Type").build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(200);
		actions.sendKeys("Any Status").build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(200);
		actions.sendKeys(PolicyNumber).build().perform();
		
		actions.moveToElement(driver.findElement(By.xpath("//*[@id='FindNowButtonMiddle']/a"))).click().perform();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='P_L_AdvancedLookup_MyInsDataGridView']")));
		
		WebElement table_element = driver.findElement(By.id("P_L_AdvancedLookup_MyInsDataGridView"));
		List<WebElement> tr_collection = table_element.findElements(By.xpath("//table[@id='P_L_AdvancedLookup_MyInsDataGridView']/tbody/tr"));
        System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
        int rowCount;
        rowCount = tr_collection.size();
        String id = "tbody/tr["+rowCount+"]/";
        actions.moveToElement(driver.findElement(By.xpath("//table[@id='P_L_AdvancedLookup_MyInsDataGridView']/"+id+"td[1]/input")));
        actions.click();
        actions.perform();
        
 */
