package com.iot.rashome.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iot.rashome.vo.MainControlVO;

@Repository
public interface MainControlDao extends CrudRepository<MainControlVO,Long>  {
 
}
