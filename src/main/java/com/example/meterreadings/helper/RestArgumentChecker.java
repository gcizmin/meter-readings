package com.example.meterreadings.helper;

import com.example.meterreadings.error.BadArgumentException;

public class RestArgumentChecker {

    public static void checkMonth(int month) throws BadArgumentException {
        if (isInvalidMonth(month)) throw new BadArgumentException("Month should be between [1-12]");
    }

    public static void checkYear(int year) throws BadArgumentException {
        if (isInvalidYear(year)) throw new BadArgumentException("Year should be between [1900-3000]");
    }

    private static boolean isInvalidMonth(int month) {
        return month < 1 || month > 12;
    }

    private static boolean isInvalidYear(int year) {
        return year < 1900 || year > 3000;
    }
}
