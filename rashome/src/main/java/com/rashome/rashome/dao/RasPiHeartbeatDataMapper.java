package com.rashome.rashome.dao;

import com.rashome.rashome.po.RasPiHeartbeatData;
import java.util.List;

public interface RasPiHeartbeatDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RasPiHeartbeatData record);

    RasPiHeartbeatData selectByPrimaryKey(Long id);

    List<RasPiHeartbeatData> selectAll();

    int updateByPrimaryKey(RasPiHeartbeatData record);
}