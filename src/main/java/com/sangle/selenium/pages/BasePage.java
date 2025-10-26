package com.sangle.selenium.pages;

import com.sangle.selenium.config.ConfigManager;
import com.sangle.selenium.driver.DriverManager;
import com.sangle.selenium.logging.StepLogger;
import com.sangle.selenium.utils.WaitUtils;
import org.openqa.selenium.WebDriver;

public abstract class BasePage<T extends BasePage<T>> {

    protected final WebDriver driver;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
    }

    protected abstract T self();

    public abstract boolean isAt();

    public T open(String relativeUrl) {
        String targetUrl = ConfigManager.getConfig().baseUrl() + relativeUrl;
        StepLogger.info("Navigating to URL: " + targetUrl);
        driver.get(targetUrl);
        WaitUtils.waitForPageToLoad(driver);
        return self();
    }

    public String title() {
        return driver.getTitle();
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
