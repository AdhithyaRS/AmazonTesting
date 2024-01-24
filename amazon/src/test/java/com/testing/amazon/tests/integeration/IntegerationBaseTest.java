package com.testing.amazon.tests.integeration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.testing.amazon.pageObjectModel.BasePage;
import com.testing.amazon.pageObjectModel.PageOutline;
import com.testing.amazon.tests.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class IntegerationBaseTest extends BaseTest{
	public WebDriver driver;
	public PageOutline page;

  @BeforeMethod
  @Parameters(value = { "browser" })
  public void setUpBrowserTest(String browser) {
	  if (browser.equals("chrome")) {
		  System.setProperty("webdriver.chrome.driver", "C:\\Users\\toadh\\chromedriver-win64_1\\chromedriver.exe");
          System.out.println("Test");
          driver = new ChromeDriver();
          
      } else if (browser.equals("fox")) {
          WebDriverManager.firefoxdriver().setup();
          driver = new FirefoxDriver();
      } else if (browser.equals("edge")) {
          WebDriverManager.edgedriver().setup();
          driver = new EdgeDriver();
      } else {
          System.out.println("no browser value out of scope");
      }
      driver.get("https://www.amazon.in");
      try {
          Thread.sleep(6000);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
      page = new BasePage(driver);
      driver.manage().window().maximize();
      System.out.println(page.driver.getClass().getName());
  }
  
  
//  //Method to capture screenshot using Selenium
//  public byte[] captureScreenshot() {
//      // Assuming driver is your WebDriver instance
//      return basePage.getScreenShot();
//  }
//
//  // Method to attach screenshot to Allure report
//  @Attachment(value = "Intermidiate Screenshots", type = "image/png")
//  public byte[] attachScreenshotToAllure(byte[] screenshot) {
//      return screenshot;
//  }

  @AfterMethod
  public void tearDownTest(ITestResult result) {
//	  if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SUCCESS ) {
//		  System.out.println("In tear down method");
//          captureScreenshot(result.getMethod().getMethodName());
//      }
	  if (driver != null) {
		  System.out.println("End!!");
          driver.quit();
      }
  }

}
