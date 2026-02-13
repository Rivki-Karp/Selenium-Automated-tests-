package com.project.btl.tests;

import com.project.btl.pages.BtlCalculatorPage;
import com.project.btl.pages.BtlNationalInsurancePage;
import com.project.btl.pages.HomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BtlCalculatorTest extends BaseTest {

    @Test
    void testYeshivaStudentCalculationFullFlow() {
        // ניווט והגעה לעמוד המחשבון
        HomePage homePage = new HomePage(driver);
        homePage.goToBtlNationalInsurancePage();
        BtlNationalInsurancePage nip = new BtlNationalInsurancePage(driver);
        nip.goToBtlInsuranceCalculatorPage();
        BtlCalculatorPage calcPage = new BtlCalculatorPage(driver);

        // שלב 1
        calcPage.chooseYeshivaStudent();
        calcPage.chooseGenderMale();
        calcPage.enterRandomBirthDate();
        calcPage.clickNextStep1();

        // שלב 2: בחרי "לא" בקצבת נכות והמשך
        calcPage.chooseNoDisability();
        calcPage.clickNextStep2();

        // שלב סיכום - בדיקת תוצאות
        Assertions.assertTrue(calcPage.isResultHeaderDisplayed(), "לא הופיעה כותרת תוצאת החישוב!");

        // ייבוא וסידור תוצאות
        String leumi = calcPage.getLeumiAmount();
        String health = calcPage.getHealthAmount();
        String total = calcPage.getTotalAmount();

        System.out.println("דמי ביטוח לאומי: " + leumi);
        System.out.println("דמי ביטוח בריאות: " + health);
        System.out.println("סך הכל דמי ביטוח לחודש: " + total);

        // בדיקה – תעדכני לפי הסכומים שמצופה להחזיר (בדוג': 43, 120.00, 163)
        Assertions.assertEquals("43", leumi, "ערך לא נכון לדמי ביטוח לאומי!");
        Assertions.assertEquals("120.00", health, "ערך לא נכון לדמי ביטוח בריאות!");
        Assertions.assertEquals("163", total, "ערך לא נכון לסך הכל דמי ביטוח!");
    }
}