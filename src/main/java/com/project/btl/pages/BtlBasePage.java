package com.project.btl.pages;

import com.aventstack.extentreports.ExtentTest;
import com.project.btl.reports.ExtentTestManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BtlBasePage extends BasePage {

    @FindBy(id = "TopQuestions")
    private WebElement searchInput;

    @FindBy(id = "ctl00_Topmneu_BranchesHyperLink")
    private WebElement branchesButton;

    @FindBy(css = "a.SnifName")
    private WebElement firstBranchLink;

    @FindBy(id = "ctl00_SiteHeader_reserve_btnSearch")
    private WebElement searchButton;

    public BtlBasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * ביצוע חיפוש באתר עם דיווח והמתנה דינמית
     */
    public SearchResultsPage performSearch(String textToSearch) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        test.info("מתחיל תהליך חיפוש עבור הערך: " + textToSearch);

        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(textToSearch);
        test.pass("הזנת טקסט לחיפוש בוצעה");

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        test.pass("לחיצה על כפתור החיפוש בוצעה בהצלחה");

        return new SearchResultsPage(driver);
    }

    /**
     * ניווט לסניפים עם דיווח
     */
    public BranchesPage goToBranches() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        test.info("ניווט לעמוד סניפים");

        wait.until(ExpectedConditions.elementToBeClickable(branchesButton)).click();
        test.pass("עמוד סניפים נטען בהצלחה");

        return new BranchesPage(driver);
    }

    @FindBy(xpath="//a[@class='menuitem_drop topMenuItem' and contains(text(),'דמי ביטוח')]")
    private WebElement btlInsuranceMenuBtn;

    @FindBy(xpath="//ul/li/a[contains(text(),'דמי ביטוח לאומי')]")
    private WebElement btlNationalInsuranceSubMenuLink;

    /**
     * פתיחת תפריט דמי ביטוח ודיווח על השלבים
     */
    public void goToBtlNationalInsurancePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        test.info("פתיחת תפריט דמי ביטוח");
        wait.until(ExpectedConditions.elementToBeClickable(btlInsuranceMenuBtn)).click();

        test.info("בחירה בתת-תפריט דמי ביטוח לאומי");
        wait.until(ExpectedConditions.elementToBeClickable(btlNationalInsuranceSubMenuLink)).click();

        test.pass("ניווט לדמי ביטוח לאומי הושלם");
    }

    @FindBy(xpath = "//a[contains(@href,'/Simulators/BituahCalc/Pages/Insurance_NotSachir.aspx') and contains(.,'מחשבון לחישוב דמי הביטוח')]")
    protected WebElement btlInsuranceCalculatorLink;

    /**
     * קבלת טקסט ה-Breadcrumbs עם המתנה ודיווח
     */
    public String getBreadcrumbsText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        test.info("בודק את נתיב הניווט (Breadcrumbs)");

        try {
            WebElement breadcrumbs = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.breadcrumbs"))
            );
            String text = breadcrumbs.getText().trim();
            test.pass("טקסט הניווט נמצא: " + text);
            return text;
        } catch (Exception e) {
            test.fail("לא ניתן היה למצוא את אלמנט הניווט");
            throw e;
        }
    }
}