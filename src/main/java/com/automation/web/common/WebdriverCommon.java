package com.automation.web.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebdriverCommon {

	public static WebElement getElementByXpath(WebDriver driver,By xpath){
		return driver.findElement(xpath);
	}

	public static List<WebElement> getElementsByXpath(WebDriver driver,By xpath){
		return driver.findElements(xpath);

	}

	public static void waitForPageLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}
	
	public static boolean explicitWait(WebDriver driver,By xpath,int seconds){
		boolean Success=false;
		try{
			WebDriverWait wait=new WebDriverWait(driver,seconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
			Success=true;
		}catch (Exception e) {
			return false;
		}
		return Success;
	}
	
	public static boolean explicitWaitElementClickable(WebDriver driver,By xpath,int seconds){
		boolean Success=false;
		try{
			WebDriverWait wait=new WebDriverWait(driver,seconds);
			wait.until(ExpectedConditions.elementToBeClickable(xpath));
			Success=true;
		}catch (Exception e) {
			return false;
		}
		return Success;
	}
	
	public static boolean ExplicitWaitInvisibilty(WebDriver driver,By xpath,int seconds){
		boolean Success=false;
		try{
			WebDriverWait wait=new WebDriverWait(driver,seconds);
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(xpath)));
			Success=true;
		}catch (Exception e) {
			return false;
		}
		return Success;
	}
	
	public static boolean MovetoElement(WebDriver driver,WebElement element){
		boolean Success=false;
		try{
			Actions action = new Actions(driver);
			action.moveToElement(element).click().build().perform();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return Success;
	}
	
	public static boolean JavaScriptClick(WebDriver driver,WebElement element){
		boolean Success=false;
		try{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return Success;
	}
}
