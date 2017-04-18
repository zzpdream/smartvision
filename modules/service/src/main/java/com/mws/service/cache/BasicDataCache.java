package com.mws.service.cache;

import com.mws.model.RedisKeys;
import com.mws.model.repositories.SystemParamDao;
import com.mws.model.sys.Param;
import com.mws.service.QueryCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Cache - 缓存服务
 */
@Service
public class BasicDataCache extends BaseCache {

	@Resource
	private SystemParamDao systemParamDao;

	/**
	 * 查询系统参数
	 *
	 * @param key 键
	 * @return 值
	 */
	public Param findSystemParam(final String key) {

		return redisService.findEntityForCacheOrDb(RedisKeys.SYSTEM_PARAM.key + key,
				new QueryCallback<Param>() {
					@Override
					public Param doInDb() {
						return systemParamDao.findSysParamByKey(key);
					}
				}, RedisKeys.LiveTime.HOURS_1.time);
	}

}
