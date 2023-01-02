package com.iot.rashome.vo;

import com.iot.rashome.vo.base.BaseVO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "device_information")
public class DeviceInformationVO extends BaseVO {
    
    private String deviceName;

    private String deviceType;

    private String deviceModel;
}
