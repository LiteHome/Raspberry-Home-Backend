package com.rashome.rashome.base;

import lombok.Data;

@Data
public abstract class Sensor {
    private String rasPiName;
    private long rasPiID;
    private String type;
    private String model;
    private long id;
    private float frequency;
    private long time;
}
