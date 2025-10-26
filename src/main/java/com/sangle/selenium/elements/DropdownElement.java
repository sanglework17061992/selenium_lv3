package com.sangle.selenium.elements;

import com.sangle.selenium.constants.ElementAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public final class DropdownElement extends BaseElement {

    public DropdownElement(By locator, String name) {
        super(locator, name);
    }

    public DropdownElement(By locator) {
        super(locator);
    }

    public void selectByValue(String value) {
        performAction(ElementAction.SELECT_BY_VALUE.withValue(value), () -> 
            new Select(getVisibleElement()).selectByValue(value));
    }

    public void selectByVisibleText(String text) {
        performAction(ElementAction.SELECT_BY_TEXT.withValue(text), () -> 
            new Select(getVisibleElement()).selectByVisibleText(text));
    }

    public void selectByIndex(int index) {
        performAction(ElementAction.SELECT_BY_INDEX.withParameter(String.valueOf(index)), () -> 
            new Select(getVisibleElement()).selectByIndex(index));
    }

    public String getSelectedOptionText() {
        return performResult(ElementAction.GET_SELECTED_TEXT.getDescription(), () -> 
            new Select(getVisibleElement()).getFirstSelectedOption().getText());
    }

    public String getSelectedOptionValue() {
        return performResult(ElementAction.GET_SELECTED_VALUE.getDescription(), () -> 
            new Select(getVisibleElement()).getFirstSelectedOption().getAttribute("value"));
    }

    public List<String> getAllOptionTexts() {
        return performResult(ElementAction.GET_ALL_OPTIONS.getDescription(), () -> 
            new Select(getVisibleElement()).getOptions().stream()
                .map(WebElement::getText)
                .toList());
    }

    public List<String> getAllOptionValues() {
        return performResult(ElementAction.GET_ALL_OPTION_VALUES.getDescription(), () -> 
            new Select(getVisibleElement()).getOptions().stream()
                .map(option -> option.getAttribute("value"))
                .toList());
    }

    public boolean isMultiple() {
        return performResult(ElementAction.CHECK_MULTIPLE.getDescription(), () -> 
            new Select(getVisibleElement()).isMultiple());
    }

    public boolean hasOption(String optionText) {
        return performResult(ElementAction.CHECK_HAS_OPTION.withValue(optionText), () -> 
            getAllOptionTexts().contains(optionText));
    }

    public boolean hasOptionWithValue(String value) {
        return performResult(ElementAction.CHECK_HAS_OPTION_VALUE.withValue(value), () -> 
            getAllOptionValues().contains(value));
    }

    public int getOptionCount() {
        return performResult(ElementAction.GET_OPTION_COUNT.getDescription(), () -> 
            new Select(getVisibleElement()).getOptions().size());
    }

    public void deselectAll() {
        performAction(ElementAction.DESELECT_ALL.getDescription(), () -> {
            Select select = new Select(getVisibleElement());
            if (select.isMultiple()) {
                select.deselectAll();
            } else {
                throw new UnsupportedOperationException("Cannot deselect from single-select dropdown");
            }
        });
    }

    public void selectMultipleByTexts(String... texts) {
        performAction(ElementAction.SELECT_MULTIPLE_BY_TEXT.withValue(String.join(", ", texts)), () -> {
            Select select = new Select(getVisibleElement());
            if (!select.isMultiple()) {
                throw new UnsupportedOperationException("Cannot select multiple options from single-select dropdown");
            }
            for (String text : texts) {
                select.selectByVisibleText(text);
            }
        });
    }
}
