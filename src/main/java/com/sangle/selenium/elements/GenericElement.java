package com.sangle.selenium.elements;

import com.sangle.selenium.constants.ElementAction;
import org.openqa.selenium.By;

public class GenericElement extends BaseElement {

    public GenericElement(By locator, String name) {
        super(locator, name);
    }

    public GenericElement(By locator) {
        super(locator);
    }

    @Override
    public boolean isDisplayed() {
        return performResult(ElementAction.CHECK_DISPLAYED.getDescription(), () -> getVisibleElement().isDisplayed());
    }

    public boolean isPresent() {
        return performResult(ElementAction.CHECK_PRESENT.getDescription(), () -> 
            !getDriver().findElements(getLocator()).isEmpty());
    }

    @Override
    public String getAttribute(String attribute) {
        return performResult(ElementAction.GET_ATTRIBUTE.withParameter(attribute), () -> 
            getPresentElement().getAttribute(attribute));
    }

    public String getText() {
        return performResult(ElementAction.GET_TEXT.getDescription(), () -> getVisibleElement().getText());
    }

    public String getTagName() {
        return performResult(ElementAction.GET_TAG_NAME.getDescription(), () -> getPresentElement().getTagName());
    }

    public String getCssValue(String propertyName) {
        return performResult(ElementAction.GET_CSS_VALUE.withParameter(propertyName), () -> 
            getVisibleElement().getCssValue(propertyName));
    }

    public boolean isEnabled() {
        return performResult(ElementAction.CHECK_ENABLED.getDescription(), () -> getPresentElement().isEnabled());
    }

    public boolean isSelected() {
        return performResult(ElementAction.CHECK_SELECTED.getDescription(), () -> getPresentElement().isSelected());
    }

    public void click() {
        performAction(ElementAction.CLICK.getDescription(), () -> getClickableElement().click());
    }

    public void scrollIntoView() {
        performAction(ElementAction.SCROLL_INTO_VIEW.getDescription(), () -> {
            var element = getPresentElement();
            scrollIntoView(element);
        });
    }

    public void hover() {
        performAction(ElementAction.HOVER_ELEMENT.getDescription(), () -> {
            var element = getVisibleElement();
            actions().moveToElement(element).perform();
        });
    }

    public void rightClick() {
        performAction(ElementAction.RIGHT_CLICK.getDescription(), () -> {
            var element = getClickableElement();
            actions().contextClick(element).perform();
        });
    }

    public void doubleClick() {
        performAction(ElementAction.DOUBLE_CLICK.getDescription(), () -> {
            var element = getClickableElement();
            actions().doubleClick(element).perform();
        });
    }

    public void waitUntilVisible() {
        performAction(ElementAction.WAIT_UNTIL_VISIBLE.getDescription(), () -> getVisibleElement());
    }

    public void waitUntilClickable() {
        performAction(ElementAction.WAIT_UNTIL_CLICKABLE.getDescription(), () -> getClickableElement());
    }

    public boolean hasClass(String className) {
        return performResult(ElementAction.CHECK_HAS_CLASS.withParameter(className), () -> {
            String classAttribute = getPresentElement().getAttribute("class");
            return classAttribute != null && classAttribute.contains(className);
        });
    }

    public String getInnerHTML() {
        return performResult(ElementAction.GET_INNER_HTML.getDescription(), () -> 
            getPresentElement().getAttribute("innerHTML"));
    }

    public String getOuterHTML() {
        return performResult(ElementAction.GET_OUTER_HTML.getDescription(), () -> 
            getPresentElement().getAttribute("outerHTML"));
    }
}
