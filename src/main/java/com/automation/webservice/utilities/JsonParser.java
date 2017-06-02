package com.automation.webservice.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);
	private JsonParser(){

	}

	public static JSONArray getJSONArray(String JSON,String ArrayheadElement){
		JSONArray array = null;
		try{
			LOGGER.info("Getting Json Array");
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(JSON);
			JSONObject jsonObject = (JSONObject) obj;
			array = (JSONArray) jsonObject.get(ArrayheadElement); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return array;
	}

}
