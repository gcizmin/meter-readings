package com.example.meterreadings.service;

public interface MeterReadingsService {

    String getTotalReadingForYear(String serialNumber, int year);

    String getReadingsForYear(String serialNumber, int year);

    String getReadingForYearAndMonth(String serialNumber, int year, int month);
}
