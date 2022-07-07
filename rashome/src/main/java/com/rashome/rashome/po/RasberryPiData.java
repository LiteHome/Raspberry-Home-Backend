package com.rashome.rashome.po;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RasberryPiData {
    private Long id;

    private Long rasberryPiID;

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

    private Date createTime;
}