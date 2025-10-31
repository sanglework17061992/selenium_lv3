package com.sangle.selenium.validation;

import com.sangle.selenium.exceptions.ValidationException;
import com.sangle.selenium.logging.StepLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;
import java.util.regex.Pattern;

/**
 * Specialized validator for web page-level validations including URL, title, and page state.
 * Provides comprehensive page validation methods for test automation scenarios.
 * 
 * @author Sangle
 * @version 1.0
 * @since 2024-10-26
 */
public final class PageValidator {
    
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    public PageValidator(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public PageValidator(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }
    
    /**
     * Validates that current page URL matches expected URL exactly
     */
    public PageValidator urlEquals(String expectedUrl, String message) {
        StepLogger.info("Validating page URL equals: " + expectedUrl);
        try {
            String actualUrl = driver.getCurrentUrl();
            if (!expectedUrl.equals(actualUrl)) {
                String errorMsg = String.format("%s - URL mismatch. Expected: [%s], Actual: [%s]", 
                    message, expectedUrl, actualUrl);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page URL validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page URL validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - URL validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page URL validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that current page URL contains expected substring
     */
    public PageValidator urlContains(String expectedSubstring, String message) {
        StepLogger.info("Validating page URL contains: " + expectedSubstring);
        try {
            String actualUrl = driver.getCurrentUrl();
            if (!actualUrl.contains(expectedSubstring)) {
                String errorMsg = String.format("%s - URL [%s] does not contain [%s]", 
                    message, actualUrl, expectedSubstring);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page URL contains validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page URL contains validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - URL contains validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page URL contains validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that current page URL matches regex pattern
     */
    public PageValidator urlMatches(String regexPattern, String message) {
        StepLogger.info("Validating page URL matches pattern: " + regexPattern);
        try {
            String actualUrl = driver.getCurrentUrl();
            if (!Pattern.matches(regexPattern, actualUrl)) {
                String errorMsg = String.format("%s - URL [%s] does not match pattern [%s]", 
                    message, actualUrl, regexPattern);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page URL pattern validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page URL pattern validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - URL pattern validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page URL pattern validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that current page URL starts with expected prefix
     */
    public PageValidator urlStartsWith(String expectedPrefix, String message) {
        StepLogger.info("Validating page URL starts with: " + expectedPrefix);
        try {
            String actualUrl = driver.getCurrentUrl();
            if (!actualUrl.startsWith(expectedPrefix)) {
                String errorMsg = String.format("%s - URL [%s] does not start with [%s]", 
                    message, actualUrl, expectedPrefix);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page URL prefix validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page URL prefix validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - URL prefix validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page URL prefix validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that current page title matches expected title exactly
     */
    public PageValidator titleEquals(String expectedTitle, String message) {
        StepLogger.info("Validating page title equals: " + expectedTitle);
        try {
            String actualTitle = driver.getTitle();
            if (!expectedTitle.equals(actualTitle)) {
                String errorMsg = String.format("%s - Title mismatch. Expected: [%s], Actual: [%s]", 
                    message, expectedTitle, actualTitle);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page title validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page title validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Title validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page title validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that current page title contains expected substring
     */
    public PageValidator titleContains(String expectedSubstring, String message) {
        StepLogger.info("Validating page title contains: " + expectedSubstring);
        try {
            String actualTitle = driver.getTitle();
            if (!actualTitle.contains(expectedSubstring)) {
                String errorMsg = String.format("%s - Title [%s] does not contain [%s]", 
                    message, actualTitle, expectedSubstring);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page title contains validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page title contains validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Title contains validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page title contains validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that current page title matches regex pattern
     */
    public PageValidator titleMatches(String regexPattern, String message) {
        StepLogger.info("Validating page title matches pattern: " + regexPattern);
        try {
            String actualTitle = driver.getTitle();
            if (!Pattern.matches(regexPattern, actualTitle)) {
                String errorMsg = String.format("%s - Title [%s] does not match pattern [%s]", 
                    message, actualTitle, regexPattern);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page title pattern validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page title pattern validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Title pattern validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page title pattern validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that page has loaded completely (document ready state)
     */
    public PageValidator isPageLoaded(String message) {
        StepLogger.info("Validating page is fully loaded");
        try {
            wait.until(webDriver -> {
                String readyState = (String) ((org.openqa.selenium.JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState");
                return "complete".equals(readyState);
            });
            StepLogger.info("Page load validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Page load validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page load validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that page source contains expected text
     */
    public PageValidator sourceContains(String expectedText, String message) {
        StepLogger.info("Validating page source contains: " + expectedText);
        try {
            String pageSource = driver.getPageSource();
            if (!pageSource.contains(expectedText)) {
                String errorMsg = String.format("%s - Page source does not contain [%s]", 
                    message, expectedText);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page source validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page source validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Page source validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page source validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that page source does not contain expected text
     */
    public PageValidator sourceDoesNotContain(String unexpectedText, String message) {
        StepLogger.info("Validating page source does not contain: " + unexpectedText);
        try {
            String pageSource = driver.getPageSource();
            if (pageSource.contains(unexpectedText)) {
                String errorMsg = String.format("%s - Page source should not contain [%s]", 
                    message, unexpectedText);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page source negative validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page source negative validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Page source negative validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page source negative validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that current URL has valid format
     */
    public PageValidator hasValidUrl(String message) {
        StepLogger.info("Validating current URL has valid format");
        try {
            String currentUrl = driver.getCurrentUrl();
            new URL(currentUrl); // This will throw MalformedURLException if invalid
            StepLogger.info("URL format validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - URL format validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("URL format validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that page has HTTPS protocol
     */
    public PageValidator isSecure(String message) {
        StepLogger.info("Validating page uses HTTPS protocol");
        try {
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.startsWith("https://")) {
                String errorMsg = String.format("%s - Page is not secure. Current URL: [%s]", 
                    message, currentUrl);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page security validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page security validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Page security validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page security validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that page title is not empty
     */
    public PageValidator hasTitle(String message) {
        StepLogger.info("Validating page has a title");
        try {
            String title = driver.getTitle();
            if (title == null || title.trim().isEmpty()) {
                String errorMsg = String.format("%s - Page title is empty or null", message);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page title presence validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page title presence validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Page title presence validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page title presence validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that page response time is within acceptable limits
     */
    public PageValidator responseTimeWithin(long maxMilliseconds, String message) {
        StepLogger.info("Validating page response time within: " + maxMilliseconds + "ms");
        try {
            long startTime = System.currentTimeMillis();
            driver.getCurrentUrl(); // Simple operation to test responsiveness
            long responseTime = System.currentTimeMillis() - startTime;
            
            if (responseTime > maxMilliseconds) {
                String errorMsg = String.format("%s - Page response time [%d ms] exceeds maximum [%d ms]", 
                    message, responseTime, maxMilliseconds);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Page response time validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Page response time validation passed: " + message + " (Response time: " + responseTime + "ms)");
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Page response time validation failed: %s", message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Page response time validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
}