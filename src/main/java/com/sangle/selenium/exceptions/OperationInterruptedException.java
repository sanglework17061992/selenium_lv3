package com.sangle.selenium.exceptions;

/**
 * Exception thrown when an operation is interrupted during execution.
 * This typically happens when a thread is interrupted while waiting.
 */
public class OperationInterruptedException extends RuntimeException {
    
    private final String operationName;
    
    public OperationInterruptedException(String operationName, String message) {
        super(String.format("Operation '%s' was interrupted: %s", operationName, message));
        this.operationName = operationName;
    }
    
    public OperationInterruptedException(String operationName, String message, Throwable cause) {
        super(String.format("Operation '%s' was interrupted: %s", operationName, message), cause);
        this.operationName = operationName;
    }
    
    public String getOperationName() {
        return operationName;
    }
}