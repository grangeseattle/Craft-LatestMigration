package businesscomponents;

import java.io.IOException;

import com.cognizant.craft.ReusableLibrary;
import com.cognizant.craft.ScriptHelper;

public class Autocrawl extends ReusableLibrary {
	public Autocrawl(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}

	public void test() {
		// if proxy please send proxy ip and port else null
		try {
//			 validateAllJsoupLinksInPage("https://learn.aumentum.thomsonreuters.com/Content1/help/Content/Home-SideNav.htm");
//			validateAllJsoupLinksInPage("https://economictimes.indiatimes.com/");
			 
			 validateAllJsoupLinksInPage("https://www.voya.com/");
//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
