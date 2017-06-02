package com.automation.webservice.utilities;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.Charsets;
import org.apache.http.NameValuePair;

public class RestService {

	private List<NameValuePair> Pair = new ArrayList<NameValuePair>();
	private Map<String,String> header = new HashMap<>();
	private String content = null;
	private String url = null;
	private Charset charset = null;

	public RestService(){

	}

	
	public List<NameValuePair> getPair() {
		return Pair;
	}


	public RestService setPair(List<NameValuePair> pair) {
		this.Pair=pair;
		return this;
	}


	public RestService setHeader(Map<String,String> header){
		this.header = header;
		return this;
	}

	public Map<String,String> getHeader(){
		return header;
	}

	public String getUrl(){
		return url;
	}

	@SuppressWarnings("deprecation")
	public RestService setBody(String content){
		return setBody(content, Charsets.UTF_8);
	}

	public RestService setBody(String content, Charset charset){
		this.content = content;
		this.setCharSet(charset);
		return this;
	}

	public String getContent() {
		return content;
	}

	public Response post(String url){
		this.url = url;
		return new Request(this).doPost();
	}


	public Response get(String url){
		this.url = url;
		return new Request(this).doGet();
	}


	void setCharSet(Charset charset){
		this.charset = charset;
	}

}
