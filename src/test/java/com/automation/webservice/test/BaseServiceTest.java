package com.automation.webservice.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import com.automation.webservice.base.CommonBase;
import com.automation.webservice.utilities.JsonRetriever;
import com.automation.webservice.utilities.Response;
import com.automation.webservice.utilities.RestService;
import com.automation.webservice.utilities.YamlHandler;



public class BaseServiceTest {
	
	private static final  Logger logger = Logger.getLogger(BaseServiceTest.class);
	protected String accessToken;
	protected String tokenType;
	static String TEST_ENV = "QA";
	public static final String SERVICES_ENV_PATH = "config/services.yml";
	private static Map<String, String> POST_HEADER = new HashMap<String, String>(); 
	private static YamlHandler SERVICES_ENV_YML = new YamlHandler(SERVICES_ENV_PATH);
	private static List<NameValuePair> Pair = new ArrayList<NameValuePair>();
	private static Map<String,Object> YELP_AUTH = CommonBase.loadServiceDetails(SERVICES_ENV_YML, TEST_ENV);
	

	@BeforeClass(alwaysRun = true)
	public void init() {
		POST_HEADER.put("Content-Type","application/x-www-form-urlencoded");
		Pair.add(new BasicNameValuePair("client_id", "GfVxCYIlxT-3I_6HzyJWdw"));
		Pair.add(new BasicNameValuePair("client_secret", "V8G1UoO9W3sizNdbsjsMQas3G03TpGbQVEIHdRmdbUsvM6bQp7sN1RMdOj30tHEL"));
		Pair.add(new BasicNameValuePair("grant_type", "client_credentials"));
		String url = "https://"+YELP_AUTH.get("host")+""+YELP_AUTH.get("pathparam");
		Response resp =new RestService().setHeader(POST_HEADER).setPair(Pair).post(url);
		logger.info("StatusCode:"+resp.getStatusCode());
		tokenType= JsonRetriever.get(resp.getBody(), "token_type").toString();
		accessToken= JsonRetriever.get(resp.getBody(), "access_token").toString();
		logger.info("access_token:"+accessToken);
		logger.info("expires_in:"+JsonRetriever.get(resp.getBody(), "expires_in"));
		logger.info("token_type:"+tokenType);
		logger.info("Getting Access Token is complete");
	}

	@AfterTest(alwaysRun = true)
	public void tearDown() {
		logger.info("test Complete");
	}
}