package com.automation.web.test;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.web.pages.RestaurantInfo;
import com.automation.web.pages.SearchPage;
import com.automation.web.pages.YelpHomepage;
import com.automation.web.testdata.TestConstants;

public class YelpTest extends BaseTest {

	private static final  Logger logger = Logger.getLogger(YelpTest.class);
	YelpHomepage homepage;
	RestaurantInfo info;
	SearchPage search;
	WebDriverWait wait;
	HashMap<String, String> filters= new HashMap<>();

	@BeforeMethod
	public void loaddriver() {
		// Load the page in the browser
		webDriver.get(websiteUrl);
	}



	@Test(groups ={TestConstants.TestNGGroups.SEARCH},description = "Search text")
	public void SearcTest(){
		try{
			homepage = PageFactory.initElements(webDriver, YelpHomepage.class);
			search= PageFactory.initElements(webDriver, SearchPage.class);
			info=PageFactory.initElements(webDriver, RestaurantInfo.class);
			
			/***********
			 * Selecting Restaurant from Search
			 */
			homepage.clickOnSearchBox(webDriver);
			homepage.waitForSearchTypeByInputTextandClick(webDriver,20);
			search.clickOnSearchBoxSearchPage(webDriver);
			
			/******************
			 * Appending Pizza at the end
			 */
			
			search.sendText("Pizza");
			
			/*********
			 * Clicking on the Search Text
			 */
			
			search.ClickSuggestions();
			
			/**********
			 * add filters
			 */
			filters.put("Category", "Pizza");
			filters.put("Features", "Order Delivery");
			
			search.ApplyFilters(webDriver,filters);
			
			/**********
			 * Asserting the Search results
			 */
			
			Assert.assertEquals(search.getNumberofSerches(webDriver),10);
			
			/*********
			 * Getting ratings from the Search
			 */
			
			search.getStarratings(webDriver);
			
			/********
			 * click first Result
			 */
			
			search.clickIndexedBasedResult(webDriver, 1);
			
			/*******
			 * Waiting for Restaurant Page to open
			 */
			info.WaitforReviewButton(webDriver, 20);
			
			/*****
			 * Print Hotel Info
			 */
			info.printRestaurantInfo(webDriver);
			
		}catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}


}
