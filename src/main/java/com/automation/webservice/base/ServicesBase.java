package com.automation.webservice.base;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automation.webservice.common.ServicesBaseHelper;
import com.automation.webservice.utilities.YamlHandler;



public class ServicesBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicesBase.class);
	// Get the run environment
	private static final String TEST_ENV = "QA";//System.getProperty("testEnv");
	// Get the reference file for env setup
	private static final String ENV_PATH = "config/services_env.yml";
	private static YamlHandler ENV_YML = new YamlHandler(ENV_PATH);
	public static Map<String,String> YELPSERVICEDETAILS = ServicesBaseHelper.loadRestServiceKeys(ENV_YML, TEST_ENV);
	
	
	
}
