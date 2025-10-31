package com.sangle.selenium.exceptions;

/**
 * Exception that indicates an operation can be retried.
 * This is typically thrown when a temporary condition (like stale elements) 
 * prevents an operation from succeeding, but the operation might succeed if retried.
 */
public class RetryableException extends RuntimeException {
    
    private final int retryAttempt;
    private final String elementName;
    
    public RetryableException(String message) {
        super(message);
        this.retryAttempt = 0;
        this.elementName = null;
    }
    
    public RetryableException(String message, Throwable cause) {
        super(message, cause);
        this.retryAttempt = 0;
        this.elementName = null;
    }
    
    public RetryableException(String message, Throwable cause, String elementName, int retryAttempt) {
        super(String.format("[Attempt %d] %s for element '%s'", retryAttempt, message, elementName));
        this.retryAttempt = retryAttempt;
        this.elementName = elementName;
        this.initCause(cause);
    }
    
    public int getRetryAttempt() {
        return retryAttempt;
    }
    
    public String getElementName() {
        return elementName;
    }
    
    public boolean isRetryable() {
        return true;
    }
}