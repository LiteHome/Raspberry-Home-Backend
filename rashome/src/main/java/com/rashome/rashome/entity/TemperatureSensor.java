package com.rashome.rashome.entity;

import com.rashome.rashome.base.Sensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureSensor extends Sensor {
    private float data;
}
