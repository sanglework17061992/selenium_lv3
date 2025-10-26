package com.sangle.selenium.constants;

public enum EnvironmentType {
    DEV,
    QA,
    UAT,
    STAGING,
    PROD;

    public static EnvironmentType from(String value) {
        for (EnvironmentType environment : values()) {
            if (environment.name().equalsIgnoreCase(value)) {
                return environment;
            }
        }
        throw new IllegalArgumentException("Unsupported environment type: " + value);
    }
}
