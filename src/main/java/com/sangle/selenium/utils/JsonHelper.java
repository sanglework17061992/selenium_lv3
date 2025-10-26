package com.sangle.selenium.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class JsonHelper {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private JsonHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T readObjectFromFile(Path path, Class<T> clazz) {
        try {
            return MAPPER.readValue(Files.newBufferedReader(path), clazz);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read JSON file: " + path, exception);
        }
    }

    public static <T> List<T> readListFromFile(Path path, Class<T> clazz) {
        try {
            return MAPPER.readValue(Files.newBufferedReader(path), MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read JSON file: " + path, exception);
        }
    }

    public static <T> T readObjectFromClasspath(String resourcePath, Class<T> clazz) {
        try (InputStream stream = getResourceAsStream(resourcePath)) {
            return MAPPER.readValue(stream, clazz);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read JSON resource: " + resourcePath, exception);
        }
    }

    public static <T> List<T> readListFromClasspath(String resourcePath, Class<T> clazz) {
        try (InputStream stream = getResourceAsStream(resourcePath)) {
            return MAPPER.readValue(stream, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read JSON resource: " + resourcePath, exception);
        }
    }

    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to convert object to JSON", exception);
        }
    }

    public static void writeObjectToFile(Path path, Object object) {
        try {
            Files.createDirectories(path.getParent());
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), object);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write JSON file: " + path, exception);
        }
    }

    private static InputStream getResourceAsStream(String resourcePath) throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return stream;
    }
}
