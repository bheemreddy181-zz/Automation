package com.automation.webservice.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
//import javax.xml.soap.SOAPPart;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Request {
	private SOAPService soapSpecification ;
	private RestService restSpecification ;
	private CloseableHttpClient httpClient  = HttpClients.createDefault();
	private HttpRequestBase httpRequest ;

	public Request(SOAPService soapSpecification) {
		this.soapSpecification = soapSpecification;
	}

	public Request(RestService restSpecification) {
		this.restSpecification = restSpecification;
	}


	/**
	 * Method to Call a SOAP webservice
	 * @return
	 */
	public Response doCall(){
		SOAPMessage soapResponse = null;
		Response response = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try{
			soapResponse = soapRequestCall(soapSpecification.getUrl(), prepareSoapMessage(soapSpecification.getHeader(),soapSpecification.getBody()));
			soapResponse.writeTo(stream);
			String message = new String(stream.toByteArray(), "utf-8");
			response = new Response(message, soapResponse.getMimeHeaders());
		}
		catch(Exception e){
			System.out.println("Error in SOAP call !!");
		}
		return response;
	}



	/**
	 * Method to call a GET RESTFUL service
	 * @return
	 */
	public Response doGet(){
		Response response = null;
		String call = "GET";
		try{
			httpRequest = new HttpGet(restSpecification.getUrl());
			buildRestHttpHeader();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			response = buildRestResponse(httpResponse);
		}
		catch(Exception e){
			System.out.println(" Error in Get Request");
			e.printStackTrace();
		}
		return response;
	}


	/**
	 * Method to call REST POST service
	 * @return
	 */

	public Response doPost(){
		Response response = null;
		String call = "POST";
		try{
			httpRequest = new HttpPost(restSpecification.getUrl());
			if(restSpecification.getPair().isEmpty()){
				buildRestHttpHeader();
				HttpEntity httpEntity = buildRestEntity();
				((HttpPost)httpRequest).setEntity(httpEntity);
			}else{
				((HttpPost) httpRequest).setEntity(new UrlEncodedFormEntity(restSpecification.getPair(), "UTF-8"));
			}
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			response = buildRestResponse(httpResponse);
		}
		catch(Exception e){
			System.out.println("Error in POST request");
			e.printStackTrace();
		}
		return response;
	}


	/**
	 * Build the payload
	 * @return
	 */
	private HttpEntity buildRestEntity() {
		String content = restSpecification.getContent();
		HttpEntity httpEntity = null;
		if(content != null){
			httpEntity = new ByteArrayEntity(content.getBytes(Charsets.UTF_8));
		}
		return httpEntity;
	}



	/**
	 * Build the headers
	 * 
	 */

	private void buildRestHttpHeader(){
		Map<String,String> header = restSpecification.getHeader();
		Set<String> headerKeySet = header.keySet();
		for(String key : headerKeySet){
			String value = header.get(key);
			httpRequest.addHeader(key,value);
		}
	}


	/**
	 * Build the custom response Object
	 * @param responseObject
	 * @return
	 */
	private <T> Response buildRestResponse(T responseObject){
		Response response = null;
		if(responseObject instanceof HttpResponse){
			HttpResponse httpResponse = (HttpResponse) responseObject;
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			HttpEntity entity = httpResponse.getEntity();
			Map<String,String> responseHeader = null;
			String responseContent = "";
			InputStream inputStream = null;
			InputStream inputStreamString = null;
			InputStream inputStreamEntity = null;
			if(entity != null){
				try{
					inputStream = entity.getContent();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(inputStream, baos);
					inputStreamString = new ByteArrayInputStream(baos.toByteArray());
					inputStreamEntity = new ByteArrayInputStream(baos.toByteArray());
					responseContent = (inputStreamString == null)? "" : IOUtils.toString(inputStreamString, Charsets.UTF_8);
				}
				catch(Exception e){
					System.out.println("Exception occured while parsing Http Response");
				}
			}
			Header[] headers = httpResponse.getAllHeaders();
			responseHeader = new HashMap();
			for(Header responseheaderTemp :  headers){
				responseHeader.put(responseheaderTemp.getName(), responseheaderTemp.getValue());
			}
			response = new Response(responseCode, responseContent, responseHeader);
			response.setEntityInputStream(inputStreamEntity);
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(inputStreamString);
		}else{
			response = new Response(responseObject);
		}
		return response;
	}




	private SOAPMessage soapRequestCall( String url, SOAPMessage request){
		SOAPMessage response = null;
		SOAPConnectionFactory factory = null;
		SOAPConnection conn = null;
		try{
			factory = SOAPConnectionFactory.newInstance();
			conn = factory.createConnection();
			response = conn.call(request, url);
			conn.close();
		}
		catch(Exception e){
			System.out.println("SOAP Conn Issue !!!");
		}
		return response;
	}

	private SOAPMessage prepareSoapMessage(Map<String, String> header, String body) {
		SOAPMessage soapMessage = null;
		try{
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			InputStream stream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
			soapMessage = messageFactory.createMessage(null,stream);
			MimeHeaders mime = soapMessage.getMimeHeaders();

			Set<String> name = header.keySet();
			for (String key : name) {
				String value = header.get(key);
				mime.addHeader(key, value);
			}
			soapMessage.saveChanges();
		}
		catch(Exception e){
			System.out.println("Error in preparing SOAP message !!" );
		}
		return soapMessage;
	}



}
