package com.automation.webservice.utilities;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

 
public class YamlHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YamlHandler.class);
	private String newYamlFilePath;
	private Map<String, Object> newYamlMap;
	
	
	
	/**
	 * Initialize the file
	 * @param yamlFilePath
	 */
	public YamlHandler(String yamlFilePath) {
		newYamlFilePath = yamlFilePath;
		Yaml yaml = new Yaml();
		try {
			LOGGER.info(getClass().getCanonicalName());
			InputStream file = getClass().getResourceAsStream(newYamlFilePath);
			if (Commons.isNull(file) && !newYamlFilePath.startsWith("/")) {
				newYamlFilePath = "/" + newYamlFilePath;
				file = getClass().getResourceAsStream(newYamlFilePath);
			}
			if (Commons.isNull(file)) {
				String error = "Invalid file path specified.. ("+ newYamlFilePath + ")";
				LOGGER.error(error);
				throw new Exception(error);
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> yamlMap = (Map<String, Object>) yaml.load(file);
			newYamlMap = yamlMap;
			file.close();
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	
	
	public Map<String,Object> getYamlMap(){
		return newYamlMap;
	}
	
	
	public String getYamlFilePath(){
		return newYamlFilePath;
	}
	
	
	@SuppressWarnings("unchecked")
	public Object fetchObject(String key) {
		Object currValue = newYamlMap;
		String[] path = key.split("\\.");
		for (String pathSection : path) {
			Object value = ((Map<String, Object>) currValue).get(pathSection);
			if (value == null && StringUtils.isNumericSpace(pathSection)) {
				value = ((Map<String, Object>) currValue).get(Integer.parseInt(pathSection));
			}
			currValue = value;
			if (currValue == null)
				break;
		}
		if (Commons.isNull(currValue)) {
			String error = "Key (" + key + ") is not found in YAML ("+ newYamlFilePath + "). Update the script or Yaml.";
			LOGGER.error(error);
			try {
				throw new Exception(error);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return currValue;
	}
	
	
	public String fetchString(String key){
		String out = "";
		String error = "";
		try{
			out = (String)fetchObject(key);
		}
		catch(ClassCastException e){
			error = "Error in fetching string value for key ("+key+") ." ;
			LOGGER.error(error);
		}
		return out;
		}
	}
	

