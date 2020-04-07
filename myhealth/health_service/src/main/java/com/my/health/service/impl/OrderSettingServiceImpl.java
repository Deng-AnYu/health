package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.dao.OrderSettingDao;
import com.my.health.pojo.MyOrderSetting;
import com.my.health.pojo.OrderSetting;
import com.my.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        //先判断是否存在这个日期的预约
        for (OrderSetting orderSetting : list) {
            int isExist = orderSettingDao.isExist(orderSetting.getOrderDate());
            if (isExist > 0) {
                //有,就更新
                orderSettingDao.updateOrder(orderSetting);
            } else {
                //没有,就创建
                orderSettingDao.addOrder(orderSetting);
            }
        }
    }

    //根据年月来获取对应的日期预约信息
    @Override
    public List<MyOrderSetting> getDataByYearAndMonth(String date) {
        //根据年月来获取对应的日期预约信息
        String dateEnd=date+"-31";
        String dateBegin=date+"-1";
        HashMap<String, String> map = new HashMap<>();
        map.put("dateEnd",dateEnd);
        map.put("dateBegin",dateBegin);
        return orderSettingDao.getDataByYearAndMonth(map);
    }
}
