package com.rashome.rashome.mapper;

import com.rashome.rashome.dto.QueryData;
import com.rashome.rashome.po.RasberryPiData;
import java.util.List;

public interface RasberryPiDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RasberryPiData record);

    RasberryPiData selectByPrimaryKey(Long id);

    List<RasberryPiData> selectAll();

    List<RasberryPiData> selectByRasberryPiID(QueryData queryData);

    List<RasberryPiData> selectByTimestamp(QueryData queryData);

    int updateByPrimaryKey(RasberryPiData record);
}