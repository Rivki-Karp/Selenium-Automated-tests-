package com.project.btl.tests;

import com.project.btl.pages.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.LocalDate;

public class UnemploymentCalculatorTest extends BaseTest {

    @Test
    void testUnemploymentCalculationFlow() {
        // 1. כניסה לדף הבית
        HomePage homePage = new HomePage(driver);

        // 2. פתיחת "קצבאות והטבות"
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement mainMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_Topmneu_HyperLink3")));
        mainMenu.click();

        // 3. בחירה באבטלה
        WebElement unemploymentLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul/li/a[@href='/benefits/Unemployment/Pages/default.aspx']")));
        unemploymentLink.click();

        // 4. ניווט לדף מחשבוני דמי אבטלה
        UnemploymentBenefitsPage benefitsPage = new UnemploymentBenefitsPage(driver);
        UnemploymentCalculatorsPage calculatorsPage = benefitsPage.goToCalculators();

        // 5. כניסה למחשבון רלוונטי
        UnemploymentCalculatorPage calculatorPage = calculatorsPage.goToUnemploymentCalculator();

        // 6. הזנת תאריך הפסקת עבודה (עד חודש אחורה מהיום)
        calculatorPage.enterTerminationDate(LocalDate.now().minusDays(20));

        // 7. בחירת גיל "מעל 28"
        calculatorPage.selectAgeAbove28();

        // 8. המשך
        calculatorPage.clickFirstNext();

        // 9. הזנת סכום השתכרות – לדוג': 20000 בכל שדה
        calculatorPage.fillSalaries(20000);
        calculatorPage.clickSecondNext();

        // 10. בדיקת הופעת שדות תוצאת החישוב (בלי לבדוק סכומים)
        Assertions.assertTrue(calculatorPage.isResultDisplayed(),
                "לא הופיעו תוצאות חישוב צפויות בדף התוצאות ('שכר יומי ממוצע', 'דמי אבטלה ליום' וכו')");
    }
}