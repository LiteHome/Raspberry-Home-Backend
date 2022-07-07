package com.rashome.rashome.mapper;

import com.rashome.rashome.po.SensorMapping;
import java.util.List;

public interface SensorMappingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SensorMapping record);

    SensorMapping selectByPrimaryKey(Long id);

    List<SensorMapping> selectAll();

    int updateByPrimaryKey(SensorMapping record);
}