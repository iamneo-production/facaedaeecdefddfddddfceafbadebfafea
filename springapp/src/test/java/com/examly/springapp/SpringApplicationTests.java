package com.examly.springapp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class EbaySearch {
	
	ChromeDriver driver;
	String url = "http://www.ebay.in";
	
	public void invokeBrowser(){
		
		
		ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://34.85.242.216:4444"), chromeOptions);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		driver.get(url);
	}
	
	public void searchProduct(String product, String category){
		
		driver.findElement(By.id("gh-ac")).sendKeys(product);
		WebElement dropdown = driver.findElement(By.id("gh-cat"));
		Select selectCategory = new Select(dropdown);
		selectCategory.selectByVisibleText(category);
		driver.findElement(By.id("gh-btn")).click();
		String result = driver.findElement(By.className("listingscnt")).getText();
		System.out.println("Result :: "+ result);
	}
	
	public void getNthProduct(int itemNumber){
		String productXpath = String.format("//div[@id='ResultSetItems']/ul[@id='ListViewInner']/li[%d]", itemNumber);
		String nthProduct = driver.findElement(By.xpath(productXpath)).getText();
		System.out.println("Nth Product :: "+ nthProduct);
	}
	
	public void getAllProducts(){
	List<WebElement> allProduct = driver.findElements(By.xpath("//div[@id='ResultSetItems']/ul[@id='ListViewInner']/li"));
	
	for(WebElement product: allProduct){
		System.out.println(product.getText());
		System.out.println("-----------------------------------------");
	}
	}
	
	public void getAllProductsViaScrollDown(){
		List<WebElement> allProduct = driver.findElements(By.xpath("//div[@id='ResultSetItems']/ul[@id='ListViewInner']/li"));
		Actions action = new Actions(driver);
		for(WebElement product: allProduct){
			
			action.moveToElement(product).build().perform();
			System.out.println(product.getText());
			System.out.println("-----------------------------------------");
		}
	}
	
	public void getAllProductsViaScrollDownViaJS(){
		List<WebElement> allProduct = driver.findElements(By.xpath("//div[@id='ResultSetItems']/ul[@id='ListViewInner']/li"));
		
		
		for(WebElement product: allProduct){
			
			int x, y;
			x = product.getLocation().x;
			y = product.getLocation().y;
			scrollDown(x, y);
			System.out.println(product.getText());
			System.out.println("-----------------------------------------");
		}
		}
	
		private void scrollDown(int x, int y){
			JavascriptExecutor jsEngine;
			String jsCommand = String.format("window.scrollBy(%d,%d)", x,y);
			jsEngine = (JavascriptExecutor) driver;
			jsEngine.executeScript(jsCommand);
	}
}