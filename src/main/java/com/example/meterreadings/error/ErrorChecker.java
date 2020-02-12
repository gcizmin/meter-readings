package com.example.meterreadings.error;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.helper.MeterReadingHelper;
import com.example.meterreadings.request.AddMonthlyMeterReadingRequest;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

public class ErrorChecker {

    private ErrorChecker() {
        // empty
    }
    public static void throwExceptionIfError(TotalYearlyMeterReadingRequest request, Optional<Integer> totalReading)
            throws MeterReadingNotFoundException {
        totalReading.orElseThrow(() -> new MeterReadingNotFoundException(request.getSerialNumber(), request.getYear()));
    }

    public static void throwExceptionIfError(YearlyMeterReadingsRequest request, List<MeterReading> meterReadings)
            throws MeterReadingNotFoundException {
        if (CollectionUtils.isEmpty(meterReadings))
            throw new MeterReadingNotFoundException(request.getSerialNumber(), request.getYear());
    }

    public static void throwExceptionIfError(MonthlyMeterReadingRequest request, Optional<Integer> reading)
            throws MeterReadingNotFoundException {
        reading.orElseThrow(() -> new MeterReadingNotFoundException(
                request.getSerialNumber(), request.getMonth(), request.getYear()));
    }

    public static void throwExceptionIfError(AddMonthlyMeterReadingRequest request, Meter meter)
            throws CannotAddMeterReadingException {
        checkNonExistingMeter(request, meter);
        checkExistingReading(request, meter);
    }

    private static void checkNonExistingMeter(AddMonthlyMeterReadingRequest request, Meter meter)
        throws CannotAddMeterReadingException {
        if (ObjectUtils.isEmpty(meter))
            throw new CannotAddMeterReadingException(request, "Meter with that serial number does not exist");
    }

    private static void checkExistingReading(AddMonthlyMeterReadingRequest request, Meter meter)
            throws CannotAddMeterReadingException {
        if (MeterReadingHelper.readingAlreadyExists(meter, request))
            throw new CannotAddMeterReadingException(request, "Reading for that month already exists");
    }
}
