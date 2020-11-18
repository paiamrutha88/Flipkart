package com.test.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Dummy {
	
	@Test
	
	public void scroll() throws InterruptedException
	{
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.flipkart.com/");
		driver.findElement(By.xpath("//div[@class='_2QfC02']/button")).click();
		driver.findElement(By.name("q")).sendKeys("Samsung mobiles");
		driver.findElement(By.xpath("//button[@class='L0Z3Pu']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		//WebElement ee = driver.findElement(By.xpath("//div[contains(text(),'Processor')]"));
		
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(10);
		js.executeScript("window.scrollBy(1001,5000)");
		Thread.sleep(10);
		js.executeScript("window.scrollBy(5001,10000)");
		Thread.sleep(10);
		
		System.out.println("scrolled");
	}

}
