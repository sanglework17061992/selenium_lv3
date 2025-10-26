package com.sangle.selenium.utils;

import net.datafaker.Faker;

public final class FakerUtil {

    private static final Faker FAKER = new Faker();

    private FakerUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String firstName() {
        return FAKER.name().firstName();
    }

    public static String lastName() {
        return FAKER.name().lastName();
    }

    public static String fullName() {
        return FAKER.name().fullName();
    }

    public static String email() {
        return FAKER.internet().emailAddress();
    }

    public static String phoneNumber() {
        return FAKER.phoneNumber().cellPhone();
    }

    public static String city() {
        return FAKER.address().city();
    }

    public static String streetAddress() {
        return FAKER.address().streetAddress();
    }

    public static String postalCode() {
        return FAKER.address().zipCode();
    }

    public static String company() {
        return FAKER.company().name();
    }
}
