package com.sangle.selenium.utils;

import com.sangle.selenium.constants.FrameworkConstants;
import com.sangle.selenium.driver.DriverManager;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ScreenshotUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Path captureScreenshot(String name) {
        WebDriver driver = DriverManager.getDriver();
        if (!(driver instanceof TakesScreenshot screenshot)) {
            LOG.warn("Driver does not support screenshots");
            return null;
        }
        byte[] screenshotBytes;
        try {
            screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException exception) {
            LOG.error("Failed to capture screenshot", exception);
            return null;
        }
        Path screenshotPath = buildScreenshotPath(name);
        try {
            Files.createDirectories(screenshotPath.getParent());
            FileUtils.writeByteArrayToFile(screenshotPath.toFile(), screenshotBytes);
        } catch (IOException exception) {
            LOG.error("Failed to persist screenshot", exception);
        }
        Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes));
        return screenshotPath;
    }

    private static Path buildScreenshotPath(String name) {
        String sanitizedName = name.replaceAll("[^a-zA-Z0-9_-]", "_");
        String fileName = sanitizedName + "_" + FORMATTER.format(LocalDateTime.now()) + ".png";
        return Paths.get("target", FrameworkConstants.SCREENSHOT_DIR, fileName);
    }
}
