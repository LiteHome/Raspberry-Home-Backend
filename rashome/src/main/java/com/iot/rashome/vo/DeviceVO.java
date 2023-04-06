package com.iot.rashome.vo;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.iot.rashome.vo.base.BaseVO;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
@Table(name = "device")
public class DeviceVO extends BaseVO {

    private String deviceName;

    private String status;

    private String healthCheckUrl;

    private String healthCheckRate;

    private String deviceInformation;

    @PrePersist
    private void onCreate() {
        if (StringUtils.isEmpty(healthCheckRate)) {
            healthCheckRate = "5";
        }
    }
}
