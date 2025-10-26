package com.sangle.selenium.tests;

import com.sangle.selenium.driver.DriverManager;
import com.sangle.selenium.listeners.FrameworkTestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({FrameworkTestListener.class})
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            WebDriver driver = DriverManager.getDriver();
            driver.manage().deleteAllCookies();
        } catch (IllegalStateException ignored) {
            // Driver was not initialised; nothing to clean up.
        } finally {
            DriverManager.quitDriver();
        }
    }
}
