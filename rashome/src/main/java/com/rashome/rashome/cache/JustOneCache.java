package com.rashome.rashome.cache;

import java.util.concurrent.ConcurrentHashMap;

public class JustOneCache<T> {
    
    private ConcurrentHashMap<String, T> cacheMap;

    public JustOneCache() {
        this.cacheMap = new ConcurrentHashMap<>();
    }

    private String encodeKey(Long rasberryPiID, Long sensorID) {
        if (sensorID == null) {
            return String.valueOf(rasberryPiID);
        }else{
            return rasberryPiID + ":" + sensorID;
        }
    }

    public T getItem(Long rasberryPiID, Long sensorID) {

        var key = encodeKey(rasberryPiID, sensorID);

        if (!cacheMap.contains(key)) {
            return null;
        }else{
            return cacheMap.get(key);
        }
    }

    public void putItem(T item, Long rasberryPiID, Long sensorID){

        var key = encodeKey(rasberryPiID, sensorID);

        this.cacheMap.put(key, item);
    }
}
