package com.sangle.selenium.listeners;

import com.sangle.selenium.config.ConfigManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public final class TestRetryAnalyzer implements IRetryAnalyzer {

    private int attempt = 0;

    @Override
    public boolean retry(ITestResult result) {
        int maxAttempts = ConfigManager.getConfig().testRetryCount();
        if (attempt < maxAttempts) {
            attempt++;
            return true;
        }
        return false;
    }
}
