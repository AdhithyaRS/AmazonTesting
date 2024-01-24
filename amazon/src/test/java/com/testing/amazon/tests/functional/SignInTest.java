package com.testing.amazon.tests.functional;

import org.testng.annotations.Test;

import com.testing.amazon.pageObjectModel.SignInPage;

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

@Test(groups = "SignInFunctionalTest")
public class SignInTest extends FunctionalBaseTest{
	private SignInPage signInPage;
	private static Map<String, List<Object[]>> testData;
	private static final String CSV_FILE_PATH = "src//test//resources//SignInFeed.csv";
	
	
	@BeforeClass
    public void setInTestData() {
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
    public void setUpSignInPage() {
		System.out.println("Checking the error");
		signInPage = page.getInstance(SignInPage.class);
		System.out.println(signInPage==null?"true":"false");
		signInPage.navigateToSignIn();
		basePage = signInPage;
    }
	
	public boolean isSignInPageLoaded() {
        // Check if the signup page is loaded
		System.out.println(signInPage.getPageTitle());
        return signInPage.getPageTitle().equals("Amazon Sign-In");
    }

  @DataProvider(name = "signInData")
  public Object[][] dp(ITestNGMethod testMethod) {
	  System.out.println("Printing test data: "+testData);
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
  
  
  
  @Parameters({ "phoneNumberOrEmail", "password" , "testMethodName"})
  @Test(dataProvider = "signInData",  enabled=true, description = "Verify that the proper alert shown with invalid phone number or email-id.")
  public void signInWithInvalidCredTest(@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("methodName") String testMethodName) {
	  try {
	        Assert.assertTrue(isSignInPageLoaded(), "SignIn page Not loaded");
	        signInPage.getPhoneNumber().sendKeys(phoneNumberOrEmail);
	        byte[] screenshot = captureScreenshot();
			attachScreenshotToAllure(screenshot);
	        signInPage.getSignInContinueButton().click();

	        // Validate: Check for the error message
	        WebElement element=signInPage.getInvalidPhoneNumberAlert();
		    if(element!=null) {
		    	System.out.println(signInPage.getInvalidPhoneNumberAlert());
		    }
		    
		    String expectedErrorMessage;
	        if (isPhoneNumber(phoneNumberOrEmail)) {
	            expectedErrorMessage = "We cannot find an account with that mobile number";
	        } else {
	            expectedErrorMessage = "We cannot find an account with that email address";
	        }
	        

	        String actualErrorMessage = element != null && element.getText().length()>0 ? element.getText() : signInPage.getPageTitle();
	        System.out.println(actualErrorMessage);
	        // Assert the error message
	        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Proper alert not shown with invalid phone number or email-id.");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
	  
  }
  
  @Parameters({ "phoneNumberOrEmail", "password" , "testMethodName"})
  @Test(dataProvider = "signInData",  enabled=true, description = "Verify that the SignIn is successful with valid phone number and password.")
  public void signInWithValidCredTest(@ParameterLabel("Phone number/Email-id") String phoneNumberOrEmail,@ParameterLabel("Password") String password,@ParameterLabel("methodName") String testMethodName) {
	  try {
	        Assert.assertTrue(isSignInPageLoaded(), "SignIn page Not loaded");
	        signInPage.getPhoneNumber().sendKeys(phoneNumberOrEmail);
	        byte[] screenshot = captureScreenshot();
			attachScreenshotToAllure(screenshot);
	        signInPage.getSignInContinueButton().click();
	        signInPage.getPassword().sendKeys(password);
	        screenshot = captureScreenshot();
			attachScreenshotToAllure(screenshot);
	        signInPage.getSignInSubmitButton().click();
	        if(signInPage.captchaPresent()) {
	        	signInPage.handleCaptcha();
	        }
	        // Validate: Check for the error message
	        WebElement element=signInPage.isSignInSuccess();

		    
		    String unExpectedMessage= "Hello, sign in";
	        

	        String actualErrorMessage = element != null && element.getText().length()>0 ? element.getText() : signInPage.getPageTitle();
	        System.out.println(actualErrorMessage);
	        // Assert the error message
	        Assert.assertNotEquals(actualErrorMessage, unExpectedMessage, "The account did not sign in after giving valid credentials.");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
	  
  }
}
