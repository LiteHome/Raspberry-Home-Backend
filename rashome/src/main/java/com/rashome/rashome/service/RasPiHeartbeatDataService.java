package com.rashome.rashome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rashome.rashome.dao.RasPiHeartbeatDataMapper;
import com.rashome.rashome.dto.RasberryPi;
import com.rashome.rashome.po.RasPiHeartbeatData;

@Service
public class RasPiHeartbeatDataService {
    
    private RasPiHeartbeatDataMapper rasPiHeartbeatDataMapper;

    @Autowired
    public void rasPiHeartbeatDataService(
        RasPiHeartbeatDataMapper rasPiHeartbeatDataMapper
    ){
        this.rasPiHeartbeatDataMapper = rasPiHeartbeatDataMapper;
    }

    public int addRecord(RasberryPi rasberryPi){
        return this.rasPiHeartbeatDataMapper.insert(
            new RasPiHeartbeatData(rasberryPi)
        );
    }
}
