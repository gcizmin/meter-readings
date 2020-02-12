package com.example.meterreadings.helper;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.request.AddMonthlyMeterReadingRequest;

public class MeterReadingHelper {

    private MeterReadingHelper() {
        // empty
    }

    public static MeterReading populateMeterReading(Meter meter, AddMonthlyMeterReadingRequest request) {
        MeterReading mr = new MeterReading();
        mr.setMeter(meter);
        mr.setMonth(request.getMonth());
        mr.setYear(request.getYear());
        mr.setReading(request.getReading());
        return mr;
    }

    public static boolean readingAlreadyExists(Meter meter, AddMonthlyMeterReadingRequest request) {
        return meter.getMeterReadings().stream().anyMatch(
                mr -> mr.getMonth().equals(request.getMonth()) && mr.getYear().equals(request.getYear()));
    }
}
