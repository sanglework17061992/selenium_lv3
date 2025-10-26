package com.sangle.selenium.utils;

import com.sangle.selenium.config.ConfigManager;
import com.sangle.selenium.config.FrameworkConfig;
import java.time.Duration;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public final class WaitUtils {

    private static final FrameworkConfig CONFIG = ConfigManager.getConfig();

    private WaitUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static WebElement waitForVisibility(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisibility(WebDriver driver, Function<WebDriver, WebElement> supplier) {
        return getWait(driver).until(driverInstance -> {
            WebElement resolvedElement = supplier.apply(driverInstance);
            return ExpectedConditions.visibilityOf(resolvedElement).apply(driverInstance);
        });
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForInvisibility(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForPageToLoad(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = webDriver -> {
            if (!(webDriver instanceof JavascriptExecutor javascriptExecutor)) {
                return true;
            }
            Object result = javascriptExecutor.executeScript("return document.readyState");
            return "complete".equals(result);
        };
        getWait(driver).until(expectation);
    }

    public static <T> T waitUntil(WebDriver driver, Function<WebDriver, T> condition) {
        return getWait(driver).until(condition);
    }

    private static FluentWait<WebDriver> getWait(WebDriver driver) {
    return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(CONFIG.explicitTimeoutSeconds()))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }
}
