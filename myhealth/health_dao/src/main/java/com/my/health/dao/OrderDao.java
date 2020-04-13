package com.my.health.dao;

import java.util.Date;
import java.util.List;
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

    Integer getCountBySetmealId(Integer setmealId);

    Integer findOrderCountByDate(String today);

    Integer findOrderCountBetweenDate(Map<String,Object> map);

    Integer findVisitsCountByDate(String today);

    Integer findVisitsCountAfterDate(Map<String,Object> map);

    List<Map> findHotSetmeal();

}
