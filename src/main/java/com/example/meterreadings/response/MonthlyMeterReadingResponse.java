package com.example.meterreadings.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Month;

@Builder
@Getter
@EqualsAndHashCode
public class MonthlyMeterReadingResponse {
    private String serialNumber;
    private Month month;
    private int year;
    private int reading;
}
