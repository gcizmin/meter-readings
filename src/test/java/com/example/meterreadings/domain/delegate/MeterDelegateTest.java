package com.example.meterreadings.domain.delegate;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import com.example.meterreadings.dao.repository.MeterRepository;
import com.example.meterreadings.domain.dto.MeterReadingDTO;
import com.example.meterreadings.domain.dto.TotalYearlyMeterReadingDTO;
import com.example.meterreadings.domain.dto.YearlyMeterReadingsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeterDelegateTest {

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MeterDelegate meterDelegate;

    @Test
    public void whenGetTotalReadingForYear_thenReturnTotalYearlyMeterReadingDTO() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int totalReading = 333;

        when(meterRepository.findTotalReadingBySerialNumberAndYear(serialNumber, year)).thenReturn(totalReading);

        TotalYearlyMeterReadingDTO found = meterDelegate.getTotalReadingForYear(serialNumber, year);

        // then
        assertThat(found.getSerialNumber(), is(serialNumber));
        assertThat(found.getYear(), is(year));
        assertThat(found.getTotalReading(), is(totalReading));
    }

    @Test
    public void whenGetReadingsForYear_thenReturnYearlyMeterReadingsDTO() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int listSize = 2;
        List<MeterReading> meterReadings = getMeterReadingsWithAllValues(serialNumber, year);

        when(meterRepository.findReadingsBySerialNumberAndYear(serialNumber, year)).thenReturn(meterReadings);

        YearlyMeterReadingsDTO found = meterDelegate.getReadingsForYear(serialNumber, year);

        // then
        assertThat(found.getReadings().size(), is(listSize));
        assertThat(found.getSerialNumber(), is(serialNumber));
        assertThat(found.getYear(), is(year));

        assertThat(found.getReadings().get(Month.NOVEMBER) == 111 &&
                        found.getReadings().get(Month.DECEMBER) == 222,
                            is(true));
    }

    @Test
    public void whenGetReadingForYearAndMonth_thenReturnMeterReadingDTO() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int month = 11;
        int reading = 222;

        when(meterRepository.findReadingBySerialNumberAndYearAndMonth(serialNumber, year, month)).thenReturn(reading);

        MeterReadingDTO found = meterDelegate.getReadingForYearAndMonth(serialNumber, year, month);
        // then
        assertThat(found.getReading(), is(reading));
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
        meterReading.setMonth(month);
        meterReading.setYear(year);
        meterReading.setReading(reading);
        return meterReading;
    }
}