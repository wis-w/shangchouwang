package com.edu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author wyg_edu
 * @date 2020年6月20日 下午9:43:15
 * @version v1.0
 */ 
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class CrowdMainClass {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdMainClass.class, args);
	}

}
