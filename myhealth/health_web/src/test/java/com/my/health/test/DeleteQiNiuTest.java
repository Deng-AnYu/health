//package com.my.health.test;
//
//import com.my.health.constant.RedisConstant;
//import com.my.health.util.QiniuUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import java.util.Set;
//
///**
// * @Author: Deng
// * @date: 2020-04-04 22:59
// * @description:
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-redis.xml")
//public class DeleteQiNiuTest {
//
//    @Autowired
//    private JedisPool jedisPool;
//
////    @Test
//    public void testDelete(){
//
//        Jedis jedis = jedisPool.getResource();
//        Set<String> set = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
//                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
//        if (set!=null&&set.size()>0){
//            for (String imgName : set) {
//                QiniuUtils.deleteFileFromQiniu(imgName);
//                jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
//            }
//        }
//
//    }
//
//}
