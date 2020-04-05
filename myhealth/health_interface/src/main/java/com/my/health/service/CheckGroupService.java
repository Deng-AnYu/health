package com.my.health.service;

import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
import com.my.health.pojo.CheckGroup;

import java.util.List;
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

    List<CheckGroup> findAllGroup();

}
