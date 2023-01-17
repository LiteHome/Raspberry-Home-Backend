package com.iot.rashome.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@JsonInclude(Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "device_data")
@JsonIgnoreProperties({"id", "masterId", "sensorId"})
public class DeviceDataVO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @JsonProperty(value = "master_id")
    private Long masterId;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value = "collected_date")
    private Date collectedDate;

    @JsonProperty(value = "sensor_id")
    private Long sensorId;

    private Float temperature;

    private Float humidity;

    @JsonProperty(value = "success_request_latency_avg")
    private Float successRequestLatencyAvg;

    @JsonProperty(value = "fail_request_latency_avg")
    private Float failRequestLatencyAvg;

    @JsonProperty(value = "fail_fetch_sensor_data_count_avg")
    private Float failFetchSensorDataCountAvg;
}
