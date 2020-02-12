package com.example.meterreadings.service;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.dao.repository.MeterReadingRepository;
import com.example.meterreadings.dao.repository.MeterRepository;
import com.example.meterreadings.error.CannotAddMeterReadingException;
import com.example.meterreadings.error.ErrorChecker;
import com.example.meterreadings.error.MeterReadingNotFoundException;
import com.example.meterreadings.helper.MeterReadingHelper;
import com.example.meterreadings.request.AddMonthlyMeterReadingRequest;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;
import com.example.meterreadings.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MeterReadingsServiceImpl implements MeterReadingsService {

    private final MeterRepository meterRepository;
    private final MeterReadingRepository meterReadingRepository;

    @Override
    public TotalYearlyMeterReadingResponse getTotalYearlyReading(TotalYearlyMeterReadingRequest request)
            throws MeterReadingNotFoundException {
        Optional<Integer> totalReading =  meterRepository.findTotalReadingBySerialNumberAndYear(
                request.getSerialNumber(), request.getYear());
        ErrorChecker.throwExceptionIfError(request, totalReading);
        return ResponseBuilder.buildTotalYearlyMeterReadingResponse(request, totalReading.get());
    }

    @Override
    public YearlyMeterReadingsResponse getYearlyReadings(YearlyMeterReadingsRequest request)
            throws MeterReadingNotFoundException {
        List<MeterReading> meterReadings = meterRepository.findReadingsBySerialNumberAndYear(
                request.getSerialNumber(), request.getYear());
        ErrorChecker.throwExceptionIfError(request, meterReadings);
        return ResponseBuilder.buildYearlyMeterReadingsResponse(request, meterReadings);
    }

    @Override
    public MonthlyMeterReadingResponse getMonthlyReading(MonthlyMeterReadingRequest request)
            throws MeterReadingNotFoundException {
        Optional<Integer> reading = meterRepository.findReadingBySerialNumberAndYearAndMonth(
                request.getSerialNumber(), request.getYear(), request.getMonth());
        ErrorChecker.throwExceptionIfError(request, reading);
        return ResponseBuilder.buildMonthlyMeterReadingResponse(request, reading.get());
    }

    @Override
    @Transactional
    public AddMonthlyMeterReadingResponse addMonthlyReading(AddMonthlyMeterReadingRequest request)
            throws CannotAddMeterReadingException {
        MeterReading mr = createMeterReading(request);
        meterReadingRepository.save(mr);
        return ResponseBuilder.buildAddMonthlyMeterReadingSuccessResponse(mr);
    }

    private MeterReading createMeterReading(AddMonthlyMeterReadingRequest request)
            throws CannotAddMeterReadingException {
        Meter meter = meterRepository.findBySerialNumber(request.getSerialNumber());
        ErrorChecker.throwExceptionIfError(request, meter);
        return MeterReadingHelper.populateMeterReading(meter, request);
    }
}
