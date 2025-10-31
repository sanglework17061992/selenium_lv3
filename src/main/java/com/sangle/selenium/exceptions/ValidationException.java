package com.sangle.selenium.exceptions;

/**
 * Exception thrown when validation or assertion operations fail.
 * Extends RuntimeException for unchecked exception handling in test automation scenarios.
 * 
 * @author Sangle
 * @version 1.0
 * @since 2024-10-26
 */
public class ValidationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new ValidationException with the specified detail message.
     *
     * @param message the detail message explaining the validation failure
     */
    public ValidationException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new ValidationException with the specified detail message and cause.
     *
     * @param message the detail message explaining the validation failure
     * @param cause the cause of the validation failure
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new ValidationException with the specified cause.
     *
     * @param cause the cause of the validation failure
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}