package com.rashome.rashome.entity;

import lombok.Data;

@Data
public class Sensor {
    private String rasName;
    private int rasID;
    private String type;
    private String model;
    private int id;
    private float time;
    private float data;
}
