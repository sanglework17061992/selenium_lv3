package com.sangle.selenium.elements;

import org.openqa.selenium.By;

public final class IFrameElement extends BaseElement {

    public IFrameElement(By locator, String name) {
        super(locator, name);
    }

    public IFrameElement(By locator) {
        super(locator);
    }

    public void switchTo() {
        performAction("switch to iframe", () -> getDriver().switchTo().frame(getPresentElement()));
    }

    public void switchBackToDefault() {
        performAction("switch back to default content", () -> getDriver().switchTo().defaultContent());
    }
}
