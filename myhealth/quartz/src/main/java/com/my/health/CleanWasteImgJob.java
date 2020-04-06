package com.my.health;

import com.my.health.constant.RedisConstant;
import com.my.health.util.QiniuUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Author: Deng
 * @date: 2020-04-06 17:11
 * @description:
 */
public class CleanWasteImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clean(){

        Jedis jedis = jedisPool.getResource();
        Set<String> set = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set!=null&&set.size()>0){
            for (String imgName : set) {
                QiniuUtils.deleteFileFromQiniu(imgName);
                jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
            }
        }

    }

}
