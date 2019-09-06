package pages;

import org.openqa.selenium.By;

import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;

public class CumminsPage extends ReusableLibrary {

	public static By signIn = By.linkText("SIGN IN");
	public static By txtUsername = By.id("signInName");
	public static By txtPassword = By.id("password");
	public static By btnSignIn = By.id("next");
	
	public static By firstSite = By.xpath("//*[@id='account-list']/li[3]/ul/li[3]/div");
	public static By ATStestcell = By.xpath("//*[text()='ATS Test Cell']");
	public static By CPSUniversity =  By.xpath("//*[text()='CPS University Building']");
	public static By viewAsset = By.linkText("VIEW ASSET");
	public static By viewEvent = By.xpath("//a[contains(@href,'events-tab')]");
	//TC 2
	public static By event = By.xpath("//*[@id='site-content']/div[2]/ul/li[2]/a");
	public static By dorpDownEvent = By.id("Filter");
	public static By btnExport = By.cssSelector("#form0 > div.export-pane.right > a");
	public static By popupExport = By.cssSelector("button.blue.btn");
	//TC 3
	public static By viewNotes = By.xpath("//*[@id='site-content']/div[2]/ul/li[5]/a");
	public static By addNotes = By.linkText("ADD NOTE");
	public static By createNote = By.id("viewModel_Text");
	public static By save = By.xpath("//*[@id='form0']/div/div/div[3]/button");
	//TC 4
	public static By viewAssetExcersize =  By.xpath("//*[@id='form0']/table/tbody/tr[5]/td[6]/a");
	public static By scheduleExcersize = By.xpath("//*[@id='asset-tabs']/ul/li[4]/a");
	public static By enable =  By.xpath("//input[@value='True']");
	public static By disable =  By.xpath("//input[@value='False']");
	public static By interval = By.id("Interval");
	public static By startDate = By.id("ExerciseStartDate");
	public static By hours = By.className("hours");
	public static By minutes = By.className("minutes");	
	public static By ampm = By.className("ampm-toggle");
	public static By duration = By.id("Duration");
	public static By endDate = By.id("ExerciseEndDate");
	public static By saveExersize = By.id("exercise-save-btn");
	public static By okay = By.xpath("//*[@id='modal-container']/div/div/footer/button[1]");	
	public CumminsPage(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void login() {

		report.updateTestLog("Launch App", "Application Opened", Status.SCREENSHOT);
		String userName = dataTable.getData("General_Data", "Username");
		String password = dataTable.getData("General_Data", "Password");
		driver.findElement(signIn).click();
		driver.findElement(txtUsername).sendKeys(userName);
		driver.findElement(txtPassword).sendKeys(password);
		driver.findElement(btnSignIn).click();
		report.updateTestLog("Login", "Clicked on Sign In", Status.DONE);
	}

}
