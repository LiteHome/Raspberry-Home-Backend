package com.rashome.rashome.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class JustOneCache<T> {
    
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<T>> cacheMap;

    public  JustOneCache() {
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
            cacheMap.put(key, new ConcurrentLinkedQueue<T>());
            return null;
        }else{
            return cacheMap.get(key).poll();
        }
    }

    public void putItem(T item, Long rasberryPiID, Long sensorID){

        var key = encodeKey(rasberryPiID, sensorID);

        if (!this.cacheMap.containsKey(key)) {
            cacheMap.put(key, new ConcurrentLinkedQueue<T>());
        }
        var queue = cacheMap.get(key);
        if (!queue.isEmpty()) {
            queue.clear();
        }
        queue.offer(item);
    }
}
