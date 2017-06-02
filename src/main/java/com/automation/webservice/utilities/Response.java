package com.automation.webservice.utilities;

import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Response {

	private int statusCode;
	private String body= "";
	private Map<String,String> header = new HashMap<>();
	private String omsGeneratedOrderId = "";
	private String responseCode = "";
	private Object responseBodyObject = null;
	private InputStream entityInputStream = null;
	/**
	 * Build a Response Object for SOAP call
	 * @param body
	 * @param header
	 */
	
	public Response(String body, MimeHeaders header){
		this.body = body;
		this.header = getSoapHeader(header);
//		this.responseCode = readResponse(getBody(), "ns:responseCode");
//		this.omsGeneratedOrderId = readResponse(getBody(), "ns:omsGeneratedOrderId");
	}

	
	/**
	 * Build a Response Object for REST call
	 * @param inputFile
	 * @param TagName
	 * @return
	 */
	public Response(int statusCode, String responseBody, Map<String,String> header){
		setStatusCode(statusCode);
		setBody(responseBody);
		setHeader(header);
	}
	
	public Response(Object responseBody){
		this.responseBodyObject = responseBody;
		if(responseBody instanceof String)
			this.body = (String) responseBody;
	}
	
	
	public static String readResponse(String inputFile, String TagName) {
		String XMLval = inputFile;
		String textVal = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource(new StringReader(XMLval)));
			doc.getDocumentElement().normalize();
			NodeList listOfNodes = doc.getElementsByTagName(TagName);

			if (listOfNodes != null && listOfNodes.getLength() == 1) {
				Node el = listOfNodes.item(0);
				textVal = el.getFirstChild().getNodeValue();
			} else {
				System.out.println("values" + textVal);
			}
		} catch (NullPointerException e) {
			System.out.println("Exception" + e);
		} catch (ParserConfigurationException e) {
			System.out.println("Exception" + e);
		} catch (SAXException | IOException e) {
			System.out.println("Exception" + e);
		}
		return textVal;
	}
		
	


	private Map<String,String> getSoapHeader(MimeHeaders headers){
		Map<String,String> header = new HashMap<>();
		Iterator<?> itr = headers.getAllHeaders();
		while(itr.hasNext()){
			MimeHeader mime = (MimeHeader) itr.next();
			header.put(mime.getName(), mime.getValue());
		}
		return header;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}


	public String getOmsGeneratedOrderId() {
		return omsGeneratedOrderId;
	}


	public void setOmsGeneratedOrderId(String omsGeneratedOrderId) {
		this.omsGeneratedOrderId = omsGeneratedOrderId;
	}


	public String getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	
	public Object getResponseBodyObject() {
		return responseBodyObject;
	}


	public void setResponseBodyObject(Object responseBodyObject) {
		this.responseBodyObject = responseBodyObject;
	}


	public InputStream getEntityInputStream() {
		return entityInputStream;
	}


	public void setEntityInputStream(InputStream entityInputStream) {
		this.entityInputStream = entityInputStream;
	}


	protected String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }
	
	
}
