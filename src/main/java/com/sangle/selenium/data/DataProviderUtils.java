package com.sangle.selenium.data;

import java.util.List;
import java.util.Map;

public final class DataProviderUtils {

    private DataProviderUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Object[][] wrapSingleColumn(List<T> data) {
        return data.stream().map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    public static Object[][] wrapKeyValue(List<Map<String, String>> data) {
        return data.stream().map(map -> new Object[]{map}).toArray(Object[][]::new);
    }
}
