package com.example.meterreadings.service;

import com.example.meterreadings.domain.delegate.MeterDelegate;
import com.example.meterreadings.domain.dto.MeterReadingDTO;
import com.example.meterreadings.domain.dto.TotalYearlyMeterReadingDTO;
import com.example.meterreadings.domain.dto.YearlyMeterReadingsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MeterReadingServiceImpl implements MeterReadingsService {

    private final MeterDelegate meterDelegate;

    @Override
    public String getTotalReadingForYear(String serialNumber, int year) {
        TotalYearlyMeterReadingDTO totalYearlyMeterReading = meterDelegate.getTotalReadingForYear(serialNumber, year);
        return getJson(totalYearlyMeterReading);
    }

    @Override
    public String getReadingsForYear(String serialNumber, int year) {
        YearlyMeterReadingsDTO yearlyMeterReadings = meterDelegate.getReadingsForYear(serialNumber, year);
        return getJson(yearlyMeterReadings);
    }

    @Override
    public String getReadingForYearAndMonth(String serialNumber, int year, int month) {
        MeterReadingDTO meterReading = meterDelegate.getReadingForYearAndMonth(serialNumber, year, month);
        return getJson(meterReading);
    }

    private String getJson(Object dto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
