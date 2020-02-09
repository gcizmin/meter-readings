package com.example.meterreadings.dao.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Month;

@Getter
@Setter
@Entity
@Table(name = "METER_READING")
public class MeterReading {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Meter meter;
    @Column(name = "month")
    private Month month;
    @Column(name = "year")
    private Integer year;
    @Column(name = "reading")
    private Integer reading;
}
