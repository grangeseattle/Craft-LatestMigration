 package businesscomponents;

/*import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;*/


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;


/**
 * Business Component Library template
 * @author pmani
 */


/*import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.cognizant.framework.Status;

import supportlibraries.ScriptHelper;*/





/**
* Business Component Library template
* @author pmani
*/
public class UMBRELLABusinessComponents extends FunctionalComponents 
{
	/**
	 * Constructor to initialize the business component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	
	public static String gqProgramType;
	
	public UMBRELLABusinessComponents(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}

	/*******************************************************************************************************************************
	 *  Function Automated	-	Policy Level Coverage and Rating Information	
	 *  Function Description	-	Enter policy level coverage details
	 *  Author	-	jprak
	 *  Script Created on	-	03/05/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	//Appended gq before method name in order to differentiate from Diamond - vbabu on 02/05/2018
	public void gqumbrPolicyLevelCoverage(String trans) throws Exception
	{
		
		String Limit = dataTable.getData("UMBR_PolicyLevelCoverage", "Limit",trans);
		String NumOfVehicles = dataTable.getData("UMBR_PolicyLevelCoverage", "NumOfVehicles",trans);
		String Motorcycles = dataTable.getData("UMBR_PolicyLevelCoverage", "Motorcycles",trans);
		String NumOfRecVeh = dataTable.getData("UMBR_PolicyLevelCoverage", "NumOfRecVeh",trans);
		String TotalResidents = dataTable.getData("UMBR_PolicyLevelCoverage", "TotalResidents",trans);
		String UnderTwentyOne = dataTable.getData("UMBR_PolicyLevelCoverage", "UnderTwentyOne",trans);
		String TwentyOneFive = dataTable.getData("UMBR_PolicyLevelCoverage", "TwentyOneFive",trans);
		String TotalResidences = dataTable.getData("UMBR_PolicyLevelCoverage", "TotalResidences",trans);
		String DwellingProperties = dataTable.getData("UMBR_PolicyLevelCoverage", "DwellingProperties",trans);
		String FamilyUnits = dataTable.getData("UMBR_PolicyLevelCoverage", "FamilyUnits",trans);
		String Watercraft = dataTable.getData("UMBR_PolicyLevelCoverage", "Watercraft",trans);
		String BusinessExp = dataTable.getData("UMBR_PolicyLevelCoverage", "BusinessExp",trans);
		String HomeBusLocs = dataTable.getData("UMBR_PolicyLevelCoverage", "HomeBusLocs",trans);
		String FarmExp = dataTable.getData("UMBR_PolicyLevelCoverage", "FarmExp",trans);
		String FarmLocs = dataTable.getData("UMBR_PolicyLevelCoverage", "FarmLocs",trans);
		
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'ctl00_Content_uxddlCoverage')]")));
		
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//Limit
		new Select(driver.findElement(By.id("ctl00_Content_uxddlCoverage"))).selectByVisibleText(Limit);
		Thread.sleep(250);
		
		//Vehicles
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtMotorVehicles')]")).click();
		actions.sendKeys(NumOfVehicles).perform();
		Thread.sleep(250);	
	
		new Select(driver.findElement(By.id("ctl00_Content_uxddlMotorcycles"))).selectByVisibleText(Motorcycles);
		Thread.sleep(250);
	
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtRecreationalVehicles')]")).click();
		actions.sendKeys(NumOfRecVeh).perform();
		Thread.sleep(250);	
	
		//Residents
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtHouseholdResidents')]")).click();
		actions.sendKeys(TotalResidents).perform();
		Thread.sleep(250);	

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtDriversUnder21')]")).click();
		actions.sendKeys(UnderTwentyOne).perform();
		Thread.sleep(250);	

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtDrivers21to25')]")).click();
		actions.sendKeys(TwentyOneFive).perform();
		Thread.sleep(250);			
		
		//Dwellings
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtResidencesOwned')]")).click();
		actions.sendKeys(TotalResidences).perform();
		Thread.sleep(250);	

		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtRentalProperties')]")).click();
		actions.sendKeys(DwellingProperties).perform();
		actions.sendKeys(Keys.TAB).perform();
		Thread.sleep(250);	
		
		if (FamilyUnits.isEmpty() == false) {
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtFamilyUnits')]")).click();
		actions.sendKeys(FamilyUnits).perform();
		Thread.sleep(250);
		}
		
		//Watercraft
		new Select(driver.findElement(By.id("ctl00_Content_uxddlWatercraft"))).selectByVisibleText(Watercraft);
		Thread.sleep(250);
		
		if (Watercraft.equalsIgnoreCase("YES")){
			
			String JetSki = dataTable.getData("UMBR_PolicyLevelCoverage", "JetSki",trans);
			String Boat50hp32l = dataTable.getData("UMBR_PolicyLevelCoverage", "Boat50hp32l",trans);
			String Boat200 = dataTable.getData("UMBR_PolicyLevelCoverage", "Boat200",trans);
			String Boat50hp39l = dataTable.getData("UMBR_PolicyLevelCoverage", "Boat50hp39l",trans);
			String Sailboat26 = dataTable.getData("UMBR_PolicyLevelCoverage", "Sailboat26",trans);
			String Sailboat32 = dataTable.getData("UMBR_PolicyLevelCoverage", "Sailboat32",trans);
			String Boat39 = dataTable.getData("UMBR_PolicyLevelCoverage", "Boat39",trans);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPersonalWatercrafts')]")).click();
			actions.sendKeys(JetSki).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPowerboats50to200HPLessThan32ft')]")).click();
			actions.sendKeys(Boat50hp32l).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPowerboatsOver200HPLessThan32ft')]")).click();
			actions.sendKeys(Boat200).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPowerboatsOver50HP32to39ft')]")).click();
			actions.sendKeys(Boat50hp39l).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtSailboats26to32ft')]")).click();
			actions.sendKeys(Sailboat26).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtSailboats32to39ft')]")).click();
			actions.sendKeys(Sailboat32).perform();
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtBoatsOver39upto45ft')]")).click();
			actions.sendKeys(Boat39).perform();
			Thread.sleep(250);
		}
		
		//Other Exposures
		new Select(driver.findElement(By.id("ctl00_Content_uxddlHomeBusiness"))).selectByVisibleText(BusinessExp);
		Thread.sleep(250);
		
		if (HomeBusLocs.equalsIgnoreCase("YES")) {
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtHomeBusinessLocations')]")).click();
		actions.sendKeys(HomeBusLocs).perform();
		Thread.sleep(250);
		}
		
		new Select(driver.findElement(By.id("ctl00_Content_uxddlFarming"))).selectByVisibleText(FarmExp);
		Thread.sleep(250);
		
		if (FarmLocs.equalsIgnoreCase("YES")) {
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtFarmLocations')]")).click();
		actions.sendKeys(FarmLocs).perform();
		Thread.sleep(250);
		}
	
		//Quote Now
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		//Rating Information
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'ctl00_uxbtnNavigateNext')]")));
		
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'ctl00_Content_uxtxtPolicyNumber')]")));
	}
	
	/*******************************************************************************************************************************
	 *  Function Automated	-	Underlying Auto Information
	 *  Function Description	-	Enter Auto Policy Detail
	 *  Author	-	jprak
	 *  Script Created on	-	03/07/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	//Appended gq before method name in order to differentiate from Diamond - vbabu on 02/05/2018
	
	public void gqumbrAutoPolicyDetail(String trans) throws Exception
	{
		
		String AutoPolicyNum = dataTable.getData("AutoPolicyDetail", "AutoPolicyNum",trans);
		String AutoCarrier = dataTable.getData("AutoPolicyDetail", "AutoCarrier",trans);
		String OtherAutoCarrier = dataTable.getData("AutoPolicyDetail", "OtherAutoCarrier",trans);
		String SplitBI = dataTable.getData("AutoPolicyDetail", "SplitBI",trans);
		String SplitPD = dataTable.getData("AutoPolicyDetail", "SplitPD",trans);
		String CSLLimit = dataTable.getData("AutoPolicyDetail", "CSLLimit",trans);
		
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPolicyNumber')]")).click();
		actions.sendKeys(AutoPolicyNum).perform();
		actions.sendKeys(Keys.TAB).perform();
		Thread.sleep(250);
	
		new Select(driver.findElement(By.id("ctl00_Content_uxddlCarrier"))).selectByVisibleText(AutoCarrier);
		Thread.sleep(250);
		
		if (OtherAutoCarrier.isEmpty() == false){
	
			driver.findElement(By.id("ctl00_Content_uxtxtOtherCarrier")).click();
			actions.sendKeys(OtherAutoCarrier).perform();
		
			new Select(driver.findElement(By.id("ctl00_Content_uxddlSplitBI"))).selectByVisibleText(SplitBI);
			Thread.sleep(250);
		
			new Select(driver.findElement(By.id("ctl00_Content_uxddlSplitLimitPD"))).selectByVisibleText(SplitPD);
			Thread.sleep(250);
		
			new Select(driver.findElement(By.id("ctl00_Content_uxddlCSLLimit"))).selectByVisibleText(CSLLimit);
			Thread.sleep(250);
			}
			
		//else{	
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_uxlnkSave")));
		driver.findElement(By.id("ctl00_Content_uxlnkSave")).click();
		//}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_uxbtnNavigateNext")));
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_uxtxtFirstName")));
	
	}
	
	/*******************************************************************************************************************************
	 *  Function Automated	-	Household Residents Not on an Auto Policy
	 *  Function Description	-	Enter Household Resident Detail
	 *  Author	-	jprak
	 *  Script Created on	-	03/07/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	//Appended gq before method name in order to differentiate from Diamond - vbabu on 02/05/2018
	
	public void gqumbrHouseholdResidentDetail(String trans) throws Exception
	{
		String HRD_First = dataTable.getData("HouseholdResidentDetail", "HRD_First",trans);
		String HRD_Last = dataTable.getData("HouseholdResidentDetail", "HRD_Last",trans);
		String HRD_DOB = dataTable.getData("HouseholdResidentDetail", "HRD_DOB",trans);
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtFirstName')]")).click();
		actions.sendKeys(HRD_First).perform();
		Thread.sleep(250);
	
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLastName')]")).click();
		actions.sendKeys(HRD_Last).perform();
		Thread.sleep(250);
	
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxdeBirthDate_txtDate')]")).click();
		actions.sendKeys(HRD_DOB).perform();
		Thread.sleep(250);
		
		driver.findElement(By.id("ctl00_Content_uxlnkSave")).click();
		
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'ctl00_Content_uxtxtPolicyNumber')]")));
	
	} 
	
	/*******************************************************************************************************************************
	 *  Function Automated	-	Underlying Homeowner Information
	 *  Function Description	-	Enter Homeowner Policy Detail
	 *  Author	-	jprak
	 *  Script Created on	-	03/07/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	//Appended gq before method name in order to differentiate from Diamond - vbabu on 02/05/2018
	
	public void gqumbrHomeownerPolicyDetail(String trans) throws Exception
	{
		String HomePolicyNum = dataTable.getData("HomePolicyDetail", "HomePolicyNum",trans);
		String OtherHomeCarrier = dataTable.getData("HomePolicyDetail", "OtherHomeCarrier",trans);
		String PolicyType = dataTable.getData("HomePolicyDetail", "PolicyType",trans);
		String ResidenceType = dataTable.getData("HomePolicyDetail", "ResidenceType",trans);
		String LiabilityLimit = dataTable.getData("HomePolicyDetail", "LiabilityLimit",trans);
		String StreetNum = dataTable.getData("HomePolicyDetail", "StreetNum",trans);
		String StreetName = dataTable.getData("HomePolicyDetail", "StreetName",trans);
		String AptUnit = dataTable.getData("HomePolicyDetail", "AptUnit",trans);
		String City = dataTable.getData("HomePolicyDetail", "City",trans);
		String State = dataTable.getData("HomePolicyDetail", "State",trans);
		String ZipCode = dataTable.getData("HomePolicyDetail", "ZipCode",trans);
		
		String Home_ResPrem = dataTable.getData("HomePolicyDetail", "Home_ResPrem",trans);
		String Home_AddLoc = dataTable.getData("HomePolicyDetail", "Home_AddLoc",trans);
		String Farm_ResPrem = dataTable.getData("HomePolicyDetail", "Farm_ResPrem",trans);
		String Farm_AddLoc = dataTable.getData("HomePolicyDetail", "Farm_AddLoc",trans);
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPolicyNumber')]")).click();
		actions.sendKeys(HomePolicyNum).perform();
		//driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		actions.sendKeys(Keys.TAB).perform();
		Thread.sleep(500);
		
		
		if (PolicyType.isEmpty()== false){
		
			new Select(driver.findElement(By.id("ctl00_Content_uxddlFormType"))).selectByVisibleText(PolicyType);
			Thread.sleep(250);
			
			driver.findElement(By.id("ctl00_Content_uxtxtOtherCarrier")).click();
			actions.sendKeys(OtherHomeCarrier).perform();
			Thread.sleep(250);
		
			new Select(driver.findElement(By.id("ctl00_Content_uxddlResidenceType"))).selectByVisibleText(ResidenceType);
			Thread.sleep(250);
		
			new Select(driver.findElement(By.id("ctl00_Content_uxddlHomeownersLiability"))).selectByVisibleText(LiabilityLimit);
			Thread.sleep(250);
		
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtStreetNumber')]")).click();
			actions.sendKeys(StreetNum).perform();
		
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtStreetName')]")).click();
			actions.sendKeys(StreetName).perform();
		
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtAptUnit')]")).click();
			actions.sendKeys(AptUnit).perform();
		
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtCityName')]")).click();
			actions.sendKeys(City).perform();
		
			new Select(driver.findElement(By.id("ctl00_Content_uxddlState"))).selectByVisibleText(State);
			Thread.sleep(250);
		
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPostalCode')]")).click();
			actions.sendKeys(ZipCode).perform();
			
			if (Home_ResPrem.equalsIgnoreCase("Yes"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxchkHomeBusiness')]")).click();
			}
			
			if (Home_AddLoc.equalsIgnoreCase("Yes"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxchkHomeBusinessAddLocation')]")).click();
			}
			
			if (Farm_ResPrem.equalsIgnoreCase("Yes"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxchkFarmLiability')]")).click();
			}
			
			if (Farm_AddLoc.equalsIgnoreCase("Yes"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxchkFarmAdditionalLocation')]")).click();
			}
				
		}	
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_uxlnkSave")));
		driver.findElement(By.xpath(".//*[@id='ctl00_Content_uxlnkSave']")).click();
		Thread.sleep(250);
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_uxbtnNavigateNext")));
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_uxtxtPolicyNumber")));
	}

	/*******************************************************************************************************************************
	 *  Function Automated	-	Underlying Dwelling Fire Information
	 *  Function Description	-	Enter Dwelling Fire Policy Detail
	 *  Author	-	jprak
	 *  Script Created on	-	03/07/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	//Appended gq before method name in order to differentiate from Diamond - vbabu on 02/05/2018
	
	public void gqumbrDwellingFirePolicyDetail(String trans) throws Exception
	{
		String DFSPolicyNum = dataTable.getData("DwellingFireDetail", "DFSPolicyNum",trans);
		String OtherDFSCarrier = dataTable.getData("DwellingFireDetail", "OtherDFSCarrier",trans);
		String DFSLiabilityLimit = dataTable.getData("DwellingFireDetail", "DFSLiabilityLimit",trans);
		
		driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtPolicyNumber')]")).click();
		actions.sendKeys(DFSPolicyNum).perform();
		actions.sendKeys(Keys.TAB).perform();
		Thread.sleep(250);
		
		if (OtherDFSCarrier.isEmpty()== false){
			
			driver.findElement(By.id("ctl00_Content_uxtxtOtherCarrier")).click();
			actions.sendKeys(OtherDFSCarrier).perform();
			Thread.sleep(250);
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddlRentalLiability"))).selectByVisibleText(DFSLiabilityLimit);
			Thread.sleep(250);
		}
		
		driver.findElement(By.id("ctl00_Content_uxlnkSave")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_uxbtnNavigateNext")));
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_uxbtnNavigateNext")));
	
	}
	
	/*******************************************************************************************************************************
	 *  Function Automated	-	Recreational Vehicles and Watercraft
	 *  Function Description	-	Enter Recreational Vehicles and Watercraft Detail
	 *  Author	-	jprak
	 *  Script Created on	-	03/07/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	 
	//Appended gq before method name in order to differentiate from Diamond - vbabu on 02/05/2018
	
	public void gqumbrRecreational(String trans) throws Exception
	{
		
		String RV_Year = dataTable.getData("RV_Watercraft","RV_Year",trans);
		String RV_Make = dataTable.getData("RV_Watercraft","RV_Make",trans);
		String RV_Model = dataTable.getData("RV_Watercraft","RV_Model",trans);
		String RV_Type = dataTable.getData("RV_Watercraft","RV_Type",trans);
		String RV_UnderlyingPol = dataTable.getData("RV_Watercraft","RV_UnderlyingPol",trans);
		
		String WC_Year = dataTable.getData("RV_Watercraft","WC_Year",trans);
		String WC_Make = dataTable.getData("RV_Watercraft","WC_Make",trans);
		String WC_Model = dataTable.getData("RV_Watercraft","WC_Model",trans);
		String WC_Type = dataTable.getData("RV_Watercraft","WC_Type",trans);
		String WC_Length = dataTable.getData("RV_Watercraft","WC_Length",trans);
		String WC_Horsepower = dataTable.getData("RV_Watercraft","WC_Horsepower",trans);
		String WC_UnderlyingPol = dataTable.getData("RV_Watercraft","WC_UnderlyingPol",trans);
		
		
		if (RV_Year.isEmpty()== false){

			driver.findElement(By.id("ctl00_uxlnkAddRecreationalVehicle")).click();
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtYear')]")).click();
			actions.sendKeys(RV_Year).perform();
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtMake')]")).click();
			actions.sendKeys(RV_Make).perform();
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtModel')]")).click();
			actions.sendKeys(RV_Model).perform();
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddlVehicleType"))).selectByVisibleText(RV_Type);
			Thread.sleep(250);
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddlPolicyForRecreationalVehicle"))).selectByVisibleText(RV_UnderlyingPol);
			Thread.sleep(250);
			
			driver.findElement(By.id("ctl00_Content_uxlnkSave")).click();
			}	
			
			if (WC_Year.isEmpty()== false){

			driver.findElement(By.id("ctl00_uxlnkAddWatercraft")).click();
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtWatercraftYear')]")).click();
			actions.sendKeys(WC_Year).perform();
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtWatercraftMake')]")).click();
			actions.sendKeys(WC_Make).perform();
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtWatercraftModel')]")).click();
			actions.sendKeys(WC_Model).perform();
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddlWatercraftType"))).selectByVisibleText(WC_Type);
			Thread.sleep(250);
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtLength')]")).click();
			actions.sendKeys(WC_Length).perform();
			
			driver.findElement(By.xpath("//input[contains(@id,'ctl00_Content_uxtxtHorsepower')]")).click();
			actions.sendKeys(WC_Horsepower).perform();
			
			new Select(driver.findElement(By.id("ctl00_Content_uxddlPolicyForWatercraft"))).selectByVisibleText(WC_UnderlyingPol);
			Thread.sleep(250);
			
			driver.findElement(By.id("ctl00_Content_uxlnkSave")).click();
			}
			
		
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl00_uxddlResponseYesNo")));
		
	}
	 
	 /*******************************************************************************************************************************
	 *  Function Automated	-	Underwriting Questions
	 *  Function Description	-	Enter Underwriting Questions Answers
	 *  Author	-	jprak
	 *  Script Created on	-	03/07/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	//Appended gq before method name in order to differentiate from Diamond - vbabu on 02/05/2018
	
	 public void gqumbrUnderwritingQuestions(String trans) throws Exception
	{
		String Losses = dataTable.getData("UnderwritingQuestions","Losses",trans);
		String Convictions = dataTable.getData("UnderwritingQuestions","Convictions",trans);
		String Violations = dataTable.getData("UnderwritingQuestions","Violations",trans);
		String Litigation = dataTable.getData("UnderwritingQuestions","Litigation",trans);
		String Declined = dataTable.getData("UnderwritingQuestions","Declined",trans);
		String Impairment = dataTable.getData("UnderwritingQuestions","Impairment",trans);
		String NotCovered = dataTable.getData("UnderwritingQuestions","NotCovered",trans);
		String NonCompPos = dataTable.getData("UnderwritingQuestions","NonCompPos",trans);
		String ReducedLimits = dataTable.getData("UnderwritingQuestions","ReducedLimits",trans);
		String ExoticAnimals = dataTable.getData("UnderwritingQuestions","ExoticAnimals",trans);
		String DangerousAnimals = dataTable.getData("UnderwritingQuestions","DangerousAnimals",trans);
		
		 
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl00_uxddlResponseYesNo"))).selectByVisibleText(Losses);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl01_uxddlResponseYesNo"))).selectByVisibleText(Convictions);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl02_uxddlResponseYesNo"))).selectByVisibleText(Violations);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl03_uxddlResponseYesNo"))).selectByVisibleText(Litigation);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl04_uxddlResponseYesNo"))).selectByVisibleText(Declined);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl05_uxddlResponseYesNo"))).selectByVisibleText(Impairment);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl06_uxddlResponseYesNo"))).selectByVisibleText(NotCovered);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl07_uxddlResponseYesNo"))).selectByVisibleText(NonCompPos);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl08_uxddlResponseYesNo"))).selectByVisibleText(ReducedLimits);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl09_uxddlResponseYesNo"))).selectByVisibleText(ExoticAnimals);
		Thread.sleep(250);
		
		new Select(driver.findElement(By.id("ctl00_Content_UnderwritingQuestions1_uxrptQuestions_ctl10_uxddlResponseYesNo"))).selectByVisibleText(DangerousAnimals);
		Thread.sleep(250);
		
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		
		if (!driver.findElements(By.xpath("//input[contains(@id,'ctl00_uxbtnNavigateNext')]")).isEmpty()) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'ctl00_uxbtnNavigateNext')]")));
		}else{
			String underwritingMessage = "Please contact your Underwriter for approval:";
			String message = driver.findElement(By.id("ctl00_Content_uxlblUnderwritingMessages")).getText();
			Assert.assertTrue(message.contains(underwritingMessage));	
		}
	}
	 
	 /*******************************************************************************************************************************
	 *  Function Automated		-	submit 
	 *  Function Description	-	To submit the application in GO Quote
	 *  Author			-	jprak
	 *  Script Created on		-	03/07/2016
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void gq_Submit() throws Exception
	{
	
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_uxbtnNavigateNext")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_cboPayPlan")));
		
		new Select(driver.findElement(By.id("ctl00_Content_cboPayPlan"))).selectByVisibleText("1 Pay");
		Thread.sleep(250);
		driver.findElement(By.id("ctl00_uxbtnNavigateNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Content_uxSummaryView_uxlblPolicyNumber")));
		String Policy;
		Policy=driver.findElement(By.id("ctl00_Content_uxSummaryView_uxlblPolicyNumber")).getText();
		dataTable.putData("General_Data", "PolicyNumber_NewBusiness", Policy) ;
		dataTable.putPolicyData("General_Data", "PolicyNumber_NewBusiness", Policy) ;
		String gqState=dataTable.getData("General_Data", "State");
		
		dataTable.putGQPolicyData(gqState, "General_Data", "PolicyNumber_NewBusiness", Policy) ;
		
	}
	 /*******************************************************************************************************************************
		 *  Function Automated		-	ApplicantInfo
		 *  Function Description	-	To Add Applicant information
		 *  Author			-	mvijayakumar
		 *  Script Created on		-	04/18/2018
		 * @throws Exception
		 *******************************************************************************************************************************/
	
	public void applicantInfo(String trans) throws Exception
	{
		//*[@id="P_L_V_DetailTreeViewn0Nodes"]/table[3]/tbody/tr/td[3]/span/a[2]
		String Policyholder2 =dataTable.getData("Applicant_Info", "Policyholder2", trans);
		String Operation =  dataTable.getData("Applicant_Info", "Operation", trans);
		String SSN = dataTable.getData("Applicant_Info", "SSN", trans);
		String Gender =dataTable.getData("Applicant_Info", "Gender", trans);
		String MaritalStatus =dataTable.getData("Applicant_Info", "Mar Status", trans);
		String Birthdate =dataTable.getData("Applicant_Info", "Birth date", trans);
		String Relationship =dataTable.getData("Applicant_Info", "Relationship", trans);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='P_L_V_DetailTreeViewn0Nodes']/table[3]/tbody/tr/td[3]/span/a[2]")));	
	driver.findElement(By.xpath("//*[@id='P_L_V_DetailTreeViewn0Nodes']/table[3]/tbody/tr/td[3]/span/a[2]")).click();
	Thread.sleep(1000);
	//*[@id="P_L_V_v41w22_t16_c0w0_t0_PolicyHolder1RadioButton"]
	//*[@id="SelectNameToolStripButtonMiddle"]/a
	//*[@id="SelectNameToolStripButtonMiddle"]/a
	driver.findElement(By.id("SelectNameToolStripButtonMiddle")).click();
	Thread.sleep(3000);
	//*[@id="SaveToolStripButtonMiddle"]/a
	//*[@id="SaveToolStripButtonMiddle"]/a
	driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
	Thread.sleep(1000);
	
	//*[@id="P_L_V_v41w22_t16_c0w0_t0_PolicyHolder2RadioButton"]
	if(Policyholder2.equalsIgnoreCase("YES")==true)
	{
	if(Operation.equalsIgnoreCase("ADD")==true)
	{
		driver.findElement(By.id("AddApplicantToolStripButtonMiddle")).click();
		Thread.sleep(1000);
	}
	if(driver.findElement(By.xpath("//input[contains(@id,'PolicyHolder2RadioButton')]")).isSelected()==true)
	{
		driver .findElement(By.xpath("//*[@id='SelectNameToolStripButtonMiddle']/a")).click();
		Thread.sleep(3000);
		
		if(SSN.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t16_c0w0_t1i0_ApplicantInsName_PersonalTaxNumber_PersonalTaxNumber"]
			String SSNdefaultVal = driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_PersonalTaxNumber_PersonalTaxNumber')]")).getAttribute("value");
			do {
				//*[@id="P_L_V_v22w22_t16_c0w0_t1i1_ApplicantInsName_PersonalTaxNumber_PersonalTaxNumber"]
				driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_PersonalTaxNumber_PersonalTaxNumber')]")).click();
				driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_PersonalTaxNumber_PersonalTaxNumber')]")).sendKeys(Keys.CONTROL, "a");
				Thread.sleep(500);
				driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_PersonalTaxNumber_PersonalTaxNumber')]")).sendKeys(SSN);
				SSNdefaultVal = driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_PersonalTaxNumber_PersonalTaxNumber')]")).getAttribute("value");
				SSNdefaultVal = SSNdefaultVal.replaceAll("-", "");
			} while (SSNdefaultVal.equalsIgnoreCase(SSN) == false);	
		}
		if(Gender.isEmpty()==false)
		{
		//*[@id="P_L_V_v22w22_t16_c0w0_t1i1_ApplicantInsName_Sex_D_I"]
		String defGender="";
		do {
		actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_Sex_D_I')]"))).click().perform();
		Thread.sleep(250);
		//actions.sendKeys(Gender).build().perform();
		if(Gender.equalsIgnoreCase("M Male")==true)
		{
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(250);
		
		actions.sendKeys(Keys.ENTER).build().perform();
		}
		else if(Gender.equalsIgnoreCase("F Female")==true)
		{
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(250);
			actions.sendKeys(Keys.ENTER).build().perform();
		}
		
		defGender=driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_Sex_D_I')]")).getAttribute("value");
		Thread.sleep(250);
		
		} while (defGender.equalsIgnoreCase(Gender)==false);
		}
		
		if(MaritalStatus.isEmpty()==false)
		{
			//*[@id="P_L_V_v22w22_t16_c0w0_t1i1_ApplicantInsName_MaritalStatus_D_I"]
			//*[@id="P_L_V_v41w22_t16_c0w0_t1i0_ApplicantInsName_MaritalStatus_D_I"]
			String defMarStatus="";
			do
			{
			actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_MaritalStatus_D_I')]"))).click().perform();
			Thread.sleep(200);
			actions.sendKeys(MaritalStatus).build().perform();
			Thread.sleep(250);
			actions.sendKeys(Keys.ENTER).build().perform();
			defMarStatus=driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_MaritalStatus_D_I')]")).getAttribute("value");
			if(defMarStatus.equalsIgnoreCase(MaritalStatus)==false)
			{
				actions.sendKeys(Keys.ARROW_DOWN).build().perform();
				Thread.sleep(250);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			Thread.sleep(250);
			}while(defMarStatus.equalsIgnoreCase(MaritalStatus)==false);
			}
		
		
		if(Birthdate.isEmpty()==false)
		{
			//*[@id="P_L_V_v22w22_t16_c0w0_t1i1_ApplicantInsName_BirthDate_BirthDate_I"]
			Birthdate= Birthdate.replaceAll("/", "");
			String DoBdefaultVal = driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_BirthDate_BirthDate_I')]")).getAttribute("value");
			System.out.println(DoBdefaultVal);
			do {
				driver.findElement(By.xpath("//input[contains(@id,'_ApplicantInsName_BirthDate_BirthDate_I')]")).sendKeys(Birthdate);
				driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_BirthDate_BirthDate_I')]")).sendKeys(Keys.CONTROL, "a");
				Thread.sleep(500);
				driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_BirthDate_BirthDate_I')]")).sendKeys(Birthdate);
				DoBdefaultVal = driver.findElement(By.xpath("//*[contains(@id,'_ApplicantInsName_BirthDate_BirthDate_I')]")).getAttribute("value");
				DoBdefaultVal = DoBdefaultVal.replaceAll("/", "");
				System.out.println(DoBdefaultVal);
			} while (DoBdefaultVal.equalsIgnoreCase(Birthdate) == false);
		}
		if(Relationship.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t16_c0w0_t1i1_RelationshipTypeInsCombo_D_I"]
			driver.findElement(By.xpath("//input[contains(@id,'_RelationshipTypeInsCombo_D_I')]")).click();
			actions.sendKeys(Relationship).build().perform();
			Thread.sleep(1000);
		}
		
		driver.findElement(By.xpath("//*[@id='SaveToolStripButtonMiddle']/a")).click();
		Thread.sleep(1000);
		 report.updateTestLog("Applicant Inforamtion", "Applicant information updated successfuly", Status.DONE);
		 report.updateTestLog("Applicant Inforamtion", "Applicant information updated successfuly", Status.SCREENSHOT);
	}
	}
	}
	
	
	 /*******************************************************************************************************************************
	 *  Function Automated		-	ApplicantInfo
	 *  Function Description	-	To Add Applicant information
	 *  Author			-	mvijayakumar
	 *  Script Created on		-	05/07/2019
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void policyInfo(String trans) throws Exception
	{
		//*[@id="P_L_V_v41w22_t14_PolicyholderTwoLink"]
		//*[@id="P_L_V_v21w22_t14_PolicyholderTwoLink"]
		driver.findElement(By.xpath("//*[contains(@id,'w22_t14_PolicyholderTwoLink')]")).click();
		Thread.sleep(1000);
		
		String FirstName = dataTable.getData("Policy_Info", "FirstName",trans);
		String MiddleName = dataTable.getData("Policy_Info", "MiddleName",trans);
		String LastName = dataTable.getData("Policy_Info", "LastName",trans);
		String LegalEntity = dataTable.getData("Policy_Info", "Legal Entity",trans);
		String ThirdPartyDesignees = dataTable.getData("Policy_Info", "Third Party Designees",trans);
		String FirstNameTP = dataTable.getData("Policy_Info", "First",trans);
		String MiddleNameTP = dataTable.getData("Policy_Info", "Middle",trans);
		String LastNameTP = dataTable.getData("Policy_Info", "Last",trans);
		String StreetNumber = dataTable.getData("Policy_Info", "StreetNumber",trans);
		String Street = dataTable.getData("Policy_Info", "Street",trans);
		String City = dataTable.getData("Policy_Info", "City",trans);
		String State = dataTable.getData("Policy_Info", "State",trans);
		String Zip = dataTable.getData("Policy_Info", "Zip",trans);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'_c0w0_t3_ExpandedNameControl_First')]")));
		
		if(FirstName.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t3_ExpandedNameControl_First"]
			//*[@id="P_L_V_v21w22_t14_c0w0_t3_ExpandedNameControl_First"]
			driver.findElement(By.xpath("//input[contains(@id,'_c0w0_t3_ExpandedNameControl_First')]")).click();
			actions.sendKeys(FirstName).build().perform();
			Thread.sleep(1000);
			
		}
		if(MiddleName.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t3_ExpandedNameControl_Middle"]
			driver.findElement(By.xpath("//input[contains(@id,'_c0w0_t3_ExpandedNameControl_Middle')]")).click();
			actions.sendKeys(MiddleName).build().perform();
			Thread.sleep(1000);
			
		}
		if(LastName.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t3_ExpandedNameControl_Last"]
			driver.findElement(By.xpath("//input[contains(@id,'c0w0_t3_ExpandedNameControl_Last')]")).click();
			actions.sendKeys(LastName).build().perform();
			Thread.sleep(1000);
			
		}
		if(LegalEntity.isEmpty()==false)
		{
			//*[@id="P_L_V_v22w22_t14_c0w0_t3_ExpandedNameControl_PersonalLegalEntityTypeCombo_D_I"]
			driver.findElement(By.xpath("//*[contains(@id, '_ExpandedNameControl_PersonalLegalEntityTypeCombo_D_I')]")).click();
			actions.sendKeys(LegalEntity).build().perform();
			Thread.sleep(1000);
			
		}
		driver.findElement(By.id("SaveInsImageButtonMiddle")).click();
		Thread.sleep(3000);
		//*[@id="P_L_V_v41w22_t14_ThirdPartyDesigneeLink"]
		
	
		if(ThirdPartyDesignees.equalsIgnoreCase("ADD")==true)
		{//*[@id="P_L_V_v21w22_t14_ThirdPartyDesigneeLink"]
			//*[@id="P_L_V_v26w22_t14_ThirdPartyDesigneeLink"]
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'_ThirdPartyDesigneeLink')]")));	
		driver.findElement(By.xpath("//*[contains(@id,'_ThirdPartyDesigneeLink')]")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.linkText("Add")).click();
		Thread.sleep(1000);
		
		if(FirstNameTP.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_First"]
			driver.findElement(By.xpath("//input[contains(@id,'c0w0_t6_c0w0_t0_MyInsName_First')]")).click();
			actions.sendKeys(FirstNameTP).build().perform();
			Thread.sleep(1000);
		}
		if(MiddleNameTP.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_Middle"]
			driver.findElement(By.xpath("//input[contains(@id,'_MyInsName_Middle')]")).click();
			actions.sendKeys(MiddleNameTP).build().perform();
			Thread.sleep(1000);
		}
		if(LastNameTP.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_Last"]
			driver.findElement(By.xpath("//input[contains(@id,'_MyInsName_Last')]")).click();
			actions.sendKeys(LastNameTP).build().perform();
			Thread.sleep(1000);
		}
		if(StreetNumber.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_HouseNumber"]
			driver.findElement(By.xpath("//input[contains(@id,'_MyInsName_HouseNumber')]")).click();
			actions.sendKeys(StreetNumber).build().perform();
			Thread.sleep(1000);
		}
		if(Street.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_StreetName"]
			driver.findElement(By.xpath("//input[contains(@id,'0_MyInsName_StreetName')]")).click();
			actions.sendKeys(Street).build().perform();
			Thread.sleep(1000);
		}
		if(City.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_City"]
			driver.findElement(By.xpath("//input[contains(@id,'_MyInsName_City')]")).click();
			actions.sendKeys(City).build().perform();
			Thread.sleep(1000);
		}
		if(State.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_AddressState_D_I"]
			driver.findElement(By.xpath("//input[contains(@id,'_MyInsName_AddressState_D_I')]")).click();
			actions.sendKeys(State).build().perform();
			Thread.sleep(1000);
			actions.sendKeys(Keys.TAB).perform();
	        Thread.sleep(1000);
		}
		if(Zip.isEmpty()==false)
		{
			actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id,'0_MyInsName_ZipCode_mtxtMain')]"))).click().perform();
	        driver.findElement(By.xpath("//input[contains(@id,'0_MyInsName_ZipCode_mtxtMain')]")).clear();
	        Thread.sleep(1000);
			actions.sendKeys(Zip).perform();
			actions.build();
	        actions.perform();
	        Thread.sleep(1000);
			//*[@id="P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_ZipCode_mtxtMain"]
			//driver.findElement(By.id("P_L_V_v41w22_t14_c0w0_t6_c0w0_t0_MyInsName_ZipCode_mtxtMain")).click();
			//actions.sendKeys(Zip).build().perform();
			//Thread.sleep(1000);
		}
		//*[@id="SaveToolStripButtonMiddle"]/a
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(5000);
		//*[@id="P_L_V_DetailTreeViewn0Nodes"]/table[2]/tbody/tr/td[3]/span/a[2]
		driver.findElement(By.xpath("//*[@id='P_L_V_DetailTreeViewn0Nodes']/table[2]/tbody/tr/td[3]/span/a[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='P_L_V_MyInsMessageBox_MyASPxPopupControl_YesInsMessageBoxButton_CD']/span")).click();
		Thread.sleep(2000);
		}
		report.updateTestLog("Policy Inforamtion", "Policy information updated successfuly", Status.DONE);
	}
	
	
	
	
	
	
	 /*******************************************************************************************************************************
	 *  Function Automated		-	UnderlyingPolicices
	 *  Function Description	-	To Add Underlying Policy Coverages
	 *  Author			-	vbabu
	 *  Script Created on		-	02/06/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
	
	public void underlyingPolicies(String trans) throws Exception
	{
		String Operation = dataTable.getData("UnderlyingPolicies","Operation",trans);
		String PolicyType = dataTable.getData("UnderlyingPolicies","PolicyType",trans);
		String Ucarrier = dataTable.getData("UnderlyingPolicies","Ucarrier",trans);
		String OtherCarrier = dataTable.getData("UnderlyingPolicies","OtherCarrier",trans);
		String PolNumber = dataTable.getData("UnderlyingPolicies","PolicyNumber",trans);
		String EffDate = dataTable.getData("UnderlyingPolicies","EffDate",trans);
		String ExpDate = dataTable.getData("UnderlyingPolicies","ExpDate",trans);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Underlying Policies")));
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Underlying Policies")).click();
		Thread.sleep(1000);
		
		if(Operation.equalsIgnoreCase("ADD"))
		{
			//*[@id="AddUnderlyingPolicyToolStripButtonMiddle"]/a
		driver.findElement(By.id("AddUnderlyingPolicyToolStripButtonMiddle")).click();
		Thread.sleep(1500);
	
		}
		
		if(Operation.equalsIgnoreCase("EDIT"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_UnderlyingPolicyInsDataGridView_EditImageButton_0')]")).click();
			Thread.sleep(1000);
			
		}
		if(PolicyType.isEmpty()==false ||Operation.equalsIgnoreCase("ADD") )
		{
		driver.findElement(By.xpath("//input[contains(@id,'_LobInsCombo_D_I')]")).click();
		actions.sendKeys(PolicyType).perform();
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(2500);
		}
		
		if(Ucarrier.isEmpty()==false)
		{
		driver.findElement(By.xpath("//input[contains(@id,'_CompanyTypeInsCombo_D_I')]")).click();
		actions.sendKeys(Ucarrier).perform();
		Thread.sleep(300);
		actions.sendKeys(Keys.TAB).build().perform();
		}
		
		if(OtherCarrier.isEmpty()==false)
		{
		driver.findElement(By.xpath("//input[contains(@id,'_CompanyInsTextbox')]")).click();
		actions.sendKeys(OtherCarrier).perform();
		Thread.sleep(300);
		}
		
		if(PolNumber.isEmpty()==false)
		{
		driver.findElement(By.xpath("//input[contains(@id,'_PolicyNumberInsTextBox')]")).click();
		actions.sendKeys(PolNumber).perform();
		Thread.sleep(300);
		}
		
		if(EffDate.isEmpty()==false)
		{
		//driver.findElement(By.xpath("//input[contains(@id,'_EffectiveDateInsDateTime_EffectiveDateInsDateTime')]")).click();
		//actions.sendKeys(EffDate).perform();
		//Thread.sleep(300);
		EffDate= EffDate.replaceAll("/", "");
		String defaultVal = driver.findElement(By.xpath("//input[contains(@id,'_EffectiveDateInsDateTime_EffectiveDateInsDateTime')]")).getAttribute("value");
		do {
			driver.findElement(By.xpath("//input[contains(@id,'_EffectiveDateInsDateTime_EffectiveDateInsDateTime')]")).sendKeys(EffDate);
			driver.findElement(By.xpath("//input[contains(@id,'_EffectiveDateInsDateTime_EffectiveDateInsDateTime')]")).sendKeys(Keys.CONTROL, "a");
			Thread.sleep(500);
			driver.findElement(By.xpath("//input[contains(@id,'_EffectiveDateInsDateTime_EffectiveDateInsDateTime')]")).sendKeys(EffDate);
			defaultVal = driver.findElement(By.xpath("//input[contains(@id,'_EffectiveDateInsDateTime_EffectiveDateInsDateTime')]")).getAttribute("value");
			defaultVal = defaultVal.replaceAll("/", "");
		} while (defaultVal.equalsIgnoreCase(EffDate) == false);
		}
		
		if(ExpDate.isEmpty()==false)
		{
		//driver.findElement(By.xpath("//input[contains(@id,'_ExpirationDateInsDateTime_ExpirationDateInsDateTime')]")).click();
		//actions.sendKeys(ExpDate).perform();
		//Thread.sleep(300);
		ExpDate= ExpDate.replaceAll("/", "");
		String defaultVal1 = driver.findElement(By.xpath("//input[contains(@id,'_ExpirationDateInsDateTime_ExpirationDateInsDateTime')]")).getAttribute("value");
		do {
			driver.findElement(By.xpath("//input[contains(@id,'_ExpirationDateInsDateTime_ExpirationDateInsDateTime')]")).sendKeys(ExpDate);
			driver.findElement(By.xpath("//input[contains(@id,'_ExpirationDateInsDateTime_ExpirationDateInsDateTime')]")).sendKeys(Keys.CONTROL, "a");
			Thread.sleep(500);
			driver.findElement(By.xpath("//input[contains(@id,'_ExpirationDateInsDateTime_ExpirationDateInsDateTime')]")).sendKeys(ExpDate);
			defaultVal1 = driver.findElement(By.xpath("//input[contains(@id,'_ExpirationDateInsDateTime_ExpirationDateInsDateTime')]")).getAttribute("value");
			defaultVal1 = defaultVal1.replaceAll("/", "");
		} while (defaultVal1.equalsIgnoreCase(ExpDate) == false);
		
		}
		report.updateTestLog("Underlying policy", "Underlying Policy information updated successfuly", Status.DONE);
		report.updateTestLog("Underlying policy", "Underlying Policy information updated successfuly", Status.SCREENSHOT);
	}
	/*******************************************************************************************************************************
	 *  Function Automated		-	Underlying Policies
	 *  Function Description	-	To Add Auto Personal
	 *  Author			-	mvijayakumar
	 *  Script Created on		-	04/17/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
	public void personalAuto(String trans) throws Exception
	{
		
			String AutoCSLLimit = dataTable.getData("PersonalAuto","AutoCSLLimit",trans);
			String SplintBILimit = dataTable.getData("PersonalAuto","SplintBILimit",trans);
			String SplitPDLimit = dataTable.getData("PersonalAuto","SplitPDLimit",trans);
			String DriverOperation = dataTable.getData("PersonalAuto","DriverOperation",trans);
			String VehicleOperation = dataTable.getData("PersonalAuto","VehicleOperation",trans);
			String RecVehOperation = dataTable.getData("PersonalAuto","RecVehOperation",trans);
			
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_modifier_id_37_3_14_37_D_I')]")));
			
			
			if(AutoCSLLimit.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_37_3_14_37_D_I')]")).click();
				actions.sendKeys(AutoCSLLimit).perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1500);
				
			}
			
			if(SplintBILimit.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_38_3_14_38_D_I')]")).click();
				actions.sendKeys(SplintBILimit).perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1500);
				
			}
			
			if(SplitPDLimit.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_39_3_14_39_D_I')]")).click();
				actions.sendKeys(SplitPDLimit).perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1500);
			
			}
			if(DriverOperation.isEmpty()==false)
			{
			if(DriverOperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddDriverToolStripButtonMiddle")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_InsName_First')]")));
			}
			
			if(DriverOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_DriverInsDataGridView_EditImageButton_0')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_InsName_First')]")));
			}
			
			
			
			String Firstname = dataTable.getData("PersonalAuto","Firstname",trans);
			String Lastname = dataTable.getData("PersonalAuto","Lastname",trans);
			String SSN = dataTable.getData("PersonalAuto","SSN",trans);
			String Gender = dataTable.getData("PersonalAuto","Gender",trans);
			String MarStatus = dataTable.getData("PersonalAuto","MarStatus",trans);
			String DLnumber = dataTable.getData("PersonalAuto","DLnumber",trans);
			String BirthDate = dataTable.getData("PersonalAuto","BirthDate",trans);
			
			if(Firstname.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_First')]")).click();
				actions.sendKeys(Firstname).perform();
				Thread.sleep(300);
			}
			
			if(Lastname.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_Last')]")).click();
				actions.sendKeys(Lastname).perform();
				Thread.sleep(300);
			}
			
			if(SSN.isEmpty()==false)
			{
			String SSNdefaultVal =driver.findElement(By.xpath("//input[contains(@id,'_PersonalTaxNumber_PersonalTaxNumber')]")).getAttribute("value");
			do {
				driver.findElement(By.xpath("//input[contains(@id,'_PersonalTaxNumber_PersonalTaxNumber')]")).click();
				driver.findElement(By.xpath("//input[contains(@id,'_PersonalTaxNumber_PersonalTaxNumber')]")).sendKeys(Keys.CONTROL, "a");
				Thread.sleep(500);
				driver.findElement(By.xpath("//input[contains(@id,'_PersonalTaxNumber_PersonalTaxNumber')]")).sendKeys(SSN);
				SSNdefaultVal = driver.findElement(By.xpath("//input[contains(@id,'_PersonalTaxNumber_PersonalTaxNumber')]")).getAttribute("value");
				SSNdefaultVal = SSNdefaultVal.replaceAll("-", "");
			} while (SSNdefaultVal.equalsIgnoreCase(SSN) == false);
			}
			
			/*if(Gender.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_Sex_D_I')]")).click();
				
				if(Gender.equalsIgnoreCase("M Male")==true)
				{
					actions.sendKeys(Keys.ARROW_DOWN).build().perform();
					Thread.sleep(250);
				//driver.findElement(By.id("P_L_ClientSubmissionWithAddressAndPOBox_Client1InsName_Sex_D_I")).sendKeys(Gender);
				actions.sendKeys(Keys.ENTER).build().perform();
				}
				
			}*/
			if(Gender.isEmpty()==false)
			{
			//*[@id="P_L_V_v22w22_t16_c0w0_t1i1_ApplicantInsName_Sex_D_I"]
			String defGender="";
			do {
			actions.moveToElement(driver.findElement(By.xpath("//*[contains(@id,'_InsName_Sex_D_I')]"))).click().perform();
			Thread.sleep(250);
			//actions.sendKeys(Gender).build().perform();
			if(Gender.equalsIgnoreCase("M Male")==true)
			{
				actions.sendKeys(Keys.ARROW_DOWN).build().perform();
				Thread.sleep(250);
			
			actions.sendKeys(Keys.ENTER).build().perform();
			}
			else if(Gender.equalsIgnoreCase("F Female")==true)
			{
				actions.sendKeys(Keys.ARROW_DOWN).build().perform();
				actions.sendKeys(Keys.ARROW_DOWN).build().perform();
				Thread.sleep(250);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			defGender=driver.findElement(By.xpath("//*[contains(@id,'_InsName_Sex_D_I')]")).getAttribute("value");
			Thread.sleep(250);
			
			} while (defGender.equalsIgnoreCase(Gender)==false);
			}
			
			
			if(MarStatus.isEmpty()==false){
				String defMarStatus="";
				do
				{
					driver.findElement(By.xpath("//input[contains(@id,'_InsName_MaritalStatus_D_I')]")).click();
				Thread.sleep(200);
				actions.sendKeys(MarStatus).build().perform();
				Thread.sleep(250);
				actions.sendKeys(Keys.ENTER).build().perform();
				defMarStatus=driver.findElement(By.xpath("//input[contains(@id,'_InsName_MaritalStatus_D_I')]")).getAttribute("value");
				if(defMarStatus.equalsIgnoreCase(MarStatus)==false)
				{
					actions.sendKeys(Keys.ARROW_DOWN).build().perform();
					Thread.sleep(250);
					actions.sendKeys(Keys.ENTER).build().perform();
				}
				Thread.sleep(250);
				}while(defMarStatus.equalsIgnoreCase(MarStatus)==false);
			}
			
			if(DLnumber.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_LicenseNumber_LicenseNumber')]")).click();
				actions.sendKeys(DLnumber).perform();
				Thread.sleep(300);	
			}
			
			if(BirthDate.isEmpty()==false){
			BirthDate= BirthDate.replaceAll("/", "");
			String DoBdefaultVal = driver.findElement(By.xpath("//input[contains(@id,'_InsName_BirthDate_BirthDate_I')]")).getAttribute("value");
			do {
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_BirthDate_BirthDate_I')]")).sendKeys(BirthDate);
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_BirthDate_BirthDate_I')]")).sendKeys(Keys.CONTROL, "a");
				Thread.sleep(500);
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_BirthDate_BirthDate_I')]")).sendKeys(BirthDate);
				DoBdefaultVal = driver.findElement(By.xpath("//input[contains(@id,'_InsName_BirthDate_BirthDate_I')]")).getAttribute("value");
				DoBdefaultVal = DoBdefaultVal.replaceAll("/", "");
			} while (DoBdefaultVal.equalsIgnoreCase(BirthDate) == false);
			
			}
			
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("AddVehicleImageButtonMiddle")));
		}
		
		
		if(VehicleOperation.isEmpty()==false)
		{
			String VehType = dataTable.getData("PersonalAuto","VehType",trans);
			String VYear = dataTable.getData("PersonalAuto","VYear",trans);
			String Vmake = dataTable.getData("PersonalAuto","Vmake",trans);
			String Vmodel = dataTable.getData("PersonalAuto","Vmodel",trans);
			String Vitems = dataTable.getData("PersonalAuto","Vitems",trans);
			
			if(VehicleOperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddVehicleImageButtonMiddle")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_VehicleTypeInsCombo_D_I')]")));
			}
			if(VehicleOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_VehicleInsDataGridView_EditImageButton_0')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_VehicleTypeInsCombo_D_I')]")));
			}
			if(VehType.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_VehicleTypeInsCombo_D_I')]")).click();
				actions.sendKeys(VehType).perform();
				Thread.sleep(300);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			if(VYear.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
				actions.sendKeys(VYear).perform();
				Thread.sleep(300);
			}
			
			if(Vmake.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MakeInsTextBox')]")).click();
				actions.sendKeys(Vmake).perform();
				Thread.sleep(300);
			}
			
			if(Vmodel.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_ModelInsTextBox')]")).click();
				actions.sendKeys(Vmodel).perform();
				Thread.sleep(300);
			}
			
			if(Vitems.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
				actions.sendKeys(Vitems).perform();
				Thread.sleep(300);
			}
			
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			//*[@id="SaveToolStripButtonMiddle"]
			
			//wait.until(ExpectedConditions.elementToBeClickable(By.id("AddRecVehicleToolStripButtonMiddle")));
		}
		
		if(RecVehOperation.isEmpty()==false)
		{
			String RecType = dataTable.getData("PersonalAuto","RecType",trans);
			String RecYear = dataTable.getData("PersonalAuto","RecYear",trans);
			String Recmake = dataTable.getData("PersonalAuto","Recmake",trans);
			String Recmodel = dataTable.getData("PersonalAuto","Recmodel",trans);
			String Recitems = dataTable.getData("PersonalAuto","Recitems",trans);
			
			if(RecVehOperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddRecVehicleToolStripButtonMiddle")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
			}
			
			if(RecVehOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleInsDataGridView_EditImageButton_0')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
			}
			
			if(RecType.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")).click();
				actions.sendKeys(RecType).perform();
				Thread.sleep(300);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			if(RecYear.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
				actions.sendKeys(RecYear).perform();
				Thread.sleep(300);
			}
			
			if(Recmake.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MakeInsTextBox')]")).click();
				actions.sendKeys(Recmake).perform();
				Thread.sleep(300);
			}
			
			if(Recmodel.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_ModelInsTextBox')]")).click();
				actions.sendKeys(Recmodel).perform();
				Thread.sleep(300);
			}
			
			if(Recitems.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
				actions.sendKeys(Recitems).perform();
				Thread.sleep(300);
			}
			
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			
		}
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		report.updateTestLog("Personal Auto", "Personal Auto information updated successfuly", Status.DONE);
		report.updateTestLog("Personal Auto", "Personal Auto information updated successfuly", Status.SCREENSHOT);
	}
	/*******************************************************************************************************************************
	 *  Function Automated		-	Underlying Policies
	 *  Function Description	-	To Add Watercraft Liability
	 *  Author			-	mvijayakumar
	 *  Script Created on		-	04/17/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
	public void watercraft(String trans) throws Exception
	{
		
			String WLiability = dataTable.getData("Watercraft","WLiability",trans);
			String Woperation = dataTable.getData("Watercraft","Woperation",trans);
			String Type = dataTable.getData("Watercraft","Type",trans);
			String Length = dataTable.getData("Watercraft","Length",trans);
			String HP = dataTable.getData("Watercraft","HP",trans);
			String Wyear = dataTable.getData("Watercraft","Wyear",trans);
			String Wmake = dataTable.getData("Watercraft","Wmake",trans);
			String Wmodel = dataTable.getData("Watercraft","Wmodel",trans);
			
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_modifier_id_44_5_14_44_D_I')]")));
			
			
			if(WLiability.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_44_5_14_44_D_I')]")).click();
				actions.sendKeys(WLiability).perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1500);
				
			}
			
			if(Woperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddWatercraftToolStripButtonMiddle")).click();
				Thread.sleep(750);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_WatercraftTypeInsCombo_D_I')]")));
			}
			
			if(Woperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_WatercraftInformationInsDataGridView_EditImageButton_0')]")).click();
				Thread.sleep(750);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_WatercraftTypeInsCombo_D_I')]")));
			}
			
			if(Woperation.equalsIgnoreCase("DELETE"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_WatercraftInformationInsDataGridView_DeleteImageButton_0')]")).click();
				Thread.sleep(750);
				
			}
			
			if(Type.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_WatercraftTypeInsCombo_D_I')]")).click();
				actions.sendKeys(Type).build().perform();
				actions.sendKeys(Keys.ENTER).build().perform();
				Thread.sleep(750);
				
			}
			if(Length.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_LengthInsTextBox')]")).click();
				actions.sendKeys(Length).build().perform();
				Thread.sleep(300);
			}
			
			if(HP.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_HorsepowerInsNumeric_HorsepowerInsNumeric')]")).click();
				actions.sendKeys(HP).build().perform();
				Thread.sleep(300);
			}
			if(Wyear.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
				actions.sendKeys(Wyear).build().perform();
				Thread.sleep(300);
			}
			
			if(Wmake.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_ManufacturerInsTextBox')]")).click();
				actions.sendKeys(Wmake).build().perform();
				Thread.sleep(300);
			}
			
			if(Wmodel.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_ModelInsTextBox')]")).click();
				actions.sendKeys(Wmodel).build().perform();
				Thread.sleep(300);
			}
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			
			if(Woperation.equalsIgnoreCase("DELETE")==false)
			{
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			}
			report.updateTestLog("Watercraft", "Watercraft information updated successfuly", Status.DONE);
			report.updateTestLog("Watercraft", "Watercraft information updated successfuly", Status.SCREENSHOT);
		}
	/*******************************************************************************************************************************
	 *  Function Automated		-	Underlying Policies
	 *  Function Description	-	To Add Recreation vehicle Liability
	 *  Author			-	mvijayakumar
	 *  Script Created on		-	04/17/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
	public void recreationalVehicle(String trans) throws Exception	
	{
			String RVCSLLimit = dataTable.getData("RecreationalVehicle","RVCSLLimit",trans);
			String RVBILimit = dataTable.getData("RecreationalVehicle","RVBILimit",trans);
			String RVPDLimit = dataTable.getData("RecreationalVehicle","RVPDLimit",trans);
			String RVOperation = dataTable.getData("RecreationalVehicle","RVOperation",trans);
			String RVType = dataTable.getData("RecreationalVehicle","RVType",trans);
			String RVYear = dataTable.getData("RecreationalVehicle","RVYear",trans);
			String RVmake = dataTable.getData("RecreationalVehicle","RVmake",trans);
			String RVmodel = dataTable.getData("RecreationalVehicle","RVmodel",trans);
			String RVitems = dataTable.getData("RecreationalVehicle","RVitems",trans);
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_modifier_id_40_8_14_40_D_I')]")));
			
			if(RVCSLLimit.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_40_8_14_40_D_I')]")).click();
				actions.sendKeys(RVCSLLimit).perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1500);
				
			}
			
			if(RVBILimit.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_41_8_14_41_D_I')]")).click();
				actions.sendKeys(RVBILimit).perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1500);
				
			}
			
			if(RVPDLimit.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_42_8_14_42_D_I')]")).click();
				actions.sendKeys(RVPDLimit).perform();
				actions.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1500);
			
			}
			
			
			if(RVOperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddRecVehicleToolStripButtonMiddle")).click();
				Thread.sleep(1500);
				//*[@id="P_L_V_v41w22_t17_c0w0_t1_c0w0_t1_c0w0_t13_c0w0_t0_RecreationalVehicleTypeInsCombo_D_I"]
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
			}
			
			if(RVOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleInsDataGridView_EditImageButton_0')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
			}
			if(RVOperation.equalsIgnoreCase("DELETE"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleInsDataGridView_DeleteImageButton_0')]")).click();
				Thread.sleep(1500);
				
			}
			
			if(RVType.isEmpty()==false)
			{
				//*[@id="P_L_V_v21w22_t17_c0w0_t1_c0w0_t1_c0w0_t13_c0w0_t0_RecreationalVehicleTypeInsCombo_D_I"]
				driver.findElement(By.xpath("//input[contains(@id,'RecreationalVehicleTypeInsCombo_D_I')]")).click();
				actions.sendKeys(RVType).perform();
				Thread.sleep(300);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			if(RVYear.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
				actions.sendKeys(RVYear).perform();
				Thread.sleep(300);
			}
			
			if(RVmake.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MakeInsTextBox')]")).click();
				actions.sendKeys(RVmake).perform();
				Thread.sleep(300);
			}
			
			if(RVmodel.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_ModelInsTextBox')]")).click();
				actions.sendKeys(RVmodel).perform();
				Thread.sleep(300);
			}
			
			if(RVitems.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
				actions.sendKeys(RVitems).perform();
				Thread.sleep(300);
			}
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			if(RVOperation.equalsIgnoreCase("DELETE")==false)
			{
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			}
			report.updateTestLog("Recreational Vehicle", "Recreational Vehicle information updated successfuly", Status.DONE);
			report.updateTestLog("Recreational Vehicle", "Recreational Vehicle information updated successfuly", Status.SCREENSHOT);
		}
	/*******************************************************************************************************************************
	 *  Function Automated		-	Underlying Policies
	 *  Function Description	-	To Add Home Personal
	 *  Author			-	mvijayakumar
	 *  Script Created on		-	04/17/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
	public void homePersonal(String trans) throws Exception
	{
	
		String HPLiabilityLimit = dataTable.getData("Home_Personal","HPLiabilityLimit",trans);
		String HPLocationOperation = dataTable.getData("Home_Personal","HPLocationOperation",trans);
		String HPWatercraftOperation = dataTable.getData("Home_Personal","HPWOperation",trans);
		String HPRecVehOperation = dataTable.getData("Home_Personal","HPRVOperation",trans);
		String HPMiscellaneousOperation = dataTable.getData("Home_Personal","HPMOperation",trans);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_modifier_id_43_9_14_43_D_I')]")));
		
		if(HPLiabilityLimit.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_43_9_14_43_D_I')]")).click();
			actions.sendKeys(HPLiabilityLimit).perform();
			actions.sendKeys(Keys.TAB).build().perform();
			Thread.sleep(1500);
			
		}
		
		if(HPLocationOperation.isEmpty()==false)
		{
		if(HPLocationOperation.equalsIgnoreCase("ADD"))
		{
			driver.findElement(By.id("AddPersonalLiabilityToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_TerritoryNumInsNumeric_TerritoryNumInsNumeric')]")));
		}
		
		if(HPLocationOperation.equalsIgnoreCase("EDIT"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityInsDataGridView')]")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_TerritoryNumInsNumeric_TerritoryNumInsNumeric')]")));
		}
		
		
		String HPTerritory = dataTable.getData("Home_Personal","HPTerritory",trans);
		String HPStreetnumber = dataTable.getData("Home_Personal","HPstreetnumber",trans);
		String HPStreet = dataTable.getData("Home_Personal","HPStreet",trans);
		String HPCity = dataTable.getData("Home_Personal","HPCity",trans);
		String HPState = dataTable.getData("Home_Personal","HPState",trans);
		String HPZip = dataTable.getData("Home_Personal","HPZip",trans);
		String HPResidenceType = dataTable.getData("Home_Personal","Hpresidencetype",trans);
		
		String HPCounty = dataTable.getData("Home_Personal","HpCounty",trans);
		String FarmLiability= dataTable.getData("Home_Personal","Farm Liability - Initial Residence",trans);
		String FarmLiabilityAddLoc= dataTable.getData("Home_Personal","Farm Liability - AddLoc",trans);
		String FarmLiabilityHO= dataTable.getData("Home_Personal","Farm LiabilityHO",trans);
		String HomeBusiness = dataTable.getData("Home_Personal","HomeBusiness",trans);
		String HBAdditionalLocation = dataTable.getData("Home_Personal","HBAdditionalLocation",trans);
		if(HPTerritory.isEmpty()==false)
		{
		driver.findElement(By.xpath("//*[contains(@id, '_TerritoryNumInsNumeric_TerritoryNumInsNumeric')]")).click();
		actions.sendKeys(HPTerritory).perform();
		Thread.sleep(300);
		}
		
		if(HPStreetnumber.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityAddressInsName_HouseNumber')]")).click();
			actions.sendKeys(HPStreetnumber).perform();
			Thread.sleep(300);
		}
		
		if(HPStreet.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityAddressInsName_StreetName')]")).click();
			actions.sendKeys(HPStreet).perform();
			Thread.sleep(300);
		}
		
		if(HPCity.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityAddressInsName_City')]")).click();
			actions.sendKeys(HPCity).perform();
			Thread.sleep(300);
		}
		
		if(HPState.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityAddressInsName_AddressState_D_I')]")).click();
			actions.sendKeys(HPState).perform();
			Thread.sleep(300);
		}
		
		if(HPCounty.isEmpty()==false)
		{//*[@id="P_L_V_v22w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_PersonalLiabilityAddressInsName_County"]
			driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityAddressInsName_County')]")).click();
			actions.sendKeys(HPCounty).perform();
			Thread.sleep(300);
		}
		
		if(HPZip.isEmpty()==false)
		{
			actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityAddressInsName_ZipCode_mtxtMain')]"))).click().perform();
	        driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityAddressInsName_ZipCode_mtxtMain')]")).clear();
	        //Thread.sleep(1000);
			actions.sendKeys(HPZip).perform();
	        Thread.sleep(500);
			
		}
		
		if(HPResidenceType.isEmpty()==false)
		{
			String defHPResidenceType="";
			do
			{
				driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityTypeInsCombo_D_I')]")).click();
			Thread.sleep(200);
			actions.sendKeys(HPResidenceType).build().perform();
			System.out.println(HPResidenceType);
			Thread.sleep(250);
			actions.sendKeys(Keys.ENTER).build().perform();
			defHPResidenceType=driver.findElement(By.xpath("//input[contains(@id,'_PersonalLiabilityTypeInsCombo_D_I')]")).getAttribute("value");
			System.out.println(HPResidenceType);
			if(defHPResidenceType.equalsIgnoreCase("")==false)
			{
				//actions.sendKeys(Keys.ARROW_DOWN).build().perform();
				Thread.sleep(250);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			Thread.sleep(250);
			}while(defHPResidenceType.equalsIgnoreCase("")==true);
		}
		
		if(FarmLiability.isEmpty()==false)
		{
			boolean isChecked = driver.findElement(By.xpath("//*[contains(@id, '_t11_c0w0_t1_FarmInsCheckbox')]")).isSelected();
			
		if(FarmLiability.equalsIgnoreCase("Yes")==true)
		{//*[@id="P_L_V_v26w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_FarmInsCheckbox"]
			//*[@id="P_L_V_v41w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_FarmInsCheckbox"]
			driver.findElement(By.xpath("//*[contains(@id, '_t11_c0w0_t1_FarmInsCheckbox')]")).click();
			Thread.sleep(500);
			
		}
		if(FarmLiability.equalsIgnoreCase("No")==true && isChecked==true)
		{
			driver.findElement(By.xpath("//*[contains(@id, '_t11_c0w0_t1_FarmInsCheckbox')]")).click();
			Thread.sleep(500);
		}
		}
		if(FarmLiabilityAddLoc.isEmpty()==false)
		{
			boolean isChecked = driver.findElement(By.xpath("//*[contains(@id, '_t11_c0w0_t1_FarmLiabilityOffPremisesInsCheckbox')]")).isSelected();
			
		if(FarmLiabilityAddLoc.equalsIgnoreCase("Yes")==true)
		{//*[@id="P_L_V_v41w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_FarmLiabilityOffPremisesInsCheckbox"]
			//*[@id="P_L_V_v41w22_t17_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_FarmLiabilityOffPremisesInsCheckbox"]
			
			driver.findElement(By.xpath("//*[contains(@id, '_t11_c0w0_t1_FarmLiabilityOffPremisesInsCheckbox')]")).click();
			Thread.sleep(500);
			
		}
		if(FarmLiabilityAddLoc.equalsIgnoreCase("No")==true && isChecked==true)
		{
			driver.findElement(By.xpath("//*[contains(@id, '_t11_c0w0_t1_FarmLiabilityOffPremisesInsCheckbox')]")).click();
			Thread.sleep(500);
		}
		}
		
		if(FarmLiabilityHO.isEmpty()==false)
		{//*[@id="P_L_V_v21w22_t17_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_ALLOtherRBInsCheckbox"]
			//*[@id="P_L_V_v41w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_IncidentalFarmingOnPremisesInsCheckbox"]
			boolean isChecked = driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_IncidentalFarmingOnPremisesInsCheckbox')]")).isSelected();
			
		if(FarmLiabilityHO.equalsIgnoreCase("Yes")==true)
		{//*[@id="P_L_V_v26w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_FarmInsCheckbox"]
			driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_IncidentalFarmingOnPremisesInsCheckbox')]")).click();
			Thread.sleep(500);
			
		}
		if(FarmLiabilityHO.equalsIgnoreCase("No")==true && isChecked==true)
		{
			//*[@id="P_L_V_v41w22_t17_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_IncidentalFarmingOnPremisesInsCheckbox"]
			driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_IncidentalFarmingOnPremisesInsCheckbox')]")).click();
			Thread.sleep(500);
		}
		}
		
		//*[@id="P_L_V_v21w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_ALLOtherRBInsCheckbox"]
		if(HBAdditionalLocation.isEmpty()==false)
		{//*[@id="P_L_V_v21w22_t17_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_ALLOtherRBInsCheckbox"]
			//*[@id="P_L_V_v41w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_IncidentalFarmingOnPremisesInsCheckbox"]
			boolean isChecked = driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_ALLOtherRBInsCheckbox')]")).isSelected();
			
		if(HBAdditionalLocation.equalsIgnoreCase("Yes")==true)
		{//*[@id="P_L_V_v26w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_FarmInsCheckbox"]
			driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_ALLOtherRBInsCheckbox')]")).click();
			Thread.sleep(500);
			
		}
		if(HBAdditionalLocation.equalsIgnoreCase("No")==true && isChecked==true)
		{
			//*[@id="P_L_V_v41w22_t17_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_IncidentalFarmingOnPremisesInsCheckbox"]
			driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_ALLOtherRBInsCheckbox')]")).click();
			Thread.sleep(500);
		}
		}
		if(HomeBusiness.isEmpty()==false)
		{
			//*[@id="P_L_V_v26w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_RBCCSInsCheckbox"]
			//*[@id="P_L_V_v41w3_t18_c0w0_t1_c0w0_t1_c0w0_t11_c0w0_t1_RBCCSInsCheckbox"]
			boolean isChecked = driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_RBCCSInsCheckbox')]")).isSelected();
			
		if(HomeBusiness.equalsIgnoreCase("Yes")==true)
		{
			driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_RBCCSInsCheckbox')]")).click();
			Thread.sleep(500);
			
		}
		if(HomeBusiness.equalsIgnoreCase("No")==true && isChecked==true)
		{
			driver.findElement(By.xpath("//*[contains(@id, 't11_c0w0_t1_RBCCSInsCheckbox')]")).click();
			Thread.sleep(500);
		}
		}
		
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("AddWatercraftToolStripButtonMiddle")));
		}
		
		if(HPWatercraftOperation.isEmpty()==false)
		{
			String HPType = dataTable.getData("Home_Personal","HPType",trans);
			String HPLength = dataTable.getData("Home_Personal","HPLength",trans);
			String HPHorsepower = dataTable.getData("Home_Personal","HPHorsepower",trans);
			String HPYear = dataTable.getData("Home_Personal","Hpyear",trans);
			String HPMake = dataTable.getData("Home_Personal","HPMake",trans);
			String HPModel = dataTable.getData("Home_Personal","HPModel",trans);
			
			if(HPWatercraftOperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddWatercraftToolStripButtonMiddle")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_t0_WatercraftTypeInsCombo_D_I')]")));
			}
			
			if(HPWatercraftOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_WatercraftInformationInsDataGridView')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_t0_WatercraftTypeInsCombo_D_I')]")));
			}
			
			if(HPType.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_t0_WatercraftTypeInsCombo_D_I')]")).click();
				actions.sendKeys(HPType).perform();
				Thread.sleep(300);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			if(HPLength.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_t0_LengthInsTextBox')]")).click();
				actions.sendKeys(HPLength).perform();
				Thread.sleep(300);
			}
			
			if(HPHorsepower.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_HorsepowerInsNumeric_HorsepowerInsNumeric')]")).click();
				actions.sendKeys(HPHorsepower).perform();
				Thread.sleep(300);
			}
			
			if(HPYear.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
				actions.sendKeys(HPYear).perform();
				Thread.sleep(300);
			}
			
			if(HPMake.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'t0_ManufacturerInsTextBox')]")).click();
				actions.sendKeys(HPMake).perform();
				Thread.sleep(300);
			}
			
			if(HPModel.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'t0_ModelInsTextBox')]")).click();
				actions.sendKeys(HPModel).perform();
				Thread.sleep(300);
			}
			
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("AddRecVehicleToolStripButtonMiddle")));
		}
		
		if(HPRecVehOperation.isEmpty()==false)
		{
			String HPRVType = dataTable.getData("Home_Personal","HPRVType",trans);
			String HPRVYear = dataTable.getData("Home_Personal","HPRVYear",trans);
			String HPRVMake = dataTable.getData("Home_Personal","HPRVMake",trans);
			String HPRVModel = dataTable.getData("Home_Personal","HPRVModel",trans);
			String HPRVItems = dataTable.getData("Home_Personal","HPRVItems",trans);
			
			if(HPRecVehOperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddRecVehicleToolStripButtonMiddle")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
			}
			
			if(HPRecVehOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleInsDataGridView')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
			}
			
			if(HPRVType.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")).click();
				actions.sendKeys(HPRVType).perform();
				Thread.sleep(300);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			if(HPRVYear.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
				actions.sendKeys(HPRVYear).perform();
				Thread.sleep(300);
			}
			
			if(HPRVMake.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MakeInsTextBox')]")).click();
				actions.sendKeys(HPRVMake).perform();
				Thread.sleep(300);
			}
			
			if(HPRVModel.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_ModelInsTextBox')]")).click();
				actions.sendKeys(HPRVModel).perform();
				Thread.sleep(300);
			}
			
			if(HPRVItems.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
				actions.sendKeys(HPRVItems).perform();
				Thread.sleep(300);
			}
			
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("AddMiscLiabilityToolStripButtonMiddle")));
		}
		
		if(HPMiscellaneousOperation.isEmpty()==false)
		{
			String HPMType = dataTable.getData("Home_Personal","HPMType",trans);
			String HPMitems = dataTable.getData("Home_Personal","HPMitems",trans);
			String HPMLocation = dataTable.getData("Home_Personal","HPMLocation",trans);
			String HPMDescription = dataTable.getData("Home_Personal","HPMDescription",trans);
			
			if(HPMiscellaneousOperation.equalsIgnoreCase("ADD"))
			{
				driver.findElement(By.id("AddMiscLiabilityToolStripButtonMiddle")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")));
			}
			
			if(HPMiscellaneousOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MiscLiabilityInsDataGridView')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")));
			}
			
			if(HPMType.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")).click();
				actions.sendKeys(HPMType).perform();
				Thread.sleep(300);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			if(HPMitems.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
				actions.sendKeys(HPMitems).perform();
				Thread.sleep(300);
			}
			
			if(HPMLocation.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_LocationNumInsNumeric_LocationNumInsNumeric')]")).click();
				actions.sendKeys(HPMLocation).perform();
				Thread.sleep(300);
			}
			
			if(HPMDescription.isEmpty()==false)
			{
				//driver.findElement(By.xpath("//input[contains(@id,'_t0i0_DescriptionTextBox')]")).click();
				//actions.sendKeys(HPMDescription).perform();
				//Thread.sleep(300);
				
				
				driver.findElement(By.xpath("//*[contains(@id, '_DescriptionTextBox')]")).click();
				Thread.sleep(500);
				actions.sendKeys(Keys.HOME).build().perform();
				actions.sendKeys(HPMDescription).build().perform();
				Thread.sleep(300);
			}
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			
		}
	
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		report.updateTestLog("Home Personal", "Home Personal information updated successfuly", Status.DONE);
		report.updateTestLog("Home Personal", "Home Personal information updated successfuly", Status.SCREENSHOT);
	}
	/*******************************************************************************************************************************
	 *  Function Automated		-	Underlying Policies
	 *  Function Description	-	To Add Dwelling Fire
	 *  Author			-	mvijayakumar
	 *  Script Created on		-	04/18/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
	public void dwellingFire(String trans) throws Exception
	{
	
		String LiabilityLimit = dataTable.getData("DwellingFireDetail","LiabilityLimit",trans);
		String DFLocationOperation = dataTable.getData("DwellingFireDetail","DFLocationOperation",trans);
		String DFWatercraftOperation = dataTable.getData("DwellingFireDetail","DFWatercraftOperation",trans);
		String DFRecVehOperation = dataTable.getData("DwellingFireDetail","DFRecVehOperation",trans);
		String DFMiscellaneousoperation = dataTable.getData("DwellingFireDetail","DFMiscellaneousoperation",trans);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_modifier_id_45_6_14_45_D_I')]")));
		
		if(LiabilityLimit.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_45_6_14_45_D_I')]")).click();
			actions.sendKeys(LiabilityLimit).perform();
			actions.sendKeys(Keys.TAB).build().perform();
			Thread.sleep(1500);
			
		}
		
		if(DFLocationOperation.isEmpty()==false)
		{
		if(DFLocationOperation.equalsIgnoreCase("ADD"))
		{
			driver.findElement(By.id("AddLocationToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_InsNameAddressControl_HouseNumber')]")));
		}
		
		if(DFLocationOperation.equalsIgnoreCase("EDIT"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_LocationInformationGridView')]")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_InsNameAddressControl_HouseNumber')]")));
		}
		
		if(DFLocationOperation.equalsIgnoreCase("DELETE"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_LocationInformationGridView')]")).click();
			Thread.sleep(1500);
			
		}
		
		String DFstreetnumber = dataTable.getData("DwellingFireDetail","DFstreetnumber",trans);
		String DFStreet = dataTable.getData("DwellingFireDetail","DFStreet",trans);
		String DFCity = dataTable.getData("DwellingFireDetail","DFCity",trans);
		String DFState = dataTable.getData("DwellingFireDetail","DFState",trans);
		String DFZip = dataTable.getData("DwellingFireDetail","DFZip",trans);
		String DFTerritory = dataTable.getData("DwellingFireDetail","DFTerritory",trans);
		String PropertyType = dataTable.getData("DwellingFireDetail","Property Type",trans);
		String RentalUnits = dataTable.getData("DwellingFireDetail","Number of Rental Units",trans);
		
		if(DFstreetnumber.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_InsNameAddressControl_HouseNumber')]")).click();
			actions.sendKeys(DFstreetnumber).perform();
			Thread.sleep(300);
		}
		
		if(DFStreet.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_t1_InsNameAddressControl_StreetName')]")).click();
			actions.sendKeys(DFStreet).perform();
			Thread.sleep(300);
		}
		
		if(DFCity.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_t1_InsNameAddressControl_City')]")).click();
			actions.sendKeys(DFCity).perform();
			Thread.sleep(300);
		}
		
		if(DFState.isEmpty()==false)
		{
			
			driver.findElement(By.xpath("//input[contains(@id,'_InsNameAddressControl_AddressState_D_I')]")).click();
			actions.sendKeys(DFState).build().perform();
			actions.sendKeys(Keys.ENTER).build().perform();
	        Thread.sleep(300);
		}
		
		if(DFZip.isEmpty()==false)
		{
			actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id,'_t1_InsNameAddressControl_ZipCode_mtxtMain')]"))).click().perform();
	        driver.findElement(By.xpath("//input[contains(@id,'_t1_InsNameAddressControl_ZipCode_mtxtMain')]")).clear();
	        //Thread.sleep(1000);
			actions.sendKeys(DFZip).perform();
			//actions.build();
	        //actions.perform();
	        Thread.sleep(500);
			
			//driver.findElement(By.xpath("//input[contains(@id,'_t1_InsNameAddressControl_ZipCode_mtxtMain')]")).click();
			//actions.sendKeys(DFZip).perform();
			//Thread.sleep(300);
		}
		
		if(DFTerritory.isEmpty()==false)
		{
		driver.findElement(By.xpath("//*[contains(@id, '_TerritoryInsNumeric_TerritoryInsNumeric')]")).click();
		actions.sendKeys(DFTerritory).perform();
		Thread.sleep(300);
		}
		
		if(PropertyType.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t17_c0w0_t1_c0w0_t1_c0w0_t10_c0w0_t2_DwellingTypeInsCombo_D_I"]
			driver.findElement(By.xpath("//*[contains(@id, '_DwellingTypeInsCombo_D_I')]")).click();
			actions.sendKeys(PropertyType).perform();
			Thread.sleep(300);
			actions.sendKeys(Keys.TAB).build().perform();
			Thread.sleep(1000);
		}
		
		if(RentalUnits.isEmpty()==false)
		{
			//*[@id="P_L_V_v41w22_t17_c0w0_t1_c0w0_t1_c0w0_t10_c0w0_t2_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric"]
			driver.findElement(By.xpath("//*[contains(@id, '_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
			actions.sendKeys(RentalUnits).perform();
			Thread.sleep(300);
		}
		
		if(DFLocationOperation.equalsIgnoreCase("DELETE")==false)
		{
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("AddWatercraftToolStripButtonMiddle")));
		}
		
	}
	
	if(DFWatercraftOperation.isEmpty()==false)
	{
		
		String DFWatercraftType = dataTable.getData("DwellingFireDetail","DFWatercraftType",trans);
		String DFLength = dataTable.getData("DwellingFireDetail","DFLength",trans);
		String DFHorsepower = dataTable.getData("DwellingFireDetail","DFHorsepower",trans);
		String DFYear = dataTable.getData("DwellingFireDetail","DFYear",trans);
		String DFMake = dataTable.getData("DwellingFireDetail","DFMake",trans);
		String DFModel = dataTable.getData("DwellingFireDetail","DFModel",trans);
		
		if(DFWatercraftOperation.equalsIgnoreCase("ADD"))
		{
			driver.findElement(By.id("AddWatercraftToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_t0_WatercraftTypeInsCombo_D_I')]")));
		}
		
		if(DFWatercraftOperation.equalsIgnoreCase("EDIT"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_WatercraftInformationInsDataGridView')]")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_t0_WatercraftTypeInsCombo_D_I')]")));
		}
		
		if(DFWatercraftOperation.equalsIgnoreCase("DELETE"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_WatercraftInformationInsDataGridView')]")).click();
			Thread.sleep(1500);
			
		}
		if(DFWatercraftType.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_t0_WatercraftTypeInsCombo_D_I')]")).click();
			actions.sendKeys(DFWatercraftType).perform();
			Thread.sleep(300);
			actions.sendKeys(Keys.ENTER).build().perform();
		}
		
		if(DFLength.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_t0_LengthInsTextBox')]")).click();
			actions.sendKeys(DFLength).perform();
			Thread.sleep(300);
		}
		
		if(DFHorsepower.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_HorsepowerInsNumeric_HorsepowerInsNumeric')]")).click();
			actions.sendKeys(DFHorsepower).perform();
			Thread.sleep(300);
		}
		
		if(DFYear.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
			actions.sendKeys(DFYear).perform();
			Thread.sleep(300);
		}
		
		if(DFMake.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'t0_ManufacturerInsTextBox')]")).click();
			actions.sendKeys(DFMake).perform();
			Thread.sleep(300);
		}
		
		if(DFModel.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'t0_ModelInsTextBox')]")).click();
			actions.sendKeys(DFModel).perform();
			Thread.sleep(300);
		}
		if(DFWatercraftOperation.equalsIgnoreCase("DELETE")==false)
		{
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("AddRecVehicleToolStripButtonMiddle")));
		}
		
	}
	
	if(DFRecVehOperation.isEmpty()==false)
	{
		String DFRType = dataTable.getData("DwellingFireDetail","DFRtype",trans);
		String DFRYear = dataTable.getData("DwellingFireDetail","DFRYear",trans);
		String DFRMake = dataTable.getData("DwellingFireDetail","DFRMake",trans);
		String DFRModel = dataTable.getData("DwellingFireDetail","DFRModel",trans);
		String DFRItems = dataTable.getData("DwellingFireDetail","DFRitems",trans);
		
		if(DFRecVehOperation.equalsIgnoreCase("ADD"))
		{
			driver.findElement(By.id("AddRecVehicleToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
		}
		
		if(DFRecVehOperation.equalsIgnoreCase("EDIT"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleInsDataGridView')]")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")));
		}

		if(DFRecVehOperation.equalsIgnoreCase("DELETE"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleInsDataGridView')]")).click();
			Thread.sleep(1500);
			
		}
		
		if(DFRType.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_RecreationalVehicleTypeInsCombo_D_I')]")).click();
			actions.sendKeys(DFRType).perform();
			Thread.sleep(300);
			actions.sendKeys(Keys.ENTER).build().perform();
		}
		
		if(DFRYear.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_YearInsNumeric_YearInsNumeric')]")).click();
			actions.sendKeys(DFRYear).perform();
			Thread.sleep(300);
		}
		
		if(DFRMake.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_MakeInsTextBox')]")).click();
			actions.sendKeys(DFRMake).perform();
			Thread.sleep(300);
		}
		
		if(DFRModel.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_ModelInsTextBox')]")).click();
			actions.sendKeys(DFRModel).perform();
			Thread.sleep(300);
		}
		
		if(DFRItems.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
			actions.sendKeys(DFRItems).perform();
			Thread.sleep(300);
		}
		if(DFRecVehOperation.equalsIgnoreCase("DELETE")==false)
		{
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("AddMiscLiabilityToolStripButtonMiddle")));
		}
		
	}
	
	if(DFMiscellaneousoperation.isEmpty()==false)
	{
		String DFMType = dataTable.getData("DwellingFireDetail","DFMtype",trans);
		String DFMitems = dataTable.getData("DwellingFireDetail","DFMitems",trans);
		String DFMLocation = dataTable.getData("DwellingFireDetail","DFMlocation",trans);
		String DFMDescription = dataTable.getData("DwellingFireDetail","DFMdescription",trans);
		
		if(DFMiscellaneousoperation.equalsIgnoreCase("ADD"))
		{
			driver.findElement(By.id("AddMiscLiabilityToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")));
		}
		
		if(DFMiscellaneousoperation.equalsIgnoreCase("EDIT"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_MiscLiabilityInsDataGridView')]")).click();
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")));
		}
		
		if(DFMiscellaneousoperation.equalsIgnoreCase("DELETE"))
		{
			driver.findElement(By.xpath("//input[contains(@id,'_MiscLiabilityInsDataGridView')]")).click();
			Thread.sleep(1500);
			
		}
		if(DFMType.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")).click();
			actions.sendKeys(DFMType).perform();
			Thread.sleep(300);
			actions.sendKeys(Keys.ENTER).build().perform();
		}
		
		if(DFMitems.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
			actions.sendKeys(DFMitems).perform();
			Thread.sleep(300);
		}
		
		if(DFMLocation.isEmpty()==false)
		{
			driver.findElement(By.xpath("//input[contains(@id,'_LocationNumInsNumeric_LocationNumInsNumeric')]")).click();
			actions.sendKeys(DFMLocation).perform();
			Thread.sleep(300);
		}
		
		if(DFMDescription.isEmpty()==false)
		{
			driver.findElement(By.xpath("//*[contains(@id, '_DescriptionTextBox')]")).click();
			Thread.sleep(500);
			actions.sendKeys(Keys.HOME).build().perform();
			actions.sendKeys(DFMDescription).build().perform();
			Thread.sleep(300);
		
		}
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		if(DFMiscellaneousoperation.equalsIgnoreCase("DELETE")==false)
		{
		driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
		Thread.sleep(1500);
		}
      }
	report.updateTestLog("Dwelling Fire", "Dwelling Fire information updated successfuly", Status.DONE);
	report.updateTestLog("Dwelling Fire", "Dwelling Fire information updated successfuly", Status.SCREENSHOT);
	}
	/*******************************************************************************************************************************
	 *  Function Automated		-	Underlying Policies
	 *  Function Description	-	To Add Miscellaneous Liability
	 *  Author			-	mvijayakumar
	 *  Script Created on		-	04/18/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
	public void miscellaneous(String trans) throws Exception
	{
	
		String MLiabilityLimit = dataTable.getData("Miscellaneous_Liability","MLiabilityLimit",trans);
		String MiscellaneousOperation = dataTable.getData("Miscellaneous_Liability","MiscellaneousOperation",trans);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_modifier_id_46_10_14_46_I')]")));
		
		if(MLiabilityLimit.isEmpty()==false)
		{
			//*[@id="P_L_V_v26w22_t17_c0w0_t1_c0w0_t1_c0w0_t6_modifier_id_46_10_14_46_modifier_id_46_10_14_46_I"]
			driver.findElement(By.xpath("//input[contains(@id,'_modifier_id_46_10_14_46_I')]")).click();
			actions.sendKeys(MLiabilityLimit).perform();
			actions.sendKeys(Keys.TAB).build().perform();
			Thread.sleep(1500);
			
		} 
	
			String MType = dataTable.getData("Miscellaneous_Liability","MType",trans);
			String MItems = dataTable.getData("Miscellaneous_Liability","MItems",trans);
			String MLocation = dataTable.getData("Miscellaneous_Liability","MLocation",trans);
			String MDescription = dataTable.getData("Miscellaneous_Liability","MDescription",trans);
			
			if(MiscellaneousOperation.equalsIgnoreCase("ADD"))
			{
				//*[@id="AddMiscLiabilityToolStripButtonMiddle"]/a
				driver.findElement(By.id("AddMiscLiabilityToolStripButtonMiddle")).click();
				Thread.sleep(1500);
				//*[@id="P_L_V_v26w22_t17_c0w0_t1_c0w0_t1_c0w0_t14_c0w0_t0i4_MiscellaneousLiabilityTypeInsCombo_D_I"]
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")));
			}
			
			if(MiscellaneousOperation.equalsIgnoreCase("EDIT"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MiscLiabilityInsDataGridView')]")).click();
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")));
			}
			
			if(MiscellaneousOperation.equalsIgnoreCase("DELETE"))
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MiscLiabilityInsDataGridView')]")).click();
				Thread.sleep(1500);
				
			}
			
			if(MType.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_MiscellaneousLiabilityTypeInsCombo_D_I')]")).click();
				actions.sendKeys(MType).perform();
				Thread.sleep(300);
				actions.sendKeys(Keys.ENTER).build().perform();
			}
			
			if(MItems.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_NumberOfItemsInsNumeric_NumberOfItemsInsNumeric')]")).click();
				actions.sendKeys(MItems).perform();
				Thread.sleep(300);
			}
			
			if(MLocation.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_LocationNumInsNumeric_LocationNumInsNumeric')]")).click();
				actions.sendKeys(MLocation).perform();
				Thread.sleep(300);
			}
			
			if(MDescription.isEmpty()==false)
			{
				driver.findElement(By.xpath("//*[contains(@id, '_DescriptionTextBox')]")).click();
				Thread.sleep(500);
				actions.sendKeys(Keys.HOME).build().perform();
				actions.sendKeys(MDescription).build().perform();
				Thread.sleep(300);
				
				
				
			}
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			if(MiscellaneousOperation.equalsIgnoreCase("DELETE")==false)
			{
			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
			Thread.sleep(1500);
			}
			report.updateTestLog("Miscellaneous", "Miscellaneous information updated successfuly", Status.DONE);
			report.updateTestLog("Miscellaneous", "Miscellaneous information updated successfuly", Status.SCREENSHOT);
	}
	

/*******************************************************************************************************************************
	 *  Function Automated		-	Coverages
	 *  Function Description	-	To Add Coverages
	 *  Author			-	Mydilee
	 *  Script Created on		-	04/18/2018
	 * @throws Exception
	 *******************************************************************************************************************************/
public void coverage(String trans) throws Exception
{
	String UmbrellaLimit = dataTable.getData("Coverage","UmbrellaLimit",trans);
	String HigherLimit = dataTable.getData("Coverage","HigherLimit",trans);
	
	driver.findElement(By.partialLinkText("Coverages")).click();
	Thread.sleep(1000);
	
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'UmbrellaLimitInsCombo_D_I')]")));
	
	if(UmbrellaLimit.isEmpty()==false)
	{
		//*[@id="P_L_V_v22w22_t18_UmbrellaLimitInsCombo_D_I"]
		driver.findElement(By.xpath("//input[contains(@id,'UmbrellaLimitInsCombo_D_I')]")).click();
		driver.findElement(By.xpath("//input[contains(@id,'UmbrellaLimitInsCombo_D_I')]")).sendKeys(Keys.CONTROL, "a");
		actions.sendKeys(UmbrellaLimit).perform();
		Thread.sleep(1000);
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(3000);
		
	}
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'_HigherLimitInsNumeric_HigherLimitInsNumeric')]")));
   if(HigherLimit.isEmpty()==false)
	{
       driver.findElement(By.xpath("//input[contains(@id,'_HigherLimitInsNumeric_HigherLimitInsNumeric')]")).click();
		driver.findElement(By.xpath("//input[contains(@id,'_HigherLimitInsNumeric_HigherLimitInsNumeric')]")).sendKeys(Keys.CONTROL, "a");
		Thread.sleep(500);
		actions.sendKeys(HigherLimit).perform();
		Thread.sleep(1500);
		
	}
   report.updateTestLog("Coverage", "Coverage information updated successfuly", Status.DONE);
	report.updateTestLog("Coverage", "Coverage information updated successfuly", Status.SCREENSHOT);
   wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_RateToolStripButton")));
}

/*******************************************************************************************************************************
*  Function Automated		- Additional Policy Info
*  Function Description	-	To Add Prior Carrier
*  Author			-	Mydilee
*  Script Created on		-	04/18/2018
* @throws Exception
*******************************************************************************************************************************/


public void priorCarrier(String trans) throws Exception
{
	//wait.until(ExpectedConditions.elementToBeClickable(By.id("P_L_V_RateToolStripButton")));
	actions.moveToElement(driver.findElement(By.partialLinkText("Additional Policy Info"))).click().perform();
	Thread.sleep(1500);
	actions.moveToElement(driver.findElement(By.partialLinkText("Prior Carrier"))).click().perform();
	Thread.sleep(2000);
	//*[@id="P_L_V_DetailTreeViewn7Nodes"]/table[3]/tbody/tr/td[4]/span/a[2]
	//driver.findElement(By.xpath("//*[@id='P_L_V_DetailTreeViewn7Nodes']/table[3]/tbody/tr/td[4]/span/a[2]")).click();
	//actions.moveToElement(driver.findElement(By.linkText("Prior Carrier"))).click();
	
   String PriorCarrier_Name1 = dataTable.getData("Prior_Carrier","PriorCarrier_Name1",trans);
	
	if (PriorCarrier_Name1.isEmpty() == false || PriorCarrier_Name1.equalsIgnoreCase("") == false)
	{
		String PriorCarrier_Name2 = dataTable.getData("Prior_Carrier", "PriorCarrier_Name2",trans);
		String PriorCarrier_TaxType = dataTable.getData("Prior_Carrier", "PriorCarrier_TaxType",trans);
		String PriorCarrier_TaxID = dataTable.getData("Prior_Carrier", "PriorCarrier_TaxID",trans);
		String PriorCarrier_Duration = dataTable.getData("Prior_Carrier", "PriorCarrier_Duration",trans);
		String PriorCarrier_PolicyNumber = dataTable.getData("Prior_Carrier", "PriorCarrier_PolicyNumber",trans); 
		String PriorCarrier_ExpDate = dataTable.getData("Prior_Carrier", "PriorCarrier_ExpDate",trans);
		String PriorCarrier_Type = dataTable.getData("Prior_Carrier", "PriorCarrier_Type",trans);
		
		
		//actions.moveToElement(driver.findElement(By.partialLinkText("Prior Carrier"))).click().perform();
		//*[@id="P_L_V_v22w22_t19_c0w0_NB_ITC2i0_t2_0_PriorCarrierInsName_0_CommercialName1_0"]
		//*[@id="P_L_V_v41w22_t19_c0w0_NB_ITC2i0_t2_0_PriorCarrierInsName_0_CommercialName1_0"]
		actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id, '_PriorCarrierInsName_0_CommercialName1_0')]"))).click().perform();
		actions.sendKeys(PriorCarrier_Name1).perform();
		Thread.sleep(1500);
		actions.sendKeys(Keys.TAB);Thread.sleep(250);
		
		
		if (PriorCarrier_Duration.isEmpty() == false || PriorCarrier_Duration.equalsIgnoreCase("") == false){
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id, 'DurationWithCompanyInsNumeric')]"))).click();
			actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id, 'DurationWithCompanyInsNumeric')]"))).click();
			driver.findElement(By.xpath("//input[contains(@id, 'DurationWithCompanyInsNumeric')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id, 'DurationWithCompanyInsNumeric')]")).sendKeys(PriorCarrier_Duration);
			
		}
		if (PriorCarrier_PolicyNumber.isEmpty() == false || PriorCarrier_PolicyNumber.equalsIgnoreCase("") == false){
			actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id, 'PriorPolicyNumberInsTextBox')]"))).click();
			actions.sendKeys(PriorCarrier_PolicyNumber).perform();
		}
		if (PriorCarrier_ExpDate.isEmpty() == false || PriorCarrier_ExpDate.equalsIgnoreCase("") == false){
			String Defdate="";
			PriorCarrier_ExpDate= PriorCarrier_ExpDate.replaceAll("/", "").toString();
			do
			{
			actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id, 'PriorPolicyNumberInsTextBox') and not(contains(@type, 'hidden'))]"))).click();
			actions.sendKeys(Keys.TAB).perform();
			actions.sendKeys(PriorCarrier_ExpDate).perform();
			Defdate=driver.findElement(By.xpath("//input[contains(@id, 'PriorExpirationDateInsDateTime') and not(contains(@type, 'hidden'))]")).getAttribute("value");
			Defdate= Defdate.replaceAll("/", "").toString();
			}while(Defdate.equalsIgnoreCase(PriorCarrier_ExpDate) == false);
		}
		if (PriorCarrier_Type.isEmpty() == false || PriorCarrier_Type.equalsIgnoreCase("") == false)
		{
			actions.moveToElement(driver.findElement(By.xpath("//input[contains(@id, 'PriorCarrierTypeInsCombo_0_D_0_I')]"))).click();
			actions.sendKeys(PriorCarrier_Type).perform();
			actions.sendKeys(Keys.ENTER).perform();
		}

	}
	
	 report.updateTestLog("Policy Level Coverage", "Policy Level Coverage information updated successfuly", Status.DONE);
	 report.updateTestLog("Policy Level Coverage", "Policy Level Coverage information updated successfuly", Status.SCREENSHOT);
	 
   }
/*******************************************************************************************************************************
*  Function Automated		- Inclusion - Exclusion
*  Function Description	-	To Add Inclusion - Exclusion
*  Author			-	Mydilee
*  Script Created on		-	04/18/2018
* @throws Exception
*******************************************************************************************************************************/
 public void inclusionExclusionUmbrella(String trans) throws Exception 
 {
	    String Description = dataTable.getData("Inclusion_Exclusion", "Description",trans);
	    String Operation = dataTable.getData("Inclusion_Exclusion", "Operation",trans);
	    String Selection = dataTable.getData("Inclusion_Exclusion", "Selection",trans);
	    String Birthdate = dataTable.getData("Inclusion_Exclusion", "Birthdate",trans);
	    String DriverName = dataTable.getData("Inclusion_Exclusion", "DriverName",trans);
	    String Vehicle = dataTable.getData("Inclusion_Exclusion", "Vehicle",trans);
	    String Streetnumber = dataTable.getData("Inclusion_Exclusion", "Streetnumber",trans);
	    String Street = dataTable.getData("Inclusion_Exclusion", "Street",trans);
	    String City = dataTable.getData("Inclusion_Exclusion", "City",trans);
	    String State = dataTable.getData("Inclusion_Exclusion", "State",trans);
	    String Zip = dataTable.getData("Inclusion_Exclusion", "Zip",trans);
	    String Name = dataTable.getData("Inclusion_Exclusion", "Name1",trans);
	    String TrustName = dataTable.getData("Inclusion_Exclusion", "Trust Name",trans);
	  //*[@id="P_L_V_DetailTreeViewn7Nodes"]/table[2]/tbody/tr/td[4]/span/a[2]
	    actions.moveToElement(driver.findElement(By.partialLinkText("Additional Policy Info"))).click().perform();
		Thread.sleep(3000);
		//*[@id="P_L_V_DetailTreeViewn9Nodes"]/table[2]/tbody/tr/td[4]/span/a[2]
	    driver.findElement(By.partialLinkText("Inclusions - Exclusions")).click();
		Thread.sleep(2000);
		String DESCC;
		
		if(Operation.equalsIgnoreCase("ADD")==true)
		{
		WebElement locationsTable = driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]"));
   	List<WebElement> tr_collection = locationsTable.findElements(By.xpath("//table[contains(@id,'InclusionExclusionInsDataGridView')]/tbody/tr"));
	    System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	    int row_num;
	    row_num=1;
	    for(int rowIter = 2;rowIter<=tr_collection.size();rowIter++)
	    {
	       String id = "tbody/tr["+rowIter+"]/";
	       DESCC = driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]/"+id+"td[6]")).getText().trim();
	       if(DESCC.trim().equalsIgnoreCase(Description)){
	        		System.out.println("Row Number: "+row_num);
	        		driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]/"+id+"td[2]/input")).click();
	        		break;
	        	}
	        }
		}
		Thread.sleep(200);
		
		if(Operation.equalsIgnoreCase("EDIT")==true)
		{
		WebElement locationsTable = driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]"));
   	List<WebElement> tr_collection = locationsTable.findElements(By.xpath("//table[contains(@id,'InclusionExclusionInsDataGridView')]/tbody/tr"));
	    System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	    int row_num;
	    row_num=1;
	    for(int rowIter = 2;rowIter<=tr_collection.size();rowIter++)
	    {
	       String id = "tbody/tr["+rowIter+"]/";
	       DESCC = driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]/"+id+"td[6]")).getText().trim();
	       if(DESCC.trim().equalsIgnoreCase(Description)){
	        		System.out.println("Row Number: "+row_num);
	        		driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]/"+id+"td[3]/input")).click();
	        		break;
	        	}
	     }
		}
		
		if(Operation.equalsIgnoreCase("DELETE")==true)
		{
		WebElement locationsTable = driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]"));
   	List<WebElement> tr_collection = locationsTable.findElements(By.xpath("//table[contains(@id,'InclusionExclusionInsDataGridView')]/tbody/tr"));
	    System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	    int row_num;
	    row_num=1;
	    for(int rowIter = 2;rowIter<=tr_collection.size();rowIter++)
	    {
	       String id = "tbody/tr["+rowIter+"]/";
	       DESCC = driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]/"+id+"td[6]")).getText().trim();
	       if(DESCC.trim().equalsIgnoreCase(Description)){
	        		System.out.println("Row Number: "+row_num);
	        		driver.findElement(By.xpath("//table[contains(@id, 'InclusionExclusionInsDataGridView')]/"+id+"td[4]/input")).click();
	        		break;
	        	}
	     }
		}
		//Handle Alert 
		if (isAlertPresent()){
			try{
				Thread.sleep(4000);
				driver.switchTo().defaultContent();
				driver.navigate().refresh();
			}catch(Exception exception){
				System.out.println(exception);
			}
		}
		Thread.sleep(200);
	 
	    Thread.sleep(1000);
	     
	 
	    	 if (driver.findElements(By.id("AddInclusionExclusionScheduledItemToolStripButtonMiddle")).size() > 0)
	    	 {
	    		 
	    	Thread.sleep(200);
	    	if(Selection.equalsIgnoreCase("")==false)
	    	{
	    	if (Operation.equalsIgnoreCase("ADD")==true)
	    	{
	    	driver.findElement(By.id("AddInclusionExclusionScheduledItemToolStripButtonMiddle")).click();
	    	Thread.sleep(1000);
	    	}
	    	
	    	if(Operation.equalsIgnoreCase("EDIT")==true)
			{
	    		driver.findElement(By.xpath("//Input[contains(@id, '_InsDataGridView_EditImageButton_0')]")).click();
	    		Thread.sleep(500);
	    		
			}
			Thread.sleep(200);
			if(Birthdate.isEmpty()==false)
			{
			Birthdate= Birthdate.replaceAll("/", "");
			//*[@id="P_L_V_v22w22_t19_c0w0_NB_ITC1i0_t1_0_c0w0_0_t2i0_0_c0w0_0_t0i0_0_InsName_0_BirthDate_0_BirthDate_0_I"]
			String DoBdefaultVal = driver.findElement(By.xpath("//Input[contains(@id, '_InsName_0_BirthDate_0_BirthDate_0_I')]")).getAttribute("value");
			do {
				driver.findElement(By.xpath("//Input[contains(@id, '_InsName_0_BirthDate_0_BirthDate_0_I')]")).sendKeys(Birthdate);
				driver.findElement(By.xpath("//Input[contains(@id, '_InsName_0_BirthDate_0_BirthDate_0_I')]")).sendKeys(Keys.CONTROL, "a");
				Thread.sleep(500);
				driver.findElement(By.xpath("//Input[contains(@id, '_InsName_0_BirthDate_0_BirthDate_0_I')]")).sendKeys(Birthdate);
				DoBdefaultVal = driver.findElement(By.xpath("//Input[contains(@id, '_InsName_0_BirthDate_0_BirthDate_0_I')]")).getAttribute("value");
				DoBdefaultVal = DoBdefaultVal.replaceAll("/", "");
			} while (DoBdefaultVal.equalsIgnoreCase(Birthdate) == false);
			}
			if(DriverName.isEmpty()==false)
	    	{
				//*[@id="P_L_V_v41w22_t19_c0w0_NB_ITC1i0_t1_0_c0w0_0_t2i0_0_c0w0_0_t0i0_0_CertificateHolderNameInsTextBox_0"]
				//*[@id="P_L_V_v41w22_t19_c0w0_NB_ITC1i0_t1_0_c0w0_0_t2i0_0_c0w0_0_t0i0_0_InsName_0_CommercialName1_0"]
				driver.findElement(By.xpath("//Input[contains(@id, '_DriverInsTextBox_0')]")).click();
	    		actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(DriverName).perform();
	    		
	    	}
			
			if(Vehicle.isEmpty()==false)
	    	{
				driver.findElement(By.xpath("//Input[contains(@id, '_VehicleInsTextbox_0')]")).click();
	    		actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(Vehicle).perform();
	    		
	    	}
			
			if(Streetnumber.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_0_HouseNumber_0')]")).click();
				actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(Streetnumber).perform();
			}
			
			if(Street.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_0_StreetName_0')]")).click();
				actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(Street).perform();
			}
			
			if(City.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_0_City_0')]")).click();
				actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(City).perform();
	    		Thread.sleep(1500);
			}
			
			if(State.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_0_AddressState_0_D_0_I')]")).click();
				actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(State).perform();
	    		Thread.sleep(1500);
	    		actions.sendKeys(Keys.TAB).build().perform();
			}
			
			if(Zip.isEmpty()==false)
			{
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_0_ZipCode_0_mtxtMain_0')]")).click();
				actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(Zip).perform();
	    		actions.sendKeys(Keys.TAB).build().perform();
			}
			
			if(Name.isEmpty()==false)
			{
				//*[@id="P_L_V_v22w22_t19_c0w0_NB_ITC1i0_t1_0_c0w0_0_t2i0_0_c0w0_0_t0i0_0_InsName_0_CommercialName1_0"]
				//*[@id="P_L_V_v22w22_t19_c0w0_NB_ITC1i0_t1_0_c0w0_0_t2i0_0_c0w0_0_t0i0_0_InsName_0_CommercialName1_0"]
				driver.findElement(By.xpath("//input[contains(@id,'_InsName_0_CommercialName1_0')]")).click();
				actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(Name).perform();
			}
			
			if(TrustName.isEmpty()==false)
			{
				//*[@id="P_L_V_v22w22_t19_c0w0_NB_ITC1i0_t1_0_c0w0_0_t2i0_0_c0w0_0_t0i0_0_CertificateHolderNameInsTextBox_0"]
				driver.findElement(By.xpath("//input[contains(@id,'_CertificateHolderNameInsTextBox_0')]")).click();
				actions.sendKeys(Keys.HOME).perform();
	    		actions.sendKeys(TrustName).perform();
			}

			driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
	    	Thread.sleep(2000);
	    	
	    	
	    	driver.findElement(By.id("SaveToolStripButtonMiddle")).click();
	    	Thread.sleep(2000);

			
           }
         }
	    	 report.updateTestLog("Inclusion Exclusion", "Inclusion Exclusion information updated successfuly", Status.DONE);
				report.updateTestLog("Inclusion Exclusion", "Inclusion Exclusion information updated successfuly", Status.SCREENSHOT);
 }

	
	
}

	 
	 
	 