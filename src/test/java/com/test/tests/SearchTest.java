package com.test.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.test.base.BaseTest;

public class SearchTest extends BaseTest {

	@Test(dataProvider = "getData")
	public void searchFor(Hashtable<String, Object> data) throws IOException, InterruptedException {

		String pname;
		String pprice;
		
		HashMap<String, String> ProductList = new HashMap<String,String>();
		
		test.log(Status.INFO, "Starting test" + testName);
		logInReport(data);

		test.log(Status.INFO, "Opening browser");
		openBrowser("Mozilla");
		
		test.log(Status.INFO, "Navigating to " + prop.getProperty("URL"));
		navigate("URL");
		
		test.log(Status.INFO, "Clicking on " + prop.getProperty("login_xpath"));
		click("login_xpath");
		
		test.log(Status.INFO, "Entering text "+data.get("SearchText").toString()+" in search button");
		type("searchTxt_name", data.get("SearchText").toString());
		
		
		click("searchSubmit_xpath");

		test.log(Status.INFO, "Selecting RAM " +data.get("RAM").toString());
		selectCheckBox("ram_xpath", data.get("RAM").toString());

		test.log(Status.INFO, "Selecting max price " + data.get("PriceMax").toString());
		selectFromDD("selectMaxPrice_xpath", data.get("PriceMax").toString());

		scrollToElement("processor_xpath");

		click("processor_xpath");

		test.log(Status.INFO, "Selecting Processor name "+data.get("Processor"));
		selectCheckBox("processorNames_xpath", data.get("Processor").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='_4rR01T']")));
		List<WebElement> product_names = driver.findElements(By.xpath("//div[@class='_4rR01T']"));
		List<WebElement> product_price = driver.findElements(By.xpath("//div[@class='_25b18c']/div[1]"));
		// sa.assertAll();
		
		
		for(int i=0;i<product_names.size();i++) {
			pname = product_names.get(i).getText();//Iterate and fetch product name
			pprice = product_price.get(i).getText();//Iterate and fetch product price
			pprice = pprice.replaceAll("[^0-9]", "");//Replace anything wil space other than numbers
			
			ProductList.put(pname,pprice);//Add product and price in HashMap
			
			
		}
		test.log(Status.INFO, "Writing test data to Json file " +ProductList.toString());
		createJsonFile("Samsung Phones", ProductList);
		
		
		//System.out.println(ProductList.get("Samsung Galaxy J2 2018 (Black, 16 GB)"));
		test.log(Status.INFO, "End of test : " + testName);
		
	}

	

}
