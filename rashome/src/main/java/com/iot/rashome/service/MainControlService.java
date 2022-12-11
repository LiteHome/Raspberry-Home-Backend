package com.iot.rashome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.MainControlDao;
import com.iot.rashome.vo.MainControlVO;

@Service
public class MainControlService {
    
    @Autowired
    private MainControlDao mainControlDao;

    public MainControlVO insert(MainControlVO mainControlVO){
        return mainControlDao.save(mainControlVO);
    }
}
