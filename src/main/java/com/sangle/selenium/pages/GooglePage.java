package com.sangle.selenium.pages;

import com.sangle.selenium.elements.ButtonElement;
import com.sangle.selenium.elements.GenericElement;
import com.sangle.selenium.elements.TextBoxElement;
import com.sangle.selenium.logging.StepLogger;
import com.sangle.selenium.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public final class GooglePage extends BasePage<GooglePage> {

    // Updated locators based on actual Google page structure
    private static final By SEARCH_BOX = By.name("q");
    private static final By SEARCH_BUTTON = By.name("btnK");
    private static final By LUCKY_BUTTON = By.name("btnI");
    private static final By GOOGLE_LOGO = By.xpath("//img[@alt='Google' or contains(@src,'logo')]");
    private static final By SEARCH_RESULTS = By.id("search");
    private static final By RESULT_LINKS = By.xpath("//div[@id='search']//h3");

    private final TextBoxElement searchBox = new TextBoxElement(SEARCH_BOX, "Search Box");
    private final ButtonElement searchButton = new ButtonElement(SEARCH_BUTTON, "Google Search Button");
    private final ButtonElement luckyButton = new ButtonElement(LUCKY_BUTTON, "I'm Feeling Lucky Button");
    private final GenericElement googleLogo = new GenericElement(GOOGLE_LOGO, "Google Logo");

    public GooglePage open() {
        super.open("/");
        return this;
    }

    @Override
    public boolean isAt() {
        StepLogger.info("Verifying Google page is loaded");
        // Just check if search box is present instead of logo for better compatibility
        WaitUtils.waitForVisibility(driver, SEARCH_BOX);
        return searchBox.isDisplayed();
    }

    @Override
    protected GooglePage self() {
        return this;
    }

    public GooglePage searchFor(String searchTerm) {
        StepLogger.info("Searching for: " + searchTerm);
        searchBox.type(searchTerm);
        searchBox.pressKey(Keys.ENTER);
        return this;
    }

    public GooglePage clickSearchButton(String searchTerm) {
        StepLogger.info("Entering search term and clicking search button: " + searchTerm);
        searchBox.type(searchTerm);
        searchButton.safeClick();
        return this;
    }

    public GooglePage clickLuckyButton(String searchTerm) {
        StepLogger.info("Entering search term and clicking I'm Feeling Lucky: " + searchTerm);
        searchBox.type(searchTerm);
        luckyButton.safeClick();
        return this;
    }

    public boolean hasSearchResults() {
        StepLogger.info("Checking if search results are displayed");
        WaitUtils.waitForVisibility(driver, SEARCH_RESULTS);
        return !driver.findElements(SEARCH_RESULTS).isEmpty();
    }

    public int getSearchResultsCount() {
        StepLogger.info("Counting search result links");
        WaitUtils.waitForVisibility(driver, RESULT_LINKS);
        return driver.findElements(RESULT_LINKS).size();
    }

    public boolean isSearchBoxVisible() {
        StepLogger.info("Checking if search box is visible");
        return searchBox.isDisplayed();
    }

    public String getSearchBoxPlaceholder() {
        StepLogger.info("Getting search box placeholder text");
        return searchBox.getAttribute("placeholder");
    }
}