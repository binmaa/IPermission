package com.ipermission.service;

import com.google.common.base.Joiner;
import com.ipermission.beans.CacheKeyConstans;
import com.ipermission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 * 缓存service
 */
@Service
@Slf4j
public class SysCacheService {
    @Resource(name = "redisPool")
    private RedisPool redisPool;

    /**
     * save 缓存
     * @param toSaveValue
     * @param timeoutSeconds
     * @param prefix
     */
    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstans prefix){
        this.saveCache(toSaveValue,timeoutSeconds,prefix,null);
    }

    /**
     * save缓存
     * @param toSaveValue 保存的值
     * @param timeoutSeconds 失效时间
     * @param prefix key前缀
     * @param keys keys
     */
    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstans prefix, String... keys){
        if(toSaveValue == null){
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generateCacheKey(prefix, keys);
            ShardedJedis jedis = redisPool.instance();
            jedis.setex(cacheKey,timeoutSeconds,toSaveValue);
        }catch (Exception e){
            log.error("保存缓存异常，prefix:{},keys:{}",prefix.name(), JsonMapper.obj2String(keys),e);
        }finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    /**
     * 获取缓存
     * @param prefix
     * @param keys
     * @return
     */
    public String getFromCache(CacheKeyConstans prefix,String... keys){
        String cacheKey = this.generateCacheKey(prefix, keys);
        ShardedJedis jedis = null;
        try {
            jedis = redisPool.instance();
            String cacheValue = jedis.get(cacheKey);
            return cacheValue;
        }catch (Exception e){
            log.error("获取缓存异常，prefix:{},keys:{}",prefix.name(), JsonMapper.obj2String(keys),e);
            return null;
        }finally {
            redisPool.safeClose(jedis);
        }
    }
    /**
     * 生成CacheKey
     * @param prefix
     * @param keys
     * @return
     */
    private String generateCacheKey(CacheKeyConstans prefix,String... keys){
        String key = prefix.name();
        if(keys != null && keys.length > 0){
            key += "_"+ Joiner.on("_").join(keys);
        }
        return key;
    }
}
