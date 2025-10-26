package com.sangle.selenium.elements;

import org.openqa.selenium.By;

public final class CheckboxElement extends BaseElement {

    public CheckboxElement(By locator, String name) {
        super(locator, name);
    }

    public CheckboxElement(By locator) {
        super(locator);
    }

    public void check() {
        performAction("check", () -> {
            var element = getClickableElement();
            if (!element.isSelected()) {
                element.click();
            }
        });
    }

    public void uncheck() {
        performAction("uncheck", () -> {
            var element = getClickableElement();
            if (element.isSelected()) {
                element.click();
            }
        });
    }

    public void toggle() {
        performAction("toggle", () -> getClickableElement().click());
    }

    public boolean isChecked() {
        return performResult("is checked", () -> getPresentElement().isSelected());
    }
}
