package com.example.meterreadings.controller;

import com.example.meterreadings.error.BadArgumentException;

public class RestArgumentChecker {

    public static final int MIN_MONTH = 1;
    public static final int MAX_MONTH = 12;
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 3000;

    private RestArgumentChecker() {
        // empty
    }

    public static void checkMonth(int month) throws BadArgumentException {
        if (isInvalidMonth(month)) throw new BadArgumentException("Month should be between [1-12]");
    }

    public static void checkYear(int year) throws BadArgumentException {
        if (isInvalidYear(year)) throw new BadArgumentException("Year should be between [1900-3000]");
    }

    private static boolean isInvalidMonth(int month) {
        return month < MIN_MONTH || month > MAX_MONTH;
    }

    private static boolean isInvalidYear(int year) {
        return year < MIN_YEAR || year > MAX_YEAR;
    }
}
