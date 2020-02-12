package com.example.meterreadings.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalYearlyMeterReadingRequest {
    private String serialNumber;
    private int year;
}
