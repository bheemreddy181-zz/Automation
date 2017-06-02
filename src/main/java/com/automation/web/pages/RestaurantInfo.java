package com.automation.web.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.automation.web.common.WebdriverCommon;

public class RestaurantInfo {
	
	private static final  Logger logger = Logger.getLogger(RestaurantInfo.class);
	
	/**********
	 * Restaurant Info
	 */
	private By Pagetitle = By.xpath("//h1[contains(@class,'page-title')]");
	private By RestaurantHours = By.xpath("//li[contains(@class,'hours')]");
	private By MapboxText = By.xpath("//div[contains(@class,'mapbox-text')]");
	private By WriteaReview = By.xpath("//div[contains(@class,'page-actions')]//a[contains(@href,'writeareview')]");
	

	
	public void WaitforReviewButton(WebDriver driver,int seconds){
		try{
		WebdriverCommon.explicitWait(driver,WriteaReview,seconds);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void printRestaurantInfo(WebDriver driver){
		try{
			logger.info("Page Title :"+WebdriverCommon.getElementByXpath(driver, Pagetitle).getText());
			logger.info("Restaurant Info :"+WebdriverCommon.getElementByXpath(driver, MapboxText).getText());
			logger.info("Workings Hours Today :"+WebdriverCommon.getElementByXpath(driver,RestaurantHours).getText());
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
