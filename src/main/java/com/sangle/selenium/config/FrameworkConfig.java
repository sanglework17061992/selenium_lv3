package com.sangle.selenium.config;

import com.sangle.selenium.constants.BrowserType;
import com.sangle.selenium.constants.EnvironmentType;

public record FrameworkConfig(
        String baseUrl,
        BrowserType browserType,
        long implicitTimeoutSeconds,
        long explicitTimeoutSeconds,
        long pageLoadTimeoutSeconds,
        boolean headless,
        EnvironmentType environmentType,
        int retryAttempts,
        long retryDelayMillis,
        boolean screenshotOnFailure,
        int testRetryCount
) {
}
