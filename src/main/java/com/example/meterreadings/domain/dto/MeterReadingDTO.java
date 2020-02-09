package com.example.meterreadings.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class MeterReadingDTO {
    private String serialNumber;
    private String month;
    private int year;
    private int reading;
}
