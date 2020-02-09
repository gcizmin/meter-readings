package com.example.meterreadings.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class TotalYearlyMeterReadingDTO {
    private String serialNumber;
    private int year;
    private int totalReading;
}
