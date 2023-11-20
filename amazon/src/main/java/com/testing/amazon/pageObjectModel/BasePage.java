package com.testing.amazon.pageObjectModel;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePage extends PageOutline{
	public WebDriver driver;
	
	public BasePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}

	@Override
	public String getPageTitle() {
		return driver.getTitle();
	}

	@Override
	public String getPageHeader(By locator) {
		return getElement(locator).getText();
	}

	@Override
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			waitForElementPresent(locator);
			element = driver.findElement(locator);
			return element;
		} catch (Exception e) {
			System.out.println("some error occurred while creating element " + locator.toString());
			e.printStackTrace();
		}

		return element;
	}

	@Override
	public void waitForElementPresent(By locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			System.out.println("some exception/error occurred while waiting for the element " + locator.toString());
		}
	}

	@Override
	public void waitForPageTitle(String title) {
		try {
			wait.until(ExpectedConditions.titleContains(title));
		} catch (Exception e) {
			System.out.println("some exception/error occurred while waiting for the element " + title);
		}
	}

	@Override
	public boolean captchaPresent() {
		try {
	        //WebElement element = getElement(locator);
	        System.out.println("Test Captcha "+getPageTitle());
	        return getPageTitle().equals("Authentication required"); // Assuming getElement returns null if the element is not found
	    } catch (NoSuchElementException | TimeoutException e) {
	        return false; // Handling exceptions that might occur if the element is not found
	    }
	}

	@Override
	public void handleCaptcha() {
		// Code to manually solve the captcha
	    // Implement the steps required to solve the captcha
	    
	    // Pause the execution until manual intervention (for instance, pressing 'Enter')
	    System.out.println("Please solve the captcha and press Enter once done.");
	    while (getPageTitle().equals("Authentication required")) {
	    	
	    }
	    System.out.println("Captcha Resolved "+getPageTitle());
	    // After solving the captcha, continue the execution flow
		
	}
	
	public byte[] getScreenShot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

}
