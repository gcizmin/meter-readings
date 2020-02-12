package com.example.meterreadings.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class TotalYearlyMeterReadingResponse {
    private String serialNumber;
    private int year;
    private int totalReading;
}
