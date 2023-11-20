package com.testing.amazon.tests.functional;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.testing.amazon.pageObjectModel.BasePage;
import com.testing.amazon.pageObjectModel.PageOutline;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;

import org.testng.annotations.AfterMethod;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;

public class FunctionalBaseTest {
	public static BasePage basePage;
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
      driver.get("https://www.amazon.com");
      try {
          Thread.sleep(6000);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
      page = new BasePage(driver);
      driver.manage().window().maximize();
      System.out.println(page.driver.getClass().getName());
  }

  @AfterMethod
  public void tearDownTest(ITestResult result) {
//	  if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SUCCESS ) {
//		  System.out.println("In tear down method");
//          captureScreenshot(result.getMethod().getMethodName());
//      }
	  if (driver != null) {
          driver.quit();
      }
  }
  
//  @Attachment(value = "Page screenshot", type = "image/png")
//  public byte[] captureScreenshot(String methodName) {
//	  System.out.println("Taking Screen shot");
//      return ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
//  }

}
