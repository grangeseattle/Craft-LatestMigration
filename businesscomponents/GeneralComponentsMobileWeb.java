package businesscomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.craft.*;
import com.cognizant.framework.Status;

import pages.TiesPages;

/**
 * Class for storing general purpose business components
 * 
 * @author Cognizant
 */
public class GeneralComponentsMobileWeb extends ReusableLibrary {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public GeneralComponentsMobileWeb(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	String name = dataTable.getData("General_Data", "Username");
	String email = dataTable.getData("General_Data", "Email");
	String phone = dataTable.getData("General_Data", "Phone");
	String message = dataTable.getData("General_Data", "Message");

	public void launchTiesApplication() throws InterruptedException {

		driver.get(properties.getProperty("ApplicationUrlRWD"));

		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
		WebElement logo = driver.findElement(TiesPages.collections);
		wait.until(ExpectedConditions.visibilityOf(logo));

		report.updateTestLog("Launch App", "Application Launched Succesfully", Status.PASS);
	}

	public void navigateToCollections() {

		driver.findElement(By.xpath("//*[@id='nav']/ol/li[1]/a")).click();
		driver.findElement(TiesPages.arrivals).click();
		report.updateTestLog("Arrivals Page", "Navigated to Arrivals Page Sucessfully", Status.PASS);
	}

	public void selectQuantity() {

		driver.findElement(TiesPages.select_tie).click();
		driver.findElement(TiesPages.quantity).click();
		report.updateTestLog("Quantity Selection", "Selected the required Quantity", Status.PASS);
	}

	public void checkOutNProvideDetails() throws InterruptedException {

		PauseScript(3);
		driver.findElement(TiesPages.add_to_cart).click();
		PauseScript(3);
		driver.findElement(TiesPages.checkout).click();
		report.updateTestLog("CheckOut", "Checked out the product", Status.PASS);

		TiesPages tiesPage = new TiesPages(scriptHelper);
		tiesPage.provideDetails();
	}

}