package com.rashome.rashome.po;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RasPiHeartbeatData {
    private Long id;

    private String name;

    private String model;

    private Long rasPiID;

    private Float frequency;

    private Long collectTime;

    private Float cpuAverageUsage;

    private String cpuPerUsage;

    private Integer cpuLogicCores;

    private Float cpuTemperature;

    private Long memoryTotal;

    private Long memoryUsed;

    private Long memoryFree;

    private Float memoryPercent;

    private Long swapTotal;

    private Long swapUsed;

    private Long swapFree;

    private Float swapPercent;

    private Float latency;

    private Date create_time;
}