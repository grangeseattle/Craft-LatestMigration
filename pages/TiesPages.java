package pages;

import org.openqa.selenium.By;

import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;

/**
 * UI Map for SignOnPage
 */
public class TiesPages extends ReusableLibrary {

	public static final By research = By.cssSelector("#rdbResearch");
	public static final By continues = By.cssSelector("#wscontinue");

	// collections

	public static final By collections = By.xpath("//*[@id='nav']/ol/li[1]/a");
	public static final By arrivals = By.cssSelector("#matter > div.layer-fore > div > div > ol > li:nth-child(1) > a");
	public static final By select_tie = By.xpath("//*[@id=\"matter\"]/div[2]/div/ol/li[1]/a");
	public static final By quantity = By.id("qty-increase");
	public static final By add_to_cart = By.cssSelector("#product_addtocart_form > div.actions-block > div > button");
	public static final By checkout = By.xpath("//*[@id='cart-checkout-methods']");

	// contacts
	public static final By contact = By.cssSelector("#footer > section.footer-sub > div > section.contact-box > a");
	public static final By txtname = By.cssSelector("#name");
	public static final By txtemail =By.id("email");
	public static final By txtphone = By.xpath("//*[@id=\"telephone\"]");
	public static final By txtmessage = By.xpath("//*[@id=\"comment\"]");
	public static final By home = By.cssSelector("#logo > a");

	public TiesPages(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void provideDetails() throws InterruptedException {

		String name = dataTable.getData("General_Data", "Username");
		String email = dataTable.getData("General_Data", "Email");
		String phone = dataTable.getData("General_Data", "Phone");
		String message = dataTable.getData("General_Data", "Message");

		PauseScript(5);
		driver.findElement(contact).click();
		driver.findElement(txtname).sendKeys(name);
		driver.findElement(txtemail).sendKeys(email);
		driver.findElement(txtphone).sendKeys(phone);
		driver.findElement(txtmessage).sendKeys(message);
		report.updateTestLog("Details", "Details Provided Succesfully", Status.PASS);
		driver.findElement(TiesPages.home).click();
	}

}