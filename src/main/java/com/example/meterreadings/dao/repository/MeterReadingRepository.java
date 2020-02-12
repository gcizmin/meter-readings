package com.example.meterreadings.dao.repository;

import com.example.meterreadings.dao.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    List<MeterReading> findByYear(int year);

    List<MeterReading> findByYearAndMeterSerialNumber(int year, String serialNumber);

    MeterReading findByMonthAndYearAndMeterSerialNumber(int month, int year, String serialNumber);
}