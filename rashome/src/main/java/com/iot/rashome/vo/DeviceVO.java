package com.iot.rashome.vo;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iot.rashome.commons.enums.DeviceStatus;
import com.iot.rashome.commons.util.DateUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@JsonIgnoreProperties({"createdBy", "creationDate", "updatedBy", "updatedDate"})
// todo : 解决默认值的问题
public class DeviceVO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(updatable = false, insertable = false)
    public String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, insertable = false)
    public ZonedDateTime creationDate;

    @Column(insertable = false)
    public String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    public ZonedDateTime updatedDate;

    // 默认值是下线
    @Column(insertable = false)
    private String status;

    @JsonProperty(value = "health_check_url")
    private String healthCheckUrl;

    @JsonProperty(value = "health_check_rate")
    private String healthCheckRate;

    @JsonProperty(value = "device_name")
    private String deviceName;

    @JsonProperty(value = "device_information")
    private String deviceInformation;

    @JsonProperty(value = "device_uuid")
    @Column(updatable = false)
    private String deviceUuid;

    @JsonProperty(value = "parent_uuid")
    private String parentUuid;

    @JsonProperty(value = "gateway_uuid")
    private String gatewayUuid;

    @JsonProperty(value = "device_tag")
    private String deviceTag;

    @PrePersist
    @PreUpdate
    private void defaultValue() {

        updatedBy = setIfNull(updatedBy, "SYSTEM");
        updatedDate = setIfNull(updatedDate, DateUtil.getCurDate());
        status = setIfNull(status, DeviceStatus.OFFLINE.name());
        healthCheckRate = setIfNull(healthCheckRate, "30");

    }

    private <T> T setIfNull(T object, T value) {
        return ObjectUtils.isEmpty(object) ? value : object;
    }
}
