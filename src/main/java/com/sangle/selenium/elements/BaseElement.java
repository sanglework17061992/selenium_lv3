package com.sangle.selenium.elements;

import com.sangle.selenium.driver.DriverManager;
import com.sangle.selenium.logging.StepLogger;
import com.sangle.selenium.utils.RetryUtils;
import com.sangle.selenium.utils.WaitUtils;
import java.util.Objects;
import java.util.function.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
            } catch (RuntimeException runtimeException) {
                StepLogger.actionFailure(name, action, locator, runtimeException);
                throw runtimeException;
            }
        });
    }

    protected JavascriptExecutor javaScript() {
        return (JavascriptExecutor) getDriver();
    }

    protected void scrollIntoView(WebElement element) {
        javaScript().executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
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
