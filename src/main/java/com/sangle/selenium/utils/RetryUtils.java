package com.sangle.selenium.utils;

import com.sangle.selenium.config.ConfigManager;
import com.sangle.selenium.config.FrameworkConfig;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RetryUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RetryUtils.class);
    private static final FrameworkConfig CONFIG = ConfigManager.getConfig();

    private RetryUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T retry(Callable<T> callable) {
        int attempts = CONFIG.retryAttempts();
        long delay = CONFIG.retryDelayMillis();
        int tryCount = 0;
        Throwable lastError = null;
        while (tryCount <= attempts) {
            try {
                return callable.call();
            } catch (Exception exception) {
                lastError = exception;
                LOG.warn("Retry attempt {} failed: {}", tryCount + 1, exception.getMessage());
                sleep(delay);
                tryCount++;
            }
        }
        if (lastError instanceof RuntimeException runtimeException) {
            throw runtimeException;
        }
        throw new IllegalStateException("Operation failed after retries", lastError);
    }

    public static void retry(Runnable runnable) {
        retry((Callable<Void>) () -> {
            runnable.run();
            return null;
        });
    }

    public static <T> T retry(Supplier<T> supplier) {
        return retry((Callable<T>) supplier::get);
    }

    private static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
