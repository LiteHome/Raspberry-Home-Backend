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
public class ReportDeviceDTO {
    
    @JsonProperty(value = "device_id")
    private Long deviceId;

    @JsonProperty(value = "nickname")
    private String nickname;
}
