package com.automation.webservice.utilities;

import java.util.Hashtable;
import java.util.Set;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.path.xml.config.XmlPathConfig;
import com.jayway.restassured.path.xml.element.Node;

public class XmlRetriever {
	
	public static Hashtable<String, String> parser(Hashtable<String, String> key,String rootnode,String document){
		Hashtable<String, String> KeyValues = new Hashtable<String, String>();
		try{
			Set<String> keys = key.keySet();
			for(String  str : keys){
				if(document.startsWith("<")){
					XmlPath xmlPath = new XmlPath(document).setRoot(rootnode).using(new XmlPathConfig("UTF-8"));
					Node node = xmlPath.getNode(rootnode);
					KeyValues.put(key.get(str), node.<String>getPath(str));
				}
				else if (document.startsWith("{")){
					JsonPath jsonPath = new JsonPath(document).using(new JsonPathConfig("UTF-8"));
					String node = jsonPath.getString(str);	
					KeyValues.put(key.get(str),node);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(document+"***Exception for Document");
		}
		return KeyValues;
	}

}
