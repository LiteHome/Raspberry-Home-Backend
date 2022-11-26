package com.iot.rashome.vo;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "main_control")
public class MainControl {
    
    @Id
    @GeneratedValue
    private Long id;

    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    private String name;

    private String kind;

    private String model;
}
