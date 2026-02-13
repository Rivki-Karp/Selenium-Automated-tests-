package com.project.btl.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentReportManager {

    private static ExtentReports extent;

    private ExtentReportManager() {}

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport/ExtentReport.html");
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("BTL - JUnit Jupiter");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    public static synchronized void flush() {
        if (extent != null) extent.flush();
    }
}