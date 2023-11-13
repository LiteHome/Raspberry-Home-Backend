package com.iot.rashome.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.rashome.dao.DeviceDao;
import com.iot.rashome.vo.DeviceVo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeviceService extends ServiceImpl<DeviceDao, DeviceVo> {

}
