package com.rashome.rashome.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuerySensorData {
    private Long rasberryPiID;
    private List<Long> sensorsID;
}
