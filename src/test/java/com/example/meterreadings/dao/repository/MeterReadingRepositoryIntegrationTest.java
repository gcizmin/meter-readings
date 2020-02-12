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

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Test
    public void whenFindByYear_thenReturnAllMeterReadings() {
        // given
        int year = 2020;

        // when
        List<MeterReading> found = meterReadingRepository.findByYear(year);

        // then
        assertThat(found.stream().allMatch(reading -> reading.getYear() == year), is(true));
    }

    @Test
    public void whenFindByYearAndSerialNumber_thenReturnAllMeterReadings() {
        // given
        int year = 2019;
        String serialNumber = "327p61";

        // when
        List<MeterReading> found = meterReadingRepository.findByYearAndMeterSerialNumber(year, serialNumber);

        // then
        assertThat(found.stream().allMatch(reading -> reading.getYear() == year &&
                serialNumber.equals(reading.getMeter().getSerialNumber())), is(true));

        assertThat(found.size(),  is(2));
    }

    @Test
    public void whenFindByMonthAndYearAndSerialNumber_thenReturnAllMeterReadings() {
        // given
        int month = 11;
        int year = 2019;
        String serialNumber = "327p61";

        // when
        MeterReading found = meterReadingRepository.findByMonthAndYearAndMeterSerialNumber(month, year, serialNumber);

        // then
        assertThat(found.getMonth() == month && found.getYear() == year &&
                serialNumber.equals(found.getMeter().getSerialNumber()), is(true));
    }
}