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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/meter_readings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MeterReadingsController {

    private final MeterReadingsService meterReadingsService;

    /**
     * UC1: Aggregate meter reading for year
     *
     * @param serialNumber meter serial mumber (any combination of characters and numbers)
     * @param year year in a 4 numbers format (ex.g. 2019)
     * @return reading
     */
    @GetMapping(value = "/total/serial_number/{serial_number}/year/{year}")
    public TotalYearlyMeterReadingResponse getTotalYearlyReading(@PathVariable("serial_number") String serialNumber,
                                                                 @PathVariable("year") int year) {
        log.info(String.format("Request - Meter serial number: [%s], Year: [%d]", serialNumber, year));
        RestArgumentChecker.checkYear(year);
        return meterReadingsService.getTotalYearlyReading(
                TotalYearlyMeterReadingRequest.builder().serialNumber(serialNumber).year(year).build());
    }

    /**
     * UC2: Get all meter readings for year
     *
     * @param serialNumber meter serial mumber (any combination of characters and numbers)
     * @param year year in a 4 numbers format (ex.g. 2019)
     * @return readings
     */
    @GetMapping(value = "/yearly_readings/serial_number/{serial_number}/year/{year}")
    public YearlyMeterReadingsResponse getYearlyReadings(@PathVariable("serial_number") String serialNumber,
                                                         @PathVariable("year") int year) {
        log.info(String.format("Request - Meter serial number: [%s], Year: [%d]", serialNumber, year));
        RestArgumentChecker.checkYear(year);
        return meterReadingsService.getYearlyReadings(YearlyMeterReadingsRequest
                .builder().serialNumber(serialNumber).year(year).build());
    }

    /**
     * UC3: Get meter reading for month for year
     *
     * @param serialNumber meter serial mumber (any combination of characters and numbers)
     * @param year year in a 4 numbers format (ex.g. 2019)
     * @param month month (1-12)
     * @return reading
     */
    @GetMapping(value = "/reading/serial_number/{serial_number}/year/{year}/month/{month}")
    public MonthlyMeterReadingResponse getMonthlyReading(@PathVariable("serial_number") String serialNumber,
                                                         @PathVariable("year") int year,
                                                         @PathVariable("month") int month) {
        log.info(String.format("Request - Meter serial number: [%s], Month: [%d], Year: [%d]",
                serialNumber, month, year));
        RestArgumentChecker.checkYear(year);
        RestArgumentChecker.checkMonth(month);
        return meterReadingsService.getMonthlyReading(MonthlyMeterReadingRequest
                .builder().serialNumber(serialNumber).year(year).month(month).build());
    }

    /**
     * UC4: Add additional meter reading
     *
     * @param request request containing serial number, month, year and reading
     * @return reading with messageStatus=Success when successful, error otherwise
     */
    @PostMapping(value = "/add_monthly_reading")
    public AddMonthlyMeterReadingResponse addMonthlyReading(@RequestBody AddMonthlyMeterReadingRequest request) {
        log.info(String.format("Request - Meter serial number: [%s], Month: [%d], Year: [%d], Reading: [%d]",
                request.getSerialNumber(), request.getMonth(), request.getMonth(), request.getYear()));
        return meterReadingsService.addMonthlyReading(request);
    }
}
