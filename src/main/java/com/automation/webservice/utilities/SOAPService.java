package com.automation.webservice.utilities;

import java.util.HashMap;
import java.util.Map;

public class SOAPService {
	private Map<String,String> header = new HashMap<>();
	private String body = null;
	private String url = null;
	
	public SOAPService(){
		
	}
	
	public SOAPService setHeaders(Map<String,String> header){
		this.header = header;
		return this;
	}
	
	public SOAPService setBody(String soapMsg){
		this.body = soapMsg;
		return this;
	}
	
	public Response call(String url){
		this.url = url;
		return new Request(this).doCall();
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}

	public String getUrl() {
		return url;
	}
	
	
}
