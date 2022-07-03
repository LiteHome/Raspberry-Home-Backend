package com.rashome.rashome.entity;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RasberryPi {
    private String name;
    private String model;
    private long id;
    private float frequency;
    private long time;
    private CpuInfo cpuInfo;
    private MemoryInfo memoryInfo;
    private SwapInfo swapInfo;
    private float latency;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CpuInfo {

        private float cpuAverageUsage;
        private List<Float> cpuPerUsage;
        private int cpuLogicCores;
        private float cpuTemperature;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemoryInfo {

        private long total;
        private long used;
        private long free;
        private float percent;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SwapInfo {

        private long total;
        private long used;
        private long free;
        private float percent;
    }
}
