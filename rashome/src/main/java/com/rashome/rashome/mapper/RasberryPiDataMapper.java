package com.rashome.rashome.mapper;

import com.rashome.rashome.po.RasberryPiData;
import java.util.List;

public interface RasberryPiDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RasberryPiData record);

    RasberryPiData selectByPrimaryKey(Long id);

    List<RasberryPiData> selectAll();

    int updateByPrimaryKey(RasberryPiData record);
}