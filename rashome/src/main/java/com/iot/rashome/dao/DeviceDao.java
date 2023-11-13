package com.iot.rashome.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iot.rashome.vo.DeviceVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceDao extends BaseMapper<DeviceVo> {
}
