package com.iot.rashome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.rashome.dao.MainControlDao;

@Service
public class MainControlService {
    

    @Autowired
    private MainControlDao mainControlDao;


}
