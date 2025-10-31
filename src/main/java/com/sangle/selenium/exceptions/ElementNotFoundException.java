package com.sangle.selenium.exceptions;

import org.openqa.selenium.By;

/**
 * Exception thrown when an element cannot be found within the specified timeout.
 * This is a terminal exception that should not be retried.
 */
public class ElementNotFoundException extends RuntimeException {
    
    private final transient By locator;
    private final long timeoutSeconds;
    private final String elementName;
    
    public ElementNotFoundException(String message) {
        super(message);
        this.locator = null;
        this.timeoutSeconds = 0;
        this.elementName = null;
    }
    
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.locator = null;
        this.timeoutSeconds = 0;
        this.elementName = null;
    }
    
    public ElementNotFoundException(String elementName, By locator, long timeoutSeconds) {
        super(String.format("Element '%s' not found within %d seconds using locator: %s", 
                           elementName, timeoutSeconds, locator));
        this.elementName = elementName;
        this.locator = locator;
        this.timeoutSeconds = timeoutSeconds;
    }
    
    public ElementNotFoundException(String elementName, By locator, long timeoutSeconds, Throwable cause) {
        super(String.format("Element '%s' not found within %d seconds using locator: %s. Root cause: %s", 
                           elementName, timeoutSeconds, locator, cause.getMessage()), cause);
        this.elementName = elementName;
        this.locator = locator;
        this.timeoutSeconds = timeoutSeconds;
    }
    
    public By getLocator() {
        return locator;
    }
    
    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }
    
    public String getElementName() {
        return elementName;
    }
}