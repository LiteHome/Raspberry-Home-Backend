package com.iot.rashome.vo;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sensor_enviroment")
public class SensorEnviromentVO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String createdBy = "System";

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp creationDate;

    private String sampledBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp sampleDate;

    private Float temperature;

    private Float humidity;
}
