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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MeterRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MeterRepository meterRepository;

    @Test
    public void whenFindBySerialNumber_thenReturnMeter() {
        // given
        String serialNumber = "327p61";

        // when
        Meter found = meterRepository.findBySerialNumber(serialNumber);

        // then
        assertThat(found.getSerialNumber(), is(serialNumber));
    }

    @Test
    public void whenFindReadingBySerialNumberAndYearAndMonth_thenReturnReading() {
        // given
        String serialNumber = "327p61";

        int year = 2019;

        int month = 12;

        int reading = 222;

        // when
        int found = meterRepository.findReadingBySerialNumberAndYearAndMonth(serialNumber, year, month);

        // then
        assertThat(found, is(reading));
    }

    @Test
    public void whenFindTotalReadingBySerialNumberAndYear_thenReturnTotalReading() {
        // given
        String serialNumber = "327p61";

        int year = 2019;

        int totalReading = 333;

        // when
        int found = meterRepository.findTotalReadingBySerialNumberAndYear(serialNumber, year);

        // then
        assertThat(found, is(totalReading));
    }

    @Test
    public void whenFindReadingsBySerialNumberAndYear_thenReturnMeterReadings() {
        // given
        String serialNumber = "327p61";

        int year = 2019;

        int listSize = 2;

        // when
        List<MeterReading> found = meterRepository.findReadingsBySerialNumberAndYear(serialNumber, year);

        // then
        assertThat(found.size(), is(listSize));

        assertThat(found.stream().allMatch(
                reading -> year == reading.getYear() && serialNumber.equals(reading.getMeter().getSerialNumber())),
                is(true));
    }
}