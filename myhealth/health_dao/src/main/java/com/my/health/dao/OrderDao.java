package com.my.health.dao;

import java.util.Date;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-09 17:20
 * @description:
 */
public interface OrderDao {
    int isIdle(Date date);

    int isAlready(Map orderInfo);

    Integer addOrder(Map orderInfo);

    Map getDataForSuccess(Integer id);
}
