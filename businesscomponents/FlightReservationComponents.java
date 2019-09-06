package businesscomponents;

import org.openqa.selenium.WebElement;

import com.cognizant.craft.DriverScript;
import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.Status;

import pages.BookFlightPage;
import pages.FlightConfirmationPage;
import pages.FlightFinderPage;
import pages.SelectFlightPage;

/**
 * Class for storing business components related to the flight reservation
 * functionality
 * 
 * @author Cognizant
 */
public class FlightReservationComponents extends ReusableLibrary {
	private static final String FLIGHTS_DATA = "Flights_Data";

	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public FlightReservationComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void findFlights() {
		FlightFinderPage flightFinderPage = new FlightFinderPage(scriptHelper);
		flightFinderPage.findFlights();
	}

	public void selectFlights() {
		SelectFlightPage selectFlightPage = new SelectFlightPage(scriptHelper);
		selectFlightPage.selectFlights();
	}

	public void bookFlights() {
		BookFlightPage bookFlightPage = new BookFlightPage(scriptHelper);
		bookFlightPage.bookFlights();
	}

	public void verifyBooking() {
		if (driverUtil.isTextPresent("^[\\s\\S]*Your itinerary has been booked![\\s\\S]*$")) {
			report.updateTestLog("Verify Booking", "Tickets booked successfully", Status.PASS);

			WebElement flightConfirmation = driver.findElement(FlightConfirmationPage.lblConfirmationMessage);

			String flightConfirmationNumber = flightConfirmation.getText();
			flightConfirmationNumber = flightConfirmationNumber.split("#")[1].trim();
			//dataTable.putData(FLIGHTS_DATA, "FlightConfirmationNumber", flightConfirmationNumber);
			report.updateTestLog("Flight Confirmation", "The flight confirmation number is " + flightConfirmationNumber,
					Status.SCREENSHOT);
		} else {
			report.updateTestLog("Verify Booking", "Tickets booking failed", Status.FAIL);
		}
	}

	public void backToFlights() {
		FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(scriptHelper);
		flightConfirmationPage.backToFlights();
	}

}