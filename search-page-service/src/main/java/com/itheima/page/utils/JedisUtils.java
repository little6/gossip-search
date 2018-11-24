package com.itheima.page.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zjl
 * @create 2018-09-23 15:47
 **/
public class JedisUtils {
    private static JedisPool jedisPool;

    static{
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(20);//最大的闲时的数量
        config.setMinIdle(5); //最小闲时

//        jedisPool = new JedisPool(config,"192.168.72.142",6379);
        jedisPool = new JedisPool(config,"192.168.127.129",6379);
    }

    // 获取连接
    public static Jedis getJedis(){

        Jedis jedis = jedisPool.getResource();
        //授权密码
        jedis.auth("redis");
        return jedis;
    }
}
