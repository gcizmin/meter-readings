package com.example.meterreadings.controller;

import com.example.meterreadings.service.MeterReadingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meter_readings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MeterReadingsController {

    private final MeterReadingsService meterReadingsService;

    @RequestMapping(value = "/serial_number/{serial_number}/year/{year}", method = RequestMethod.GET)
    public String getTotalReadingForYear(@PathVariable("serial_number") String serialNumber, @PathVariable("year") int year) {
        return meterReadingsService.getTotalReadingForYear(serialNumber, year);
    }

    @RequestMapping(value = "/serial_number/{serial_number}/year/{year}", method = RequestMethod.GET)
    public String getReadingsForYear(@PathVariable("serial_number") String serialNumber, @PathVariable("year") int year) {
        return meterReadingsService.getReadingsForYear(serialNumber, year);
    }

    @RequestMapping(value = "/serial_number/{serial_number}/year/{year}/month/{month}", method = RequestMethod.GET)
    public String getReadingForYearAndMonth(@PathVariable("serial_number") String serialNumber, @PathVariable("year") int year, @PathVariable("month") int month) {
        return meterReadingsService.getReadingForYearAndMonth(serialNumber, year, month);
    }
}
