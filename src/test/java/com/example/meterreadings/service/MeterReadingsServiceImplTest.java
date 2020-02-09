package com.example.meterreadings.service;

import com.example.meterreadings.domain.delegate.MeterDelegate;
import com.example.meterreadings.domain.dto.MeterReadingDTO;
import com.example.meterreadings.domain.dto.TotalYearlyMeterReadingDTO;
import com.example.meterreadings.domain.dto.YearlyMeterReadingsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeterReadingsServiceImplTest {

    @Mock
    private MeterDelegate meterDelegate;

    @InjectMocks
    private MeterReadingServiceImpl meterReadingsService;

    @Test
    public void whenGetTotalReadingForYear_thenReturnJson() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int totalReading = 333;
        TotalYearlyMeterReadingDTO dto = TotalYearlyMeterReadingDTO
                .builder()
                .serialNumber(serialNumber)
                .year(year)
                .totalReading(totalReading)
                .build();
        String json = "{\"serialNumber\":\"327p61\",\"year\":2019,\"totalReading\":333}";

        when(meterDelegate.getTotalReadingForYear(serialNumber, year)).thenReturn(dto);

        assertThat(meterReadingsService.getTotalReadingForYear(serialNumber, year), is(json));
    }

    @Test
    public void whenGetReadingsForYear_thenReturnJson() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int listSize = 2;
        Map<Month, Integer> readings = new HashMap<>() {{
           put(Month.NOVEMBER, 111);
           put(Month.DECEMBER, 222);
        }};
        YearlyMeterReadingsDTO dto = YearlyMeterReadingsDTO
                .builder()
                .serialNumber(serialNumber)
                .year(year)
                .readings(readings)
                .build();
        String json = "{\"serialNumber\":\"327p61\",\"year\":2019,\"readings\":{\"NOVEMBER\":111,\"DECEMBER\":222}}";
        String json2 = "{\"serialNumber\":\"327p61\",\"year\":2019,\"readings\":{\"DECEMBER\":222,\"NOVEMBER\":111}}";

        when(meterDelegate.getReadingsForYear(serialNumber, year)).thenReturn(dto);

        String found = meterReadingsService.getReadingsForYear(serialNumber, year);
        assertThat(json.equals(found) || json2.equals(found), is(true));
    }

    @Test
    public void whenGetReadingForYearAndMonth_thenReturnJson() {
        // given
        String serialNumber = "327p61";
        int year = 2019;
        int month = 11;
        int reading = 222;
        MeterReadingDTO dto = MeterReadingDTO
                .builder()
                .serialNumber(serialNumber)
                .month(Month.of(month).name())
                .year(year)
                .reading(reading)
                .build();
        String json = "{\"serialNumber\":\"327p61\",\"month\":\"NOVEMBER\",\"year\":2019,\"reading\":222}";

        when(meterDelegate.getReadingForYearAndMonth(serialNumber, year, month)).thenReturn(dto);

        assertThat(meterReadingsService.getReadingForYearAndMonth(serialNumber, year, month), is(json));
    }
}
