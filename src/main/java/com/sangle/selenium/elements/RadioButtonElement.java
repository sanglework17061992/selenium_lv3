package com.sangle.selenium.elements;

import com.sangle.selenium.constants.ElementAction;
import org.openqa.selenium.By;

public final class RadioButtonElement extends BaseElement {

    public RadioButtonElement(By locator, String name) {
        super(locator, name);
    }

    public RadioButtonElement(By locator) {
        super(locator);
    }

    public void select() {
        performAction(ElementAction.SELECT_RADIO.getDescription(), () -> {
            var element = getClickableElement();
            if (!element.isSelected()) {
                element.click();
            }
        });
    }

    public boolean isSelected() {
        return performResult(ElementAction.CHECK_RADIO_SELECTED.getDescription(), () -> getPresentElement().isSelected());
    }

    public void forceSelect() {
        performAction(ElementAction.FORCE_SELECT.getDescription(), () -> getClickableElement().click());
    }

    public boolean isEnabled() {
        return performResult(ElementAction.CHECK_ENABLED.getDescription(), () -> getPresentElement().isEnabled());
    }

    public String getValue() {
        return performResult(ElementAction.GET_VALUE.getDescription(), () -> getPresentElement().getAttribute("value"));
    }

    public String getLabel() {
        return performResult(ElementAction.GET_LABEL.getDescription(), () -> {
            // Try to find associated label
            String id = getPresentElement().getAttribute("id");
            if (id != null && !id.isEmpty()) {
                try {
                    var label = getDriver().findElement(By.xpath("//label[@for='" + id + "']"));
                    return label.getText();
                } catch (Exception e) {
                    // Fall back to parent text or element text
                    return getPresentElement().getText();
                }
            }
            return getPresentElement().getText();
        });
    }

    public void selectByJavaScript() {
        performAction(ElementAction.SELECT_BY_JS.getDescription(), () -> 
            javaScript().executeScript("arguments[0].checked = true; arguments[0].dispatchEvent(new Event('change'));", 
                getPresentElement()));
    }
}
