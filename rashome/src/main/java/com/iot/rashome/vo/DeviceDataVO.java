package com.iot.rashome.vo;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.iot.rashome.converter.ListFloatConverter;
import com.iot.rashome.converter.StringDateDeserializer;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
@Entity
@Table(name = "device_data")
@JsonIgnoreProperties({"id", "deviceId"})
public class DeviceDataVO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value = "collected_date")
    @JsonDeserialize(using = StringDateDeserializer.class)
    // 前端传入是 long timestamp(单位 ms), deserializer 转为 ZoneDateTime
    private ZonedDateTime collectedDate;

    @JsonProperty(value = "device_id")
    private Long deviceId;

    private Float temperature;

    private Float humidity;

    @JsonProperty(value = "cpu_usage")
    @Convert(converter = ListFloatConverter.class)
    // 存储到数据库, 以及数据库取出的时候自动转换
    private List<Float> cpuUsage;

    @JsonProperty(value = "memory_usage")
    @Convert(converter = ListFloatConverter.class)
    // 存储到数据库, 以及数据库取出的时候自动转换
    private List<Float> memoryUsage;

    @JsonProperty(value = "disk_usage")
    @Convert(converter = ListFloatConverter.class)
    // 存储到数据库, 以及数据库取出的时候自动转换
    private List<Float> diskUsage;

    @JsonProperty(value = "network_usage")
    @Convert(converter = ListFloatConverter.class)
    // 存储到数据库, 以及数据库取出的时候自动转换
    private List<Float> networkUsage;

    @JsonProperty(value = "success_request_latency_avg")
    private Float successRequestLatencyAvg;

    @JsonProperty(value = "fail_request_latency_avg")
    private Float failRequestLatencyAvg;

    @JsonProperty(value = "fail_fetch_sensor_data_count_avg")
    private Float failFetchSensorDataCountAvg;

    @JsonProperty(value = "camera_image_url")
    private String cameraImageUrl;

    @Transient
    @JsonProperty(value = "camera_image_base64")
    private String cameraImageBase64;
}
