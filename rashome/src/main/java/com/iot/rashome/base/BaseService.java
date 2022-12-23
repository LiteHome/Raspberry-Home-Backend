package com.iot.rashome.base;

import java.util.Date;
import java.util.List;

public interface BaseService<V> {

    V insert(V record);
    
    V getLatestRecord(String sampledBy);

    List<V> getRecordBetweenDate(Date leftBorderDate, Date rightBorderDate);
}
