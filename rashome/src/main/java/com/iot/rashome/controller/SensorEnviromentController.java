package com.iot.rashome.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.iot.rashome.base.BaseController;
import com.iot.rashome.dto.Request;
import com.iot.rashome.service.SensorEnviromentService;
import com.iot.rashome.vo.SensorEnviromentVO;

@RestController
@RequestMapping("/sensorEnviroment")
public class SensorEnviromentController extends BaseController<SensorEnviromentService, SensorEnviromentVO> {

    @PostMapping("/")
    public void insert(@RequestBody Request request) throws JsonMappingException, JsonProcessingException{
        realInsert(request, SensorEnviromentVO.class);
    }
}
