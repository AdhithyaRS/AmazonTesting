package com.testing.amazon.listeners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.testng.ITestListener;
import org.testng.ITestResult;

import customInterfaces.ParameterLabel;
import io.qameta.allure.Attachment;

public class AllureListener implements ITestListener {
	
	
	
	@Override
    public void onTestSuccess(ITestResult result) {
		String successDetails = captureSuccessDetails(result);
        attachToReport("Test Success Details", successDetails);
    }

    // Capture test success details
    public String captureSuccessDetails(ITestResult result) {
        StringBuilder details = new StringBuilder();
        details.append("Test Method: ").append(result.getMethod().getMethodName()).append("\n");
        details.append("Test Description: ").append(result.getMethod().getDescription()).append("\n");
        details.append("Test Status: PASSED\n");
        details.append("\nParameters:\n");
        details.append(captureParameters(result)).append("\n");
        System.out.println(details.toString());
        return details.toString();
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	String failureDetails = captureFailureDetails(result);
        attachToReport("Test Failure Details", failureDetails);
    }

    // Capture test failure details
    public String captureFailureDetails(ITestResult result) {
        StringBuilder details = new StringBuilder();
        details.append("Test Method: ").append(result.getMethod().getMethodName()).append("\n");
        details.append("Test Description: ").append(result.getMethod().getDescription()).append("\n");
        details.append("Test Status: FAILED\n");
        details.append("Failure Reason: ").append(result.getThrowable().getMessage()).append("\n");
        details.append("\nParameters:\n");
        details.append(captureParameters(result)).append("\n");
        //System.out.println(details.toString());
        return details.toString();
    }

    public String captureParameters(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        Object[] parameters = result.getParameters();

        StringBuilder parametersText = new StringBuilder();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameters.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof ParameterLabel) {
                    ParameterLabel label = (ParameterLabel) annotation;
                    parametersText.append(label.value()).append(": ").append(parameters[i]).append("\n");
                }
            }
        }
        return parametersText.toString();
    }
    @Attachment(value = "{attachmentName}", type = "text/plain")
    public String attachToReport(String attachmentName, String content) {
        //System.out.println(content); // Debugging purposes - remove in final version
        return content;
    }
    
//    @Attachment(value = "Page screenshot", type = "image/png")
//	public byte[] saveScreenshotPNG() {
//    	
//		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//	}
}
