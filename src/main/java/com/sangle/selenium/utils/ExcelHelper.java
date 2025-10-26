package com.sangle.selenium.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public final class ExcelHelper {

    private ExcelHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Map<String, String>> readSheet(Path path, String sheetName) {
        try (InputStream stream = Files.newInputStream(path); Workbook workbook = WorkbookFactory.create(stream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }
            return toMapList(sheet.iterator());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Excel file: " + path, exception);
        }
    }

    public static List<Map<String, String>> readSheetFromResource(String resource, String sheetName) {
        try (InputStream stream = getResourceAsStream(resource); Workbook workbook = WorkbookFactory.create(stream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }
            return toMapList(sheet.iterator());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Excel resource: " + resource, exception);
        }
    }

    private static List<Map<String, String>> toMapList(Iterator<Row> rowIterator) {
        List<Map<String, String>> rows = new ArrayList<>();
        if (!rowIterator.hasNext()) {
            return rows;
        }
        Row headerRow = rowIterator.next();
        List<String> headers = new ArrayList<>();
        headerRow.forEach(cell -> headers.add(cell.getStringCellValue()));

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Map<String, String> rowMap = new HashMap<>();
            for (int cellIndex = 0; cellIndex < headers.size(); cellIndex++) {
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                rowMap.put(headers.get(cellIndex), cellToString(cell));
            }
            rows.add(rowMap);
        }
        return rows;
    }

    private static String cellToString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC -> {
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getLocalDateTimeCellValue().toString();
                }
                yield Double.toString(cell.getNumericCellValue());
            }
            case FORMULA -> cell.getCellFormula();
            case BLANK, _NONE, ERROR -> "";
        };
    }

    private static InputStream getResourceAsStream(String resource) throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            throw new IOException("Resource not found: " + resource);
        }
        return stream;
    }
}
