package com.sangle.selenium.logging;

import io.qameta.allure.Allure;
import java.util.Optional;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StepLogger {

    private static final Logger LOG = LoggerFactory.getLogger("FrameworkActions");
    private static final int DETAIL_MAX_LENGTH = 200;

    private StepLogger() {
        throw new IllegalStateException("Utility class");
    }

    public static void actionStart(String elementName, String action, By locator) {
        logAction("START", elementName, action, locator, null, null);
    }

    public static void actionSuccess(String elementName, String action, By locator, Object detail) {
        logAction("SUCCESS", elementName, action, locator, detail, null);
    }

    public static void actionFailure(String elementName, String action, By locator, Throwable throwable) {
        logAction("FAILURE", elementName, action, locator,
                Optional.ofNullable(throwable).map(Throwable::getMessage).orElse(null), throwable);
    }

    public static void info(String message) {
        LOG.info(message);
        safeAllureStep(message);
    }

    public static void warn(String message) {
        LOG.warn(message);
        safeAllureStep("WARN: " + message);
    }

    public static void error(String message, Throwable throwable) {
        LOG.error(message, throwable);
        safeAllureStep("ERROR: " + message);
    }

    private static void logAction(String stage, String elementName, String action, By locator, Object detail,
                                  Throwable throwable) {
        String message = buildActionMessage(stage, elementName, action, locator, detail);
        if (throwable == null) {
            LOG.info(message);
        } else {
            LOG.error(message, throwable);
        }
        safeAllureStep(message);
    }

    private static String buildActionMessage(String stage, String elementName, String action, By locator, Object detail) {
        String locatorValue = locator == null ? "N/A" : locator.toString();
        String detailValue = Optional.ofNullable(detail)
                .map(StepLogger::truncateDetail)
                .map(text -> " | detail=" + text)
                .orElse("");
        return String.format("[%s][%s] %s -> %s | locator=%s%s",
                stage,
                Thread.currentThread().getName(),
                elementName,
                action,
                locatorValue,
                detailValue);
    }

    private static String truncateDetail(Object detail) {
        String value = String.valueOf(detail);
        if (value.length() <= DETAIL_MAX_LENGTH) {
            return value;
        }
        return value.substring(0, DETAIL_MAX_LENGTH) + "...";
    }

    private static void safeAllureStep(String message) {
        try {
            Allure.step(message);
        } catch (IllegalStateException ignored) {
            // No active Allure lifecycle; skip attaching step.
        }
    }
}
