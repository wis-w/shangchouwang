package com.edu.crowd.handler;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.croed.util.ResultEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wyg_edu
 * @date 2020年6月23日 下午8:34:18
 * @version v1.0
 */
@Slf4j
@RestController
public class RedisHandler {
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * redis保存操作
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping("/set/redis/key/value/remote")
	ResultEntity<String> setRedisKVRemote(@RequestParam("key")String key,@RequestParam("value")String value){
		try {
			ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
			opsForValue.set(key, value);
			log.info("redis保存操作结束");
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			log.info("redis保存操作发生异常",e);
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	/**
	 * redis保存，设置有超时时间
	 * @param key
	 * @param value
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	@RequestMapping("/set/redis/key/value/remote/with/timeOut")
	ResultEntity<String> setRedisKVRemoteWithTimeOut(@RequestParam("key")String key,
			@RequestParam("value")String value,
			@RequestParam("time")long time,
			@RequestParam("timeUnit")TimeUnit timeUnit){
		try {
			ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
			opsForValue.set(key, value,time,timeUnit);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			log.info("redis保存操作发生异常",e);
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	/**
	 * 根据key查询redis值
	 * @param key
	 * @return
	 */
	@RequestMapping("/get/redis/string/value/by/key")
	ResultEntity<String> getRedisStringValueByKey(@RequestParam("key")String key){
		try {
			ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
			String string = opsForValue.get(key);
			return ResultEntity.successWithData(string);
		} catch (Exception e) {
			log.info("redis查询操作发生异常",e);
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	/**
	 * 根据key删除redis的value操作
	 * @param key
	 * @return
	 */
	@RequestMapping("/remove/redis/key/remote")
	ResultEntity<String> removeRedisKeyRemote(@RequestParam("key")String key){
		
		try {
			redisTemplate.delete(key);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			log.info("redis删除操作发生异常",e);
			return ResultEntity.failed(e.getMessage());
		}
	}
}
