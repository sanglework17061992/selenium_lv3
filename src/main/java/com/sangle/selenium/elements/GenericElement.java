package com.sangle.selenium.elements;

import org.openqa.selenium.By;

public class GenericElement extends BaseElement {

    public GenericElement(By locator, String name) {
        super(locator, name);
    }

    public GenericElement(By locator) {
        super(locator);
    }

    public boolean isDisplayed() {
        return performResult("is displayed", () -> getVisibleElement().isDisplayed());
    }

    public boolean isPresent() {
        return performResult("is present", () -> !getDriver().findElements(getLocator()).isEmpty());
    }

    public String getAttribute(String attribute) {
        return performResult("get attribute '" + attribute + "'", () -> getPresentElement().getAttribute(attribute));
    }

    public String getText() {
        return performResult("get text", () -> getVisibleElement().getText());
    }
}
