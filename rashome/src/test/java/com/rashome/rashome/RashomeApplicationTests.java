package com.rashome.rashome;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.sardine.DavResource;
import com.github.sardine.impl.SardineException;
import com.rashome.rashome.service.NextcloudService;


@SpringBootTest
@MapperScan
@ConfigurationPropertiesScan("com.rashome.rashome.config")
class RashomeApplicationTests {

	private NextcloudService nextcloudService;

	@Autowired
	public RashomeApplicationTests(NextcloudService nextcloudService){
		this.nextcloudService = nextcloudService;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void createFolderTree() throws SardineException, IOException{
		this.nextcloudService.uploadFile(
			new String[]{"test3", "test4", "test5", "test6"},
			"123.txt",
			"hello world".getBytes()
			);
	}
}
