package com.rashome.rashome.mapper;

import com.rashome.rashome.dto.QueryData;
import com.rashome.rashome.po.RasberryPiData;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RasberryPiDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RasberryPiData record);

    RasberryPiData selectByPrimaryKey(Long id);

    List<RasberryPiData> selectAll();

    List<RasberryPiData> selectByRasberryPiID(@Param("queryData") QueryData queryData);

    List<RasberryPiData> selectByTimestamp(@Param("queryData") QueryData queryData);

    int updateByPrimaryKey(RasberryPiData record);
}