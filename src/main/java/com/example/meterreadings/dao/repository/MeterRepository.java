package com.example.meterreadings.dao.repository;

import com.example.meterreadings.dao.entity.Meter;
import com.example.meterreadings.dao.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {

    Meter findBySerialNumber(String SerialNumber);

    @Query("SELECT mr.reading " +
            "FROM Meter m, MeterReading mr " +
                "WHERE m.id = mr.meter.id " +
                    "AND m.serialNumber = :serialNumber " +
                    "AND mr.year = :year " +
                    "AND month = :month")
    int findReadingBySerialNumberAndYearAndMonth(@Param("serialNumber") String serialNumber,
                                                        @Param("year") int year,
                                                        @Param("month") int month);

    @Query("SELECT SUM(mr.reading) " +
            "FROM Meter m, MeterReading mr " +
            "WHERE m.id = mr.meter.id " +
            "AND m.serialNumber = :serialNumber " +
            "AND mr.year = :year")
    int findTotalReadingBySerialNumberAndYear(@Param("serialNumber") String serialNumber,
                                                @Param("year") int year);

    @Query("SELECT DISTINCT m.meterReadings " +
            "FROM Meter m, MeterReading mr " +
            "WHERE m.id = mr.meter.id " +
            "AND m.serialNumber = :serialNumber " +
            "AND mr.year = :year")
    List<MeterReading> findReadingsBySerialNumberAndYear(@Param("serialNumber") String serialNumber,
                                                            @Param("year") int year);
}