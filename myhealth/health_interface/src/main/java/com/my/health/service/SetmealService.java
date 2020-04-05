package com.my.health.service;

import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
import com.my.health.pojo.Setmeal;

/**
 * @Author: Deng
 * @date: 2020-04-04 20:39
 * @description:
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id, Integer[] checkgroupIds);
}
