package com.sangle.selenium.driver;

import com.sangle.selenium.config.ConfigManager;
import com.sangle.selenium.config.FrameworkConfig;
import java.time.Duration;
import java.util.Objects;
import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
        throw new IllegalStateException("Utility class");
    }

    public static void initDriver() {
        if (Objects.nonNull(DRIVER.get())) {
            return;
        }
        FrameworkConfig config = ConfigManager.getConfig();
        WebDriver driver = new WebDriverFactory().createWebDriver(config);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.implicitTimeoutSeconds()));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(config.explicitTimeoutSeconds()));
        if (!config.headless()) {
            driver.manage().window().maximize();
        }
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (Objects.isNull(driver)) {
            throw new IllegalStateException("WebDriver is not initialized for the current thread");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (Objects.nonNull(driver)) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
