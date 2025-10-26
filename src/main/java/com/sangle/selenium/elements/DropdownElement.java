package com.sangle.selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public final class DropdownElement extends BaseElement {

    public DropdownElement(By locator, String name) {
        super(locator, name);
    }

    public DropdownElement(By locator) {
        super(locator);
    }

    public void selectByValue(String value) {
        performAction("select by value '" + value + "'", () -> new Select(getVisibleElement()).selectByValue(value));
    }

    public void selectByVisibleText(String text) {
        performAction("select by text '" + text + "'", () -> new Select(getVisibleElement()).selectByVisibleText(text));
    }

    public void selectByIndex(int index) {
        performAction("select by index " + index, () -> new Select(getVisibleElement()).selectByIndex(index));
    }

    public String getSelectedOptionText() {
        return performResult("read selected dropdown option", () -> new Select(getVisibleElement()).getFirstSelectedOption().getText());
    }
}
