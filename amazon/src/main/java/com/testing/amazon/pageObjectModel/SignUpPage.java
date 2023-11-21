package com.testing.amazon.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignUpPage extends BasePage{
	private By goToSignUpPage = By.id("nav-link-accountList-nav-line-1");
	private By userName = By.name("customerName");
	private By phoneNumber = By.id("ap_email");
	private By password = By.id("ap_password");
	private By passwordCheck = By.id("ap_password_check");
	private By signUpButton = By.id("continue");


	public SignUpPage(WebDriver driver) {
		super(driver);
	}
	
	public void navigateToSignUp() {
		try{
			getElement(goToSignUpPage).click();
			getElement(By.id("createAccountSubmit")).click();
			
		} catch (NoSuchElementException e) {
	        System.out.println("Element did not located");
	        e.printStackTrace();
	    } catch (Exception e) {
	    	System.out.println("Unknown Exception, Please handle it");
	        // Handle any other unexpected exceptions
	        e.printStackTrace();
	    }
	}
	
	public String getSignUpPageTitle() {
		return getPageTitle();
	}
	
	public WebElement getUserName() {
		return getElement(userName);
	}
	
	public WebElement getPhoneNumber() {
		return getElement(phoneNumber);
	}

	public WebElement getPassword() {
		return getElement(password);
	}
	
	public WebElement getReEnterPassword() {
		return getElement(passwordCheck);
	}

	public WebElement getSignUpButton() {
		return getElement(signUpButton);
	}
	
	public WebElement getMissingNameAlert() {
		return getElement(By.xpath("//div[@id='auth-customerName-missing-alert'][contains(@style,'display')]"));
	}
	public WebElement getMissingPhoneNumberAlert() {
		return getElement(By.xpath("//div[@id='auth-email-missing-alert'][contains(@style,'display')]"));
	}
	public WebElement getMissingPasswordAlert() {
		return getElement(By.xpath("//div[@id='auth-password-missing-alert'][contains(@style,'display')]"));
	}
	public WebElement getMissingReEnterPasswordAlert() {
		return getElement(By.xpath("//div[@id='auth-passwordCheck-missing-alert'][contains(@style,'display')]"));
	}
	
	public String getInvalidNameAlert() {
		if(captchaPresent()) {
			handleCaptcha();
		}
		return getPageTitle();
	}
	public WebElement getInvalidEmailAlert() {
		return getElement(By.xpath("//div[@id='auth-email-invalid-claim-alert'][contains(@style,'display')]"));
	}
	public WebElement getInvalidPhoneNumberAlert() {
		if(captchaPresent()) {
			handleCaptcha();
		}
		return getElement(By.xpath("//div[@class='a-alert-content']//li/span"));
	}
	public WebElement getInvalidPasswordAlert() {
		return getElement(By.xpath("//div[@id='auth-password-invalid-password-alert'][contains(@style,'display')]"));
	}
	public WebElement getInvalidReEnterPasswordAlert() {
		return getElement(By.xpath("//div[@id='auth-password-mismatch-alert'][contains(@style,'display')]"));
	}
	
	

}
