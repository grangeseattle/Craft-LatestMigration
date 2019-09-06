package com.cognizant.framework.nft;

import static com.cognizant.framework.nft.PassiveScan.zap_passivescan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.xml.sax.SAXException;
import org.zaproxy.clientapi.core.ClientApiException;

import com.cognizant.craft.ScriptHelper;
import com.cognizant.framework.NFT;
import com.cognizant.framework.Settings;
public class SecurityNFT {

    private static boolean isSecurityExecutedForEnv1 = false;
	private static boolean isSecurityExecutedForEnv2 = false;

	private static String Env1 = "";
	private static String Env2 = "";

	public static void main(String[] args) {
//		SecurityNFT.evaluateSecurityForPage("https://www.google.com", scriptHelper);
//		SecurityNFT.evaluateSecurityForPage("https://www.google.com", "On_Premise", "Tc1",
//				"D:\\chrome_driver\\chromedriver_2.45.exe");
//		copyReportTo(".\\..\\");
	}

	/**
	 * Description:This method tests the security of AUT page
	 * 
	 * @param testURL
	 * @param scriptHelper TODO
	 */
	public static synchronized void evaluateSecurityForPage(String testURL, ScriptHelper scriptHelper) {

		if (NFT.executeSecurity) {
			Properties propertiesGlobal = Settings.getInstance();
			String environment = scriptHelper.getReport().getSeleniumTestParameters().getEnvironment();
			try {
				if (!testURL.contains("data")) {

					InputStream fileInput = PerformanceNFT.class.getResourceAsStream("datafile_sec.properties");
					Properties properties = new Properties();
					properties.load(fileInput);

					// this is for executing the code only once for each
					// enviromment
					Proxy proxy = new Proxy();
					proxy.setHttpProxy("http://" + properties.getProperty("ZAP_ADDRESS") + ":"
							+ properties.getProperty("ZAP_PORT"));
					proxy.setSslProxy("http://" + properties.getProperty("ZAP_ADDRESS") + ":"
							+ properties.getProperty("ZAP_PORT"));
					proxy.setSocksProxy("http://" + properties.getProperty("ZAP_ADDRESS") + ":"
							+ properties.getProperty("ZAP_PORT"));
					DesiredCapabilities capabilities = new DesiredCapabilities();
					capabilities.setCapability(CapabilityType.PROXY, proxy);
					System.setProperty("webdriver.chrome.driver", propertiesGlobal.getProperty("ChromeDriverPath"));

					if ((!isSecurityExecutedForEnv1 && Env1 == "")) {

						File envListFile = new File("environments.json");
						if (envListFile.exists()) {
							envListFile.delete();
						}

						WebDriver driver = new ChromeDriver(capabilities);
						try {
							driver.get(testURL);
							zap_passivescan(environment, testURL);
							driver.quit();
						}

						catch (ClientApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Env1 = environment;
						isSecurityExecutedForEnv1 = true;
						driver.quit();
						return;
					}

					if ((!isSecurityExecutedForEnv2 && Env2 == ""
							&& (Env1 != null && !environment.equalsIgnoreCase(Env1)))) {
						WebDriver driver = new ChromeDriver(capabilities);
						try {
							// HomePage
							driver.get(testURL);
							zap_passivescan(environment, testURL);
							driver.quit();
						}

						catch (ClientApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Env2 = environment;
						isSecurityExecutedForEnv2 = true;
						driver.quit();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void copyReportTo(String destinationPath) {

		String enviroment1 = null, enviroment2 = null;
		File envListFile = new File("environments.json");
		JSONArray envList = null;
		if (envListFile.exists()) {
			JSONParser jsonParser = new JSONParser();

			try (FileReader reader = new FileReader(envListFile)) {
				// Read JSON file
				Object obj = jsonParser.parse(reader);
				envList = (JSONArray) obj;
				if(envList.size()==2){
				enviroment1 = envList.get(0).toString().split("--")[0];
				enviroment2 = envList.get(1).toString().split("--")[0];
				}else if(envList.size()==1)
				{
					enviroment1 = envList.get(0).toString().split("--")[0];
						
				}else
				{
					throw new Exception("environments count must not be Zero");
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			File envFile = new File("environments.json");

			File html1File = new File("htmlreport_" + enviroment1 + ".html");
			File html2File = new File("htmlreport_" + enviroment2 + ".html");
			File xml1File = new File("xmlreport_" + enviroment1 + ".xml");
			File xm2File = new File("xmlreport_" + enviroment2 + ".xml");
			File zap1File = new File("zap_" + enviroment1 + ".json");
			File zap2File = new File("zap_" + enviroment2 + ".json");
			File zapraw1File = new File("zapraw_" + enviroment1 + ".json");
			File zapraw2File = new File("zapraw_" + enviroment2 + ".json");
//		System.out.println("-----------"+new File(destinationPath).getAbsolutePath());
			try {

				FileUtils.copyFileToDirectory(envFile, new File(destinationPath));
				if (enviroment1 != null) {
					FileUtils.copyFileToDirectory(html1File, new File(destinationPath));
					FileUtils.copyFileToDirectory(xml1File, new File(destinationPath));
					FileUtils.copyFileToDirectory(zap1File, new File(destinationPath));
					FileUtils.copyFileToDirectory(zapraw1File, new File(destinationPath));
					FileUtils.forceDelete(html1File);
					FileUtils.forceDelete(xml1File);
					FileUtils.forceDelete(zap1File);
					FileUtils.forceDelete(zapraw1File);
					
				}

				if (enviroment2 != null) {
					FileUtils.copyFileToDirectory(html2File, new File(destinationPath));
					FileUtils.copyFileToDirectory(xm2File, new File(destinationPath));
					FileUtils.copyFileToDirectory(zap2File, new File(destinationPath));
					FileUtils.copyFileToDirectory(zapraw2File, new File(destinationPath));
					FileUtils.forceDelete(html2File);
					FileUtils.forceDelete(xm2File);
					FileUtils.forceDelete(zap2File);
					FileUtils.forceDelete(zapraw2File);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

}
