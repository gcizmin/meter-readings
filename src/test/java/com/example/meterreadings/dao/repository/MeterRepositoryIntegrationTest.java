package com.example.meterreadings.dao.repository;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MeterRepositoryIntegrationTest {

    private static final String SERIAL_NUMBER_327p61 = "327p61";
    private static final int YEAR_2019 = 2019;
    private static final int MONTH_12 = 12;
    private static final int READING_222 = 222;
    private static final int READING_333 = 333;


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MeterRepository meterRepository;

    @Test
    public void whenFindBySerialNumber_thenReturnMeter() {
        Meter found = meterRepository.findBySerialNumber(SERIAL_NUMBER_327p61);
        assertThat(found.getSerialNumber(), is(SERIAL_NUMBER_327p61));
    }

    @Test
    public void whenFindReadingBySerialNumberAndYearAndMonth_thenReturnReading() {
        Optional<Integer> found = meterRepository.findReadingBySerialNumberAndYearAndMonth(
                SERIAL_NUMBER_327p61, YEAR_2019, MONTH_12);
        assertThat(found.get(), is(READING_222));
    }

    @Test
    public void whenFindTotalReadingBySerialNumberAndYear_thenReturnTotalReading() {
        Optional<Integer> found = meterRepository.findTotalReadingBySerialNumberAndYear(
                SERIAL_NUMBER_327p61, YEAR_2019);
        assertThat(found.get(), is(READING_333));
    }

    @Test
    public void whenFindReadingsBySerialNumberAndYear_thenReturnMeterReadings() {
        List<MeterReading> found = meterRepository.findReadingsBySerialNumberAndYear(SERIAL_NUMBER_327p61, YEAR_2019);
        assertThat(found.size(), is(2));
        assertThat(found.stream().allMatch(reading -> YEAR_2019 == reading.getYear() &&
                        SERIAL_NUMBER_327p61.equals(reading.getMeter().getSerialNumber())),
                is(true));
    }
}