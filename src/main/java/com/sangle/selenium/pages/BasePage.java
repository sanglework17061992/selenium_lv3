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

    // Page validation methods - integrating with validation framework
    
    /**
     * Creates a PageValidator for this page with the current driver
     * @return PageValidator instance for chaining validations
     */
    public com.sangle.selenium.validation.PageValidator validatePage() {
        return new com.sangle.selenium.validation.PageValidator(driver);
    }
    
    /**
     * Validates that current URL equals expected URL
     * @param expectedUrl expected URL
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveUrl(String expectedUrl, String message) {
        validatePage().urlEquals(expectedUrl, message);
        return self();
    }
    
    /**
     * Validates that current URL contains expected substring
     * @param expectedSubstring expected URL substring
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveUrlContaining(String expectedSubstring, String message) {
        validatePage().urlContains(expectedSubstring, message);
        return self();
    }
    
    /**
     * Validates that current URL matches regex pattern
     * @param regexPattern regex pattern for URL
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveUrlMatching(String regexPattern, String message) {
        validatePage().urlMatches(regexPattern, message);
        return self();
    }
    
    /**
     * Validates that current URL starts with expected prefix
     * @param expectedPrefix expected URL prefix
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveUrlStartingWith(String expectedPrefix, String message) {
        validatePage().urlStartsWith(expectedPrefix, message);
        return self();
    }
    
    /**
     * Validates that page title equals expected title
     * @param expectedTitle expected page title
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveTitle(String expectedTitle, String message) {
        validatePage().titleEquals(expectedTitle, message);
        return self();
    }
    
    /**
     * Validates that page title contains expected substring
     * @param expectedSubstring expected title substring
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveTitleContaining(String expectedSubstring, String message) {
        validatePage().titleContains(expectedSubstring, message);
        return self();
    }
    
    /**
     * Validates that page title matches regex pattern
     * @param regexPattern regex pattern for title
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveTitleMatching(String regexPattern, String message) {
        validatePage().titleMatches(regexPattern, message);
        return self();
    }
    
    /**
     * Validates that page has loaded completely
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldBeLoaded(String message) {
        validatePage().isPageLoaded(message);
        return self();
    }
    
    /**
     * Validates that page source contains expected text
     * @param expectedText expected text in page source
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldContainText(String expectedText, String message) {
        validatePage().sourceContains(expectedText, message);
        return self();
    }
    
    /**
     * Validates that page source does not contain text
     * @param unexpectedText text that should not be in page source
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldNotContainText(String unexpectedText, String message) {
        validatePage().sourceDoesNotContain(unexpectedText, message);
        return self();
    }
    
    /**
     * Validates that page uses HTTPS protocol
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldBeSecure(String message) {
        validatePage().isSecure(message);
        return self();
    }
    
    /**
     * Validates that page has a valid URL format
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveValidUrl(String message) {
        validatePage().hasValidUrl(message);
        return self();
    }
    
    /**
     * Validates that page has any title (not empty)
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldHaveAnyTitle(String message) {
        validatePage().hasTitle(message);
        return self();
    }
    
    /**
     * Validates that page response time is within limits
     * @param maxMilliseconds maximum response time in milliseconds
     * @param message validation message
     * @return this page for method chaining
     */
    public T shouldRespondWithin(long maxMilliseconds, String message) {
        validatePage().responseTimeWithin(maxMilliseconds, message);
        return self();
    }
}
