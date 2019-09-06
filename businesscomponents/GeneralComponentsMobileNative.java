package businesscomponents;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;

import pages.EriBankPage;
import pages.TestMunkPage;

/**
 * Class for storing general purpose business components
 * 
 * @author Cognizant
 */
public class GeneralComponentsMobileNative extends ReusableLibrary {

	EriBankPage eribankPage = new EriBankPage(scriptHelper);

	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public GeneralComponentsMobileNative(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void loginEriBank() {
		String userName = dataTable.getData("General_Data", "Username");
		String password = dataTable.getData("General_Data", "Password");

		eribankPage.txtUsername.sendKeys(userName);
		eribankPage.txtPassword.sendKeys(password);
		eribankPage.btnLogin.click();
		report.updateTestLog("LoginEribank", "Logged in Succesfully", Status.PASS);
	}

	public void makePayment() throws InterruptedException {

		eribankPage.btnMakePayment.click();
		PauseScript(3);
		report.updateTestLog("Payment", "Making the Payment", Status.PASS);
	}

	public void enterPaymentDetails() {

		eribankPage.txtPhone.sendKeys(dataTable.getData("General_Data", "Phone"));
		eribankPage.txtName.sendKeys(dataTable.getData("General_Data", "Name"));
		eribankPage.txtAmount.sendKeys(dataTable.getData("General_Data", "Amount"));
		driver.hideKeyboard();
		eribankPage.btnCountrySelect.click();
		eribankPage.btnCountryValue.click();

		report.updateTestLog("Enter Details", "Entered Details Successfuly", Status.PASS);

		eribankPage.btnSendPayment.click();
		eribankPage.btnYes.click();

		report.updateTestLog("Payment", "Payment Succesfull", Status.PASS);
	}

	public void logoutEriBank() {

		eribankPage.btnLogout.click();
		report.updateTestLog("Logout", "Logged Out Succesfully", Status.PASS);
	}

	public void signInTestMunk() {

		TestMunkPage testMunkPage = new TestMunkPage(scriptHelper);
		testMunkPage.login();
	}

	public void verifyTestMunkSignIn() {

		if (driver.findElement(TestMunkPage.lblHome).isDisplayed()) {
			report.updateTestLog("Home Screen", "Home Screen Displayed succesfully", Status.PASS);
		} else {
			report.updateTestLog("Home Screen", "Home Screen didnot display", Status.FAIL);
		}
	}

	public void performOperations() {

		report.updateTestLog("Perform Operations", "Performing Operations", Status.SCREENSHOT);
		driver.findElement(TestMunkPage.btnSkip).click();
		driver.findElement(TestMunkPage.btnSecond).click();
		driver.findElement(TestMunkPage.btnAlert).click();
		driver.findElement(TestMunkPage.btnDismiss).click();
		driver.findElement(TestMunkPage.btnTable).click();
		driver.findElement(TestMunkPage.btnSection).click();
		driver.findElement(TestMunkPage.btnBack).click();
		driver.findElement(TestMunkPage.btnHome).click();
		report.updateTestLog("Home Screen", "Home Screen Displayed succesfully", Status.PASS);
	}
}