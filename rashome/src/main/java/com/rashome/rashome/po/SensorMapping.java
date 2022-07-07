package com.rashome.rashome.po;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorMapping {
    private Long id;

    private String sensorModel;

    private Date createTime;

    private Date updateTime;

    private String sensorType;
}