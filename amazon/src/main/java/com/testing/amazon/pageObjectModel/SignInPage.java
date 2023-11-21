package com.testing.amazon.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage extends BasePage{
	private By goToSignInPage = By.id("nav-link-accountList-nav-line-1");
	private By phoneNumber = By.id("ap_email");
	private By signInContinueButton = By.id("continue");

	public SignInPage(WebDriver driver) {
		
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void navigateToSignIn() {
		try{
			getElement(goToSignInPage).click();
			
		} catch (NoSuchElementException e) {
	        System.out.println("Element did not located");
	        e.printStackTrace();
	    } catch (Exception e) {
	    	System.out.println("Unknown Exception, Please handle it");
	        // Handle any other unexpected exceptions
	        e.printStackTrace();
	    }
	}
	
	public WebElement getPhoneNumber() {
		return getElement(phoneNumber);
	}

	public WebElement getSignInContinueButton() {
		return getElement(signInContinueButton);
	}
	
	public WebElement getMissingPhoneNumberAlert() {
		return getElement(By.xpath("//div[@id='auth-email-missing-alert'][contains(@style,'display')]"));
	}
	public WebElement getInvalidPhoneNumberAlert() {
		if(captchaPresent()) {
			handleCaptcha();
		}
		return getElement(By.xpath("//div[@class='a-alert-content']//li/span"));
	}

}
