package com.project.btl.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BtlNationalInsurancePage extends BtlBasePage {

    @FindBy(xpath = "//h1[contains(text(),'דמי ביטוח לאומי')]")
    private WebElement pageHeader;

    public BtlNationalInsurancePage(WebDriver driver) {
        super(driver);
    }

    public boolean isAt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(pageHeader)).isDisplayed();
    }
    /**
     * מעבר לדף מחשבון דמי ביטוח (BtlInsuranceCalculatorPage)
     */
    public BtlCalculatorPage goToBtlInsuranceCalculatorPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btlInsuranceCalculatorLink)).click();
        return new BtlCalculatorPage(driver);
    }
}