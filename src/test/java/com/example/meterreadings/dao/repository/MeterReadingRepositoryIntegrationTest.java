package com.example.meterreadings.dao.repository;

import com.example.meterreadings.dao.entity.MeterReading;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MeterReadingRepositoryIntegrationTest {

    private static final int MONTH_11 = 11;
    private static final int YEAR_2019 = 2019;
    private static final int YEAR_2020 = 2020;
    private static final String SERIAL_NUMBER_327p61 = "327p61";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Test
    public void whenFindByYear_thenReturnAllMeterReadings() {
        List<MeterReading> found = meterReadingRepository.findByYear(YEAR_2020);
        assertThat(found.stream().allMatch(reading -> reading.getYear() == YEAR_2020), is(true));
    }

    @Test
    public void whenFindByYearAndSerialNumber_thenReturnAllMeterReadings() {
        List<MeterReading> found = meterReadingRepository.findByYearAndMeterSerialNumber(YEAR_2019, SERIAL_NUMBER_327p61);
        assertThat(found.stream().allMatch(reading -> reading.getYear() == YEAR_2019 &&
                SERIAL_NUMBER_327p61.equals(reading.getMeter().getSerialNumber())), is(true));
        assertThat(found.size(),  is(2));
    }

    @Test
    public void whenFindByMonthAndYearAndSerialNumber_thenReturnAllMeterReadings() {
        MeterReading found = meterReadingRepository.findByMonthAndYearAndMeterSerialNumber(
                MONTH_11, YEAR_2019, SERIAL_NUMBER_327p61);
        assertThat(found.getMonth() == MONTH_11 && found.getYear() == YEAR_2019 &&
                SERIAL_NUMBER_327p61.equals(found.getMeter().getSerialNumber()), is(true));
    }
}