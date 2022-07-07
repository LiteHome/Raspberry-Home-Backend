package com.rashome.rashome;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan(basePackages = "com.rashome.rashome.dao")
class RashomeApplicationTests {


	@Test
	void contextLoads() {
	}

	@Test
	void testDaoSelect(){
	}

	@Test
	void testDaoInsert(){
	}
}
