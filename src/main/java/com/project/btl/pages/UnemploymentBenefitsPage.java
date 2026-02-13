package com.project.btl.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class UnemploymentBenefitsPage extends BtlBasePage {

    @FindBy(xpath = "//a[.//strong[contains(text(),'למחשבוני דמי אבטלה')]]")
    private WebElement calculatorsLink;

    public UnemploymentBenefitsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * מעבר לדף "מחשבוני דמי אבטלה"
     */
    public UnemploymentCalculatorsPage goToCalculators() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(calculatorsLink)).click();
        return new UnemploymentCalculatorsPage(driver);
    }
}