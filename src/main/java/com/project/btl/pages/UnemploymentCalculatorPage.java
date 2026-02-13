package com.project.btl.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Page Object עבור מחשבון דמי אבטלה עם דיווחי Extent Reports והמתנות דינמיות
 */
public class UnemploymentCalculatorPage extends BtlBasePage {

    public UnemploymentCalculatorPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input.date[id*='PiturimDate_Date']")
    private WebElement terminationDateInput;

    @FindBy(xpath = "//label[contains(text(),'מעל 28')]/preceding-sibling::input[@type='radio']")
    private WebElement ageAbove28Radio;

    @FindBy(css = "input[type='submit'].btnNext, input[type='submit'][id*='StartNextButton']")
    private WebElement firstNextBtn;

    @FindBy(xpath = "//input[contains(@id,'IncomeGrid') and contains(@class,'txtbox_sallary')]")
    private List<WebElement> salaryInputs;

    @FindBy(css = "input[type='submit'].btnNext, input[type='submit'][id*='StepNextButton']")
    private WebElement secondNextBtn;

    @FindBy(css = "div.CalcResult.ResultText ul")
    private WebElement resultsList;

    public void enterTerminationDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        test.info("מזין תאריך הפסקת עבודה: " + formattedDate);

        // המתנה דינמית: ממתין שהשדה יהיה ניתן ללחיצה
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(terminationDateInput));
            input.clear();
            input.sendKeys(formattedDate);
            input.sendKeys(Keys.TAB);
            test.pass("תאריך הפסקת עבודה הוזן בהצלחה");
        } catch (Exception e) {
            test.fail("נכשל בהזנת תאריך הפסקת עבודה: " + e.getMessage());
            throw e;
        }
    }

    public void selectAgeAbove28() {
        test.info("בוחר באפשרות גיל: מעל 28");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // המתנה דינמית לכפתור הרדיו
        wait.until(ExpectedConditions.elementToBeClickable(ageAbove28Radio)).click();
        test.pass("בחירת גיל בוצעה");
    }

    public void clickFirstNext() {
        test.info("לחיצה על כפתור 'המשך' (שלב 1)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(firstNextBtn)).click();
    }

    public void fillSalaries(int amount) {
        test.info("ממלא שדות שכר בסכום: " + amount);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            // המתנה דינמית עד שכל רשימת השדות תופיע בדף
            wait.until(ExpectedConditions.visibilityOfAllElements(salaryInputs));
            for (WebElement input : salaryInputs) {
                input.clear();
                input.sendKeys(String.valueOf(amount));
            }
            test.pass("כל שדות השכר מולאו בהצלחה");
        } catch (Exception e) {
            test.fail("שגיאה במילוי שדות השכר: " + e.getMessage());
            throw e;
        }
    }

    public void clickSecondNext() {
        test.info("לחיצה על כפתור 'המשך' לקבלת תוצאות (שלב 2)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(secondNextBtn)).click();
    }

    public boolean isResultDisplayed() {
        test.info("בודק האם תוצאות החישוב מוצגות כנדרש");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            // המתנה דינמית עד שהתוצאות יופיעו פיזית בדף
            wait.until(ExpectedConditions.visibilityOf(resultsList));
            String resultsText = resultsList.getText();

            boolean hasAvgSalary   = resultsText.contains("שכר יומי ממוצע");
            boolean hasDayBenefit  = resultsText.contains("דמי אבטלה ליום");
            boolean hasMonthBenefit= resultsText.contains("דמי אבטלה לחודש");

            if (hasAvgSalary && hasDayBenefit && hasMonthBenefit) {
                test.pass("כל תוצאות החישוב מוצגות");
                return true;
            } else {
                test.fail("חלק מנתוני התוצאה חסרים");
                return false;
            }
        } catch (TimeoutException e) {
            test.fail("תוצאות החישוב לא הופיעו בזמן הקצוב");
            return false;
        }
    }

    public String getResultValueByLabel(String labelText) {
        test.info("שליפת ערך עבור: " + labelText);
        try {
            // חיפוש דינמי בתוך הרשימה שכבר הופיעה
            for (WebElement li : resultsList.findElements(By.tagName("li"))) {
                WebElement label = li.findElement(By.tagName("label"));
                if (label.getText().contains(labelText)) {
                    String value = li.findElement(By.className("value")).getText().trim();
                    test.pass("נמצא ערך: " + value);
                    return value;
                }
            }
        } catch (Exception e) {
            test.info("לא נמצא ערך לתווית");
        }
        return "";
    }
}