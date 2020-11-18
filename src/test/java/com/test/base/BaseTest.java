package com.test.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.test.reports.ExtentManager;
import com.test.util.DataUtil;
import com.test.util.Xls_Reader;

public class BaseTest {

	public WebDriver driver;
	public Xls_Reader reader;
	public String testName;

	public ExtentReports rep;
	public ExtentTest test;

	public Properties prop;

	public SoftAssert sa;

	public WebDriverWait wait;
	public JSONObject jObj;
	public PrintWriter pWriter;

	@BeforeClass
	// @Test
	public void init() {

		testName = this.getClass().getSimpleName();

		System.out.println(testName);

		jObj = new JSONObject();
		// initialise properties file
		prop = new Properties();

		try {
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + "//src//test//resources//project.properties");
			prop.load(fs);
			
			pWriter = new PrintWriter(System.getProperty("user.dir")+prop.getProperty("jsonFilePath"));

		} catch (Exception e) {

			e.printStackTrace();
		}

		// initialise excel file
		// reader = new
		// Xls_Reader(System.getProperty("user.dir")+prop.getProperty("dataFile"));
		// //for checkin

		System.out.println(System.getProperty("user.dir") + prop.getProperty("dataFile"));
		reader = new Xls_Reader(
				(System.getProperty("user.dir") + "//src//test//resources//" + prop.getProperty("dataFile")));
		
		
		
		

	}

	public void openBrowser(String bType) {

		if (bType.equalsIgnoreCase("Mozilla")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (bType.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
			driver = new ChromeDriver();
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	public void navigate(String urlKey) {
		driver.get(prop.getProperty(urlKey));
	}

	public void click(String locatorKey) {
		getElement(locatorKey).click();
	}

	public void type(String locatorKey, String data) {
		getElement(locatorKey).sendKeys(data);
	}

	public void selectCheckBox(String locatorKey, String data) {
		List<WebElement> elements = driver.findElements(By.xpath(prop.getProperty(locatorKey)));

		for (WebElement e : elements) {
			// System.out.println(e.getText());

			if (e.getText().equals(data)) {
				e.click();
				break;
			}
		}
	}

	public void selectFromDD(String locatorKey, String data) {
		Select maxPrice = new Select(getElement(locatorKey));

		maxPrice.selectByVisibleText(data);
	}

	// finding element and returning it
	public WebElement getElement(String locatorKey) {
		WebElement e = null;
		wait = new WebDriverWait(driver, 10);
		try {
			if (locatorKey.endsWith("_id")) {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(prop.getProperty(locatorKey))));
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_name")) {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(prop.getProperty(locatorKey))));
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_xpath")) {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorKey))));
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			}

			else if (locatorKey.endsWith("_class")) {
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.className(prop.getProperty(locatorKey))));
				e = driver.findElement(By.className(prop.getProperty(locatorKey)));
			} else {
				reportFailure("Locator not correct - " + locatorKey);
				Assert.fail("Locator not correct - " + locatorKey);
			}

		} catch (Exception ex) {
			// fail the test and report the error
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			Assert.fail("Failed the test - " + ex.getMessage());
		}
		return e;
	}

	public void scrollToElement(String locatorKey) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		wait = new WebDriverWait(driver, 10);
		WebElement ee = getElement(locatorKey);
		js.executeScript("arguments[0].scrollIntoView(true);", ee);

		// executeScript("window.scrollBy(0,-250)", "");
		// js.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//div[contains(text(),'Processor')]"))
		// );

		js.executeScript("scroll(150, 0)");

	}

	/*********************** Validations ***************************/
	public boolean verifyTitle() {
		return false;
	}

	public boolean isElementPresent(String locatorKey) {
		List<WebElement> elementList = null;
		if (locatorKey.endsWith("_id"))
			elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_name"))
			elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_xpath"))
			elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		else {
			reportFailure("Locator not correct - " + locatorKey);
			Assert.fail("Locator not correct - " + locatorKey);
		}

		if (elementList.size() == 0)
			return false;
		else
			return true;
	}

	public boolean verifyText(String locatorKey, String expectedTextKey) {
		String actualText = getElement(locatorKey).getText().trim();
		String expectedText = prop.getProperty(expectedTextKey);
		if (actualText.equals(expectedText))
			return true;
		else
			return false;

	}

	public void reportPass(String msg) {
		test.log(Status.PASS, msg);
		takeScreenShot();
	}

	public void reportFailure(String msg) {
		test.log(Status.FAIL, msg);
		takeScreenShot();
		Assert.fail(msg);
	}

	public void takeScreenShot() {
		// fileName of the screenshot
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		// store screenshot in that file
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(ExtentManager.screenShotFolderPath + screenshotFile));
			test.log(Status.INFO, "Screenshot-> "
					+ test.addScreenCaptureFromPath(ExtentManager.screenShotFolderPath + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void createJsonFile(String jsonName, HashMap<String, String> productList) {
		// TODO Auto-generated method stub
		
		
		jObj.put(jsonName, productList);
		
		pWriter.write(jObj.toString());
		
	}

	@BeforeMethod
	public void initTest() {
		rep = ExtentManager.getInstance(System.getProperty("user.dir") + prop.getProperty("reportPath"));
		test = rep.createTest(testName);
		// sa = new SoftAssert();

	}

	@DataProvider

	public Object[][] getData() {
		// System.out.println("Inside data provider");

		return DataUtil.getTestData(reader, testName);

	}

	@AfterMethod

	public void afterMethod(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {

			//test.log(Status.FAIL, "Test failed " + result.getThrowable());
			reportFailure("Test Failed : "+ testName);
		} else if (result.getStatus() == ITestResult.SKIP) {

			test.log(Status.SKIP, "Test skipped " + result.getThrowable());
		} else {

			reportPass("Test Passed : " +testName);
			//test.log(Status.PASS, "Test passed");
		}

		if (rep != null)
			rep.flush();
		
		
		if(pWriter != null)
		{
		pWriter.flush();
		pWriter.close();
		}

	}
	/*
	 * public void skipTest(Hashtable<String, Object> data) { if
	 * (DataUtil.isSkip(testName, reader)) { test.log(Status.SKIP, "test skipped" +
	 * data.get("TestCaseName")); throw new SkipException("Rumnode set to  N " +
	 * testName);
	 * 
	 * } }
	 */

	public void logInReport(Hashtable<String, Object> data) {
		test.log(Status.INFO,
				"Running for test :" + data.get("TestCaseName") + " TestCase ID : " + data.get("TestCaseID"));
		test.log(Status.INFO, "Running test for data : " + data);
		test.log(Status.INFO, "test data fetched successfully");

	}

}
