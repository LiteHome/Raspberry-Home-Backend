package com.rashome.rashome.po;

import java.util.Date;

import com.rashome.rashome.dto.CameraInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraData {
    private Long id;

    private Long rasberryPiID;

    private Long sensorID;

    private Float frequency;

    private Long collectTime;

    private String imageUrl;

    private Integer isProcess;

    private Date create_time;

    private Date update_time;

    public CameraData(CameraInfo cameraInfo){
        this.rasberryPiID = cameraInfo.getRasberryPiID();
        this.sensorID = cameraInfo.getSensorID();
        this.frequency = cameraInfo.getFrequency();
        this.collectTime = cameraInfo.getCollectTime();
    }
}