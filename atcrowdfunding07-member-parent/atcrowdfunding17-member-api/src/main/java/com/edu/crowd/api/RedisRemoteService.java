package com.edu.crowd.api;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.croed.util.ResultEntity;

/**
 * @author wyg_edu
 * @date 2020年6月23日 下午8:16:49
 * @version v1.0
 */
@FeignClient("com-crowd-redis")
public interface RedisRemoteService {
	
	@RequestMapping("/set/redis/key/value/remote")
	ResultEntity<String> setRedisKVRemote(@RequestParam("key")String key,@RequestParam("value")String value);
	
	@RequestMapping("/set/redis/key/value/remote/with/timeOut")
	ResultEntity<String> setRedisKVRemoteWithTimeOut(@RequestParam("key")String key,
			@RequestParam("value")String value,
			@RequestParam("time")long time,
			@RequestParam("timeUnit")TimeUnit timeUnit);
	
	
	@RequestMapping("/get/redis/string/value/by/key")
	ResultEntity<String> getRedisStringValueByKey(@RequestParam("key")String key);
	
	@RequestMapping("/remove/redis/key/remote")
	ResultEntity<String> removeRedisKeyRemote(@RequestParam("key")String key);
	
}
