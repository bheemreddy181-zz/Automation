package com.automation.webservice.base;

import java.util.HashMap;
import java.util.Map;
import com.automation.webservice.utilities.YamlHandler;

public class CommonBase {
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadServiceDetails(YamlHandler yml, String env) {
		Map<String, Object> envKeys = (Map<String, Object>) yml.fetchObject(env);
		Map<String, Object> YmlDetails = new HashMap<>();
		YmlDetails = (Map<String, Object>) envKeys.get("YELP_AUTH");
		return YmlDetails;
		
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadSearchServiceDetails(YamlHandler yml, String env) {
		Map<String, Object> envKeys = (Map<String, Object>) yml.fetchObject(env);
		Map<String, Object> YmlDetails = new HashMap<>();
		YmlDetails = (Map<String, Object>) envKeys.get("DELIVERY_SEARCH");
		return YmlDetails;
		
	}
	
	
	
}
