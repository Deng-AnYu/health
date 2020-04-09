package com.my.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.my.health.constant.MessageConstant;
import com.my.health.constant.RedisMessageConstant;
import com.my.health.pojo.Order;
import com.my.health.pojo.Result;
import com.my.health.service.OrderService;
import com.my.health.util.SMSUtils;
import com.my.health.util.ValidateCodeUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-08 23:35
 * @description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    //提交预约数据,进行校验,再进行预约操作
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map orderInfo) {
        Result result = null;
        String telephone = (String) orderInfo.get("telephone");
        try {
            Jedis jedis = jedisPool.getResource();
            String ckeckCode = jedis.get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

            String validateCode = (String) orderInfo.get("validateCode");
            //检查验证码
            if (validateCode != null && validateCode.equals(ckeckCode)) {
                orderInfo.put("orderStatus", "未就诊");
                orderInfo.put("orderType", "微信预约");
                result = orderService.order(orderInfo);
            } else {
                //验证码错误时
                result = new Result(false, MessageConstant.ORDERSETTING_MESSAGE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
        //如果返回的是正确的.表示预约成功
        //这时需要发送一个短信,和用户说已经预约了
        if (result.isFlag()) {
            String orderDate = (String) orderInfo.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
                System.out.println("已经发送短信通知用户");
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(false, MessageConstant.GET_ORDERSETTING_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

}
