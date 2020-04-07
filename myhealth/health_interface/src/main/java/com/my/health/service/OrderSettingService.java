package com.my.health.service;

import com.my.health.pojo.MyOrderSetting;
import com.my.health.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-04-06 20:28
 * @description:
 */
public interface OrderSettingService {
    void add(ArrayList<OrderSetting> list);

    List<MyOrderSetting> getDataByYearAndMonth(String date);
}
