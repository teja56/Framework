package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
@Epic("Epic - 102: create Home page features")
@Feature("US - 502 : create test for Home page on hubspot")
public class HomePageTest {
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	HomePage homePage;
	Credentials userCred;

	@BeforeMethod(alwaysRun =true)
	@Parameters(value = { "browser" })
	public void setUp(String browser) {
		String browserName = null;
		basePage = new BasePage();
		prop = basePage.init_properties();


		if(browser.equals(null) || browser.equals("") || browser.isEmpty()){
			 browserName = prop.getProperty("browser");
		}else{
			browserName = browser;
		}
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred =new Credentials(prop.getProperty("username"), prop.getProperty("password"));

		homePage = loginPage.doLogin(userCred);
		
	}

	@Test(priority = 1, groups ="sanity")
	@Description("Verify HomePage title test..")
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageTitleTest() {
		String title = homePage.getHomePageTitle();
		System.out.println("home page title is : " + title);
		Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
	}

	@Test(priority = 2)
	@Description("Verify HomePage header test..")
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageHeaderTest() {
		String header = homePage.getHomePageHeader();
		System.out.println("home page header is : " + header);
		Assert.assertEquals(header, AppConstants.HOME_PAGE_HEADER);
	}

	@Test(priority = 3, enabled=false)
	@Description("Verify Logged in user test..")
	@Severity(SeverityLevel.CRITICAL)
	public void verifyLoggedInUserTest() {
		String accountName = homePage.getLoggedInUSerAccountName();
		System.out.println("Logged in account name : " + accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}

	@AfterMethod(alwaysRun =true)
	public void tearDown() {
		driver.quit();
	}

}
