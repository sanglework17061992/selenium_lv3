package com.sangle.selenium.utils;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CsvHelper {

    private CsvHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String[]> readAll(Path path) {
        try (Reader reader = Files.newBufferedReader(path); CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read CSV file: " + path, exception);
        }
    }

    public static List<Map<String, String>> readAsMaps(Path path) {
        try (Reader reader = Files.newBufferedReader(path); CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> rows = csvReader.readAll();
            if (rows.isEmpty()) {
                return List.of();
            }
            String[] headers = rows.get(0);
            return rows.stream().skip(1).map(row -> mapRow(headers, row)).toList();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read CSV file: " + path, exception);
        }
    }

    public static <T> List<T> readBeans(Path path, Class<T> type, String... columns) {
        try (Reader reader = Files.newBufferedReader(path)) {
            ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(type);
            strategy.setColumnMapping(columns);
            return new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read CSV file: " + path, exception);
        }
    }

    public static List<Map<String, String>> readAsMapsFromResource(String resource) {
        try (Reader reader = new InputStreamReader(getResourceAsStream(resource)); CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> rows = csvReader.readAll();
            if (rows.isEmpty()) {
                return List.of();
            }
            String[] headers = rows.get(0);
            return rows.stream().skip(1).map(row -> mapRow(headers, row)).toList();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read CSV resource: " + resource, exception);
        }
    }

    private static Map<String, String> mapRow(String[] headers, String[] row) {
        Map<String, String> mapped = new HashMap<>();
        for (int index = 0; index < headers.length && index < row.length; index++) {
            mapped.put(headers[index], row[index]);
        }
        return mapped;
    }

    private static java.io.InputStream getResourceAsStream(String resource) throws IOException {
        java.io.InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            throw new IOException("Resource not found: " + resource);
        }
        return stream;
    }
}
