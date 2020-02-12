package com.example.meterreadings.error;

public class MeterReadingNotFoundException extends RuntimeException {

    public MeterReadingNotFoundException(String serialNumber, int year) {
        super(String.format("No meter readings for meter serial number [%s] and year [%d]!", serialNumber, year));
    }

    public MeterReadingNotFoundException(String serialNumber, int month, int year) {
        super(String.format("No meter readings for meter serial number [%s] and month [%d/%d]!",
                serialNumber, month, year));
    }
}
