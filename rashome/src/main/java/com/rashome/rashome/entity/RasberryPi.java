package com.rashome.rashome.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RasberryPi {
    private String name;
    private int id;
    private float time;
    private float cpuTEM;
    private float cpuUsage;
    private float netStatus;
}
