package com.project.btl.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public final class ScreenshotUtils {
    private ScreenshotUtils() {}

    public static Path takeScreenshot(WebDriver driver, Path destination) throws IOException {
        if (driver == null) throw new IllegalArgumentException("WebDriver is null");
        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalStateException("Driver does not support screenshots: " + driver.getClass());
        }

        Files.createDirectories(destination.getParent());

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return Files.copy(src.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
    }
}