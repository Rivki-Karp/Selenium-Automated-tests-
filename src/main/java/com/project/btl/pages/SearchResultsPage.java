package com.project.btl.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage extends BtlBasePage {

    // איתור הכותרת של דף תוצאות החיפוש
    // הערה: יש לוודא את ה-CSS הספציפי באתר ביטוח לאומי, בדרך כלל זו כותרת H1 או H2
    @FindBy(css = "#results h2")
    private WebElement resultsHeader;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * פונקציה המחזירה את הטקסט שמופיע בכותרת התוצאות
     */
    public String getHeaderText() {
        test.info("ממתין להופעת כותרת תוצאות החיפוש");
        // המתנה דינמית של עד 15 שניות לכותרת
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOf(resultsHeader));
            String text = header.getText();
            test.pass("כותרת התוצאות נמצאה: " + text);
            return text;
        } catch (Exception e) {
            test.fail("הכותרת לא הופיעה בדף התוצאות: " + e.getMessage());
            throw e;
        }
    }
}