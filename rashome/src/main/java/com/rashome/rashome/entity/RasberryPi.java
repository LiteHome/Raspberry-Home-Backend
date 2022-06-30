package com.rashome.rashome.entity;

import lombok.Data;

@Data
public class RasberryPi {
    private String rasName;
    private int id;
    private float time;
    private float cpuTEM;
    private float cpuUsage;
    private float netStatus;
}
