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

    private static final String SERIAL_NUMBER_327p61 = "327p61";
    private static final int YEAR_2019 = 2019;
    private static final int MONTH_11 = 11;
    private static final int MONTH_10 = 10;
    private static final int READING_333 = 333;
    private static final int READING_222 = 222;
    private static final int READING_111 = 111;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private MeterReadingRepository meterReadingRepository;

    @InjectMocks
    private MeterReadingsServiceImpl meterReadingsService;

    @Test
    public void whenGetTotalYearlyReading_thenReturnTotalYearlyMeterReadingResponse() {
        TotalYearlyMeterReadingRequest request = TotalYearlyMeterReadingRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).build();
        TotalYearlyMeterReadingResponse response = TotalYearlyMeterReadingResponse.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).totalReading(READING_333).build();

        when(meterRepository.findTotalReadingBySerialNumberAndYear(SERIAL_NUMBER_327p61, YEAR_2019)).thenReturn(Optional.of(READING_333));

        assertThat(meterReadingsService.getTotalYearlyReading(request), is(response));
    }

    @Test
    public void whenGetTotalYearlyReading_thenReturnMeterReadingNotFoundException() {
        TotalYearlyMeterReadingRequest request = TotalYearlyMeterReadingRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).build();
        String expectedMessage = String.format(
                "No meter readings for meter serial number [%s] and year [%d]!", SERIAL_NUMBER_327p61, YEAR_2019);

        when(meterRepository.findTotalReadingBySerialNumberAndYear(SERIAL_NUMBER_327p61, YEAR_2019)).thenReturn(Optional.empty());
        String actualMessage = assertThrows(MeterReadingNotFoundException.class,
                () -> meterReadingsService.getTotalYearlyReading(request)).getMessage();

        assertThat(actualMessage.equals(expectedMessage), is(true));
    }

    @Test
    public void whenGetYearlyReadings_thenReturnYearlyMeterReadingsResponse() {
        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(SERIAL_NUMBER_327p61, YEAR_2019);
        Map<Month, Integer> readings = new HashMap<>() {{
            put(Month.NOVEMBER, READING_111);
            put(Month.DECEMBER, READING_222);
        }};
        YearlyMeterReadingsRequest request = YearlyMeterReadingsRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).build();
        YearlyMeterReadingsResponse response = YearlyMeterReadingsResponse.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).readings(readings).build();

        when(meterRepository.findReadingsBySerialNumberAndYear(SERIAL_NUMBER_327p61, YEAR_2019)).thenReturn(meterReadings);

        assertThat(meterReadingsService.getYearlyReadings(request), is(response));
    }

    @Test
    public void whenGetYearlyReadings_thenReturnMeterReadingNotFoundException() {
        YearlyMeterReadingsRequest request = YearlyMeterReadingsRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).build();
        String expectedMessage = String.format(
                "No meter readings for meter serial number [%s] and year [%d]!", SERIAL_NUMBER_327p61, YEAR_2019);

        when(meterRepository.findReadingsBySerialNumberAndYear(SERIAL_NUMBER_327p61, YEAR_2019))
                .thenReturn(new ArrayList<>());
        String actualMessage = assertThrows(MeterReadingNotFoundException.class,
                () -> meterReadingsService.getYearlyReadings(request)).getMessage();

        assertThat(actualMessage.equals(expectedMessage), is(true));
    }

    @Test
    public void whenGetMonthlyReading_thenReturnMonthlyMeterReadingResponse() {
        Optional<Integer> reading = Optional.of(READING_222);
        MonthlyMeterReadingRequest request = MonthlyMeterReadingRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).month(MONTH_11).build();
        MonthlyMeterReadingResponse response = MonthlyMeterReadingResponse.builder().serialNumber(SERIAL_NUMBER_327p61)
                .month(Month.of(MONTH_11)).year(YEAR_2019).reading(reading.get()).build();

        when(meterRepository.findReadingBySerialNumberAndYearAndMonth(SERIAL_NUMBER_327p61, YEAR_2019, MONTH_11)).thenReturn(reading);

        assertThat(meterReadingsService.getMonthlyReading(request), is(response));
    }

    @Test
    public void whenGetMonthlyReading_thenReturnMeterReadingNotFoundException() {
        MonthlyMeterReadingRequest request = MonthlyMeterReadingRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .year(YEAR_2019).month(MONTH_11).build();
        String expectedMessage = String.format(
                "No meter readings for meter serial number [%s] and month [%d/%d]!",
                SERIAL_NUMBER_327p61, MONTH_11, YEAR_2019);

        when(meterRepository.findReadingBySerialNumberAndYearAndMonth(SERIAL_NUMBER_327p61, YEAR_2019, MONTH_11)).thenReturn(Optional.empty());
        String actualMessage = assertThrows(MeterReadingNotFoundException.class,
                () -> meterReadingsService.getMonthlyReading(request)).getMessage();

        assertThat(actualMessage.equals(expectedMessage), is(true));
    }

    @Test
    public void whenAddMonthlyReading_thenReturnAddMonthlyMeterReadingResponse() {
        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(SERIAL_NUMBER_327p61, YEAR_2019);
        AddMonthlyMeterReadingRequest request = AddMonthlyMeterReadingRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .month(MONTH_10).year(YEAR_2019).reading(READING_333).build();
        AddMonthlyMeterReadingResponse response = AddMonthlyMeterReadingResponse.builder().serialNumber(SERIAL_NUMBER_327p61)
                .month(MONTH_10).year(YEAR_2019).reading(READING_333).statusMessage("Success").build();

        when(meterRepository.findBySerialNumber(SERIAL_NUMBER_327p61))
                .thenReturn(populateMeter(2L, SERIAL_NUMBER_327p61, meterReadings));

        assertThat(meterReadingsService.addMonthlyReading(request), is(response));
    }

    @Test
    public void whenAddMonthlyReading_thenReturnCannotAddMeterReadingException() {
        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(SERIAL_NUMBER_327p61, YEAR_2019);
        AddMonthlyMeterReadingRequest request = AddMonthlyMeterReadingRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .month(MONTH_10).year(YEAR_2019).reading(READING_333).build();
        String expectedMessage = "Meter with that serial number does not exist";

        when(meterRepository.findBySerialNumber(SERIAL_NUMBER_327p61)).thenReturn(null);
        String actualMessage = assertThrows(CannotAddMeterReadingException.class,
                () -> meterReadingsService.addMonthlyReading(request)).getMessage();

        assertThat(actualMessage.contains(expectedMessage), is(true));
    }

    @Test
    public void whenAddMonthlyReading_thenReturnCannotAddMeterReadingException_DoubleMonth() {
        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(SERIAL_NUMBER_327p61, YEAR_2019);
        AddMonthlyMeterReadingRequest request = AddMonthlyMeterReadingRequest.builder().serialNumber(SERIAL_NUMBER_327p61)
                .month(MONTH_11).year(YEAR_2019).reading(READING_111).build();
        Meter meter = populateMeter(2L, SERIAL_NUMBER_327p61, meterReadings);
        String expectedMessage = "Reading for that month already exists";

        when(meterRepository.findBySerialNumber(SERIAL_NUMBER_327p61)).thenReturn(meter);
        String actualMessage = assertThrows(CannotAddMeterReadingException.class,
                () -> meterReadingsService.addMonthlyReading(request)).getMessage();

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
