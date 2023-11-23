package com.testing.amazon.tests.functional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.testing.amazon.pageObjectModel.Products;
import customInterfaces.ParameterLabel;


@Test(groups = "SearchProductFunctionalTest")
public class SearchProductTest extends FunctionalBaseTest{
	
	private Products products;
	private static Map<String, List<Object[]>> testData;
	private static final String CSV_FILE_PATH = "src//test//resources//ProductSearchFeed.csv";
	
	
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
    public void setUpProductSearchPage() {
		System.out.println("Checking the error");
		products = page.getInstance(Products.class);
		System.out.println(products==null?"true":"false");
        // Navigate to the sign-up page or set up any preconditions specific to sign-up
		
		basePage = products;
    }

  @DataProvider(name = "productSearchData")
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
  
  
  
  @Parameters({ "searchFor", "SearchIn" ,"confirmSearch", "testMethodName"})
  @Test(dataProvider = "productSearchData",  enabled=true, description = "Verify that relavant search results shown for a product search.")
  public void searchByCategoryTest(@ParameterLabel("Search Keyword") String searchFor,@ParameterLabel("Search In Category") String searchIn,@ParameterLabel("Confirm Brand in Search Result") String confirmSearch,@ParameterLabel("methodName") String testMethodName) {
	  try {
		  products.getCategory(searchIn);
		  byte[] screenshot = captureScreenshot();
		  attachScreenshotToAllure(screenshot);
		  products.getSearchBar().sendKeys(searchFor);
		  screenshot = captureScreenshot();
		  attachScreenshotToAllure(screenshot);
		  products.getSearchSubmitButton().click();
		  
		  String dynamicXPath = String.format("//h2//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'%s')]", confirmSearch.toLowerCase());

        // Execute the XPath query
		  List<WebElement> searchResults = driver.findElements(By.xpath(dynamicXPath));
        // Assert that searchResults is not null
		  Assert.assertNotNull(searchResults, "Search result is null for brand: " + confirmSearch);
		  
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to an exception: " + e.getMessage());
	    } 
	  
  }

}
