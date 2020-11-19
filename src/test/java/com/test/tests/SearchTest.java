package com.test.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
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

		
		List<WebElement> product_names = getAllElements("productNames_xpath");
		List<WebElement> product_price = getAllElements("productPprices_xpath");
		// sa.assertAll();
		
		
		for(int i=0;i<product_names.size();i++) {
			pname = product_names.get(i).getText();
			pprice = product_price.get(i).getText();
			pprice = pprice.replaceAll("[^0-9]", "");
			
			ProductList.put(pname,pprice);//Add product and price in HashMap
			
			
		}
		test.log(Status.INFO, "Writing test data to Json file " +ProductList.toString());
		createJsonFile("Samsung Phones", ProductList);
		
				//System.out.println(ProductList.get("Samsung Galaxy J2 2018 (Black, 16 GB)"));
		test.log(Status.INFO, "End of test : " + testName);
		
	}

	

}
