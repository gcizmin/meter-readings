package com.example.meterreadings.controller;

import com.example.meterreadings.request.AddMonthlyMeterReadingRequest;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;
import com.example.meterreadings.response.AddMonthlyMeterReadingResponse;
import com.example.meterreadings.response.MonthlyMeterReadingResponse;
import com.example.meterreadings.response.TotalYearlyMeterReadingResponse;
import com.example.meterreadings.response.YearlyMeterReadingsResponse;
import com.example.meterreadings.service.MeterReadingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/meter_readings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MeterReadingsController {

    private final MeterReadingsService meterReadingsService;

    @GetMapping(value = "/total/serial_number/{serial_number}/year/{year}")
    public TotalYearlyMeterReadingResponse getTotalYearlyReading(@PathVariable("serial_number") String serialNumber,
                                                                 @PathVariable("year") int year) {
        RestArgumentChecker.checkYear(year);
        return meterReadingsService.getTotalYearlyReading(
                TotalYearlyMeterReadingRequest.builder().serialNumber(serialNumber).year(year).build());
    }

    @GetMapping(value = "/yearly_readings/serial_number/{serial_number}/year/{year}")
    public YearlyMeterReadingsResponse getYearlyReadings(@PathVariable("serial_number") String serialNumber,
                                                         @PathVariable("year") int year) {
        RestArgumentChecker.checkYear(year);
        return meterReadingsService.getYearlyReadings(YearlyMeterReadingsRequest
                .builder().serialNumber(serialNumber).year(year).build());
    }

    @GetMapping(value = "/reading/serial_number/{serial_number}/year/{year}/month/{month}")
    public MonthlyMeterReadingResponse getMonthlyReading(@PathVariable("serial_number") String serialNumber,
                                                         @PathVariable("year") int year,
                                                         @PathVariable("month") int month) {
        RestArgumentChecker.checkYear(year);
        RestArgumentChecker.checkMonth(month);
        return meterReadingsService.getMonthlyReading(MonthlyMeterReadingRequest
                .builder().serialNumber(serialNumber).year(year).month(month).build());
    }

    @PostMapping(value = "/add_monthly_reading")
    public AddMonthlyMeterReadingResponse addMonthlyReading(@RequestBody AddMonthlyMeterReadingRequest request) {
        return meterReadingsService.addMonthlyReading(request);
    }
}
