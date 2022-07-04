package com.rashome.rashome.dao;

import com.rashome.rashome.po.Dht11Data;
import java.util.List;

public interface Dht11DataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dht11Data record);

    Dht11Data selectByPrimaryKey(Long id);

    List<Dht11Data> selectAll();

    int updateByPrimaryKey(Dht11Data record);
}