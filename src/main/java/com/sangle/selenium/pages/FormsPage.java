package com.sangle.selenium.pages;

import com.sangle.selenium.elements.ButtonElement;
import com.sangle.selenium.elements.GenericElement;
import com.sangle.selenium.elements.TextBoxElement;
import com.sangle.selenium.elements.CheckboxElement;
import com.sangle.selenium.elements.DropdownElement;
import com.sangle.selenium.logging.StepLogger;
import com.sangle.selenium.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public final class FormsPage extends BasePage<FormsPage> {

    // Practice Form elements
    private static final By PRACTICE_FORM_HEADER = By.xpath("//h1[text()='Practice Form']");
    private static final By FIRST_NAME_INPUT = By.id("firstName");
    private static final By LAST_NAME_INPUT = By.id("lastName");
    private static final By EMAIL_INPUT = By.id("userEmail");
    private static final By PHONE_INPUT = By.id("userNumber");
    private static final By MALE_GENDER_RADIO = By.xpath("//label[@for='gender-radio-1']");
    private static final By FEMALE_GENDER_RADIO = By.xpath("//label[@for='gender-radio-2']");
    private static final By OTHER_GENDER_RADIO = By.xpath("//label[@for='gender-radio-3']");
    private static final By DATE_OF_BIRTH_INPUT = By.id("dateOfBirthInput");
    private static final By SUBJECTS_INPUT = By.id("subjectsInput");
    private static final By SPORTS_HOBBY_CHECKBOX = By.xpath("//label[@for='hobbies-checkbox-1']");
    private static final By READING_HOBBY_CHECKBOX = By.xpath("//label[@for='hobbies-checkbox-2']");
    private static final By MUSIC_HOBBY_CHECKBOX = By.xpath("//label[@for='hobbies-checkbox-3']");
    private static final By CURRENT_ADDRESS_TEXTAREA = By.id("currentAddress");
    private static final By SUBMIT_BUTTON = By.id("submit");

    // Navigation elements
    private static final By PRACTICE_FORM_MENU = By.xpath("//span[text()='Practice Form']");

    // Elements
    private final GenericElement formHeader = new GenericElement(PRACTICE_FORM_HEADER, "Practice Form Header");
    private final TextBoxElement firstNameInput = new TextBoxElement(FIRST_NAME_INPUT, "First Name Input");
    private final TextBoxElement lastNameInput = new TextBoxElement(LAST_NAME_INPUT, "Last Name Input");
    private final TextBoxElement emailInput = new TextBoxElement(EMAIL_INPUT, "Email Input");
    private final TextBoxElement phoneInput = new TextBoxElement(PHONE_INPUT, "Phone Input");
    private final ButtonElement maleGenderRadio = new ButtonElement(MALE_GENDER_RADIO, "Male Gender Radio");
    private final ButtonElement femaleGenderRadio = new ButtonElement(FEMALE_GENDER_RADIO, "Female Gender Radio");
    private final ButtonElement otherGenderRadio = new ButtonElement(OTHER_GENDER_RADIO, "Other Gender Radio");
    private final TextBoxElement dateOfBirthInput = new TextBoxElement(DATE_OF_BIRTH_INPUT, "Date of Birth Input");
    private final TextBoxElement subjectsInput = new TextBoxElement(SUBJECTS_INPUT, "Subjects Input");
    private final CheckboxElement sportsHobbyCheckbox = new CheckboxElement(SPORTS_HOBBY_CHECKBOX, "Sports Hobby Checkbox");
    private final CheckboxElement readingHobbyCheckbox = new CheckboxElement(READING_HOBBY_CHECKBOX, "Reading Hobby Checkbox");
    private final CheckboxElement musicHobbyCheckbox = new CheckboxElement(MUSIC_HOBBY_CHECKBOX, "Music Hobby Checkbox");
    private final TextBoxElement currentAddressTextarea = new TextBoxElement(CURRENT_ADDRESS_TEXTAREA, "Current Address Textarea");
    private final ButtonElement submitButton = new ButtonElement(SUBMIT_BUTTON, "Submit Button");
    private final ButtonElement practiceFormMenu = new ButtonElement(PRACTICE_FORM_MENU, "Practice Form Menu");

    public FormsPage open() {
        super.open("/forms");
        return this;
    }

    public FormsPage openPracticeForm() {
        StepLogger.info("Clicking on Practice Form menu item");
        practiceFormMenu.safeClick();
        return this;
    }

    @Override
    public boolean isAt() {
        StepLogger.info("Verifying Forms page is loaded");
        WaitUtils.waitForVisibility(driver, PRACTICE_FORM_HEADER);
        return formHeader.isDisplayed();
    }

    @Override
    protected FormsPage self() {
        return this;
    }

    public FormsPage fillFirstName(String firstName) {
        StepLogger.info("Filling first name: " + firstName);
        firstNameInput.type(firstName);
        return this;
    }

    public FormsPage fillLastName(String lastName) {
        StepLogger.info("Filling last name: " + lastName);
        lastNameInput.type(lastName);
        return this;
    }

    public FormsPage fillEmail(String email) {
        StepLogger.info("Filling email: " + email);
        emailInput.type(email);
        return this;
    }

    public FormsPage fillPhone(String phone) {
        StepLogger.info("Filling phone: " + phone);
        phoneInput.type(phone);
        return this;
    }

    public FormsPage selectMaleGender() {
        StepLogger.info("Selecting Male gender");
        maleGenderRadio.safeClick();
        return this;
    }

    public FormsPage selectFemaleGender() {
        StepLogger.info("Selecting Female gender");
        femaleGenderRadio.safeClick();
        return this;
    }

    public FormsPage selectOtherGender() {
        StepLogger.info("Selecting Other gender");
        otherGenderRadio.safeClick();
        return this;
    }

    public FormsPage fillDateOfBirth(String date) {
        StepLogger.info("Filling date of birth: " + date);
        dateOfBirthInput.clear();
        dateOfBirthInput.type(date);
        return this;
    }

    public FormsPage fillSubjects(String subjects) {
        StepLogger.info("Filling subjects: " + subjects);
        subjectsInput.type(subjects);
        // Press Escape to close any autocomplete dropdown
        driver.findElement(By.id("subjectsInput")).sendKeys(Keys.ESCAPE);
        return this;
    }

    public FormsPage selectSportsHobby() {
        StepLogger.info("Selecting Sports hobby");
        sportsHobbyCheckbox.check();
        return this;
    }

    public FormsPage selectReadingHobby() {
        StepLogger.info("Selecting Reading hobby");
        readingHobbyCheckbox.check();
        return this;
    }

    public FormsPage selectMusicHobby() {
        StepLogger.info("Selecting Music hobby");
        musicHobbyCheckbox.check();
        return this;
    }

    public FormsPage fillCurrentAddress(String address) {
        StepLogger.info("Filling current address: " + address);
        currentAddressTextarea.type(address);
        return this;
    }

    public FormsPage submitForm() {
        StepLogger.info("Submitting the form");
        submitButton.safeClick();
        return this;
    }

    public boolean isFormHeaderDisplayed() {
        return formHeader.isDisplayed();
    }

    public String getFirstNameValue() {
        return firstNameInput.getValue();
    }

    public String getLastNameValue() {
        return lastNameInput.getValue();
    }

    public String getEmailValue() {
        return emailInput.getValue();
    }

    public String getPhoneValue() {
        return phoneInput.getValue();
    }

    public String getCurrentAddressValue() {
        return currentAddressTextarea.getValue();
    }

    public boolean isSportsHobbySelected() {
        return sportsHobbyCheckbox.isChecked();
    }

    public boolean isReadingHobbySelected() {
        return readingHobbyCheckbox.isChecked();
    }

    public boolean isMusicHobbySelected() {
        return musicHobbyCheckbox.isChecked();
    }
}