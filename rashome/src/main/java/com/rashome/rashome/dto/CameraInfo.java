package com.rashome.rashome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraInfo {
    private Long rasberryPiID;

    private Long sensorID;

    private Float frequency;

    private Long collectTime;
}
