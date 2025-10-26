package com.sangle.selenium.elements;

import com.sangle.selenium.logging.StepLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class ButtonElement extends BaseElement {

    public ButtonElement(By locator, String name) {
        super(locator, name);
    }

    public ButtonElement(By locator) {
        super(locator);
    }

    public void click() {
        performAction("click", () -> getClickableElement().click());
    }

    public void jsClick() {
        performAction("javascript click", () -> javaScript().executeScript("arguments[0].click();", getPresentElement()));
    }

    public void safeClick() {
        performAction("safe click", () -> {
            try {
                WebElement element = getClickableElement();
                scrollIntoView(element);
                element.click();
            } catch (RuntimeException exception) {
                StepLogger.warn(getName() + " default click failed, falling back to JS click");
                WebElement element = getPresentElement();
                javaScript().executeScript("arguments[0].click();", element);
            }
        });
    }
}
