package com.example.meterreadings.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MonthlyMeterReadingRequest {
    private String serialNumber;
    private int year;
    private int month;
}
