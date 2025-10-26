package com.sangle.selenium.constants;

public enum ElementAction {
    // Click actions
    CLICK("click"),
    DOUBLE_CLICK("double click"),
    RIGHT_CLICK("right click (context menu)"),
    JS_CLICK("javascript click"),
    SAFE_CLICK("safe click"),
    SCROLL_TO_AND_CLICK("scroll to element and click"),
    
    // Text input actions
    TYPE_TEXT("type text"),
    SEND_KEYS("send keys"),
    CLEAR_TEXT("clear text"),
    CLEAR_AND_TYPE("clear and type text"),
    APPEND_TEXT("append text"),
    SELECT_ALL("select all text"),
    TYPE_AND_SUBMIT("type and submit"),
    
    // Key press actions
    PRESS_KEY("press key"),
    PRESS_ENTER("press Enter key"),
    PRESS_TAB("press Tab key"),
    PRESS_ESCAPE("press Escape key"),
    
    // Checkbox/Radio actions
    CHECK("check checkbox"),
    UNCHECK("uncheck checkbox"),
    TOGGLE("toggle checkbox state"),
    SELECT_RADIO("select radio button"),
    FORCE_CHECK("force check checkbox via JavaScript"),
    FORCE_UNCHECK("force uncheck checkbox via JavaScript"),
    FORCE_SELECT("force select radio button"),
    SELECT_BY_JS("select radio button via JavaScript"),
    ENSURE_CHECKED("ensure checkbox is checked"),
    ENSURE_UNCHECKED("ensure checkbox is unchecked"),
    
    // Dropdown actions
    SELECT_BY_VALUE("select option by value"),
    SELECT_BY_TEXT("select option by text"),
    SELECT_BY_INDEX("select option by index"),
    GET_SELECTED_TEXT("get selected option text"),
    GET_SELECTED_VALUE("get selected option value"),
    GET_ALL_OPTIONS("get all option texts"),
    DESELECT_ALL("deselect all options"),
    SELECT_MULTIPLE_BY_TEXT("select multiple options by texts"),
    
    // Verification actions
    GET_TEXT("get element text content"),
    GET_VALUE("get value attribute"),
    GET_ATTRIBUTE("get attribute"),
    GET_BUTTON_TEXT("get button text"),
    GET_BUTTON_VALUE("get button value"),
    GET_SELECTED_OPTION_TEXT("get selected option text"),
    GET_SELECTED_OPTION_VALUE("get selected option value"),
    GET_ALL_OPTION_TEXTS("get all option texts"),
    GET_ALL_OPTION_VALUES("get all option values"),
    GET_TAG_NAME("get element tag name"),
    GET_CSS_VALUE("get CSS property"),
    GET_INNER_HTML("get element innerHTML"),
    GET_OUTER_HTML("get element outerHTML"),
    GET_LABEL("get radio button label"),
    GET_OPTION_COUNT("get total option count"),
    
    // State check actions
    CHECK_DISPLAYED("check if element is displayed"),
    CHECK_PRESENT("check if element is present in DOM"),
    CHECK_ENABLED("check if element is enabled"),
    CHECK_SELECTED("check if element is selected"),
    CHECK_CLICKABLE("check if element is clickable"),
    CHECK_CHECKED("check if checkbox is checked"),
    CHECK_RADIO_SELECTED("check if radio button is selected"),
    CHECK_EMPTY("check if text field is empty"),
    CHECK_PLACEHOLDER("check placeholder"),
    CHECK_HAS_CLASS("check if element has CSS class"),
    CHECK_HAS_OPTION("check if dropdown has option"),
    CHECK_HAS_OPTION_VALUE("check if dropdown has option with value"),
    CHECK_MULTIPLE("check if dropdown allows multiple selections"),
    
    // Text verification actions
    VERIFY_TEXT("verify text equals"),
    VERIFY_PLACEHOLDER("verify placeholder equals"),
    
    // Mouse actions
    HOVER("hover over element"),
    HOVER_ELEMENT("hover over element"),
    SCROLL_INTO_VIEW("scroll element into view"),
    
    // Wait actions
    WAIT_VISIBLE("wait until element becomes visible"),
    WAIT_CLICKABLE("wait until element becomes clickable"),
    WAIT_UNTIL_VISIBLE("wait until element becomes visible"),
    WAIT_UNTIL_CLICKABLE("wait until element becomes clickable"),
    CLICK_AND_WAIT("click and wait"),
    
    // Table actions
    GET_ROW_COUNT("get row count"),
    GET_COLUMN_COUNT("get column count"),
    GET_CELL_TEXT("get cell text"),
    TABLE_CONTAINS_TEXT("table contains text");
    
    private final String description;
    
    ElementAction(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Helper methods for parameterized actions
    public String withParameter(String parameter) {
        return description + " '" + parameter + "'";
    }
    
    public String withParameters(String... parameters) {
        if (parameters.length == 0) {
            return description;
        }
        return description + " '" + String.join("', '", parameters) + "'";
    }
    
    public String withValue(Object value) {
        return description + " " + value;
    }
    
    public String withMilliseconds(long milliseconds) {
        return description + " " + milliseconds + "ms";
    }
}