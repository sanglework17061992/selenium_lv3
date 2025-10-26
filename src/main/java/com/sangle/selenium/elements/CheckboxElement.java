package com.sangle.selenium.elements;

import com.sangle.selenium.constants.ElementAction;
import org.openqa.selenium.By;

public final class CheckboxElement extends BaseElement {

    public CheckboxElement(By locator, String name) {
        super(locator, name);
    }

    public CheckboxElement(By locator) {
        super(locator);
    }

    public void check() {
        performAction(ElementAction.CHECK.getDescription(), () -> {
            var element = getClickableElement();
            if (!element.isSelected()) {
                element.click();
            }
        });
    }

    public void uncheck() {
        performAction(ElementAction.UNCHECK.getDescription(), () -> {
            var element = getClickableElement();
            if (element.isSelected()) {
                element.click();
            }
        });
    }

    public void toggle() {
        performAction(ElementAction.TOGGLE.getDescription(), () -> getClickableElement().click());
    }

    public boolean isChecked() {
        return performResult(ElementAction.CHECK_CHECKED.getDescription(), () -> getPresentElement().isSelected());
    }

    public void setState(boolean shouldBeChecked) {
        if (shouldBeChecked) {
            performAction(ElementAction.ENSURE_CHECKED.getDescription(), () -> {
                var element = getClickableElement();
                if (!element.isSelected()) {
                    element.click();
                }
            });
        } else {
            performAction(ElementAction.ENSURE_UNCHECKED.getDescription(), () -> {
                var element = getClickableElement();
                if (element.isSelected()) {
                    element.click();
                }
            });
        }
    }

    public boolean isEnabled() {
        return performResult(ElementAction.CHECK_ENABLED.getDescription(), () -> getPresentElement().isEnabled());
    }

    public void forceCheck() {
        performAction(ElementAction.FORCE_CHECK.getDescription(), () -> {
            if (!isChecked()) {
                javaScript().executeScript("arguments[0].checked = true; arguments[0].dispatchEvent(new Event('change'));", 
                    getPresentElement());
            }
        });
    }

    public void forceUncheck() {
        performAction(ElementAction.FORCE_UNCHECK.getDescription(), () -> {
            if (isChecked()) {
                javaScript().executeScript("arguments[0].checked = false; arguments[0].dispatchEvent(new Event('change'));", 
                    getPresentElement());
            }
        });
    }
}
