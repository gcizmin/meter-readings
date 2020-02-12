package com.example.meterreadings.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Month;
import java.util.Map;

@Builder
@Getter
@EqualsAndHashCode
public class YearlyMeterReadingsResponse {
    private String serialNumber;
    private int year;
    private Map<Month, Integer> readings;
}
