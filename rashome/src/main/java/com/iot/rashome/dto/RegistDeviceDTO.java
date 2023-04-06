package com.iot.rashome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegistDeviceDTO {

    @JsonProperty(value = "health_check_url")
    private String healthCheckUrl;

    @JsonProperty(value = "health_check_rate")
    private String healthCheckRate;

    @JsonProperty(value = "device_name")
    private String deviceName;

    @JsonProperty(value = "device_information")
    private String deviceInformation;
}
