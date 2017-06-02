package com.automation.web.webdriver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

	/* Browsers constants */
	public static final String CHROME = "chrome";
	public static final String FIREFOX = "firefox";
	public static final String INTERNET_EXPLORER = "ie";
	public static final String HTML_UNIT = "htmlunit";

	/* Platform constants */
	public static final String WINDOWS = "windows";
	public static final String XP = "xp";
	public static final String VISTA = "vista";
	public static final String MAC = "mac";
	public static final String LINUX = "linux";

	private WebDriverFactory(){}
	public static WebDriver getInstance(String gridHubUrl, Browser browser,
			String username, String password) {
		WebDriver webDriver = null;
		DesiredCapabilities capability = new DesiredCapabilities();
		String browserName = browser.getName();
		capability.setJavascriptEnabled(true);
		if (CHROME.equals(browserName)) {
			capability = DesiredCapabilities.chrome();
		} else if (FIREFOX.equals(browserName)) {
			capability = DesiredCapabilities.firefox();
			FirefoxProfile ffProfile = new FirefoxProfile();
			if (username != null && password != null) {
				capability.setCapability(FirefoxDriver.PROFILE, ffProfile);
			}
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		} else if (INTERNET_EXPLORER.equals(browserName)) {
			capability = DesiredCapabilities.internetExplorer();
			capability
			.setCapability(
					InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
		} else {
			webDriver = new HtmlUnitDriver(true);
		}
		capability = setVersionAndPlatform(capability, browser.getVersion(),browser.getPlatform());
		try {
			webDriver = new RemoteWebDriver (capability);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webDriver;
	}

	public static WebDriver getInstance(Browser browser) {
		String browserName = browser.getName();
		WebDriver webDriver = null;
		if (CHROME.equals(browserName)) {
			setChromeDriver();
			webDriver = new ChromeDriver();
		} else if (FIREFOX.equals(browserName)) {
			System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver/geckodriver.exe");
			FirefoxProfile ffProfile = new FirefoxProfile();
			webDriver = new FirefoxDriver(ffProfile);
		} else if (INTERNET_EXPLORER.equals(browserName)) {
			webDriver = new InternetExplorerDriver();
		} else {
			webDriver = new HtmlUnitDriver(true);
		}

		return webDriver;
	}
	private static DesiredCapabilities setVersionAndPlatform(
			DesiredCapabilities capability, String version, String platform) {
		if (MAC.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.MAC);
		} else if (LINUX.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.LINUX);
		} else if (XP.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.XP);
		} else if (VISTA.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.VISTA);
		} else if (WINDOWS.equalsIgnoreCase(platform)) {
			capability.setPlatform(Platform.WINDOWS);
		} else {
			capability.setPlatform(Platform.ANY);
		}
		if (version != null) {
			capability.setVersion(version);
		}
		return capability;
	}
	private static void setChromeDriver() {
		String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
		String chromeBinary = "src/main/resources/drivers/chrome/chromedriver"
				+ (os.equals("win") ? ".exe" : "");
		System.setProperty("webdriver.chrome.driver", chromeBinary);
	}
	
	
}
