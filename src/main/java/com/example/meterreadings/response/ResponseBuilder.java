package com.example.meterreadings.response;

import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseBuilder {

    public static final String SUCCESS = "Success";

    private ResponseBuilder() {
        // empty
    }
    public static TotalYearlyMeterReadingResponse buildTotalYearlyMeterReadingResponse(
            TotalYearlyMeterReadingRequest request, Integer totalReading) {
        return TotalYearlyMeterReadingResponse
                .builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .totalReading(totalReading)
                .build();
    }

    public static YearlyMeterReadingsResponse buildYearlyMeterReadingsResponse(
            YearlyMeterReadingsRequest request, List<MeterReading> meterReadings) {
        return YearlyMeterReadingsResponse
                .builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .readings(buildReadingsMap(meterReadings))
                .build();
    }

    public static MonthlyMeterReadingResponse buildMonthlyMeterReadingResponse(
            MonthlyMeterReadingRequest request, Integer reading) {
        return MonthlyMeterReadingResponse.builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .month(Month.of(request.getMonth()))
                .reading(reading)
                .build();
    }

    public static AddMonthlyMeterReadingResponse buildAddMonthlyMeterReadingSuccessResponse(MeterReading mr) {
        return AddMonthlyMeterReadingResponse.builder().serialNumber(mr.getMeter().getSerialNumber())
                .month(mr.getMonth()).year(mr.getYear()).reading(mr.getReading())
                .statusMessage(SUCCESS).build();
    }

    private static Map<Month, Integer> buildReadingsMap(List<MeterReading> meterReadings) {
        return meterReadings.stream().collect(
                Collectors.toMap(mr -> Month.of(mr.getMonth()), MeterReading::getReading));
    }
}
