package com.automation.webservice.utilities;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


public class PropertyFileUtil {

	Properties properties = new Properties();
	private Map<String,String> objMap = new HashMap<String, String>(); 
	InputStream input = null;
	static OutputStream output = null;
	
	public PropertyFileUtil(String fileName){
		try{
			input = getClass().getClassLoader().getResourceAsStream(fileName);
			properties.load(input);
			loadProperties();
		}
		catch(Exception e){
			
		}
	}
	
	public Map<String,String> getConfigMapping(){
		return objMap;
	}
	
	private void loadProperties(){
		for (Entry<Object, Object> entry : properties.entrySet()) {
			objMap.put((String)entry.getKey(), (String)entry.getValue());
			
		}
	}
	
	
	
}
