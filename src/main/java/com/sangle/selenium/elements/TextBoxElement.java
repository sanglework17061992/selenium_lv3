package com.sangle.selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public final class TextBoxElement extends BaseElement {

    public TextBoxElement(By locator, String name) {
        super(locator, name);
    }

    public TextBoxElement(By locator) {
        super(locator);
    }

    public void type(CharSequence text) {
        performAction("type text", () -> getVisibleElement().sendKeys(text));
    }

    public void clear() {
        performAction("clear text", () -> getVisibleElement().clear());
    }

    public void clearAndType(CharSequence text) {
        performAction("clear and type text", () -> {
            var element = getVisibleElement();
            element.clear();
            element.sendKeys(text);
        });
    }

    public String getText() {
    return performResult("get text", () -> getVisibleElement().getText());
    }

    public boolean verifyText(String expectedText) {
    return performResult("verify text", () -> getVisibleElement().getText().contentEquals(expectedText));
    }

    public String getValue() {
        return performResult("get value", () -> getVisibleElement().getAttribute("value"));
    }

    public void pressKey(CharSequence key) {
        performAction("press key", () -> getVisibleElement().sendKeys(key));
    }
}
