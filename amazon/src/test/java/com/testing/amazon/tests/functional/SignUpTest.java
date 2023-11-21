package com.testing.amazon.tests.functional;

import org.testng.annotations.Test;

import com.testing.amazon.pageObjectModel.SignUpPage;

import customInterfaces.ParameterLabel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class SignUpTest extends FunctionalBaseTest{
	private SignUpPage signUpPage;
	private static Map<String, List<Object[]>> testData;
	private static final String CSV_FILE_PATH = "src//test//resources//SignUpFeed.csv";
	
	
	@BeforeClass
    public void setUpTestData() {
        testData = readTestDataFromCSV();
    }
	
	public Map<String, List<Object[]>> readTestDataFromCSV() {
		testData= new HashMap<String, List<Object[]>>(); 
        // Logic to read CSV and populate testData map
		String line;
	      String[] headers = null;

	      try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
	          while ((line = br.readLine()) != null) {
	              String[] data = line.split(",");
	              if (headers == null) {
	                  headers = data;
	              } else {
	                  String testMethodName = data[data.length - 1]; // Assuming method name is the last column
	                  if (testData.isEmpty() || !testData.containsKey(testMethodName)) {
	                      testData.put(testMethodName, new ArrayList<Object[]>());
	                  }
	                  testData.get(testMethodName).add(data);
	              }
	          }
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	      return testData;
    }
	@BeforeMethod
    public void setUpSignUpPage() {
		signUpPage = page.getInstance(SignUpPage.class);
        // Navigate to the sign-up page or set up any preconditions specific to sign-up
		signUpPage.navigateToSignUp();
		basePage = signUpPage;
    }
	
	public boolean isSignUpPageLoaded() {
        // Check if the signup page is loaded
		System.out.println(signUpPage.getPageTitle());
        return signUpPage.getPageTitle().equals("Amazon Registration");
    }

  @DataProvider(name = "signUpData")
  public Object[][] dp(ITestNGMethod testMethod) {

   // Return data based on the test method being executed
      String methodName = testMethod.getMethodName();
      List<Object[]> specificTestData = testData.get(methodName);

      // Convert list to Object[][]
      Object[][] testDataArray = new Object[specificTestData.size()][];
      for (int i = 0; i < specificTestData.size(); i++) {
          testDataArray[i] = specificTestData.get(i);
      }
      return testDataArray;
  }
  
  @Parameters({ "userName", "phoneNumberOrEmail", "password", "reEnterPassword" , "testMethodName"})
  @Test(dataProvider = "signUpData",  enabled=false, description = "Verify that the signUp process should not proceed to next step with invalid user name.")
  public void signUpWithInvalidUserNameTest(@ParameterLabel("Username") String userName,@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("Re-enter Password") String reEnterPassword,@ParameterLabel("methodName") String testMethodName) {
	  try {
	        Assert.assertTrue(isSignUpPageLoaded(), "SignUp page Not loaded");
	        signUpPage.getUserName().sendKeys(userName);
	        signUpPage.getPhoneNumber().sendKeys(phoneNumberOrEmail);
	        signUpPage.getPassword().sendKeys(password);
	        signUpPage.getReEnterPassword().sendKeys(reEnterPassword);
	        signUpPage.getSignUpButton().click();

	        // Validate: Check for the error message
	        String actualTitle = signUpPage.getInvalidNameAlert();
	        String unexpectedTitle = "Amazon Phone Verification";
	        System.out.println(actualTitle);

	        // Assert the error message
	        Assert.assertNotEquals(actualTitle, unexpectedTitle, "Going to next step of signUp after entering invalid user name.");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
	  
  }
  
  @Parameters({ "userName", "phoneNumberOrEmail", "password", "reEnterPassword" , "testMethodName"})
  @Test(dataProvider = "signUpData",  enabled=false, description = "Verify that the signUp process should not proceed to next step with invalid phone Number and also checking that proper error message displayed.")
  public void signUpWithInvalidPhoneNumberTest(@ParameterLabel("Username") String userName,@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("Re-enter Password") String reEnterPassword,@ParameterLabel("methodName") String testMethodName) {
	  try{
		  	Assert.assertTrue(isSignUpPageLoaded(), "SignUp page Not loaded");
		  	signUpPage.getUserName().sendKeys(userName);
		    signUpPage.getPhoneNumber().sendKeys(phoneNumberOrEmail);
		    signUpPage.getPassword().sendKeys(password);
		    signUpPage.getReEnterPassword().sendKeys(reEnterPassword);
		    signUpPage.getSignUpButton().click();
	
		    // Validate: Check for the error message
		    WebElement element=signUpPage.getInvalidPhoneNumberAlert();
		    if(element!=null) {
		    	System.out.println(signUpPage.getPageTitle());
		    }
	
	    	String  expectedErrorMessage = "Please enter a valid mobile phone number with area code.";
	    	String  actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
	
		    // Assert the error message
		    Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Going to next step of signUp after entering invalid Mobile number.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
  }

  @Parameters({ "userName", "phoneNumberOrEmail", "password", "reEnterPassword" , "testMethodName"})
  @Test(dataProvider = "signUpData",  enabled=false, description = "Verify that the signUp process should not proceed to next step with invalid email-id and also checking that proper error message displayed.")
  public void signUpWithInvalidEmailTest(@ParameterLabel("Username") String userName,@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("Re-enter Password") String reEnterPassword,@ParameterLabel("methodName") String testMethodName) {
	  	try{
	  		Assert.assertTrue(isSignUpPageLoaded(), "SignUp page Not loaded");
		  	
			signUpPage.getUserName().sendKeys(userName);
			signUpPage.getPhoneNumber().sendKeys(phoneNumberOrEmail);
			signUpPage.getPassword().sendKeys(password);
			signUpPage.getReEnterPassword().sendKeys(reEnterPassword);
			signUpPage.getSignUpButton().click();
			
			// Validate: Check for the error message
			WebElement element=signUpPage.getInvalidEmailAlert();
			if(element!=null) {
				System.out.println("Test Method"+signUpPage.getPageTitle());
			}
			
			String  expectedErrorMessage = "Wrong or Invalid email address or mobile phone number. Please correct and try again.";
			String  actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
			
			// Assert the error message
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "No error message shown after entering invalid email-id.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
  }
  
  @Parameters({ "userName", "phoneNumberOrEmail", "password", "reEnterPassword" , "testMethodName"})
  @Test(dataProvider = "signUpData",  enabled=false, description = "Verify that the signUp process should not proceed to next step with invalid password and also checking that proper error message displayed.")
  public void signUpWithInvalidPasswordTest(@ParameterLabel("Username") String userName,@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("Re-enter Password") String reEnterPassword,@ParameterLabel("methodName") String testMethodName) {
	  	try{
	  		Assert.assertTrue(isSignUpPageLoaded(), "SignUp page Not loaded");
			signUpPage.getUserName().sendKeys(userName);
			signUpPage.getPhoneNumber().sendKeys(phoneNumberOrEmail);
			signUpPage.getPassword().sendKeys(password);
			signUpPage.getReEnterPassword().sendKeys(reEnterPassword);
			signUpPage.getSignUpButton().click();
			
			// Validate: Check for the error message
			WebElement element=signUpPage.getInvalidPasswordAlert();
			if(element!=null) {
				System.out.println(testMethodName+" "+signUpPage.getPageTitle());
			}
			
			String  expectedErrorMessage = "Minimum 6 characters required";
			String  actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
			
			// Assert the error message
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "No error message shown after entering invalid password."); 	  
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
  }
  
  @Parameters({ "userName", "phoneNumberOrEmail", "password", "reEnterPassword" , "testMethodName"})
  @Test(dataProvider = "signUpData",  enabled=false, description = "Verify that the signUp process should not proceed to next step with invalid re-enter password and also checking that proper error message displayed.")
  public void signUpWithInvalidPasswordCheckTest(@ParameterLabel("Username") String userName,@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("Re-enter Password") String reEnterPassword,@ParameterLabel("methodName") String testMethodName) {
	  	try{
	  		Assert.assertTrue(isSignUpPageLoaded(), "SignUp page Not loaded");
			signUpPage.getUserName().sendKeys(userName);
			signUpPage.getPhoneNumber().sendKeys(phoneNumberOrEmail);
			signUpPage.getPassword().sendKeys(password);
			signUpPage.getReEnterPassword().sendKeys(reEnterPassword);
			signUpPage.getSignUpButton().click();
			
			// Validate: Check for the error message
			WebElement element=signUpPage.getInvalidReEnterPasswordAlert();
			if(element!=null) {
				System.out.println(testMethodName+" "+signUpPage.getPageTitle());
			}
			
			String  expectedErrorMessage = "Passwords must match";
			String  actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
			
			// Assert the error message
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "No error message shown after entering invalid reEnter-password.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
  }
  
  
  @Parameters({ "userName", "phoneNumberOrEmail", "password", "reEnterPassword" , "testMethodName"})
  @Test(dataProvider = "signUpData",  enabled=false, description = "Verify that the signUp process should not proceed to next step with empty cred and also checking that proper error message displayed.")
  public void signUpWithEmptyCredTest(@ParameterLabel("Username") String userName,@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("Re-enter Password") String reEnterPassword,@ParameterLabel("methodName") String testMethodName) {
	  try {	
			Assert.assertTrue(isSignUpPageLoaded(), "SignUp page Not loaded");
	//		signUpPage.getUserName().sendKeys(userName);
	//		signUpPage.getPhoneNumber().sendKeys(email);
	//		signUpPage.getPassword().sendKeys(password);
	//		signUpPage.getReEnterPassword().sendKeys(passwordCheck);
			signUpPage.getSignUpButton().click();
			
			// Validate: Check for the error message
			WebElement element=signUpPage.getMissingNameAlert();
			String  expectedErrorMessage = "Enter your name";
			String  actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
			// Assert the error message
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "No appropriate error message shown after giving blank user name");
			
			element=signUpPage.getMissingPhoneNumberAlert();
			expectedErrorMessage = "Enter your email or mobile phone number";
			actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
			// Assert the error message
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "No appropriate error message shown after giving blank phone or email-id");
			
			element=signUpPage.getMissingPasswordAlert();
			expectedErrorMessage = "Minimum 6 characters required";
			actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
			// Assert the error message
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "No appropriate error message shown after giving blank password");
			
			signUpPage.getPassword().sendKeys("123456");
			signUpPage.getSignUpButton().click();
			element= signUpPage.getMissingReEnterPasswordAlert();
			expectedErrorMessage = "Type your password again";
			actualErrorMessage = element!=null && element.getText().equals(expectedErrorMessage)?element.getText():signUpPage.getPageTitle();	    
			// Assert the error message
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "No appropriate error message shown after giving blank re-enter password");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
		
  }
  
  
//  @Test(dataProvider = "signUpData")
//  public void signUpWithValidCredTest() {
//	  
//  }
  

}
