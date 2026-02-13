package com.project.btl.tests;

import com.project.btl.pages.BranchesPage;
import com.project.btl.pages.HomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BranchesTest extends BaseTest {

    @Test
    void testBranchesPageNavigation() {
        HomePage homePage = new HomePage(driver);

        // ניווט לדף הסניפים
        BranchesPage branchesPage = homePage.goToBranches();

        // חיפוש כותרת הדף (יש להתאים סלקטור מדויק לפי מה שרואים בדפדפן!)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement headerElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.opener-bg h1")
                )
        );

        // קבלת טקסט מהכותרת ובדיקה שהוא נכון
        String header = headerElement.getText();
        String expectedHeader = "סניפים וערוצי שירות";
        Assertions.assertTrue(header.contains(expectedHeader),
                "הכותרת שחזרה: " + header + " ולא '" + expectedHeader + "'");
    }

    @Test
    void testBranchInfoDisplay() {
        HomePage homePage = new HomePage(driver);
        BranchesPage branchesPage = homePage.goToBranches();

        // טיפול בטאב חדש אם יש (בהתאם לאתר שלך)
        String mainTab = driver.getWindowHandle();
        for(String handle : driver.getWindowHandles()) {
            if(!handle.equals(mainTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // לחיצה על שם הסניף הראשון
        WebElement firstBranchLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.SnifName")
        ));
        firstBranchLink.click();

        // המתנה עד שפרטי הסניף נגלים - חפש UL עם class "SnifDetails" שמכיל <label>כתובת</label> וכו'
        WebElement detailsList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("ul.SnifDetails")
        ));

        // חפש את כל הלייבלים (בדיקה לפי טקסט, אפשר גם כל אחד בנפרד)
        boolean addressExists = detailsList.findElements(By.xpath(".//label[text()='כתובת']")).size() > 0;
        boolean receptionExists = detailsList.findElements(By.xpath(".//label[text()='קבלת קהל']")).size() > 0;
        boolean phoneExists = detailsList.findElements(By.xpath(".//label[text()='מענה טלפוני']")).size() > 0;

        Assertions.assertTrue(addressExists, "לא נמצא טקסט 'כתובת'");
        Assertions.assertTrue(receptionExists, "לא נמצא טקסט 'קבלת קהל'");
        Assertions.assertTrue(phoneExists, "לא נמצא טקסט 'מענה טלפוני'");
    }
}