package com.example.meterreadings.service;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.dao.repository.MeterReadingRepository;
import com.example.meterreadings.dao.repository.MeterRepository;
import com.example.meterreadings.error.CannotAddMeterReadingException;
import com.example.meterreadings.error.MeterReadingNotFoundException;
import com.example.meterreadings.helper.MeterReadingHelper;
import com.example.meterreadings.request.AddMonthlyMeterReadingRequest;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;
import com.example.meterreadings.response.AddMonthlyMeterReadingResponse;
import com.example.meterreadings.response.MonthlyMeterReadingResponse;
import com.example.meterreadings.response.TotalYearlyMeterReadingResponse;
import com.example.meterreadings.response.YearlyMeterReadingsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MeterReadingServiceImpl implements MeterReadingsService {

    private final MeterRepository meterRepository;

    private final MeterReadingRepository meterReadingRepository;

    @Override
    public TotalYearlyMeterReadingResponse getTotalReadingForYear(TotalYearlyMeterReadingRequest request) {
        Optional<Integer> totalReading =  meterRepository.findTotalReadingBySerialNumberAndYear(
                request.getSerialNumber(), request.getYear());
        totalReading.orElseThrow(() -> new MeterReadingNotFoundException(request.getSerialNumber(), request.getYear()));
        return TotalYearlyMeterReadingResponse
                .builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .totalReading(totalReading.get())
                .build();

    }

    @Override
    public YearlyMeterReadingsResponse getReadingsForYear(YearlyMeterReadingsRequest request) {
        List<MeterReading> meterReadings = meterRepository.findReadingsBySerialNumberAndYear(
                request.getSerialNumber(), request.getYear());
        if (CollectionUtils.isEmpty(meterReadings))
            throw new MeterReadingNotFoundException(request.getSerialNumber(), request.getYear());
        Map<Month, Integer> readings = meterReadings.stream().collect(
                Collectors.toMap(mr -> Month.of(mr.getMonth()), MeterReading::getReading));
        return YearlyMeterReadingsResponse
                .builder()
                .serialNumber(request.getSerialNumber())
                .year(request.getYear())
                .readings(readings)
                .build();
    }

    @Override
    public MonthlyMeterReadingResponse getReadingForMonth(MonthlyMeterReadingRequest request) {
        Optional<Integer> reading = meterRepository.findReadingBySerialNumberAndYearAndMonth(
                request.getSerialNumber(), request.getYear(), request.getMonth());
        reading.orElseThrow(() -> new MeterReadingNotFoundException(request.getSerialNumber(), request.getMonth(), request.getYear()));
        return MonthlyMeterReadingResponse.builder()
                    .serialNumber(request.getSerialNumber())
                    .year(request.getYear())
                    .month(Month.of(request.getMonth()))
                    .reading(reading.get())
                    .build();
    }

    @Override
    @Transactional
    public AddMonthlyMeterReadingResponse addMonthlyReading(AddMonthlyMeterReadingRequest request) {
        Meter meter = meterRepository.findBySerialNumber(request.getSerialNumber());
        if (ObjectUtils.isEmpty(meter))
            throw new CannotAddMeterReadingException(request, "Meter with that serial number does not exist");
        if (MeterReadingHelper.readingAlreadyExists(meter, request))
            throw new CannotAddMeterReadingException(request, "Reading for that month already exists");
        MeterReading mr = MeterReadingHelper.populateMeterReading(meter, request);
        meterReadingRepository.save(mr);
        return AddMonthlyMeterReadingResponse.builder().serialNumber(request.getSerialNumber())
                .month(request.getMonth()).year(request.getYear()).reading(request.getReading())
                .statusMessage("Success").build();
    }
}
