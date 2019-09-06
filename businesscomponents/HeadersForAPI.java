package businesscomponents;

import java.util.HashMap;
import java.util.Map;

public class HeadersForAPI {

	public Map<String, String> getHeaders1() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Accept", "application/json;text/xml");
		map.put("Content-Type", "audio/wav; codec=audio/pcm; samplerate=16000");
		map.put("Expect", "100-continue");
		map.put("Ocp-Apim-Subscription-Key", "870bf44973ee40d7939ae13f07249a71");
		map.put("Host", "speech.platform.bing.com");
		return map;
	}

	public Map<String, String> getHeaders2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "text/xml");
		return map;
	}

	public Map<String, String> getHeaders3() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
		return map;
	}

	public Map<String, String> getHeaders4() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "text/xml");
		map.put("SOAPAction", "https://www.w3schools.com/xml/CelsiusToFahrenheit");
		return map;
	}

}
