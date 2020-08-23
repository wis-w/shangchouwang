package com.edu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wyg_edu
 * @date 2020年6月20日 下午9:43:15
 * @version v1.0
 */ 
@MapperScan("com.edu.crowd.mapper")
@SpringBootApplication
public class CrowdMainClass {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdMainClass.class, args);
	}

}
