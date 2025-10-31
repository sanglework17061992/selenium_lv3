package com.sangle.selenium.validation;

import com.sangle.selenium.exceptions.ValidationException;
import com.sangle.selenium.logging.StepLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Soft assertions collector that accumulates validation failures and reports them together.
 * Allows tests to continue executing even after assertion failures, collecting all issues
 * before throwing a consolidated exception.
 * 
 * @author Sangle
 * @version 1.0
 * @since 2024-10-26
 */
public final class SoftAssertions {
    
    private static final String SOFT_ASSERTION_FAILED_PREFIX = "Soft assertion failed: ";
    private static final String SOFT_ASSERTION_PASSED_PREFIX = "Soft assertion passed: ";
    
    private final List<AssertionError> failures = new ArrayList<>();
    private final boolean logFailuresImmediately;
    
    public SoftAssertions() {
        this.logFailuresImmediately = true;
    }
    
    public SoftAssertions(boolean logFailuresImmediately) {
        this.logFailuresImmediately = logFailuresImmediately;
    }
    
    /**
     * Soft assertion that condition is true
     */
    public SoftAssertions assertTrue(boolean condition, String message) {
        if (!condition) {
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + message);
            addFailure(error, message);
        } else {
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + message);
        }
        return this;
    }
    
    /**
     * Soft assertion that condition is false
     */
    public SoftAssertions assertFalse(boolean condition, String message) {
        return assertTrue(!condition, message);
    }
    
    /**
     * Soft assertion that two objects are equal
     */
    public SoftAssertions assertEquals(Object expected, Object actual, String message) {
        if (!java.util.Objects.equals(expected, actual)) {
            String errorMsg = String.format("%s - Expected: [%s], Actual: [%s]", 
                message, expected, actual);
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg);
            addFailure(error, errorMsg);
        } else {
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + message);
        }
        return this;
    }
    
    /**
     * Soft assertion that two objects are not equal
     */
    public SoftAssertions assertNotEquals(Object unexpected, Object actual, String message) {
        if (java.util.Objects.equals(unexpected, actual)) {
            String errorMsg = String.format("%s - Values should not be equal: [%s]", 
                message, unexpected);
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg);
            addFailure(error, errorMsg);
        } else {
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + message);
        }
        return this;
    }
    
    /**
     * Soft assertion that object is null
     */
    public SoftAssertions assertNull(Object object, String message) {
        if (object != null) {
            String errorMsg = String.format("%s - Expected null but was: [%s]", message, object);
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg);
            addFailure(error, errorMsg);
        } else {
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + message);
        }
        return this;
    }
    
    /**
     * Soft assertion that object is not null
     */
    public SoftAssertions assertNotNull(Object object, String message) {
        if (object == null) {
            String errorMsg = message + " - Expected non-null value";
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg);
            addFailure(error, errorMsg);
        } else {
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + message);
        }
        return this;
    }
    
    /**
     * Soft assertion that string contains expected substring
     */
    public SoftAssertions assertContains(String actual, String expected, String message) {
        if (actual == null || expected == null || !actual.contains(expected)) {
            String errorMsg = String.format("%s - String [%s] does not contain [%s]", 
                message, actual, expected);
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg);
            addFailure(error, errorMsg);
        } else {
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + message);
        }
        return this;
    }
    
    /**
     * Executes a supplier and captures any exceptions as soft assertion failures
     */
    public SoftAssertions assertDoesNotThrow(Supplier<Void> executable, String message) {
        try {
            executable.get();
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + message);
        } catch (Exception e) {
            String errorMsg = String.format("%s - Unexpected exception: %s", message, e.getMessage());
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg, e);
            addFailure(error, errorMsg);
        }
        return this;
    }
    
    /**
     * Executes a hard assertion and captures any ValidationException as soft failure
     */
    public SoftAssertions assertThat(Runnable hardAssertion, String context) {
        try {
            hardAssertion.run();
            StepLogger.info(SOFT_ASSERTION_PASSED_PREFIX + context);
        } catch (ValidationException e) {
            String errorMsg = String.format("%s - Validation failed: %s", context, e.getMessage());
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg, e);
            addFailure(error, errorMsg);
        } catch (Exception e) {
            String errorMsg = String.format("%s - Unexpected error: %s", context, e.getMessage());
            AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + errorMsg, e);
            addFailure(error, errorMsg);
        }
        return this;
    }
    
    /**
     * Adds a custom failure with message
     */
    public SoftAssertions addFailure(String message) {
        AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + message);
        addFailure(error, message);
        return this;
    }
    
    /**
     * Adds a custom failure with message and cause
     */
    public SoftAssertions addFailure(String message, Throwable cause) {
        AssertionError error = new AssertionError(SOFT_ASSERTION_FAILED_PREFIX + message, cause);
        addFailure(error, message);
        return this;
    }
    
    /**
     * Internal method to add failure to collection
     */
    private void addFailure(AssertionError error, String message) {
        failures.add(error);
        if (logFailuresImmediately) {
            StepLogger.error("Soft assertion failure recorded: " + message, error);
        }
    }
    
    /**
     * Checks if there are any assertion failures
     */
    public boolean hasFailures() {
        return !failures.isEmpty();
    }
    
    /**
     * Gets the number of assertion failures
     */
    public int getFailureCount() {
        return failures.size();
    }
    
    /**
     * Gets all failure messages as a list
     */
    public List<String> getFailureMessages() {
        return failures.stream()
            .map(Throwable::getMessage)
            .toList();
    }
    
    /**
     * Gets a consolidated failure report
     */
    public String getFailureReport() {
        if (failures.isEmpty()) {
            return "No soft assertion failures recorded.";
        }
        
        StringBuilder report = new StringBuilder();
        report.append(String.format("Soft Assertions Summary: %d failure(s) found%n", failures.size()));
        report.append("=".repeat(60)).append(String.format("%n"));
        
        for (int i = 0; i < failures.size(); i++) {
            AssertionError failure = failures.get(i);
            report.append(String.format("%d. %s%n", i + 1, failure.getMessage()));
            if (failure.getCause() != null) {
                report.append(String.format("   Caused by: %s%n", failure.getCause().getMessage()));
            }
        }
        
        report.append("=".repeat(60));
        return report.toString();
    }
    
    /**
     * Clears all recorded failures
     */
    public SoftAssertions clearFailures() {
        failures.clear();
        StepLogger.info("Soft assertion failures cleared");
        return this;
    }
    
    /**
     * Asserts all collected soft assertions and throws ValidationException if any failed
     */
    public void assertAll() {
        assertAll("Soft assertion verification");
    }
    
    /**
     * Asserts all collected soft assertions with custom message
     */
    public void assertAll(String message) {
        if (hasFailures()) {
            String fullReport = String.format("%s%n%n%s", message, getFailureReport());
            StepLogger.error("Soft assertions failed: " + message, new ValidationException(fullReport));
            
            // Create consolidated exception with all failure details
            ValidationException consolidatedException = new ValidationException(fullReport);
            
            // Add suppressed exceptions for each individual failure
            for (AssertionError failure : failures) {
                consolidatedException.addSuppressed(failure);
            }
            
            throw consolidatedException;
        } else {
            StepLogger.info("All soft assertions passed: " + message);
        }
    }
    
    /**
     * Returns summary statistics about the soft assertions
     */
    public String getSummary() {
        if (failures.isEmpty()) {
            return "Soft Assertions: All passed (0 failures)";
        } else {
            return String.format("Soft Assertions: %d failure(s) recorded", failures.size());
        }
    }
    
    /**
     * Creates a new SoftAssertions instance with the same configuration
     */
    public SoftAssertions newInstance() {
        return new SoftAssertions(this.logFailuresImmediately);
    }
    
    /**
     * Builder-style method to configure immediate logging
     */
    public static SoftAssertions withImmediateLogging() {
        return new SoftAssertions(true);
    }
    
    /**
     * Builder-style method to configure deferred logging
     */
    public static SoftAssertions withDeferredLogging() {
        return new SoftAssertions(false);
    }
}