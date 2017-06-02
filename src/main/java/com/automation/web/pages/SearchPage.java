package com.automation.web.pages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.automation.web.common.WebdriverCommon;


public class SearchPage {

	private static final  Logger logger = Logger.getLogger(SearchPage.class);

	@FindBy(how=How.XPATH,using="//div[contains(@class,'suggestions')]//li[contains(@data-suggest-query,'Restaurants') and contains(@data-suggest-query,'Pizza')]")
	@CacheLookup
	WebElement pizzaSuggesstions;


	@FindBy(how=How.XPATH,using="//label[contains(@class,'main-search')]//input[@id='find_desc']")
	@CacheLookup
	WebElement MainSearch;

	private By Allfilters = By.xpath("//span[contains(@class,'all-filters')]");
	private By numberOfSearches = By.xpath("//div[@class='search-results-content']//li[contains(@class,'regular')]//ul");
	private By maps = By.xpath("//span[contains(.,'Map data')]");
	private By searchHeaderText = By.xpath("//h1[contains(.,'Best Restaurants Pizza')]");
	private By searchPageSearchTextBox = By.xpath("//label[contains(@class,'main-search')]//input[@id='find_desc']");
	private By overlayLoad = By.xpath("//div[contains(@class,'throbber-container')]");
	private By pizzaHeader = By.xpath("//div[contains(@class,'search-header')]//a[contains(@href,'pizza')]");
	
	
	
	public void ClickSuggestions(){
		try{
			logger.info("clicking suggesstions");
			pizzaSuggesstions.click();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public int getNumberofSerches(WebDriver driver){
		logger.info("Total Searches");
		return WebdriverCommon.getElementsByXpath(driver,numberOfSearches).size();
	}

	public void getStarratings(WebDriver driver){
		try{
			List<WebElement> Results = driver.findElements(numberOfSearches);
			int i=1;
			for(WebElement result : Results){
					logger.info("Hotel Name  :"+result.findElement(By.xpath("//span[contains(@class,'indexed') and contains(.,'"+i+".')]//a[contains(@class,'name')]")).getText());
					logger.info("Ratings :"+result.findElement(By.xpath("//span[contains(@class,'indexed') and contains(.,'"+i+".')]//parent::h3//following-sibling::div/div[contains(@title,'rating')]")).getAttribute("title"));
					i++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitForHeaderText(WebDriver driver, int seconds){
		try{
			WebdriverCommon.explicitWait(driver,searchHeaderText,seconds);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitForMapsToLoad(WebDriver driver,int seconds){
		try{
			logger.info("waiting for maps to load , as maps are the ones loaded in the last : Explicit wait for Page Load");
			WebdriverCommon.explicitWait(driver, maps, seconds);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void clickOnSearchBoxSearchPage(WebDriver driver){
		try{
			WebdriverCommon.MovetoElement(driver, driver.findElement(searchPageSearchTextBox));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void sendText(String text){
		try{
			MainSearch.sendKeys(Keys.END+" "+text);
		}catch (NoSuchElementException e) {
			logger.info(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	public void ApplyFilters(WebDriver driver,HashMap<String, String> filters){
		try{
			Thread.sleep(2000);
			WebdriverCommon.explicitWaitElementClickable(driver, Allfilters, 20);
			logger.info("after element clickable all filters");
			WebdriverCommon.JavaScriptClick(driver,driver.findElement(Allfilters));
			Iterator it = filters.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry filter = (Map.Entry)it.next();
				WebdriverCommon.explicitWaitElementClickable
				(driver, By.xpath("//span[contains(.,'"+filter.getValue()+"')]//ancestor::label[contains(@class,'check')]"), 30);
				logger.info("Xpath :"+"//span[contains(.,'"+filter.getValue()+"')]//ancestor::label[contains(@class,'check')]");
				WebdriverCommon.MovetoElement(driver,driver.findElement(By.xpath("//span[contains(.,'"+filter.getValue()+"')]//ancestor::label[contains(@class,'check')]")));
				WebdriverCommon.ExplicitWaitInvisibilty(driver, overlayLoad, 20);
				if(filter.getValue().toString().equalsIgnoreCase("Pizza")){
					logger.info("Waiting for Pizza Header");
					WebdriverCommon.explicitWait(driver, pizzaHeader, 20);
					logger.info("Completed Waiting for Pizza Header");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("");
		}
	}

	
	public void clickIndexedBasedResult(WebDriver driver,int index){
		try{
			Thread.sleep(2000);
			WebdriverCommon.explicitWaitElementClickable(driver, By.xpath("//div[@class='search-results-content']//li[contains(@class,'regular')]//span[contains(@class,'indexed') and contains(.,'"+index+".')]//a"), 30);
			WebdriverCommon.getElementByXpath(driver,By.xpath("//div[@class='search-results-content']//li[contains(@class,'regular')]//span[contains(@class,'indexed') and contains(.,'"+index+".')]//a")).click();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	

}
