package com.rashome.rashome.mapper;

import com.rashome.rashome.po.CameraData;
import java.util.List;

public interface CameraDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CameraData record);

    CameraData selectByPrimaryKey(Long id);

    List<CameraData> selectAll();

    int updateByPrimaryKey(CameraData record);
}