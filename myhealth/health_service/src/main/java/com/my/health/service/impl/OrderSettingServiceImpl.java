package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.dao.OrderSettingDao;
import com.my.health.pojo.OrderSetting;
import com.my.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @Author: Deng
 * @date: 2020-04-06 20:28
 * @description:
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //根据excel添加预约
    @Override
    public void add(ArrayList<OrderSetting> list) {
        //先判断是否存在这个用户的预约
        for (OrderSetting orderSetting : list) {
            int isExist=orderSettingDao.isExist(orderSetting.getId());
            if (isExist>0){
                //有,就更新
                orderSettingDao.updateOrder(orderSetting);
            }else {
                //没有,就创建
                orderSettingDao.addOrder(orderSetting);
            }
        }
    }
}
