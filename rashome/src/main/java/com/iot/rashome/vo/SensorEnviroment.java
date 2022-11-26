package com.iot.rashome.vo;

import java.sql.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "sensor_enviroment")
public class SensorEnviroment {

    @Id
    @GeneratedValue
    private Long id;

    private String createdBy = "System";

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String sampledBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sampleDate;

    private Float temperature;

    private Float humidity;
}
