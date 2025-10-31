package com.sangle.selenium.validation;

import com.sangle.selenium.exceptions.ValidationException;
import com.sangle.selenium.logging.StepLogger;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Core validation engine providing comprehensive validation methods and assertion API.
 * Supports various validation operations for test automation scenarios.
 * 
 * @author Sangle
 * @version 1.0
 * @since 2024-10-26
 */
public final class ValidationEngine {
    
    private static final String ASSERTION_FAILED_PREFIX = "Assertion failed: ";
    private static final String ASSERTION_PASSED_PREFIX = "Assertion passed: ";
    
    private ValidationEngine() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Validates that condition is true
     */
    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            ValidationException exception = new ValidationException(message);
            StepLogger.error(ASSERTION_FAILED_PREFIX + message, exception);
            throw exception;
        }
        StepLogger.info(ASSERTION_PASSED_PREFIX + message);
    }
    
    /**
     * Validates that condition is false
     */
    public static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }
    
    /**
     * Validates that two objects are equal
     */
    public static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            String errorMsg = String.format("%s - Expected: [%s], Actual: [%s]", 
                message, expected, actual);
            ValidationException exception = new ValidationException(errorMsg);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        }
        StepLogger.info(ASSERTION_PASSED_PREFIX + message);
    }
    
    /**
     * Validates that two objects are not equal
     */
    public static void assertNotEquals(Object unexpected, Object actual, String message) {
        if (Objects.equals(unexpected, actual)) {
            String errorMsg = String.format("%s - Values should not be equal: [%s]", 
                message, unexpected);
            ValidationException exception = new ValidationException(errorMsg);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        }
        StepLogger.info(ASSERTION_PASSED_PREFIX + message);
    }
    
    /**
     * Validates that object is null
     */
    public static void assertNull(Object object, String message) {
        if (object != null) {
            String errorMsg = String.format("%s - Expected null but was: [%s]", message, object);
            ValidationException exception = new ValidationException(errorMsg);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        }
        StepLogger.info(ASSERTION_PASSED_PREFIX + message);
    }
    
    /**
     * Validates that object is not null
     */
    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            String errorMsg = message + " - Expected non-null value";
            ValidationException exception = new ValidationException(errorMsg);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        }
        StepLogger.info(ASSERTION_PASSED_PREFIX + message);
    }
    
    /**
     * Validates that string contains expected substring
     */
    public static void assertContains(String actual, String expected, String message) {
        assertNotNull(actual, "Actual string cannot be null");
        assertNotNull(expected, "Expected substring cannot be null");
        
        if (!actual.contains(expected)) {
            String errorMsg = String.format("%s - String [%s] does not contain [%s]", 
                message, actual, expected);
            ValidationException exception = new ValidationException(errorMsg);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        }
        StepLogger.info(ASSERTION_PASSED_PREFIX + message);
    }
    
    /**
     * Validates that string matches regex pattern
     */
    public static void assertMatches(String actual, String regex, String message) {
        assertNotNull(actual, "Actual string cannot be null");
        assertNotNull(regex, "Regex pattern cannot be null");
        
        if (!Pattern.matches(regex, actual)) {
            String errorMsg = String.format("%s - String [%s] does not match pattern [%s]", 
                message, actual, regex);
            ValidationException exception = new ValidationException(errorMsg);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        }
        StepLogger.info(ASSERTION_PASSED_PREFIX + message);
    }
    
    /**
     * Validates that supplier doesn't throw an exception
     */
    public static void assertDoesNotThrow(Supplier<Void> executable, String message) {
        try {
            executable.get();
            StepLogger.info(ASSERTION_PASSED_PREFIX + message);
        } catch (Exception e) {
            String errorMsg = String.format("%s - Unexpected exception: %s", 
                message, e.getMessage());
            ValidationException exception = new ValidationException(errorMsg, e);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        }
    }
    
    /**
     * Validates that supplier throws expected exception type
     */
    public static <T extends Throwable> T assertThrows(Class<T> expectedType, 
            Supplier<Void> executable, String message) {
        try {
            executable.get();
            String errorMsg = String.format("%s - Expected exception of type [%s] but none was thrown", 
                message, expectedType.getSimpleName());
            ValidationException exception = new ValidationException(errorMsg);
            StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
            throw exception;
        } catch (Exception e) {
            if (expectedType.isInstance(e)) {
                StepLogger.info(ASSERTION_PASSED_PREFIX + message);
                return expectedType.cast(e);
            } else {
                String errorMsg = String.format("%s - Expected [%s] but got [%s]: %s", 
                    message, expectedType.getSimpleName(), 
                    e.getClass().getSimpleName(), e.getMessage());
                ValidationException exception = new ValidationException(errorMsg, e);
                StepLogger.error(ASSERTION_FAILED_PREFIX + errorMsg, exception);
                throw exception;
            }
        }
    }
}