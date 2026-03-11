# Bituach Leumi (BTL) Automation Testing Project

A comprehensive Selenium WebDriver automation framework for testing the Israeli National Insurance Institute (Bituach Leumi) website - [btl.gov.il](https://www.btl.gov.il/).

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running Tests](#running-tests)
- [Test Scenarios](#test-scenarios)
- [Test Reports](#test-reports)
- [Key Features](#key-features)
- [Page Object Model](#page-object-model)

## 🎯 About the Project

This automation testing suite validates critical functionalities of the Bituach Leumi (Israeli National Insurance Institute) website, including:

- **Benefits Navigation & Breadcrumbs** - Navigation paths for unemployment, income support, disability, old age, and children benefits
- **Branch Locator** - Branch information, addresses, reception hours, and contact details
- **Insurance Calculators** - National insurance fee calculations based on user demographics
- **Unemployment Benefits Calculator** - Benefit calculation based on employment history and salary data
- **Search Functionality** - Site-wide search capabilities

## 🛠️ Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 19 | Programming language |
| **Selenium WebDriver** | 4.16.1 | Browser automation |
| **JUnit Jupiter** | 5.10.2 | Testing framework |
| **ExtentReports** | 5.1.2 | HTML test reporting |
| **WebDriverManager** | 5.6.3 | Automatic browser driver management |
| **Maven** | - | Build and dependency management |

## 📁 Project Structure

```
automation-project/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/project/btl/
│   │           ├── Main.java
│   │           ├── pages/              # Page Object Model classes
│   │           │   ├── BasePage.java
│   │           │   ├── BtlBasePage.java
│   │           │   ├── HomePage.java
│   │           │   ├── BranchesPage.java
│   │           │   ├── BtlCalculatorPage.java
│   │           │   ├── BtlNationalInsurancePage.java
│   │           │   ├── SearchResultsPage.java
│   │           │   ├── UnemploymentBenefitsPage.java
│   │           │   ├── UnemploymentCalculatorPage.java
│   │           │   └── UnemploymentCalculatorsPage.java
│   │           ├── reports/            # ExtentReports configuration
│   │           │   ├── ExtentReportManager.java
│   │           │   └── ExtentTestManager.java
│   │           └── utils/              # Utility classes
│   │               └── ScreenshotUtils.java
│   └── test/
│       └── java/
│           └── com/project/btl/
│               ├── extensions/
│               │   └── ExtentReportExtension.java
│               └── tests/              # Test classes
│                   ├── BaseTest.java
│                   ├── BenefitsBreadcrumbsTest.java
│                   ├── BranchesTest.java
│                   ├── BtlCalculatorTest.java
│                   ├── SearchTest.java
│                   └── UnemploymentCalculatorTest.java
├── target/
│   └── ExtentReport/                   # Generated test reports
│       ├── ExtentReport.html
│       └── screenshots/
└── pom.xml
```

## ✅ Prerequisites

Before running the tests, ensure you have the following installed:

- **Java Development Kit (JDK) 19** or higher
- **Maven 3.6+**
- **Google Chrome** browser (latest version)
- **Internet connection** (for accessing btl.gov.il and downloading WebDriver)

## 🚀 Installation & Setup

1. **Clone the repository** (or download the project)
   ```bash
   cd c:\Users\user1\Documents\תכנות\selenium\automation-project
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Verify installation**
   ```bash
   mvn test-compile
   ```

## ▶️ Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=BranchesTest
mvn test -Dtest=BtlCalculatorTest
mvn test -Dtest=SearchTest
```

### Run Tests with Maven Surefire
```bash
mvn clean test
```

### View Test Results
After test execution, open the HTML report:
```
target/ExtentReport/ExtentReport.html
```

## 🧪 Test Scenarios

### 1. **BenefitsBreadcrumbsTest** (5 parameterized tests)
Validates breadcrumb navigation for different benefit types:
- Unemployment (אבטלה)
- Income Support (הבטחת הכנסה)
- Disability (נכות)
- Old Age (זקנה)
- Children (ילדים)

### 2. **BranchesTest** (2 tests)
- Navigates to the branches page and verifies header
- Clicks on the first branch and validates:
  - Branch address
  - Reception hours
  - Phone number

### 3. **BtlCalculatorTest** (1 test)
Complete insurance calculator flow:
- Selects "Yeshiva Student" category
- Chooses male gender
- Generates random birthdate (18-70 years old)
- Selects "no disability" option
- Validates calculated insurance fee amounts

### 4. **SearchTest** (1 test)
- Performs site-wide search
- Verifies search results page displays correctly

### 5. **UnemploymentCalculatorTest** (1 test)
Complete unemployment benefits calculator:
- Enters employment termination date
- Enters age (28+)
- Fills in salary information
- Validates result fields are displayed

## 📊 Test Reports

### ExtentReports Features
- **Rich HTML Dashboard** - Colorful, interactive test execution report
- **Test Status** - Pass/Fail status for each test
- **Execution Timeline** - Test duration and timestamps
- **Error Details** - Full stack traces for failures
- **Screenshots** - Automatic screenshot capture on test failure
- **Logs** - Step-by-step test execution logs in Hebrew

### Report Location
- **HTML Report**: `target/ExtentReport/ExtentReport.html`
- **Screenshots**: `target/ExtentReport/screenshots/`

### Report Generation
Reports are automatically generated after each test run. Simply open the HTML file in any browser.

## ⭐ Key Features

### 1. **Page Object Model (POM) Architecture**
- Clean separation of page objects and test logic
- Reusable page components
- Easy maintenance and scalability

### 2. **Automatic Driver Management**
- WebDriverManager handles Chrome driver downloads and updates
- No manual driver setup required

### 3. **Robust Wait Strategies**
- Explicit waits with WebDriverWait (10-15 seconds)
- ExpectedConditions for element visibility and clickability
- Custom wait conditions for complex interactions

### 4. **Smart Element Interaction**
- Safe click implementation with JavaScript fallback
- Handles ElementClickInterceptedException automatically
- Multi-tab/window handling

### 5. **Thread-Safe Reporting**
- ThreadLocal ExtentTest for parallel execution support
- Automatic screenshot capture on failures
- Detailed Hebrew-language logging

### 6. **Dynamic Test Data**
- Random birthdate generation for varied test scenarios
- Current date-based calculations
- Parameterized testing support

### 7. **Localization Support**
- Full Hebrew language support in test logs and assertions
- Whitespace normalization for Hebrew text validation

### 8. **JUnit 5 Extensions**
- Custom ExtentReportExtension for automatic report integration
- Lifecycle hook integration (test start, failure, success, cleanup)

## 🏗️ Page Object Model

### Base Classes

#### **BasePage**
Abstract base class providing:
- WebDriver initialization
- ExtentTest instance for reporting
- PageFactory initialization

#### **BtlBasePage** (extends BasePage)
Common functionality for all BTL pages:
- `performSearch(String query)` - Site-wide search
- `goToBranches()` - Navigate to branches page
- `goToBtlNationalInsurancePage()` - Navigate via main menu
- `getBreadcrumbsText()` - Retrieve breadcrumb navigation
- `clickElement()` - Safe click with retry logic

### Page Classes

| Page Class | Responsibility |
|-----------|----------------|
| **HomePage** | Main landing page interactions |
| **BranchesPage** | Branch listing and details |
| **BtlCalculatorPage** | Insurance fee calculator |
| **BtlNationalInsurancePage** | National insurance information |
| **SearchResultsPage** | Search results display |
| **UnemploymentBenefitsPage** | Benefits information |
| **UnemploymentCalculatorPage** | Benefits calculator |
| **UnemploymentCalculatorsPage** | Calculator selection |

## 📝 Test Execution Flow

1. **@BeforeEach** - BaseTest initializes Chrome WebDriver and navigates to btl.gov.il
2. **Test Execution** - Page objects interact with site elements, logging each step
3. **On Failure** - ExtentReportExtension captures screenshot and logs exception
4. **@AfterEach** - WebDriver quits and cleans up resources
5. **Report Generation** - ExtentReportManager flushes HTML report with all results

## 🔧 Utilities

### **ScreenshotUtils**
Static utility class for screenshot management:
- Validates WebDriver capabilities
- Creates screenshot directories automatically
- Returns Path for report attachment
- Timestamped screenshot naming

## 📄 License

This project is created for educational and testing purposes.

## 👥 Contributing

This is an automation testing project. To contribute:
1. Follow the existing Page Object Model structure
2. Add tests in the appropriate test class
3. Update ExtentReport logs with Hebrew descriptions
4. Ensure all tests pass before committing

## 📧 Contact

For questions or issues, please refer to the project documentation or create an issue in the repository.

---

**Note**: This automation suite is designed to test a Hebrew-language government website. All test logs and assertions are in Hebrew to match the application under test.
