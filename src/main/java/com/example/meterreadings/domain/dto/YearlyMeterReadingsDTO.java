package com.example.meterreadings.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Month;
import java.util.Map;

@Builder
@Getter
public class YearlyMeterReadingsDTO {
    private String serialNumber;
    private int year;
    private Map<Month, Integer> readings;
}
