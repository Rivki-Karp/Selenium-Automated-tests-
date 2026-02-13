package com.project.btl.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.stream.Stream;

public class BenefitsBreadcrumbsTest extends BaseTest {

    private static final By BENEFITS_MENU = By.id("ctl00_Topmneu_HyperLink3");

    private static final By BREADCRUMBS = By.xpath(
            "//*[contains(@class,'breadcrumbs') or contains(@class,'breadcrumb') or @id='breadcrumbs' or @aria-label='breadcrumb']"
    );

    record Case(String linkText, String expectedBreadcrumbText) {}

    static Stream<Case> cases() {
        return Stream.of(
                new Case("אבטלה", "אבטלה"),
                new Case("הבטחת הכנסה", "הבטחת הכנסה"),
                new Case("נכות", "נכות"),
                new Case("זקנה", "זקנה"),
                new Case("ילדים", "ילדים")
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("cases")
    void benefitsBreadcrumbs_shouldContainExpectedText(Case tc) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        // פתח תפריט קצבאות והטבות
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(BENEFITS_MENU));
        openMenu(menu);

        // מצא קישור לפי טקסט
        By linkBy = By.xpath("//a[normalize-space()='" + tc.linkText() + "' or contains(normalize-space(),'" + tc.linkText() + "')]");
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(linkBy));
        safeClick(link);

        // המתן שה-breadcrumbs יתעדכן (לא רק שיופיע)
        WebElement breadcrumbsEl = wait.until(ExpectedConditions.visibilityOfElementLocated(BREADCRUMBS));
        wait.until(d -> normalizeWhitespace(breadcrumbsEl.getText()).contains(tc.expectedBreadcrumbText()));

        String breadcrumbsText = normalizeWhitespace(breadcrumbsEl.getText());
        Assertions.assertTrue(
                breadcrumbsText.contains(tc.expectedBreadcrumbText()),
                () -> "Breadcrumb לא תקין. מצופה להכיל: '" + tc.expectedBreadcrumbText() + "' אבל היה: '" + breadcrumbsText + "'"
        );
    }

    private void openMenu(WebElement menuElement) {
        try {
            new Actions(driver).moveToElement(menuElement).pause(Duration.ofMillis(200)).perform();
        } catch (Exception ignored) {}
        safeClick(menuElement);
    }

    private void safeClick(WebElement el) {
        try {
            el.click();
        } catch (ElementClickInterceptedException | JavascriptException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    private static String normalizeWhitespace(String s) {
        if (s == null) return "";
        return s.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
    }
}