package com.ipermission.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * 获取redis连接池资源
 */
@Service("redisPool")
@Slf4j
public class RedisPool {

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    /**
     * 获取实例
     * @return
     */
    public ShardedJedis instance(){
        return shardedJedisPool.getResource();
    }

    /**
     * 关闭连接池
     * @param shardedJedis
     */
    public void safeClose(ShardedJedis shardedJedis){
        try{
            if(shardedJedis != null){
                shardedJedis.close();
            }
        }catch (Exception e){
            log.error("关闭连接池资源失败",e);
        }
    }
}
