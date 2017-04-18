package com.mws.service.cache;

import org.springframework.stereotype.Service;

import com.mws.model.RedisKeys;

/**
 * Cache - access_token
 *
 * @author xingkong1221
 * @date 2015-11-25
 */
@Service
public class AccessTokenCache extends BaseCache {

    public static final String ACCESS_TOKEN_KEY = "access_token_key";

    public static final RedisKeys.LiveTime expireTime = RedisKeys.LiveTime.HOURS_2;

    /**
     * 把token放入到缓存中
     *
     * @param uid 用户编号
     * @param accessToken token
     */
    public void pushToken(Integer uid, String accessToken) {
        redisService.setEntity(accessToken, uid, expireTime.time);
    }

    /**
     * 从缓存中删除token信息
     *
     * @param accessToken token
     */
    public void removeToken(String accessToken) {
        redisService.del(accessToken);
    }

    /**
     * 判断token是否存在
     *
     * @param accessToken token
     * @return 是否存在
     */
    public boolean isTokenExists(String accessToken) {
        return redisService.exists(accessToken);
    }

    /**
     * 根据token查询用户编号
     *
     * @param accessToken token
     * @return 用户编号
     */
    public Integer getUid(String accessToken) {
        return redisService.getEntity(accessToken);
    }


}
