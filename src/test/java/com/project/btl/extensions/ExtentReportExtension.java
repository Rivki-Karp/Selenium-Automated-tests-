package com.project.btl.extensions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.project.btl.reports.ExtentReportManager;
import com.project.btl.reports.ExtentTestManager;
import com.project.btl.tests.BaseTest;
import com.project.btl.utils.ScreenshotUtils;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportExtension implements BeforeEachCallback, AfterTestExecutionCallback, AfterAllCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        String testName = context.getDisplayName();
        ExtentTest test = ExtentReportManager.getExtent().createTest(testName);
        ExtentTestManager.setTest(test);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        ExtentTest test = ExtentTestManager.getTest();
        if (test == null) return;

        if (context.getExecutionException().isPresent()) {
            Throwable t = context.getExecutionException().get();
            test.log(Status.FAIL, t);

            WebDriver driver = extractDriver(context);
            if (driver != null) {
                attachScreenshot(test, context, driver);
            } else {
                test.log(Status.FAIL, "WebDriver not available for screenshot");
            }
        } else {
            test.log(Status.PASS, "Test passed");
        }

        ExtentTestManager.clear();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        ExtentReportManager.flush();
    }

    private WebDriver extractDriver(ExtensionContext context) {
        Object instance = context.getRequiredTestInstance();
        if (instance instanceof BaseTest base) {
            return base.getDriver();
        }
        return null;
    }

    private void attachScreenshot(ExtentTest test, ExtensionContext context, WebDriver driver) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String methodName = context.getRequiredTestMethod().getName();
            String fileName = methodName + "_" + timestamp + ".png";

            Path reportDir = Paths.get("target", "ExtentReport");
            Path fullPath = reportDir.resolve(Paths.get("screenshots", fileName));

            ScreenshotUtils.takeScreenshot(driver, fullPath);

            // Path יחסי מתוך ExtentReport.html
            String relativePath = ("screenshots/" + fileName);

            test.fail("Screenshot on failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());

        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to capture screenshot: " + e.getMessage());
        }
    }
}