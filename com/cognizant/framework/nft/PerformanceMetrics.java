package com.cognizant.framework.nft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.json.JSONException;
/*import org.influxdb.dto.Point;*/
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;



public class PerformanceMetrics {
	

	public JavascriptExecutor js;
	public WebDriverWait wait;
	public boolean dtEnabled;
	public String CUSTOMERID;
	public String USERID;
	public String PASSWORD;
	public boolean timersEnabled;
	public  FileWriter timerlog;
	public boolean acceptNextAlert = true;
	public SimpleDateFormat dtFmt;
	public Date date;
	public  Date dateTime;
	public  long lastLoadTime;
	public  DateFormat requiredFormat;
	public  String DATE;
//	public  String TESTAGENT;
	public  String timerName;
	public  long 	 start;
	public  long   finish;
	public boolean dynaTrace=false;
	public boolean browserTimers=true;
	public String PROFILE="Tino";
	public String URL = "";
public String SiteNavigation1 (WebDriver driver,String browser,String ENV,String URL,String BUILD,String PROJECT) throws IOException {

		String browserVersion = "";
		
		dtEnabled=dynaTrace;
		
	
		/*System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();*/
		
		dtFmt = new SimpleDateFormat("yyMMdd");
		date = new Date();
		//System.out.println("Im here");
		Capabilities browser1 = ((ChromeDriver) driver).getCapabilities();
		browserVersion=browser1.getVersion();
		
		
		browser="Chrome-"+browserVersion;
		System.out.println("Browser: "+browser);

		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 30);
		
		AgentData11(ENV,URL);
		
		//URL = ad.getURL();
		//URL = "https://authvchnform.cognizant.com/vpn/tmindex.html";
		System.out.println("BROWSER=="+browser);
		System.out.println("ENV=="+ENV);
		System.out.println("BUILD=="+BUILD);
		System.out.println("TESTURL=="+URL);
		//System.out.println("TESTAGENT=="+TESTAGENT);
		
		PerformanceUtils1(js, ENV, browser,BUILD,PROJECT,URL, dynaTrace,browserTimers);
		//System.out.println("perf=="+perf);
		//System.out.println("perfff"+perf.toString());
		
		if (dtEnabled) {
			startRecording(getProfile(), PROJECT, driver, browser, ENV,BUILD);
		}
		return browser;
	}

public String SiteNavigationwithCache(WebDriver driver,String browser,String ENV,String URL,String BUILD,String PROJECT) throws IOException {

	String browserVersion = "";
	
	dtEnabled=dynaTrace;
	

	/*System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe");
	driver = new ChromeDriver();*/
	
	dtFmt = new SimpleDateFormat("yyMMdd");
	date = new Date();
	//System.out.println("Im here");
	Capabilities browser1 = ((ChromeDriver) driver).getCapabilities();
	browserVersion=browser1.getVersion();
	
	
	browser="Chrome-"+browserVersion;
	System.out.println("Browser: "+browser);

	js = (JavascriptExecutor) driver;
	wait = new WebDriverWait(driver, 30);
	
	AgentData11(ENV,URL);
	
	//URL = ad.getURL();
	//URL = "https://authvchnform.cognizant.com/vpn/tmindex.html";
	System.out.println("BROWSER=="+browser);
	System.out.println("ENV=="+ENV);
	System.out.println("BUILD=="+BUILD);
	System.out.println("TESTURL=="+URL);
	//System.out.println("TESTAGENT=="+TESTAGENT);
	
	PerformanceUtils1withCache(js, ENV, browser,BUILD,PROJECT,URL, dynaTrace,browserTimers);
	//System.out.println("perf=="+perf);
	//System.out.println("perfff"+perf.toString());
	
	if (dtEnabled) {
		startRecording(getProfile(), PROJECT, driver, browser, ENV,BUILD);
	}
	return browser;
}

public void startRecording(String Profile, String Scenario,WebDriver driver,String browser,String ENV,String BUILD) throws IOException {
	
	String sessionName;
	SimpleDateFormat dtFmt = new SimpleDateFormat("yyMMdd");
	Date date = new Date();
	Profile.replace(" ", "%20");
	
	driver.get("http://admin:admin@wajxpnasv01:8020/rest/html/management/profiles/"+Profile+"/configurations");
	
	// Find the dT Profile being used
	int i;
	String dtProfile="";
	for (i=1;i<10;i++) {
		if (driver.findElement(By.xpath("(//ul[@class='switches']//li)["+i+"]")).getAttribute("class").equals("active")) {
			dtProfile=driver.findElement(By.xpath("(//ul[@class='list']/li)["+i+"]")).getText().replace("active", "");
			i=10;
		}
	}

		// dynamically build the session name
	sessionName=dtFmt.format(date).toString()+"_Dotcom_"+ENV+"_"+
			browser+"_"+Scenario+"_"+getBuild(BUILD)+"_"+dtProfile;

	System.out.println("Session Name: "+sessionName);
	

	Profile.replace("%20", " ");
	
	driver.findElement(By.linkText(Profile)).click();

	sleep(1500);
	WebElement testName = driver.findElement(By.xpath(".//*[@id='presentableName']"));
	testName.clear();
	testName.sendKeys(sessionName);
	driver.findElement(By.id("isSessionLocked")).click();

	driver.findElement(By.id("but_startrecording")).click();

	driver.quit();
}

public void landing (WebDriver driver,String ENV,String URL) throws IOException {
	
	startTimer("Landing Page");
		if (ENV.toUpperCase().contains("QA12")) {
			driver.get(URL);
		} else if (ENV.equals("Jordan")) {
			driver.get(URL+"webapp/wcs/stores/servlet/sahome?storeId=10101");
		} else {
			driver.get(URL);
		}
	stopTimer();

	/////////////////////////////////////////////////////////////////////////////////
	//                    Accept Certificate for PERF                              //
	////////////////////////////////////////////////////////////////////////////////
	try {
		if (driver.getTitle().matches("Certificate Error: Navigation Blocked")) {
			driver.get("javascript:document.getElementById('overridelink').click();");
			sleep(500);
			Alert alert = driver.switchTo().alert();

			try {
				alert.accept();
			} catch (Exception e) {
				System.out.println("Certificate Alert not present.");
			}
		}
	} catch (Exception e) {}
}
public void setAppServer (WebDriver driver,String ENV) throws IOException {
	
	if (ENV=="QP2" || ENV=="PRF") {
		String CookieValue = driver.manage().getCookieNamed("OSAJSESSIONID").getValue();
		//System.out.println("ORIGINAL COOKIE VALUE:  "+CookieValue);
		driver.manage().deleteCookieNamed("OSAJSESSIONID");
		CookieValue = CookieValue.substring(0, CookieValue.length()-5).concat("21QP1");
		js.executeScript("document.cookie='OSAJSESSIONID="+CookieValue+"';");
	} else if (ENV=="QP1") {
		String CookieValue = driver.manage().getCookieNamed("OSAJSESSIONID").getValue();
		//System.out.println("ORIGINAL COOKIE VALUE:  "+CookieValue);
		driver.manage().deleteCookieNamed("OSAJSESSIONID");
		CookieValue = CookieValue.substring(0, CookieValue.length()-5).concat("01QP1");
		js.executeScript("document.cookie='OSAJSESSIONID="+CookieValue+"';");
	}
}
public void newBrowser(WebDriver driver) {
	driver.close();

	killProc();

		driver = new ChromeDriver();
	

	js = (JavascriptExecutor) driver;
	wait = new WebDriverWait(driver, 30);
	System.out.println("JS----------------"+js);
	setJSX(js);
	System.out.println("completed");
}

public void loadURL (String url, String pageName,WebDriver driver,String browser,String TESTURL,String BUILD,String ENV) throws IOException, JSONException{
	driver.get(url);
	pageTimer(pageName, browser, TESTURL, BUILD, ENV, "");
}

public void clearCache()
{
	
	try {
		Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 8");
		sleep(5000);
		System.out.println("Clear cache complete");
		//Process clearCache = Runtime.getRuntime().exec("C:\\selenium-java-2.43.0\\ClearCacheLite.bat");
		//clearCache.waitFor();
	} catch (Exception e) {
		System.out.println("Failed to clear cache");
	}
}


public void findToProceed(String cssPath,WebDriver driver) {
	WebDriverWait wait = new WebDriverWait(driver, 20);

	try {
		sleep(2000);
		//wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssPath))));
		sleep(2500);

	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("CSS element "+cssPath+" not found on page!");
	}
}
public void clearCache(WebDriver driver)
{
	try {
		Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
		driver.manage().deleteAllCookies();
		
		//Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 4351");
		
		//Process clearCache = Runtime.getRuntime().exec("C:\\selenium-java-2.43.0\\ClearCacheFull.bat");
		//clearCache.waitFor();
		sleep(8000);
	} catch (Exception e) {
		System.out.println("Failed to clear cache");
	}
}
	public void sleep (int sleepMS) {
		try {
			Thread.sleep(sleepMS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void killProc () {
		//Kill any existing driver server processes 
		try {
			Process killProc = Runtime.getRuntime().exec("C:\\AutomationScripts_Selenium\\IEDriverServer_x64_3.6.0\\kill_IEDriverServer.exe");
			killProc.waitFor();
			killProc = Runtime.getRuntime().exec("C:\\AutomationScripts_Selenium\\chromedriver_win32\\kill_chromedriver.exe");
			killProc.waitFor();
		} catch (IOException e2) {
			System.out.println("Failed to kill the running driver processes");
		} catch (InterruptedException e) {
			System.out.println("Failed to wait for killProc to terminate");
		} catch (NullPointerException e3) {
			System.out.println("Failed to wait for killProc to terminate");
		}
	}
public void stopRecording(String Profile,WebDriver driver) {
		
		
		WebDriverWait wait = new WebDriverWait(driver, 120);
		
		Profile.replace(" ", "%20");
		
		driver.get("http://admin:admin@wajxpnasv01:8020/rest/html/management/profiles/"+Profile);

		driver.findElement(By.id("but_stoprecording")).click();

		//wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("div#innercontent"), "Recording was stopped."));
		
		driver.quit();

	}

	
	public void close(WebDriver driver,String ENV,String URL) throws IOException {
		pageTimerEnd();
		driver.close();
		//Thread.sleep(1000);
	//	sleep(600);
	
		
		if (dtEnabled) {
			AgentData11(ENV,URL);
			stopRecording(getProfile(), driver);
		}
		driver.quit();
			


}
	
	public String PerformanceUtils1(JavascriptExecutor jsx, String ENV, String browser, String BUILD, String PROJECT, String TESTURL, boolean dynaTrace, boolean browserTimers) throws IOException {
		
		
		dtEnabled = dynaTrace;
		timersEnabled=browserTimers;
		
		String TestTURL = TESTURL.replace("http:", "").replace("https:", "").replaceAll("/", "");
		
		if (!PROJECT.isEmpty()) {
			String Project= PROJECT+"_";
		}

		pageTimerInit(ENV, browser, PROJECT, BUILD);
		return TestTURL;
	}
	
	
public String PerformanceUtils1withCache(JavascriptExecutor jsx, String ENV, String browser, String BUILD, String PROJECT, String TESTURL, boolean dynaTrace, boolean browserTimers) throws IOException {
		
		
		dtEnabled = dynaTrace;
		timersEnabled=browserTimers;
		
		String TestTURL = TESTURL.replace("http:", "").replace("https:", "").replaceAll("/", "");
		
		if (!PROJECT.isEmpty()) {
			String Project= PROJECT+"_";
		}

		pageTimerInitWithCache(ENV, browser, PROJECT, BUILD);
		return TestTURL;
	}
	
	public  FileWriter pageTimerInit(String ENV,String browser,String PROJECT,String BUILD) throws IOException {
	
		if (timersEnabled==false) {
			return timerlog;
		}

		SimpleDateFormat dtFmt = new SimpleDateFormat("yyMMdd");
		dateTime = new Date();
		
		requiredFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss.SSS");
		requiredFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));

		//String filename = "test";
		DATE = dtFmt.format(dateTime).toString();
		String filename = DATE+"_DOTCOM"+ENV+"_"+
				browser+"_"+PROJECT+BUILD;
		String filen="sample";
		System.out.println("filename"+filename);	
		//Get system name
//		TESTAGENT = System.getenv().get("COMPUTERNAME").toString();
//		System.out.println("TESTAGENT"+TESTAGENT);
		
		try {
			if (System.getProperty("user.name").equals("amazooji")) {
				timerlog = new FileWriter("C:\\Performance\\PageTimerLogs\\"+filename+".log");
			} else {
				timerlog = new FileWriter("C:\\AutomationScripts_Selenium\\"+filename+".log");
				System.out.println("timerlog"+timerlog.getEncoding());
			}

			timerlog.append("\n\n-------------"+filename+"-------------\n\n");
			
			//Print Column Header Info
			timerlog.append("Date/Time\tBrowser\tEnvironment\tBuild\tPageName\tTimeToFirstByte\tFirstImpression\tonLoadTime\tTotalLoadTime\tRedirectTime\t"+
					"CacheLookup\tDNSLookup\tTCPConnect\tRequestSubmit\tServerTime\tResponseTransmitTime\tDOMLoadingtoInteractive\t"+
					"DOMLoadingtoLoaded\tDOMLoadingtoComplete\tonLoadExecuteTime\tunLoadTime\tTestURL\tTestAgent\tConnectTime\t"+
					"TotalServerTime\tClientonLoadTime\tClientTotalTime\tInitialConnection\tDomComplete\tDownloadTime\n");

			timerlog.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    		System.out.println("Failed to write performance metrics to file.");
		}
		
		return timerlog;
	}
	
	public  void pageTimerEnd() {
		if (timersEnabled==false) {
			return;
		}
		try {
			timerlog.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to close Page Timer File.");
		}
	}
	
	public  FileWriter pageTimerInitWithCache(String ENV,String browser,String PROJECT,String BUILD) throws IOException {
		
		if (timersEnabled==false) {
			return timerlog;
		}

		SimpleDateFormat dtFmt = new SimpleDateFormat("yyMMdd");
		dateTime = new Date();
		
		requiredFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss.SSS");
		requiredFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));

		//String filename = "test";
		DATE = dtFmt.format(dateTime).toString();
		String filename = DATE+"_DOTCOM"+ENV+"_"+
				browser+"_"+PROJECT+BUILD;
		String filen="sample";
		System.out.println("filename"+filename);	
		//Get system name
//		TESTAGENT = System.getenv().get("COMPUTERNAME").toString();
//		System.out.println("TESTAGENT"+TESTAGENT);
		
		try {
			if (System.getProperty("user.name").equals("amazooji")) {
				timerlog = new FileWriter("C:\\Performance\\PageTimerLogs\\"+filename+".log");
			} else {
				timerlog = new FileWriter("C:\\AutomationScripts_Selenium\\"+filename+".log");
				System.out.println("timerlog"+timerlog.getEncoding());
			}

			timerlog.append("\n\n-------------"+filename+"-------------\n\n");
			
			//Print Column Header Info
			timerlog.append("Date/Time\tBrowser\tEnvironment\tBuild\tPageName\tTimeToFirstByte\tFirstImpression\tonLoadTime\tTotalLoadTime\tRedirectTime\t"+
					"CacheLookup\tDNSLookup\tTCPConnect\tRequestSubmit\tServerTime\tResponseTransmitTime\tDOMLoadingtoInteractive\t"+
					"DOMLoadingtoLoaded\tDOMLoadingtoComplete\tonLoadExecuteTime\tunLoadTime\tTestURL\tTestAgent\tConnectTime\t"+
					"TotalServerTime\tClientonLoadTime\tClientTotalTime\tInitialConnection\tDomComplete\tDownloadTime\n");

			timerlog.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    		System.out.println("Failed to write performance metrics to file.");
		}
		
		return timerlog;
	}
	
	
	public  JSONObject pageTimer(String PageName,String browser,String TESTURL,String BUILD,String ENV, String scenarioTestcase) throws IOException, JSONException {
		int temp=0;
		int convert;
		int pagesize;
		JSONObject browserdata=new JSONObject();

		System.out.println(" Page Name ****** " +PageName);
		
		
		long navigationStart = -1;
		long redirectStart = -1;
		long redirectEnd = -1;
		long unloadEventStart = -1;
		long unloadEventEnd = -1;
		long loadEventEnd = -1;
		long fetchStart = -1;
		long connectEnd = -1;
		long connectStart = -1;
		long domainLookupEnd = -1;
		long domainLookupStart = -1;
		long requestStart = -1;
		long responseStart = -1;
		long domInteractive = -1;
		long domLoading = -1;
		long msFirstPaint = -1;
		long responseEnd = -1;
		long domContentLoadedEventStart = -1;
		long domComplete = -1;
		long domContentLoadedEventEnd = -1;
		long loadEventStart = -1;
		String resourceAPI;
		boolean loading = true;
		String Time = null;
		
		//System.out.println("I am In1 ");
		
		//Get the current time in Eastern
		TimeZone timeZone1 = TimeZone.getTimeZone("America/New_York");
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(timeZone1);
		String Hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		String Min  = String.valueOf(calendar.get(Calendar.MINUTE));

		Date dateTime = new Date();
		//System.out.println("Test date/Time: "+requiredFormat.format(dateTime));

		
		if (Min.length()==1) {
			Time = Hour + ":0" + Min;
		} else {
			Time = Hour + ":" + Min;
		}

		//System.out.println("I am In2 ");
		while (loading) {
			try {
				//System.out.println("I am In3 ");
				Thread.sleep(3500);
				//System.out.println("I am In2=4 ");
				System.out.println("BROWSER=="+browser);
				System.out.println("ENV=="+ENV);
				System.out.println("BUILD=="+BUILD);
				System.out.println("TESTURL=="+TESTURL);
//				System.out.println("TESTAGENT=="+TESTAGENT);
				System.out.println("loading...loadEventEnd: "+loadEventEnd);
				loadEventEnd = (long) Double.valueOf(js.executeScript("return window.performance.timing.loadEventEnd;").toString()).doubleValue();
				Thread.sleep(1500);
				System.out.println("I am In 4 ");

				if (loadEventEnd != lastLoadTime && loadEventEnd > 0) {
					loading = false;
					lastLoadTime = loadEventEnd;
				} else {
					System.out.println("loading... lastLoadTime: "+lastLoadTime+
							           "\n           loadEventEnd: "+loadEventEnd);
				}
			} catch (Exception e) {
				
			} 
		}
		
		
		
		try {
			navigationStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.navigationStart;").toString()).doubleValue();
		} catch (Exception e) {
			navigationStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.fetchStart;").toString()).doubleValue(); 
		}


		try {
			redirectStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.redirectStart;").toString()).doubleValue();

		} catch (Exception e) {
			redirectStart = -1;
		}

		try {
			redirectEnd = (long) Double.valueOf(js.executeScript("return window.performance.timing.redirectEnd;").toString()).doubleValue();

		} catch (Exception e) {
			redirectEnd = -1;
		}

		try {
			unloadEventStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.unloadEventStart;").toString()).doubleValue();

		} catch (Exception e) {
			unloadEventStart = -1;
		}

		try {
			unloadEventEnd = (long) Double.valueOf(js.executeScript("return window.performance.timing.unloadEventEnd;").toString()).doubleValue();

		} catch (Exception e) {
			unloadEventEnd = -1;
		}
		
		try {
			fetchStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.fetchStart;").toString()).doubleValue();

			if (navigationStart<=0) {
				navigationStart = fetchStart;
			}
		} catch (Exception e) {
			fetchStart = -1;
		}

		try {
			connectEnd = (long) Double.valueOf(js.executeScript("return window.performance.timing.connectEnd;").toString()).doubleValue();

		} catch (Exception e) {
			connectEnd = -1;
		}
		
		try {
			connectStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.connectStart;").toString()).doubleValue();

		} catch (Exception e) {
			connectStart = -1;
		}
		
		try {
			domainLookupEnd = (long) Double.valueOf(js.executeScript("return window.performance.timing.domainLookupEnd;").toString()).doubleValue();

		} catch (Exception e) {
			domainLookupEnd = -1;
		}
		
		try {
			domainLookupStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.domainLookupStart;").toString()).doubleValue();

		} catch (Exception e) {
			domainLookupStart = -1;
		}
		
		try {
			requestStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.requestStart;").toString()).doubleValue();

		} catch (Exception e) {
			requestStart = -1;
		}
		try {
			responseStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.responseStart;").toString()).doubleValue();

		} catch (Exception e) {
			responseStart = -1;
		}
		try {
			domInteractive = (long) Double.valueOf(js.executeScript("return window.performance.timing.domInteractive;").toString()).doubleValue();

		} catch (Exception e) {
			domInteractive = -1;
		}
		
		try {
			domLoading = (long) Double.valueOf(js.executeScript("return window.performance.timing.domLoading;").toString()).doubleValue();

		} catch (Exception e) {
			domLoading = -1;
		}
		
		try {
			msFirstPaint = (long) Double.valueOf(js.executeScript("return window.performance.timing.msFirstPaint;").toString()).doubleValue();

		} catch (Exception e) {
			msFirstPaint = -1;
		}
		
		try {
			responseEnd = (long) Double.valueOf(js.executeScript("return window.performance.timing.responseEnd;").toString()).doubleValue();

		} catch (Exception e) {
			responseEnd = -1;
		}
		
		try {
			domContentLoadedEventStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.domContentLoadedEventStart;").toString()).doubleValue();

		} catch (Exception e) {
			domContentLoadedEventStart = -1;
		}
		
		try {
			domComplete = (long) Double.valueOf(js.executeScript("return window.performance.timing.domComplete;").toString()).doubleValue();
//			System.out.println("   domComplete: "+domComplete);
		} catch (Exception e) {
			domComplete = -1;
		}
		
		try {
			domContentLoadedEventEnd = (long) Double.valueOf(js.executeScript("return window.performance.timing.domContentLoadedEventEnd;").toString()).doubleValue();
//			System.out.println("   domContentLoadedEventEnd: "+domContentLoadedEventEnd);
		} catch (Exception e) {
			domContentLoadedEventEnd = -1;
		}
		
		try {
			loadEventStart = (long) Double.valueOf(js.executeScript("return window.performance.timing.loadEventStart;").toString()).doubleValue();
		System.out.println("   loadEventStart: "+loadEventStart);
		} catch (Exception e) {
			loadEventStart = -1;
		}
		
		try {
			resourceAPI = (String) js.executeScript("return JSON.stringify(performance.getEntriesByType('resource'))");
		System.out.println("   resourceAPI: "+js.executeScript("return JSON.stringify(performance.getEntriesByType('resource'))"));
		} catch (Exception e) {
			resourceAPI = (String) js.executeScript("return JSON.stringify(performance.getEntriesByType('resource'))");
			e.printStackTrace();
		}
		
		org.json.JSONArray jsonarray = new org.json.JSONArray(resourceAPI);

		System.out.println("Length=========>"+jsonarray.length());
				    for(int i=0; i<jsonarray.length(); i++){
				        org.json.JSONObject obj = jsonarray.getJSONObject(i);

				        Integer size=(Integer) obj.get("transferSize");
				       temp = temp+size;
				      

				        
				        System.out.println(size);
				    }   
				    
				    System.out.println(temp);
				   
		
		
		String redirect = "";
		String AppCache = "";
		String dnsLookup = "";
		String tcpConnect = "";
		String requestTime = "";
		String serverTime = "";
		String responseTransmit = "";
		String domLoad2Inter = "";
		String domLoad2DomLoaded = "";
		String domLoad2Complete = "";
		String onloadExecute = "";
		String ttFirstByte = "";
		String ttFirstImpression = "";
		String ttOnLoad = "";
		String Total = "";
		String unLoadTime = "";
		String connectTime = "";
		String totalServerTime = "";
		String clientOnLoadTime = "";
		String clientTotalTime = "";
		String initialConnection="";
		String DomComplete="";
		String DownloadTime="";
		
		
		
		//Calculate Metrics
		if (redirectEnd>=0 || redirectStart>=0) {
			redirect = String.valueOf(redirectEnd-redirectStart);
			//System.out.println("redirect time: "+redirect);
		}
		if (domainLookupStart>=0 || fetchStart>=0) {
			//AppCache = (int) (domainLookupStart-fetchStart);
			AppCache = String.valueOf(domainLookupStart-fetchStart);
			//System.out.println("Cache time: "+AppCache);
		}
		if (domainLookupEnd>=0 || domainLookupStart>=0) {
			//dnsLookup = (int) (domainLookupEnd-domainLookupStart);
			dnsLookup = String.valueOf(domainLookupEnd-domainLookupStart);
			//System.out.println("Cache time: "+AppCache);
		}
		if (connectEnd>=0 || connectStart>=0) {
			//tcpConnect = (int) (connectEnd-connectStart);
			tcpConnect = String.valueOf(connectEnd-connectStart);
			//System.out.println("tcpConnect: "+tcpConnect);
		}
		if (responseStart>=0 || requestStart>=0) {
			//requestTime = (int) (responseStart-requestStart);
			requestTime = String.valueOf(responseStart-requestStart);
			//System.out.println("requestTime: "+requestTime);
		}
		if (responseEnd>=0 || requestStart>=0) {
			//serverTime = (int) (responseEnd-requestStart);
			serverTime = String.valueOf(responseEnd-requestStart);
			//System.out.println("serverTime: "+serverTime);
		}
		if (responseEnd>=0 || responseStart>=0) {
			//responseTransmit = (int) (responseEnd-responseStart);
			responseTransmit = String.valueOf(responseEnd-responseStart);
			//System.out.println("responseTransmit: "+responseTransmit);
		}
		if (domInteractive>=0 || domLoading>=0) {
			//domLoad2Inter = (int) (domInteractive-domLoading);
			domLoad2Inter = String.valueOf(domInteractive-domLoading);
			//System.out.println("domLoad2Inter: "+domLoad2Inter);
		}
		if (domContentLoadedEventEnd>=0 || domLoading>=0) {
			//domLoad2DomLoaded = (int) (domContentLoadedEventEnd-domLoading);
			domLoad2DomLoaded = String.valueOf(domContentLoadedEventEnd-domLoading);
			//System.out.println("domLoad2DomLoaded: "+domLoad2DomLoaded);
		}
		if (domComplete>=0 || domLoading>=0) {
			//domLoad2Complete = (int) (domComplete-domLoading);
			domLoad2Complete = String.valueOf(domComplete-domLoading);
			//System.out.println("domLoad2Complete: "+domLoad2Complete);
		}
		if (loadEventEnd>=0 || loadEventStart>=0) {
			//onloadExecute = (int) (loadEventEnd-loadEventStart);
			onloadExecute = String.valueOf(loadEventEnd-loadEventStart);
			//System.out.println("onloadExecute: "+onloadExecute);
		}
		if (requestStart>=0 || responseStart>=0) {
			//ttFirstByte = (int) (responseStart-navigationStart);
			ttFirstByte = String.valueOf(responseStart-requestStart);
			//System.out.println("ttFirstByte: "+ttFirstByte);
		}
		if (msFirstPaint>=0 || navigationStart>=0) {
			//ttFirstImpression = (int) (msFirstPaint-navigationStart);
			ttFirstImpression = String.valueOf(msFirstPaint-navigationStart);
			//System.out.println("ttFirstImpression: "+ttFirstImpression);
		}
		if (loadEventStart>=0 || navigationStart>=0) {
			//ttOnLoad = (int) (loadEventStart-navigationStart);
			ttOnLoad = String.valueOf(loadEventStart-navigationStart);
			//System.out.println("ttOnLoad: "+ttOnLoad);
		}
		if (loadEventEnd>=0 || navigationStart>=0) {
			//Total = (int) (loadEventEnd-navigationStart);
			Total = String.valueOf(loadEventEnd-navigationStart);
			//System.out.println("Total: "+Total);
		}
		if (unloadEventEnd>=0 || unloadEventStart>=0) {
			//unLoadTime = (int) (unloadEventEnd-unloadEventStart);
			unLoadTime = String.valueOf(unloadEventEnd-unloadEventStart);
			//System.out.println("unLoadTime: "+unLoadTime);
		}
		if (connectEnd>=0 || navigationStart>=0) {
			//unLoadTime = (int) (unloadEventEnd-unloadEventStart);
			connectTime = String.valueOf(connectEnd-navigationStart);
			//System.out.println("unLoadTime: "+unLoadTime);
		}
		if (responseEnd>=0 || requestStart>=0) {
			//unLoadTime = (int) (unloadEventEnd-unloadEventStart);
			totalServerTime = String.valueOf(responseEnd-requestStart);
			//System.out.println("unLoadTime: "+unLoadTime);
		}
		if (loadEventStart>=0 || responseEnd>=0) {
			//unLoadTime = (int) (unloadEventEnd-unloadEventStart);
			clientOnLoadTime = String.valueOf(loadEventStart-responseEnd);
			//System.out.println("unLoadTime: "+unLoadTime);
		}
		if (loadEventStart>=0 || responseEnd>=0) {
			//unLoadTime = (int) (unloadEventEnd-unloadEventStart);
			clientTotalTime = String.valueOf(loadEventEnd-responseEnd);
			//System.out.println("unLoadTime: "+unLoadTime);
		}
		
		if (requestStart >= 0 || navigationStart >= 0) {

			initialConnection = String.valueOf(requestStart - navigationStart);
			System.out.println("initialConnection: " + initialConnection);
		}

		if (loadEventStart >= 0 || domLoading >= 0) {

			DomComplete = String.valueOf(loadEventStart - domLoading);
			System.out.println("DomComplete: " + DomComplete);
		}

		if (responseEnd >= 0 || responseStart >= 0) {

			DownloadTime = String.valueOf(responseEnd - responseStart);
			System.out.println("DownloadTime: " + DownloadTime);
		}
		
		if (timersEnabled) {
			
			
			if (PageName.isEmpty()==false) {
				//Print the Performance Timing Values
				try {
					timerlog.append(requiredFormat.format(dateTime)+"\t"+browser+"\t"+ENV+"\t"+BUILD+"\t"+PageName+"\t"+ttFirstByte+"\t"+ttFirstImpression+"\t"+ttOnLoad+"\t"+Total+"\t"+redirect+"\t"+AppCache+"\t"+dnsLookup+
							"\t"+tcpConnect+"\t"+requestTime+"\t"+serverTime+"\t"+responseTransmit+"\t"+domLoad2Inter+"\t"+domLoad2DomLoaded+"\t"+domLoad2Complete+"\t"+onloadExecute+
							"\t"+unLoadTime+"\t"+TESTURL+"\t\t"+connectTime+"\t"+totalServerTime+"\t"+clientOnLoadTime+"\t"+clientTotalTime+"\t"+initialConnection+"\t"+DomComplete+"\t"+DownloadTime+"\n");
		
					timerlog.flush();
		
				} catch (IOException e1) {
					System.out.println("***Failed to wrtite Performance Timings to file.***");
					e1.printStackTrace();
				}
			}
		}
		
		System.out.println("\t"+PageName + " Loaded in: "+Total+"ms\t\t("+loadEventEnd+" - "+navigationStart+")");
		
		//Print all timers if results seem fishy
		if (navigationStart==0 || loadEventEnd==0 || (loadEventEnd-navigationStart) <0 || (loadEventEnd-navigationStart) > 80000) {
			System.out.println("   responseEnd: "+responseEnd);
			System.out.println("   msFirstPaint: "+msFirstPaint);
			System.out.println("   domLoading: "+domLoading);
			System.out.println("   domComplete: "+domComplete);
			System.out.println("   domInteractive: "+domInteractive);
			System.out.println("   responseStart: "+responseStart);
			System.out.println("   requestStart: "+requestStart);
			System.out.println("   domainLookupStart: "+domainLookupStart);
			System.out.println("   domainLookupEnd: "+domainLookupEnd);
			System.out.println("   connectStart: "+connectStart);
			System.out.println("   domContentLoadedEventEnd: "+domContentLoadedEventEnd);
			System.out.println("   connectEnd: "+connectEnd);
			System.out.println("   fetchStart: "+fetchStart);
			System.out.println("   unloadEventEnd: "+unloadEventEnd);
			System.out.println("   unloadEventStart: "+unloadEventStart);
			System.out.println("   redirectEnd: "+redirectEnd);
			System.out.println("   redirectStart: "+redirectStart);
			System.out.println("   loadEventStart: "+loadEventStart);
			System.out.println("   navigationStart: "+navigationStart);
			System.out.println("   loadEventEnd: "+loadEventEnd);
			System.out.println("   domContentLoadedEventStart: "+domContentLoadedEventStart);
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		browserdata.put("DOMLoadingtoComplete", domLoad2Complete);
		browserdata.put("DOMLoadingtoLoaded", domLoad2DomLoaded);
		browserdata.put("ClientonLoadTime", clientOnLoadTime);
		browserdata.put("onLoadTime", ttOnLoad);
		browserdata.put("CacheLookup", AppCache);
		browserdata.put("onLoadExecuteTime", onloadExecute);
		browserdata.put("PageName", PageName);
		browserdata.put("DNSLookup", dnsLookup);
		browserdata.put("RedirectTime", redirect);
		browserdata.put("Build", BUILD);
		browserdata.put("ResponseTransmitTime", responseTransmit);
		browserdata.put("unLoadTime", unLoadTime);
		browserdata.put("InitialConnection", initialConnection);
		browserdata.put("ConnectTime", connectTime);
		browserdata.put("TestAgent", "hello");
		browserdata.put("TimeToFirstByte", ttFirstByte);
		browserdata.put("ClientTotalTime", clientTotalTime);
		browserdata.put("DomComplete", DomComplete);
		browserdata.put("TotalLoadTime", Total);
		browserdata.put("TCPConnect", tcpConnect);
		browserdata.put("Date/Time", requiredFormat.format(dateTime));
		browserdata.put("RequestSubmit", requestTime);
		browserdata.put("ServerTime", serverTime);
		browserdata.put("TestURL", TESTURL);
		browserdata.put("DownloadTime", DownloadTime);
		browserdata.put("Environment", ENV);
		browserdata.put("FirstImpression", ttFirstImpression);
		browserdata.put("TotalServerTime", totalServerTime);
		browserdata.put("Browser", browser);
		browserdata.put("DOMLoadingtoInteractive", domLoad2Inter);
		browserdata.put("PageSize", temp);
		browserdata.put("nwrequest", jsonarray.length());
		browserdata.put("ScenarioTestcase", scenarioTestcase);
		
		
		
		return browserdata;
	}
	
	public String responseAPI(WebDriver driver) throws JSONException{
		int temp=0;
		int convert;
		String resourceAPI = null;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 30);
		
		JSONArray resourceapi=new JSONArray();
		try {
			resourceAPI = (String) js.executeScript("return JSON.stringify(performance.getEntriesByType('resource'))");
		System.out.println("   resourceAPI: "+js.executeScript("return JSON.stringify(performance.getEntriesByType('resource'))"));
		} catch (Exception e) {
			//resourceAPI = (String) js.executeScript("return JSON.stringify(performance.getEntriesByType('resource'))");
			e.printStackTrace();
		}
		
		org.json.JSONArray jsonarray = new org.json.JSONArray(resourceAPI);

		System.out.println("Length=========>"+jsonarray.length());
				    for(int i=0; i<jsonarray.length(); i++){
				        org.json.JSONObject obj = jsonarray.getJSONObject(i);

				        Integer size=(Integer) obj.get("transferSize");
				       temp = temp+size;
				      

				        
				        System.out.println(size);
				    }   
				    
				    System.out.println(temp);
				    convert=1024*1024;
				    int pagesize=(temp/convert);
				    System.out.println(pagesize);
		
		return resourceAPI;
		
		
	}
	
	public String CreateJson(JSONArray output)
	
	{
		
		String performancefile = "performance.json";
		File perf = new File(performancefile);

		if (perf.exists()) {
			System.out.println("performance.json File is created");
		}
		FileWriter writerperffile = null;
		try {
			writerperffile = new FileWriter(perf);
			writerperffile.write(output.toString());
			writerperffile.flush();
			writerperffile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(perf.getAbsolutePath());
		try {
			System.out.println(perf.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return performancefile;
		
		
	}
	
public String CreateJsonCache(JSONArray output)
	
	{
		
		String performancefile = "performancecache.json";
		File perf = new File(performancefile);

		if (perf.exists()) {
			System.out.println("performance.json File is created");
		}
		FileWriter writerperffile = null;
		try {
			writerperffile = new FileWriter(perf);
			writerperffile.write(output.toString());
			writerperffile.flush();
			writerperffile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return performancefile;
		
		
	}
	
	public  void ajaxPageTimer (String PageName, int loadTime) throws IOException {
		File file = new File("C:/AutomationScripts/datafile.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);	    		
		String ENV = properties.getProperty("ENV");
		String browser=properties.getProperty("browser");
		String BUILD=properties.getProperty("BUILD");
		
		if (browser.equals("IE8")) {
			System.out.println("\t"+PageName + " Loading");
			return;
		}
		
		dateTime = new Date();
		
		//Print the Performance Timing Values
		if (timersEnabled) {
			try {
				timerlog.append(requiredFormat.format(dateTime)+"\t"+browser+"\t"+ENV+"\t"+BUILD+"\t"+PageName+"\t\t\t"+loadTime+"\t"+loadTime+"\t\t\t\t\t\t\t\t\t\t\t\t\n");
	
				timerlog.flush();
	
			} catch (IOException e1) {
				System.out.println("***Failed to wrtite Performance Timings to file.***");
				e1.printStackTrace();
			}
		}
		
		System.out.println("\t"+PageName + " Loaded in: "+loadTime+"ms");
	
	}

	public  void setMarker(String marker)
	{
		if (dtEnabled==false) {
			return;
		}
		try {
			js.executeAsyncScript("_dt_addMark('"+marker+"'); callback();");
		} catch (Exception e) {
			//System.out.println("***Unable to implement marker for "+ marker + ".***");
		}
	}

	public  void startTimer(String timer) 
	{
		if (dtEnabled==false) {
			return;
		}
		timerName = timer;

		try {
			js.executeAsyncScript("_dt_setTimerName('" + timer + "'); callback();");
		} catch (Exception e) {
			//System.out.println("***Unable to implement manual dT timer for "+ timer + ".***");
		}
		start = System.currentTimeMillis();
	}
	

	public  void stopTimer() 
	{
		if (dtEnabled==false) {
			return;
		}

		finish = System.currentTimeMillis();

		try {
			js.executeAsyncScript("_dt_setTimerName(''); callback();");
		} catch (Exception e) {
			//System.out.println("***Unable to stop manual dT timer.***");
			//e.printStackTrace();
		}
	}
	
	public void setJSX(JavascriptExecutor newJSX) {
		js = newJSX;
	}

	public String getBuild(String BUILD) throws IOException {
	
		return BUILD;
	}
	

		
		public void AgentData11(String Env,String URL) {
			URL = URL;;
			if(Env=="sample")
			{
				URL = URL;
			}
		}

		
		public String getURL () {
			return URL;
		}
	
		
		public String getProfile () {
			return PROFILE;
		}
		
		public static JSONObject TxtParser(File srcFile) throws IOException {
			// TODO Auto-generated method stub

			JSONObject finalobj = new JSONObject();
			// JSONObject finalobj=new JSONObject();
			JSONArray objarray = new JSONArray();
			FileReader fr = new FileReader(srcFile);
			BufferedReader br = new BufferedReader(fr);
			String header = null;
			String str;
			int count = 0;
			String[] names = null;
			String[] values = null;
			int lines = 0;

			while ((str = br.readLine()) != null) {

				if (str.contains("Date")) {
					header = str;
					lines++;
					str = br.readLine();
				}
				if (lines > 0) {
					JSONObject output = new JSONObject();
					System.out.println("lines" + lines);
					System.out.println("values str" + str);
					StringTokenizer strtkn = new StringTokenizer(header, "\t");
					StringTokenizer strtkn1 = new StringTokenizer(str, "\t");
					count = strtkn.countTokens();
					/*
					 * System.out.println("nnnn"+strtkn.countTokens());
					 * System.out.println("ppp"+strtkn1.countTokens());
					 */
					System.out.println("counttttt" + count);
					names = new String[count];
					values = new String[count];
					for (int i = 0; i < count; i++) {
						String name = strtkn.nextToken();
						String value = strtkn1.nextToken();
					/*	if (name.matches("Browser") || name.matches("PageName")
								|| name.matches("TimeToFirstByte")
								|| name.matches("RedirectTime")
								|| name.matches("TestURL")
								|| name.matches("TotalServerTime")) {*/
							System.out.println("before values" + name + value);
							names[i] = name;
							values[i] = value;
							output.put(name, value);
							
							// objarray.add(output);
						}
					//}
					/*if(recommarray.size()!=0)
					{
					output.put("recommendations", recommarray);
					}
					else if(recommarray.size()==0)
					{
						output.put("recommendations", "Reccomendations not available");
					}
					
					if(mitiarray.size()!=0)
					{
					output.put("mitigation", mitiarray);
					}
					else if(mitiarray.size()==0)
					{
						output.put("mitigation", "Mitigation not available");	
					}
*/
					

					System.out.println("output json at each i" + output);
					objarray.add(output);
					/*objarray.add(recommarray);*/
					
				}

			}
			finalobj.put("output", objarray);
			/*finalobj.put("recommendations", recommarray);*/
			System.out.println("finallyyyy" + objarray);
			
			
			File file = new File("C:/Jar/datafile.properties");
		    FileInputStream fileInput = new FileInputStream(file);
		    Properties properties = new Properties();
		    properties.load(fileInput);
			//String performancefile = properties.getProperty("path")+"\\"+"performance.json";
		   //String performancefile ="C:\\Users\\Sindhuja.Raja\\Documents\\AccessibilityReport\\CTS\\performance.json";
		  String performancefile ="performancecache.json";
			System.out.println(performancefile);
			File perf = new File(performancefile);

			if (perf.exists()) {
				System.out.println("performance.json File is created");
			}
			FileWriter writerperffile = null;
			try {
				writerperffile = new FileWriter(perf);
				writerperffile.write(finalobj.toString());
				writerperffile.flush();
				writerperffile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// finalobj=(JSONObject)(objarray);
			/*
			 * for(int i=0;i<objarray.size();i++) { finalobj.put( objarray)); }
			 */
			return finalobj;
		}
		
		
		public static JSONObject TxtParserWithCache(File srcFile,JSONArray recommarray,JSONArray mitiarray) throws IOException {
			// TODO Auto-generated method stub

			JSONObject finalobj = new JSONObject();
			// JSONObject finalobj=new JSONObject();
			JSONArray objarray = new JSONArray();
			FileReader fr = new FileReader(srcFile);
			BufferedReader br = new BufferedReader(fr);
			String header = null;
			String str;
			int count = 0;
			String[] names = null;
			String[] values = null;
			int lines = 0;

			while ((str = br.readLine()) != null) {

				if (str.contains("Date")) {
					header = str;
					lines++;
					str = br.readLine();
				}
				if (lines > 0) {
					JSONObject output = new JSONObject();
					System.out.println("lines" + lines);
					System.out.println("values str" + str);
					StringTokenizer strtkn = new StringTokenizer(header, "\t");
					StringTokenizer strtkn1 = new StringTokenizer(str, "\t");
					count = strtkn.countTokens();
					/*
					 * System.out.println("nnnn"+strtkn.countTokens());
					 * System.out.println("ppp"+strtkn1.countTokens());
					 */
					System.out.println("counttttt" + count);
					names = new String[count];
					values = new String[count];
					for (int i = 0; i < count; i++) {
						String name = strtkn.nextToken();
						String value = strtkn1.nextToken();
					/*	if (name.matches("Browser") || name.matches("PageName")
								|| name.matches("TimeToFirstByte")
								|| name.matches("RedirectTime")
								|| name.matches("TestURL")
								|| name.matches("TotalServerTime")) {*/
							System.out.println("before values" + name + value);
							names[i] = name;
							values[i] = value;
							output.put(name, value);
							
							// objarray.add(output);
						}
					//}
					
					if(recommarray.size()!=0)
					{
					output.put("recommendations", recommarray);
					}
					else if(recommarray.size()==0)
					{
						output.put("recommendations", "Reccomendations not available");
					}
					
					if(mitiarray.size()!=0)
					{
					output.put("mitigation", mitiarray);
					}
					else if(mitiarray.size()==0)
					{
						output.put("mitigation", "Mitigation not available");	
					}

					System.out.println("output json at each i" + output);
					objarray.add(output);
					/*objarray.add(recommarray);*/
					
				}

			}
			finalobj.put("output", objarray);
			/*finalobj.put("recommendations", recommarray);*/
			System.out.println("finallyyyy" + objarray);
			
			
			
			// finalobj=(JSONObject)(objarray);
			/*
			 * for(int i=0;i<objarray.size();i++) { finalobj.put( objarray)); }
			 */
			return finalobj;
		}
		
		
		
	}




