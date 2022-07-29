package com.rashome.rashome.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryData {

    // only set rasberryPiID = query rasberrypi data
    private Long rasberryPiID;

    // set both rasid and sensorid = query sensor data
    private List<Long> sensorsID;

    private long startTimestamp;
    private long endTimestamp;
}
