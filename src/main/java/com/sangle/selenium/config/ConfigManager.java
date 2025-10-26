package com.sangle.selenium.config;

import com.sangle.selenium.constants.BrowserType;
import com.sangle.selenium.constants.EnvironmentType;
import com.sangle.selenium.constants.FrameworkConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public final class ConfigManager {

    private static final AtomicReference<FrameworkConfig> CONFIG_CACHE = new AtomicReference<>();

    private ConfigManager() {
        throw new IllegalStateException("Utility class");
    }

    public static FrameworkConfig getConfig() {
        FrameworkConfig cachedConfig = CONFIG_CACHE.get();
        if (Objects.nonNull(cachedConfig)) {
            return cachedConfig;
        }
        FrameworkConfig resolvedConfig = loadConfig();
        if (CONFIG_CACHE.compareAndSet(null, resolvedConfig)) {
            return resolvedConfig;
        }
        return CONFIG_CACHE.get();
    }

    private static FrameworkConfig loadConfig() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(FrameworkConstants.CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Unable to locate " + FrameworkConstants.CONFIG_FILE + " on classpath");
            }
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load configuration file", exception);
        }
        overrideFromSystemProperties(properties);
        return new FrameworkConfig(
                properties.getProperty(FrameworkConstants.ENV_BASE_URL),
                BrowserType.from(properties.getProperty(FrameworkConstants.ENV_BROWSER)),
                Long.parseLong(properties.getProperty(FrameworkConstants.ENV_IMPLICIT_TIMEOUT)),
                Long.parseLong(properties.getProperty(FrameworkConstants.ENV_EXPLICIT_TIMEOUT)),
                Long.parseLong(properties.getProperty(FrameworkConstants.ENV_PAGE_LOAD_TIMEOUT)),
                Boolean.parseBoolean(properties.getProperty(FrameworkConstants.ENV_HEADLESS)),
                EnvironmentType.from(properties.getProperty(FrameworkConstants.ENV_ENVIRONMENT)),
                Integer.parseInt(properties.getProperty(FrameworkConstants.ENV_RETRY_ATTEMPTS)),
                Long.parseLong(properties.getProperty(FrameworkConstants.ENV_RETRY_DELAY)),
                Boolean.parseBoolean(properties.getProperty(FrameworkConstants.ENV_SCREENSHOT_ON_FAILURE)),
                Integer.parseInt(properties.getProperty(FrameworkConstants.ENV_TEST_RETRY_COUNT, "0"))
        );
    }

    private static void overrideFromSystemProperties(Properties properties) {
        properties.forEach((key, value) -> {
            String resolvedValue = System.getProperty(key.toString());
            if (Objects.nonNull(resolvedValue) && !resolvedValue.isBlank()) {
                properties.setProperty(key.toString(), resolvedValue);
            }
        });
    }
}
