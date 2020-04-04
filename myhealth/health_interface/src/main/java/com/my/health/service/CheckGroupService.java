package com.my.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.PageResult;
import com.my.health.QueryPageBean;
import com.my.health.pojo.CheckGroup;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-03 21:01
 * @description:
 */
public interface CheckGroupService {

    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Map findGroupAndLinkedItem(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(Integer id);
}
