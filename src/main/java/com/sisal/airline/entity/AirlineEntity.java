package com.sisal.airline.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "AIRLINE")
public class AirlineEntity {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column(name = "created_date")
    private OffsetDateTime createdDate;
}
