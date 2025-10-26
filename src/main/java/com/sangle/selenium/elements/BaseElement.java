package com.sangle.selenium.elements;

import com.sangle.selenium.driver.DriverManager;
import com.sangle.selenium.exceptions.ElementNotFoundException;
import com.sangle.selenium.exceptions.OperationInterruptedException;
import com.sangle.selenium.exceptions.RetryableException;
import com.sangle.selenium.logging.StepLogger;
import com.sangle.selenium.utils.RetryUtils;
import com.sangle.selenium.utils.WaitUtils;
import java.util.Objects;
import java.util.function.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public abstract class BaseElement {

    private final By locator;
    private final String name;

    protected BaseElement(By locator, String name) {
        this.locator = Objects.requireNonNull(locator, "Locator cannot be null");
        this.name = Objects.requireNonNullElseGet(name, locator::toString);
    }

    protected BaseElement(By locator) {
        this(locator, locator.toString());
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected WebElement getVisibleElement() {
        return WaitUtils.waitForVisibility(getDriver(), locator);
    }

    protected WebElement getClickableElement() {
        return WaitUtils.waitForClickable(getDriver(), locator);
    }

    protected WebElement getPresentElement() {
        return WaitUtils.waitForPresence(getDriver(), locator);
    }

    protected void performAction(String action, Runnable runnable) {
        RetryUtils.retry(() -> {
            StepLogger.actionStart(name, action, locator);
            try {
                runnable.run();
                StepLogger.actionSuccess(name, action, locator, null);
            } catch (StaleElementReferenceException e) {
                StepLogger.actionFailure(name, action, locator, e);
                throw new RetryableException("Stale element reference, retrying...", e, name, 1);
            } catch (ElementClickInterceptedException e) {
                StepLogger.actionFailure(name, action, locator, e);
                handleClickInterception();
                StepLogger.actionSuccess(name, action + " (with recovery)", locator, null);
            } catch (org.openqa.selenium.ElementNotInteractableException e) {
                StepLogger.actionFailure(name, action, locator, e);
                waitForInteractable();
                throw new RetryableException("Element not interactable, retrying...", e, name, 1);
            } catch (TimeoutException | NoSuchElementException e) {
                StepLogger.actionFailure(name, action, locator, e);
                throw new ElementNotFoundException(name, locator, 30, e);
            } catch (WebDriverException e) {
                StepLogger.actionFailure(name, action, locator, e);
                throw new ElementNotFoundException("WebDriver operation failed for element: " + name, e);
            } catch (RuntimeException runtimeException) {
                StepLogger.actionFailure(name, action, locator, runtimeException);
                throw runtimeException;
            }
        });
    }

    protected <T> T performResult(String action, Supplier<T> actionSupplier) {
        return RetryUtils.retry((Supplier<T>) () -> {
            StepLogger.actionStart(name, action, locator);
            try {
                T result = actionSupplier.get();
                StepLogger.actionSuccess(name, action, locator, result);
                return result;
            } catch (StaleElementReferenceException e) {
                StepLogger.actionFailure(name, action, locator, e);
                throw new RetryableException("Stale element reference, retrying...", e, name, 1);
            } catch (TimeoutException | NoSuchElementException e) {
                StepLogger.actionFailure(name, action, locator, e);
                throw new ElementNotFoundException(name, locator, 30, e);
            } catch (RuntimeException runtimeException) {
                StepLogger.actionFailure(name, action, locator, runtimeException);
                throw runtimeException;
            }
        });
    }

    protected JavascriptExecutor javaScript() {
        return (JavascriptExecutor) getDriver();
    }

    protected Actions actions() {
        return new Actions(getDriver());
    }

    protected void scrollIntoView(WebElement element) {
        javaScript().executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Handles click interception by trying scroll and JS click fallback
     */
    private void handleClickInterception() {
        try {
            // First try scrolling into view
            WebElement element = getPresentElement();
            scrollIntoView(element);
            Thread.sleep(500); // Brief pause for smooth scrolling
            
            // Try regular click again
            element.click();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OperationInterruptedException("click interception handling", "interrupted during execution", e);
        } catch (Exception scrollException) {
            // If scroll doesn't work, use JavaScript click
            javaScript().executeScript("arguments[0].click();", getPresentElement());
        }
    }

    /**
     * Waits for element to become interactable
     */
    private void waitForInteractable() {
        try {
            // Wait for element to be both displayed and enabled
            WebElement element = getPresentElement();
            int attempts = 0;
            while (attempts < 10 && (!element.isDisplayed() || !element.isEnabled())) {
                Thread.sleep(500);
                element = getPresentElement();
                attempts++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            StepLogger.warn("Wait for interactable was interrupted: " + name);
        } catch (Exception e) {
            StepLogger.warn("Element still not interactable after wait: " + name);
        }
    }

    public By getLocator() {
        return locator;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return performResult("check if displayed", () -> getVisibleElement().isDisplayed());
    }

    public String getAttribute(String attributeName) {
        return performResult("get attribute: " + attributeName, () -> getVisibleElement().getAttribute(attributeName));
    }
}
