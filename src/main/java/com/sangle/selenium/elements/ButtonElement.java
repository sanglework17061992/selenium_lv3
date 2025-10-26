package com.sangle.selenium.elements;

import com.sangle.selenium.constants.ElementAction;
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
        performAction(ElementAction.CLICK.getDescription(), () -> getClickableElement().click());
    }

    public void jsClick() {
        performAction(ElementAction.JS_CLICK.getDescription(), () -> 
            javaScript().executeScript("arguments[0].click();", getPresentElement()));
    }

    public void safeClick() {
        performAction(ElementAction.SAFE_CLICK.getDescription(), () -> {
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

    public void doubleClick() {
        performAction(ElementAction.DOUBLE_CLICK.getDescription(), () -> {
            WebElement element = getClickableElement();
            actions().doubleClick(element).perform();
        });
    }

    public void rightClick() {
        performAction(ElementAction.RIGHT_CLICK.getDescription(), () -> {
            WebElement element = getClickableElement();
            actions().contextClick(element).perform();
        });
    }

    public boolean isEnabled() {
        return performResult(ElementAction.CHECK_ENABLED.getDescription(), () -> getPresentElement().isEnabled());
    }

    public boolean isClickable() {
        return performResult(ElementAction.CHECK_CLICKABLE.getDescription(), () -> {
            try {
                getClickableElement();
                return true;
            } catch (RuntimeException e) {
                return false;
            }
        });
    }

    public String getButtonText() {
        return performResult(ElementAction.GET_BUTTON_TEXT.getDescription(), () -> getVisibleElement().getText());
    }

    public String getButtonValue() {
        return performResult(ElementAction.GET_BUTTON_VALUE.getDescription(), () -> getPresentElement().getAttribute("value"));
    }

    public void clickAndWait(int milliseconds) {
        performAction(ElementAction.CLICK_AND_WAIT.withMilliseconds(milliseconds), () -> {
            click();
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Wait interrupted", e);
            }
        });
    }

    public void scrollToAndClick() {
        performAction(ElementAction.SCROLL_TO_AND_CLICK.getDescription(), () -> {
            WebElement element = getPresentElement();
            scrollIntoView(element);
            getClickableElement().click();
        });
    }
}
