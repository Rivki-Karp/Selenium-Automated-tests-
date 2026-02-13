package com.project.btl.tests;

import com.project.btl.pages.HomePage;
import com.project.btl.pages.SearchResultsPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SearchTest extends BaseTest {

    @Test
    void testSearchFunctionality() {
        // 1. אתחול דף הבית
        HomePage homePage = new HomePage(driver);

        // 2. הגדרת מחרוזת החיפוש
        String searchTerm = "חישוב סכום דמי לידה ליום";

        // 3. ביצוע החיפוש דרך המתודה שהגדרנו ב-BtlBasePage
        SearchResultsPage resultsPage = homePage.performSearch(searchTerm);

        // 4. קבלת הכותרת שהופיעה בפועל בדף התוצאות
        String actualTitle = resultsPage.getHeaderText();

        // 5. הגדרת התוצאה הצפויה
        String expectedTitle = "תוצאות חיפוש עבור " + searchTerm;

        // 6. בדיקת ההתאמה (Assertion)
        Assertions.assertTrue(actualTitle.contains(expectedTitle),
                "הכותרת בדף התוצאות אינה תואמת לציפייה. הופיע: " + actualTitle);
    }
}