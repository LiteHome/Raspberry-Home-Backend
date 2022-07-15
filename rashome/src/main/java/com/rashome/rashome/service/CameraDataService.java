package com.rashome.rashome.service;

import org.springframework.stereotype.Service;

import com.rashome.rashome.mapper.CameraDataMapper;
import com.rashome.rashome.po.CameraData;

@Service
public class CameraDataService {
    
    private CameraDataMapper cameraDataMapper;

    public CameraDataService(
        CameraDataMapper cameraDataMapper
    ) {
        this.cameraDataMapper = cameraDataMapper;
    }

    public int addRecord(CameraData cameraData){
        return this.cameraDataMapper.insert(cameraData);
    }

    
}
