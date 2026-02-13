package com.project.btl.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class UnemploymentCalculatorsPage extends BtlBasePage {

    @FindBy(xpath = "//a[contains(text(),'חישוב דמי אבטלה')]")
    private WebElement unemploymentCalculatorLink;

    public UnemploymentCalculatorsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * כניסה לדף חישוב דמי אבטלה עם דיווח והמתנה
     */
    public UnemploymentCalculatorPage goToUnemploymentCalculator() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        test.info("ניווט לדף מחשבון דמי אבטלה");

        wait.until(ExpectedConditions.elementToBeClickable(unemploymentCalculatorLink)).click();
        test.pass("מעבר למחשבון אבטלה בוצע בהצלחה");

        return new UnemploymentCalculatorPage(driver);
    }
}