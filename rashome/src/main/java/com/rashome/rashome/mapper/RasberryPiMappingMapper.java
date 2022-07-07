package com.rashome.rashome.mapper;

import com.rashome.rashome.po.RasberryPiMapping;
import java.util.List;

public interface RasberryPiMappingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RasberryPiMapping record);

    RasberryPiMapping selectByPrimaryKey(Long id);

    List<RasberryPiMapping> selectAll();

    int updateByPrimaryKey(RasberryPiMapping record);
}