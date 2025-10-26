package com.sangle.selenium.elements;

import com.sangle.selenium.constants.ElementAction;
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
        performAction(ElementAction.TYPE_TEXT.withValue(text.toString()), () -> getVisibleElement().sendKeys(text));
    }

    public void clear() {
        performAction(ElementAction.CLEAR_TEXT.getDescription(), () -> getVisibleElement().clear());
    }

    public void clearAndType(CharSequence text) {
        performAction(ElementAction.CLEAR_AND_TYPE.withValue(text.toString()), () -> {
            var element = getVisibleElement();
            element.clear();
            element.sendKeys(text);
        });
    }

    public String getText() {
        return performResult(ElementAction.GET_TEXT.getDescription(), () -> getVisibleElement().getText());
    }

    public boolean verifyText(String expectedText) {
        return performResult(ElementAction.VERIFY_TEXT.withValue(expectedText), () -> 
            getVisibleElement().getText().contentEquals(expectedText));
    }

    public String getValue() {
        return performResult(ElementAction.GET_VALUE.getDescription(), () -> getVisibleElement().getAttribute("value"));
    }

    public void pressKey(CharSequence key) {
        performAction(ElementAction.PRESS_KEY.withValue(key.toString()), () -> getVisibleElement().sendKeys(key));
    }

    public void pressEnter() {
        performAction(ElementAction.PRESS_ENTER.getDescription(), () -> getVisibleElement().sendKeys(Keys.ENTER));
    }

    public void pressTab() {
        performAction(ElementAction.PRESS_TAB.getDescription(), () -> getVisibleElement().sendKeys(Keys.TAB));
    }

    public void pressEscape() {
        performAction(ElementAction.PRESS_ESCAPE.getDescription(), () -> getVisibleElement().sendKeys(Keys.ESCAPE));
    }

    public boolean isEmpty() {
        return performResult(ElementAction.CHECK_EMPTY.getDescription(), () -> {
            String value = getVisibleElement().getAttribute("value");
            return value == null || value.trim().isEmpty();
        });
    }

    public void selectAll() {
        performAction(ElementAction.SELECT_ALL.getDescription(), () -> getVisibleElement().sendKeys(Keys.chord(Keys.CONTROL, "a")));
    }

    public void appendText(CharSequence text) {
        performAction(ElementAction.APPEND_TEXT.withValue(text.toString()), () -> {
            var element = getVisibleElement();
            element.sendKeys(Keys.END);
            element.sendKeys(text);
        });
    }

    public boolean hasPlaceholder(String expectedPlaceholder) {
        return performResult(ElementAction.CHECK_PLACEHOLDER.withValue(expectedPlaceholder), () -> {
            String placeholder = getVisibleElement().getAttribute("placeholder");
            return placeholder != null && placeholder.equals(expectedPlaceholder);
        });
    }
}
