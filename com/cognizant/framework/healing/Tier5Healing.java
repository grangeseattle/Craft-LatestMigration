package com.cognizant.framework.healing;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Tier5Healing {

	private WebDriver driver;
	public By healedLocator;

	public Tier5Healing(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement invokeTier5Process(Map<String, String> map, String code) {

		WebElement element = null;
		turnOffImplicitWaits();
		element = tryHealingWithNewLocators(map, code);
		turnOnImplicitWaits();
		return element;
	}

	private WebElement tryHealingWithNewLocators(Map<String, String> map, String code) {
		WebElement element = null;

		if (map.get("x") != null & map.get("y") != null) {

			try {

				if (getIfClick(code)) {
					element = getElementFromPoint(Integer.parseInt(map.get("x")), Integer.parseInt(map.get("y")));
				} else {
					System.out.println(
							"Object Healing Machine Suggest : \n Please use Actions to perform Operation. \n Element has identifed using Coordinates but this element works along with Action Class"
									+ "\r\n" + "Actions actions = new Actions(driver);\r\n"
									+ "		actions.moveToElement(element);actions.sendKeys(\"SOME DATA\");\r\n"
									+ "		actions.build().perform();");
				}

			} catch (Exception e) {

			}

		}

		return element;

	}

	private boolean getIfClick(String code) {
		boolean ifClick = false;
		if (code.contains("click()")) {
			ifClick = true;
		}
		return ifClick;
	}

	private WebElement getElementFromPoint(int x_value, int y_value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		while (true) {
			String s_Script = "return document.elementFromPoint(arguments[0], arguments[1]);";

			WebElement ele = (WebElement) js.executeScript(s_Script, x_value, y_value);
			if (ele == null)
				return null;

			if (ele.getTagName() != "frame" && ele.getTagName() != "iframe")
				return ele;

			Point p_Pos = getElementPosition(ele);
			x_value -= p_Pos.x;
			y_value -= p_Pos.y;

			driver.switchTo().frame(ele);
		}
	}

	@SuppressWarnings("unchecked")
	private Point getElementPosition(WebElement ele) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		String s_Script = "var X, Y; " + "if (window.pageYOffset) " // supported
																	// by most
																	// browsers
				+ "{ " + "  X = window.pageXOffset; " + "  Y = window.pageYOffset; " + "} " + "else " // Internet
																										// Explorer
																										// 6,
																										// 7,
																										// 8
				+ "{ " + "  var  Elem = document.documentElement; " // <html>
																	// node (IE
																	// with
																	// DOCTYPE)
				+ "  if (!Elem.clientHeight) Elem = document.body; " // <body>
																		// node
																		// (IE
																		// in
																		// quirks
																		// mode)
				+ "  X = Elem.scrollLeft; " + "  Y = Elem.scrollTop; " + "} " + "return new Array(X, Y);";

		List<Object> list = (List<Object>) js.executeScript(s_Script);

		int s32_ScrollX = Integer.valueOf(list.get(0).toString());
		int s32_ScrollY = Integer.valueOf(list.get(1).toString());

		return new Point(ele.getLocation().getX() - s32_ScrollX, ele.getLocation().getY() - s32_ScrollY);
	}
	
	public By getHealedLocator() {
		return this.healedLocator;
	}
	
	private void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	private void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

}
