package com.example.meterreadings.response;

import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;
import lombok.extern.slf4j.Slf4j;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ResponseBuilder {

    public static final String SUCCESS = "Success";

    private ResponseBuilder() {
        // empty
    }
    public static TotalYearlyMeterReadingResponse buildTotalYearlyMeterReadingResponse(
            TotalYearlyMeterReadingRequest request, Integer totalReading) {
        log.info(String.format("Returned - Meter serial number [%s], Year [%d], Total reading: [%d]",
                request.getSerialNumber(), request.getYear(), totalReading));
        return TotalYearlyMeterReadingResponse
                .builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .totalReading(totalReading)
                .build();
    }

    public static YearlyMeterReadingsResponse buildYearlyMeterReadingsResponse(
            YearlyMeterReadingsRequest request, List<MeterReading> meterReadings) {
        log.info(String.format("Returned - Meter serial number [%s], Year [%d], Meter readings",
                request.getSerialNumber(), request.getYear()));
        return YearlyMeterReadingsResponse
                .builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .readings(buildReadingsMap(meterReadings))
                .build();
    }

    public static MonthlyMeterReadingResponse buildMonthlyMeterReadingResponse(
            MonthlyMeterReadingRequest request, Integer reading) {
        log.info(String.format("Returned - Meter serial number [%s], Month: [%d], Year [%d], Reading: [%d]",
                request.getSerialNumber(), request.getMonth(), request.getYear(), reading));
        return MonthlyMeterReadingResponse.builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .month(Month.of(request.getMonth()))
                .reading(reading)
                .build();
    }

    public static AddMonthlyMeterReadingResponse buildAddMonthlyMeterReadingSuccessResponse(MeterReading mr) {
        log.info(String.format("Added - Meter serial number [%s], Month [%d], Year [%d], Reading [%d], Status: [%s]",
                mr.getMeter().getSerialNumber(), mr.getMonth(), mr.getYear(), mr.getReading(), SUCCESS));
        return AddMonthlyMeterReadingResponse.builder()
                .serialNumber(mr.getMeter().getSerialNumber())
                .month(mr.getMonth())
                .year(mr.getYear())
                .reading(mr.getReading())
                .statusMessage(SUCCESS).build();
    }

    private static Map<Month, Integer> buildReadingsMap(List<MeterReading> meterReadings) {
        return meterReadings.stream().collect(
                Collectors.toMap(mr -> Month.of(mr.getMonth()), MeterReading::getReading));
    }
}
