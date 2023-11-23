package com.testing.amazon.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Products extends BasePage{
	
	private By searchBar = By.id("twotabsearchtextbox");
	private By searchSubmitButton = By.id("nav-search-submit-button");
	

	public Products(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void signIn(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
	
	public void getCategory( String name) {
		// TODO Auto-generated method stub
		try{
			getElement(By.id("searchDropdownBox")).click();
		
			getElement(By.xpath("//option[contains(@value,'"+name+"')]")).click();
		} catch (NoSuchElementException e) {
	        System.out.println("Element did not located");
	        e.printStackTrace();
	    } catch (Exception e) {
	    	System.out.println("Unknown Exception, Please handle it");
	        // Handle any other unexpected exceptions
	        e.printStackTrace();
	    }
			
	}
	
	public WebElement getSearchBar() {
		// TODO Auto-generated method stub
		return getElement(searchBar);
	}
	public WebElement getSearchSubmitButton() {
		// TODO Auto-generated method stub
		return getElement(searchSubmitButton);
	}
	
	
	
	

}
