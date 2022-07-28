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

    private Long rasberryPiID;

    private Long sensorID;

    private Float frequency;

    // in ms
    private Long collectTime;

    private Float sensorTemperature;

    private Float sensorHumidity;

    private Date createTime;

    public Dht11Data(long rasberryPiID, long sensorID){
        this.rasberryPiID = rasberryPiID;
        this.sensorID = sensorID;
    }
}