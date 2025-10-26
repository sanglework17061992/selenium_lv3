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
    private final ElementStateManager stateManager;

    protected BaseElement(By locator, String name) {
        this.locator = Objects.requireNonNull(locator, "Locator cannot be null");
        this.name = Objects.requireNonNullElseGet(name, locator::toString);
        this.stateManager = new ElementStateManager(this.name);
    }

    protected BaseElement(By locator) {
        this(locator, locator.toString());
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected WebElement getVisibleElement() {
        return getSmartElement(() -> WaitUtils.waitForVisibility(getDriver(), locator));
    }

    protected WebElement getClickableElement() {
        return getSmartElement(() -> WaitUtils.waitForClickable(getDriver(), locator));
    }

    protected WebElement getPresentElement() {
        return getSmartElement(() -> WaitUtils.waitForPresence(getDriver(), locator));
    }

    /**
     * Smart element retrieval with state management and caching.
     * @param elementSupplier function to retrieve fresh element
     * @return WebElement instance
     */
    protected WebElement getSmartElement(java.util.function.Supplier<WebElement> elementSupplier) {
        // Check if cached element is still valid
        WebElement cachedElement = stateManager.getCachedElement();
        if (cachedElement != null) {
            StepLogger.info("Using cached element: " + name);
            return cachedElement;
        }
        
        // Element is stale or not cached, fetch new one
        if (stateManager.getCurrentState().needsRefresh()) {
            StepLogger.info("Refreshing element due to state: " + stateManager.getCurrentState().getDescription());
            stateManager.refreshElement();
        }
        
        WebElement freshElement = elementSupplier.get();
        stateManager.updateCache(freshElement);
        
        StepLogger.info("Element refreshed and cached - " + stateManager.getCacheStatistics());
        
        return freshElement;
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

    // Element state management methods
    
    /**
     * Gets the current state of the element.
     * @return current ElementState
     */
    public ElementState getElementState() {
        return stateManager.getCurrentState();
    }
    
    /**
     * Checks if element is currently cached and valid.
     * @return true if element is cached
     */
    public boolean isElementCached() {
        return stateManager.isCacheValid();
    }
    
    /**
     * Clears the element cache, forcing next interaction to re-locate the element.
     * @return this element for method chaining
     */
    public BaseElement clearElementCache() {
        performAction("clear element cache", () -> {
            stateManager.refreshElement();
            StepLogger.info("Element cache cleared: " + name);
        });
        return this;
    }
    
    /**
     * Gets the number of times this element became stale.
     * @return staleness count
     */
    public int getStalenessCount() {
        return stateManager.getStalenessCount();
    }
    
    /**
     * Gets the number of times this element was refreshed.
     * @return refresh count
     */
    public int getRefreshCount() {
        return stateManager.getRefreshCount();
    }
    
    /**
     * Forces cache refresh and returns this element for chaining.
     * @return this element for method chaining
     */
    public BaseElement refresh() {
        clearElementCache();
        return this;
    }
    
    /**
     * Sets custom cache validity duration for this element.
     * @param durationMs cache validity in milliseconds
     * @return this element for method chaining
     */
    public BaseElement setCacheValidityDuration(long durationMs) {
        stateManager.setCacheValidityDuration(durationMs);
        return this;
    }
    
    /**
     * Gets comprehensive cache statistics for debugging.
     * @return formatted statistics string
     */
    public String getCacheStatistics() {
        return stateManager.getCacheStatistics();
    }
}
