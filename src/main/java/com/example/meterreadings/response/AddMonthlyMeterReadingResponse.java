package com.example.meterreadings.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddMonthlyMeterReadingResponse {
    private String serialNumber;
    private int year;
    private int month;
    private int reading;
    private String statusMessage;
}
