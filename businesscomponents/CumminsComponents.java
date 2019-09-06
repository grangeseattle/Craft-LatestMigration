package businesscomponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;
import com.cognizant.framework.nft.AccessibilityNFT;
import com.cognizant.framework.nft.PerformanceNFT;
import com.cognizant.framework.nft.SecurityNFT;

import pages.CumminsPage;

/**
 * Class for storing general purpose business components
 * 
 * @author Cognizant
 */
public class CumminsComponents extends ReusableLibrary {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	CumminsPage cumminsPage = new CumminsPage(scriptHelper);
	WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);

	public CumminsComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);

	}

	public void callApp() {
		String applicationurl = null;
		if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance1")) {
			applicationurl = properties.getProperty("ApplicationUrl1");
		} else if (scriptHelper.getTestInstance().equalsIgnoreCase("Instance2")) {
			applicationurl = properties.getProperty("ApplicationUrl2");
		}	 
		 driver.get(applicationurl);
		 
		 brokenLinkValidatorReusable(applicationurl,driver.getWebDriver());
		 driver.manage().window().maximize();
		 scriptHelper.getReport().getSeleniumTestParameters().getCurrentScenario();
		 if (Boolean.parseBoolean(properties.getProperty("accessibilityReport"))) {
			 AccessibilityNFT.evaluatePageAccessibilityTest(driver.getWebDriver(),applicationurl,scriptHelper);
		 }if (Boolean.parseBoolean(properties.getProperty("performanceReport"))) {
			 PerformanceNFT.evaluatePerformanceForPage(driver.getWebDriver(),driver.getCurrentUrl(),scriptHelper);
		 }
		 if (Boolean.parseBoolean(properties.getProperty("securityReport"))) {
				
		SecurityNFT.evaluateSecurityForPage(driver.getCurrentUrl(),
				scriptHelper);
		 }			
	}

	public void validCumminslogin() {

		cumminsPage.login();
		report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
	}

	public void viewAssetDetails() throws InterruptedException {
		WebElement firstSite = driver.findElement(cumminsPage.CPSUniversity);
		wait.until(ExpectedConditions.visibilityOf(firstSite));
		firstSite.click();
		WebElement viewAsset = driver.findElement(cumminsPage.viewAsset);
		wait.until(ExpectedConditions.visibilityOf(viewAsset));
		clickViewAssets("2300 Normal");
		report.updateTestLog("View Assets", "Asset details opened", Status.PASS);
		switchStateCtrl();
	//	checkEvents();
		
	}

	public void viewEventsDetails() throws InterruptedException {
		WebElement firstSite = driver.findElement(cumminsPage.CPSUniversity);
		wait.until(ExpectedConditions.visibilityOf(firstSite));
		firstSite.click();
		WebElement viewAsset = driver.findElement(cumminsPage.viewAsset);
		wait.until(ExpectedConditions.visibilityOf(viewAsset));
		driver.findElement(cumminsPage.event).click();
		Select select = new Select(driver.findElement(cumminsPage.dorpDownEvent));
		select.selectByValue("0");
		Thread.sleep(10000);
		driver.findElement(cumminsPage.btnExport).click();
		driver.findElement(cumminsPage.popupExport).click();
		report.updateTestLog("Export Data", "Data Exported Successfully", Status.PASS);
	}

	public void logout() {
		
	}

	public void clickViewAssets(String value) {
		WebElement table = driver.findElement(By.xpath("//*[@id='form0']/table"));
		outerloop: for (WebElement tr : table.findElements(By.tagName("tr"))) {
			List<WebElement> tds = tr.findElements(By.tagName("td"));
			for (int i = 0; i < tds.size(); i++) {
				String xyz = tds.get(i).getText();
				if (xyz.equals(value)) {
					tds.get(5).click();
					break outerloop;
				}

			}
		}
	}

	public void switchStateCtrl() throws InterruptedException {
		String test1 = driver.findElement(By.cssSelector("a.command.start")).getAttribute("class");
		String test2 = driver.findElement(By.cssSelector("a.command.stop")).getAttribute("class");
		if (!test1.contains("disabled")) {
			driver.findElement(By.cssSelector("a.command.start")).click();
			driver.findElement(By.cssSelector("#modal-container > div > div > footer > button.accept.blue.btn")).click();
			wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("body > div.toast"), "Remote Start command received. Generator set will start momentarily."));
			String actualtxt = driver.findElement(By.cssSelector("body > div.toast")).getText();
			String expectedtxt = "Remote Start command received. Generator set will start momentarily.";
			if (expectedtxt.equalsIgnoreCase(actualtxt)) {
				report.updateTestLog("Test Start", "Test Started successfully", Status.PASS);
			}
		} else if (!test2.contains("disabled")) {
			driver.findElement(By.cssSelector("a.command.stop")).click();
			driver.findElement(By.cssSelector("#modal-container > div > div > footer > button.accept.blue.btn")).click();
			wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("body > div.toast"), "Remote Stop command received. Generator set will stop momentarily."));
			String actualtxt = driver.findElement(By.cssSelector("body > div.toast")).getText();
			String expectedtxt = "Remote Stop command received. Generator set will stop momentarily.";
			if (expectedtxt.equalsIgnoreCase(actualtxt)) {
				report.updateTestLog("Test Stop", "Test Stopped successfully", Status.PASS);
			}
		}
	}

	public void checkEvents(String option1, String option2, String option3)
	{
		driver.findElement(cumminsPage.viewEvent).click();
		String lastupdate = driver.findElement(By.id("last-updated")).getText();
		if(driver.findElement(By.cssSelector("div.ats-icon.switch_13")).isDisplayed())
		{
			WebElement table = driver.findElement(By.id("events-table"));
		    List<WebElement> tr = table.findElements(By.tagName("tr"));
		    for(int i=0;i<5;i++)
		    {
		    List<WebElement> tds = tr.get(1).findElements(By.tagName("td"));
				
					String xyz = tds.get(3).getText();
					if (xyz.equals("Test/Exercise Not In-Progress")||xyz.equals("Source-1 Connected")||xyz.equals("Source-2 Disconnected")) {
						 tr.get(1).findElements(By.tagName("td")).get(4).click();
						 tr.get(2).findElements(By.tagName("td")).get(4).click();
						 tr.get(3).findElements(By.tagName("td")).get(4).click();
					}

		   }
		}
		else if(driver.findElement(By.cssSelector("div.ats-icon.switch_7")).isDisplayed())
		{
			WebElement table = driver.findElement(By.id("events-table"));
		    List<WebElement> tr = table.findElements(By.tagName("tr"));
		    for(int i=0;i<5;i++)
		    {
		    List<WebElement> tds = tr.get(1).findElements(By.tagName("td"));
				
					String xyz = tds.get(3).getText();
					if (xyz.equals("Test/Exercise In-Progress")||xyz.equals("Source-1 Disconnected")||xyz.equals("Source-2 Connected")) {
						 tr.get(1).findElements(By.tagName("td")).get(4).click();
						 tr.get(2).findElements(By.tagName("td")).get(4).click();
						 tr.get(3).findElements(By.tagName("td")).get(4).click();
					}

		   }
			
		}
	}
	
	public void checkEvents()
	{
		driver.findElement(cumminsPage.viewEvent).click();
		//String lastupdate = driver.findElement(By.id("last-updated")).getText();
		if(driver.findElement(By.cssSelector("div.genset-icon.status_4")).isDisplayed())
		{
			WebElement table = driver.findElement(By.id("events-table"));
		    List<WebElement> tr = table.findElements(By.tagName("tr"));
		    
		    List<WebElement> tds = tr.get(1).findElements(By.tagName("td"));
		    for(int i=0;i<5;i++)
		    {	
				    String xyz = tds.get(i).getText();
					System.out.println(xyz);
					/*String xyz = tds.get(3).getText();
					if (xyz.equals("Genset Started")) {
						 tr.get(1).findElements(By.tagName("td")).get(4).click();
						 driver.findElement(By.xpath("//*[@id='form0']/div/a")).click();
						 driver.findElement(cumminsPage.btnExport).click();
						 report.updateTestLog("Event", "Event exported successfully", Status.PASS);
					}*/
		    }
		   }
		
		else if(driver.findElement(By.cssSelector("div.genset-icon.status_1")).isDisplayed())
		{
			WebElement table = driver.findElement(By.id("events-table"));
		    List<WebElement> tr = table.findElements(By.tagName("tr"));
		    
		    List<WebElement> tds = tr.get(1).findElements(By.tagName("td"));
		    for(int i=0;i<5;i++)
		    {	
					String xyz = tds.get(i).getText();
					System.out.println(xyz);
					/*if (xyz.equals("Genset Stopped")) {
						 tr.get(1).findElements(By.tagName("td")).get(4).click();
						 driver.findElement(By.xpath("//*[@id='form0']/div/a")).click();
						 driver.findElement(cumminsPage.btnExport).click();
						 report.updateTestLog("Event", "Event exported successfully", Status.PASS);
					}*/

		   }
			
		}
		else
		{
			System.out.println("Classes are not visible");
		}
	}
	
	public void createNotes()
	{
		WebElement firstSite = driver.findElement(cumminsPage.CPSUniversity);
		wait.until(ExpectedConditions.visibilityOf(firstSite));
		firstSite.click();
		WebElement viewAsset = driver.findElement(cumminsPage.viewAsset);
		wait.until(ExpectedConditions.visibilityOf(viewAsset));
		driver.findElement(cumminsPage.viewNotes).click();
		driver.findElement(cumminsPage.addNotes).click();
		driver.findElement(cumminsPage.createNote).sendKeys("ABD");
		driver.findElement(cumminsPage.save).click();
		report.updateTestLog("Note", "Note Created Successfully", Status.PASS);
	}
    
	public void scheduleExcersize()
	{
		WebElement firstSite = driver.findElement(cumminsPage.ATStestcell);
		wait.until(ExpectedConditions.visibilityOf(firstSite));
		firstSite.click();
		WebElement viewAsset = driver.findElement(cumminsPage.viewAsset);
		wait.until(ExpectedConditions.visibilityOf(viewAsset));
		clickViewAssets("PC3300 Genset");
		driver.findElement(cumminsPage.scheduleExcersize).click();
		driver.findElement(cumminsPage.enable).click();
		Select select = new Select(driver.findElement(cumminsPage.interval));
		select.selectByValue("1");
		driver.findElement(cumminsPage.startDate).clear();
		driver.findElement(cumminsPage.startDate).sendKeys("10/27/2018");
		//driver.findElement(By.xpath("//*[@id='form0']/ul/li[5]/div[1]")).click();
		driver.findElement(cumminsPage.hours).clear();
		driver.findElement(cumminsPage.hours).sendKeys("10");
		//driver.findElement(cumminsPage.hours).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(cumminsPage.minutes).clear();
		driver.findElement(cumminsPage.minutes).sendKeys("20");
		//driver.findElement(cumminsPage.hours).sendKeys(Keys.ARROW_DOWN);
		Select select1 = new Select(driver.findElement(cumminsPage.duration));
		select1.selectByVisibleText("20");
		//driver.findElement(cumminsPage.endDate).sendKeys("10/21/2018");
		driver.findElement(cumminsPage.saveExersize).click();
		driver.findElement(By.cssSelector("#modal-container > div > div > footer > button.accept.blue.btn")).click();
		wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("body > div.toast"), "Schedule has been sent to this device"));
		WebElement opentoast = driver.findElement(By.cssSelector("body > div.toast"));
		//wait.until(ExpectedConditions.visibilityOf(opentoast));
		if(opentoast.getText().equals("Schedule has been sent to this device"))
		{
		report.updateTestLog("Excersize", "Excersize created successfully", Status.PASS);
		}
		else
		{
		report.updateTestLog("Excersize", "Excersize is not created successfully", Status.FAIL);	
		}
		
		
	}

}