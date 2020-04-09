package com.my.health.dao;

import com.my.health.pojo.MyOrderSetting;
import com.my.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-06 20:28
 * @description:
 */
public interface OrderSettingDao {

    int isExist(Date date);

    void updateOrder(OrderSetting orderSetting);

    void addOrder(OrderSetting orderSetting);

    List<MyOrderSetting> getDataByYearAndMonth(Map map);

}
