package com.test.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Dummy {
	
	@Test
	
	public void scroll() throws InterruptedException
	{
		WebDriver driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("https://www.flipkart.com/");
		driver.findElement(By.xpath("//div[@class='_2QfC02']/button")).click();
		//Thread.sleep(10);
		driver.findElement(By.name("q")).sendKeys("Samsung ");
		
		//Thread.sleep(10);
		
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'samsung mobiles')]")));
	    //act.sendKeys(Keys.DOWN).perform();
	   // act.sendKeys(Keys.DOWN).perform();
	    //act.sendKeys(Keys.ENTER).perform();
		
		
	    WebDriverWait wait = new WebDriverWait(driver,20);
	    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[contains(text(),'samsung')]")));

	    List<WebElement> list = driver.findElements(By.xpath("//span[contains(text(),'samsung')]"));

	    System.out.println("Auto Suggest List ::" + list.size());

	    for(int i = 1 ;i< list.size();i++)
	    {
	        System.out.println(list.get(i).getText());

	        if(list.get(i).getText().equals("samsung mobiles"))
	        {
	            list.get(i).click();
	            break;


	        }
	    //driver.close();
	    }
	    
	    
	}

}
