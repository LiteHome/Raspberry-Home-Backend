package com.iot.rashome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.rashome.base.BaseController;
import com.iot.rashome.service.SensorEnviromentService;
import com.iot.rashome.vo.SensorEnviromentVO;

@RestController
@RequestMapping("/sensorEnviroment")
public class SensorEnviromentController extends BaseController<SensorEnviromentService, SensorEnviromentVO> {

    @Override
    public Class<SensorEnviromentVO> setVoType() {
        return SensorEnviromentVO.class;
    }

    @Override
    public Class<?> setChildClass() {
        return SensorEnviromentController.class;
    }
}
