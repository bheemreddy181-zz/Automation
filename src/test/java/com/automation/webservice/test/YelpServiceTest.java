package com.automation.webservice.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.web.testdata.TestConstants;
import com.automation.webservice.base.CommonBase;
import com.automation.webservice.utilities.JsonRetriever;
import com.automation.webservice.utilities.Response;
import com.automation.webservice.utilities.RestService;
import com.automation.webservice.utilities.YamlHandler;


public class YelpServiceTest extends BaseServiceTest {

	
	private static Map<String, String> POST_HEADER = new HashMap<String, String>(); 
	private static YamlHandler SERVICES_ENV_YML = new YamlHandler(SERVICES_ENV_PATH);
	protected static Map<String,Object> YELP = CommonBase.loadSearchServiceDetails(SERVICES_ENV_YML, TEST_ENV);
	
	private YelpServiceTest() {

	}
	private static final  Logger logger = Logger.getLogger(YelpServiceTest.class);
	@Test(groups ={TestConstants.TestNGGroups.YELPAPI},description = "Delivery")
	public void APITest(){
		try{
			POST_HEADER.put("Authorization",tokenType+" "+accessToken);
			String url = "https://"+YELP.get("host")+""+YELP.get("pathparam")+""+YELP.get("qryparam");
			Response Delivery = new RestService().setHeader(POST_HEADER).get(url);
			int Status=Delivery.getStatusCode();
			Assert.assertEquals(Status, 200);
			logger.info(JsonRetriever.get(Delivery.getBody(),"total"));
		}catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}


}
