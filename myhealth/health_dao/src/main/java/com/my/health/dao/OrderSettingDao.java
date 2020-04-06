package com.my.health.dao;

import com.my.health.pojo.OrderSetting;

/**
 * @Author: Deng
 * @date: 2020-04-06 20:28
 * @description:
 */
public interface OrderSettingDao {

    int isExist(Integer id);

    void updateOrder(OrderSetting orderSetting);

    void addOrder(OrderSetting orderSetting);
}
