package com.automation.web.pages;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.automation.web.common.WebdriverCommon;



public class YelpHomepage {

	@FindBy(how=How.XPATH,using="//label[contains(@class,'business-search-form_input')]//input[@id='find_desc']")
	@CacheLookup
	public static WebElement SearchTextBox;
	
	@FindBy(how=How.XPATH,using="//span[contains(.,'Restaurants')]/ancestor::li")
	@CacheLookup
	WebElement Restaurants;
	
	private By SearchType = By.xpath("//li[@data-suggest-query='Restaurants']");
	private By mainSearchTextBox=By.xpath("//label[contains(@class,'business-search-form_input')]//input[@id='find_desc']");
	private static final  Logger logger = Logger.getLogger(YelpHomepage.class);
	
	public WebElement getFindTextBox(){
		return SearchTextBox;
	}
	
	public void searchText(){
		try{
			SearchTextBox.click();
		}catch (NoSuchElementException e) {
			logger.info(e.getMessage());
		}
	}
	
	public void selectRestaurants(WebDriver driver){
		try{
			Restaurants.click();
		}catch (NoSuchElementException e) {
			logger.info(e.getMessage());
		}
	}

	

	public void clickOnSearchBox(WebDriver driver){
		try{
			WebdriverCommon.MovetoElement(driver, driver.findElement(mainSearchTextBox));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void waitForSearchTypeByInputTextandClick(WebDriver driver,int seconds){
		try{
			WebdriverCommon.explicitWait(driver, SearchType, seconds);
			driver.findElement(SearchType).click();
			}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
}
