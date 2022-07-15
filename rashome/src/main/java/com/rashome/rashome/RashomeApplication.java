package com.rashome.rashome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@MapperScan
@ConfigurationPropertiesScan("com.rashome.rashome.config")
public class RashomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RashomeApplication.class, args);
	}

}
