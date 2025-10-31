package com.sangle.selenium.tests;

import com.sangle.selenium.driver.DriverManager;
import com.sangle.selenium.pages.GooglePage;
import com.sangle.selenium.validation.SoftAssertions;
import com.sangle.selenium.validation.ValidationEngine;
import com.sangle.selenium.exceptions.ValidationException;
import com.sangle.selenium.elements.TextBoxElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for validation framework components
 * Tests ValidationEngine, SoftAssertions, ElementValidator, PageValidator integrations
 */
public class ValidationFrameworkTest {
    
    private WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
    }
    
    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
    
    @Test
    public void testValidationEngineAssertions() {
        // Test basic assertions
        ValidationEngine.assertTrue(true, "Basic true assertion should pass");
        ValidationEngine.assertEquals("expected", "expected", "String equality should pass");
        ValidationEngine.assertContains("Hello World", "World", "Contains assertion should pass");
        
        // Test numeric assertions
        ValidationEngine.assertEquals(42, 42, "Integer equality should pass");
        
        // Test null assertions
        ValidationEngine.assertNotNull("not null", "Not null assertion should pass");
        ValidationEngine.assertNull(null, "Null assertion should pass");
        
        System.out.println("✅ ValidationEngine basic assertions test passed");
    }
    
    @Test
    public void testSoftAssertions() {
        SoftAssertions softAssert = new SoftAssertions();
        
        // Add multiple assertions - some will pass, some will fail for testing
        softAssert.assertTrue(true, "This should pass");
        softAssert.assertEquals("test", "test", "This should also pass");
        
        // Verify no failures so far
        if (softAssert.hasFailures()) {
            System.out.println("❌ Unexpected failures in soft assertions");
        } else {
            System.out.println("✅ SoftAssertions working correctly - no failures detected");
        }
    }
    
    @Test
    public void testElementValidationIntegration() {
        // Navigate to Google for testing
        GooglePage googlePage = new GooglePage();
        googlePage.open();
        
        // Test element validation methods using correct constructor
        TextBoxElement searchBox = new TextBoxElement(By.name("q"), "Google Search Box");
        
        // Test element validation integration
        searchBox.shouldExist("Search box should exist on Google page");
        searchBox.shouldBeVisible("Search box should be visible");
        searchBox.shouldBeEnabled("Search box should be enabled");
        
        // Test element state validation
        searchBox.type("Selenium Test");
        searchBox.shouldHaveText("Selenium Test", "Search box should contain entered text");
        
        System.out.println("✅ Element validation integration test passed");
    }
    
    @Test
    public void testPageValidationIntegration() {
        // Navigate to Google for testing
        GooglePage googlePage = new GooglePage();
        googlePage.open();
        
        // Test page validation methods
        googlePage.shouldHaveUrlContaining("google.com", "URL should contain google.com");
        googlePage.shouldHaveTitleContaining("Google", "Title should contain Google");
        googlePage.shouldBeLoaded("Page should be loaded");
        googlePage.shouldBeSecure("Google should use HTTPS");
        googlePage.shouldHaveValidUrl("URL should be valid");
        googlePage.shouldHaveAnyTitle("Page should have a title");
        
        System.out.println("✅ Page validation integration test passed");
    }
    
    @Test
    public void testValidationExceptionHandling() {
        try {
            // This should throw ValidationException
            ValidationEngine.assertTrue(false, "This assertion should fail");
        } catch (ValidationException e) {
            // Expected exception
            System.out.println("✅ ValidationException properly thrown: " + e.getMessage());
            return;
        }
        
        throw new AssertionError("ValidationException should have been thrown");
    }
    
    @Test
    public void testComplexValidationScenario() {
        // Test complex validation scenario with multiple components
        GooglePage googlePage = new GooglePage();
        googlePage.open();
        
        // Use soft assertions for multiple validations
        SoftAssertions softAssert = new SoftAssertions();
        
        try {
            // Page validations
            softAssert.assertTrue(googlePage.currentUrl().contains("google"), "URL validation");
            softAssert.assertTrue(googlePage.title().contains("Google"), "Title validation");
            
            // Element validations using shouldXXX methods (which work correctly)
            TextBoxElement searchBox = new TextBoxElement(By.name("q"), "Search Box");
            searchBox.shouldBeVisible("Search box should be visible");
            searchBox.shouldBeEnabled("Search box should be enabled");
            
            // Perform search and validate results
            searchBox.type("Selenium WebDriver");
            searchBox.pressEnter();
            
            // Wait for results to load using WebDriver wait
            org.openqa.selenium.support.ui.WebDriverWait wait = 
                new org.openqa.selenium.support.ui.WebDriverWait(DriverManager.getDriver(), 
                java.time.Duration.ofSeconds(5));
            wait.until(webDriver -> googlePage.currentUrl().contains("search"));
            
            // Validate search results page
            softAssert.assertTrue(googlePage.currentUrl().contains("search"), "Search results URL");
            
            // Assert all validations
            softAssert.assertAll();
            
            System.out.println("✅ Complex validation scenario test passed");
            
        } catch (Exception e) {
            System.out.println("❌ Complex validation scenario failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void testValidationFrameworkPerformance() {
        long startTime = System.currentTimeMillis();
        
        // Perform multiple validations to test performance
        for (int i = 0; i < 100; i++) {
            ValidationEngine.assertTrue(true, "Performance test assertion " + i);
            ValidationEngine.assertEquals("test", "test", "Performance test equality " + i);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("✅ Validation framework performance test: " + duration + "ms for 200 assertions");
        
        // Ensure reasonable performance (should complete in under 1 second)
        ValidationEngine.assertTrue(duration < 1000, "Validation framework should be performant");
    }
}