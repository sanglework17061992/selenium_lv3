package com.sangle.selenium.listeners;

import com.sangle.selenium.config.ConfigManager;
import com.sangle.selenium.driver.DriverManager;
import com.sangle.selenium.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import java.nio.file.Path;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public final class FrameworkTestListener implements ITestListener {

    private static final Logger LOG = LoggerFactory.getLogger(FrameworkTestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("Starting test: {}", result.getName());
        try {
            Allure.parameter("Test Name", result.getName());
        } catch (IllegalStateException exception) {
            LOG.debug("Unable to set Allure parameter at test start", exception);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("Test succeeded: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("Test failed: {}", result.getName(), result.getThrowable());
        if (!ConfigManager.getConfig().screenshotOnFailure()) {
            return;
        }
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                Path path = ScreenshotUtil.captureScreenshot(result.getName());
                if (path != null) {
                    LOG.info("Screenshot captured: {}", path.toAbsolutePath());
                }
            }
        } catch (IllegalStateException exception) {
            LOG.warn("Unable to capture screenshot. Driver not available.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("Test skipped: {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        LOG.info("Starting suite: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOG.info("Suite finished: {}", context.getName());
    }
}
