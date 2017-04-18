package com.mws.service.cache;

import com.mws.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;


abstract class BaseCache {

	@Autowired
	protected RedisService redisService;

}
