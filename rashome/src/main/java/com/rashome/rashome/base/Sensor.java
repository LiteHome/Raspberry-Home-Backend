package com.rashome.rashome.base;

import lombok.Data;

@Data
public abstract class Sensor {
    private String rasPiName;
    private int rasPiID;
    private String type;
    private String model;
    private int id;
    private float frequency;
    private float time;
}
