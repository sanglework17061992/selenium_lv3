package com.sangle.selenium.elements;

import org.openqa.selenium.By;

public final class RadioButtonElement extends BaseElement {

    public RadioButtonElement(By locator, String name) {
        super(locator, name);
    }

    public RadioButtonElement(By locator) {
        super(locator);
    }

    public void select() {
        performAction("select", () -> {
            var element = getClickableElement();
            if (!element.isSelected()) {
                element.click();
            }
        });
    }

    public boolean isSelected() {
        return performResult("is selected", () -> getPresentElement().isSelected());
    }
}
