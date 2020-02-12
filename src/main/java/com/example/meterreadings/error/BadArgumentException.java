package com.example.meterreadings.error;

public class BadArgumentException extends RuntimeException {

    public BadArgumentException(String reason) {
        super(reason);
    }
}
