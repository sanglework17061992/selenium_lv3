package com.sangle.selenium.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String now(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    public static LocalDate parseDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseDateTime(String dateTime, String pattern) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate addDays(LocalDate date, long days) {
        return date.plusDays(days);
    }

    public static LocalDateTime addDays(LocalDateTime dateTime, long days) {
        return dateTime.plusDays(days);
    }

    public static String convertTimeZone(LocalDateTime dateTime, ZoneId from, ZoneId to, String pattern) {
        return dateTime.atZone(from).withZoneSameInstant(to).toLocalDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }
}
