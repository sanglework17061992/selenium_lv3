package com.sangle.selenium.tests;

import com.sangle.selenium.pages.GooglePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Google Search")
@Feature("Basic Google Functionality")
public final class GoogleTest extends BaseTest {

    @Test(groups = {"SMOKE", "REGRESSION"})
    @Story("Page Load")
    @Description("Verify Google homepage loads correctly")
    public void shouldLoadGoogleHomepage() {
        GooglePage googlePage = new GooglePage()
                .open();

        Assert.assertTrue(googlePage.isAt(), "Google homepage should be loaded");
        Assert.assertTrue(googlePage.isSearchBoxVisible(), "Search box should be visible");
    }

    @Test(groups = {"SMOKE", "REGRESSION"})
    @Story("Search Functionality")
    @Description("Verify basic search functionality using Enter key")
    public void shouldPerformBasicSearch() {
        GooglePage googlePage = new GooglePage()
                .open()
                .searchFor("Selenium WebDriver");

        Assert.assertTrue(googlePage.hasSearchResults(), "Search results should be displayed");
        Assert.assertTrue(googlePage.getSearchResultsCount() > 0, "Should have at least one search result");
    }

    @Test(groups = {"REGRESSION"})
    @Story("Search Functionality")
    @Description("Verify search functionality using search button")
    public void shouldSearchUsingSearchButton() {
        GooglePage googlePage = new GooglePage()
                .open()
                .clickSearchButton("Java programming");

        Assert.assertTrue(googlePage.hasSearchResults(), "Search results should be displayed");
        Assert.assertTrue(googlePage.getSearchResultsCount() > 0, "Should have at least one search result");
    }

    @Test(groups = {"SMOKE"})
    @Story("Page Elements")
    @Description("Verify key page elements are present")
    public void shouldDisplayKeyElements() {
        GooglePage googlePage = new GooglePage()
                .open();

        Assert.assertTrue(googlePage.isAt(), "Google homepage should be loaded");
        Assert.assertTrue(googlePage.isSearchBoxVisible(), "Search box should be visible");
        
        // Verify we can interact with the search box
        String placeholder = googlePage.getSearchBoxPlaceholder();
        // Google's search box may or may not have a placeholder, so we just verify it returns a string
        Assert.assertNotNull(placeholder, "Search box placeholder should not be null");
    }
}