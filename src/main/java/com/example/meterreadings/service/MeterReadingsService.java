package com.example.meterreadings.service;

import com.example.meterreadings.error.CannotAddMeterReadingException;
import com.example.meterreadings.error.MeterReadingNotFoundException;
import com.example.meterreadings.request.AddMonthlyMeterReadingRequest;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;
import com.example.meterreadings.response.AddMonthlyMeterReadingResponse;
import com.example.meterreadings.response.MonthlyMeterReadingResponse;
import com.example.meterreadings.response.TotalYearlyMeterReadingResponse;
import com.example.meterreadings.response.YearlyMeterReadingsResponse;

public interface MeterReadingsService {

    TotalYearlyMeterReadingResponse getTotalYearlyReading(TotalYearlyMeterReadingRequest request)
            throws MeterReadingNotFoundException;

    YearlyMeterReadingsResponse getYearlyReadings(YearlyMeterReadingsRequest request)
            throws MeterReadingNotFoundException;

    MonthlyMeterReadingResponse getMonthlyReading(MonthlyMeterReadingRequest request)
            throws MeterReadingNotFoundException;

    AddMonthlyMeterReadingResponse addMonthlyReading(AddMonthlyMeterReadingRequest request)
            throws CannotAddMeterReadingException;
}
