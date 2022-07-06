package com.rashome.rashome.po;

import java.util.Date;

import com.rashome.rashome.dto.RasberryPi;

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

    public RasPiHeartbeatData(RasberryPi rasberryPi){
        this.name = rasberryPi.getName();
        this.model = rasberryPi.getModel();
        this.rasPiID = rasberryPi.getId();
        this.frequency = rasberryPi.getFrequency();
        this.collectTime = rasberryPi.getTime();
        this.latency = rasberryPi.getLatency();

        this.cpuAverageUsage = rasberryPi.getCpuInfo().getCpuAverageUsage();
        this.cpuPerUsage = rasberryPi.getCpuInfo().getCpuPerUsage().toString();
        this.cpuLogicCores = rasberryPi.getCpuInfo().getCpuLogicCores();
        this.cpuTemperature = rasberryPi.getCpuInfo().getCpuTemperature();

        this.memoryTotal = rasberryPi.getMemoryInfo().getTotal();
        this.memoryUsed = rasberryPi.getMemoryInfo().getUsed();
        this.memoryFree = rasberryPi.getMemoryInfo().getFree();
        this.memoryPercent = rasberryPi.getMemoryInfo().getPercent();

        this.swapTotal = rasberryPi.getSwapInfo().getTotal();
        this.swapUsed = rasberryPi.getSwapInfo().getUsed();
        this.swapFree = rasberryPi.getSwapInfo().getFree();
        this.swapPercent = rasberryPi.getSwapInfo().getPercent();
    }
}