package com.rashome.rashome.po;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dht11Data {
    private Long id;

    private String rasPiName;

    private Long rasPiID;

    private String sensorType;

    private String sensorModel;

    private Long sensorID;

    private Float frequency;

    private Long collectTime;

    private Float sensorData;

    private Date create_time;
}