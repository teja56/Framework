package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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

@Epic("Epic - 101: create login page features")
@Feature("US - 501 : create test for login page on hubspot")
public class LoginPageTest {
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;

	@BeforeMethod(alwaysRun = true)
	@Parameters(value = { "browser" })
	public void setUp(String browser) {
		String browserName = null;
		basePage = new BasePage();
		prop = basePage.init_properties();

		if (browser.equals(null)) {
			browserName = prop.getProperty("browser");
		} else {
			browserName = browser;
		}
		driver = basePage.init_driver(browserName);

		driver.get(prop.getProperty("url"));

		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 1)
	@Description("Verify loginPage title test..")
	@Severity(SeverityLevel.NORMAL)
	public void verifyLoginPageTitleTest() {

		String title = loginPage.getPageTitle();
		System.out.println("login page title is : " + title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test(priority = 2, groups = "sanity")
	@Description("Verify Sign up link test..")
	@Severity(SeverityLevel.CRITICAL)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}

	@Test(priority = 3)
	@Description("Verify login test..")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		HomePage homePage = loginPage.doLogin(userCred);
		String header = homePage.getHomePageHeader();
		Assert.assertEquals(header, AppConstants.HOME_PAGE_HEADER);
	}

	@DataProvider
	public Object[][] getLoginInvalidData() {
		Object data[][] = { { "test1111@gmail.com", "test123" }, { "test2222@gmail.com", "test1234" },
				{ "test33333@gmail.com", "test12345" }, { "testasda", "testasda" }, { " ", " " } };
		return data;
	}

	@Test(priority = 4, dataProvider = "getLoginInvalidData", enabled = false)
	public void loginTest_InvalidCredentials(String username, String pwd) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMessage());
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

}
