package com.iot.rashome.vo;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iot.rashome.commons.enums.DeviceStatus;

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
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
@JsonIgnoreProperties({"id", "createdBy", "creationDate", "updatedBy", "updatedDate"})
public class DeviceVO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    public String createdBy = "SYSTEM";

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    public Date creationDate;

    public String updatedBy = "SYSTEM";

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedDate;

    // 默认值是下线
    private String status = DeviceStatus.ONLINE.name();

    @JsonProperty(value = "health_check_url")
    private String healthCheckUrl;

    // 默认值是 30s
    @JsonProperty(value = "health_check_rate")
    private String healthCheckRate = "30";

    @JsonProperty(value = "device_name")
    private String deviceName;

    @JsonProperty(value = "device_information")
    private String deviceInformation;

    @JsonProperty(value = "device_uccid")
    private String deviceUuid;

    @JsonProperty(value = "device_tag")
    private String deviceTag;
}
