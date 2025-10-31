package com.sangle.selenium.validation;

import com.sangle.selenium.elements.BaseElement;
import com.sangle.selenium.elements.ElementState;
import com.sangle.selenium.exceptions.ValidationException;
import com.sangle.selenium.logging.StepLogger;
import com.sangle.selenium.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Specialized validator for web elements providing comprehensive validation methods.
 * Integrates with the framework's element state management and logging systems.
 * 
 * @author Sangle
 * @version 1.0
 * @since 2024-10-26
 */
public final class ElementValidator {
    
    private final WebDriver driver;
    
    public ElementValidator(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Validates that element is visible on the page
     */
    public ElementValidator isVisible(BaseElement element, String message) {
        StepLogger.info("Validating element visibility: " + element.getName());
        try {
            WaitUtils.waitForVisibility(driver, element.getLocator());
            if (!element.isDisplayed()) {
                String errorMsg = String.format("%s - Element [%s] is not visible", 
                    message, element.getName());
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element visibility validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element visibility validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] visibility check failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element visibility validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element is not visible on the page
     */
    public ElementValidator isNotVisible(BaseElement element, String message) {
        StepLogger.info("Validating element is not visible: " + element.getName());
        try {
            if (element.isDisplayed()) {
                String errorMsg = String.format("%s - Element [%s] should not be visible", 
                    message, element.getName());
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element invisibility validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element invisibility validation passed: " + message);
            return this;
        } catch (Exception e) {
            // If element is not found, that's acceptable for invisibility check
            StepLogger.info("Element invisibility validation passed: " + message + " (element not found)");
            return this;
        }
    }
    
    /**
     * Validates that element is enabled and interactable
     */
    public ElementValidator isEnabled(BaseElement element, String message) {
        StepLogger.info("Validating element is enabled: " + element.getName());
        try {
            WebElement webElement = driver.findElement(element.getLocator());
            if (!webElement.isEnabled()) {
                String errorMsg = String.format("%s - Element [%s] is not enabled", 
                    message, element.getName());
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element enabled validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element enabled validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] enabled check failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element enabled validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element is disabled
     */
    public ElementValidator isDisabled(BaseElement element, String message) {
        StepLogger.info("Validating element is disabled: " + element.getName());
        try {
            WebElement webElement = driver.findElement(element.getLocator());
            if (webElement.isEnabled()) {
                String errorMsg = String.format("%s - Element [%s] should be disabled", 
                    message, element.getName());
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element disabled validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element disabled validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] disabled check failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element disabled validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element's text matches expected value
     */
    public ElementValidator hasText(BaseElement element, String expectedText, String message) {
        StepLogger.info("Validating element text: " + element.getName());
        try {
            WebElement webElement = driver.findElement(element.getLocator());
            String actualText = webElement.getText();
            if (!expectedText.equals(actualText)) {
                String errorMsg = String.format("%s - Element [%s] text mismatch. Expected: [%s], Actual: [%s]", 
                    message, element.getName(), expectedText, actualText);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element text validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element text validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] text validation failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element text validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element's text contains expected substring
     */
    public ElementValidator textContains(BaseElement element, String expectedSubstring, String message) {
        StepLogger.info("Validating element text contains: " + element.getName());
        try {
            WebElement webElement = driver.findElement(element.getLocator());
            String actualText = webElement.getText();
            if (!actualText.contains(expectedSubstring)) {
                String errorMsg = String.format("%s - Element [%s] text [%s] does not contain [%s]", 
                    message, element.getName(), actualText, expectedSubstring);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element text contains validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element text contains validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] text contains validation failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element text contains validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element has specified attribute with expected value
     */
    public ElementValidator hasAttribute(BaseElement element, String attributeName, String expectedValue, String message) {
        StepLogger.info("Validating element attribute: " + element.getName());
        try {
            String actualValue = element.getAttribute(attributeName);
            if (!expectedValue.equals(actualValue)) {
                String errorMsg = String.format("%s - Element [%s] attribute [%s] mismatch. Expected: [%s], Actual: [%s]", 
                    message, element.getName(), attributeName, expectedValue, actualValue);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element attribute validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element attribute validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] attribute validation failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element attribute validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element is in expected state
     */
    public ElementValidator hasState(BaseElement element, ElementState expectedState, String message) {
        StepLogger.info("Validating element state: " + element.getName());
        try {
            ElementState actualState = element.getElementState();
            if (expectedState != actualState) {
                String errorMsg = String.format("%s - Element [%s] state mismatch. Expected: [%s], Actual: [%s]", 
                    message, element.getName(), expectedState, actualState);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element state validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element state validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] state validation failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element state validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element exists in DOM
     */
    public ElementValidator exists(BaseElement element, String message) {
        StepLogger.info("Validating element exists: " + element.getName());
        try {
            WebElement webElement = driver.findElement(element.getLocator());
            if (webElement == null) {
                String errorMsg = String.format("%s - Element [%s] does not exist in DOM", 
                    message, element.getName());
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element existence validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element existence validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] existence check failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element existence validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element does not exist in DOM
     */
    public ElementValidator doesNotExist(BaseElement element, String message) {
        StepLogger.info("Validating element does not exist: " + element.getName());
        try {
            List<WebElement> elements = driver.findElements(element.getLocator());
            if (!elements.isEmpty()) {
                String errorMsg = String.format("%s - Element [%s] should not exist in DOM but found %d instances", 
                    message, element.getName(), elements.size());
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element non-existence validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element non-existence validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] non-existence check failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element non-existence validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that element count matches expected value
     */
    public ElementValidator countEquals(BaseElement element, int expectedCount, String message) {
        StepLogger.info("Validating element count: " + element.getName());
        try {
            List<WebElement> elements = driver.findElements(element.getLocator());
            int actualCount = elements.size();
            if (expectedCount != actualCount) {
                String errorMsg = String.format("%s - Element [%s] count mismatch. Expected: [%d], Actual: [%d]", 
                    message, element.getName(), expectedCount, actualCount);
                ValidationException exception = new ValidationException(errorMsg);
                StepLogger.error("Element count validation failed: " + errorMsg, exception);
                throw exception;
            }
            StepLogger.info("Element count validation passed: " + message);
            return this;
        } catch (Exception e) {
            String errorMsg = String.format("%s - Element [%s] count validation failed: %s", 
                message, element.getName(), e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error("Element count validation failed: " + errorMsg, exception);
            throw exception;
        }
    }
}