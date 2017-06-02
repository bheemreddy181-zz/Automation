package com.automation.webservice.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.webservice.utilities.YamlHandler;

public class ServicesBaseHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicesBaseHelper.class);
	public static Map<String, String> restkeys;
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> loadRestServiceKeys(YamlHandler yml, String testenv){
		LOGGER.info("Getting Rest Key Details");
		Map<String, Object> envKeys = (Map<String, Object>) yml.fetchObject(testenv);
		restkeys = (Map<String, String>) envKeys.get("YELP_AUTH");
		return restkeys;
	}
	

}
