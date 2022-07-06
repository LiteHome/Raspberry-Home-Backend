package com.rashome.rashome;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rashome.rashome.dao.RasPiHeartbeatDataMapper;
import com.rashome.rashome.dto.RasberryPi;
import com.rashome.rashome.po.RasPiHeartbeatData;

@SpringBootTest
@MapperScan(basePackages = "com.rashome.rashome.dao")
class RashomeApplicationTests {


	private RasPiHeartbeatDataMapper rasPiHeartbeatDataMapper;

	@Autowired
	public void rashomeApplicationTests(RasPiHeartbeatDataMapper rasPiHeartbeatDataMapper){
		this.rasPiHeartbeatDataMapper = rasPiHeartbeatDataMapper;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testDaoSelect(){
		List<RasPiHeartbeatData> data = rasPiHeartbeatDataMapper.selectAll();
		System.out.println(data);
	}

	@Test
	void testDaoInsert(){
	}
}
