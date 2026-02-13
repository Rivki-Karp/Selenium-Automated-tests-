package com.project.btl.reports;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    private ExtentTestManager() {}

    public static ExtentTest getTest() {
        return TEST.get();
    }

    public static void setTest(ExtentTest test) {
        TEST.set(test);
    }

    public static void clear() {
        TEST.remove();
    }
}