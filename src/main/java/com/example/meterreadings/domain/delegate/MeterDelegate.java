package com.example.meterreadings.domain.delegate;

import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.dao.repository.MeterRepository;
import com.example.meterreadings.domain.dto.MeterReadingDTO;
import com.example.meterreadings.domain.dto.TotalYearlyMeterReadingDTO;
import com.example.meterreadings.domain.dto.YearlyMeterReadingsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MeterDelegate {

    private MeterRepository meterRepository;

    public TotalYearlyMeterReadingDTO getTotalReadingForYear(String serialNumber, int year) {
        int totalReading =  meterRepository.findTotalReadingBySerialNumberAndYear(serialNumber, year);
        return TotalYearlyMeterReadingDTO
                .builder()
                .serialNumber(serialNumber)
                .year(year)
                .totalReading(totalReading)
                .build();
    }

    public YearlyMeterReadingsDTO getReadingsForYear(String serialNumber, int year) {
        List<MeterReading> meterReadings = meterRepository.findReadingsBySerialNumberAndYear(serialNumber, year);
        Map<Month, Integer> readings = meterReadings.stream().collect(
                Collectors.toMap(MeterReading::getMonth, MeterReading::getReading));
        return YearlyMeterReadingsDTO
                .builder()
                .serialNumber(serialNumber)
                .year(year)
                .readings(readings)
                .build();
    }

    public MeterReadingDTO getReadingForYearAndMonth(String serialNumber, int year, int month) {
        int reading = meterRepository.findReadingBySerialNumberAndYearAndMonth(serialNumber, year, month);
        return MeterReadingDTO.builder()
                .serialNumber(serialNumber)
                .year(year)
                .month(Month.of(month).name())
                .reading(reading)
                .build();
    }
}
