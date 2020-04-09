package com.my.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.my.health.constant.MessageConstant;
import com.my.health.constant.RedisMessageConstant;
import com.my.health.pojo.Result;
import com.my.health.util.SMSUtils;
import com.my.health.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: Deng
 * @date: 2020-04-08 23:27
 * @description:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateController {


    @Autowired
    private JedisPool jedisPool;

    //发送预约验证码,并以电话号码加标示为key保存在redis中
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            sendMessageAndSaveToRedis(telephone,RedisMessageConstant.SENDTYPE_ORDER);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

    //发送预约验证码,并以电话号码加标示为key保存在redis中
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        try {
            sendMessageAndSaveToRedis(telephone,RedisMessageConstant.SENDTYPE_LOGIN);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

    //发送短信,并保存到redis中
    private void sendMessageAndSaveToRedis(String telephone,String saveType) throws ClientException {
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        //todo
//        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode.toString());
        Jedis jedis = jedisPool.getResource();
        //并以电话号码加上类型为kye保存在redis中
        jedis.setex(telephone + saveType, 60 * 30, validateCode.toString());
    }
}
