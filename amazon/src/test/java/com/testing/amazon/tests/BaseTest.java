package com.testing.amazon.tests;

import com.testing.amazon.pageObjectModel.BasePage;
import io.qameta.allure.Attachment;

public class BaseTest {
	public static BasePage basePage;
	
	  //Method to capture screenshot using Selenium
	  public byte[] captureScreenshot() {
	      // Assuming driver is your WebDriver instance
	      return basePage.getScreenShot();
	  }

	  // Method to attach screenshot to Allure report
	  @Attachment(value = "Intermidiate Screenshots", type = "image/png")
	  public byte[] attachScreenshotToAllure(byte[] screenshot) {
	      return screenshot;
	  }

}
