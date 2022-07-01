package com.rashome.rashome.entity;

import lombok.Data;

@Data
public class TemperatureSensor {
    private String rasPiName;
    private int rasPiID;
    private String type;
    private String model;
    private int id;
    private float frequency;
    private float time;
    private float data;
}
