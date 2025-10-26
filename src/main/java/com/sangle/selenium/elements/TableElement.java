package com.sangle.selenium.elements;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TableElement extends BaseElement {

    private static final By ROW_LOCATOR = By.cssSelector("tr");
    private static final By CELL_LOCATOR = By.cssSelector("td,th");

    public TableElement(By locator, String name) {
        super(locator, name);
    }

    public TableElement(By locator) {
        super(locator);
    }

    public int getRowCount() {
        return performResult("get row count", () -> rows().size());
    }

    public int getColumnCount() {
        return performResult("get column count", () -> {
            List<WebElement> currentRows = rows();
            if (currentRows.isEmpty()) {
                return 0;
            }
            return cells(currentRows.get(0)).size();
        });
    }

    public String getCellText(int rowIndex, int columnIndex) {
        return performResult("get cell text", () -> {
            List<WebElement> currentRows = rows();
            if (rowIndex < 0 || rowIndex >= currentRows.size()) {
                throw new IllegalArgumentException("Row index out of bounds: " + rowIndex);
            }
            List<WebElement> currentCells = cells(currentRows.get(rowIndex));
            if (columnIndex < 0 || columnIndex >= currentCells.size()) {
                throw new IllegalArgumentException("Column index out of bounds: " + columnIndex);
            }
            return currentCells.get(columnIndex).getText();
        });
    }

    public boolean containsText(String text) {
        return performResult("table contains text", () -> rows().stream()
                .flatMap(row -> cells(row).stream())
                .map(WebElement::getText)
                .anyMatch(text::equalsIgnoreCase));
    }

    public java.util.List<java.util.List<String>> asTextMatrix() {
        return performResult("get table data", () -> rows().stream()
                .map(row -> cells(row).stream().map(WebElement::getText).toList())
                .toList());
    }

    public String findCellValue(String searchValue, int searchColumnIndex, int resultColumnIndex) {
        return performResult("find cell value", () -> {
            for (WebElement row : rows()) {
                java.util.List<WebElement> currentCells = cells(row);
                if (searchColumnIndex >= currentCells.size()) {
                    continue;
                }
                if (currentCells.get(searchColumnIndex).getText().equalsIgnoreCase(searchValue)) {
                    if (resultColumnIndex >= currentCells.size()) {
                        throw new IllegalArgumentException("Result column index out of bounds: " + resultColumnIndex);
                    }
                    return currentCells.get(resultColumnIndex).getText();
                }
            }
            throw new IllegalArgumentException("No cell found matching value: " + searchValue);
        });
    }

    private List<WebElement> rows() {
        return getPresentElement().findElements(ROW_LOCATOR);
    }

    private List<WebElement> cells(WebElement row) {
        return row.findElements(CELL_LOCATOR);
    }
}
