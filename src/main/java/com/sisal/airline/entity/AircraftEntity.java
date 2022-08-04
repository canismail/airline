package com.sisal.airline.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "AIRCRAFT")
public class AircraftEntity {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column(name = "airline_id")
    private Integer airlineId;
    @Column(name = "created_date")
    private OffsetDateTime createdDate;
}
