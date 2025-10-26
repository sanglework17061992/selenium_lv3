package com.sangle.selenium.pages;

import com.sangle.selenium.elements.ButtonElement;
import com.sangle.selenium.elements.GenericElement;
import com.sangle.selenium.logging.StepLogger;
import com.sangle.selenium.utils.WaitUtils;
import org.openqa.selenium.By;

public final class HomePage extends BasePage<HomePage> {

    // Main page elements
    private static final By HEADER_LOGO = By.cssSelector("header img[src='/images/Toolsqa.jpg']");
    private static final By CATEGORY_CARDS = By.cssSelector(".category-cards .card");
    
    // Category card selectors
    private static final By ELEMENTS_CARD = By.xpath("//div[@class='card-body']//h5[text()='Elements']");
    private static final By FORMS_CARD = By.xpath("//div[@class='card-body']//h5[text()='Forms']");
    private static final By ALERTS_CARD = By.xpath("//div[@class='card-body']//h5[text()='Alerts, Frame & Windows']");
    private static final By WIDGETS_CARD = By.xpath("//div[@class='card-body']//h5[text()='Widgets']");
    private static final By INTERACTIONS_CARD = By.xpath("//div[@class='card-body']//h5[text()='Interactions']");
    private static final By BOOKSTORE_CARD = By.xpath("//div[@class='card-body']//h5[text()='Book Store Application']");

    // Elements
    private final GenericElement headerLogo = new GenericElement(HEADER_LOGO, "DemoQA Header Logo");
    private final ButtonElement elementsCard = new ButtonElement(ELEMENTS_CARD, "Elements Card");
    private final ButtonElement formsCard = new ButtonElement(FORMS_CARD, "Forms Card");
    private final ButtonElement alertsCard = new ButtonElement(ALERTS_CARD, "Alerts, Frame & Windows Card");
    private final ButtonElement widgetsCard = new ButtonElement(WIDGETS_CARD, "Widgets Card");
    private final ButtonElement interactionsCard = new ButtonElement(INTERACTIONS_CARD, "Interactions Card");
    private final ButtonElement bookstoreCard = new ButtonElement(BOOKSTORE_CARD, "Book Store Application Card");

    public HomePage open() {
        super.open("/");
        return this;
    }

    @Override
    public boolean isAt() {
        StepLogger.info("Verifying DemoQA Home page is loaded");
        WaitUtils.waitForVisibility(driver, HEADER_LOGO);
        return headerLogo.isDisplayed();
    }

    @Override
    protected HomePage self() {
        return this;
    }

    public boolean isLogoDisplayed() {
        StepLogger.info("Checking if DemoQA logo is displayed");
        return headerLogo.isDisplayed();
    }

    public int getCategoryCardsCount() {
        StepLogger.info("Getting count of category cards");
        WaitUtils.waitForVisibility(driver, CATEGORY_CARDS);
        return driver.findElements(CATEGORY_CARDS).size();
    }

    public boolean isElementsCardDisplayed() {
        return elementsCard.isDisplayed();
    }

    public boolean isFormsCardDisplayed() {
        return formsCard.isDisplayed();
    }

    public boolean isAlertsCardDisplayed() {
        return alertsCard.isDisplayed();
    }

    public boolean isWidgetsCardDisplayed() {
        return widgetsCard.isDisplayed();
    }

    public boolean isInteractionsCardDisplayed() {
        return interactionsCard.isDisplayed();
    }

    public boolean isBookStoreCardDisplayed() {
        return bookstoreCard.isDisplayed();
    }

    public void clickElementsCard() {
        StepLogger.info("Clicking on Elements card");
        elementsCard.safeClick();
    }

    public void clickFormsCard() {
        StepLogger.info("Clicking on Forms card");
        formsCard.safeClick();
    }

    public void clickAlertsCard() {
        StepLogger.info("Clicking on Alerts, Frame & Windows card");
        alertsCard.safeClick();
    }

    public void clickWidgetsCard() {
        StepLogger.info("Clicking on Widgets card");
        widgetsCard.safeClick();
    }

    public void clickInteractionsCard() {
        StepLogger.info("Clicking on Interactions card");
        interactionsCard.safeClick();
    }

    public void clickBookStoreCard() {
        StepLogger.info("Clicking on Book Store Application card");
        bookstoreCard.safeClick();
    }
}