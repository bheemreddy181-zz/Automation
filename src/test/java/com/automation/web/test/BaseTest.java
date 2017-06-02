package com.automation.web.test;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import com.automation.web.testdata.CustomerConfig;
import com.automation.web.webdriver.Browser;
import com.automation.web.webdriver.WebDriverFactory;



public class BaseTest {
	
	private static final  Logger logger = Logger.getLogger(CustomerConfig.class);
	protected WebDriver webDriver;
	protected Browser browser;
	protected String websiteUrl;

	@BeforeClass(alwaysRun = true)
	public void init() {
		browser = new Browser();
		websiteUrl=CustomerConfig.loadProperty("site.url");
		browser.setName(CustomerConfig.loadProperty("browser.name"));
		browser.setVersion(CustomerConfig.loadProperty("browser.version"));
		browser.setPlatform(CustomerConfig.loadProperty("browser.platform"));
		webDriver = WebDriverFactory.getInstance(browser);
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		logger.info("Test case Config setup complete.");
	}

	@AfterTest(alwaysRun = true)
	public void tearDown() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}
}