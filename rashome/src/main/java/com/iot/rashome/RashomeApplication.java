package com.iot.rashome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("com.iot.rashome.vo")
@EnableScheduling
@EnableTransactionManagement
public class RashomeApplication {
	public static void main(String[] args) {
		SpringApplication.run(RashomeApplication.class, args);
	}

}
