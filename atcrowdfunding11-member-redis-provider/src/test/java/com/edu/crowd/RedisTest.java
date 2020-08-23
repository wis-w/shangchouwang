package com.edu.crowd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wyg_edu
 * @date 2020年6月22日 下午9:53:54
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Test
	public void testRedis() {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set("CIB","CIB是个大苹果");
		log.info("大大大苹果");
	}

}
