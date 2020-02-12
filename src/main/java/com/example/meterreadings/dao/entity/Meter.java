package com.example.meterreadings.dao.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "METER")
@EqualsAndHashCode
public class Meter {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "serial_number", unique = true)
    private String serialNumber;
    @OneToMany(
        mappedBy = "meter",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<MeterReading> meterReadings;
}
