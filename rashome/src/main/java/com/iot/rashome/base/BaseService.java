package com.iot.rashome.base;

public interface BaseService<V> {

    V insert(V record);
    
    V getLatestRecord(String sampledBy);
}
