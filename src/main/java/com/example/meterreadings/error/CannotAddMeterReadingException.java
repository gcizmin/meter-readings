package com.example.meterreadings.error;

import com.example.meterreadings.request.AddMonthlyMeterReadingRequest;

public class CannotAddMeterReadingException extends RuntimeException {

    public CannotAddMeterReadingException(AddMonthlyMeterReadingRequest request, String reason) {
        super(String.format("Cannot add meter reading [%d] for meter serial number [%s] and month [%d/%d] - %s",
                request.getReading(), request.getSerialNumber(), request.getMonth(), request.getYear(), reason));
    }
}
