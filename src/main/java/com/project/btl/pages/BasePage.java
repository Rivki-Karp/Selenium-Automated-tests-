package com.project.btl.pages;

import com.aventstack.extentreports.ExtentTest;
import com.project.btl.reports.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected WebDriver driver;
    protected ExtentTest test;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.test = ExtentTestManager.getTest();

        if (this.test == null) {
            System.err.println("Warning: ExtentTest is null in BasePage!");
        }

        PageFactory.initElements(driver, this);
    }
}
