package com.example.meterreadings.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue
    private Long id;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "city")
    private String city;
}
