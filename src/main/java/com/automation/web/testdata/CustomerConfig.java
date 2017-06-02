package com.automation.web.testdata;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class CustomerConfig {

	private static final  Logger logger = Logger.getLogger(CustomerConfig.class);
	private static final String PROP_FILE = "run.props";
    
    private CustomerConfig() {}

public static String loadProperty(String name) {
	Properties props = new Properties();
	try {
		props.load(CustomerConfig.class.getClassLoader().getResourceAsStream(PROP_FILE));
	} catch (IOException e) {
		e.printStackTrace();
	}
	String value = "";
	if (name != null) {
		 value = System.getProperty(name);
		if ((value == null)) {
			value = props.getProperty(name);
		}
		props.setProperty(name, value);
		logger.info("Property Name: "+ name+" with Value :"+value);
	}
	return value;
}
	

}
