package com.sangle.selenium.constants;

public final class FrameworkConstants {

    private FrameworkConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CONFIG_FILE = "config.properties";
    public static final String SCREENSHOT_DIR = "screenshots";
    public static final String ENV_BROWSER = "browser";
    public static final String ENV_BASE_URL = "base.url";
    public static final String ENV_IMPLICIT_TIMEOUT = "implicit.timeout.seconds";
    public static final String ENV_EXPLICIT_TIMEOUT = "explicit.timeout.seconds";
    public static final String ENV_PAGE_LOAD_TIMEOUT = "page.load.timeout.seconds";
    public static final String ENV_HEADLESS = "headless";
    public static final String ENV_ENVIRONMENT = "environment";
    public static final String ENV_RETRY_ATTEMPTS = "retry.attempts";
    public static final String ENV_RETRY_DELAY = "retry.delay.millis";
    public static final String ENV_TEST_RETRY_COUNT = "test.retry.count";
    public static final String ENV_SCREENSHOT_ON_FAILURE = "screenshot.on.failure";
}
