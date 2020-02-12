package com.example.meterreadings.service;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.dao.repository.MeterRepository;
import com.example.meterreadings.request.MonthlyMeterReadingRequest;
import com.example.meterreadings.request.TotalYearlyMeterReadingRequest;
import com.example.meterreadings.request.YearlyMeterReadingsRequest;
import com.example.meterreadings.response.MonthlyMeterReadingResponse;
import com.example.meterreadings.response.TotalYearlyMeterReadingResponse;
import com.example.meterreadings.response.YearlyMeterReadingsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Month;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeterReadingsServiceImplTest {

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MeterReadingServiceImpl meterReadingsService;

    @Test
    public void whenGetTotalReadingForYear_thenReturnJson() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        Optional<Integer> totalReading = Optional.of(333);
        TotalYearlyMeterReadingRequest request = TotalYearlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .year(year).build();
        TotalYearlyMeterReadingResponse response = TotalYearlyMeterReadingResponse.builder().serialNumber(serialNumber)
                .year(year).totalReading(totalReading.get()).build();

        String json = "{\"serialNumber\":\"327p61\",\"year\":2019,\"totalReading\":333}";

        when(meterRepository.findTotalReadingBySerialNumberAndYear(serialNumber, year)).thenReturn(totalReading);

        assertThat(meterReadingsService.getTotalReadingForYear(request), is(response));
    }

    @Test
    public void whenGetReadingsForYear_thenReturnJson() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int listSize = 2;
        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(serialNumber, year);
        Map<Month, Integer> readings = new HashMap<>() {{
           put(Month.NOVEMBER, 111);
           put(Month.DECEMBER, 222);
        }};
        YearlyMeterReadingsRequest request = YearlyMeterReadingsRequest.builder().serialNumber(serialNumber)
                .year(year).build();
        YearlyMeterReadingsResponse response = YearlyMeterReadingsResponse.builder().serialNumber(serialNumber)
                .year(year).readings(readings).build();

        String json = "{\"serialNumber\":\"327p61\",\"year\":2019,\"readings\":{\"NOVEMBER\":111,\"DECEMBER\":222}}";
        String json2 = "{\"serialNumber\":\"327p61\",\"year\":2019,\"readings\":{\"DECEMBER\":222,\"NOVEMBER\":111}}";

        when(meterRepository.findReadingsBySerialNumberAndYear(serialNumber, year)).thenReturn(meterReadings);

        assertThat(meterReadingsService.getReadingsForYear(request), is(response));
    }

    @Test
    public void whenGetReadingForYearAndMonth_thenReturnJson() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int month = 11;
        Optional<Integer> reading = Optional.of(222);
        MonthlyMeterReadingRequest request = MonthlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .year(year).month(month).build();
        MonthlyMeterReadingResponse response = MonthlyMeterReadingResponse.builder().serialNumber(serialNumber)
                .month(Month.of(month)).year(year).reading(reading.get()).build();

        String json = "{\"serialNumber\":\"327p61\",\"month\":\"NOVEMBER\",\"year\":2019,\"reading\":222}";

        when(meterRepository.findReadingBySerialNumberAndYearAndMonth(serialNumber, year, month)).thenReturn(reading);

        assertThat(meterReadingsService.getReadingForMonth(request), is(response));
    }

    private static List<MeterReading> getMeterReadingsWithAllValues(String serialNumber, int year) {
        List<MeterReading> meterReadings = new ArrayList<>();
        meterReadings.add(populateMeterReading(4L, 2L, serialNumber,
                Month.NOVEMBER, year, 111));
        meterReadings.add(populateMeterReading(5L, 2L, serialNumber,
                Month.DECEMBER, year, 222));
        return meterReadings;
    }

    private static MeterReading populateMeterReading(long meterReadingId, long meterId, String serialNumber,
                                                     Month month, int year, int reading) {
        MeterReading meterReading = new MeterReading();
        meterReading.setId(meterReadingId);
        meterReading.setMeter(new Meter());
        meterReading.getMeter().setId(meterId);
        meterReading.getMeter().setSerialNumber(serialNumber);
        meterReading.setMonth(month.getValue());
        meterReading.setYear(year);
        meterReading.setReading(reading);
        return meterReading;
    }
}
