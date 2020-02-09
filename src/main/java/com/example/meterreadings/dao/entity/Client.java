package com.example.meterreadings.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id")
    private Meter meter;
}
