package com.project.btl.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BtlCalculatorPage extends BtlBasePage {

    public BtlCalculatorPage(WebDriver driver) {
        super(driver);
    }

    // --- אלמנטים שלב 1 ---
    @FindBy(xpath = "//label[contains(text(),'תלמיד ישיבה')]/preceding-sibling::input[@type='radio']")
    private WebElement yeshivaStudentRadio;

    @FindBy(xpath = "//label[contains(text(),'זכר')]/preceding-sibling::input[@type='radio' and not(@disabled)]")
    private WebElement genderMaleRadio;

    @FindBy(css = "input.date")
    private WebElement birthDateInput;

    @FindBy(css = "input[type='submit'].btnNext, input[type='submit'][value='המשך']")
    private WebElement nextButtonStep1;

    // --- אלמנטים שלב 2 ---
    @FindBy(xpath = "//label[@for='ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_GetNechut_1']")
    private WebElement disabilityNoLabel;

    @FindBy(id = "ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_StepNavigationTemplateContainerID_StepNextButton")
    private WebElement nextButtonStep2;

    // --- אלמנטים תוצאה ---
    @FindBy(xpath = "//h3[contains(normalize-space(text()),'תוצאת החישוב')]")
    private WebElement resultHeader;

    @FindBy(css = "div.ResultText ul.CalcResult")
    private WebElement calcResultList;

    // === מתודות שלב 1 ===

    public void chooseYeshivaStudent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        test.info("בוחר באפשרות: תלמיד ישיבה");
        wait.until(ExpectedConditions.elementToBeClickable(yeshivaStudentRadio)).click();
    }

    public void chooseGenderMale() {
        test.info("בוחר מין: זכר");
        driver.findElement(By.xpath("//label[contains(text(),'זכר')]")).click();
    }

    /**
     * הגרלת תאריך לידה (בין 18 ל-70 שנה אחורה) והזנתו
     */
    public void enterRandomBirthDate() {
        Random random = new Random();
        // הגרלת מספר שנים אחורה בין 18 ל-70
        int yearsBack = 18 + random.nextInt(53);
        LocalDate randomDate = LocalDate.now().minusYears(yearsBack).minusDays(random.nextInt(365));
        String formattedDate = randomDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        test.info("מגריל ומזין תאריך לידה: " + formattedDate);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.visibilityOf(birthDateInput));
        input.clear();
        input.sendKeys(formattedDate);
        input.sendKeys(Keys.TAB);
        test.pass("תאריך לידה הוזן בהצלחה");
    }

    public void clickNextStep1() {
        test.info("לחיצה על 'המשך' שלב 1");
        nextButtonStep1.click();
    }

    // === מתודות שלב 2 ===

    public void chooseNoDisability() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        test.info("בוחר 'לא' בקבלת קצבת נכות");
        wait.until(ExpectedConditions.elementToBeClickable(disabilityNoLabel)).click();
    }

    public void clickNextStep2() {
        test.info("לחיצה על 'המשך' שלב 2");
        nextButtonStep2.click();
    }

    // === מתודות סיכום ובדיקה ===

    public boolean isResultHeaderDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        boolean displayed = wait.until(ExpectedConditions.visibilityOf(resultHeader)).isDisplayed();
        if(displayed) test.pass("כותרת תוצאות החישוב מוצגת");
        return displayed;
    }

    public String getLeumiAmount() {
        String amount = parseResultAmount("דמי ביטוח לאומי:");
        test.info("דמי ביטוח לאומי שחושבו: " + amount);
        return amount;
    }

    public String getHealthAmount() {
        String amount = parseResultAmount("דמי ביטוח בריאות:");
        test.info("דמי ביטוח בריאות שחושבו: " + amount);
        return amount;
    }

    public String getTotalAmount() {
        String amount = parseResultAmount("סך הכל דמי ביטוח לחודש:");
        test.info("סך הכל לתשלום: " + amount);
        return amount;
    }

    private String parseResultAmount(String label) {
        for (WebElement li : calcResultList.findElements(By.tagName("li"))) {
            if (li.getText().contains(label)) {
                return li.findElement(By.tagName("strong")).getText().trim();
            }
        }
        return "";
    }
}