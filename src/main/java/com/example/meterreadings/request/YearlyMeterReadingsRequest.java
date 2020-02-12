package com.example.meterreadings.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class YearlyMeterReadingsRequest {
    private String serialNumber;
    private int year;
}
