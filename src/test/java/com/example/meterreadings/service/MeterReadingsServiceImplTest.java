package com.example.meterreadings.service;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.dao.repository.MeterReadingRepository;
import com.example.meterreadings.dao.repository.MeterRepository;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Month;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeterReadingsServiceImplTest {

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private MeterReadingRepository meterReadingRepository;

    @InjectMocks
    private MeterReadingServiceImpl meterReadingsService;

    @Test
    public void whenGetTotalYearlyReading_thenReturnTotalYearlyMeterReadingResponse() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        Optional<Integer> totalReading = Optional.of(333);
        TotalYearlyMeterReadingRequest request = TotalYearlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .year(year).build();
        TotalYearlyMeterReadingResponse response = TotalYearlyMeterReadingResponse.builder().serialNumber(serialNumber)
                .year(year).totalReading(totalReading.get()).build();

        when(meterRepository.findTotalReadingBySerialNumberAndYear(serialNumber, year)).thenReturn(totalReading);

        assertThat(meterReadingsService.getTotalYearlyReading(request), is(response));
    }

    @Test
    public void whenGetTotalYearlyReading_thenReturnMeterReadingNotFoundException() {
        // given
        String serialNumber = "327p61";
        int year = 2019;

        TotalYearlyMeterReadingRequest request = TotalYearlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .year(year).build();

        when(meterRepository.findTotalReadingBySerialNumberAndYear(serialNumber, year)).thenReturn(Optional.empty());

         Exception exception = assertThrows(MeterReadingNotFoundException.class,
                () -> {meterReadingsService.getTotalYearlyReading(request);});

        String expectedMessage = "No meter readings for meter serial number [327p61] and year [2019]!";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage.equals(expectedMessage), is(true));
    }

    @Test
    public void whenGetYearlyReadings_thenReturnYearlyMeterReadingsResponse() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(serialNumber, year);
        Map<Month, Integer> readings = new HashMap<>() {{
            put(Month.NOVEMBER, 111);
            put(Month.DECEMBER, 222);
        }};
        YearlyMeterReadingsRequest request = YearlyMeterReadingsRequest.builder().serialNumber(serialNumber)
                .year(year).build();
        YearlyMeterReadingsResponse response = YearlyMeterReadingsResponse.builder().serialNumber(serialNumber)
                .year(year).readings(readings).build();

        when(meterRepository.findReadingsBySerialNumberAndYear(serialNumber, year)).thenReturn(meterReadings);

        assertThat(meterReadingsService.getYearlyReadings(request), is(response));
    }

    @Test
    public void whenGetYearlyReadings_thenReturnMeterReadingNotFoundException() {
        // given
        String serialNumber = "327p61";
        int year = 2019;

        YearlyMeterReadingsRequest request = YearlyMeterReadingsRequest.builder().serialNumber(serialNumber)
                .year(year).build();

        when(meterRepository.findReadingsBySerialNumberAndYear(serialNumber, year)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(MeterReadingNotFoundException.class,
                () -> {meterReadingsService.getYearlyReadings(request);});

        String expectedMessage = "No meter readings for meter serial number [327p61] and year [2019]!";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage.equals(expectedMessage), is(true));
    }

    @Test
    public void whenGetMonthlyReading_thenReturnMonthlyMeterReadingResponse() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int month = 11;
        Optional<Integer> reading = Optional.of(222);
        MonthlyMeterReadingRequest request = MonthlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .year(year).month(month).build();
        MonthlyMeterReadingResponse response = MonthlyMeterReadingResponse.builder().serialNumber(serialNumber)
                .month(Month.of(month)).year(year).reading(reading.get()).build();

        when(meterRepository.findReadingBySerialNumberAndYearAndMonth(serialNumber, year, month)).thenReturn(reading);

        assertThat(meterReadingsService.getMonthlyReading(request), is(response));
    }

    @Test
    public void whenGetMonthlyReading_thenReturnMeterReadingNotFoundException() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int month = 11;
        MonthlyMeterReadingRequest request = MonthlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .year(year).month(month).build();

        when(meterRepository.findReadingBySerialNumberAndYearAndMonth(serialNumber, year, month)).thenReturn(Optional.empty());

        Exception exception = assertThrows(MeterReadingNotFoundException.class,
                () -> {meterReadingsService.getMonthlyReading(request);});

        String expectedMessage = "No meter readings for meter serial number [327p61] and month [11/2019]!";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage.equals(expectedMessage), is(true));
    }

    @Test
    public void whenAddMonthlyReading_thenReturnAddMonthlyMeterReadingResponse() {
        // given
        String serialNumber = "327p61";
        int month = 10;
        int year = 2019;
        int reading = 333;

        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(serialNumber, year);

        AddMonthlyMeterReadingRequest request = AddMonthlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .month(month).year(year).reading(reading).build();
        AddMonthlyMeterReadingResponse response = AddMonthlyMeterReadingResponse.builder().serialNumber(serialNumber)
                .month(month).year(year).reading(reading).statusMessage("Success").build();

        when(meterRepository.findBySerialNumber(serialNumber))
                .thenReturn(populateMeter(2L, serialNumber, meterReadings));

        assertThat(meterReadingsService.addMonthlyReading(request), is(response));
    }

    @Test
    public void whenAddMonthlyReading_thenReturnCannotAddMeterReadingException() {
        // given
        String serialNumber = "327p61";
        int month = 10;
        int year = 2019;
        int reading = 333;

        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(serialNumber, year);

        AddMonthlyMeterReadingRequest request = AddMonthlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .month(month).year(year).reading(reading).build();

        when(meterRepository.findBySerialNumber(serialNumber)).thenReturn(null);

        Exception exception = assertThrows(CannotAddMeterReadingException.class,
                () -> {meterReadingsService.addMonthlyReading(request);});

        String expectedMessage = "Meter with that serial number does not exist";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage.contains(expectedMessage), is(true));
    }

    @Test
    public void whenAddMonthlyReading_thenReturnCannotAddMeterReadingException_DoubleMonth() {
        // given
        String serialNumber = "327p61";
        int month = 11;
        int year = 2019;
        int reading = 111;

        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(serialNumber, year);

        AddMonthlyMeterReadingRequest request = AddMonthlyMeterReadingRequest.builder().serialNumber(serialNumber)
                .month(month).year(year).reading(reading).build();

        when(meterRepository.findBySerialNumber(serialNumber)).thenReturn(populateMeter(2L, serialNumber, meterReadings));

        Exception exception = assertThrows(CannotAddMeterReadingException.class,
                () -> {meterReadingsService.addMonthlyReading(request);});

        String expectedMessage = "Reading for that month already exists";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage.contains(expectedMessage), is(true));
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

    private static Meter populateMeter(long meterId, String serialNumber, List<MeterReading> meterReadings) {
        Meter meter = new Meter();
        meter.setId(meterId);
        meter.setSerialNumber(serialNumber);
        meter.setMeterReadings(meterReadings);
        return meter;
    }
}
